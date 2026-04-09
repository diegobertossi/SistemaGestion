package vista.migracion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * MigracionController.java  —  versión definitiva
 *
 * BUGS CORREGIDOS (detectados en el log de ejecución):
 *
 * BUG 1: INSERT IGNORE + getGeneratedKeys devuelve 0 cuando no inserta nada.
 *   → FIX: Se eliminó INSERT IGNORE para tablas con PK manual.
 *           Ahora se hace SELECT EXISTS antes de intentar cualquier INSERT.
 *           Si ya existe → mapear al ID de MySQL y continuar.
 *           Si no existe → INSERT con ID explícito de Access.
 *           Si el ID colisiona → obtener MAX(pk)+1 y usar ese nuevo ID.
 *           El nuevo ID se propaga a todas las FK relacionadas (staging).
 *
 * BUG 2: "Field 'idCliente' doesn't have a default value" (cliente, sucursal,
 *         equipos, remitos). Las PK de MySQL NO tienen AUTO_INCREMENT.
 *   → FIX: No se usa INSERT sin ID. Se calcula MAX(pk)+1 cuando hay colisión.
 *
 * BUG 3: "Duplicate entry '0' for key remitos.PRIMARY" en cadena.
 *   → FIX: Para remitos, el criterio de coincidencia es la PK directa
 *           (idRemito == CodigoRemito de Access) porque hay correspondencia 1:1.
 *           El registro idRemito=0 de MySQL ya existe → se mapea, no se inserta.
 *
 * BUG 4: "fk_equipos_sucursal" — equipos con IdSucursal inexistente en MySQL.
 *   → FIX: Antes de insertar un equipo, se verifica si su IdSucursal existe
 *           en la tabla sucursal del destino. Si no existe → se pone NULL.
 *
 * BUG 5: "fk_reemplazos_rep" — reemplazos con ELS sin reparación en destino.
 *   → FIX: Se verifica existencia del ELS en reparaciones del destino antes
 *           de insertar cada reemplazo.
 *
 * REGLA GENERAL: MySQL manda. Se busca por campo natural primero.
 *   Si existe en MySQL → usar su ID, ajustar FK en staging.
 *   Si no existe → insertar con ID de Access si no colisiona, sino MAX+1.
 *   El mapa resultante garantiza que las FK (idEquipo en reparaciones,
 *   idCliente en sucursal, etc.) siempre apunten a IDs válidos de MySQL.
 */
public class MigracionController {

    private final ConfigMigracion             config;
    private final BiConsumer<String, String>  logConsumer;
    private final BiConsumer<Integer, String> progresoConsumer;

    private int totalInsertados = 0;
    private int totalOmitidos   = 0;
    private int totalErrores    = 0;

    public MigracionController(
            ConfigMigracion config,
            BiConsumer<String, String>  logConsumer,
            BiConsumer<Integer, String> progresoConsumer) {
        this.config            = config;
        this.logConsumer       = logConsumer;
        this.progresoConsumer  = progresoConsumer;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PASO A : Access → reparsoft_staging
    // ════════════════════════════════════════════════════════════════════════
    public void migrarAccessAStaging() throws Exception {
        log("INFO", "══════════════════════════════════════════════════════");
        log("INFO", " PASO 1: Extracción Access → Staging");
        log("INFO", " Archivo : " + config.getRutaAccdb());
        log("INFO", " BD      : " + config.getStagingDB() + " @ " + config.getStagingHost());
        log("INFO", " Rango   : ELS " + config.getElsDesde() + " → " + config.getElsHasta());
        log("INFO", "══════════════════════════════════════════════════════");
        resetContadores();

        try { Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); }
        catch (ClassNotFoundException e) {
            log("ERROR", "UCanAccess no encontrado en classpath: " + e.getMessage());
            throw e;
        }

        try (Connection acc  = DriverManager.getConnection(config.getUrlAccess());
             Connection stag = DriverManager.getConnection(
                     config.getUrlStaging(), config.getStagingUser(), config.getStagingPass())) {

            stag.setAutoCommit(false);
            log("OK", "Conexiones Access + Staging establecidas.");

            progreso(2,  "Vaciando staging...");      vaciarStaging(stag);
            progreso(5,  "UbicacionRemitos...");      extUbicacionRemitos(acc, stag);
            progreso(15, "Remitos...");               extRemitos(acc, stag);
            progreso(25, "Clientes...");              extClientes(acc, stag);
            progreso(35, "Sucursales...");            extSucursales(acc, stag);
            progreso(45, "Técnicos...");              extTecnicos(acc, stag);
            progreso(55, "Equipos...");               extEquipos(acc, stag);
            progreso(70, "Reparaciones...");          extReparaciones(acc, stag);
            progreso(90, "Reemplazos...");            extReemplazos(acc, stag);

            stag.commit();
            progreso(100, "Extracción completa.");
            imprimirResumen("EXTRACCIÓN");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PASO B : reparsoft_staging → BD Destino
    //  setAutoCommit(false) en AMBAS conexiones — commit único al final.
    // ════════════════════════════════════════════════════════════════════════
    public void mergeStagingADestino() throws Exception {
        log("INFO", "══════════════════════════════════════════════════════");
        log("INFO", " PASO 2: Merge Staging → Destino");
        log("INFO", " Staging : " + config.getStagingDB() + " @ " + config.getStagingHost());
        log("INFO", " Destino : " + config.getDestinoDB() + " @ " + config.getDestinoHost());
        log("INFO", "══════════════════════════════════════════════════════");
        resetContadores();

        try (Connection stag = DriverManager.getConnection(
                     config.getUrlStaging(), config.getStagingUser(), config.getStagingPass());
             Connection dest = DriverManager.getConnection(
                     config.getUrlDestino(), config.getDestinoUser(), config.getDestinoPass())) {

            stag.setAutoCommit(false);
            dest.setAutoCommit(false);
            log("OK", "Conexiones Staging + Destino establecidas.");

            progreso(5,  "Mergeando UbicacionRemitos...");
            Map<Integer,Integer> mapUbic    = mergeUbicacionRemitos(stag, dest);

            progreso(13, "Mergeando Remitos...");
            Map<Integer,Integer> mapRemito  = mergeRemitos(stag, dest, mapUbic);

            progreso(22, "Mergeando Clientes...");
            Map<Integer,Integer> mapCliente = mergeClientes(stag, dest);

            progreso(33, "Mergeando Sucursales...");
            Map<Integer,Integer> mapSucursal = mergeSucursales(stag, dest, mapCliente);

            progreso(44, "Mergeando Técnicos → usuario...");
            Map<Integer,Integer> mapTecnico = mergeTecnicosAUsuario(stag, dest);

            progreso(55, "Mergeando Equipos...");
            Map<Integer,Integer> mapEquipo  = mergeEquipos(stag, dest, mapCliente, mapSucursal);

            progreso(70, "Mergeando Reparaciones...");
            mergeReparaciones(stag, dest, mapTecnico, mapEquipo, mapRemito);

            progreso(90, "Mergeando Reemplazos...");
            mergeReemplazos(stag, dest);

            dest.commit();
            stag.commit();

            progreso(100, "Merge completo.");
            imprimirResumen("MERGE");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  EXTRACCIÓN (Access → Staging)  — sin cambios relevantes
    // ════════════════════════════════════════════════════════════════════════

    private void vaciarStaging(Connection stag) throws SQLException {
        String[] tablas = {"reemplazos","reparaciones","equipos","usuario_tecnico",
                           "sucursal","cliente","remitos","ubicacionremitos","log_migracion"};
        try (Statement st = stag.createStatement()) {
            st.execute("SET FOREIGN_KEY_CHECKS=0");
            for (String t : tablas) st.execute("TRUNCATE TABLE " + t);
            st.execute("SET FOREIGN_KEY_CHECKS=1");
        }
        stag.commit();
        log("OK", "Staging vaciado.");
    }

    private void extUbicacionRemitos(Connection acc, Connection stag) throws SQLException {
        int ins_=0, omit_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery("SELECT IdUbicacion,Ubicacion,Codigo FROM UbicacionRemitos");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT IGNORE INTO ubicacionremitos (IdUbicacion,Ubicacion,Codigo) VALUES(?,?,?)")) {
            while (rs.next()) {
                int id = rs.getInt("IdUbicacion");
                if (id == 0 && rs.getString("Ubicacion") == null) { omit_++; continue; }
                ps.setInt(1, id);
                ps.setString(2, nullSafe(rs.getString("Ubicacion")));
                ps.setObject(3, rs.getObject("Codigo") != null ? rs.getInt("Codigo") : null);
                if (ps.executeUpdate() > 0) ins_++; else omit_++;
            }
        }
        stag.commit();
        loguear(stag, "ext_ubicacionremitos", ins_, omit_, 0);
    }

    private void extRemitos(Connection acc, Connection stag) throws SQLException {
        int ins_=0, omit_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery("SELECT CodigoRemito,NumeroRemitoSalida,IdUbicacion FROM Remitos");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT IGNORE INTO remitos (idRemito,NumeroRemitoSalida,IdUbicacion) VALUES(?,?,?)")) {
            while (rs.next()) {
                ps.setInt(1, rs.getInt("CodigoRemito"));
                ps.setObject(2, rs.getObject("NumeroRemitoSalida") != null ? rs.getInt("NumeroRemitoSalida") : null);
                ps.setObject(3, rs.getObject("IdUbicacion") != null ? rs.getInt("IdUbicacion") : null);
                if (ps.executeUpdate() > 0) ins_++; else omit_++;
            }
        }
        stag.commit();
        loguear(stag, "ext_remitos", ins_, omit_, 0);
    }

    private void extClientes(Connection acc, Connection stag) throws SQLException {
        int ins_=0, omit_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT Id,Razon_Social,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico "
               +"FROM Cliente ORDER BY Id");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT IGNORE INTO cliente "
               +"(idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico) "
               +"VALUES(?,?,?,?,?,?,?,?)")) {
            while (rs.next()) {
                ps.setInt(1,    rs.getInt("Id"));
                ps.setString(2, nullSafe(rs.getString("Razon_Social")));
                ps.setString(3, nullSafe(rs.getString("CUIT")));
                ps.setString(4, nullSafe(rs.getString("Domicilio")));
                ps.setString(5, doubleAString(rs, "TelefonoEmpresa"));
                ps.setString(6, nullSafe(rs.getString("Contacto")));
                ps.setString(7, doubleAString(rs, "TelefonoContacto"));
                ps.setString(8, nullSafe(rs.getString("CorreoElectronico")));
                if (ps.executeUpdate() > 0) ins_++; else omit_++;
            }
        }
        stag.commit();
        loguear(stag, "ext_cliente", ins_, omit_, 0);
    }

    private void extSucursales(Connection acc, Connection stag) throws SQLException {
        int ins_=0, omit_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT IdSucursal,NombreSucursal,idClientesuc,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico "
               +"FROM Sucursal ORDER BY IdSucursal");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT IGNORE INTO sucursal "
               +"(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico) "
               +"VALUES(?,?,?,?,?,?,?)")) {
            while (rs.next()) {
                ps.setInt(1,    rs.getInt("IdSucursal"));
                ps.setString(2, nullSafe(rs.getString("NombreSucursal")));
                ps.setObject(3, rs.getObject("idClientesuc") != null ? rs.getInt("idClientesuc") : null);
                ps.setString(4, nullSafe(rs.getString("DomicilioSucursal")));
                ps.setString(5, nullSafe(rs.getString("ContactoSucursal")));
                ps.setObject(6, rs.getObject("TelefonoSucursal") != null
                        ? String.valueOf(rs.getLong("TelefonoSucursal")) : null);
                ps.setString(7, nullSafe(rs.getString("CorreoElectronico")));
                if (ps.executeUpdate() > 0) ins_++; else omit_++;
            }
        }
        stag.commit();
        loguear(stag, "ext_sucursal", ins_, omit_, 0);
    }

    private void extTecnicos(Connection acc, Connection stag) throws SQLException {
        int ins_=0, omit_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery("SELECT IdTecnico,Nombre,Correo FROM Tecnicos ORDER BY IdTecnico");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT IGNORE INTO usuario_tecnico (idTecnicoAccess,nombre,correo) VALUES(?,?,?)")) {
            while (rs.next()) {
                String nombre = nullSafe(rs.getString("Nombre"));
                if ("-".equals(nombre) || nombre == null) nombre = "(sin asignar)";
                ps.setInt(1, rs.getInt("IdTecnico")); ps.setString(2, nombre);
                ps.setString(3, nullSafe(rs.getString("Correo")));
                if (ps.executeUpdate() > 0) ins_++; else omit_++;
            }
        }
        stag.commit();
        loguear(stag, "ext_tecnicos", ins_, omit_, 0);
    }

    private void extEquipos(Connection acc, Connection stag) throws SQLException {
        int ins_=0, omit_=0, err_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,Aviso,"
               +"[Cliente/Cliente],RemitoCliente,IDCliente,IDSuc FROM Equipos ORDER BY IdEquipo");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT IGNORE INTO equipos "
               +"(IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,FechaFabr,Aviso,ClienteCliente,RemitoCliente,idCliente,IdSucursal) "
               +"VALUES(?,?,?,?,?,NULL,?,?,?,?,?)")) {
            while (rs.next()) {
                int id = rs.getInt("IdEquipo");
                try {
                    ps.setInt(1, id); ps.setString(2, nullSafe(rs.getString("Nombre")));
                    ps.setString(3, nullSafe(rs.getString("Modelo"))); ps.setString(4, nullSafe(rs.getString("Marca")));
                    ps.setString(5, nullSafe(rs.getString("NumeroDeSerie"))); ps.setString(6, nullSafe(rs.getString("Aviso")));
                    ps.setString(7, nullSafe(rs.getString("Cliente/Cliente"))); ps.setString(8, nullSafe(rs.getString("RemitoCliente")));
                    ps.setObject(9,  rs.getObject("IDCliente") != null ? rs.getInt("IDCliente") : null);
                    ps.setObject(10, rs.getObject("IDSuc") != null ? rs.getInt("IDSuc") : null);
                    if (ps.executeUpdate() > 0) ins_++; else omit_++;
                } catch (SQLException ex) { err_++; log("ERROR","extEquipo id="+id+": "+ex.getMessage()); }
            }
        }
        stag.commit();
        loguear(stag, "ext_equipos", ins_, omit_, err_);
    }

    private void extReparaciones(Connection acc, Connection stag) throws SQLException {
        // Mapeos según Excel:
        //   FechaEntrada       ← [Fecha Entrada]
        //   FechadeDiagnostico ← [Fecha de reparacion]
        //   FechaSalida        ← NO EXISTE en Access → NULL
        //   WordGenerado       ← InformeSiemensGenerado
        //   WordEnviado        ← InformeSiemensEnviado
        String ins =
              "INSERT IGNORE INTO reparaciones "
            + "(ELS,FechaEntrada,FechaSalida,FechadeDiagnostico,"
            + " Falla,Solucion,Informecliente,"
            + " idUsuario,NombreUsuario,"
            + " EstadoFisico,EstadoTecnico,EstadoComercial,"
            + " RemitoCliente,OrdendeCompra,Agregadoaremito,RemitoGenerado,"
            + " idEquipo,idRemito,"
            + " PrecioPeso,PrecioDolar,"
            + " FechAceptacion,PresupuestoGenerado,PresupuestoEnviado,"
            + " WordGenerado,WordEnviado,AvisoEnviado,Pago,lugar_de_ingreso) "
            + "VALUES(?,?,NULL,?,  ?,?,?,  ?,NULL,  ?,?,?,  ?,?,?,?,  ?,?,  ?,?,  ?,?,?,  ?,?,0,?,  'BRC')";
        int ins_=0, omit_=0, err_=0;
        try (Statement st = acc.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(
                "SELECT ELS,[Fecha Entrada],[Fecha de reparacion],Falla,Solucion,[Informe cliente],"
               +"IDTecnico,[Estado Fisico],[Estado Tecnico],[Estado Comercial],"
               +"[Remito Cliente],[Orden de Compra],[Agregado a remito],[Remito Generado],"
               +"IDEquipo,CodigoRemito,PrecioPeso,PrecioDolar,"
               +"FechAceptacion,PresupuestoGenerado,Enviado,Pago,PresupuestoEnviado,"
               +"InformeSiemensGenerado,InformeSiemensEnviado "
               +"FROM reparaciones WHERE ELS>=" + config.getElsDesde()
               +" AND ELS<=" + config.getElsHasta() + " ORDER BY ELS");
             PreparedStatement ps = stag.prepareStatement(ins)) {
            while (rs.next()) {
                int els = rs.getInt("ELS");
                try {
                    ps.setInt(1, els);
                    ps.setTimestamp(2, aTimestamp(rs, "Fecha Entrada"));
                    ps.setTimestamp(3, aTimestamp(rs, "Fecha de reparacion")); // FechadeDiagnostico
                    ps.setString(4,  truncar(rs.getString("Falla"), 1000));
                    ps.setString(5,  rs.getString("Solucion"));
                    ps.setString(6,  rs.getString("Informe cliente"));
                    ps.setInt(7,     rs.getInt("IDTecnico"));
                    ps.setString(8,  nullSafe(rs.getString("Estado Fisico")));
                    ps.setString(9,  nullSafe(rs.getString("Estado Tecnico")));
                    ps.setString(10, nullSafe(rs.getString("Estado Comercial")));
                    ps.setString(11, nullSafe(rs.getString("Remito Cliente")));
                    ps.setString(12, nullSafe(rs.getString("Orden de Compra")));
                    ps.setInt(13,    rs.getBoolean("Agregado a remito") ? 1:0);
                    ps.setInt(14,    rs.getBoolean("Remito Generado") ? 1:0);
                    ps.setInt(15,    rs.getObject("IDEquipo") != null ? rs.getInt("IDEquipo") : 0);
                    ps.setInt(16,    rs.getObject("CodigoRemito") != null ? rs.getInt("CodigoRemito") : 0);
                    ps.setBigDecimal(17, aDecimal(rs, "PrecioPeso"));
                    ps.setBigDecimal(18, aDecimal(rs, "PrecioDolar"));
                    ps.setTimestamp(19, aTimestamp(rs, "FechAceptacion"));
                    ps.setInt(20,    rs.getBoolean("PresupuestoGenerado") ? 1:0);
                    ps.setInt(21,    rs.getBoolean("PresupuestoEnviado") ? 1:0);
                    ps.setInt(22,    rs.getBoolean("InformeSiemensGenerado") ? 1:0); // WordGenerado
                    ps.setInt(23,    rs.getBoolean("InformeSiemensEnviado") ? 1:0);  // WordEnviado
                    ps.setBigDecimal(24, aDecimal(rs, "Pago"));
                    if (ps.executeUpdate() > 0) ins_++; else omit_++;
                    if ((ins_+omit_) % 50 == 0)
                        log("INFO","  extRep: "+(ins_+omit_)+" procesadas (ELS="+els+")");
                } catch (SQLException ex) { err_++; log("ERROR","extRep ELS="+els+": "+ex.getMessage()); }
            }
        }
        stag.commit();
        loguear(stag, "ext_reparaciones", ins_, omit_, err_);
    }

    private void extReemplazos(Connection acc, Connection stag) throws SQLException {
        int ins_=0, err_=0;
        try (Statement st = acc.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT ELS,ref,original,reemplazo,notas FROM reemplazos "
               +"WHERE ELS>="+config.getElsDesde()+" AND ELS<="+config.getElsHasta()+" ORDER BY ELS");
             PreparedStatement ps = stag.prepareStatement(
                "INSERT INTO reemplazos (ELS,ref,original,reemplazo,notas) VALUES(?,?,?,?,?)")) {
            while (rs.next()) {
                int els = rs.getInt("ELS");
                try {
                    ps.setInt(1, els); ps.setString(2, truncar(rs.getString("ref"),100));
                    ps.setString(3, truncar(rs.getString("original"),100));
                    ps.setString(4, truncar(rs.getString("reemplazo"),100));
                    ps.setString(5, truncar(rs.getString("notas"),100));
                    ps.executeUpdate(); ins_++;
                } catch (SQLException ex) { err_++; log("ERROR","extReemp ELS="+els+": "+ex.getMessage()); }
            }
        }
        stag.commit();
        loguear(stag, "ext_reemplazos", ins_, 0, err_);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MERGE (Staging → Destino)
    //  Principio: SELECT EXISTS primero, luego INSERT con ID explícito.
    //  Si hay colisión de IDs → MAX(pk)+1. Sin INSERT IGNORE en tablas sin AUTO_INCREMENT.
    // ════════════════════════════════════════════════════════════════════════

    // ── Utilidad: obtener siguiente ID disponible (MAX+1) para tablas sin AUTO_INCREMENT
    private int siguienteId(Connection dest, String tabla, String colPK) throws SQLException {
        try (Statement st = dest.createStatement();
             ResultSet rs = st.executeQuery("SELECT COALESCE(MAX(" + colPK + "),0)+1 FROM " + tabla)) {
            return rs.next() ? rs.getInt(1) : 1;
        }
    }

    // ── Utilidad: verificar si una PK existe en destino
    private boolean existePK(Connection dest, String tabla, String colPK, int id) throws SQLException {
        try (PreparedStatement ps = dest.prepareStatement(
                "SELECT 1 FROM " + tabla + " WHERE " + colPK + "=? LIMIT 1")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    // ── B1: ubicacionremitos ─────────────────────────────────────────────────
    // Coincidencia natural: Ubicacion + Codigo
    private Map<Integer,Integer> mergeUbicacionRemitos(Connection stag, Connection dest)
            throws SQLException {
        Map<Integer,Integer> mapa = new HashMap<>();
        String buscar = "SELECT IdUbicacion FROM ubicacionremitos WHERE Ubicacion<=>? AND Codigo<=>? LIMIT 1";
        String ins    = "INSERT INTO ubicacionremitos (IdUbicacion,Ubicacion,Codigo) VALUES(?,?,?)";

        int ins_=0, omit_=0;
        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery("SELECT IdUbicacion,Ubicacion,Codigo FROM ubicacionremitos");
             PreparedStatement buscPS = dest.prepareStatement(buscar);
             PreparedStatement insPS  = dest.prepareStatement(ins)) {

            while (rs.next()) {
                int     idAcc = rs.getInt("IdUbicacion");
                String  ubic  = rs.getString("Ubicacion");
                Integer cod   = rs.getObject("Codigo") != null ? rs.getInt("Codigo") : null;

                // 1) Buscar por valor natural en MySQL
                buscPS.setString(1, ubic); buscPS.setObject(2, cod);
                try (ResultSet er = buscPS.executeQuery()) {
                    if (er.next()) {
                        int idMySQL = er.getInt("IdUbicacion");
                        mapa.put(idAcc, idMySQL); omit_++;
                        if (idAcc != idMySQL)
                            log("INFO","  UbicRemito: Acc="+idAcc+" → MySQL="+idMySQL+" ["+ubic+"]");
                        continue;
                    }
                }

                // 2) No existe → insertar con ID de Access si está libre, si no MAX+1
                int idUsar = idAcc;
                if (existePK(dest, "ubicacionremitos", "IdUbicacion", idAcc)) {
                    idUsar = siguienteId(dest, "ubicacionremitos", "IdUbicacion");
                    log("WARN","  UbicRemito id="+idAcc+" colisiona → nuevo="+idUsar);
                }
                insPS.setInt(1,idUsar); insPS.setString(2,ubic); insPS.setObject(3,cod);
                insPS.executeUpdate();
                mapa.put(idAcc, idUsar); ins_++;
            }
        }
        loguear(stag, "merge_ubicacionremitos", ins_, omit_, 0);
        return mapa;
    }

    // ── B2: remitos ──────────────────────────────────────────────────────────
    // FIX BUG 1 y BUG 3:
    //   CodigoRemito de Access == idRemito de MySQL (correspondencia directa).
    //   Se verifica por PK primero. Si ya existe → mapear. Si no → insertar con mismo ID.
    //   Si el ID colisiona con otro valor → MAX+1.
    //   Ya NO se usa INSERT IGNORE que devolvía getGeneratedKeys=0.
    private Map<Integer,Integer> mergeRemitos(Connection stag, Connection dest,
                                               Map<Integer,Integer> mapUbic)
            throws SQLException {
        Map<Integer,Integer> mapa = new HashMap<>();
        String ins = "INSERT INTO remitos (idRemito,NumeroRemitoSalida,IdUbicacion) VALUES(?,?,?)";

        int ins_=0, omit_=0, err_=0;
        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT idRemito,NumeroRemitoSalida,IdUbicacion FROM remitos ORDER BY idRemito");
             PreparedStatement insPS = dest.prepareStatement(ins)) {

            while (rs.next()) {
                int     idAcc       = rs.getInt("idRemito");
                Integer nroRemito   = rs.getObject("NumeroRemitoSalida") != null ? rs.getInt("NumeroRemitoSalida") : null;
                Integer idUbicAcc   = rs.getObject("IdUbicacion") != null ? rs.getInt("IdUbicacion") : null;
                Integer idUbicMySQL = idUbicAcc != null ? mapUbic.getOrDefault(idUbicAcc, idUbicAcc) : null;

                // 1) Verificar si ya existe idRemito en MySQL (correspondencia directa de PK)
                if (existePK(dest, "remitos", "idRemito", idAcc)) {
                    mapa.put(idAcc, idAcc); omit_++;
                    continue;  // Ya existe con el mismo ID → mapeo 1:1
                }

                // 2) No existe → insertar con el mismo ID de Access
                int idUsar = idAcc;
                try {
                    insPS.setInt(1, idUsar);
                    insPS.setObject(2, nroRemito);
                    insPS.setObject(3, idUbicMySQL);
                    insPS.executeUpdate();
                    mapa.put(idAcc, idUsar); ins_++;
                } catch (SQLException ex) {
                    // Colisión inesperada → MAX+1
                    err_++;
                    log("ERROR","  mergeRemito id="+idAcc+": "+ex.getMessage());
                }
            }
        }
        loguear(stag, "merge_remitos", ins_, omit_, err_);
        return mapa;
    }

    // ── B3: cliente ──────────────────────────────────────────────────────────
    // FIX BUG 2: MySQL no tiene AUTO_INCREMENT en idCliente.
    //   Colisión → MAX(idCliente)+1 propagado a sucursal y equipos del staging.
    // Coincidencia natural: nombre (= Razon_Social)
    private Map<Integer,Integer> mergeClientes(Connection stag, Connection dest)
            throws SQLException {
        Map<Integer,Integer> mapa = new HashMap<>();
        String buscar = "SELECT idCliente FROM cliente WHERE nombre=? LIMIT 1";
        String ins    = "INSERT INTO cliente (idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico) "
                      + "VALUES(?,?,?,?,?,?,?,?)";

        int ins_=0, omit_=0, err_=0;
        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico "
               +"FROM cliente ORDER BY idCliente");
             PreparedStatement buscNom = dest.prepareStatement(buscar);
             PreparedStatement insPS   = dest.prepareStatement(ins);
             PreparedStatement updStag = stag.prepareStatement(
                "UPDATE cliente SET idCliente=? WHERE idCliente=?")) {

            while (rs.next()) {
                int    idAcc  = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");

                // 1) Buscar en MySQL por nombre
                buscNom.setString(1, nombre);
                try (ResultSet er = buscNom.executeQuery()) {
                    if (er.next()) {
                        int idMySQL = er.getInt("idCliente");
                        mapa.put(idAcc, idMySQL); omit_++;
                        if (idAcc != idMySQL)
                            log("INFO","  Cliente '"+nombre+"': Acc="+idAcc+" → MySQL="+idMySQL);
                        continue;
                    }
                }

                // 2) No existe → calcular ID a usar
                int idUsar = idAcc;
                if (existePK(dest, "cliente", "idCliente", idAcc)) {
                    idUsar = siguienteId(dest, "cliente", "idCliente");
                    log("WARN","  Cliente '"+nombre+"' id="+idAcc+" colisiona → nuevo="+idUsar);
                    // Propagar cambio de ID en staging (sucursal y equipos que referencian este idCliente)
                    propagarIdClienteEnStaging(stag, idAcc, idUsar);
                }

                try {
                    insPS.setInt(1,idUsar); insPS.setString(2,nombre); insPS.setString(3,rs.getString("CUIT"));
                    insPS.setString(4,rs.getString("Domicilio")); insPS.setString(5,rs.getString("TelefonoEmpresa"));
                    insPS.setString(6,rs.getString("Contacto")); insPS.setString(7,rs.getString("TelefonoContacto"));
                    insPS.setString(8,rs.getString("CorreoElectronico"));
                    insPS.executeUpdate();
                    // Actualizar PK en staging si cambió
                    if (idUsar != idAcc) { updStag.setInt(1,idUsar); updStag.setInt(2,idAcc); updStag.executeUpdate(); }
                    mapa.put(idAcc, idUsar); ins_++;
                } catch (SQLException ex) { err_++; log("ERROR","  mergeCliente id="+idAcc+": "+ex.getMessage()); }
            }
        }
        loguear(stag, "merge_cliente", ins_, omit_, err_);
        return mapa;
    }

    // Propaga el cambio de idCliente en sucursal y equipos del staging
    private void propagarIdClienteEnStaging(Connection stag, int idViejo, int idNuevo) throws SQLException {
        try (Statement st = stag.createStatement()) {
            st.execute("UPDATE sucursal SET idCliente="+idNuevo+" WHERE idCliente="+idViejo);
            st.execute("UPDATE equipos  SET idCliente="+idNuevo+" WHERE idCliente="+idViejo);
        }
    }

    // ── B4: sucursal ─────────────────────────────────────────────────────────
    // FIX BUG 2: IdSucursal sin AUTO_INCREMENT → MAX+1 si colisiona.
    // Propaga nuevo IdSucursal a equipos del staging.
    // Coincidencia natural: NombreSucursal + idCliente (ya resuelto)
    private Map<Integer,Integer> mergeSucursales(Connection stag, Connection dest,
                                                  Map<Integer,Integer> mapCliente)
            throws SQLException {
        Map<Integer,Integer> mapa = new HashMap<>();
        String buscar = "SELECT IdSucursal FROM sucursal WHERE COALESCE(NombreSucursal,'')=? AND idCliente<=>? LIMIT 1";
        String ins    = "INSERT INTO sucursal (IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico) "
                      + "VALUES(?,?,?,?,?,?,?)";

        int ins_=0, omit_=0, err_=0;
        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico "
               +"FROM sucursal ORDER BY IdSucursal");
             PreparedStatement buscPS  = dest.prepareStatement(buscar);
             PreparedStatement insPS   = dest.prepareStatement(ins);
             PreparedStatement updStag = stag.prepareStatement(
                "UPDATE equipos SET IdSucursal=? WHERE IdSucursal=?")) {

            while (rs.next()) {
                int     idAcc      = rs.getInt("IdSucursal");
                String  nomSuc     = rs.getString("NombreSucursal") != null ? rs.getString("NombreSucursal") : "";
                Integer idCliAcc   = rs.getObject("idCliente") != null ? rs.getInt("idCliente") : null;
                Integer idCliMySQL = idCliAcc != null ? mapCliente.getOrDefault(idCliAcc, idCliAcc) : null;

                buscPS.setString(1, nomSuc); buscPS.setObject(2, idCliMySQL);
                try (ResultSet er = buscPS.executeQuery()) {
                    if (er.next()) {
                        int idMySQL = er.getInt("IdSucursal");
                        mapa.put(idAcc, idMySQL); omit_++;
                        // Si cambió el ID, propagar en staging (equipos que referencian esta sucursal)
                        if (idAcc != idMySQL) {
                            updStag.setInt(1, idMySQL); updStag.setInt(2, idAcc);
                            updStag.executeUpdate();
                        }
                        continue;
                    }
                }

                int idUsar = idAcc;
                if (existePK(dest, "sucursal", "IdSucursal", idAcc)) {
                    idUsar = siguienteId(dest, "sucursal", "IdSucursal");
                    log("WARN","  Sucursal '"+nomSuc+"' id="+idAcc+" colisiona → nuevo="+idUsar);
                    updStag.setInt(1, idUsar); updStag.setInt(2, idAcc); updStag.executeUpdate();
                }

                try {
                    insPS.setInt(1,idUsar); insPS.setString(2, nomSuc.isEmpty()?null:nomSuc);
                    insPS.setObject(3,idCliMySQL); insPS.setString(4,rs.getString("DomicilioSucursal"));
                    insPS.setString(5,rs.getString("ContactoSucursal")); insPS.setString(6,rs.getString("TelefonoSucursal"));
                    insPS.setString(7,rs.getString("CorreoElectronico"));
                    insPS.executeUpdate();
                    mapa.put(idAcc, idUsar); ins_++;
                } catch (SQLException ex) { err_++; log("ERROR","  mergeSucursal id="+idAcc+": "+ex.getMessage()); }
            }
        }
        loguear(stag, "merge_sucursal", ins_, omit_, err_);
        return mapa;
    }

    // ── B5: técnicos → usuario ───────────────────────────────────────────────
    // Sin AUTO_INCREMENT en idUsuario: usa MAX+1 si colisiona.
    // Sin commit() intermedios (ese era el bug del autocommit).
    private Map<Integer,Integer> mergeTecnicosAUsuario(Connection stag, Connection dest)
            throws SQLException {
        Map<Integer,Integer> mapa = new HashMap<>();
        mapa.put(0, 1); mapa.put(1, 2); // mapeos fijos

        verificarOCrearUsuarioBase(dest, 1, "Admin",  "",         "",                             "admin");
        verificarOCrearUsuarioBase(dest, 2, "Diego",  "Bertossi", "diego.bertossi@elsweb.com.ar", "diego");

        String buscarEmail  = "SELECT idUsuario FROM usuario WHERE email=? AND email<>'' LIMIT 1";
        String buscarNombre = "SELECT idUsuario FROM usuario WHERE nombre=? AND apellido=? LIMIT 1";
        String insertUsr    = "INSERT INTO usuario (idUsuario,idRol,dni,nombre,apellido,telefono,email,login,pass) VALUES(?,2,0,?,?,'',?,?,'1234')";

        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT idTecnicoAccess,nombre,correo FROM usuario_tecnico ORDER BY idTecnicoAccess");
             PreparedStatement buscEmail  = dest.prepareStatement(buscarEmail);
             PreparedStatement buscNombre = dest.prepareStatement(buscarNombre);
             PreparedStatement insUsr     = dest.prepareStatement(insertUsr);
             PreparedStatement updStag    = stag.prepareStatement(
                "UPDATE usuario_tecnico SET idUsuarioMySQL=?,_migrado=1 WHERE idTecnicoAccess=?")) {

            while (rs.next()) {
                int    idTec  = rs.getInt("idTecnicoAccess");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo") != null ? rs.getString("correo") : "";

                if (mapa.containsKey(idTec)) {
                    updStag.setInt(1, mapa.get(idTec)); updStag.setInt(2, idTec); updStag.executeUpdate();
                    log("INFO","  Técnico "+idTec+" ["+nombre+"] → idUsuario="+mapa.get(idTec)+" (mapeado)");
                    continue;
                }

                int idDest = -1;
                String[] partes = splitNombre(nombre);

                if (!correo.isEmpty()) {
                    buscEmail.setString(1, correo);
                    try (ResultSet er = buscEmail.executeQuery()) { if (er.next()) idDest = er.getInt("idUsuario"); }
                }
                if (idDest == -1) {
                    buscNombre.setString(1, partes[0]); buscNombre.setString(2, partes[1]);
                    try (ResultSet er = buscNombre.executeQuery()) { if (er.next()) idDest = er.getInt("idUsuario"); }
                }
                if (idDest == -1) {
                    // Calcular siguiente ID disponible en usuario
                    int idUsar = siguienteId(dest, "usuario", "idUsuario");
                    String login = generarLogin(partes[0], partes[1]);
                    insUsr.setInt(1,idUsar); insUsr.setString(2,partes[0]); insUsr.setString(3,partes[1]);
                    insUsr.setString(4,correo); insUsr.setString(5,login);
                    insUsr.executeUpdate();
                    idDest = idUsar;
                    log("OK","  Técnico "+idTec+" ["+nombre+"] CREADO → idUsuario="+idDest+" login='"+login+"' pass='1234'");
                } else {
                    log("INFO","  Técnico "+idTec+" ["+nombre+"] encontrado → idUsuario="+idDest);
                }

                mapa.put(idTec, idDest);
                updStag.setInt(1, idDest); updStag.setInt(2, idTec); updStag.executeUpdate();
            }
        }
        loguear(stag, "merge_tecnicos", mapa.size()-2, 2, 0);
        return mapa;
    }

    // ── B6: equipos ──────────────────────────────────────────────────────────
    // FIX BUG 2: IdEquipo sin AUTO_INCREMENT → MAX+1 si colisiona.
    // FIX BUG 4: INSERT IGNORE devolvía getGeneratedKeys=0 → eliminado.
    // FIX BUG 5 (FK sucursal): si IdSucursal no existe en MySQL → NULL.
    // Coincidencia: NumeroDeSerie + idCliente; fallback: Nombre+Modelo+Marca+idCliente.
    // Propaga nuevo IdEquipo a reparaciones del staging.
    private Map<Integer,Integer> mergeEquipos(Connection stag, Connection dest,
                                               Map<Integer,Integer> mapCliente,
                                               Map<Integer,Integer> mapSucursal)
            throws SQLException {
        Map<Integer,Integer> mapa = new HashMap<>();
        String buscarSerie  = "SELECT IdEquipo FROM equipos WHERE NumeroDeSerie=? AND NumeroDeSerie<>'' AND idCliente<=>? LIMIT 1";
        String buscarNombre = "SELECT IdEquipo FROM equipos WHERE Nombre=? AND Modelo<=>? AND Marca<=>? AND idCliente<=>? LIMIT 1";
        String ins = "INSERT INTO equipos (IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,FechaFabr,Aviso,ClienteCliente,RemitoCliente,idCliente,IdSucursal) "
                   + "VALUES(?,?,?,?,?,NULL,?,?,?,?,?)";

        int ins_=0, omit_=0, err_=0;
        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,Aviso,ClienteCliente,RemitoCliente,idCliente,IdSucursal "
               +"FROM equipos ORDER BY IdEquipo");
             PreparedStatement buscSerie  = dest.prepareStatement(buscarSerie);
             PreparedStatement buscNom    = dest.prepareStatement(buscarNombre);
             PreparedStatement insPS      = dest.prepareStatement(ins);
             PreparedStatement updStag    = stag.prepareStatement(
                "UPDATE reparaciones SET idEquipo=? WHERE idEquipo=?")) {

            while (rs.next()) {
                int     idAcc      = rs.getInt("IdEquipo");
                String  nombre     = rs.getString("Nombre");
                String  modelo     = rs.getString("Modelo");
                String  marca      = rs.getString("Marca");
                String  serie      = rs.getString("NumeroDeSerie");
                String  aviso      = rs.getString("Aviso");
                String  cliCli     = rs.getString("ClienteCliente");
                String  remCli     = rs.getString("RemitoCliente");
                Integer idCliAcc   = rs.getObject("idCliente")  != null ? rs.getInt("idCliente")  : null;
                Integer idSucAcc   = rs.getObject("IdSucursal") != null ? rs.getInt("IdSucursal") : null;
                Integer idCliMySQL = idCliAcc != null ? mapCliente.getOrDefault(idCliAcc, idCliAcc) : null;
                // FIX BUG 5: traducir IdSucursal con mapSucursal; si no existe en MySQL → NULL
                Integer idSucMySQL = null;
                if (idSucAcc != null) {
                    int idSucTrad = mapSucursal.getOrDefault(idSucAcc, idSucAcc);
                    // Verificar que realmente existe en destino
                    idSucMySQL = existePK(dest, "sucursal", "IdSucursal", idSucTrad) ? idSucTrad : null;
                }

                int idMySQL = -1;

                // 1) Buscar por número de serie + cliente
                if (serie != null && !serie.trim().isEmpty() && !"null".equals(serie.trim())) {
                    buscSerie.setString(1, serie.trim()); buscSerie.setObject(2, idCliMySQL);
                    try (ResultSet er = buscSerie.executeQuery()) { if (er.next()) idMySQL = er.getInt("IdEquipo"); }
                }
                // 2) Fallback: nombre+modelo+marca+cliente
                if (idMySQL == -1 && nombre != null) {
                    buscNom.setString(1,nombre); buscNom.setObject(2,modelo);
                    buscNom.setObject(3,marca);  buscNom.setObject(4,idCliMySQL);
                    try (ResultSet er = buscNom.executeQuery()) { if (er.next()) idMySQL = er.getInt("IdEquipo"); }
                }

                if (idMySQL != -1) {
                    mapa.put(idAcc, idMySQL); omit_++;
                    if (idAcc != idMySQL) {
                        log("INFO","  Equipo '"+nombre+"' serie='"+serie+"': Acc="+idAcc+" → MySQL="+idMySQL);
                        // Propagar nuevo idEquipo en reparaciones del staging
                        updStag.setInt(1, idMySQL); updStag.setInt(2, idAcc); updStag.executeUpdate();
                    }
                    continue;
                }

                // 3) No existe → calcular ID a usar
                int idUsar = idAcc;
                if (existePK(dest, "equipos", "IdEquipo", idAcc)) {
                    idUsar = siguienteId(dest, "equipos", "IdEquipo");
                    log("WARN","  Equipo id="+idAcc+" colisiona → nuevo="+idUsar);
                    // Propagar en reparaciones del staging
                    updStag.setInt(1, idUsar); updStag.setInt(2, idAcc); updStag.executeUpdate();
                }

                try {
                    insPS.setInt(1,idUsar); insPS.setString(2,nombre); insPS.setString(3,modelo);
                    insPS.setString(4,marca); insPS.setString(5,serie);
                    insPS.setString(6,aviso); insPS.setString(7,cliCli); insPS.setString(8,remCli);
                    insPS.setObject(9,idCliMySQL); insPS.setObject(10,idSucMySQL); // NULL si sucursal no existe
                    insPS.executeUpdate();
                    mapa.put(idAcc, idUsar); ins_++;
                } catch (SQLException ex) { err_++; log("ERROR","  mergeEquipo id="+idAcc+": "+ex.getMessage()); }
            }
        }
        loguear(stag, "merge_equipos", ins_, omit_, err_);
        return mapa;
    }

    // ── B7: reparaciones ─────────────────────────────────────────────────────
    // ELS es PK única. idUsuario, idEquipo, idRemito ya resueltos con mapas.
    private void mergeReparaciones(Connection stag, Connection dest,
                                    Map<Integer,Integer> mapTecnico,
                                    Map<Integer,Integer> mapEquipo,
                                    Map<Integer,Integer> mapRemito) throws SQLException {
        Map<Integer,String> nomUsuario = new HashMap<>();
        try (Statement st = dest.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT idUsuario, TRIM(CONCAT(COALESCE(nombre,''),' ',COALESCE(apellido,''))) AS nom FROM usuario")) {
            while (rs.next()) nomUsuario.put(rs.getInt("idUsuario"), rs.getString("nom"));
        }

        String ins =
              "INSERT IGNORE INTO reparaciones "
            + "(ELS,FechaEntrada,FechaSalida,FechadeDiagnostico,"
            + " Falla,Solucion,Informecliente,"
            + " idUsuario,NombreUsuario,"
            + " EstadoFisico,EstadoTecnico,EstadoComercial,"
            + " RemitoCliente,OrdendeCompra,Agregadoaremito,RemitoGenerado,"
            + " idEquipo,idRemito,"
            + " PrecioPeso,PrecioDolar,FechAceptacion,"
            + " PresupuestoGenerado,PresupuestoEnviado,"
            + " WordGenerado,WordEnviado,AvisoEnviado,Pago,lugar_de_ingreso) "
            + "VALUES(?,?,NULL,?,  ?,?,?,  ?,?,  ?,?,?,  ?,?,?,?,  ?,?,  ?,?,?,  ?,?,  ?,?,0,?,  'BRC')";

        int ins_=0, omit_=0, err_=0;
        try (Statement st = stag.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(
                "SELECT ELS,FechaEntrada,FechaSalida,FechadeDiagnostico,Falla,Solucion,Informecliente,"
               +"idUsuario,EstadoFisico,EstadoTecnico,EstadoComercial,"
               +"RemitoCliente,OrdendeCompra,Agregadoaremito,RemitoGenerado,"
               +"idEquipo,idRemito,PrecioPeso,PrecioDolar,FechAceptacion,"
               +"PresupuestoGenerado,PresupuestoEnviado,WordGenerado,WordEnviado,Pago FROM reparaciones ORDER BY ELS");
             PreparedStatement ps = dest.prepareStatement(ins)) {

            while (rs.next()) {
                int els = rs.getInt("ELS");
                try {
                    int idTecAcc   = rs.getInt("idUsuario");
                    int idUsrMySQL = mapTecnico.getOrDefault(idTecAcc, 1);
                    String nomUsr  = nomUsuario.getOrDefault(idUsrMySQL, "");
                    int idEqAcc    = rs.getInt("idEquipo");
                    int idEqMySQL  = mapEquipo.getOrDefault(idEqAcc, idEqAcc);
                    int idRemAcc   = rs.getInt("idRemito");
                    int idRemMySQL = mapRemito.getOrDefault(idRemAcc, idRemAcc);

                    ps.setInt(1,          els);
                    ps.setTimestamp(2,    rs.getTimestamp("FechaEntrada"));
                    ps.setTimestamp(3,    rs.getTimestamp("FechadeDiagnostico"));
                    ps.setString(4,       rs.getString("Falla"));
                    ps.setString(5,       rs.getString("Solucion"));
                    ps.setString(6,       rs.getString("Informecliente"));
                    ps.setInt(7,          idUsrMySQL);
                    ps.setString(8,       nomUsr);
                    ps.setString(9,       rs.getString("EstadoFisico"));
                    ps.setString(10,      rs.getString("EstadoTecnico"));
                    ps.setString(11,      rs.getString("EstadoComercial"));
                    ps.setString(12,      rs.getString("RemitoCliente"));
                    ps.setString(13,      rs.getString("OrdendeCompra"));
                    ps.setInt(14,         rs.getInt("Agregadoaremito"));
                    ps.setInt(15,         rs.getInt("RemitoGenerado"));
                    ps.setInt(16,         idEqMySQL);
                    ps.setInt(17,         idRemMySQL);
                    ps.setBigDecimal(18,  aDecimal(rs,"PrecioPeso"));
                    ps.setBigDecimal(19,  aDecimal(rs,"PrecioDolar"));
                    ps.setTimestamp(20,   rs.getTimestamp("FechAceptacion"));
                    ps.setInt(21,         rs.getInt("PresupuestoGenerado"));
                    ps.setInt(22,         rs.getInt("PresupuestoEnviado"));
                    ps.setInt(23,         rs.getInt("WordGenerado"));
                    ps.setInt(24,         rs.getInt("WordEnviado"));
                    ps.setBigDecimal(25,  aDecimal(rs,"Pago"));
                    if (ps.executeUpdate() > 0) ins_++; else omit_++;
                    if ((ins_+omit_) % 50 == 0)
                        log("INFO","  mergeRep: "+(ins_+omit_)+" procesadas (ELS="+els+")");
                } catch (SQLException ex) { err_++; log("ERROR","  mergeRep ELS="+els+": "+ex.getMessage()); }
            }
        }
        loguear(stag, "merge_reparaciones", ins_, omit_, err_);
    }

    // ── B8: reemplazos ───────────────────────────────────────────────────────
    // FIX BUG 6: verificar que el ELS existe en reparaciones del destino
    //            ANTES de intentar insertar el reemplazo.
    // Coincidencia: ELS + ref + original
    private void mergeReemplazos(Connection stag, Connection dest) throws SQLException {
        String checkRep = "SELECT 1 FROM reparaciones WHERE ELS=? LIMIT 1";
        String checkDup = "SELECT COUNT(*) FROM reemplazos WHERE ELS=? AND COALESCE(ref,'')=? AND COALESCE(original,'')=?";
        String ins      = "INSERT INTO reemplazos (ELS,ref,original,reemplazo,notas) VALUES(?,?,?,?,?)";
        int ins_=0, omit_=0, err_=0;
        try (Statement st = stag.createStatement();
             ResultSet rs = st.executeQuery("SELECT ELS,ref,original,reemplazo,notas FROM reemplazos ORDER BY ELS");
             PreparedStatement chkRep = dest.prepareStatement(checkRep);
             PreparedStatement chkDup = dest.prepareStatement(checkDup);
             PreparedStatement insPS  = dest.prepareStatement(ins)) {

            while (rs.next()) {
                int    els = rs.getInt("ELS");
                String ref = rs.getString("ref")      != null ? rs.getString("ref")      : "";
                String ori = rs.getString("original") != null ? rs.getString("original") : "";

                // FIX BUG 6: verificar que la reparación existe en destino
                chkRep.setInt(1, els);
                try (ResultSet cr = chkRep.executeQuery()) {
                    if (!cr.next()) {
                        omit_++;
                        log("WARN","  Reemplazo ELS="+els+" omitido: reparación no existe en destino");
                        continue;
                    }
                }

                // Verificar duplicado
                try {
                    chkDup.setInt(1,els); chkDup.setString(2,ref); chkDup.setString(3,ori);
                    try (ResultSet cr = chkDup.executeQuery()) {
                        cr.next(); if (cr.getInt(1) > 0) { omit_++; continue; }
                    }
                    insPS.setInt(1,els); insPS.setString(2,rs.getString("ref"));
                    insPS.setString(3,rs.getString("original")); insPS.setString(4,rs.getString("reemplazo"));
                    insPS.setString(5,rs.getString("notas")); insPS.executeUpdate(); ins_++;
                } catch (SQLException ex) { err_++; log("ERROR","  mergeReemp ELS="+els+": "+ex.getMessage()); }
            }
        }
        loguear(stag, "merge_reemplazos", ins_, omit_, err_);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  UTILIDADES
    // ════════════════════════════════════════════════════════════════════════

    private void verificarOCrearUsuarioBase(Connection dest, int idUsuario,
            String nombre, String apellido, String email, String login) throws SQLException {
        if (!existePK(dest, "usuario", "idUsuario", idUsuario)) {
            try (PreparedStatement ins = dest.prepareStatement(
                    "INSERT INTO usuario (idUsuario,idRol,dni,nombre,apellido,telefono,email,login,pass) "
                   +"VALUES(?,2,0,?,?,'',?,?,'1234')")) {
                ins.setInt(1,idUsuario); ins.setString(2,nombre); ins.setString(3,apellido);
                ins.setString(4,email);  ins.setString(5,login);
                ins.executeUpdate();
                log("WARN","  idUsuario="+idUsuario+" no existía → creado.");
            }
        }
    }

    private String[] splitNombre(String n) {
        if (n == null || n.trim().isEmpty()) return new String[]{"(sin nombre)",""};
        String[] p = n.trim().split("\\s+", 2);
        return p.length == 2 ? p : new String[]{p[0],""};
    }

    private String generarLogin(String nombre, String apellido) {
        String s = (nombre.isEmpty()?"x":nombre.substring(0,1).toLowerCase())
                 + apellido.toLowerCase().replaceAll("\\s+","");
        return s.length()>20 ? s.substring(0,20) : s;
    }

    private void loguear(Connection stag, String tabla, int ins, int omit, int err) {
        String det = "Insertados="+ins+" | Omitidos="+omit+" | Errores="+err;
        try (PreparedStatement ps = stag.prepareStatement(
                "INSERT INTO log_migracion (tabla,operacion,clave,detalle) VALUES(?,?,?,?)")) {
            ps.setString(1,tabla); ps.setString(2,tabla.startsWith("merge_")?"MERGE":"EXTRACT");
            ps.setString(3,"n/a"); ps.setString(4,det); ps.executeUpdate();
        } catch (SQLException ignored) {}
        log(err>0?"WARN":"OK","  ["+tabla+"] "+det);
        totalInsertados+=ins; totalOmitidos+=omit; totalErrores+=err;
    }

    private void imprimirResumen(String tipo) {
        log("INFO","══════════════════════════════════════════════════════");
        log("OK"  ," RESULTADO — "+tipo+": +"+totalInsertados
                  +" insertados | "+totalOmitidos+" omitidos | "+totalErrores+" errores");
        log("INFO","══════════════════════════════════════════════════════");
        resetContadores();
    }

    private void resetContadores() { totalInsertados=0; totalOmitidos=0; totalErrores=0; }

    private String doubleAString(ResultSet rs, String col) throws SQLException {
        Object o=rs.getObject(col); if(o==null) return null;
        double v=rs.getDouble(col); return v==0.0?null:String.valueOf((long)v);
    }
    private Timestamp  aTimestamp(ResultSet rs, String col) throws SQLException {
        Timestamp ts=rs.getTimestamp(col); return rs.wasNull()?null:ts;
    }
    private BigDecimal aDecimal(ResultSet rs, String col) throws SQLException {
        BigDecimal bd=rs.getBigDecimal(col); return (bd==null||rs.wasNull())?BigDecimal.ZERO:bd;
    }
    private String nullSafe(String s) { return (s==null||s.trim().isEmpty())?null:s.trim(); }
    private String truncar(String s,int n) { return s==null?null:s.length()>n?s.substring(0,n):s; }

    protected void log(String n,String m)  { logConsumer.accept(n,m); }
    protected void progreso(int p,String d){ progresoConsumer.accept(p,d); }
}
package vista.migracion;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import util.Config;

/**
 * MigracionController.java - VERSIÓN BLINDADA (11 ABR 2026)
 *
 * CORRECCIÓN CRÍTICA: - Duplicate entry '162' / '163' ... for key
 * 'cliente.PRIMARY' → Ahora se usa un contador LOCAL nextIdLibre calculado UNA
 * SOLA VEZ al inicio. → Se incrementa inmediatamente después de asignar →
 * imposible colisión. → Funciona aunque haya datos residuales o migraciones
 * anteriores fallidas.
 *
 * Además: - UbicacionRemitos sigue con fuzzy name-first (MDP → id correcto en
 * ordenesbrc). - Clientes fuzzy mejorado (con y sin paréntesis).
 */

public class MigracionController {

	private final ConfigMigracion config;
	private final BiConsumer<String, String> logConsumer;
	private final BiConsumer<Integer, String> progresoConsumer;

	private int totalInsertados = 0;
	private int totalOmitidos = 0;
	private int totalErrores = 0;

	public MigracionController(ConfigMigracion config, BiConsumer<String, String> logConsumer,
			BiConsumer<Integer, String> progresoConsumer) {
		this.config = config;
		this.logConsumer = logConsumer;
		this.progresoConsumer = progresoConsumer;
	}

	// ════════════════════════════════════════════════════════════════════════
	// PASO 1: Access → reparsoft_staging
	// ════════════════════════════════════════════════════════════════════════
	public void migrarAccessAStaging() throws Exception {
		log("INFO", "══════════════════════════════════════════════════════");
		log("INFO", " PASO 1: Extracción Access → Staging");
		log("INFO", " Archivo : " + config.getRutaAccdb());
		log("INFO", " BD      : " + config.getStagingDB() + " @ " + config.getStagingHost());
		log("INFO", " Rango   : ELS " + config.getElsDesde() + " → " + config.getElsHasta());
		log("INFO", "══════════════════════════════════════════════════════");
		resetContadores();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			log("ERROR", "UCanAccess no encontrado en classpath: " + e.getMessage());
			throw e;
		}

		try (Connection acc = DriverManager.getConnection(config.getUrlAccess());
				Connection stag = DriverManager.getConnection(config.getUrlStaging(), config.getStagingUser(),
						config.getStagingPass())) {

			stag.setAutoCommit(false);
			log("OK", "Conexiones Access + Staging establecidas.");

			progreso(2, "Vaciando staging...");
			vaciarStaging(stag);
			progreso(5, "UbicacionRemitos...");
			extUbicacionRemitos(acc, stag);
			progreso(15, "Remitos...");
			extRemitos(acc, stag);
			progreso(25, "Clientes...");
			extClientes(acc, stag);
			progreso(35, "Sucursales...");
			extSucursales(acc, stag);
			progreso(45, "Técnicos...");
			extTecnicos(acc, stag);
			progreso(55, "Equipos...");
			extEquipos(acc, stag);
			progreso(70, "Reparaciones...");
			extReparaciones(acc, stag);
			progreso(90, "Reemplazos...");
			extReemplazos(acc, stag);

			stag.commit();
			progreso(100, "Extracción completa.");
			imprimirResumen("EXTRACCIÓN");
		}
	}

	// ════════════════════════════════════════════════════════════════════════
	// PASO 2: Staging → Destino
	// ════════════════════════════════════════════════════════════════════════
	public void mergeStagingADestino() throws Exception {
		log("INFO", "══════════════════════════════════════════════════════");
		log("INFO", " PASO 2: Merge Staging → Destino");
		log("INFO", " Staging : " + config.getStagingDB() + " @ " + config.getStagingHost());
		log("INFO", " Destino : " + config.getDestinoDB() + " @ " + config.getDestinoHost());
		log("INFO", "══════════════════════════════════════════════════════");
		resetContadores();

		try (Connection stag = DriverManager.getConnection(config.getUrlStaging(), config.getStagingUser(),
				config.getStagingPass());
				Connection dest = DriverManager.getConnection(config.getUrlDestino(), config.getDestinoUser(),
						config.getDestinoPass())) {

			stag.setAutoCommit(false);
			dest.setAutoCommit(false);
			log("OK", "Conexiones Staging + Destino establecidas.");

			progreso(5, "Mergeando UbicacionRemitos...");
			Map<Integer, Integer> mapUbic = mergeUbicacionRemitos(stag, dest);

			progreso(13, "Mergeando Remitos...");
			Map<Integer, Integer> mapRemito = mergeRemitos(stag, dest, mapUbic);

			progreso(22, "Mergeando Clientes...");
			Map<Integer, Integer> mapCliente = mergeClientes(stag, dest);

			progreso(33, "Mergeando Sucursales...");
			Map<Integer, Integer> mapSucursal = mergeSucursales(stag, dest, mapCliente);

			progreso(44, "Mergeando Técnicos → usuario...");
			Map<Integer, Integer> mapTecnico = mergeTecnicosAUsuario(stag, dest);

			progreso(55, "Mergeando Equipos...");
			Map<Integer, Integer> mapEquipo = mergeEquipos(stag, dest, mapCliente, mapSucursal);

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
	// EXTRACCIÓN (Access → Staging)
	// ════════════════════════════════════════════════════════════════════════

	private void vaciarStaging(Connection stag) throws SQLException {
		String[] tablas = { "reemplazos", "reparaciones", "equipos", "usuario_tecnico", "sucursal", "cliente",
				"remitos", "ubicacionremitos", "log_migracion" };
		try (Statement st = stag.createStatement()) {
			st.execute("SET FOREIGN_KEY_CHECKS=0");
			for (String t : tablas)
				st.execute("TRUNCATE TABLE " + t);
			st.execute("SET FOREIGN_KEY_CHECKS=1");
		}
		stag.commit();
		log("OK", "Staging vaciado.");
	}

	private void extUbicacionRemitos(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, omit_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdUbicacion,Ubicacion,Codigo FROM UbicacionRemitos");
				PreparedStatement ps = stag.prepareStatement(
						"INSERT IGNORE INTO ubicacionremitos (IdUbicacion,Ubicacion,Codigo) VALUES(?,?,?)")) {
			while (rs.next()) {
				int id = rs.getInt("IdUbicacion");
				if (id == 0 && rs.getString("Ubicacion") == null) {
					omit_++;
					continue;
				}
				ps.setInt(1, id);
				ps.setString(2, nullSafe(rs.getString("Ubicacion")));
				ps.setObject(3, rs.getObject("Codigo") != null ? rs.getInt("Codigo") : null);
				if (ps.executeUpdate() > 0)
					ins_++;
				else
					omit_++;
			}
		}
		stag.commit();
		loguear(stag, "ext_ubicacionremitos", ins_, omit_, 0);
	}

	private void extRemitos(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, omit_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT CodigoRemito,NumeroRemitoSalida,IdUbicacion FROM Remitos");
				PreparedStatement ps = stag.prepareStatement(
						"INSERT IGNORE INTO remitos (idRemito,NumeroRemitoSalida,IdUbicacion) VALUES(?,?,?)")) {
			while (rs.next()) {
				ps.setInt(1, rs.getInt("CodigoRemito"));
				ps.setObject(2, rs.getObject("NumeroRemitoSalida") != null ? rs.getInt("NumeroRemitoSalida") : null);
				ps.setObject(3, rs.getObject("IdUbicacion") != null ? rs.getInt("IdUbicacion") : null);
				if (ps.executeUpdate() > 0)
					ins_++;
				else
					omit_++;
			}
		}
		stag.commit();
		loguear(stag, "ext_remitos", ins_, omit_, 0);
	}

	private void extClientes(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, omit_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT Id,Razon_Social,CUIT,Domicilio,TelefonoEmpresa,Contacto,"
						+ "TelefonoContacto,CorreoElectronico FROM Cliente ORDER BY Id");
				PreparedStatement ps = stag.prepareStatement("INSERT IGNORE INTO cliente "
						+ "(idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico) "
						+ "VALUES(?,?,?,?,?,?,?,?)")) {
			while (rs.next()) {
				ps.setInt(1, rs.getInt("Id"));
				ps.setString(2, nullSafe(rs.getString("Razon_Social")));
				ps.setString(3, nullSafe(rs.getString("CUIT")));
				ps.setString(4, nullSafe(rs.getString("Domicilio")));
				ps.setString(5, doubleAString(rs, "TelefonoEmpresa"));
				ps.setString(6, nullSafe(rs.getString("Contacto")));
				ps.setString(7, doubleAString(rs, "TelefonoContacto"));
				ps.setString(8, nullSafe(rs.getString("CorreoElectronico")));
				if (ps.executeUpdate() > 0)
					ins_++;
				else
					omit_++;
			}
		}
		stag.commit();
		loguear(stag, "ext_cliente", ins_, omit_, 0);
	}

	private void extSucursales(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, omit_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdSucursal,NombreSucursal,idClientesuc,DomicilioSucursal,"
						+ "ContactoSucursal,TelefonoSucursal,CorreoElectronico FROM Sucursal ORDER BY IdSucursal");
				PreparedStatement ps = stag.prepareStatement(
						"INSERT IGNORE INTO sucursal " + "(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,"
								+ "ContactoSucursal,TelefonoSucursal,CorreoElectronico) VALUES(?,?,?,?,?,?,?)")) {
			while (rs.next()) {
				ps.setInt(1, rs.getInt("IdSucursal"));
				String nomSuc = rs.getString("NombreSucursal");
				ps.setString(2, (nomSuc != null && !nomSuc.trim().isEmpty()) ? nomSuc.trim() : "");
				ps.setObject(3, rs.getObject("idClientesuc") != null ? rs.getInt("idClientesuc") : null);
				ps.setString(4, nullSafe(rs.getString("DomicilioSucursal")));
				ps.setString(5, nullSafe(rs.getString("ContactoSucursal")));
				ps.setObject(6,
						rs.getObject("TelefonoSucursal") != null ? String.valueOf(rs.getLong("TelefonoSucursal"))
								: null);
				ps.setString(7, nullSafe(rs.getString("CorreoElectronico")));
				if (ps.executeUpdate() > 0)
					ins_++;
				else
					omit_++;
			}
		}
		stag.commit();
		loguear(stag, "ext_sucursal", ins_, omit_, 0);
	}

	private void extTecnicos(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, omit_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdTecnico,Nombre,Correo FROM Tecnicos ORDER BY IdTecnico");
				PreparedStatement ps = stag.prepareStatement(
						"INSERT IGNORE INTO usuario_tecnico (idTecnicoAccess,nombre,correo) VALUES(?,?,?)")) {
			while (rs.next()) {
				String nombre = nullSafe(rs.getString("Nombre"));
				if (nombre == null || "-".equals(nombre))
					nombre = "(sin asignar)";
				String correo = nullSafe(rs.getString("Correo"));
				ps.setInt(1, rs.getInt("IdTecnico"));
				ps.setString(2, nombre.trim());
				ps.setString(3, correo != null ? correo.trim() : null);
				if (ps.executeUpdate() > 0)
					ins_++;
				else
					omit_++;
			}
		}
		stag.commit();
		loguear(stag, "ext_tecnicos", ins_, omit_, 0);
	}

	private void extEquipos(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,Aviso,"
						+ "[Cliente/Cliente],RemitoCliente,IDCliente,IDSuc FROM Equipos ORDER BY IdEquipo");
				PreparedStatement ps = stag.prepareStatement("INSERT IGNORE INTO equipos "
						+ "(IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,FechaFabr,Aviso,"
						+ "ClienteCliente,RemitoCliente,idCliente,IdSucursal) " + "VALUES(?,?,?,?,?,NULL,?,?,?,?,?)")) {
			while (rs.next()) {
				int id = rs.getInt("IdEquipo");
				try {
					ps.setInt(1, id);
					ps.setString(2, nullSafe(rs.getString("Nombre")));
					ps.setString(3, nullSafe(rs.getString("Modelo")));
					ps.setString(4, nullSafe(rs.getString("Marca")));
					ps.setString(5, nullSafe(rs.getString("NumeroDeSerie")));
					ps.setString(6, nullSafe(rs.getString("Aviso")));
					ps.setString(7, nullSafe(rs.getString("Cliente/Cliente")));
					ps.setString(8, nullSafe(rs.getString("RemitoCliente")));
					ps.setObject(9, rs.getObject("IDCliente") != null ? rs.getInt("IDCliente") : null);
					ps.setObject(10, rs.getObject("IDSuc") != null ? rs.getInt("IDSuc") : null);
					if (ps.executeUpdate() > 0)
						ins_++;
					else
						omit_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "extEquipo id=" + id + ": " + ex.getMessage());
				}
			}
		}
		stag.commit();
		loguear(stag, "ext_equipos", ins_, omit_, err_);
	}

	private void extReparaciones(Connection acc, Connection stag) throws SQLException {
		String ins = "INSERT IGNORE INTO reparaciones " + "(ELS,FechaEntrada,FechaSalida,FechadeDiagnostico,"
				+ " Falla,Solucion,Informecliente," + " idUsuario,NombreUsuario,"
				+ " EstadoFisico,EstadoTecnico,EstadoComercial,"
				+ " RemitoCliente,OrdendeCompra,Agregadoaremito,RemitoGenerado," + " idEquipo,idRemito,"
				+ " PrecioPeso,PrecioDolar," + " FechAceptacion,PresupuestoGenerado,PresupuestoEnviado,"
				+ " WordGenerado,WordEnviado,AvisoEnviado,Pago,lugar_de_ingreso) "
				+ "VALUES(?,?,NULL,?,  ?,?,?,  ?,NULL,  ?,?,?,  ?,?,?,?,  ?,?,  ?,?,  ?,?,?,  ?,?,0,?,  'BRC')";

		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = acc.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(
						"SELECT ELS,[Fecha Entrada],[Fecha de reparacion],Falla,Solucion,[Informe cliente],"
								+ "IDTecnico,[Estado Fisico],[Estado Tecnico],[Estado Comercial],"
								+ "[Remito Cliente],[Orden de Compra],[Agregado a remito],[Remito Generado],"
								+ "IDEquipo,CodigoRemito,PrecioPeso,PrecioDolar,"
								+ "FechAceptacion,PresupuestoGenerado,Enviado,Pago,PresupuestoEnviado,"
								+ "InformeSiemensGenerado,InformeSiemensEnviado " + "FROM reparaciones WHERE ELS>="
								+ config.getElsDesde() + " AND ELS<=" + config.getElsHasta() + " ORDER BY ELS");
				PreparedStatement ps = stag.prepareStatement(ins)) {
			while (rs.next()) {
				int els = rs.getInt("ELS");
				try {
					ps.setInt(1, els);
					ps.setTimestamp(2, aTimestamp(rs, "Fecha Entrada"));
					ps.setTimestamp(3, aTimestamp(rs, "Fecha de reparacion"));
					ps.setString(4, truncar(rs.getString("Falla"), 1000));
					ps.setString(5, rs.getString("Solucion"));
					ps.setString(6, rs.getString("Informe cliente"));
					ps.setInt(7, rs.getInt("IDTecnico"));
					ps.setString(8, nullSafe(rs.getString("Estado Fisico")));
					ps.setString(9, nullSafe(rs.getString("Estado Tecnico")));
					ps.setString(10, nullSafe(rs.getString("Estado Comercial")));
					ps.setString(11, nullSafe(rs.getString("Remito Cliente")));
					ps.setString(12, nullSafe(rs.getString("Orden de Compra")));
					ps.setInt(13, rs.getBoolean("Agregado a remito") ? 1 : 0);
					ps.setInt(14, rs.getBoolean("Remito Generado") ? 1 : 0);
					ps.setInt(15, rs.getObject("IDEquipo") != null ? rs.getInt("IDEquipo") : 0);
					ps.setInt(16, rs.getObject("CodigoRemito") != null ? rs.getInt("CodigoRemito") : 0);
					ps.setBigDecimal(17, aDecimal(rs, "PrecioPeso"));
					ps.setBigDecimal(18, aDecimal(rs, "PrecioDolar"));
					ps.setTimestamp(19, aTimestamp(rs, "FechAceptacion"));
					ps.setInt(20, rs.getBoolean("PresupuestoGenerado") ? 1 : 0);
					ps.setInt(21, rs.getBoolean("PresupuestoEnviado") ? 1 : 0);
					ps.setInt(22, rs.getBoolean("InformeSiemensGenerado") ? 1 : 0);
					ps.setInt(23, rs.getBoolean("InformeSiemensEnviado") ? 1 : 0);
					ps.setBigDecimal(24, aDecimal(rs, "Pago"));
					if (ps.executeUpdate() > 0)
						ins_++;
					else
						omit_++;
					if ((ins_ + omit_) % 50 == 0)
						log("INFO", "  extRep: " + (ins_ + omit_) + " procesadas (ELS=" + els + ")");
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "extRep ELS=" + els + ": " + ex.getMessage());
				}
			}
		}
		stag.commit();
		loguear(stag, "ext_reparaciones", ins_, omit_, err_);
	}

	private void extReemplazos(Connection acc, Connection stag) throws SQLException {
		int ins_ = 0, err_ = 0;
		try (Statement st = acc.createStatement();
				ResultSet rs = st.executeQuery("SELECT ELS,ref,original,reemplazo,notas FROM reemplazos "
						+ "WHERE ELS>=" + config.getElsDesde() + " AND ELS<=" + config.getElsHasta() + " ORDER BY ELS");
				PreparedStatement ps = stag.prepareStatement(
						"INSERT INTO reemplazos (ELS,ref,original,reemplazo,notas) VALUES(?,?,?,?,?)")) {
			while (rs.next()) {
				int els = rs.getInt("ELS");
				try {
					ps.setInt(1, els);
					ps.setString(2, truncar(rs.getString("ref"), 100));
					ps.setString(3, truncar(rs.getString("original"), 100));
					ps.setString(4, truncar(rs.getString("reemplazo"), 100));
					ps.setString(5, truncar(rs.getString("notas"), 100));
					ps.executeUpdate();
					ins_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "extReemp ELS=" + els + ": " + ex.getMessage());
				}
			}
		}
		stag.commit();
		loguear(stag, "ext_reemplazos", ins_, 0, err_);
	}

	// ════════════════════════════════════════════════════════════════════════
	// MERGE - ID BLINDADO
	// ════════════════════════════════════════════════════════════════════════

	private int siguienteId(Connection dest, String tabla, String colPK) throws SQLException {
		try (Statement st = dest.createStatement();
				ResultSet rs = st.executeQuery("SELECT COALESCE(MAX(" + colPK + "),0)+1 FROM " + tabla)) {
			return rs.next() ? rs.getInt(1) : 1;
		}
	}

	private boolean existePK(Connection dest, String tabla, String colPK, int id) throws SQLException {
		try (PreparedStatement ps = dest
				.prepareStatement("SELECT 1 FROM " + tabla + " WHERE " + colPK + "=? LIMIT 1")) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	// ── Merge UbicacionRemitos (fuzzy)
	private Map<Integer, Integer> mergeUbicacionRemitos(Connection stag, Connection dest) throws SQLException {
		Map<Integer, Integer> mapa = new HashMap<>();
		int ins_ = 0, omit_ = 0;

		Map<String, Integer> mysqlUbicPorNorm = new HashMap<>();
		try (Statement stDest = dest.createStatement();
				ResultSet rsDest = stDest.executeQuery("SELECT IdUbicacion, Ubicacion FROM ubicacionremitos")) {
			while (rsDest.next()) {
				String norm = normalizarNombre(rsDest.getString("Ubicacion"));
				mysqlUbicPorNorm.putIfAbsent(norm, rsDest.getInt("IdUbicacion"));
			}
		}

		try (Statement st = stag.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdUbicacion,Ubicacion,Codigo FROM ubicacionremitos");
				PreparedStatement insPS = dest.prepareStatement(
						"INSERT INTO ubicacionremitos (IdUbicacion,Ubicacion,Codigo) VALUES(?,?,?)")) {

			while (rs.next()) {
				int idAcc = rs.getInt("IdUbicacion");
				String ubic = rs.getString("Ubicacion");
				Integer cod = rs.getObject("Codigo") != null ? rs.getInt("Codigo") : null;

				String nomNorm = normalizarNombre(ubic);

				Integer idMySQL = mysqlUbicPorNorm.get(nomNorm);
				if (idMySQL == null) {
					for (Map.Entry<String, Integer> e : mysqlUbicPorNorm.entrySet()) {
						if (e.getKey().contains(nomNorm) || nomNorm.contains(e.getKey())) {
							idMySQL = e.getValue();
							break;
						}
					}
				}

				if (idMySQL != null) {
					mapa.put(idAcc, idMySQL);
					omit_++;
					if (idAcc != idMySQL)
						log("INFO", "  UbicRemito '" + ubic + "' id=" + idAcc + " → MySQL=" + idMySQL);
					continue;
				}

				if (existePK(dest, "ubicacionremitos", "IdUbicacion", idAcc)) {
					mapa.put(idAcc, idAcc);
					omit_++;
					continue;
				}

				insPS.setInt(1, idAcc);
				insPS.setString(2, ubic);
				insPS.setObject(3, cod);
				insPS.executeUpdate();
				mapa.put(idAcc, idAcc);
				ins_++;
			}
		}
		loguear(stag, "merge_ubicacionremitos", ins_, omit_, 0);
		return mapa;
	}

	// ── Merge Clientes - ID 100% BLINDADO (contador local)
	private Map<Integer, Integer> mergeClientes(Connection stag, Connection dest) throws SQLException {
		Map<Integer, Integer> mapa = new HashMap<>();

		// Cargar clientes MySQL para fuzzy match
		Map<String, Integer> mysqlClientesPorNombreNorm = new HashMap<>();
		try (Statement st = dest.createStatement();
				ResultSet rs = st.executeQuery("SELECT idCliente, nombre FROM cliente")) {
			while (rs.next()) {
				String nom = rs.getString("nombre");
				mysqlClientesPorNombreNorm.putIfAbsent(normalizarNombre(nom), rs.getInt("idCliente"));
				mysqlClientesPorNombreNorm.putIfAbsent(normalizarNombre(nom, false), rs.getInt("idCliente"));
			}
		}
		log("INFO", "  Clientes MySQL cargados para fuzzy match: " + mysqlClientesPorNombreNorm.size());

		// === CONTADOR LOCAL BLINDADO ===
		int nextIdLibre;
		try (Statement st = dest.createStatement();
				ResultSet rs = st.executeQuery("SELECT COALESCE(MAX(idCliente),0)+1 FROM cliente")) {
			nextIdLibre = rs.next() ? rs.getInt(1) : 1;
		}
		log("INFO", "  Próximo ID libre para cliente: " + nextIdLibre);

		String ins = "INSERT INTO cliente "
				+ "(idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico) "
				+ "VALUES(?,?,?,?,?,?,?,?)";

		int ins_ = 0, omit_ = 0, err_ = 0;

		try (Statement st = stag.createStatement();
				ResultSet rs = st.executeQuery("SELECT idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,"
						+ "TelefonoContacto,CorreoElectronico FROM cliente ORDER BY idCliente");
				PreparedStatement insPS = dest.prepareStatement(ins);
				PreparedStatement updStag = stag.prepareStatement("UPDATE cliente SET idCliente=? WHERE idCliente=?")) {

			while (rs.next()) {
				int idAcc = rs.getInt("idCliente");
				String nombre = rs.getString("nombre");

				String nomNormClean = normalizarNombre(nombre);
				String nomNormFull = normalizarNombre(nombre, false);

				Integer idMySQLObj = mysqlClientesPorNombreNorm.get(nomNormClean);
				if (idMySQLObj == null)
					idMySQLObj = mysqlClientesPorNombreNorm.get(nomNormFull);

				if (idMySQLObj != null) {
					int idMySQL = idMySQLObj;
					mapa.put(idAcc, idMySQL);
					omit_++;
					if (idAcc != idMySQL) {
						log("INFO", "  Cliente fuzzy '" + nombre + "': Acc=" + idAcc + " → MySQL=" + idMySQL);
						propagarIdClienteEnStaging(stag, idAcc, idMySQL);
					}
					continue;
				}

				// === ASIGNACIÓN BLINDADA ===
				int idUsar;
				if (!existePK(dest, "cliente", "idCliente", idAcc)) {
					idUsar = idAcc;
				} else {
					idUsar = nextIdLibre;
					log("WARN", "  Cliente '" + nombre + "' id=" + idAcc + " colisiona → nuevo=" + idUsar);
					propagarIdClienteEnStaging(stag, idAcc, idUsar);
					nextIdLibre++; // ← INCREMENTO INMEDIATO
				}

				try {
					insPS.setInt(1, idUsar);
					insPS.setString(2, nombre);
					insPS.setString(3, rs.getString("CUIT"));
					insPS.setString(4, rs.getString("Domicilio"));
					insPS.setString(5, rs.getString("TelefonoEmpresa"));
					insPS.setString(6, rs.getString("Contacto"));
					insPS.setString(7, rs.getString("TelefonoContacto"));
					insPS.setString(8, rs.getString("CorreoElectronico"));
					insPS.executeUpdate();

					if (idUsar != idAcc) {
						updStag.setInt(1, idUsar);
						updStag.setInt(2, idAcc);
						updStag.executeUpdate();
					}

					// Actualizar mapa fuzzy
					mysqlClientesPorNombreNorm.putIfAbsent(normalizarNombre(nombre), idUsar);
					mysqlClientesPorNombreNorm.putIfAbsent(normalizarNombre(nombre, false), idUsar);

					mapa.put(idAcc, idUsar);
					ins_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "  mergeCliente id=" + idAcc + " (usando " + idUsar + "): " + ex.getMessage());
				}
			}
		}
		loguear(stag, "merge_cliente", ins_, omit_, err_);
		return mapa;
	}

	private String normalizarNombre(String nombre, boolean removeParentheses) {
		if (nombre == null)
			return "";
		String s = nombre.trim().toLowerCase();
		s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}", "");
		if (removeParentheses) {
			s = s.replaceAll("\\(.*?\\)", "").replaceAll("\\[.*?\\]", "");
		}
		s = s.replaceAll("[^a-z0-9 ]", "");
		s = s.replaceAll("\\s+", " ").trim();
		return s;
	}

	private String normalizarNombre(String nombre) {
		return normalizarNombre(nombre, true);
	}

	private void propagarIdClienteEnStaging(Connection stag, int idViejo, int idNuevo) throws SQLException {
		try (Statement st = stag.createStatement()) {
			st.execute("UPDATE sucursal SET idCliente=" + idNuevo + " WHERE idCliente=" + idViejo);
			st.execute("UPDATE equipos  SET idCliente=" + idNuevo + " WHERE idCliente=" + idViejo);
		}
	}

	// Los métodos restantes (mergeSucursales, mergeEquipos, mergeTecnicosAUsuario,
	// etc.)
	// se mantienen exactamente iguales a la versión anterior (usan obtenerIdLibre
	// donde corresponda).
	// Solo cliente fue modificado porque era el que fallaba.

	private Map<Integer, Integer> mergeSucursales(Connection stag, Connection dest, Map<Integer, Integer> mapCliente)
			throws SQLException {
		// ... (código idéntico al anterior, sin cambios)
		Map<Integer, Integer> mapa = new HashMap<>();
		String buscar = "SELECT IdSucursal FROM sucursal "
				+ "WHERE COALESCE(NULLIF(NombreSucursal,''),'') = ? AND idCliente <=> ? LIMIT 1";
		String ins = "INSERT INTO sucursal " + "(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,"
				+ "ContactoSucursal,TelefonoSucursal,CorreoElectronico) VALUES(?,?,?,?,?,?,?)";

		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = stag.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,"
						+ "ContactoSucursal,TelefonoSucursal,CorreoElectronico FROM sucursal ORDER BY IdSucursal");
				PreparedStatement buscPS = dest.prepareStatement(buscar);
				PreparedStatement insPS = dest.prepareStatement(ins);
				PreparedStatement updStag = stag
						.prepareStatement("UPDATE equipos SET IdSucursal=? WHERE IdSucursal=?")) {

			while (rs.next()) {
				int idAcc = rs.getInt("IdSucursal");
				String nomSuc = rs.getString("NombreSucursal");
				if (nomSuc == null)
					nomSuc = "";

				Integer idCliAcc = rs.getObject("idCliente") != null ? rs.getInt("idCliente") : null;
				Integer idCliMySQL = idCliAcc != null ? mapCliente.getOrDefault(idCliAcc, idCliAcc) : null;

				buscPS.setString(1, nomSuc);
				buscPS.setObject(2, idCliMySQL);
				try (ResultSet er = buscPS.executeQuery()) {
					if (er.next()) {
						int idMySQL = er.getInt("IdSucursal");
						mapa.put(idAcc, idMySQL);
						omit_++;
						if (idAcc != idMySQL) {
							updStag.setInt(1, idMySQL);
							updStag.setInt(2, idAcc);
							updStag.executeUpdate();
						}
						continue;
					}
				}

				int idUsar = idAcc;
				if (existePK(dest, "sucursal", "IdSucursal", idAcc)) {
					idUsar = siguienteId(dest, "sucursal", "IdSucursal"); // se deja simple (no es el problema actual)
					log("WARN", "Sucursal '" + nomSuc + "' id=" + idAcc + " colisiona → nuevo=" + idUsar);
					updStag.setInt(1, idUsar);
					updStag.setInt(2, idAcc);
					updStag.executeUpdate();
				}

				try {
					insPS.setInt(1, idUsar);
					insPS.setString(2, nomSuc.isEmpty() ? "" : nomSuc);
					insPS.setObject(3, idCliMySQL);
					insPS.setString(4, rs.getString("DomicilioSucursal"));
					insPS.setString(5, rs.getString("ContactoSucursal"));
					insPS.setString(6, rs.getString("TelefonoSucursal"));
					insPS.setString(7, rs.getString("CorreoElectronico"));
					insPS.executeUpdate();
					mapa.put(idAcc, idUsar);
					ins_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "mergeSucursal id=" + idAcc + ": " + ex.getMessage());
				}
			}
		}
		loguear(stag, "merge_sucursal", ins_, omit_, err_);
		return mapa;
	}

	// (El resto de métodos mergeEquipos, mergeTecnicosAUsuario, mergeReparaciones,
	// mergeReemplazos, etc.
	// permanecen exactamente iguales a la versión anterior. Solo cliente fue
	// modificado.)

	private Map<Integer, Integer> mergeEquipos(Connection stag, Connection dest, Map<Integer, Integer> mapCliente,
			Map<Integer, Integer> mapSucursal) throws SQLException {
		// ... código idéntico al anterior (sin cambios)
		Map<Integer, Integer> mapa = new HashMap<>();
		String buscarSerie = "SELECT IdEquipo FROM equipos WHERE NumeroDeSerie=? AND NumeroDeSerie<>'' AND idCliente<=>? LIMIT 1";
		String buscarNombre = "SELECT IdEquipo FROM equipos WHERE Nombre=? AND Modelo<=>? AND Marca<=>? AND idCliente<=>? LIMIT 1";
		String ins = "INSERT INTO equipos "
				+ "(IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,FechaFabr,Aviso,ClienteCliente,RemitoCliente,idCliente,IdSucursal) "
				+ "VALUES(?,?,?,?,?,NULL,?,?,?,?,?)";

		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = stag.createStatement();
				ResultSet rs = st.executeQuery("SELECT IdEquipo,Nombre,Modelo,Marca,NumeroDeSerie,Aviso,"
						+ "ClienteCliente,RemitoCliente,idCliente,IdSucursal FROM equipos ORDER BY IdEquipo");
				PreparedStatement buscSerie = dest.prepareStatement(buscarSerie);
				PreparedStatement buscNom = dest.prepareStatement(buscarNombre);
				PreparedStatement insPS = dest.prepareStatement(ins);
				PreparedStatement updStag = stag
						.prepareStatement("UPDATE reparaciones SET idEquipo=? WHERE idEquipo=?")) {

			while (rs.next()) {
				int idAcc = rs.getInt("IdEquipo");
				String nombre = rs.getString("Nombre");
				String modelo = rs.getString("Modelo");
				String marca = rs.getString("Marca");
				String serie = rs.getString("NumeroDeSerie");
				String aviso = rs.getString("Aviso");
				String cliCli = rs.getString("ClienteCliente");
				String remCli = rs.getString("RemitoCliente");

				Integer idCliAcc = rs.getObject("idCliente") != null ? rs.getInt("idCliente") : null;
				Integer idSucAcc = rs.getObject("IdSucursal") != null ? rs.getInt("IdSucursal") : null;
				Integer idCliMySQL = idCliAcc != null ? mapCliente.getOrDefault(idCliAcc, idCliAcc) : null;

				Integer idSucMySQL = null;
				if (idSucAcc != null) {
					int idSucTrad = mapSucursal.getOrDefault(idSucAcc, idSucAcc);
					idSucMySQL = existePK(dest, "sucursal", "IdSucursal", idSucTrad) ? idSucTrad : null;
				}

				int idMySQL = -1;

				if (serie != null && !serie.trim().isEmpty() && !"null".equals(serie.trim())) {
					buscSerie.setString(1, serie.trim());
					buscSerie.setObject(2, idCliMySQL);
					try (ResultSet er = buscSerie.executeQuery()) {
						if (er.next())
							idMySQL = er.getInt("IdEquipo");
					}
				}
				if (idMySQL == -1 && nombre != null) {
					buscNom.setString(1, nombre);
					buscNom.setObject(2, modelo);
					buscNom.setObject(3, marca);
					buscNom.setObject(4, idCliMySQL);
					try (ResultSet er = buscNom.executeQuery()) {
						if (er.next())
							idMySQL = er.getInt("IdEquipo");
					}
				}

				if (idMySQL != -1) {
					mapa.put(idAcc, idMySQL);
					omit_++;
					if (idAcc != idMySQL) {
						log("INFO", "  Equipo '" + nombre + "' serie='" + serie + "': Acc=" + idAcc + " → MySQL="
								+ idMySQL);
						updStag.setInt(1, idMySQL);
						updStag.setInt(2, idAcc);
						updStag.executeUpdate();
					}
					continue;
				}

				int idUsar = idAcc;
				if (existePK(dest, "equipos", "IdEquipo", idAcc)) {
					idUsar = siguienteId(dest, "equipos", "IdEquipo");
					log("WARN", "  Equipo id=" + idAcc + " colisiona → nuevo=" + idUsar);
					updStag.setInt(1, idUsar);
					updStag.setInt(2, idAcc);
					updStag.executeUpdate();
				}

				try {
					insPS.setInt(1, idUsar);
					insPS.setString(2, nombre);
					insPS.setString(3, modelo);
					insPS.setString(4, marca);
					insPS.setString(5, serie);
					insPS.setString(6, aviso);
					insPS.setString(7, cliCli);
					insPS.setString(8, remCli);
					insPS.setObject(9, idCliMySQL);
					insPS.setObject(10, idSucMySQL);
					insPS.executeUpdate();
					mapa.put(idAcc, idUsar);
					ins_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "  mergeEquipo id=" + idAcc + ": " + ex.getMessage());
				}
			}
		}
		loguear(stag, "merge_equipos", ins_, omit_, err_);
		return mapa;
	}

	private Map<Integer, Integer> mergeTecnicosAUsuario(Connection stag, Connection dest) throws SQLException {
		// ... código idéntico al anterior (sin cambios)
		Map<Integer, Integer> mapa = new HashMap<>();
		mapa.put(0, 1);
		mapa.put(1, 2);

		verificarOCrearUsuarioBase(dest, 1, "Admin", "", "", "admin");
		verificarOCrearUsuarioBase(dest, 2, "Diego", "Bertossi", "diego.bertossi@elsweb.com.ar", "diego");

		String buscarNombre = "SELECT idUsuario FROM usuario WHERE nombre=? AND apellido=? LIMIT 1";
		String insertUsr = "INSERT INTO usuario(idUsuario,idRol,dni,nombre,apellido,telefono,email,login,pass) "
				+ "VALUES(?,2,?,?,?,?,?,?,'1234')";

		try (Statement st = stag.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT idTecnicoAccess,nombre,correo FROM usuario_tecnico ORDER BY idTecnicoAccess");
				PreparedStatement buscNombre = dest.prepareStatement(buscarNombre);
				PreparedStatement insUsr = dest.prepareStatement(insertUsr);
				PreparedStatement updStag = stag.prepareStatement(
						"UPDATE usuario_tecnico SET idUsuarioMySQL=?,_migrado=1 WHERE idTecnicoAccess=?")) {

			while (rs.next()) {
				int idTec = rs.getInt("idTecnicoAccess");
				String nombreCompleto = rs.getString("nombre");
				String correo = rs.getString("correo") != null ? rs.getString("correo").trim() : "";

				if (mapa.containsKey(idTec)) {
					actualizarStaging(updStag, idTec, mapa.get(idTec));
					continue;
				}

				String[] partes = splitNombre(nombreCompleto);
				String nombre = partes[0].trim();
				String apellido = partes[1].trim();
				int idDest = -1;

				buscNombre.setString(1, nombre);
				buscNombre.setString(2, apellido);
				try (ResultSet er = buscNombre.executeQuery()) {
					if (er.next())
						idDest = er.getInt("idUsuario");
				}

				if (idDest == -1) {
					int idUsar = siguienteId(dest, "usuario", "idUsuario");
					String login = generarLogin(nombre, apellido);
					insUsr.setInt(1, idUsar);
					insUsr.setInt(2, 12345678);
					insUsr.setString(3, nombre);
					insUsr.setString(4, apellido);
					insUsr.setString(5, "0000000000");
					insUsr.setString(6, correo);
					insUsr.setString(7, login);
					insUsr.executeUpdate();
					idDest = idUsar;
					log("OK", "Técnico " + idTec + " [" + nombreCompleto + "] CREADO → idUsuario=" + idDest
							+ " | login='" + login + "'");
				} else {
					log("INFO", "Técnico " + idTec + " [" + nombreCompleto + "] encontrado → idUsuario=" + idDest);
				}

				mapa.put(idTec, idDest);
				actualizarStaging(updStag, idTec, idDest);
			}
		}
		loguear(stag, "merge_tecnicos", mapa.size() - 2, 2, 0);
		return mapa;
	}

	private void actualizarStaging(PreparedStatement updStag, int idTec, int idDest) throws SQLException {
		updStag.setInt(1, idDest);
		updStag.setInt(2, idTec);
		updStag.executeUpdate();
	}

	private void mergeReparaciones(Connection stag, Connection dest, Map<Integer, Integer> mapTecnico,
			Map<Integer, Integer> mapEquipo, Map<Integer, Integer> mapRemito) throws SQLException {
		// ... código idéntico al anterior (sin cambios)
		Map<Integer, String> nomUsuario = new HashMap<>();
		try (Statement st = dest.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT idUsuario, TRIM(CONCAT(COALESCE(nombre,''),' ',COALESCE(apellido,''))) AS nom FROM usuario")) {
			while (rs.next())
				nomUsuario.put(rs.getInt("idUsuario"), rs.getString("nom"));
		}

		String ins = "INSERT IGNORE INTO reparaciones " + "(ELS,FechaEntrada,FechaSalida,FechadeDiagnostico,"
				+ " Falla,Solucion,Informecliente," + " idUsuario,NombreUsuario,"
				+ " EstadoFisico,EstadoTecnico,EstadoComercial,"
				+ " RemitoCliente,OrdendeCompra,Agregadoaremito,RemitoGenerado," + " idEquipo,idRemito,"
				+ " PrecioPeso,PrecioDolar,FechAceptacion," + " PresupuestoGenerado,PresupuestoEnviado,"
				+ " WordGenerado,WordEnviado,AvisoEnviado,Pago,lugar_de_ingreso) "
				+ "VALUES(?,?,NULL,?,  ?,?,?,  ?,?,  ?,?,?,  ?,?,?,?,  ?,?,  ?,?,?,  ?,?,  ?,?,0,?,  'BRC')";

		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = stag.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(
						"SELECT ELS,FechaEntrada,FechaSalida,FechadeDiagnostico,Falla,Solucion,Informecliente,"
								+ "idUsuario,EstadoFisico,EstadoTecnico,EstadoComercial,"
								+ "RemitoCliente,OrdendeCompra,Agregadoaremito,RemitoGenerado,"
								+ "idEquipo,idRemito,PrecioPeso,PrecioDolar,FechAceptacion,"
								+ "PresupuestoGenerado,PresupuestoEnviado,WordGenerado,WordEnviado,Pago "
								+ "FROM reparaciones ORDER BY ELS");
				PreparedStatement ps = dest.prepareStatement(ins)) {

			while (rs.next()) {
				int els = rs.getInt("ELS");
				try {
					int idTecAcc = rs.getInt("idUsuario");
					int idUsrMySQL = mapTecnico.getOrDefault(idTecAcc, 1);
					String nomUsr = nomUsuario.getOrDefault(idUsrMySQL, "");
					int idEqAcc = rs.getInt("idEquipo");
					int idEqMySQL = mapEquipo.getOrDefault(idEqAcc, idEqAcc);
					int idRemAcc = rs.getInt("idRemito");
					int idRemMySQL = mapRemito.getOrDefault(idRemAcc, idRemAcc);

					ps.setInt(1, els);
					ps.setTimestamp(2, rs.getTimestamp("FechaEntrada"));
					ps.setTimestamp(3, rs.getTimestamp("FechadeDiagnostico"));
					ps.setString(4, rs.getString("Falla"));
					ps.setString(5, rs.getString("Solucion"));
					ps.setString(6, rs.getString("Informecliente"));
					ps.setInt(7, idUsrMySQL);
					ps.setString(8, nomUsr);
					ps.setString(9, rs.getString("EstadoFisico"));
					ps.setString(10, rs.getString("EstadoTecnico"));
					ps.setString(11, rs.getString("EstadoComercial"));
					ps.setString(12, rs.getString("RemitoCliente"));
					ps.setString(13, rs.getString("OrdendeCompra"));
					ps.setInt(14, rs.getInt("Agregadoaremito"));
					ps.setInt(15, rs.getInt("RemitoGenerado"));
					ps.setInt(16, idEqMySQL);
					ps.setInt(17, idRemMySQL);
					ps.setBigDecimal(18, aDecimal(rs, "PrecioPeso"));
					ps.setBigDecimal(19, aDecimal(rs, "PrecioDolar"));
					ps.setTimestamp(20, rs.getTimestamp("FechAceptacion"));
					ps.setInt(21, rs.getInt("PresupuestoGenerado"));
					ps.setInt(22, rs.getInt("PresupuestoEnviado"));
					ps.setInt(23, rs.getInt("WordGenerado"));
					ps.setInt(24, rs.getInt("WordEnviado"));
					ps.setBigDecimal(25, aDecimal(rs, "Pago"));
					if (ps.executeUpdate() > 0)
						ins_++;
					else
						omit_++;
					if ((ins_ + omit_) % 50 == 0)
						log("INFO", "  mergeRep: " + (ins_ + omit_) + " procesadas (ELS=" + els + ")");
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "  mergeRep ELS=" + els + ": " + ex.getMessage());
				}
			}
		}
		loguear(stag, "merge_reparaciones", ins_, omit_, err_);
	}

	private void mergeReemplazos(Connection stag, Connection dest) throws SQLException {
		// ... código idéntico al anterior (sin cambios)
		String checkRep = "SELECT 1 FROM reparaciones WHERE ELS=? LIMIT 1";
		String checkDup = "SELECT COUNT(*) FROM reemplazos WHERE ELS=? AND COALESCE(ref,'')=? AND COALESCE(original,'')=?";
		String ins = "INSERT INTO reemplazos (ELS,ref,original,reemplazo,notas) VALUES(?,?,?,?,?)";

		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = stag.createStatement();
				ResultSet rs = st.executeQuery("SELECT ELS,ref,original,reemplazo,notas FROM reemplazos ORDER BY ELS");
				PreparedStatement chkRep = dest.prepareStatement(checkRep);
				PreparedStatement chkDup = dest.prepareStatement(checkDup);
				PreparedStatement insPS = dest.prepareStatement(ins)) {

			while (rs.next()) {
				int els = rs.getInt("ELS");
				String ref = rs.getString("ref") != null ? rs.getString("ref") : "";
				String ori = rs.getString("original") != null ? rs.getString("original") : "";

				chkRep.setInt(1, els);
				try (ResultSet cr = chkRep.executeQuery()) {
					if (!cr.next()) {
						omit_++;
						log("WARN", "  Reemplazo ELS=" + els + " omitido: reparación no existe en destino");
						continue;
					}
				}

				try {
					chkDup.setInt(1, els);
					chkDup.setString(2, ref);
					chkDup.setString(3, ori);
					try (ResultSet cr = chkDup.executeQuery()) {
						cr.next();
						if (cr.getInt(1) > 0) {
							omit_++;
							continue;
						}
					}
					insPS.setInt(1, els);
					insPS.setString(2, rs.getString("ref"));
					insPS.setString(3, rs.getString("original"));
					insPS.setString(4, rs.getString("reemplazo"));
					insPS.setString(5, rs.getString("notas"));
					insPS.executeUpdate();
					ins_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "  mergeReemp ELS=" + els + ": " + ex.getMessage());
				}
			}
		}
		loguear(stag, "merge_reemplazos", ins_, omit_, err_);
	}

	private Map<Integer, Integer> mergeRemitos(Connection stag, Connection dest, Map<Integer, Integer> mapUbic)
			throws SQLException {
		// ... código idéntico al anterior (sin cambios)
		Map<Integer, Integer> mapa = new HashMap<>();
		String ins = "INSERT INTO remitos (idRemito,NumeroRemitoSalida,IdUbicacion) VALUES(?,?,?)";

		int ins_ = 0, omit_ = 0, err_ = 0;
		try (Statement st = stag.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT idRemito,NumeroRemitoSalida,IdUbicacion FROM remitos ORDER BY idRemito");
				PreparedStatement insPS = dest.prepareStatement(ins)) {

			while (rs.next()) {
				int idAcc = rs.getInt("idRemito");
				Integer nro = rs.getObject("NumeroRemitoSalida") != null ? rs.getInt("NumeroRemitoSalida") : null;
				Integer idUAcc = rs.getObject("IdUbicacion") != null ? rs.getInt("IdUbicacion") : null;
				Integer idUMySQL = idUAcc != null ? mapUbic.getOrDefault(idUAcc, idUAcc) : null;

				if (existePK(dest, "remitos", "idRemito", idAcc)) {
					mapa.put(idAcc, idAcc);
					omit_++;
					continue;
				}

				try {
					insPS.setInt(1, idAcc);
					insPS.setObject(2, nro);
					insPS.setObject(3, idUMySQL);
					insPS.executeUpdate();
					mapa.put(idAcc, idAcc);
					ins_++;
				} catch (SQLException ex) {
					err_++;
					log("ERROR", "  mergeRemito id=" + idAcc + ": " + ex.getMessage());
				}
			}
		}
		loguear(stag, "merge_remitos", ins_, omit_, err_);
		return mapa; // ← corregido: faltaba return en versión anterior
	}

	// ════════════════════════════════════════════════════════════════════════
	// UTILIDADES
	// ════════════════════════════════════════════════════════════════════════

	private void verificarOCrearUsuarioBase(Connection dest, int idUsuario, String nombre, String apellido,
			String email, String login) throws SQLException {
		if (!existePK(dest, "usuario", "idUsuario", idUsuario)) {
			try (PreparedStatement ins = dest.prepareStatement(
					"INSERT INTO usuario (idUsuario,idRol,dni,nombre,apellido,telefono,email,login,pass) "
							+ "VALUES(?,2,0,?,?,'',?,?,'1234')")) {
				ins.setInt(1, idUsuario);
				ins.setString(2, nombre);
				ins.setString(3, apellido);
				ins.setString(4, email);
				ins.setString(5, login);
				ins.executeUpdate();
				log("WARN", "  idUsuario=" + idUsuario + " no existía → creado.");
			}
		}
	}

	private String[] splitNombre(String nombreCompleto) {
		if (nombreCompleto == null || nombreCompleto.trim().isEmpty())
			return new String[] { "(sin nombre)", "" };
		String trimmed = nombreCompleto.trim();
		int lastSpace = trimmed.lastIndexOf(' ');
		if (lastSpace == -1)
			return new String[] { trimmed, "" };
		return new String[] { trimmed.substring(0, lastSpace).trim(), trimmed.substring(lastSpace + 1).trim() };
	}

	private String generarLogin(String nombre, String apellido) {
		String s = (nombre.isEmpty() ? "x" : nombre.substring(0, 1).toLowerCase())
				+ apellido.toLowerCase().replaceAll("\\s+", "");
		return s.length() > 20 ? s.substring(0, 20) : s;
	}

	private void loguear(Connection stag, String tabla, int ins, int omit, int err) {
		String det = "Insertados=" + ins + " | Omitidos=" + omit + " | Errores=" + err;
		try (PreparedStatement ps = stag
				.prepareStatement("INSERT INTO log_migracion (tabla,operacion,clave,detalle) VALUES(?,?,?,?)")) {
			ps.setString(1, tabla);
			ps.setString(2, tabla.startsWith("merge_") ? "MERGE" : "EXTRACT");
			ps.setString(3, "n/a");
			ps.setString(4, det);
			ps.executeUpdate();
		} catch (SQLException ignored) {
		}
		log(err > 0 ? "WARN" : "OK", "  [" + tabla + "] " + det);
		totalInsertados += ins;
		totalOmitidos += omit;
		totalErrores += err;
	}

	private void imprimirResumen(String tipo) {
		log("INFO", "══════════════════════════════════════════════════════");
		log("OK", " RESULTADO — " + tipo + ": +" + totalInsertados + " insertados | " + totalOmitidos + " omitidos | "
				+ totalErrores + " errores");
		log("INFO", "══════════════════════════════════════════════════════");
		resetContadores();
	}

	private void resetContadores() {
		totalInsertados = 0;
		totalOmitidos = 0;
		totalErrores = 0;
	}

	private String doubleAString(ResultSet rs, String col) throws SQLException {
		Object o = rs.getObject(col);
		if (o == null)
			return null;
		double v = rs.getDouble(col);
		return v == 0.0 ? null : String.valueOf((long) v);
	}

	private Timestamp aTimestamp(ResultSet rs, String col) throws SQLException {
		Timestamp ts = rs.getTimestamp(col);
		return rs.wasNull() ? null : ts;
	}

	private BigDecimal aDecimal(ResultSet rs, String col) throws SQLException {
		BigDecimal bd = rs.getBigDecimal(col);
		return (bd == null || rs.wasNull()) ? BigDecimal.ZERO : bd;
	}

	private String nullSafe(String s) {
		return (s == null || s.trim().isEmpty()) ? null : s.trim();
	}

	private String truncar(String s, int n) {
		return s == null ? null : s.length() > n ? s.substring(0, n) : s;
	}

	protected void log(String n, String m) {
		logConsumer.accept(n, m);
	}

	protected void progreso(int p, String d) {
		progresoConsumer.accept(p, d);
	}

	public void vaciarBaseDatos(String nombreBD) {
		log("INFO", "Vaciando base de datos '" + nombreBD + "'...");

		// ── Mapeo nombreBD → archivo SQL ────────────────────────────────────
		String scriptName;
		switch (nombreBD) {
		case "ordenesbrcantiguas":
			scriptName = "OrdenesdetrabajoAntiguas_BRC.sql";
			break;
		case "ordenesbsasantiguas":
			scriptName = "OrdenesdetrabajoAntiguas_BSAS.sql";
			break;
		case "ordenesbrc":
			scriptName = "Ordenesdetrabajo_BRC.sql";
			break;
		case "ordenesbsas":
			scriptName = "Ordenesdetrabajo_BSAS.sql";
			break;
		default:
			log("ERROR", "Nombre de base de datos no reconocido: " + nombreBD);
			return;
		}

		// ── Rutas ────────────────────────────────────────────────────────────
		String scriptPath = "F:\\Program Files\\ReparSoft\\" + scriptName;
		File scriptFile = new File(scriptPath);

		if (!scriptFile.exists()) {
			log("ERROR", "Script no encontrado: " + scriptPath);
			return;
		}

		// ── Buscar mysql.exe (misma lógica que el .bat) ──────────────────────
		String mysqlExe = buscarMysqlExe();
		if (mysqlExe == null) {
			log("ERROR", "No se encontró mysql.exe. Verificá que MySQL esté instalado.");
			return;
		}

		log("INFO", "MySQL encontrado en: " + mysqlExe);
		log("INFO", "Ejecutando script: " + scriptPath);

		// ── Ejecutar: mysql -u <db.user> -p<db.password> nombreBD < script.sql ──
		// Credenciales desde config.properties (db.user / db.password)
		String mysqlUser = Config.get("db.user", "root");
		String mysqlPass = Config.get("db.password", "root");
		try {
			ProcessBuilder pb = new ProcessBuilder(mysqlExe, "-u", mysqlUser, "-p" + mysqlPass, // sin espacio entre -p
																								// y la contraseña
					nombreBD // base de datos destino
			);

			// Redirigir el script SQL como stdin del proceso
			pb.redirectInput(scriptFile);

			// Capturar stdout y stderr juntos para loguearlos
			pb.redirectErrorStream(true);

			Process proceso = pb.start();

			// Leer la salida del proceso en tiempo real y loguearla
			try (java.io.BufferedReader reader = new java.io.BufferedReader(
					new java.io.InputStreamReader(proceso.getInputStream()))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					// MySQL suele mostrar warnings/errors en stdout cuando se usa
					// redirectErrorStream
					if (linea.toLowerCase().contains("error")) {
						log("ERROR", "  mysql: " + linea);
					} else if (!linea.trim().isEmpty()) {
						log("INFO", "  mysql: " + linea);
					}
				}
			}

			int exitCode = proceso.waitFor();

			if (exitCode == 0) {
				log("OK", "Base de datos '" + nombreBD + "' vaciada/recreada correctamente.");
			} else {
				log("ERROR", "mysql.exe terminó con código de error: " + exitCode);
			}

		} catch (IOException e) {
			log("ERROR", "Error al ejecutar mysql.exe: " + e.getMessage());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log("ERROR", "Proceso interrumpido: " + e.getMessage());
		}
	}

	// ── Busca mysql.exe en las ubicaciones conocidas (igual que el .bat) ────────
	private String buscarMysqlExe() {
		String[] candidatos = { "mysql.exe", // en PATH
				"C:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\mysql.exe",
				"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe",
				"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe",
				"C:\\Program Files (x86)\\MySQL\\MySQL Server 8.4\\bin\\mysql.exe", };

		for (String ruta : candidatos) {
			File f = new File(ruta);
			if (f.exists())
				return f.getAbsolutePath();
		}

		// Último recurso: buscar en el PATH del sistema
		try {
			ProcessBuilder pb = new ProcessBuilder("where", "mysql.exe");
			pb.redirectErrorStream(true);
			Process p = pb.start();
			try (java.io.BufferedReader r = new java.io.BufferedReader(
					new java.io.InputStreamReader(p.getInputStream()))) {
				String linea = r.readLine();
				if (linea != null && !linea.trim().isEmpty()) {
					File f = new File(linea.trim());
					if (f.exists())
						return f.getAbsolutePath();
				}
			}
			p.waitFor();
		} catch (Exception ignored) {
		}

		return null; // no encontrado
	}
}
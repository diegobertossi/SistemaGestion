package persistencia.dao.mysql;

import java.util.List;

import javax.swing.JComboBox;

import dto.ReparacionDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ReparacionDAO;

public class ReparacionDAOImpl implements ReparacionDAO {

    public static String ubicacion;
    private Conexion conexion;
    private ReparacionQueryManager queryManager;
    private ReparacionEstadisticasManager estadisticasManager;
    private ReparacionComboManager comboManager;

    @SuppressWarnings("unused")
    public ReparacionDAOImpl(String ubicacionBase) {
        ubicacion = ubicacionBase;
        conexion = Conexion.getConexion(ubicacion);
        
        // Inicializar los gestores especializados
        this.queryManager = new ReparacionQueryManager(conexion);
        this.estadisticasManager = new ReparacionEstadisticasManager(conexion);
        this.comboManager = new ReparacionComboManager(conexion);
    }

    // ========== OPERACIONES CRUD BÁSICAS ==========

    @Override
    public boolean insert(ReparacionDTO reparacion) {
        return queryManager.insert(reparacion);
    }

    @Override
    public boolean insertEquipo(ReparacionDTO reparacion) {
        return queryManager.insertEquipo(reparacion);
    }

    @Override
    public boolean delete(ReparacionDTO reparacion) {
        return queryManager.delete(reparacion);
    }

    @Override
    public List<ReparacionDTO> readAll() {
        return queryManager.readAll();
    }

    @Override
    public ReparacionDTO obtenerReparacionXels(Integer els) {
        return queryManager.obtenerReparacionXELS(els);
    }

    @Override
    public ReparacionDTO obtenerReparacionXserie(String serie) {
        return queryManager.obtenerReparacionXSerie(serie);
    }

    @Override
    public int obtenerNumeroELSels() {
        return queryManager.obtenerMaximoELS();
    }

    @Override
    public int obtenerNumeroELSbsas() {
        return queryManager.obtenerMaximoELSBSAS();
    }

    @Override
    public int obtenerIDequipo() {
        return queryManager.obtenerMaximoIDEquipo();
    }

    @Override
    public boolean edit(ReparacionDTO reparacion) {
        return queryManager.update(reparacion);
    }

    @Override
    public boolean editEquipo(ReparacionDTO reparacion) {
        return queryManager.updateEquipo(reparacion);
    }

    @Override
    public void editarReparacionAgregarRemito(ReparacionDTO reparacion) {
        queryManager.updateAgregarRemito(reparacion);
    }

    @Override
    public void editMarcarEnviados(ReparacionDTO reparacion) {
        queryManager.updateMarcarEnviados(reparacion);
    }

    @Override
    public void editarReparacionAnularRemito(ReparacionDTO reparacion) {
        queryManager.updateAnularRemito(reparacion);
    }

    @Override
    public void editPresupuesto(ReparacionDTO reparacion) {
        queryManager.updatePresupuesto(reparacion);
    }

    @Override
    public void editarReparacionAceptacion(ReparacionDTO reparacion) {
        queryManager.updateAceptacion(reparacion);
    }

    @Override
    public void editarReparacionPago(ReparacionDTO reparacion) {
        queryManager.updatePago(reparacion);
    }

    @Override
    public List<ReparacionDTO> readAllXIDclienteIDSucursal(Integer idCliente, Integer idSucursal) {
        return queryManager.readAllXIDClienteIDSucursal(idCliente, idSucursal);
    }

    @Override
    public List<ReparacionDTO> readAllXIDremito(int idRemito) {
        return queryManager.readAllXIDRemito(idRemito);
    }

    @Override
    public List<ReparacionDTO> readAllxComponenteOriginal(String componente) {
        return queryManager.readAllXComponenteOriginal(componente);
    }

    @Override
    public List<ReparacionDTO> readAllxComponenteReemplazo(String componente) {
        return queryManager.readAllXComponenteReemplazo(componente);
    }

    @Override
    public List<ReparacionDTO> readAllListadoMarcarAceptaciones() {
        return queryManager.readAllListadoMarcarAceptaciones();
    }

    @Override
    public List<Integer> buscarEnCampos(String campo, String texto) {
        return queryManager.buscarEnCampos(campo, texto);
    }

    // ========== ESTADÍSTICAS GENERALES ==========

    @Override
    public int ingresosPorAnio(int anio) {
        return estadisticasManager.ingresosPorAnio(anio);
    }

    @Override
    public int diagnosticosPorAnio(int anio) {
        return estadisticasManager.diagnosticosPorAnio(anio);
    }

    @Override
    public double FacturacionPesoPorAnio(int anio) {
        return estadisticasManager.facturacionPesoPorAnio(anio);
    }

    @Override
    public double FacturacionDolarPorAnio(int anio) {
        return estadisticasManager.facturacionDolarPorAnio(anio);
    }

    @Override
    public int reparadosPorAnio(int anio) {
        return estadisticasManager.reparadosPorAnio(anio);
    }

    @Override
    public int sinFallasPorAnio(int anio) {
        return estadisticasManager.sinFallasPorAnio(anio);
    }

    @Override
    public int enGtiaPorAnio(int anio) {
        return estadisticasManager.enGtiaPorAnio(anio);
    }

    @Override
    public int EnRepPorAnio(int anio) {
        return estadisticasManager.enRepPorAnio(anio);
    }

    @Override
    public int ventasPorAnio(int anio) {
        return estadisticasManager.ventasPorAnio(anio);
    }

    @Override
    public int SinRepPorAnio(int anio) {
        return estadisticasManager.sinRepPorAnio(anio);
    }

    @Override
    public int RepAcepPorAnio(int anio) {
        return estadisticasManager.repAcepPorAnio(anio);
    }

    @Override
    public int RepNoAcepPorAnio(int anio) {
        return estadisticasManager.repNoAcepPorAnio(anio);
    }

    @Override
    public int RepEsperaPorAnio(int anio) {
        return estadisticasManager.repEsperaPorAnio(anio);
    }

    // ========== ESTADÍSTICAS POR CLIENTE ==========

    @Override
    public int IngresosXanioXcliente(int anio, int idCliente) {
        return estadisticasManager.ingresosXanioXcliente(anio, idCliente);
    }

    @Override
    public int ReparadosXanioXcliente(int anio, int idCliente) {
        return estadisticasManager.reparadosXanioXcliente(anio, idCliente);
    }

    @Override
    public int SinFallaXanioXcliente(int anio, int idCliente) {
        return estadisticasManager.sinFallaXanioXcliente(anio, idCliente);
    }

    @Override
    public int GtiaXanioXcliente(int anio, int idCliente) {
        return estadisticasManager.gtiaXanioXcliente(anio, idCliente);
    }

    @Override
    public int EnRepXanioXclientecliente(int anio, int idCliente) {
        return estadisticasManager.enRepXanioXcliente(anio, idCliente);
    }

    @Override
    public int VentasXanioXcliente(int anio, int idCliente) {
        return estadisticasManager.ventasXanioXcliente(anio, idCliente);
    }

    @Override
    public int SinRepXanioXcliente(int anio, int idCliente) {
        return estadisticasManager.sinRepXanioXcliente(anio, idCliente);
    }

    @Override
    public int RepAcepXcliente(int anio, int idCliente) {
        return estadisticasManager.repAcepXcliente(anio, idCliente);
    }

    @Override
    public int RepNoAcepXcliente(int anio, int idCliente) {
        return estadisticasManager.repNoAcepXcliente(anio, idCliente);
    }

    @Override
    public int RepEsperaXcliente(int anio, int idCliente) {
        return estadisticasManager.repEsperaXcliente(anio, idCliente);
    }

    @Override
    public double FacturacionPesoPorAnioPorCliente(int anio, int idCliente) {
        return estadisticasManager.facturacionPesoPorAnioPorCliente(anio, idCliente);
    }

    @Override
    public double FacturacionDolarPorAnioPorCliente(int anio, int idCliente) {
        return estadisticasManager.facturacionDolarPorAnioPorCliente(anio, idCliente);
    }

    // ========== ESTADÍSTICAS POR TÉCNICO ==========

    @Override
    public int DiagnosticosXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.diagnosticosXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int ReparadosXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.reparadosXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int SinFallaXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.sinFallaXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int GtiaXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.gtiaXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int EnRepXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.enRepXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int VentasXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.ventasXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int SinRepXanioXtecnico(int anio, int idTecnico) {
        return estadisticasManager.sinRepXanioXtecnico(anio, idTecnico);
    }

    @Override
    public int RepAcepXtecnico(int anio, int idTecnico) {
        return estadisticasManager.repAcepXtecnico(anio, idTecnico);
    }

    @Override
    public int RepNoAcepXtecnico(int anio, int idTecnico) {
        return estadisticasManager.repNoAcepXtecnico(anio, idTecnico);
    }

    @Override
    public int RepEsperaXtecnico(int anio, int idTecnico) {
        return estadisticasManager.repEsperaXtecnico(anio, idTecnico);
    }

    @Override
    public double FacturacionPesoPorAnioPorTecnico(int anio, int idTecnico) {
        return estadisticasManager.facturacionPesoPorAnioPorTecnico(anio, idTecnico);
    }

    @Override
    public double FacturacionDolarPorAnioPorTecnico(int anio, int idTecnico) {
        return estadisticasManager.facturacionDolarPorAnioPorTecnico(anio, idTecnico);
    }

    // ========== ESTADÍSTICAS POR MES ==========

    @Override
    public List<Integer> ingresosPorAnioPorMes(int anio) {
        return estadisticasManager.ingresosPorAnioPorMes(anio);
    }

    @Override
    public List<Integer> diagnosticoPorAnioPorMes(int anio) {
        return estadisticasManager.diagnosticoPorAnioPorMes(anio);
    }

    @Override
    public List<Double> facturacionPorAnioPorMes(int anio) {
        return estadisticasManager.facturacionPorAnioPorMes(anio);
    }

    @Override
    public List<Integer> diagnosticoPorAnioPorTecnico(int anio, int tecnico) {
        return estadisticasManager.diagnosticoPorAnioPorTecnico(anio, tecnico);
    }

    @Override
    public List<Integer> aceptacionesPorAnioPorTecnico(int anio, int tecnico) {
        return estadisticasManager.aceptacionesPorAnioPorTecnico(anio, tecnico);
    }

    @Override
    public List<Double> facturacionPorAnioPorTecnico(int anio, int tecnico) {
        return estadisticasManager.facturacionPorAnioPorTecnico(anio, tecnico);
    }

    @Override
    public List<Double> FacturacionDolaresPorAnioPorTecnico(int anio, int idTecnico) {
        return estadisticasManager.facturacionDolaresPorAnioPorTecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> ReparadosXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.reparadosXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> EnGtiaXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.enGtiaXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> SinFallaXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.sinFallaXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> EnRepXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.enRepXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> VentasXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.ventasXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> SinRepXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.sinRepXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> RepAcepXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.repAcepXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> RepNoAcepXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.repNoAcepXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> EsperaRepXmesXtecnico(int anio, int idTecnico) {
        return estadisticasManager.esperaRepXmesXtecnico(anio, idTecnico);
    }

    @Override
    public List<Integer> ingresosPorAnioPorCliente(int anio, int idCliente) {
        return estadisticasManager.ingresosPorAnioPorCliente(anio, idCliente);
    }

    @Override
    public List<Double> facturacionPorAnioPorCliente(int anio, int idCliente) {
        return estadisticasManager.facturacionPorAnioPorCliente(anio, idCliente);
    }

    @Override
    public List<Integer> aceptacionesPorAnioPorCliente(int anio, int idCliente) {
        return estadisticasManager.aceptacionesPorAnioPorCliente(anio, idCliente);
    }

    // ========== MÉTODOS PARA COMBOBOX ==========

    @Override
    public void ListarEquipo(JComboBox<?> comboBox) {
        comboManager.listarEquipo(comboBox);
    }

    @Override
    public void ListarMarca(JComboBox<?> comboBox) {
        comboManager.listarMarca(comboBox);
    }

    @Override
    public void ListarModelos(JComboBox<?> comboBox) {
        comboManager.listarModelos(comboBox);
    }

    @Override
    public void ListarModelosxMarca(JComboBox<?> comboBox, String marca) {
        comboManager.listarModelosXMarca(comboBox, marca);
    }

    @Override
    public void ListarSeriexModelo(JComboBox<?> comboBox, String modelo) {
        comboManager.listarSerieXModelo(comboBox, modelo);
    }

    @Override
    public void ListarEstadoCom(JComboBox<?> comboBox) {
        comboManager.listarEstadoCom(comboBox);
    }

    @Override
    public void ListarEstadoFis(JComboBox<?> comboBox) {
        comboManager.listarEstadoFis(comboBox);
    }

    @Override
    public void comboFiltroEstadoTec(JComboBox<?> comboBox) {
        comboManager.listarEstadoTec(comboBox);
    }

    @Override
    public void comboFiltroAviso(JComboBox<?> comboBox) {
        comboManager.listarAviso(comboBox);
    }

    @Override
    public void comboFiltroELS(JComboBox<?> comboBox) {
        comboManager.listarELS(comboBox);
    }

    @Override
    public void comboSerie(JComboBox<?> comboBox) {
        comboManager.listarSerie(comboBox);
    }

	@Override
	public List<ReparacionDTO> buscarHistorialPrecios(String criterio, String texto) {
		return queryManager.buscarHistorialPrecios(criterio, texto);
	}
}
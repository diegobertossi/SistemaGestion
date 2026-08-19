package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import org.apache.logging.log4j.util.Strings;

import dto.ReparacionDTO;
import persistencia.dao.mysql.ReparacionEstadisticasManager;



public interface ReparacionDAO 
{
	
	public boolean insert(ReparacionDTO nuevaReparacion);
			
	public boolean insertEquipo(ReparacionDTO nuevaReparacion);

	public boolean edit(ReparacionDTO reparacion_a_editar);
	
	public void editMarcarEnviados(ReparacionDTO reparacionAeditar);
	
	public void editarReparacionAnularRemito(ReparacionDTO reparacionAeditar);

	public void editarReparacionAgregarRemito(ReparacionDTO reparacionAeditar);

	public void editPresupuesto(ReparacionDTO reparacionAeditar);
	
	public void editarReparacionAceptacion(ReparacionDTO reparacionAeditar);
	
	public void editarReparacionPago(ReparacionDTO reparacionAeditar);
	
	public boolean editEquipo(ReparacionDTO reparacion_a_editar);
		
	public boolean delete(ReparacionDTO Reparacion_a_eliminar);
	
	public List<ReparacionDTO> readAll();
	
	public ReparacionDTO obtenerReparacionXels(Integer i);
		
	public int obtenerNumeroELSels();
	
	public int obtenerIDequipo();

	public void ListarEquipo(JComboBox<?> box);

	public void ListarMarca(JComboBox<?> comboMarca);

	public void ListarModelosxMarca(JComboBox<?> comboModelos, String marca);

	public void ListarSeriexModelo(JComboBox<?> comboSerie, String modelo);

	public void ListarModelos(JComboBox<?> box);

	public void ListarEstadoCom(JComboBox<?> comboFiltroEstadoCom);

	public void ListarEstadoFis(JComboBox<?> comboFiltroEstadoFis);

	public void comboFiltroEstadoTec(JComboBox<?> comboFiltroEstadoTec);

	public void comboFiltroAviso(JComboBox<?> comboFiltroAviso);

	public void comboFiltroELS(JComboBox<?> comboFiltroELS);

	public List<ReparacionDTO> readAllXIDclienteIDSucursal(Integer IDCliente, Integer IDSucursal);
	
	public List<ReparacionDTO> readAllXIDclienteIDSucursalResumido(Integer IDCliente, Integer IDSucursal);

	public void comboSerie(JComboBox<?> comboSerie);

	public ReparacionDTO obtenerReparacionXserie(String serie);

	public List<ReparacionDTO> readAllXIDremito(int iDremito);

	public List<ReparacionDTO> readAllxComponenteOriginal(String componente);

	public List<ReparacionDTO> readAllxComponenteReemplazo(String componente);

	public List<ReparacionDTO> readAllListadoMarcarAceptaciones();

	public int ingresosPorAnio(int anio);

	public ReparacionEstadisticasManager.TotalesPorAnio obtenerTotalesPorAnio(int anio);

	public int diagnosticosPorAnio(int anio);
	
	public int reparadosPorAnio(int anio);
	
	public int sinFallasPorAnio(int anio);
	
	public int enGtiaPorAnio(int anio);
	
	public int EnRepPorAnio(int anio);
	
	public int ventasPorAnio(int anio);
	
	public int SinRepPorAnio(int anio);
	
	public int IngresosXanioXcliente(int anio, int idCliente);

	public int ReparadosXanioXcliente(int anio, int idCliente);

	public int SinFallaXanioXcliente(int anio, int idCliente);

	public int GtiaXanioXcliente(int anio, int idCliente);

	public int EnRepXanioXclientecliente(int anio, int idCliente);

	public int VentasXanioXcliente(int anio, int idCliente);

	public int SinRepXanioXcliente(int anio, int idCliente);

	public int RepAcepXcliente(int anio, int idCliente);

	public int RepEsperaXcliente(int anio, int idCliente);
	
	public int RepNoAcepXcliente(int anio, int idCliente);

	public int RepAcepPorAnio(int anio);

	public int RepNoAcepPorAnio(int anio);

	public int RepEsperaPorAnio(int anio);

	public double FacturacionPesoPorAnio(int anio);

	public double FacturacionDolarPorAnio(int anio);

	public double FacturacionPesoPorAnioPorCliente(int anio, int idCliente);

	public double FacturacionDolarPorAnioPorCliente(int anio, int idCliente);
	
	public int DiagnosticosXanioXtecnico(int anio, int idTecnico);

	public int ReparadosXanioXtecnico(int anio, int idTecnico);

	public int SinFallaXanioXtecnico(int anio, int idTecnico);

	public int GtiaXanioXtecnico(int anio, int idTecnico);

	public int EnRepXanioXtecnico(int anio, int idTecnico);

	public int VentasXanioXtecnico(int anio, int idTecnico);

	public int SinRepXanioXtecnico(int anio, int idTecnico);

	public int RepAcepXtecnico(int anio, int idTecnico);

	public int RepNoAcepXtecnico(int anio, int idTecnico);

	public int RepEsperaXtecnico(int anio, int idTecnico);

	public double FacturacionPesoPorAnioPorTecnico(int anio, int idTecnico);

	public double FacturacionDolarPorAnioPorTecnico(int anio, int idTecnico);

	
	public List<Integer> ingresosPorAnioPorMes(int anio);
	
	public List<Integer> diagnosticoPorAnioPorMes(int anio);
	
	public List<Double> facturacionPorAnioPorMes(int anio);

	public List<Integer> diagnosticoPorAnioPorTecnico(int anio, int idTecnico);

	public List<Integer> aceptacionesPorAnioPorTecnico(int anio, int idTecnico);

	public List<Double> facturacionPorAnioPorTecnico(int anio, int idtecnico);

	public List<Integer> ingresosPorAnioPorCliente(int anio, int idCliente);

	public List<Double> facturacionPorAnioPorCliente(int anio, int idCliente);

	public List<Integer> aceptacionesPorAnioPorCliente(int anio, int idCliente);

	public List<Integer> ReparadosXmesXtecnico(int anio, int idTecnico);

	public List<Integer> EnGtiaXmesXtecnico(int anio, int idTecnico);

	public List<Integer> SinFallaXmesXtecnico(int anio, int idTecnico);

	public List<Integer> EnRepXmesXtecnico(int anio, int idTecnico);

	public List<Integer> VentasXmesXtecnico(int anio, int idTecnico);

	public List<Integer> SinRepXmesXtecnico(int anio, int idTecnico);

	public List<Integer> RepAcepXmesXtecnico(int anio, int idTecnico);

	public List<Integer> RepNoAcepXmesXtecnico(int anio, int idTecnico);

	public List<Integer> EsperaRepXmesXtecnico(int anio, int idTecnico);

	public List<Double> FacturacionDolaresPorAnioPorTecnico(int anio, int idTecnico);

	public int obtenerNumeroELSbsas();

	public List<Integer> buscarEnCampos(String campo, String texto);

	public List<ReparacionDTO> buscarHistorialPrecios(String criterio, String texto);

	// NUEVO: paginación server-side
	List<ReparacionDTO> readAllPaginado(int limit, int offset);

	// NUEVO: conteo total para calcular páginas
	int contarReparaciones();


	

}

package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import dto.ClienteDTO;
import dto.ClienteWSPDTO;
import dto.FacturacionXclienteDTO;
import dto.RemitoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.RolDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.ClienteWSPDAO;
import persistencia.dao.interfaz.FacturacionXclienteDAO;
import persistencia.dao.interfaz.RemitoDAO;
import persistencia.dao.interfaz.ReparacionDAO;
import persistencia.dao.interfaz.RepuestoDAO;
import persistencia.dao.interfaz.RolDAO;
import persistencia.dao.interfaz.SucursalDAO;
import persistencia.dao.interfaz.UsuarioDAO;
import persistencia.dao.mysql.ClienteDAOImpl;
import persistencia.dao.mysql.ClienteWSPDAOImpl;
import persistencia.dao.mysql.FacturacionXclienteDAOImp;
import persistencia.dao.mysql.RemitoDAOImpl;
import persistencia.dao.mysql.ReparacionDAOImpl;
import persistencia.dao.mysql.RepuestosDAOImpl;
import persistencia.dao.mysql.RolDAOImpl;
import persistencia.dao.mysql.SucursalDAOImpl;
import persistencia.dao.mysql.UsuarioDAOImpl;

public class Agenda {
	
	private ClienteWSPDAO ClienteWSP;
	private ClienteDAO Cliente;
	private ReparacionDAO ReparacionR;
	private RepuestoDAO Repuestos;
	private SucursalDAO Sucursal;
	private UsuarioDAO usuario;
	private RolDAO rol;
	private RemitoDAO remito;	
	private FacturacionXclienteDAO facturacionXcliente;
	private String ubicacionBase;
	private boolean esBaseAntigua;   // ← NUEVO: controla si usa base antigua

	/**
	 * Constructor original (mantenido por compatibilidad)
	 */
	public Agenda(String ubicacionDeBase) { 
		this(ubicacionDeBase, Conexion.isModoAntigua());   // ← Usa el modo global actual
	}

	/**
	 * Nuevo constructor que permite especificar si es base antigua
	 */
	public Agenda(String ubicacionDeBase, boolean esAntigua) { 
		
		this.ubicacionBase = ubicacionDeBase;
		this.esBaseAntigua = esAntigua;

		// Se fuerza la conexión con el modo correcto antes de instanciar los DAO
		Conexion.getConexion(ubicacionDeBase, esAntigua);

		Cliente = new ClienteDAOImpl(ubicacionDeBase);
		ReparacionR = new ReparacionDAOImpl(ubicacionDeBase);
		Repuestos = new RepuestosDAOImpl(ubicacionDeBase);
		Sucursal = new SucursalDAOImpl(ubicacionDeBase);
		usuario = new UsuarioDAOImpl(ubicacionDeBase);
		rol = new RolDAOImpl(ubicacionDeBase);
		remito = new RemitoDAOImpl(ubicacionDeBase);
		ClienteWSP = new ClienteWSPDAOImpl(ubicacionDeBase);
		facturacionXcliente = new FacturacionXclienteDAOImp(ubicacionDeBase); 
		
		System.out.println("Agenda creada para ubicación: " + ubicacionDeBase + 
				(esAntigua ? " → BASE ANTIGUA" : " → BASE NORMAL"));
	}

	// USUARIOS
	public void agregarUsuario(UsuarioDTO nuevoUsuario) {
		usuario.insert(nuevoUsuario);
	}

	public void borrarUsuario(UsuarioDTO usuario_a_eliminar) {
		usuario.delete(usuario_a_eliminar);
	}

	public void editarUsuario(UsuarioDTO usuario_a_editar) {
		usuario.edit(usuario_a_editar);
	}

	public List<UsuarioDTO> obtenerUsuarios() {
		return usuario.readAll();
	}

	public List<UsuarioDTO> obtenerUsuariosXrol(int idRol) {
		return usuario.readAllXRol(idRol);
	}

	@SuppressWarnings("rawtypes")
	public void ListarTecnicos(JComboBox comboFiltroTecnico) {
		usuario.comboFiltroTecnicos(comboFiltroTecnico);
	}
	
	@SuppressWarnings("rawtypes")
	public void ListarTecnicosV(JComboBox<?> comboTecnico) {
		usuario.comboFiltroTecnicosV(comboTecnico);
	}
	
	public String obtenerCorreoPorNombre(String nombreCompleto) {
	    return usuario.correoPorNombre(nombreCompleto);
	}
	
	public int idUsuarioporNombre(String nombreTecnico) {
		return usuario.obtenerIDporNombre(nombreTecnico);
	}

	// ROLES
	public void agregarRol(RolDTO nuevorol) {
		rol.insert(nuevorol);
	}

	public void borrarRol(RolDTO rol_a_eliminar) {
		rol.delete(rol_a_eliminar);
	}

	public void editarRol(RolDTO rol_a_editar) {
		rol.edit(rol_a_editar);
	}

	public List<RolDTO> obtenerRoles() {
		return rol.readAll();
	}

	public String obtenerRolXid(int id) {
		return rol.readAllxid(id);
	}

	// CLIENTES
	public void agregarClientes(ClienteDTO nuevoCliente) {
		Cliente.insert(nuevoCliente);
	}

	public void editarClientes(ClienteDTO Clienteeditado) {
		Cliente.edit(Clienteeditado);
	}

	public void borrarCliente(ClienteDTO Cliente_a_eliminar) {
		Cliente.delete(Cliente_a_eliminar);
	}

	public List<ClienteDTO> obtenerCliente() {
		return Cliente.readAll();
	}

	public void ListarCliente(JComboBox<?> box) {
		Cliente.ListarClientes(box);
	}

	public boolean reparacionAsociadaCliente(int idCliente) {
		return Cliente.obtenerReparacionxIDCliente(idCliente);
	}

	public String dameCuitPorIdCliente(int idCliente) {
		return Cliente.dameCuitPorIdCliente(idCliente);
	}

	public int dameIDcliente() {
		return Cliente.obtenerIDcliente();
	}

	public int idClienteporNombre(String nombreCliente) {
		return Cliente.obtenerIDporNombre(nombreCliente);
	}

	public ClienteDTO obtenerClientePorRazonSocial(String nombreCliente) {
		return Cliente.obtenerPorRazonSocial(nombreCliente);
	}

	public String ContactoPorCliente(String nombreCliente) {
		return Cliente.obtenerContactoPorCliente(nombreCliente);
	}

	public String obtenerTelefonoPorCliente(String OrgCliente) {
		return Cliente.obtenerTelefonoPorCliente(OrgCliente);
	}

	public String EmailPorCliente(String nombreCliente) {
		return Cliente.obtenerEmailPorCliente(nombreCliente);
	}

	public String dameUbucacionBase() {
		return ubicacionBase;
	}

	// SUCURSALES
	public void agregarSucursal(SucursalDTO nuevaSucursal) {
		Sucursal.insert(nuevaSucursal);
	}

	public void borrarSucursal(SucursalDTO Sucursal_a_eliminar) {
		Sucursal.delete(Sucursal_a_eliminar);
	}

	public List<SucursalDTO> obtenerSucursales() {
		return Sucursal.readAll();
	}

	public List<SucursalDTO> obtenerSucursalesxCliente(int idCliente) {
		return Sucursal.obtenerSucursalXidCliente(idCliente);
	}

	public void ListarSucursalesxCliente(JComboBox<?> box, int id) {
		Sucursal.ListarSucursalesxCliente(box, id);
	}

	public void ListarSucursales(JComboBox<?> box) {
		Sucursal.ListarSucursales(box);
	}

	public int dameIDsucursal() {
		return Sucursal.obtenerIDsucursal();
	}

	public int cantSucursalesXCliente(int idcliente) {
		return Sucursal.obtenercantidaddeSucursales(idcliente);
	}

	public void editarSucursal(SucursalDTO Sucursalaeditar) {
		Sucursal.edit(Sucursalaeditar);
	}

	public boolean reparacionAsociada(int idsucursal) {
		return Sucursal.obtenerReparacionxIDsSuc(idsucursal);
	}

	public int idSucursalporNombre(String nombreSucursal, int IDCliente) {
		return Sucursal.obtenerIDporNombre(nombreSucursal, IDCliente);
	}

	public void ListarOrganizacionWSP(JComboBox<?> comboOrganizacion) {
		ClienteWSP.ListarClientesWSP(comboOrganizacion);
	}

	public void ListarContactoxOrganizacion(JComboBox<?> comboNombreBuscado, String organizacionWSP) {
		ClienteWSP.ListarContactoxOrganizacion(comboNombreBuscado, organizacionWSP);
	}

	public String obtenerTelefonoxContacto(String contactoWSP) {
		return ClienteWSP.obtenetTelefonoXcontacto(contactoWSP);
	}

	public List<ClienteWSPDTO> obtenerClientesWSP() {
		return ClienteWSP.readAll();
	}

	public void agregarClienteWSP(ClienteWSPDTO nuevoClienteWSPDTO) {
		ClienteWSP.insert(nuevoClienteWSPDTO);
	}

	public void borrarClienteWSP(ClienteWSPDTO clienteWSP_a_eliminar) {
		ClienteWSP.delete(clienteWSP_a_eliminar);
	}

	public void editarClienteWSP(ClienteWSPDTO clienteWSP_a_editar) {
		ClienteWSP.edit(clienteWSP_a_editar);
	}

	// EQUIPOS
	public void ListarEquipo(JComboBox<?> box) {
		ReparacionR.ListarEquipo(box);
	}

	public void ListarModelos(JComboBox<?> box) {
		ReparacionR.ListarModelos(box);
	}

	public void ListarMarca(JComboBox<?> comboMarca) {
		ReparacionR.ListarMarca(comboMarca);
	}

	public void ListarModelosxMarca(JComboBox<?> comboModelos, String marca) {
		ReparacionR.ListarModelosxMarca(comboModelos, marca);
	}

	public void ListarSeriexModelo(JComboBox<?> comboSerie, String modelo) {
		ReparacionR.ListarSeriexModelo(comboSerie, modelo);
	}

	public void ListarSerie(JComboBox<?> comboSerie) {
		ReparacionR.comboSerie(comboSerie);
	}

	// REPARACIONES
	public void agregarReparacionR(ReparacionDTO nuevaReparacion) {
		ReparacionR.insertEquipo(nuevaReparacion);
		ReparacionR.insert(nuevaReparacion);
	}

	public void editarReparacionR(ReparacionDTO Reparacion_a_editar) {
		ReparacionR.edit(Reparacion_a_editar);
		ReparacionR.editEquipo(Reparacion_a_editar);
	}

	public void editarReparacionMarcarEnviados(ReparacionDTO reparacionAeditar) {
		ReparacionR.editMarcarEnviados(reparacionAeditar);
	}

	public void editarReparacionAnularRemito(ReparacionDTO reparacionAeditar) {
		ReparacionR.editarReparacionAnularRemito(reparacionAeditar);
	}

	public void editarReparacionAgregarRemito(ReparacionDTO reparacionAeditar) {
		ReparacionR.editarReparacionAgregarRemito(reparacionAeditar);
	}

	public void editarReparacionAceptacion(ReparacionDTO reparacionAeditar) {
		ReparacionR.editarReparacionAceptacion(reparacionAeditar);
	}

	public void editarReparacionPresupuesto(ReparacionDTO reparacionAeditar) {
		ReparacionR.editPresupuesto(reparacionAeditar);
	}

	public void editarReparacionPago(ReparacionDTO reparacionAeditar) {
		ReparacionR.editarReparacionPago(reparacionAeditar);
	}

	public void borraReparacion(ReparacionDTO Reparacion_a_eliminar) {
		ReparacionR.delete(Reparacion_a_eliminar);
	}

	public List<ReparacionDTO> obtenerReparacion() {
		return ReparacionR.readAll();
	}

	public List<ReparacionDTO> obtenerReparacionParaListadoMarcarAceptaciones() {
		return ReparacionR.readAllListadoMarcarAceptaciones();
	}

	public ReparacionDTO dameReparacionXels(int i) {
		return ReparacionR.obtenerReparacionXels(i);
	}

	public ReparacionDTO dameReparacionXserie(String i) {
		return ReparacionR.obtenerReparacionXserie(i);
	}

	public int dameNumeroELS() {
		return ReparacionR.obtenerNumeroELSels();
	}

	public int dameNumeroELSbsas() {
		return ReparacionR.obtenerNumeroELSbsas();
	}

	public int dameIDequipo() {
		return ReparacionR.obtenerIDequipo();
	}

	public List<ReparacionDTO> obtenerReparacionXIDclienteIDsucursal(Integer IDcliente, Integer IDsucursal) {
		return ReparacionR.readAllXIDclienteIDSucursal(IDcliente, IDsucursal);
	}

	public List<ReparacionDTO> obtenerReparacionResumenXIDclienteIDsucursal(Integer IDcliente, Integer IDsucursal) {
		return ReparacionR.readAllXIDclienteIDSucursalResumido(IDcliente, IDsucursal);
	}

	public List<ReparacionDTO> obtenerReparacionesXremito(int iDremito) {
		return ReparacionR.readAllXIDremito(iDremito);
	}

	public List<ReparacionDTO> obtenerReparacionPorCompOriginal(String componente) {
		return ReparacionR.readAllxComponenteOriginal(componente);
	}

	public List<ReparacionDTO> obtenerReparacionPorCompReemplazo(String componente) {
		return ReparacionR.readAllxComponenteReemplazo(componente);
	}

	public int dameIngresosPorAnio(int anio) {
		return ReparacionR.ingresosPorAnio(anio);
	}

	/**
	 * Todos los totales por año en una única operación consolidada
	 */
	public persistencia.dao.mysql.ReparacionEstadisticasManager.TotalesPorAnio obtenerTotalesPorAnio(int anio) {
		return ReparacionR.obtenerTotalesPorAnio(anio);
	}

	public int dameDiagnosticosPorAnio(int anio) {
		return ReparacionR.diagnosticosPorAnio(anio);
	}

	public int dameReparadosPorAnio(int anio) {
		return ReparacionR.reparadosPorAnio(anio);
	}

	public int dameSinFallaPorAnio(int anio) {
		return ReparacionR.sinFallasPorAnio(anio);
	}

	public int dameRepEnGtiaPorAnio(int anio) {
		return ReparacionR.enGtiaPorAnio(anio);
	}

	public int dameEnRepPorAnio(int anio) {
		return ReparacionR.EnRepPorAnio(anio);
	}

	public int dameVentasPorAnio(int anio) {
		return ReparacionR.ventasPorAnio(anio);
	}

	public int dameSinRepAnio(int anio) {
		return ReparacionR.SinRepPorAnio(anio);
	}

	public int dameRepAcepPorAnio(int anio) {
		return ReparacionR.RepAcepPorAnio(anio);
	}

	public int dameRepNoAcepPorAnio(int anio) {
		return ReparacionR.RepNoAcepPorAnio(anio);
	}

	public int dameRepEsperaPorAnio(int anio) {
		return ReparacionR.RepEsperaPorAnio(anio);
	}

	public double dameFacturacionPesoPorAnio(int anio) {
		return ReparacionR.FacturacionPesoPorAnio(anio);
	}

	public double dameFacturacionDolarPorAnio(int anio) {
		return ReparacionR.FacturacionDolarPorAnio(anio);
	}

	public int dameTotalIngresosXanioXcliente(int anio, int idCliente) {
		return ReparacionR.IngresosXanioXcliente(anio, idCliente);
	}

	public int dameTotalReparadosXanioXcliente(int anio, int idCliente) {
		return ReparacionR.ReparadosXanioXcliente(anio, idCliente);
	}

	public int dameTotalSinFallaXanioXcliente(int anio, int idCliente) {
		return ReparacionR.SinFallaXanioXcliente(anio, idCliente);
	}

	public int dameTotalGtiaXanioXcliente(int anio, int idCliente) {
		return ReparacionR.GtiaXanioXcliente(anio, idCliente);
	}

	public int dameTotalEnRepXanioXclientecliente(int anio, int idCliente) {
		return ReparacionR.EnRepXanioXclientecliente(anio, idCliente);
	}

	public int dameTotalVentasXanioXcliente(int anio, int idCliente) {
		return ReparacionR.VentasXanioXcliente(anio, idCliente);
	}

	public int dameTotalSinRepXanioXcliente(int anio, int idCliente) {
		return ReparacionR.SinRepXanioXcliente(anio, idCliente);
	}

	public int dameTotalRepAcepXcliente(int anio, int idCliente) {
		return ReparacionR.RepAcepXcliente(anio, idCliente);
	}

	public int dameTotalRepNoAcepXcliente(int anio, int idCliente) {
		return ReparacionR.RepNoAcepXcliente(anio, idCliente);
	}

	public int dameTotalRepEsperaXcliente(int anio, int idCliente) {
		return ReparacionR.RepEsperaXcliente(anio, idCliente);
	}

	public List<Integer> dameIngresosPorAnioPorMes(int anio) {
		return ReparacionR.ingresosPorAnioPorMes(anio);
	}

	public List<Integer> dameDiagnosticosPorAnioPorMes(int anio) {
		return ReparacionR.diagnosticoPorAnioPorMes(anio);
	}

	public List<Double> dameFacturacionPorAnioPorMes(int anio) {
		return ReparacionR.facturacionPorAnioPorMes(anio);
	}

	public List<Integer> dameDiagnosticosPorAnioPorTecnico(int anio, int idTecnico) {
		return ReparacionR.diagnosticoPorAnioPorTecnico(anio, idTecnico);
	}

	public List<Integer> dameAceptacionesPorAnioPorTecnico(int anio, int idTecnico) {
		return ReparacionR.aceptacionesPorAnioPorTecnico(anio, idTecnico);
	}

	public List<Double> dameFacturacionPorAnioPorTecnico(int anio, int idtecnico) {
		return ReparacionR.facturacionPorAnioPorTecnico(anio, idtecnico);
	}

	public List<Integer> dameIngresosPorAnioPorCliente(int anio, int idCliente) {
		return ReparacionR.ingresosPorAnioPorCliente(anio, idCliente);
	}

	public List<Double> dameFacturacionPorAnioPorCliente(int anio, int idCliente) {
		return ReparacionR.facturacionPorAnioPorCliente(anio, idCliente);
	}

	public List<Integer> dameAceptacionesPorAnioPorCliente(int anio, int idCliente) {
		return ReparacionR.aceptacionesPorAnioPorCliente(anio, idCliente);
	}

	public List<Integer> dameReparadosXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.ReparadosXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameRepEnGtiaXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.EnGtiaXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameSinFallaXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.SinFallaXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameEnRepXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.EnRepXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameVentasXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.VentasXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameSinRepXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.SinRepXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameRepAcepXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.RepAcepXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameRepNoAcepXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.RepNoAcepXmesXtecnico(anio, idTecnico);
	}

	public List<Integer> dameEsperaXmesXtecnico(int anio, int idTecnico) {
		return ReparacionR.EsperaRepXmesXtecnico(anio, idTecnico);
	}

	public List<Double> dameFacturacionDolaresPorAnioPorTecnico(int anio, int idTecnico) {
		return ReparacionR.FacturacionDolaresPorAnioPorTecnico(anio, idTecnico);
	}

	public double dameFacturacionPesoPorAnioPorCliente(int anio, int idCliente) {
		return ReparacionR.FacturacionPesoPorAnioPorCliente(anio, idCliente);
	}

	public double dameFacturacionDolarPorAnioPorCliente(int anio, int idCliente) {
		return ReparacionR.FacturacionDolarPorAnioPorCliente(anio, idCliente);
	}

	public int dameTotalDiagnosticosXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.DiagnosticosXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalReparadosXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.ReparadosXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalSinFallaXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.SinFallaXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalGtiaXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.GtiaXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalEnRepXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.EnRepXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalVentasXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.VentasXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalSinRepXanioXtecnico(int anio, int idTecnico) {
		return ReparacionR.SinRepXanioXtecnico(anio, idTecnico);
	}

	public int dameTotalRepAcepXtecnico(int anio, int idTecnico) {
		return ReparacionR.RepAcepXtecnico(anio, idTecnico);
	}

	public int dameTotalRepNoAcepXtecnico(int anio, int idTecnico) {
		return ReparacionR.RepNoAcepXtecnico(anio, idTecnico);
	}

	public int dameTotalRepEsperaXtecnico(int anio, int idTecnico) {
		return ReparacionR.RepEsperaXtecnico(anio, idTecnico);
	}

	public double dameFacturacionPesoPorAnioPorTecnico(int anio, int idTecnico) {
		return ReparacionR.FacturacionPesoPorAnioPorTecnico(anio, idTecnico);
	}

	public double dameFacturacionDolarPorAnioPorTecnico(int anio, int idTecnico) {
		return ReparacionR.FacturacionDolarPorAnioPorTecnico(anio, idTecnico);
	}

	public List<Integer> buscarEnCampos(String campo, String texto) {
		return ReparacionR.buscarEnCampos(campo, texto);
	}

	// REPUESTOS
	public void agregarRepuesto(RepuestosDTO nuevoRepuesto) {
		Repuestos.insert(nuevoRepuesto);
	}

	public void borraRepuesto(RepuestosDTO Repuesto_a_eliminar) {
		Repuestos.delete(Repuesto_a_eliminar);
	}

	public void editarRepuesto(RepuestosDTO Repuesto_a_editar) {
		Repuestos.edit(Repuesto_a_editar);
	}

	public List<RepuestosDTO> obtenerRepuesto() {
		return Repuestos.readAll();
	}

	public void ListarRepuestos(JComboBox<?> box) {
		Repuestos.ListarRepuestos(box);
	}

	public void ListarRepuestosReemplazo(JComboBox<?> comboCompReemplazo) {
		Repuestos.ListarRepuestosReemplazo(comboCompReemplazo);
	}

	public List<RepuestosDTO> dameRepuestoXels(int i) {
		return Repuestos.obtenerRepuestosXels(i);
	}

	public void ListarEstadoCom(JComboBox<?> comboFiltroEstadoCom) {
		ReparacionR.ListarEstadoCom(comboFiltroEstadoCom);
	}

	public void ListarEstadoFis(JComboBox<?> comboFiltroEstadoFis) {
		ReparacionR.ListarEstadoFis(comboFiltroEstadoFis);
	}

	public void ListarEstadoTec(JComboBox<?> comboFiltroEstadoTec) {
		ReparacionR.comboFiltroEstadoTec(comboFiltroEstadoTec);
	}

	public void ListarAvisos(JComboBox<?> comboFiltroAviso) {
		ReparacionR.comboFiltroAviso(comboFiltroAviso);
	}

	public void ListarELS(JComboBox<?> comboFiltroELS) {
		ReparacionR.comboFiltroELS(comboFiltroELS);
	}

	// REMITOS
	public void agregarRemito(RemitoDTO nuevoRemito) {
		remito.insert(nuevoRemito);
	}

	public void ListarUbicacion(JComboBox<?> comboUbicacion) {
		remito.ListarUbicacion(comboUbicacion);
	}

	public void ListarRemitoPorUbicacion(JComboBox<?> box, int id) {		
		remito.ListarRemitoPorUbicacion(box, id);
	}

	public int obtenerNumeroRemito(int codigo) {
		return remito.obtenerNumeroRemito(codigo);
	}

	public int dameIDRemito() {
		return remito.obtenerIDRemito();
	}

	public int idRemitoXubicacionNumero(int iDubicacion, int numero) {
		return remito.idRemitoXubicacionNumero(iDubicacion, numero);
	}

	public void eliminarRemito(int IDRemito) {
		remito.delete(IDRemito);
	}

	public String getUbicacionBase() {
		return ubicacionBase;
	}

	public void setUbicacionBase(String ubicacionBase) {
		this.ubicacionBase = ubicacionBase;
	}

	public List<FacturacionXclienteDTO> dameFacturacionXcliente(int anio) {
		return facturacionXcliente.readAll(anio);
	}

	public List<ReparacionDTO> buscarHistorialPrecios(String criterio, String texto) {
	    return ReparacionR.buscarHistorialPrecios(criterio, texto);
	}

	// ====================== MÉTODOS NUEVOS ======================
	
	public boolean isBaseAntigua() {
		return esBaseAntigua;
	}

	public String getNombreBaseActual() {
		if (ubicacionBase.equalsIgnoreCase("Bariloche")) {
			return esBaseAntigua ? "ordenesbrcantiguas" : "ordenesbrc";
		} else if (ubicacionBase.equalsIgnoreCase("Buenos Aires")) {
			return esBaseAntigua ? "ordenesbsascantiguas" : "ordenesbsas";
		}
		return ubicacionBase + (esBaseAntigua ? "antiguas" : "");
	}
	
	
	// NUEVO: paginación del listado
	public List<ReparacionDTO> obtenerReparacionPaginada(int limit, int offset) {
	    return ReparacionR.readAllPaginado(limit, offset);
	}

	public int contarReparaciones() {
	    return ReparacionR.contarReparaciones();
	}
	
}
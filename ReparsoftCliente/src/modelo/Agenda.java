package modelo;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import dto.ClienteDTO;
import dto.ClienteWSPDTO;
import dto.RemitoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.RolDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.ClienteWSPDAO;
import persistencia.dao.interfaz.RemitoDAO;
import persistencia.dao.interfaz.ReparacionDAO;
import persistencia.dao.interfaz.RepuestoDAO;
import persistencia.dao.interfaz.RolDAO;
import persistencia.dao.interfaz.SucursalDAO;
import persistencia.dao.interfaz.UsuarioDAO;
import persistencia.dao.mysql.ClienteDAOImpl;
import persistencia.dao.mysql.ClienteWSPDAOImpl;
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

	public Agenda() {

		Cliente = new ClienteDAOImpl();
		ReparacionR = new ReparacionDAOImpl();
		Repuestos = new RepuestosDAOImpl();
		Sucursal = new SucursalDAOImpl();
		usuario = new UsuarioDAOImpl();
		rol = new RolDAOImpl();
		remito = new RemitoDAOImpl();
		ClienteWSP = new ClienteWSPDAOImpl();

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

	public void ListarTecnicos(JComboBox comboFiltroTecnico) {
		usuario.comboFiltroTecnicos(comboFiltroTecnico);

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

	
	public String  obtenerRolXid(int id) {
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

	public void ListarCliente(JComboBox box) {

		Cliente.ListarClientes(box);
	}

	
	public boolean reparacionAsociadaCliente(int idCliente) {
		
		return Cliente.obtenerReparacionxIDCliente(idCliente);
		
	}
	
	public int dameIDcliente() {
		return Cliente.obtenerIDcliente();

	}

	public int idClienteporNombre(String nombreCliente) {
		return Cliente.obtenerIDporNombre(nombreCliente);

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

	public void ListarSucursalesxCliente(JComboBox box, int id) {

		Sucursal.ListarSucursalesxCliente(box, id);
	}

	public void ListarSucursales(JComboBox box) {

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
	
	
	
	
	public void ListarOrganizacionWSP(JComboBox comboOrganizacion) {
				
		ClienteWSP.ListarClientesWSP(comboOrganizacion);
		
	}
	
	
	public void ListarContactoxOrganizacion(JComboBox comboNombreBuscado, String organizacionWSP) {
		
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
	public void ListarEquipo(JComboBox box) {

		ReparacionR.ListarEquipo(box);

	}

	public void ListarModelos(JComboBox box) {

		ReparacionR.ListarModelos(box);

	}

	public void ListarMarca(JComboBox comboMarca) {
		ReparacionR.ListarMarca(comboMarca);

	}

	public void ListarModelosxMarca(JComboBox comboModelos, String marca) {
		ReparacionR.ListarModelosxMarca(comboModelos, marca);

	}

	public void ListarSeriexModelo(JComboBox comboSerie, String modelo) {
		ReparacionR.ListarSeriexModelo(comboSerie, modelo);

	}
	
	public void ListarSerie(JComboBox comboSerie) {
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
	
	public ReparacionDTO dameReparacionXserie(String  i) {
		return ReparacionR.obtenerReparacionXserie(i);
	}


	public int dameNumeroELS() {
		return ReparacionR.obtenerNumeroELSels();
	}

	public int dameIDequipo() {
		return ReparacionR.obtenerIDequipo();

	}

	public List<ReparacionDTO> obtenerReparacionXIDclienteIDsucursal(Integer IDcliente, Integer IDsucursal) {
		return ReparacionR.readAllXIDclienteIDSucursal(IDcliente, IDsucursal);
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

	public void ListarRepuestos(JComboBox box) {

		Repuestos.ListarRepuestos(box);
	}
	
	public void ListarRepuestosReemplazo(JComboBox comboCompReemplazo) {

		Repuestos.ListarRepuestosReemplazo(comboCompReemplazo);
		
	}
	
	
	public List<RepuestosDTO> dameRepuestoXels(int i) {
		return Repuestos.obtenerRepuestosXels(i);
	}

	public void ListarEstadoCom(JComboBox comboFiltroEstadoCom) {
		ReparacionR.ListarEstadoCom(comboFiltroEstadoCom);

	}

	public void ListarEstadoFis(JComboBox comboFiltroEstadoFis) {
		ReparacionR.ListarEstadoFis(comboFiltroEstadoFis);

	}

	public void ListarEstadoTec(JComboBox comboFiltroEstadoTec) {
		ReparacionR.comboFiltroEstadoTec(comboFiltroEstadoTec);

	}

	public void ListarAvisos(JComboBox comboFiltroAviso) {
		ReparacionR.comboFiltroAviso(comboFiltroAviso);

	}

	public void ListarELS(JComboBox comboFiltroELS) {
		ReparacionR.comboFiltroELS(comboFiltroELS);

	}

	// REMITOS
	
	public void agregarRemito(RemitoDTO nuevoRemito) {
		remito.insert(nuevoRemito);
	}

	public void ListarUbicacion(JComboBox comboUbicacion) {
		remito.ListarUbicacion(comboUbicacion);

	}
	
	public void ListarRemitoPorUbicacion(JComboBox box, int id) {		
		
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


	
		
	
}

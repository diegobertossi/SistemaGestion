package dto;

import java.util.Date;

import javax.xml.crypto.NoSuchMechanismException;

public class ReparacionDTO {
	// private int Id;
	private int ELS;
	private String FechaEntrada;
	private String FechadeDiagnostico;
	private String FechAceptacion;
	private String Falla;
	private String Solucion;
	private String Informecliente;
	private int idUsuario;
	private String EstadoFisico;
	private String EstadoTecnico;
	private String EstadoComercial;
	private String RemitoCliente;
	private String OrdendeCompra;
	private Boolean Agregadoaremito;
	private Boolean RemitoGenerado;
	private int idEquipo;
	private int idRemito;
	private Double PrecioPeso;
	private Double PrecioDolar;
	private Boolean InformeEnviado;
	private Boolean PresupuestoGenerado;
	private Boolean PresupuestoEnviado;
	private Boolean Enviado;
	private Boolean AvisoEnviado;
	private Double Pago;
	private String NombreEquipo;
	private String Correo;
	private String Modelo;
	private String Marca;
	private String Aviso;
	private String ClienteCliente;
	private String NumeroDeSerie;
	private int idCliente;
	private int idSucursal;
	private String Sucursal;
	private String Cliente;
	private String NombreUsuario;
	private int Codigo;
	private int NumeroRemitoSalida;
	private String FechaFabr;
	private String componenteOriginal;
	private String componenteReemplazo;
	

	public ReparacionDTO(int ELS, String Fecha_Entrada, String Fechadereparacion, String Falla, String Solucion,
			String Informecliente, int IDTecnico, String EstadoFisico, String EstadoTecnico, String EstadoComercial,
			String RemitoCliente, String OrdendeCompra, Boolean Agregadoaremito, Boolean RemitoGenerado, int IDEquipo,
			int CodigoRemito, Double PrecioPeso, Double PrecioDolar, Boolean InformeEnviado, String FechAceptacion,
			Boolean PresupuestoGenerado, Boolean Enviado, Double Pago, Boolean PresupuestoEnviado, String Nombre, String Correo, String Modelo,
			String Marca, String NumeroDeSerie, String Aviso, String ClienteCliente, int IDCliente, int IDSuc,
			String Cliente, String Sucursal, String NombreUsuario, int Codigo, int NumeroRemitoSalida,
			String FechaFabr, Boolean AvisoEnviado) {

		this.ELS = ELS;
		this.FechaEntrada = Fecha_Entrada;
		this.FechAceptacion = FechAceptacion;
		this.FechadeDiagnostico = Fechadereparacion;
		this.Falla = Falla;
		this.Solucion = Solucion;
		this.Informecliente = Informecliente;
		this.idUsuario = IDTecnico;
		this.EstadoFisico = EstadoFisico;
		this.EstadoTecnico = EstadoTecnico;
		this.EstadoComercial = EstadoComercial;
		this.RemitoCliente = RemitoCliente;
		this.OrdendeCompra = OrdendeCompra;
		this.Agregadoaremito = Agregadoaremito;
		this.RemitoGenerado = RemitoGenerado;
		this.idEquipo = IDEquipo;
		this.idRemito = CodigoRemito;
		this.PrecioPeso = PrecioPeso;
		this.PrecioDolar = PrecioDolar;
		this.InformeEnviado = InformeEnviado;
		this.PresupuestoGenerado = PresupuestoGenerado;
		this.PresupuestoEnviado = PresupuestoEnviado;
		this.Enviado = Enviado;
		this.Pago = Pago;
		this.NombreEquipo = Nombre;
		this.Correo = Correo;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.Aviso = Aviso;
		this.ClienteCliente = ClienteCliente;
		this.NumeroDeSerie = NumeroDeSerie;
		this.idCliente = IDCliente;
		this.idSucursal = IDSuc;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		this.NombreUsuario = NombreUsuario;
		this.Codigo = Codigo;
		this.NumeroRemitoSalida = NumeroRemitoSalida;
		this.FechaFabr = FechaFabr;
		this.AvisoEnviado = AvisoEnviado;

	}

	public ReparacionDTO(int ELS, String Fecha_Entrada, String Cliente, String Sucursal, String Nombre, String Marca,
			String Modelo, String original, String reemplazo) {

		this.ELS = ELS;
		this.FechaEntrada = Fecha_Entrada;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		this.NombreEquipo = Nombre;
		this.Marca = Marca;
		this.Modelo = Modelo;
		this.componenteOriginal = original;
		this.componenteReemplazo = reemplazo;

	}

	public ReparacionDTO(int ELS, String Informecliente, Double preciopeso, Double preciodolar,boolean PresupuestoGenerado, boolean PresupuestoEnviado) {

		this.ELS = ELS;
		this.Informecliente = Informecliente;
		this.PrecioPeso = preciopeso;
		this.PrecioDolar = preciodolar;
		this.PresupuestoGenerado = PresupuestoGenerado;
		this.PresupuestoEnviado = PresupuestoEnviado;

	}

	public ReparacionDTO(int ELS, String Fecha_Entrada, String Fechadereparacion, String Falla, String Solucion,
			String Informecliente, String EstadoFisico, String EstadoTecnico, String EstadoComercial,
			String RemitoCliente, int IDEquipo, String Cliente, String Sucursal, String FechAceptacion, String Nombre,
			String Modelo, String Marca, String NumeroDeSerie, String Aviso, String ClienteCliente, int IDCliente,
			int IDSuc, String FechaFabr, int idUsuario, boolean enviado, double presupuesto, double pago, boolean PresupuestoGenerado, boolean avisoEnviao,boolean PresupuestoEnviado ) {

		this.ELS = ELS;
		this.FechaEntrada = Fecha_Entrada;
		this.FechAceptacion = FechAceptacion;
		this.FechadeDiagnostico = Fechadereparacion;
		this.Falla = Falla;
		this.Solucion = Solucion;
		this.Informecliente = Informecliente;
		this.EstadoFisico = EstadoFisico;
		this.EstadoTecnico = EstadoTecnico;
		this.EstadoComercial = EstadoComercial;
		this.RemitoCliente = RemitoCliente;
		this.idEquipo = IDEquipo;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.Aviso = Aviso;
		this.ClienteCliente = ClienteCliente;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		this.idCliente = IDCliente;
		this.idSucursal = IDSuc;
		this.FechaFabr = FechaFabr;
		this.idUsuario = idUsuario;
		this.Enviado = enviado;
		this.PrecioPeso = presupuesto;
		this.Pago = pago;
		this.PresupuestoGenerado = PresupuestoGenerado;
		this.AvisoEnviado = avisoEnviao;
		this.PresupuestoEnviado = PresupuestoEnviado;

	
	}
	
	

	public ReparacionDTO(int ELS, String Fecha_Entrada, String Fechadereparacion, String Falla, String Solucion,
			String Informecliente, String EstadoFisico, String EstadoTecnico, String EstadoComercial,
			String RemitoCliente, int IDEquipo, String Cliente, String Sucursal, String Nombre, String Modelo,
			String Marca, String NumeroDeSerie, String Aviso, String ClienteCliente, int IDCliente, int IDSuc) {

		this.ELS = ELS;
		this.FechaEntrada = Fecha_Entrada;
		this.FechadeDiagnostico = Fechadereparacion;
		this.Falla = Falla;
		this.Solucion = Solucion;
		this.Informecliente = Informecliente;
		this.EstadoFisico = EstadoFisico;
		this.EstadoTecnico = EstadoTecnico;
		this.EstadoComercial = EstadoComercial;
		this.RemitoCliente = RemitoCliente;
		this.idEquipo = IDEquipo;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.Aviso = Aviso;
		this.ClienteCliente = ClienteCliente;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		this.idCliente = IDCliente;
		this.idSucursal = IDSuc;

	}

	public ReparacionDTO(int ELS, String Fecha_Entrada, String Falla, String Solucion, String Informecliente,
			String EstadoFisico, String EstadoTecnico, String EstadoComercial, String RemitoCliente, int IDEquipo,
			String Cliente, String Sucursal, String Nombre, String Modelo, String Marca, String NumeroDeSerie,
			String Aviso, String ClienteCliente, int IDCliente, int IDSuc) {

		this.ELS = ELS;
		this.FechaEntrada = Fecha_Entrada;
		this.Falla = Falla;
		this.Solucion = Solucion;
		this.Informecliente = Informecliente;
		this.EstadoFisico = EstadoFisico;
		this.EstadoTecnico = EstadoTecnico;
		this.EstadoComercial = EstadoComercial;
		this.RemitoCliente = RemitoCliente;
		this.idEquipo = IDEquipo;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.Aviso = Aviso;
		this.ClienteCliente = ClienteCliente;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		this.idCliente = IDCliente;
		this.idSucursal = IDSuc;

	}

	public ReparacionDTO(int ELS, String Fecha_Entrada, String Falla, String EstadoFisico, String EstadoTecnico,
			String RemitoCliente, int IDEquipo, int IDusuario, String Nombre, String Modelo, String Marca,
			String NumeroDeSerie, String Aviso, String ClienteCliente, int IDCliente, int IDSuc, String FechaFabr) {
		this.ELS = ELS;
		this.FechaEntrada = Fecha_Entrada;
		this.Falla = Falla;
		this.EstadoFisico = EstadoFisico;
		this.EstadoTecnico = EstadoTecnico;
		this.RemitoCliente = RemitoCliente;
		this.idEquipo = IDEquipo;
		this.idUsuario = IDusuario;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Aviso = Aviso;
		this.ClienteCliente = ClienteCliente;
		this.idCliente = IDCliente;
		this.idSucursal = IDSuc;
		this.FechaFabr = FechaFabr;

	}

	public ReparacionDTO(int IDEquipo, String Nombre, String Modelo, String Marca, String NumeroDeSerie, String Aviso,
			String ClienteCliente, int IDCliente, int IDSuc) {
		this.idEquipo = IDEquipo;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Aviso = Aviso;
		this.ClienteCliente = ClienteCliente;
		this.idCliente = IDCliente;
		this.idSucursal = IDSuc;

	}

	public ReparacionDTO(String Nombre) {

		this.NombreEquipo = Nombre;

	}

	public ReparacionDTO(int ELS, boolean agregadoAremito, int idRemito) {

		this.ELS = ELS;
		this.Agregadoaremito = agregadoAremito;
		this.idRemito = idRemito;

	}

	public ReparacionDTO(int ELS, String estadoFisico, boolean enviado, boolean agregadoAremito) {

		this.ELS = ELS;
		this.EstadoFisico = estadoFisico;
		this.Enviado = enviado;
		this.Agregadoaremito = agregadoAremito;

	}

	public ReparacionDTO(int ELS, String estadoFisico, boolean generado, boolean agregado, Integer idRemito,
			boolean enviado) {

		this.ELS = ELS;
		this.EstadoFisico = estadoFisico;
		this.RemitoGenerado = generado;
		this.Agregadoaremito = agregado;
		this.idRemito = idRemito;
		this.Enviado = enviado;

	}

	public String getNombreUsuario() {
		return NombreUsuario;
	}

	public int getCodigo() {
		return Codigo;
	}

	public void setCodigo(int codigo) {
		Codigo = codigo;
	}

	public int getNumeroRemitoSalida() {
		return NumeroRemitoSalida;
	}

	public void setNumeroRemitoSalida(int numeroRemitoSalida) {
		NumeroRemitoSalida = numeroRemitoSalida;
	}

	public void setNombreUsuario(String nombreUsuario) {
		NombreUsuario = nombreUsuario;
	}

	public String getSucursal() {
		return Sucursal;
	}

	public void setSucursal(String sucursal) {
		Sucursal = sucursal;
	}

	public String getCliente() {
		return Cliente;
	}

	public void setCliente(String cliente) {
		Cliente = cliente;
	}

	public String getNombreEquipo() {
		return NombreEquipo;
	}

	public void setNombreEquipo(String nombre) {
		NombreEquipo = nombre;
	}

	public String getCorreo() {
		return Correo;
	}

	public void setCorreo(String correo) {
		Correo = correo;
	}

	public String getModelo() {
		return Modelo;
	}

	public void setModelo(String modelo) {
		Modelo = modelo;
	}

	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}

	public String getAviso() {
		return Aviso;
	}

	public void setAviso(String aviso) {
		Aviso = aviso;
	}

	public String getClienteCliente() {
		return ClienteCliente;
	}

	public void setClienteCliente(String clienteCliente) {
		ClienteCliente = clienteCliente;
	}

	public String getNumeroDeSerie() {
		return NumeroDeSerie;
	}

	public void setNumeroDeSerie(String numeroDeSerie) {
		NumeroDeSerie = numeroDeSerie;
	}

	public int getIDCliente() {
		return idCliente;
	}

	public void setIDCliente(int iDCliente) {
		idCliente = iDCliente;
	}

	public int getIDSuc() {
		return idSucursal;
	}

	public void setIDSuc(int iDSuc) {
		idSucursal = iDSuc;
	}

	public int getELS() {
		return ELS;
	}

	public void setELS(int eLS) {
		ELS = eLS;
	}

	public String getFecha_Entrada() {
		return FechaEntrada;
	}

	public void setFecha_Entrada(String fecha_Entrada) {
		FechaEntrada = fecha_Entrada;
	}

	public String getFechadereparacion() {
		return FechadeDiagnostico;
	}

	public void setFechadereparacion(String fechadereparacion) {
		FechadeDiagnostico = fechadereparacion;
	}

	public String getFechAceptacion() {
		return FechAceptacion;
	}

	public void setFechAceptacion(String fechAceptacion) {
		FechAceptacion = fechAceptacion;
	}

	public String getFechaFabr() {
		return FechaFabr;
	}

	public void setFechaFabr(String fechaFabr) {
		FechaFabr = fechaFabr;
	}

	public String getFalla() {
		return Falla;
	}

	public void setFalla(String falla) {
		Falla = falla;
	}

	public String getSolucion() {
		return Solucion;
	}

	public void setSolucion(String solucion) {
		Solucion = solucion;
	}

	public String getInformecliente() {
		return Informecliente;
	}

	public void setInformecliente(String informecliente) {
		Informecliente = informecliente;
	}

	public int getidUsuario() {
		return idUsuario;
	}

	public void setIDTecnico(int idUsuarios) {
		idUsuario = idUsuarios;
	}

	public String getEstadoFisico() {
		return EstadoFisico;
	}

	public void setEstadoFisico(String estadoFisico) {
		EstadoFisico = estadoFisico;
	}

	public String getEstadoTecnico() {
		return EstadoTecnico;
	}

	public void setEstadoTecnico(String estadoTecnico) {
		EstadoTecnico = estadoTecnico;
	}

	public String getEstadoComercial() {
		return EstadoComercial;
	}

	public void setEstadoComercial(String estadoComercial) {
		EstadoComercial = estadoComercial;
	}

	public String getRemitoCliente() {
		return RemitoCliente;
	}

	public void setRemitoCliente(String remitoCliente) {
		RemitoCliente = remitoCliente;
	}

	public String getOrdendeCompra() {
		return OrdendeCompra;
	}

	public void setOrdendeCompra(String ordendeCompra) {
		OrdendeCompra = ordendeCompra;
	}

	public Boolean getAgregadoaremito() {
		return Agregadoaremito;
	}

	public void setAgregadoaremito(Boolean agregadoaremito) {
		Agregadoaremito = agregadoaremito;
	}

	public Boolean getRemitoGenerado() {
		return RemitoGenerado;
	}

	public void setRemitoGenerado(Boolean remitoGenerado) {
		RemitoGenerado = remitoGenerado;
	}

	public int getIDEquipo() {
		return idEquipo;
	}

	public void setIDEquipo(int iDEquipo) {
		idEquipo = iDEquipo;
	}

	public int getidRemito() {
		return idRemito;
	}

	public void setCodigoRemito(int IDRemito) {
		idRemito = IDRemito;
	}

	public Double getPrecioPeso() {
		return PrecioPeso;
	}

	public void setPrecioPeso(Double precioPeso) {
		PrecioPeso = precioPeso;
	}

	public Double getPrecioDolar() {
		return PrecioDolar;
	}
	
	public void setComponenteOriginal(String ComponenteOriginal) {
		componenteOriginal = ComponenteOriginal;
	}

	public String getComponenteOriginal() {
		return componenteOriginal;
	}
	
	public void setComponenteReemplazo(String ComponenteReemplazo) {
		componenteReemplazo = ComponenteReemplazo;
	}

	public String getComponenteReemplazo() {
		return componenteReemplazo;
	}

	public void setPrecioDolar(Double precioDolar) {
		PrecioDolar = precioDolar;
	}

	public Boolean getInformeEnviado() {
		return InformeEnviado;
	}

	public void setInformeEnviado(Boolean informeEnviado) {
		InformeEnviado = informeEnviado;
	}

	public Boolean getPresupuestoGenerado() {
		return PresupuestoGenerado;
	}

	public void setPresupuestoGenerado(Boolean presupuestoGenerado) {
		PresupuestoGenerado = presupuestoGenerado;
	}

	public Boolean getEnviado() {
		return Enviado;
	}

	public void setEnviado(Boolean enviado) {
		Enviado = enviado;
	}

	public Double getPago() {
		return Pago;
	}

	public void setPago(Double pago) {
		Pago = pago;
	}

	public String toString() {
		return this.NombreEquipo;

	}

	public Boolean getAvisoEnviado() {
		return AvisoEnviado;
	}
	
	public void setAvisoEnviado(Boolean AvEnviado) {
		AvisoEnviado = AvEnviado;
	}

	public Boolean getPresupuestoEnviado() {
		return PresupuestoEnviado;
	}

	public void setPresupuestoEnviado(Boolean presupuestoEnviado) {
		PresupuestoEnviado = presupuestoEnviado;
	}

}

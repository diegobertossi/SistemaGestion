package dto;

public class RegistroEntradaReporteDTO
{
	private int ELS;
	private String Fecha_Entrada;
	private String Fechadereparacion;
	private String FechAceptacion;
	private String Falla;
	private String Solucion;
	private String Informecliente;
	private int IDTecnico;
	private String EstadoTecnicoClave;
	private String EstadoComercialClave;
	private String EstadoFisicoClave;
	private String EstadoFisico;
	private String EstadoTecnico;
	private String EstadoComercial;
	private String RemitoCliente;
	private String OrdendeCompra;
	private Boolean Agregadoaremito;
	private Boolean RemitoGenerado;
	private int IDEquipo;
	private int CodigoRemito;
	private Double PrecioPeso;
	private Double PrecioDolar;
	private Boolean InformeEnviado;
	private Boolean PresupuestoGenerado;
	private Boolean Enviado;
	private Double Pago;
	private String NombreEquipo;
	private String Correo;
	private String Modelo;
	private String Marca;
	private String Aviso;
	private String ClienteCliente;
	private String NumeroDeSerie;
	private int IDCliente;
	private int IDSuc;
	private String Sucursal;
	private String Cliente;
	private String NombreTecnico;
	private int Codigo;
	private int NumeroRemitoSalida;
	
	public RegistroEntradaReporteDTO(int ELS, String Fecha_Entrada, String Falla, String EstadoFisico, String EstadoTecnico, String RemitoCliente, int IDEquipo ,String Nombre, String Modelo, String Marca, String NumeroDeSerie, String Aviso,String ClienteCliente,int IDCliente,int IDSuc, String Cliente, String Sucursal)
	{
		this.ELS = ELS;
		this.Fecha_Entrada = Fecha_Entrada;
		this.Falla = Falla;
		this.EstadoFisico = EstadoFisico;
		this.EstadoTecnico = EstadoTecnico;
		this.RemitoCliente = RemitoCliente;
		this.IDEquipo = IDEquipo;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Aviso = Aviso;
		this.ClienteCliente =ClienteCliente;
		this.IDCliente = IDCliente;
		this.IDSuc = IDSuc;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		
			
	}

	public int getELS() {
		return ELS;
	}

	public void setELS(int eLS) {
		ELS = eLS;
	}

	public String getFecha_Entrada() {
		return Fecha_Entrada;
	}

	public void setFecha_Entrada(String fecha_Entrada) {
		Fecha_Entrada = fecha_Entrada;
	}

	public String getFechadereparacion() {
		return Fechadereparacion;
	}

	public void setFechadereparacion(String fechadereparacion) {
		Fechadereparacion = fechadereparacion;
	}

	public String getFechAceptacion() {
		return FechAceptacion;
	}

	public void setFechAceptacion(String fechAceptacion) {
		FechAceptacion = fechAceptacion;
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

	public int getIDTecnico() {
		return IDTecnico;
	}

	public void setIDTecnico(int iDTecnico) {
		IDTecnico = iDTecnico;
	}

	public String getEstadoTecnicoClave() {
		return EstadoTecnicoClave;
	}

	public void setEstadoTecnicoClave(String estadoTecnicoClave) {
		EstadoTecnicoClave = estadoTecnicoClave;
	}

	public String getEstadoComercialClave() {
		return EstadoComercialClave;
	}

	public void setEstadoComercialClave(String estadoComercialClave) {
		EstadoComercialClave = estadoComercialClave;
	}

	public String getEstadoFisicoClave() {
		return EstadoFisicoClave;
	}

	public void setEstadoFisicoClave(String estadoFisicoClave) {
		EstadoFisicoClave = estadoFisicoClave;
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
		return IDEquipo;
	}

	public void setIDEquipo(int iDEquipo) {
		IDEquipo = iDEquipo;
	}

	public int getCodigoRemito() {
		return CodigoRemito;
	}

	public void setCodigoRemito(int codigoRemito) {
		CodigoRemito = codigoRemito;
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

	public String getNombreEquipo() {
		return NombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		NombreEquipo = nombreEquipo;
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
		return IDCliente;
	}

	public void setIDCliente(int iDCliente) {
		IDCliente = iDCliente;
	}

	public int getIDSuc() {
		return IDSuc;
	}

	public void setIDSuc(int iDSuc) {
		IDSuc = iDSuc;
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

	public String getNombreTecnico() {
		return NombreTecnico;
	}

	public void setNombreTecnico(String nombreTecnico) {
		NombreTecnico = nombreTecnico;
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


	
}

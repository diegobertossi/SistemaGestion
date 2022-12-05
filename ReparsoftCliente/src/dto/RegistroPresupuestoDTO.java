package dto;

public class RegistroPresupuestoDTO
{
	private int ELS;
	private String Informecliente;
	private String RemitoCliente;
	private Double PrecioPeso;
	private Double PrecioDolar;
	private String NombreEquipo;
	private String Modelo;
	private String Marca;
	private String Aviso;
	private String ClienteCliente;
	private String NumeroDeSerie;
	private String Sucursal;
	private String Cliente;
	private boolean chckpesos;
	private boolean chckdolar;
	private String condicionesMoneda;
	private String condicionesPago;
	private String plazoEntrega;
	
	public RegistroPresupuestoDTO(int ELS, String InformeCliente, String RemitoCliente,Double PrecioPeso, Double PrecioDolar, String Nombre, String Modelo, String Marca, String NumeroDeSerie, String Aviso,String ClienteCliente, String Cliente, String Sucursal)
	{
		this.ELS = ELS;
		this.Informecliente = InformeCliente;
		this.RemitoCliente = RemitoCliente;
		this.PrecioPeso = PrecioPeso;
		this.PrecioDolar = PrecioDolar;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Aviso = Aviso;
		this.ClienteCliente =ClienteCliente;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		
			
	}

	
	public RegistroPresupuestoDTO(int ELS, String InformeCliente, String RemitoCliente,Double PrecioPeso, Double PrecioDolar, String Nombre, String Modelo, String Marca, String NumeroDeSerie, String Aviso,String ClienteCliente, String Cliente, String Sucursal,boolean chckpesos, boolean chckdolar, String condicionesMoneda, String condicionesPago, String plazoEntrega)
	{
		this.ELS = ELS;
		this.Informecliente = InformeCliente;
		this.RemitoCliente = RemitoCliente;
		this.PrecioPeso = PrecioPeso;
		this.PrecioDolar = PrecioDolar;
		this.NombreEquipo = Nombre;
		this.Modelo = Modelo;
		this.Marca = Marca;
		this.NumeroDeSerie = NumeroDeSerie;
		this.Aviso = Aviso;
		this.ClienteCliente =ClienteCliente;
		this.Cliente = Cliente;
		this.Sucursal = Sucursal;
		this.chckpesos = chckpesos;
		this.chckdolar = chckdolar;
		this.condicionesMoneda = condicionesMoneda;
		this.condicionesPago = condicionesPago;
		this.plazoEntrega = plazoEntrega;
			
	}


	public String getCondicionesMoneda() {
		return condicionesMoneda;
	}


	public void setCondicionesMoneda(String condicionesMoneda) {
		this.condicionesMoneda = condicionesMoneda;
	}


	public String getCondicionesPago() {
		return condicionesPago;
	}


	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}


	public String getPlazoEntrega() {
		return plazoEntrega;
	}


	public void setPlazoEntrega(String plazoEntrega) {
		this.plazoEntrega = plazoEntrega;
	}


	public int getELS() {
		return ELS;
	}


	public void setELS(int eLS) {
		ELS = eLS;
	}


	public String getInformecliente() {
		return Informecliente;
	}


	public void setInformecliente(String informecliente) {
		Informecliente = informecliente;
	}


	public String getRemitoCliente() {
		return RemitoCliente;
	}


	public void setRemitoCliente(String remitoCliente) {
		RemitoCliente = remitoCliente;
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


	public String getNombreEquipo() {
		return NombreEquipo;
	}


	public void setNombreEquipo(String nombreEquipo) {
		NombreEquipo = nombreEquipo;
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


	public boolean isChckpesos() {
		return chckpesos;
	}


	public void setChckpesos(boolean chckpesos) {
		this.chckpesos = chckpesos;
	}


	public boolean isChckdolar() {
		return chckdolar;
	}


	public void setChckdolar(boolean chckdolar) {
		this.chckdolar = chckdolar;
	}




	
}

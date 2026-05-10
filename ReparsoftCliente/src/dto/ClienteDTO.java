package dto;

public class ClienteDTO 
{
	private Integer Id;
	private String Razon_Social;
	private String CUIT;
	private String Domicilio;
	private String TelefonoEmpresa;
	private String Contacto;
	private String TelefonoContacto;
	private String CorreoElectronico;

	public ClienteDTO(Integer Id, String Razon_Social,String CUIT,String Domicilio, String TelefonoEmpresa,String Contacto, String TelefonoContacto,String CorreoElectronico)
	{
		this.Id = Id;
		this.Razon_Social = Razon_Social;
		this.CUIT= CUIT;
		this.Domicilio=Domicilio;
		this.TelefonoEmpresa= TelefonoEmpresa;
		this.Contacto=Contacto;
		this.TelefonoContacto= TelefonoContacto;		
		this.CorreoElectronico = CorreoElectronico;
		
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getRazon_Social() {
		return Razon_Social;
	}

	public void setRazon_Social(String razon_Social) {
		Razon_Social = razon_Social;
	}

	public String getCUIT() {
		return CUIT;
	}

	public void setCUIT(String cUIT) {
		CUIT = cUIT;
	}

	public String getDomicilio() {
		return Domicilio;
	}

	public void setDomicilio(String domicilio) {
		Domicilio = domicilio;
	}

	public String getTelefonoEmpresa() {
		return TelefonoEmpresa;
	}

	public void setTelefonoEmpresa(String telefonoEmpresa) {
		TelefonoEmpresa = telefonoEmpresa;
	}

	public String getContacto() {
		return Contacto;
	}

	public void setContacto(String contacto) {
		Contacto = contacto;
	}

	public String getTelefonoContacto() {
		return TelefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		TelefonoContacto = telefonoContacto;
	}

	public String getCorreoElectronico() {
		return CorreoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		CorreoElectronico = correoElectronico;
	}

	
	public String toString(){
		return this.Razon_Social;
		
	}
	

	
}

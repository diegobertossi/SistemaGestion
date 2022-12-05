package dto;

public class ClienteWSPDTO 
{
	private Integer idClienteWSP;
	private String organizacion;
	private String nombreWSP;
	private String TelefonoWSP;
	

	public ClienteWSPDTO(Integer idClienteWSP, String organizacion,String nombreWSP,String TelefonoWSP)
	{
		this.idClienteWSP = idClienteWSP;
		this.organizacion = organizacion;
		this.nombreWSP= nombreWSP;
		this.TelefonoWSP=TelefonoWSP;
		
	}

	
	public ClienteWSPDTO( String organizacion)
	{
		
		
		this.organizacion= organizacion;
		
		
	}

	
	public Integer getIdClienteWSP() {
		return idClienteWSP;
	}


	public void setIdClienteWSP(int idClienteWSP) {
		this.idClienteWSP = idClienteWSP;
	}


	public String getOrganizacion() {
		return organizacion;
	}


	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}


	public String getNombreWSP() {
		return nombreWSP;
	}


	public void setNombreWSP(String nombreWSP) {
		this.nombreWSP = nombreWSP;
	}


	public String getTelefonoWSP() {
		return TelefonoWSP;
	}


	public void setTelefonoWSP(String telefonoWSP) {
		this.TelefonoWSP = telefonoWSP;
	}

	public String toString(){
		return this.organizacion;
		
	}


	
}

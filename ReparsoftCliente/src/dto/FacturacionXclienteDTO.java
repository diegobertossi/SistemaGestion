package dto;

public class FacturacionXclienteDTO 
{
	
	private int idCliente;
	private String nombreCliente;
	private double facturacion; 


	public FacturacionXclienteDTO(int idCliente, String nombreCliente, double facturacion)
	
	{
		
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.facturacion = facturacion;
		

	}


	public int getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public double getFacturacion() {
		return facturacion;
	}


	public void setFacturacion(double facturacion) {
		this.facturacion = facturacion;
	}
	
	
	

}

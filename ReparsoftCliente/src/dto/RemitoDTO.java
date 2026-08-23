package dto;

import java.util.ArrayList;
import java.util.List;

public class RemitoDTO 
{
	private Integer IdUbicacion;
	private String ubicacion;
	private Integer codigoUbicacion;
	private Integer IdRemito;
	private Integer numeroRemitoSalida;
	List<String> descripcion = new ArrayList<String>();
	private String Cliente;
	private String RemitoConformado;
	private int cantBultos; 
	private String cuit;
	private String domicilio;
	private String fecha_Entrada;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RemitoDTO(Integer IdUbicacion, Integer codigoUbicacion,Integer IdRemito, Integer numeroRemitoSalida,List descripcion, String Cliente, String RemitoConformado,int cantBultos, String cuit, String domicilio)
	{
		this.IdUbicacion = IdUbicacion;
		this.codigoUbicacion = codigoUbicacion;
		this.IdRemito = IdRemito;
		this.numeroRemitoSalida = numeroRemitoSalida;
		this.descripcion=descripcion;
		this.Cliente= Cliente;
		this.RemitoConformado = RemitoConformado;
		this.cantBultos =cantBultos;
		this.cuit = cuit;
		this.domicilio = domicilio;
	
		
		
	}

	public RemitoDTO(Integer IdUbicacion,Integer numeroRemitoSalida, Integer IdRemito)
	{
		this.IdUbicacion = IdUbicacion;
		this.numeroRemitoSalida = numeroRemitoSalida;
		this.IdRemito = IdRemito;
	
		
		
	}



	public List<String> getDescripcion() {
		return descripcion;
	}





	public void setDescripcion(List<String> descripcion) {
		this.descripcion = descripcion;
	}





	public int getCantBultos() {
		return cantBultos;
	}
	


	public void setCantBultos(int cantBultos) {
		this.cantBultos = cantBultos;
	}
	


	public String getRemitoConformado() {
		return RemitoConformado;
	}
	


	public void setRemitoConformado(String remitoConformado) {
		RemitoConformado = remitoConformado;
	}
	





	public Integer getIdUbicacion() {
		return IdUbicacion;
	}


	public void setIdUbicacion(Integer idUbicacion) {
		IdUbicacion = idUbicacion;
	}


	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public Integer getCodigoUbicacion() {
		return codigoUbicacion;
	}


	public void setCodigoUbicacion(Integer codigoUbicacion) {
		this.codigoUbicacion = codigoUbicacion;
	}


	public Integer getIdRemito() {
		return IdRemito;
	}


	public void setIdRemito(Integer idRemito) {
		IdRemito = idRemito;
	}


	public Integer getNumeroRemitoSalida() {
		return numeroRemitoSalida;
	}


	public void setNumeroRemitoSalida(Integer numeroRemitoSalida) {
		this.numeroRemitoSalida = numeroRemitoSalida;
	}


	public String getCliente() {
		return Cliente;
	}
	


	public void setCliente(String cliente) {
		Cliente = cliente;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getFecha_Entrada() {
		return fecha_Entrada;
	}

	public void setFecha_Entrada(String fecha_Entrada) {
		this.fecha_Entrada = fecha_Entrada;
	}
	



		
}

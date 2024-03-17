package dto;

public class RegistroResumenTecnicoDTO {

	private String nombreTecnico;
	private String anio;
	private String mes;
	// POR AÑO
	private String revisadosAnio;
	private String reparadosAnio;
	private String reparadosEngtiaAnio;
	private String sinFallaAnio;
	private String enReparacionAnio;
	private String ventasAnio;
	private String sinReparacionAnio;
	
	private String repAceptadaAnio;
	private String repNoAceptAnio;
	private String repEsperaAnio ;
	
	
	// POR MES
	private String revisadosMes;
	private String reparadosMes;
	private String reparadosEngtiaMes;
	private String sinFallaMes;
	private String enReparacionMes;
	private String ventasMes;
	private String sinReparacionMes;
	
	private String repAceptadaMes; 
	private String repNoAceptMes; 
	private String repEsperaMes; 
	private String aceptacionesDelMes;

	public RegistroResumenTecnicoDTO(String nombreTecnico, String anio, String mes, String revisadosAnio, String reparadosAnio,
			String reparadosEngtiaAnio, String sinFallaAnio, String enReparacionAnio, String ventasAnio,
			String sinReparacionAnio,String repAceptadaAnio,String repNoAceptAnio, String repEsperaAnio, String revisadosMes, String reparadosMes, String sinFallaMes, String reparadosEngtiaMes,
			String enReparacionMes, String ventasMes, String sinReparacionMes, String repAceptadaMes,  String repNoAceptMes, String repEsperaMes, String aceptacionesDelMes) {

		this.nombreTecnico = nombreTecnico;
		this.anio = anio;
		this.mes = mes;
		
		this.revisadosAnio = revisadosAnio;
		this.reparadosAnio = reparadosAnio;
		this.reparadosEngtiaAnio = reparadosEngtiaAnio;
		this.sinFallaAnio = sinFallaAnio;
		this.enReparacionAnio = enReparacionAnio;
		this.ventasAnio = ventasAnio;
		this.sinReparacionAnio = sinReparacionAnio;
		this.repAceptadaAnio= repAceptadaAnio;
		this.repNoAceptAnio=repNoAceptAnio;
		this.repEsperaAnio=repEsperaAnio;
		
		this.revisadosMes = revisadosMes;
		this.reparadosMes = reparadosMes;
		this.reparadosEngtiaMes = reparadosEngtiaMes;
		this.sinFallaMes = sinFallaMes;
		this.enReparacionMes = enReparacionMes;
		this.ventasMes = ventasMes;
		this.sinReparacionMes = sinReparacionMes;
		this.repAceptadaMes =repAceptadaMes; 
		this.repNoAceptMes = repNoAceptMes; 
		this.repEsperaMes = repEsperaMes; 
		this.aceptacionesDelMes= aceptacionesDelMes;
		

	}

	public String getNombreTecnico() {
		return nombreTecnico;
	}

	public void setNombreTecnico(String nombreTecnico) {
		this.nombreTecnico = nombreTecnico;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getRevisadosAnio() {
		return revisadosAnio;
	}

	public void setRevisadosAnio(String revisadosAnio) {
		this.revisadosAnio = revisadosAnio;
	}

	public String getReparadosAnio() {
		return reparadosAnio;
	}

	public void setReparadosAnio(String reparadosAnio) {
		this.reparadosAnio = reparadosAnio;
	}

	public String getReparadosEngtiaAnio() {
		return reparadosEngtiaAnio;
	}

	public void setReparadosEngtiaAnio(String reparadosEngtiaAnio) {
		this.reparadosEngtiaAnio = reparadosEngtiaAnio;
	}

	public String getSinFallaAnio() {
		return sinFallaAnio;
	}

	public void setSinFallaAnio(String sinFallaAnio) {
		this.sinFallaAnio = sinFallaAnio;
	}

	public String getEnReparacionAnio() {
		return enReparacionAnio;
	}

	public void setEnReparacionAnio(String enReparacionAnio) {
		this.enReparacionAnio = enReparacionAnio;
	}

	public String getVentasAnio() {
		return ventasAnio;
	}

	public void setVentasAnio(String ventasAnio) {
		this.ventasAnio = ventasAnio;
	}

	public String getSinReparacionAnio() {
		return sinReparacionAnio;
	}

	public void setSinReparacionAnio(String sinReparacionAnio) {
		this.sinReparacionAnio = sinReparacionAnio;
	}

	public String getRevisadosMes() {
		return revisadosMes;
	}

	public void setRevisadosMes(String revisadosMes) {
		this.revisadosMes = revisadosMes;
	}

	public String getReparadosMes() {
		return reparadosMes;
	}

	public void setReparadosMes(String reparadosMes) {
		this.reparadosMes = reparadosMes;
	}

	public String getReparadosEngtiaMes() {
		return reparadosEngtiaMes;
	}

	public void setReparadosEngtiaMes(String reparadosEngtiaMes) {
		this.reparadosEngtiaMes = reparadosEngtiaMes;
	}

	public String getSinFallaMes() {
		return sinFallaMes;
	}

	public void setSinFallaMes(String sinFallaMes) {
		this.sinFallaMes = sinFallaMes;
	}

	public String getEnReparacionMes() {
		return enReparacionMes;
	}

	public void setEnReparacionMes(String enReparacionMes) {
		this.enReparacionMes = enReparacionMes;
	}

	public String getVentasMes() {
		return ventasMes;
	}

	public void setVentasMes(String ventasMes) {
		this.ventasMes = ventasMes;
	}

	public String getSinReparacionMes() {
		return sinReparacionMes;
	}

	public void setSinReparacionMes(String sinReparacionMes) {
		this.sinReparacionMes = sinReparacionMes;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getRepAceptadaAnio() {
		return repAceptadaAnio;
	}

	public void setRepAceptadaAnio(String repAceptadaAnio) {
		this.repAceptadaAnio = repAceptadaAnio;
	}

	public String getRepNoAceptAnio() {
		return repNoAceptAnio;
	}

	public void setRepNoAceptAnio(String repNoAceptAnio) {
		this.repNoAceptAnio = repNoAceptAnio;
	}

	public String getRepEsperaAnio() {
		return repEsperaAnio;
	}

	public void setRepEsperaAnio(String repEsperaAnio) {
		this.repEsperaAnio = repEsperaAnio;
	}

	public String getRepAceptadaMes() {
		return repAceptadaMes;
	}

	public void setRepAceptadaMes(String repAceptadaMes) {
		this.repAceptadaMes = repAceptadaMes;
	}

	public String getRepNoAceptMes() {
		return repNoAceptMes;
	}

	public void setRepNoAceptMes(String repNoAceptMes) {
		this.repNoAceptMes = repNoAceptMes;
	}

	public String getRepEsperaMes() {
		return repEsperaMes;
	}

	public void setRepEsperaMes(String repEsperaMes) {
		this.repEsperaMes = repEsperaMes;
	}

	public String getAceptacionesDelMes() {
		return aceptacionesDelMes;
	}

	public void setAceptacionesDelMes(String aceptacionesDelMes) {
		this.aceptacionesDelMes = aceptacionesDelMes;
	}

}

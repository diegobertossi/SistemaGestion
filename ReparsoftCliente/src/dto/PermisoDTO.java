package dto;

public class PermisoDTO {

	private Integer idPermiso;
	private Integer idRol;
	private Integer idPantalla;
	private String nombrePantalla;
	private String nombrePantallaPadre;
	
	public PermisoDTO(Integer idPermiso, Integer idRol, Integer idPantalla, String nombrePantalla)
	{
		this.idPermiso = idPermiso;
		this.idRol = idRol;
		this.idPantalla = idPantalla;
		this.nombrePantalla = nombrePantalla;
	}
	
	public PermisoDTO(Integer idPermiso, Integer idRol, Integer idPantalla, String nombrePantalla,String padre)
	{
		this.idPermiso = idPermiso;
		this.idRol = idRol;
		this.idPantalla = idPantalla;
		this.nombrePantalla = nombrePantalla;
		this.nombrePantallaPadre = padre;
	}

	public Integer getIdPermiso() {
		return idPermiso;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public Integer getIdPantalla() {
		return idPantalla;
	}

	public String getNombrePantalla() {
		return nombrePantalla;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof PermisoDTO){
			PermisoDTO compare = (PermisoDTO) obj;
			if(compare.getIdPantalla() == 0 && compare.getIdRol() == 0)
			{
				return compare.getNombrePantalla() == getNombrePantalla();
			}
			else if(getIdPantalla() == 0 && getIdRol() == 0)
			{
				return compare.getNombrePantalla().equals(getNombrePantalla());
			}
			else return compare.getIdPantalla() == getIdPantalla() && getIdRol() == compare.getIdRol();
		}
		return obj.toString().equals(getNombrePantalla());
	}

	public String getNombrePantallaPadre() {
		return nombrePantallaPadre;
	}
	
	
	
}

package dto;

public class UsuarioDTO 
{
	private int idUsuario;
	private int idRol;
	private Integer dni;
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	private String login;
	private String pass;

	public UsuarioDTO(int idUsuario, int idRol, int dni,String nombre, String apellido, String telefono, String email)
	{
		this.idUsuario = idUsuario;
		this.idRol = idRol;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
	}
	
	public UsuarioDTO(int idUsuario, int idRol, int dni,String nombre, String apellido, String telefono, String email,String login, String pass)
	{
		this.idUsuario = idUsuario;
		this.idRol = idRol;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
		this.login = login;
		this.pass = pass;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getLogin() {
		return login;
	}

	public String getPass() {
		return pass;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof UsuarioDTO)
		{
			UsuarioDTO compare = (UsuarioDTO) obj;
			if(compare.getIdUsuario() != getIdUsuario())
			{
				return compare.getDni() == getDni() || compare.getEmail().equals(getEmail()) ;//|| compare.getLogin().equals(getLogin());
			}
		}
		return super.equals(obj);
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}

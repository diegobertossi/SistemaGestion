package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.ClienteDTO;


public interface ClienteDAO 
{
	
	public boolean insert(ClienteDTO Cliente);
	
	public boolean edit(ClienteDTO Cliente);

	public boolean delete(ClienteDTO Cliente_a_eliminar);
	
	public List<ClienteDTO> readAll();
	
	List<ClienteDTO> readAllPaginado(int limit, int offset);
	
	int contarClientes();
	
	public void ListarClientes(@SuppressWarnings("rawtypes") JComboBox box);
	
	public int obtenerIDcliente();

	public int obtenerIDporNombre(String nombreCliente);
	
	public String obtenerContactoPorCliente(String nombreCliente);

	public String obtenerEmailPorCliente(String nombreCliente);

	public boolean obtenerReparacionxIDCliente(int idCliente);
	
	public String obtenerTelefonoPorCliente(String Cliente);

	public String dameCuitPorIdCliente(int idCliente);
	
	public ClienteDTO obtenerPorRazonSocial(String nombre);

}

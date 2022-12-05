package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import dto.ClienteDTO;
import dto.ClienteWSPDTO;


public interface ClienteWSPDAO 
{
	
	public boolean insert(ClienteWSPDTO Cliente);
	
	public boolean edit(ClienteWSPDTO Cliente);

	public boolean delete(ClienteWSPDTO Cliente_a_eliminar);
	
	public List<ClienteWSPDTO> readAll();
	
	public void ListarClientesWSP(JComboBox box);
	
	public int obtenerIDclienteWSP();

	public int obtenerIDporNombreWSP(String nombreCliente);
	
	public String obtenerNumeroPorCliente(String nombreClienteWSP);

	public void ListarContactoxOrganizacion(JComboBox comboNombreBuscado, String organizacionWSP);

	public String obtenetTelefonoXcontacto(String contactoWSP);

	

}

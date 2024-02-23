package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.SucursalDTO;


public interface SucursalDAO 
{
	
	public boolean insert(SucursalDTO Sucursal);

	public boolean delete(SucursalDTO Sucursal_a_eliminar);
	
	public List<SucursalDTO> readAll();
	
	public List<SucursalDTO> obtenerSucursalXidCliente(Integer i);
	
	public void ListarSucursalesxCliente(JComboBox<?> box, int id);

	public int obtenerIDsucursal();

	public int obtenercantidaddeSucursales(int idcliente);
	
	public boolean edit(SucursalDTO Sucursal);

	public boolean obtenerReparacionxIDsSuc(int idsucursal);

	public int obtenerIDporNombre(String nombreSucursal, int IDCliente );

	public void ListarSucursales(JComboBox<?> box);


	
	
}

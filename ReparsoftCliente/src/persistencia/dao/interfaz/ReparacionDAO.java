package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.ReparacionDTO;



public interface ReparacionDAO 
{
	
	public boolean insert(ReparacionDTO nuevaReparacion);
			
	public boolean insertEquipo(ReparacionDTO nuevaReparacion);

	public boolean edit(ReparacionDTO reparacion_a_editar);
	
	public boolean editEquipo(ReparacionDTO reparacion_a_editar);
		
	public boolean delete(ReparacionDTO Reparacion_a_eliminar);
	
	public List<ReparacionDTO> readAll();
	
	public ReparacionDTO obtenerReparacionXels(Integer i);
		
	public int obtenerNumeroELSels();
	
	public int obtenerIDequipo();

	public void ListarEquipo(JComboBox<?> box);

	public void ListarMarca(JComboBox<?> comboMarca);

	public void ListarModelosxMarca(JComboBox<?> comboModelos, String marca);

	public void ListarSeriexModelo(JComboBox<?> comboSerie, String modelo);

	public void ListarModelos(JComboBox<?> box);

	public void ListarEstadoCom(JComboBox<?> comboFiltroEstadoCom);

	public void ListarEstadoFis(JComboBox<?> comboFiltroEstadoFis);

	public void comboFiltroEstadoTec(JComboBox<?> comboFiltroEstadoTec);

	public void comboFiltroAviso(JComboBox<?> comboFiltroAviso);

	public void comboFiltroELS(JComboBox<?> comboFiltroELS);

	public List<ReparacionDTO> readAllXIDclienteIDSucursal(Integer IDCliente, Integer IDSucursal);

	public void comboSerie(JComboBox<?> comboSerie);

	public ReparacionDTO obtenerReparacionXserie(String serie);

	public List<ReparacionDTO> readAllXIDremito(int iDremito);

	public List<ReparacionDTO> readAllxComponenteOriginal(String componente);

	public List<ReparacionDTO> readAllxComponenteReemplazo(String componente);

	public List<ReparacionDTO> readAllListadoMarcarAceptaciones();


	

}

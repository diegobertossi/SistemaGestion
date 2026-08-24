package presentacion.controlador.gestores;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import javax.swing.DefaultComboBoxModel;

import org.junit.Test;

import dto.UsuarioDTO;

/**
 * Valida el comportamiento pedido para técnicos eliminados: si un usuario con
 * reparaciones asociadas se borra de la tabla usuario, su nombre debe conservarse
 * en la reparación y al entrar a editar no debe perderse la selección.
 * (El fix agrega un ítem "fantasma" al combo cuando el técnico ya no existe.)
 */
public class GestorVisualizacionEquiposTest {

    private DefaultComboBoxModel<UsuarioDTO> modeloConTecnicosVigentes() {
        DefaultComboBoxModel<UsuarioDTO> model = new DefaultComboBoxModel<>();
        model.addElement(new UsuarioDTO("Diego", "Fernandez"));
        model.addElement(new UsuarioDTO("Sergio", "Lopez"));
        return model;
    }

    /** Técnico vigente: se selecciona y NO se agrega ningún ítem extra. */
    @Test
    public void tecnicoExistente_seSeleccionaSinAgregarItem() {
        DefaultComboBoxModel<UsuarioDTO> model = modeloConTecnicosVigentes();

        GestorVisualizacionEquipos.resolverSeleccionTecnico(model, "Sergio Lopez");

        assertEquals(2, model.getSize());
        assertSame(model.getElementAt(1), model.getSelectedItem());
        assertEquals("Sergio Lopez", model.getSelectedItem().toString());
    }

    /** Técnico vigente con diferencia de mayúsculas: también se selecciona. */
    @Test
    public void tecnicoExistente_ignoraMayusculas() {
        DefaultComboBoxModel<UsuarioDTO> model = modeloConTecnicosVigentes();

        GestorVisualizacionEquipos.resolverSeleccionTecnico(model, "diego fernandez");

        assertEquals(2, model.getSize());
        assertEquals("Diego Fernandez", model.getSelectedItem().toString());
    }

    /**
     * Técnico ELIMINADO (ya no existe en la tabla usuario): se agrega un ítem
     * con su nombre al combo y queda seleccionado, para no perder el nombre
     * al editar/guardar la reparación.
     */
    @Test
    public void tecnicoEliminado_seConservaElNombreConItemFantasma() {
        DefaultComboBoxModel<UsuarioDTO> model = modeloConTecnicosVigentes();

        GestorVisualizacionEquipos.resolverSeleccionTecnico(model, "Pedro Gomez");

        assertEquals(3, model.getSize());
        UsuarioDTO fantasma = (UsuarioDTO) model.getSelectedItem();
        assertEquals("Pedro Gomez", fantasma.toString());
        assertEquals("Pedro", fantasma.getNombre());
        assertEquals("Gomez", fantasma.getApellido());
    }

    /** Nombre compuesto: se conserva intacto en el ítem fantasma. */
    @Test
    public void tecnicoEliminado_nombreCompuestoSeConservaExacto() {
        DefaultComboBoxModel<UsuarioDTO> model = modeloConTecnicosVigentes();

        GestorVisualizacionEquipos.resolverSeleccionTecnico(model, "María Fernanda Rosa");

        assertEquals(3, model.getSize());
        UsuarioDTO fantasma = (UsuarioDTO) model.getSelectedItem();
        assertEquals("María Fernanda Rosa", fantasma.toString());
        // Convención coherente con obtenerIDporNombre: split en el primer espacio
        assertEquals("María", fantasma.getNombre());
        assertEquals("Fernanda Rosa", fantasma.getApellido());
    }

    /** Sin técnico asignado (vacío): no se agrega nada y no hay selección. */
    @Test
    public void sinTecnico_noSeAgregaItemNiSeleccion() {
        DefaultComboBoxModel<UsuarioDTO> model = modeloConTecnicosVigentes();

        GestorVisualizacionEquipos.resolverSeleccionTecnico(model, "");

        assertEquals(2, model.getSize());
        assertNull(model.getSelectedItem());
    }

    /** Nombre null: mismo comportamiento que vacío, sin lanzar errores. */
    @Test
    public void tecnicoNull_noSeAgregaItemNiSeleccion() {
        DefaultComboBoxModel<UsuarioDTO> model = modeloConTecnicosVigentes();

        GestorVisualizacionEquipos.resolverSeleccionTecnico(model, null);

        assertEquals(2, model.getSize());
        assertNull(model.getSelectedItem());
    }
}

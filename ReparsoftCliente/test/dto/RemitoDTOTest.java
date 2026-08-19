package dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Valida el modelo de datos de remitos.
 */
public class RemitoDTOTest {

    /** El constructor completo inicializa todos los campos. */
    @Test
    public void constructorCompleto_inicializaTodosLosCampos() {
        List<String> descripcion = Arrays.asList("TV", "Monitor");
        RemitoDTO remito = new RemitoDTO(3, 7, 42, 25, descripcion, "Cliente SA",
                "CONFORMADO", 4, "30-11111111-1", "Calle 1");

        assertEquals(Integer.valueOf(3), remito.getIdUbicacion());
        assertEquals(Integer.valueOf(7), remito.getCodigoUbicacion());
        assertEquals(Integer.valueOf(42), remito.getIdRemito());
        assertEquals(Integer.valueOf(25), remito.getNumeroRemitoSalida());
        assertEquals(descripcion, remito.getDescripcion());
        assertEquals("Cliente SA", remito.getCliente());
        assertEquals("CONFORMADO", remito.getRemitoConformado());
        assertEquals(4, remito.getCantBultos());
        assertEquals("30-11111111-1", remito.getCuit());
        assertEquals("Calle 1", remito.getDomicilio());
    }

    /** El constructor reducido (uso en numeración) deja el resto en null. */
    @Test
    public void constructorReducido_dejaCamposRestantesEnNull() {
        RemitoDTO remito = new RemitoDTO(2, 33, 100);
        assertEquals(Integer.valueOf(2), remito.getIdUbicacion());
        assertEquals(Integer.valueOf(33), remito.getNumeroRemitoSalida());
        assertEquals(Integer.valueOf(100), remito.getIdRemito());
        assertNull(remito.getCliente());
        assertNull(remito.getCuit());
        assertNull(remito.getDomicilio());
        assertNull(remito.getCodigoUbicacion());
    }

    /** Los setters actualizan los valores. */
    @Test
    public void setters_actualizanValores() {
        RemitoDTO remito = new RemitoDTO(1, 1, 1);
        remito.setUbicacion("Bariloche");
        remito.setDomicilio("Av. Mitre 123");
        remito.setCuit("27-99999999-9");
        remito.setCantBultos(9);
        remito.setRemitoConformado("NO CONFORMADO");
        remito.setDescripcion(Arrays.asList("Notebook"));
        remito.setCodigoUbicacion(2);
        remito.setNumeroRemitoSalida(88);
        remito.setCliente("Otro Cliente");

        assertEquals("Bariloche", remito.getUbicacion());
        assertEquals("Av. Mitre 123", remito.getDomicilio());
        assertEquals("27-99999999-9", remito.getCuit());
        assertEquals(9, remito.getCantBultos());
        assertEquals("NO CONFORMADO", remito.getRemitoConformado());
        assertEquals(Arrays.asList("Notebook"), remito.getDescripcion());
        assertEquals(Integer.valueOf(2), remito.getCodigoUbicacion());
        assertEquals(Integer.valueOf(88), remito.getNumeroRemitoSalida());
        assertEquals("Otro Cliente", remito.getCliente());
    }
}
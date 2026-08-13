package dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Valida los valores por defecto y el null-safety de los constructores de ClienteDTO.
 */
public class ClienteDTOTest {

    /** El constructor corto fija tipoDocumento=CUIT, condicionIva="" y tipoPersona=empresa. */
    @Test
    public void constructorCorto_aplicaDefaults() {
        ClienteDTO cliente = new ClienteDTO(1, "Cliente SA", "30-11111111-1",
                "Calle 1", "444", "Contacto", "555", "cli@test.com");

        assertEquals("Cliente SA", cliente.getRazon_Social());
        assertEquals("CUIT", cliente.getTipoDocumento());
        assertEquals("", cliente.getCondicionIva());
        assertEquals("empresa", cliente.getTipoPersona());
    }

    /** El constructor largo conserva los valores explícitos. */
    @Test
    public void constructorLargo_conservaValoresExplicitos() {
        ClienteDTO cliente = new ClienteDTO(2, "Otra SA", "20-22222222-2",
                "Calle 2", "666", "Contacto2", "777", "cli2@test.com",
                "DNI", "Responsable Inscripto", "persona fisica");

        assertEquals("DNI", cliente.getTipoDocumento());
        assertEquals("Responsable Inscripto", cliente.getCondicionIva());
        assertEquals("persona fisica", cliente.getTipoPersona());
    }

    /** El constructor largo con valores nulos aplica los mismos defaults. */
    @Test
    public void constructorLargo_conNullAplicaDefaults() {
        ClienteDTO cliente = new ClienteDTO(3, "Tercera SA", "27-33333333-3",
                "Calle 3", "888", "Contacto3", "999", "cli3@test.com",
                null, null, null);

        assertEquals("CUIT", cliente.getTipoDocumento());
        assertEquals("", cliente.getCondicionIva());
        assertEquals("empresa", cliente.getTipoPersona());
    }

    /** toString expone la razón social (usado en combos). */
    @Test
    public void toString_devuelveRazonSocial() {
        ClienteDTO cliente = new ClienteDTO(1, "Razon Social X", "30-1-1",
                "", "", "", "", "");
        assertEquals("Razon Social X", cliente.toString());
    }
}

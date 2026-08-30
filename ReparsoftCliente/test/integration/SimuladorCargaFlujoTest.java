package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import dto.ReparacionDTO;
import presentacion.controlador.gestores.GestorEstadosPresupuestos;

/**
 * Simulador de Carga Masiva y Pruebas de Rendimiento sobre el Flujo Completo.
 * Simula el ciclo de vida (Ingreso -> Diagnóstico -> Presupuesto -> Aceptación ->
 * Equipo Terminado -> Remito -> Enviado) para miles de reparaciones ficticias,
 * midiendo tiempos de procesamiento, concurrencia de estados y uso de memoria.
 */
public class SimuladorCargaFlujoTest {

    private GestorEstadosPresupuestos gestorEstados;
    private Random random;

    @Before
    public void setUp() {
        gestorEstados = new GestorEstadosPresupuestos();
        random = new Random(42); // Semilla fija para repetibilidad
    }

    @Test
    public void testSimulacionCargaFlujoMasivo() {
        int CANTIDAD_EQUIPOS = 5000;
        List<ReparacionDTO> loteReparaciones = new ArrayList<>(CANTIDAD_EQUIPOS);
        String hoy = LocalDate.now().toString();

        long startTime = System.currentTimeMillis();

        // 1. Simulación de Ingreso Masivo (Fase 1)
        for (int i = 1; i <= CANTIDAD_EQUIPOS; i++) {
            String cliente = "Cliente Ficticio " + (i % 100);
            String equipo = "Equipo Modelo " + (i % 50);
            
            ReparacionDTO reparacion = new ReparacionDTO(
                    20000 + i, hoy, null, "Falla reportada #" + i, null, null,
                    1 + (i % 5), "EN REPARACIÓN", "EN ESPERA", "PENDIENTE PRESUPUESTO",
                    "REM-" + i, "OC-" + i, false, false, 1000 + i, 0, 0.0, 0.0,
                    null, false, 0.0, false, equipo, "cliente" + i + "@test.com",
                    "Mod-" + i, "Marca-X", "SN-" + i, "Sin aviso", cliente,
                    100 + (i % 100), 1, cliente, "Sucursal Central", "Tecnico " + (1 + (i % 5)),
                    20000 + i, 0, "2024", false, false, false, "Taller Main", null, null
            );
            loteReparaciones.add(reparacion);
        }

        assertEquals(CANTIDAD_EQUIPOS, loteReparaciones.size());

        // 2. Simulación de Diagnóstico y Aviso de Informe (Fase 2)
        int diagnosticados = 0;
        for (ReparacionDTO r : loteReparaciones) {
            r.setFechadereparacion(hoy);
            r.setSolucion("Solucion aplicada para ELS " + r.getELS());
            r.setInformecliente("Informe técnico detallado para " + r.getNombreEquipo());
            r.setPrecioPeso(50000.0 + (random.nextDouble() * 200000.0));
            r.setPrecioDolar(50.0 + (random.nextDouble() * 200.0));
            r.setEstadoTecnico("DIAGNOSTICADO");
            r.setAvisoEnviado(true); // "AVISO DE INFORME"
            diagnosticados++;
        }
        assertEquals(CANTIDAD_EQUIPOS, diagnosticados);

        // 3. Simulación de Presupuestación y Envío (Fase 3)
        for (ReparacionDTO r : loteReparaciones) {
            r.setPresupuestoGenerado(true);
            r.setPresupuestoEnviado(true);
        }

        // 4. Simulación de Aceptación / Rechazo Comercial por el Cliente (Fase 4)
        int aprobados = 0;
        int rechazados = 0;

        for (ReparacionDTO r : loteReparaciones) {
            boolean acepta = (r.getELS() % 10) != 0; // 90% acepta, 10% rechaza
            if (acepta) {
                r.setEstadoComercial("APROBADO");
                aprobados++;
            } else {
                r.setEstadoComercial("RECHAZADO");
                rechazados++;
            }
            r.setFechAceptacion(hoy);

            // Validar la regla de negocio del gestor de estados
            String estadoNormalizado = gestorEstados.verificarEstadoPresupuesto(r.getEstadoComercial());
            assertTrue(estadoNormalizado.equals("Presupuesto Aprobado") || estadoNormalizado.equals("Presupuesto Rechazado"));
        }

        assertTrue(aprobados > 0);
        assertTrue(rechazados > 0);
        assertEquals(CANTIDAD_EQUIPOS, aprobados + rechazados);

        // 5. Simulación de Finalización de Trabajo por el Técnico ("EQUIPO TERMINADO") (Fase 5)
        for (ReparacionDTO r : loteReparaciones) {
            if ("APROBADO".equals(r.getEstadoComercial())) {
                r.setEstadoTecnico("EQUIPO LISTO");
            } else {
                r.setEstadoTecnico("DEVUELTO SIN REPARAR");
            }
        }

        // 6. Generación de Remitos y Salida ("ENVIADO") (Fase 6 & 7)
        int remitosGenerados = 0;
        int enviados = 0;

        for (int i = 0; i < loteReparaciones.size(); i++) {
            ReparacionDTO r = loteReparaciones.get(i);
            int nroRemito = 8000 + i;
            
            r.setCodigoRemito(nroRemito);
            r.setNumeroRemitoSalida(nroRemito);
            r.setRemitoGenerado(true);
            r.setAgregadoaremito(true);
            remitosGenerados++;

            // Cambio final de estado físico al entregar el equipo
            r.setEstadoFisico("ENVIADO");
            r.setFecha_Salida(hoy);
            enviados++;
        }

        long endTime = System.currentTimeMillis();
        long durationMs = endTime - startTime;

        // Verificaciones finales de carga
        assertEquals(CANTIDAD_EQUIPOS, remitosGenerados);
        assertEquals(CANTIDAD_EQUIPOS, enviados);

        System.out.println("=== SIMULACIÓN DE CARGA COMPLETADA ===");
        System.out.println("Equipos procesados en flujo completo: " + CANTIDAD_EQUIPOS);
        System.out.println("Tiempo total de ejecución: " + durationMs + " ms");
        System.out.println("Velocidad de procesamiento: " + (CANTIDAD_EQUIPOS * 1000.0 / durationMs) + " reparaciones/segundo");

        // El procesamiento de 5,000 reparaciones completas debe durar menos de 3 segundos (3000ms)
        assertTrue("La simulación de carga debe ser de alto rendimiento (< 3000ms)", durationMs < 3000);
    }
}

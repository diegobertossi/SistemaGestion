package presentacion.controlador.gestores;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Test;

/**
 * Valida las reglas de negocio de estados de presupuesto y pago
 * (lógica pura de GestorEstadosPresupuestos, sin dependencias externas).
 */
public class GestorEstadosPresupuestosTest {

    private final GestorEstadosPresupuestos gestor = new GestorEstadosPresupuestos();

    private Date fecha(int diasDesdeHoy) {
        return java.sql.Date.valueOf(LocalDate.now().plusDays(diasDesdeHoy));
    }

    // ---------- verificarEstadoPago ----------

    /** Sin presupuesto o sin pago registrado el estado es "Pago Pendiente". */
    @Test
    public void verificarEstadoPago_conNullDevuelvePendiente() {
        assertEquals("Pago Pendiente", gestor.verificarEstadoPago(null, null));
        assertEquals("Pago Pendiente", gestor.verificarEstadoPago(null, 100.0));
        assertEquals("Pago Pendiente", gestor.verificarEstadoPago(100.0, null));
    }

    /** Pago mayor o igual al presupuesto => "Pagado" (borde: pago == presupuesto). */
    @Test
    public void verificarEstadoPago_pagoIgualOMayorEsPagado() {
        assertEquals("Pagado", gestor.verificarEstadoPago(100.0, 100.0));
        assertEquals("Pagado", gestor.verificarEstadoPago(100.0, 150.0));
    }

    /** Pago parcial mayor a cero => "Pago Parcial". */
    @Test
    public void verificarEstadoPago_pagoParcial() {
        assertEquals("Pago Parcial", gestor.verificarEstadoPago(100.0, 50.0));
        assertEquals("Pago Parcial", gestor.verificarEstadoPago(100.0, 0.01));
    }

    /** Pago en cero => "Pago Pendiente". */
    @Test
    public void verificarEstadoPago_pagoCeroEsPendiente() {
        assertEquals("Pago Pendiente", gestor.verificarEstadoPago(100.0, 0.0));
    }

    // ---------- estaPagado / calcularMontoPendiente / calcularPorcentajePago ----------

    @Test
    public void estaPagado_soloConPagoCompleto() {
        assertTrue(gestor.estaPagado(100.0, 100.0));
        assertFalse(gestor.estaPagado(100.0, 99.99));
        assertFalse(gestor.estaPagado(null, 100.0));
    }

    /** El monto pendiente nunca es negativo. */
    @Test
    public void calcularMontoPendiente_nuncaNegativo() {
        assertEquals(70.0, gestor.calcularMontoPendiente(100.0, 30.0), 0.001);
        assertEquals(0.0, gestor.calcularMontoPendiente(30.0, 100.0), 0.001);
        assertEquals(0.0, gestor.calcularMontoPendiente(null, 30.0), 0.001);
        assertEquals(0.0, gestor.calcularMontoPendiente(30.0, null), 0.001);
    }

    /** El porcentaje de pago se acota al 100% y tolera división por cero. */
    @Test
    public void calcularPorcentajePago_acotadoYsinDivisionPorCero() {
        assertEquals(50.0, gestor.calcularPorcentajePago(100.0, 50.0), 0.001);
        assertEquals(100.0, gestor.calcularPorcentajePago(100.0, 200.0), 0.001);
        assertEquals(100.0, gestor.calcularPorcentajePago(100.0, 100.0), 0.001);
        assertEquals(0.0, gestor.calcularPorcentajePago(null, 50.0), 0.001);
        assertEquals(0.0, gestor.calcularPorcentajePago(0.0, 50.0), 0.001);
        assertEquals(0.0, gestor.calcularPorcentajePago(100.0, null), 0.001);
    }

    // ---------- verificarEstadoPresupuesto ----------

    /** Null o estado desconocido => "Presupuesto Pendiente". */
    @Test
    public void verificarEstadoPresupuesto_nullODesconocidoEsPendiente() {
        assertEquals("Presupuesto Pendiente", gestor.verificarEstadoPresupuesto(null));
        assertEquals("Presupuesto Pendiente", gestor.verificarEstadoPresupuesto("En espera"));
    }

    /** Acepta "Aprobado/Aprobada" y "Rechazado/Rechazada" sin importar mayúsculas. */
    @Test
    public void verificarEstadoPresupuesto_aceptaVariantesYMayusculas() {
        assertEquals("Presupuesto Aprobado", gestor.verificarEstadoPresupuesto("Aprobado"));
        assertEquals("Presupuesto Aprobado", gestor.verificarEstadoPresupuesto("aprobada"));
        assertEquals("Presupuesto Rechazado", gestor.verificarEstadoPresupuesto("Rechazado"));
        assertEquals("Presupuesto Rechazado", gestor.verificarEstadoPresupuesto("RECHAZADA"));
    }

    // ---------- determinarEstadoTecnico ----------

    /** Sin fecha de entrega => "En Reparación". */
    @Test
    public void determinarEstadoTecnico_sinFechaEsEnReparacion() {
        assertEquals("En Reparación", gestor.determinarEstadoTecnico(null));
    }

    /** Fecha futura => en reparación; hoy => pendiente entrega; pasada => entregado. */
    @Test
    public void determinarEstadoTecnico_segunLaFechaDeEntrega() {
        assertEquals("En Reparación", gestor.determinarEstadoTecnico(fecha(1)));
        assertEquals("Pendiente Entrega", gestor.determinarEstadoTecnico(fecha(0)));
        assertEquals("Entregado", gestor.determinarEstadoTecnico(fecha(-1)));
    }

    // ---------- verificarRetraso / calcularDiasRetraso ----------

    @Test
    public void verificarRetraso_soloSiFechaPasada() {
        assertFalse(gestor.verificarRetraso(null));
        assertFalse(gestor.verificarRetraso(fecha(1)));
        assertFalse(gestor.verificarRetraso(fecha(0)));
        assertTrue(gestor.verificarRetraso(fecha(-1)));
    }

    /** Días de retraso se cuentan solo cuando la fecha ya pasó. */
    @Test
    public void calcularDiasRetraso_cuentaSoloFechasPasadas() {
        assertEquals(0, gestor.calcularDiasRetraso(null));
        assertEquals(0, gestor.calcularDiasRetraso(fecha(5)));
        assertEquals(1, gestor.calcularDiasRetraso(fecha(-1)));
        assertEquals(0, gestor.calcularDiasRetraso(fecha(0)));
    }

    // ---------- puedeFacturarse ----------

    /** Facturable solo si es entregable y el presupuesto está aprobado. */
    @Test
    public void puedeFacturarse_requiereEntregableYAprobado() {
        assertTrue(gestor.puedeFacturarse("Entregado", "Presupuesto Aprobado"));
        assertTrue(gestor.puedeFacturarse("Pendiente Entrega", "Presupuesto Aprobado"));
        assertFalse(gestor.puedeFacturarse("En Reparación", "Presupuesto Aprobado"));
        assertFalse(gestor.puedeFacturarse("Entregado", "Presupuesto Pendiente"));
        assertFalse(gestor.puedeFacturarse("Entregado", null));
        assertFalse(gestor.puedeFacturarse(null, "Presupuesto Aprobado"));
    }

    // ---------- requiereAtencionUrgente / obtenerColorAlerta ----------

    /** Urgente solo cuando hay pago pendiente Y retraso. */
    @Test
    public void requiereAtencionUrgente_combinaPagoYRetraso() {
        assertTrue(gestor.requiereAtencionUrgente(100.0, 50.0, fecha(-1)));
        assertFalse(gestor.requiereAtencionUrgente(100.0, 100.0, fecha(-1)));
        assertFalse(gestor.requiereAtencionUrgente(100.0, 50.0, fecha(1)));
    }

    /** Colores de alerta: rojo urgente, naranja retraso, amarillo parcial, verde ok. */
    @Test
    public void obtenerColorAlerta_priorizaUrgencia() {
        assertEquals(new Color(244, 67, 54), gestor.obtenerColorAlerta(100.0, 50.0, fecha(-1)));
        assertEquals(new Color(255, 152, 0), gestor.obtenerColorAlerta(100.0, 100.0, fecha(-1)));
        assertEquals(new Color(255, 193, 7), gestor.obtenerColorAlerta(100.0, 50.0, fecha(1)));
        assertEquals(new Color(76, 175, 80), gestor.obtenerColorAlerta(100.0, 100.0, fecha(1)));
    }

    // ---------- obtenerColorEstadoPago / obtenerColorEstadoPresupuesto / obtenerColorEstadoTecnico ----------

    /** Cada estado de pago tiene su color; desconocido cae en pendiente (rojo). */
    @Test
    public void obtenerColorEstadoPago_mapeaEstados() {
        assertEquals(new Color(76, 175, 80), gestor.obtenerColorEstadoPago("Pagado"));
        assertEquals(new Color(255, 193, 7), gestor.obtenerColorEstadoPago("Pago Parcial"));
        assertEquals(new Color(244, 67, 54), gestor.obtenerColorEstadoPago("Pago Pendiente"));
        assertEquals(new Color(244, 67, 54), gestor.obtenerColorEstadoPago("Estado raro"));
    }

    @Test
    public void obtenerColorEstadoPresupuesto_mapeaEstados() {
        assertEquals(new Color(33, 150, 243), gestor.obtenerColorEstadoPresupuesto("Presupuesto Aprobado"));
        assertEquals(new Color(156, 39, 176), gestor.obtenerColorEstadoPresupuesto("Presupuesto Rechazado"));
        assertEquals(new Color(255, 152, 0), gestor.obtenerColorEstadoPresupuesto("Presupuesto Pendiente"));
        assertEquals(new Color(255, 152, 0), gestor.obtenerColorEstadoPresupuesto("Otro"));
    }

    @Test
    public void obtenerColorEstadoTecnico_mapeaEstados() {
        assertEquals(new Color(76, 175, 80), gestor.obtenerColorEstadoTecnico("En Reparación"));
        assertEquals(new Color(255, 193, 7), gestor.obtenerColorEstadoTecnico("Pendiente Entrega"));
        assertEquals(new Color(103, 58, 183), gestor.obtenerColorEstadoTecnico("Entregado"));
        assertEquals(new Color(76, 175, 80), gestor.obtenerColorEstadoTecnico("Otro"));
    }

    // ---------- generarDescripcionEstado ----------

    /** La descripción incluye estado de pago, estado técnico y, si aplica, retraso. */
    @Test
    public void generarDescripcionEstado_incluyeEstadosYRetraso() {
        String desc = gestor.generarDescripcionEstado(100.0, 50.0, fecha(-1));
        assertTrue(desc.startsWith("Estado de Pago: Pago Parcial\nEstado Técnico: Entregado\n"));
        assertTrue(desc.contains("Monto Pendiente"));
        assertTrue(desc.contains("Retraso: 1 días"));

        String sinRetraso = gestor.generarDescripcionEstado(100.0, 50.0, fecha(1));
        assertTrue(sinRetraso.startsWith("Estado de Pago: Pago Parcial\nEstado Técnico: En Reparación\n"));
        assertFalse(sinRetraso.contains("Retraso"));
    }
}

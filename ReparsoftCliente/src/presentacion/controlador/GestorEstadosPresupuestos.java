package presentacion.controlador;

import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import modelo.Agenda;
import presentacion.vista.VentanaVisualizarEquipos;

/**
 * GestorEstadosPresupuestos
 * Responsable de:
 * - Verificar estados de presupuesto
 * - Determinar estado de pago
 * - Calcular montos pendientes
 * - Aplicar estilos visuales
 */
public class GestorEstadosPresupuestos {
    
    // Colores
    private static final Color PAGADO = new Color(76, 175, 80);
    private static final Color PARCIAL = new Color(255, 193, 7);
    private static final Color PENDIENTE = new Color(244, 67, 54);
    private static final Color PRESUPUESTO_APROBADO = new Color(33, 150, 243);
    private static final Color PRESUPUESTO_RECHAZADO = new Color(156, 39, 176);
    private static final Color PRESUPUESTO_PENDIENTE = new Color(255, 152, 0);
    private static final Color EN_REPARACION = new Color(76, 175, 80);
    private static final Color PENDIENTE_ENTREGA = new Color(255, 193, 7);
    private static final Color ENTREGADO = new Color(103, 58, 183);
    
    private Agenda agenda;
    
    /**
     * Constructor
     */
    public GestorEstadosPresupuestos() {
    }
    
    /**
     * Verifica estado de pago
     */
    public String verificarEstadoPago(Double presupuesto, Double pago) {
        if (presupuesto == null || pago == null) {
            return "Pago Pendiente";
        }
        
        if (pago >= presupuesto) {
            return "Pagado";
        } else if (pago > 0) {
            return "Pago Parcial";
        } else {
            return "Pago Pendiente";
        }
    }
    
    /**
     * Obtiene color del estado de pago
     */
    public Color obtenerColorEstadoPago(String estado) {
        switch (estado) {
            case "Pagado":
                return PAGADO;
            case "Pago Parcial":
                return PARCIAL;
            case "Pago Pendiente":
                return PENDIENTE;
            default:
                return PENDIENTE;
        }
    }
    
    /**
     * Verifica estado del presupuesto
     */
    public String verificarEstadoPresupuesto(String estado) {
        if (estado == null) {
            return "Presupuesto Pendiente";
        }
        
        if (estado.equalsIgnoreCase("Aprobado") || estado.equalsIgnoreCase("Aprobada")) {
            return "Presupuesto Aprobado";
        } else if (estado.equalsIgnoreCase("Rechazado") || estado.equalsIgnoreCase("Rechazada")) {
            return "Presupuesto Rechazado";
        } else {
            return "Presupuesto Pendiente";
        }
    }
    
    /**
     * Obtiene color del estado de presupuesto
     */
    public Color obtenerColorEstadoPresupuesto(String estado) {
        switch (estado) {
            case "Presupuesto Aprobado":
                return PRESUPUESTO_APROBADO;
            case "Presupuesto Rechazado":
                return PRESUPUESTO_RECHAZADO;
            case "Presupuesto Pendiente":
                return PRESUPUESTO_PENDIENTE;
            default:
                return PRESUPUESTO_PENDIENTE;
        }
    }
    
    /**
     * Determina estado técnico según fecha de entrega
     */
    public String determinarEstadoTecnico(java.util.Date fechaEntrega) {
        if (fechaEntrega == null) {
            return "En Reparación";
        }
        
        LocalDate fecha = new java.sql.Date(fechaEntrega.getTime()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        
        if (hoy.isBefore(fecha)) {
            return "En Reparación";
        } else if (hoy.isEqual(fecha)) {
            return "Pendiente Entrega";
        } else {
            return "Entregado";
        }
    }
    
    /**
     * Obtiene color del estado técnico
     */
    public Color obtenerColorEstadoTecnico(String estado) {
        switch (estado) {
            case "En Reparación":
                return EN_REPARACION;
            case "Pendiente Entrega":
                return PENDIENTE_ENTREGA;
            case "Entregado":
                return ENTREGADO;
            default:
                return EN_REPARACION;
        }
    }
    
    /**
     * Verifica si hay retraso en la reparación
     */
    public boolean verificarRetraso(java.util.Date fechaEntrega) {
        if (fechaEntrega == null) {
            return false;
        }
        
        LocalDate fecha = new java.sql.Date(fechaEntrega.getTime()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        
        return hoy.isAfter(fecha);
    }
    
    /**
     * Calcula días de retraso
     */
    public long calcularDiasRetraso(java.util.Date fechaEntrega) {
        if (!verificarRetraso(fechaEntrega)) {
            return 0;
        }
        
        LocalDate fecha = new java.sql.Date(fechaEntrega.getTime()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        
        return ChronoUnit.DAYS.between(fecha, hoy);
    }
    
    /**
     * Verifica si está pagado completamente
     */
    public boolean estaPagado(Double presupuesto, Double pago) {
        String estado = verificarEstadoPago(presupuesto, pago);
        return "Pagado".equals(estado);
    }
    
    /**
     * Calcula monto pendiente
     */
    public Double calcularMontoPendiente(Double presupuesto, Double pago) {
        if (presupuesto == null || pago == null) {
            return 0.0;
        }
        
        Double pendiente = presupuesto - pago;
        return pendiente > 0 ? pendiente : 0.0;
    }
    
    /**
     * Calcula porcentaje de pago
     */
    public Double calcularPorcentajePago(Double presupuesto, Double pago) {
        if (presupuesto == null || presupuesto <= 0) {
            return 0.0;
        }
        
        if (pago == null) {
            return 0.0;
        }
        
        Double porcentaje = (pago / presupuesto) * 100;
        return Math.min(porcentaje, 100.0);
    }
    
    /**
     * Genera descripción del estado
     */
    public String generarDescripcionEstado(Double presupuesto, Double pago, 
                                          java.util.Date fechaEntrega) {
        StringBuilder descripcion = new StringBuilder();
        
        String estadoPago = verificarEstadoPago(presupuesto, pago);
        String estadoTecnico = determinarEstadoTecnico(fechaEntrega);
        
        descripcion.append("Estado de Pago: ").append(estadoPago).append("\n");
        descripcion.append("Estado Técnico: ").append(estadoTecnico).append("\n");
        
        Double montoPendiente = calcularMontoPendiente(presupuesto, pago);
        if (montoPendiente > 0) {
            descripcion.append(String.format("Monto Pendiente: $%.2f\n", montoPendiente));
        }
        
        if (verificarRetraso(fechaEntrega)) {
            long diasRetraso = calcularDiasRetraso(fechaEntrega);
            descripcion.append(String.format("Retraso: %d días\n", diasRetraso));
        }
        
        return descripcion.toString();
    }
    
    /**
     * Verifica si requiere atención urgente
     */
    public boolean requiereAtencionUrgente(Double presupuesto, Double pago, 
                                          java.util.Date fechaEntrega) {
        boolean pagoPendiente = !estaPagado(presupuesto, pago);
        boolean tieneRetraso = verificarRetraso(fechaEntrega);
        
        return pagoPendiente && tieneRetraso;
    }
    
    /**
     * Obtiene color de alerta general
     */
    public Color obtenerColorAlerta(Double presupuesto, Double pago, 
                                    java.util.Date fechaEntrega) {
        boolean pagoPendiente = !estaPagado(presupuesto, pago);
        boolean tieneRetraso = verificarRetraso(fechaEntrega);
        
        if (pagoPendiente && tieneRetraso) {
            return PENDIENTE;
        } else if (tieneRetraso) {
            return PRESUPUESTO_PENDIENTE;
        } else if (pagoPendiente) {
            return PARCIAL;
        } else {
            return PAGADO;
        }
    }
    
    /**
     * Puede facturarse
     */
    public boolean puedeFacturarse(String estadoTecnico, String estadoPresupuesto) {
        boolean entregable = "Pendiente Entrega".equals(estadoTecnico) || 
                            "Entregado".equals(estadoTecnico);
        boolean presupuestoAprobado = "Presupuesto Aprobado".equals(estadoPresupuesto);
        
        return entregable && presupuestoAprobado;
    }
}

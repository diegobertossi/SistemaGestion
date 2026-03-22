package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.vista.VentanaGenerarPresupuesto;
import presentacion.vista.VentanaHistorialPrecios;
import tiposPropios.MonedaFormatter;

/**
 * ControladorHistorialPrecios
 *
 * Coordina la VentanaHistorialPrecios con el modelo (Agenda).
 * Recibe los datos de contexto desde VentanaGenerarPresupuesto para
 * pre-cargar el campo de búsqueda según el equipo que se está presupuestando.
 *
 * Responsabilidades actuales:
 *  - Pre-cargar el campo "buscar" según el radio button seleccionado
 *  - Actualizar dinámicamente ese campo al cambiar de radio button
 *  - Ejecutar la búsqueda al pulsar BUSCAR (stub hasta que se defina la query)
 *  - Limpiar filtros y tabla al pulsar LIMPIAR
 *  - Mostrar detalle al seleccionar una fila
 *  - Copiar precios a VentanaGenerarPresupuesto al pulsar "USAR ESTOS PRECIOS"
 *  - Cerrar la ventana al pulsar CERRAR
 */
public class ControladorHistorialPrecios implements ActionListener, ItemListener{

    // ===== REFERENCIAS =====
    private VentanaHistorialPrecios ventanaHistorialPrecios;
    private VentanaGenerarPresupuesto ventanaGenerarPresupuesto;
    private Agenda agenda;

    // ===== DATOS DE CONTEXTO (vienen de la ventana de presupuesto) =====
    private String nombreEquipoContexto;
    private String modeloContexto;
    private String marcaContexto;
    private String clienteContexto;

    // ===== RESULTADOS EN TABLA =====
    // Lista que se llenará cuando esté disponible la query.
    // Por ahora se declara para no romper la estructura futura.
    // private List<HistorialPrecioDTO> resultadosEnTabla;

    // ===== CONSTRUCTOR =====
    /**
     * @param ventanaHistorialPrecios   La vista que este controlador maneja.
     * @param ventanaGenerarPresupuesto La ventana de origen, para leer contexto
     *                                  y devolver los precios seleccionados.
     * @param agenda                    El modelo de negocio.
     */
    public ControladorHistorialPrecios(
            VentanaHistorialPrecios ventanaHistorialPrecios,
            VentanaGenerarPresupuesto ventanaGenerarPresupuesto,
            Agenda agenda) {

        this.ventanaHistorialPrecios   = ventanaHistorialPrecios;
        this.ventanaGenerarPresupuesto = ventanaGenerarPresupuesto;
        this.agenda                    = agenda;

        // Capturar datos de contexto desde la ventana de presupuesto
        this.nombreEquipoContexto = ventanaGenerarPresupuesto.getTextEquipo().getText().trim();
        this.modeloContexto       = ventanaGenerarPresupuesto.getTextModelo().getText().trim();
        this.marcaContexto        = ventanaGenerarPresupuesto.getTextMarca().getText().trim();
        this.clienteContexto      = ventanaGenerarPresupuesto.getTextCliente().getText().trim();

        // Registrar listeners
        registrarListeners();

        // Pre-cargar el campo buscar con el valor correspondiente al radio
        // button que esté seleccionado por defecto (Nombre de Equipo)
        actualizarCampoBuscar();
    }

    // ═══════════════════════════════════════════════════════
    //  REGISTRO DE LISTENERS
    // ═══════════════════════════════════════════════════════

    private void registrarListeners() {

        // Botones
        ventanaHistorialPrecios.getBtnBuscar()      .addActionListener(this);
        ventanaHistorialPrecios.getBtnLimpiar()     .addActionListener(this);
        ventanaHistorialPrecios.getBtnUsarPrecios() .addActionListener(this);
        ventanaHistorialPrecios.getBtnCerrar()      .addActionListener(this);

        // Radio buttons — cambio dinámico del campo buscar
        ventanaHistorialPrecios.getRdbNombreEquipo().addItemListener(this);
        ventanaHistorialPrecios.getRdbMarca()       .addItemListener(this);
        ventanaHistorialPrecios.getRdbModelo()      .addItemListener(this);

        // Tabla — clic en fila para cargar detalle
        //ventanaHistorialPrecios.getTablaHistorial().addMouseListener(this);
        
        ventanaHistorialPrecios.getTablaHistorial().getSelectionModel()
        .addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDetalleFilaSeleccionada();
            }
        });
        
    }

    // ═══════════════════════════════════════════════════════
    //  ACTION LISTENER
    // ═══════════════════════════════════════════════════════

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ventanaHistorialPrecios.getBtnBuscar()) {
            ejecutarBusqueda();

        } else if (e.getSource() == ventanaHistorialPrecios.getBtnLimpiar()) {
            limpiarFiltrosYTabla();

        } else if (e.getSource() == ventanaHistorialPrecios.getBtnUsarPrecios()) {
            usarPreciosSeleccionados();

        } else if (e.getSource() == ventanaHistorialPrecios.getBtnCerrar()) {
            cerrar();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  ITEM LISTENER  (radio buttons)
    // ═══════════════════════════════════════════════════════

    /**
     * Se dispara cada vez que un radio button cambia de estado.
     * Solo reaccionamos cuando el nuevo estado es SELECTED para
     * no procesar el evento dos veces (deselect + select).
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            actualizarCampoBuscar();
        }
    }


    // ═══════════════════════════════════════════════════════
    //  LÓGICA PRINCIPAL
    // ═══════════════════════════════════════════════════════

    /**
     * Actualiza el campo de texto "buscar" según el radio button activo,
     * tomando como valor por defecto el dato de contexto correspondiente.
     * Se llama al iniciar y cada vez que el usuario cambia de radio button.
     */
    private void actualizarCampoBuscar() {

        String valorParaBuscar = "";

        if (ventanaHistorialPrecios.getRdbNombreEquipo().isSelected()) {
            valorParaBuscar = nombreEquipoContexto;

        } else if (ventanaHistorialPrecios.getRdbMarca().isSelected()) {
           
            valorParaBuscar = marcaContexto;

        } else if (ventanaHistorialPrecios.getRdbModelo().isSelected()) {
            valorParaBuscar = modeloContexto;
        }

        ventanaHistorialPrecios.getTxtBuscar().setText(valorParaBuscar);

        // Limpiar tabla y detalle al cambiar criterio para evitar
        // resultados desactualizados visibles en pantalla.
        limpiarTabla();
        ventanaHistorialPrecios.limpiarDetalle();
    }

    /**
     * Ejecuta la búsqueda según el criterio y el texto ingresados.
     *
     * PENDIENTE: cuando se defina la query y el DAO correspondiente,
     * reemplazar el bloque marcado con la llamada real a Agenda.
     */
    private void ejecutarBusqueda() {

        String textoBusqueda = ventanaHistorialPrecios.getTxtBuscar().getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(
                ventanaHistorialPrecios,
                "Ingrese un texto para buscar.",
                "Campo vacío",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Determinar el criterio activo
        String criterio = obtenerCriterioActivo();

        // Limpiar tabla antes de cargar nuevos resultados
        limpiarTabla();
        ventanaHistorialPrecios.limpiarDetalle();

        List<ReparacionDTO> resultados = agenda.buscarHistorialPrecios(criterio, textoBusqueda);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(
                ventanaHistorialPrecios,
                "No se encontraron registros para la búsqueda realizada.",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        cargarResultadosEnTabla(resultados);
//        JOptionPane.showMessageDialog(
//            ventanaHistorialPrecios,
//            "Búsqueda pendiente de implementación.\n"
//                + "Criterio: " + criterio + "\n"
//                + "Texto: "    + textoBusqueda,
//            "En desarrollo",
//            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Retorna una descripción del criterio de filtro activo.
     * Se usa tanto para la búsqueda como para mensajes al usuario.
     */
    private String obtenerCriterioActivo() {
        if (ventanaHistorialPrecios.getRdbNombreEquipo().isSelected()) {
            return "NOMBRE_EQUIPO";
        } else if (ventanaHistorialPrecios.getRdbMarca().isSelected()) {
            return "MARCA";
        } else if (ventanaHistorialPrecios.getRdbModelo().isSelected()) {
            return "MODELO";
        }
        return "NOMBRE_EQUIPO"; // valor por defecto defensivo
    }

    private void cargarResultadosEnTabla(List<ReparacionDTO> resultados) {
        for (ReparacionDTO item : resultados) {
            ventanaHistorialPrecios.getModelHistorial().addRow(new Object[] {
                item.getELS(),
                item.getNombreEquipo(),
                item.getMarca(),
                item.getModelo(),
                item.getFechadereparacion(),   // contiene la fecha ya formateada dd/MM/yyyy
                item.getPrecioPeso(),
                item.getPrecioDolar()
            });
        }
        ventanaHistorialPrecios.setCellRender(ventanaHistorialPrecios.getTablaHistorial());
    }

    /**
     * Lee la fila seleccionada en la tabla y carga sus valores
     * en el panel de detalle de la ventana.
     */
    private void cargarDetalleFilaSeleccionada() {
        int fila = ventanaHistorialPrecios.getTablaHistorial().getSelectedRow();
        MonedaFormatter monedaFormatter = new MonedaFormatter();

        if (fila < 0) {
            return; // clic fuera de una fila válida
        }

        // Leer valores directamente del modelo de la tabla.
        // El orden de columnas corresponde al array nombreColumnas:
        // 0=ELS, 1=EQUIPO, 2=MARCA, 3=MODELO, 4=FECHA, 5=PRECIO$, 6=PRECIO U$S
        Object els        = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 0);
        Object equipo     = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 1);
        Object marca      = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 2);
        Object modelo     = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 3);
        Object fecha      = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 4);
        Object precioPeso = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 5);
        Object precioDol  = ventanaHistorialPrecios.getModelHistorial().getValueAt(fila, 6);

        ventanaHistorialPrecios.setTxtELS        (els        != null ? els.toString()        : "");
        ventanaHistorialPrecios.setTxtEquipo     (equipo     != null ? equipo.toString()     : "");
        ventanaHistorialPrecios.setTxtMarca      (marca      != null ? marca.toString()      : "");
        ventanaHistorialPrecios.setTxtModelo     (modelo     != null ? modelo.toString()     : "");
        ventanaHistorialPrecios.setTxtFecha      (fecha      != null ? fecha.toString()      : "");
        ventanaHistorialPrecios.setTxtPrecioPeso (precioPeso != null ? monedaFormatter.formatPeso(precioPeso.toString()) : "");
        ventanaHistorialPrecios.setTxtPrecioDolar(precioDol  != null ? monedaFormatter.formatDolar(precioDol.toString()) : "");
    }

    /**
     * Copia los precios del detalle seleccionado a los campos de precio
     * de la VentanaGenerarPresupuesto y luego cierra esta ventana.
     */
    private void usarPreciosSeleccionados() {

        String precioPeso = ventanaHistorialPrecios.getTxtPrecioPeso().getText().trim();
        String precioDolar = ventanaHistorialPrecios.getTxtPrecioDolar().getText().trim();

        if (precioPeso.isEmpty() && precioDolar.isEmpty()) {
            JOptionPane.showMessageDialog(
                ventanaHistorialPrecios,
                "Primero seleccioná un registro de la tabla.",
                "Sin selección",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            ventanaHistorialPrecios,
            "¿Deseás usar estos precios en el presupuesto actual?\n\n"
                + "Precio en PESOS:    " + precioPeso  + "\n"
                + "Precio en DÓLARES: " + precioDolar,
            "Confirmar",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {

            // Copiar a la ventana de presupuesto
            // Los campos de VentanaGenerarPresupuesto esperan el texto tal
            // como viene; el formateador de moneda se aplicará al guardar.
            if (!precioPeso.isEmpty()) {
                ventanaGenerarPresupuesto.getTextPrecioPeso().setText(precioPeso);
            }
            if (!precioDolar.isEmpty()) {
                ventanaGenerarPresupuesto.getTextPrecioDolar().setText(precioDolar);
            }

            cerrar();
        }
    }

    /**
     * Limpia solo la tabla (sin tocar los filtros ni el detalle).
     */
    private void limpiarTabla() {
        ventanaHistorialPrecios.getModelHistorial().setRowCount(0);
    }

    /**
     * Limpia filtros, tabla y detalle, y restituye el valor de contexto
     * en el campo buscar según el radio button activo.
     */
    private void limpiarFiltrosYTabla() {
        actualizarCampoBuscar(); // restaura el texto del campo buscar
        limpiarTabla();
        ventanaHistorialPrecios.limpiarDetalle();
    }

    /**
     * Cierra la ventana de historial.
     */
    private void cerrar() {
        ventanaHistorialPrecios.dispose();
        ventanaHistorialPrecios = null;
    }

    // ═══════════════════════════════════════════════════════
    //  GETTERS  (por si ControladorPresupuestos los necesita)
    // ═══════════════════════════════════════════════════════

    public VentanaHistorialPrecios getVentanaHistorialPrecios() {
        return ventanaHistorialPrecios;
    }
}

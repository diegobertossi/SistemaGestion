package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaAgregarEquipo;
import presentacion.vista.VentanaVerificarIngresoAnterior;

/**
 * GestorVerificacionIngresoAnterior
 * Responsable de:
 * - Abrir ventana de verificación de ingreso anterior
 * - Buscar equipos por ELS o serie
 * - Calcular días desde ingreso anterior
 * - Verificar período de garantía
 */
public class GestorVerificacionIngresoAnterior {
    
    private ControladorReparacion controlador;
    private Agenda agenda;
    private VentanaAgregarEquipo ventanaAgregarEquipo;
    private VentanaVerificarIngresoAnterior ventanaVerificar;
    
    private ReparacionDTO reparacionEncontrada;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    /**
     * Constructor
     */
    public GestorVerificacionIngresoAnterior(ControladorReparacion controlador,
                                             Agenda agenda,
                                             VentanaAgregarEquipo ventanaAgregarEquipo) {
        this.controlador = controlador;
        this.agenda = agenda;
        this.ventanaAgregarEquipo = ventanaAgregarEquipo;
    }
    
    /**
     * Muestra la ventana de verificación
     */
    public void mostrar() {
        ventanaVerificar = new VentanaVerificarIngresoAnterior(controlador);
        
        // Listeners
        ventanaVerificar.getBtnPorels().addActionListener(e -> mostrarPorELS());
        ventanaVerificar.getBtnPorSerie().addActionListener(e -> mostrarPorSerie());
        ventanaVerificar.getBtnVerificar().addActionListener(e -> verificar());
        ventanaVerificar.getBtnSI().addActionListener(e -> procesarSI());
        ventanaVerificar.getBtnNO().addActionListener(e -> cerrar());
        
        // Llenar combos
        llenarComboELS();
        llenarComboSeries();
        
        ventanaVerificar.setVisible(true);
    }
    
    /**
     * Muestra búsqueda por ELS
     */
    private void mostrarPorELS() {
        ventanaVerificar.getComboFiltroELS().setVisible(true);
        ventanaVerificar.getComboFiltroELS().setSelectedIndex(-1);
        ventanaVerificar.getComboSerie().setVisible(false);
    }
    
    /**
     * Muestra búsqueda por Serie
     */
    private void mostrarPorSerie() {
        ventanaVerificar.getComboFiltroELS().setVisible(false);
        ventanaVerificar.getComboSerie().setVisible(true);
        ventanaVerificar.getComboSerie().setSelectedIndex(-1);
    }
    
    /**
     * Llena combo de ELS
     */
    private void llenarComboELS() {
        agenda.ListarELS(ventanaVerificar.getComboFiltroELS());
    }
    
    /**
     * Llena combo de series
     */
    private void llenarComboSeries() {
        agenda.ListarSerie(ventanaVerificar.getComboSerie());
    }
    
    /**
     * Verifica el ingreso anterior
     */
    private void verificar() {
        Object elsSelected = ventanaVerificar.getComboFiltroELS().getSelectedItem();
        Object serieSelected = ventanaVerificar.getComboSerie().getSelectedItem();
        
        if (elsSelected == null && serieSelected == null) {
            JOptionPane.showMessageDialog(null, "Seleccione ELS o Serie", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (elsSelected != null) {
            int els = Integer.parseInt(elsSelected.toString());
            reparacionEncontrada = agenda.dameReparacionXels(els);
        } else if (serieSelected != null) {
            String serie = serieSelected.toString();
            reparacionEncontrada = agenda.dameReparacionXserie(serie);
        }
        
        if (reparacionEncontrada == null) {
            limpiarCampos();
            ventanaVerificar.getBtnSI().setEnabled(false);
            ventanaVerificar.getBtnNO().setEnabled(false);
            JOptionPane.showMessageDialog(null, "No se encontró ningún equipo.", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Cargar datos encontrados
        ventanaVerificar.getTextELS().setText(String.valueOf(reparacionEncontrada.getELS()));
        ventanaVerificar.getTextAviso().setText(reparacionEncontrada.getAviso());
        ventanaVerificar.getTextCliente().setText(reparacionEncontrada.getCliente());
        ventanaVerificar.getTextSucursal().setText(reparacionEncontrada.getSucursal());
        ventanaVerificar.getTextEquipo().setText(reparacionEncontrada.getNombreEquipo());
        ventanaVerificar.getTextMarca().setText(reparacionEncontrada.getMarca());
        ventanaVerificar.getTextModelo().setText(reparacionEncontrada.getModelo());
        ventanaVerificar.getTextSerie().setText(reparacionEncontrada.getNumeroDeSerie());
        
        // Parsear fechas
        try {
            if (reparacionEncontrada.getFechaFabr() != null) {
                ventanaVerificar.setFechaFabr2(dateFormat.parse(reparacionEncontrada.getFechaFabr()));
            }
            
            if (reparacionEncontrada.getFecha_Entrada() != null) {
                ventanaVerificar.setFechaIngresoAnterior(
                    dateFormat.parse(reparacionEncontrada.getFecha_Entrada()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        // Calcular días
        calcularDiasYMostrarNota();
        
        ventanaVerificar.getBtnSI().setEnabled(true);
        ventanaVerificar.getBtnNO().setEnabled(true);
    }
    
    /**
     * Calcula días desde ingreso anterior y muestra nota
     */
    private void calcularDiasYMostrarNota() {
        java.util.Date fechaEntrada = ventanaVerificar.getTextFechaIngreso().getDate();
        
        if (fechaEntrada == null) return;
        
        LocalDate fechaAnterior = new java.sql.Date(fechaEntrada.getTime()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(fechaAnterior, hoy);
        
        ventanaVerificar.getTextPasaron().setText(String.valueOf(dias));
        
        String nota;
        if (dias <= 30) {
            nota = "EL EQUIPO NO DEBERÁ INGRESARSE NUEVAMENTE YA QUE HAN PASADO MENOS DE 30 DÍAS DESDE SU INGRESO ANTERIOR.";
        } else if (dias <= 90) {
            nota = "EL EQUIPO SE ENCUENTRA EN PERÍODO DE GARANTÍA. VERIFICAR SI CORRESPONDE.";
        } else {
            nota = "EL EQUIPO NO SE ENCUENTRA DENTRO DE LOS 90 DÍAS DE GARANTÍA.";
        }
        
        ventanaVerificar.getTextNota().setText(nota);
    }
    
    /**
     * Procesa selección SI
     */
    private void procesarSI() {
        if (reparacionEncontrada == null) return;
        
        // Habilitar campos en ventana de agregar equipo
        ventanaAgregarEquipo.getComboClientes().setEditable(true);
        ventanaAgregarEquipo.getComboSucursal().setEditable(true);
        ventanaAgregarEquipo.getComboNombreEquipo().setEditable(true);
        ventanaAgregarEquipo.getComboSerie().setEditable(true);
        ventanaAgregarEquipo.getComboMarca().setEditable(true);
        ventanaAgregarEquipo.getComboModelo().setEditable(true);
        ventanaAgregarEquipo.getTextFechafabricacion().setEditable(true);
        ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(true);
        
        // Resetear índices
        ventanaAgregarEquipo.getComboClientes().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboNombreEquipo().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboSerie().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboMarca().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboModelo().setSelectedIndex(-1);
        
        // Setear datos del equipo encontrado
        ventanaAgregarEquipo.getComboNombreEquipo().setSelectedItem(reparacionEncontrada.getNombreEquipo());
        
        if (!reparacionEncontrada.getMarca().isEmpty()) {
            ventanaAgregarEquipo.getComboMarca().setSelectedItem(reparacionEncontrada.getMarca());
        }
        
        if (!reparacionEncontrada.getModelo().isEmpty()) {
            ventanaAgregarEquipo.getComboModelo().setSelectedItem(reparacionEncontrada.getModelo());
        }
        
        ventanaAgregarEquipo.getComboSerie().setSelectedItem(reparacionEncontrada.getNumeroDeSerie());
        
        // Agregar nota en falla
        String nota = "ELS ANT: " + reparacionEncontrada.getELS();
        if (!reparacionEncontrada.getAviso().isEmpty()) {
            nota += " - AVISO ANT: " + reparacionEncontrada.getAviso();
        }
        ventanaAgregarEquipo.getTextFalla().setText(nota);
        
        // Setear fecha de fabricación
        if (reparacionEncontrada.getFechaFabr() != null) {
            try {
                ventanaAgregarEquipo.setTextFechafabricacion2(
                    dateFormat.parse(reparacionEncontrada.getFechaFabr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        // Setear cliente (debe hacerse al final para cargar sucursales)
        String sucursalASetear = reparacionEncontrada.getSucursal();
        ventanaAgregarEquipo.getComboClientes().setSelectedItem(reparacionEncontrada.getCliente());
        
        // Setear sucursal después de que se carguen (usar invokeLater)
        if (!sucursalASetear.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                ventanaAgregarEquipo.getComboSucursal().setSelectedItem(sucursalASetear);
            });
        }
        
        cerrar();
    }
    
    /**
     * Limpia todos los campos
     */
    private void limpiarCampos() {
        ventanaVerificar.getTextELS().setText("");
        ventanaVerificar.getTextAviso().setText("");
        ventanaVerificar.getTextCliente().setText("");
        ventanaVerificar.getTextSucursal().setText("");
        ventanaVerificar.getTextEquipo().setText("");
        ventanaVerificar.getTextMarca().setText("");
        ventanaVerificar.getTextModelo().setText("");
        ventanaVerificar.getTextSerie().setText("");
        ventanaVerificar.setFechaFabr2(null);
        ventanaVerificar.setFechaIngresoAnterior(null);
        ventanaVerificar.getTextPasaron().setText("");
        ventanaVerificar.getTextNota().setText("");
    }
    
    /**
     * Cierra ventana
     */
    private void cerrar() {
        if (ventanaVerificar != null) {
            ventanaVerificar.dispose();
            ventanaVerificar = null;
        }
    }
    
    /**
     * Getters
     */
    public ReparacionDTO getReparacionEncontrada() {
        return reparacionEncontrada;
    }
}

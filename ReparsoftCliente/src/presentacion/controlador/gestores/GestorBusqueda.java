package presentacion.controlador.gestores;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaProgreso;
import persistencia.dao.mysql.LogDAO;
import presentacion.vista.VentanaBusquedaEquipo;
import presentacion.vista.VentanaVisualizarEquipos;

/**
 * GestorBusqueda
 * Responsable de:
 * - Abrir ventana de búsqueda
 * - Realizar búsquedas por campo
 * - Mostrar resultados con enlaces clicables
 * - Navegar a equipos desde resultados
 */
public class GestorBusqueda {
    
    private ControladorReparacion controlador;
    private Agenda agenda;
    private VentanaBusquedaEquipo ventanaBusqueda;
    
    /**
     * Constructor
     */
    public GestorBusqueda(ControladorReparacion controlador, Agenda agenda) {
        this.controlador = controlador;
        this.agenda = agenda;
    }
    
    /**
     * Abre ventana de búsqueda
     */
    public void abrirVentanaBusqueda() {
        ventanaBusqueda = new VentanaBusquedaEquipo(controlador);
        
        // Listeners
        ventanaBusqueda.btnBuscar.addActionListener(e -> realizarBusqueda());
        
        ventanaBusqueda.textPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                actualizarCursor(e);
            }
        });
        
        ventanaBusqueda.textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClickEnResultado(e);
            }
        });
        
        ventanaBusqueda.setVisible(true);
    }
    
    /**
     * Realiza búsqueda en campos. La consulta corre en segundo plano para no
     * bloquear la EDT (búsqueda por texto con LIKE sobre columnas grandes).
     */
    private void realizarBusqueda() {
        ventanaBusqueda.getTextPane().setText("");

        String campoBusqueda = ventanaBusqueda.getComboBuscador().getSelectedItem().toString();

        // Mapear nombres de campo a columnas de BD
        switch (campoBusqueda) {
            case "Falla":
                campoBusqueda = "Falla";
                break;
            case "Diagnóstico":
                campoBusqueda = "Solucion";
                break;
            case "Informe Cliente":
                campoBusqueda = "Informecliente";
                break;
        }

        final String textoBusqueda = ventanaBusqueda.getTextField().getText();
        final String campoFinal = campoBusqueda;

        ventanaBusqueda.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new javax.swing.SwingWorker<List<Integer>, Void>() {
            @Override
            protected List<Integer> doInBackground() throws Exception {
                return agenda.buscarEnCampos(campoFinal, textoBusqueda);
            }

            @Override
            protected void done() {
                ventanaBusqueda.setCursor(Cursor.getDefaultCursor());
                try {
                    mostrarResultados(get());
                } catch (Exception ex) {
                    LogDAO.error("Error en búsqueda por " + campoFinal + " '" + textoBusqueda + "'", ex);
                }
            }
        }.execute();
    }
    
    /**
     * Muestra resultados en el text pane
     */
    private void mostrarResultados(List<Integer> resultados) {
        StyledDocument doc = ventanaBusqueda.textPane.getStyledDocument();
        Style styleEnlace = ventanaBusqueda.textPane.addStyle("enlace", null);
        
        StyleConstants.setForeground(styleEnlace, Color.BLUE);
        StyleConstants.setBold(styleEnlace, true);
        StyleConstants.setUnderline(styleEnlace, true);
        
        try {
            for (Integer resultado : resultados) {
                doc.insertString(doc.getLength(), resultado + "\n", styleEnlace);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Actualiza cursor al pasar sobre enlaces
     */
    private void actualizarCursor(MouseEvent e) {
        if (ventanaBusqueda == null) return;
        
        java.awt.Point pt = e.getPoint();
        int pos = ventanaBusqueda.textPane.viewToModel(pt);
        StyledDocument doc = ventanaBusqueda.textPane.getStyledDocument();
        
        if (pos >= 0 && pos < doc.getLength()) {
            Element elem = doc.getCharacterElement(pos);
            AttributeSet as = elem.getAttributes();
            
            if (StyleConstants.isBold(as) && StyleConstants.getForeground(as).equals(Color.BLUE)) {
                ventanaBusqueda.textPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                ventanaBusqueda.textPane.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    /**
     * Maneja clic en resultado
     */
    private void manejarClickEnResultado(MouseEvent e) {
        java.awt.Point pt = e.getPoint();
        int pos = ventanaBusqueda.textPane.viewToModel(pt);
        StyledDocument doc = ventanaBusqueda.textPane.getStyledDocument();
        
        try {
            Element elem = doc.getCharacterElement(pos);
            AttributeSet as = elem.getAttributes();
            
            if (StyleConstants.isBold(as) && StyleConstants.getForeground(as).equals(Color.BLUE)) {
                int start = elem.getStartOffset();
                int end = elem.getEndOffset();
                String numeroELS = doc.getText(start, end - start).trim();
                
                try {
                    int els = Integer.parseInt(numeroELS);
                    navegarAEquipo(els);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Navega a equipo específico
     */
    private void navegarAEquipo(int els) {
        try {
            VentanaVisualizarEquipos ventana = controlador.getVentanaVisualizarEquipos();
            if (ventana == null) {
                ventana = new VentanaVisualizarEquipos(controlador);
                controlador.setVentanaVisualizarEquipos(ventana);
            }
            
            ventana.setTextELS(Integer.toString(els));
            controlador.getGestorVisualizacion().cargarDatosEquipoAsync(ventana, els);


        } catch (Exception ex) {
            LogDAO.error("Error al navegar al equipo ELS " + els, ex);
        }
    }
    
    /**
     * Procesa eventos delegados
     */
    public void procesarEventos(ActionEvent e) {
        if (e.getSource() == controlador.getVentanaVisualizarEquipos().getBtnBuscar()) {
            abrirVentanaBusqueda();
        }
    }
    
    /**
     * Getters
     */
    public VentanaBusquedaEquipo getVentanaBusqueda() {
        return ventanaBusqueda;
    }
}

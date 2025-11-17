package presentacion.controlador.gestores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;

import dto.ReparacionDTO;
import dto.RegistroEntradaReporteDTO;
import modelo.Agenda;
import presentacion.vista.VentanaAgregarEquipo;
import presentacion.vista.VentanaBusquedaEquipo;
import presentacion.vista.VentanaVisualizarEquipos;
import tiposPropios.MonedaFormatter;

/**
 * GestorDatos
 * Responsable de:
 * - Extraer datos de pantallas
 * - Convertir a DTOs
 * - Validar caracteres especiales
 * - Parsear valores monetarios
 */
public class GestorDatos {
    
    private Agenda agenda;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private MonedaFormatter monedaFormatter = new MonedaFormatter();
    private List<String> caracteresNoValidos = new ArrayList<>();
    
    /**
     * Constructor
     */
    public GestorDatos(Agenda agenda) {
        this.agenda = agenda;
    }
    
    /**
     * Extrae datos de visualización en DTO
     */
    public ReparacionDTO extraerDatos(VentanaVisualizarEquipos ventana, ReparacionDTO reparacionActual) {
    	
        int els = Integer.parseInt(ventana.getTextELS());
 
        String falla = ventana.getTextFalla().getText();
        String solucion = ventana.getTextDiagnostico().getText();
        String informe = ventana.getTextInformeCliente().getText();
        String remito = ventana.getTextRemitoCliente().getText();
        String nombreEquipo = ventana.getTextNombreEquipo().getText();
        String modelo = ventana.getTextModelo().getText();
        String marca = ventana.getTextMarca().getText();
        String serie = ventana.getTextNSerie().getText();
        String aviso = ventana.getTextAvisoCliente().getText();
        String clienteCliente = ventana.getTextClienteCliente().getText();
        
        // Validar caracteres
        if (verificarCaracteresPermitidos(falla, solucion, informe, nombreEquipo, modelo, marca, serie)) {
            return null;
        }
        
        // Parsear fechas
        String fechaEntrada = null;
        java.util.Date fechaEntradaVisual = ventana.getFechaEntrada().getDate();
        if (fechaEntradaVisual != null) {
            fechaEntrada = dateFormat.format(fechaEntradaVisual);
        }
        
        String fechaReparacion = null;
        java.util.Date fechaReparacionVisual = ventana.getFechaReparacion().getDate();
        if (fechaReparacionVisual != null) {
            fechaReparacion = dateFormat.format(fechaReparacionVisual);
        }
        
        String fechaAceptacion = null;
        java.util.Date fechaAceptacionVisual = ventana.getFechaRespuesta().getDate();
        if (fechaAceptacionVisual != null) {
            fechaAceptacion = dateFormat.format(fechaAceptacionVisual);
        }
        
        String fechaSalida = null;
        java.util.Date fechaSalidaVisual = ventana.getFechaSalida().getDate();
        if (fechaSalidaVisual != null) {
            fechaSalida = dateFormat.format(fechaSalidaVisual);
        }
        
        String fechaFabr = null;
        java.util.Date fechaFabrVisual = ventana.getFechaFabr().getDate();
        if (fechaFabrVisual != null) {
            fechaFabr = dateFormat.format(fechaFabrVisual);
        }
        
        // Obtener valores monetarios
        double presupuesto = parsearMoneda(ventana.getTextPresupuesto().getText());
        double presupuestoDolar = parsearMoneda(ventana.getTextPresupuestoDolar().getText());
        double pago = parsearMoneda(ventana.getTextPago().getText());
        
        
        
        
        ventana.getTextCliente()
					.setText(ventana.getComboClientes().getSelectedItem().toString());
        ventana.getTextSucursal()
					.setText(ventana.getComboSucursal().getSelectedItem().toString());
        ventana.getTextNombreTecnico()
					.setText(ventana.getComboTecnico().getSelectedItem().toString());
	
        
        // Obtener IDs
        String cliente = ventana.getTextCliente().getText();
        String sucursal = ventana.getTextSucursal().getText();
        String nombreTecnico = ventana.getTextNombreTecnico().getText();
        
        int idCliente = agenda.idClienteporNombre(cliente);
        int idSucursal = agenda.idSucursalporNombre(sucursal, idCliente);
        //int idUsuario = agenda.idUsuarioporNombre(nombreTecnico);
        
        int idUsuario;
		int idUsuarioAux = IDUsuarioPorNombre(nombreTecnico);

		if (idUsuarioAux == 0) {

			idUsuario = 1;
		}

		else {
			idUsuario = idUsuarioAux;

		}
        // Estados
        String estadoFisico = ventana.getTextEstadoFisico().getText();
        String estadoTecnico = ventana.getTextEstadoTecnico().getText();
        String estadoComercial = ventana.getTextEstadoComercial().getText();
        String lugarIngreso = ventana.getTextLugarDeIngreso().getText();
        
        // Checkboxes
        boolean presupuestoGenerado = ventana.getChckPDFGenerado();
        boolean presupuestoEnviado = ventana.getChckPDFEnviado();
        boolean avisoEnviado = ventana.getChckbxAvisoEnviado();
        boolean wordGenerado = ventana.getChckWORDGenerado();
        boolean wordEnviado = ventana.getChckWORDEnviado();
        
        // OC
        String ordenCompra = ventana.getTextOC().getText();
        
        // Remito
        int idRemito = reparacionActual.getidRemito();
        boolean agregadoAremito = reparacionActual.getAgregadoaremito();
        boolean remitoGenerado = reparacionActual.getRemitoGenerado();
        
        return new ReparacionDTO(els, fechaEntrada, fechaReparacion, falla, solucion, informe,
            estadoFisico, estadoTecnico, estadoComercial, remito, reparacionActual.getIDEquipo(), 
            idRemito, cliente, sucursal, fechaAceptacion, nombreEquipo, modelo, marca, serie, aviso, 
            clienteCliente, idCliente, idSucursal, fechaFabr, idUsuario, nombreTecnico, presupuesto, 
            presupuestoDolar, pago, presupuestoGenerado, avisoEnviado, presupuestoEnviado, wordGenerado, 
            wordEnviado, ordenCompra, agregadoAremito, remitoGenerado, lugarIngreso, fechaSalida);
    }
    
    private int IDUsuarioPorNombre(String nombreTecnico) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
     * Extrae datos de agregar equipo
     */
    public ReparacionDTO extraerDatosAgregar(VentanaAgregarEquipo ventana, int idCliente, int idSucursal) {
        int els = Integer.parseInt(ventana.getTextELS());
        String falla = ventana.getTextFalla().getText();
        String remito = ventana.getTextRemitoCliente().getText();
        int idEquipo = agenda.dameIDequipo() + 1;
        String nombreEquipo = ventana.getComboNombreEquipo().getSelectedItem().toString();
        String modelo = ventana.getComboModelo().getSelectedItem().toString();
        String marca = ventana.getComboMarca().getSelectedItem().toString();
        String serie = ventana.getComboSerie().getSelectedItem().toString();
        String aviso = ventana.getTextAvisoCliente().getText();
        String clienteCliente = ventana.getTextClienteCliente().getText();
        
        // Validar caracteres
        if (verificarCaracteresPermitidos(nombreEquipo, falla, modelo, marca, serie)) {
            return null;
        }
        
        // Parsear fecha
        String fechaEntrada = null;
        java.util.Date fecha = ventana.getFechaEntrada().getDate();
        if (fecha != null) {
            fechaEntrada = dateFormat.format(fecha);
        }
        
        String fechaFabr = null;
        java.util.Date fechaFabrObj = ventana.getTextFechafabricacion().getDate();
        if (fechaFabrObj != null) {
            fechaFabr = dateFormat.format(fechaFabrObj);
        }
        
        // Estado físico (lugar de ingreso)
        String estadoFisico = obtenerEstadoFisico(ventana);
        String lugarDeIngreso = estadoFisico;
        
        String estadoTecnico = "Sin Revisar";
        String estadoComercial = "A la Espera de Aceptación";
        String cliente = ventana.getComboClientes().getSelectedItem().toString();
        String sucursal = ventana.getComboSucursal().getSelectedItem().toString();
        int idUsuario = 1;
        
        return new ReparacionDTO(els, fechaEntrada, falla, estadoFisico, estadoTecnico, 
            estadoComercial, remito, idEquipo, idUsuario, nombreEquipo, modelo, marca, serie, 
            aviso, clienteCliente, idCliente, idSucursal, fechaFabr, lugarDeIngreso);
    }
    
    /**
     * Extrae datos para registro de ingreso
     */
    public RegistroEntradaReporteDTO extraerRegistroIngreso(VentanaAgregarEquipo ventana, 
                                                             int idCliente, int idSucursal) {
        int els = Integer.parseInt(ventana.getTextELS());
        String falla = ventana.getTextFalla().getText();
        String remito = ventana.getTextRemitoCliente().getText();
        int idEquipo = agenda.dameIDequipo() + 1;
        String nombreEquipo = ventana.getComboNombreEquipo().getSelectedItem().toString();
        String modelo = ventana.getComboModelo().getSelectedItem().toString();
        String marca = ventana.getComboMarca().getSelectedItem().toString();
        String serie = ventana.getComboSerie().getSelectedItem().toString();
        String aviso = ventana.getTextAvisoCliente().getText();
        String clienteCliente = ventana.getTextClienteCliente().getText();
        String cliente = ventana.getComboClientes().getSelectedItem().toString();
        String sucursal = ventana.getComboSucursal().getSelectedItem().toString();
        
        String fechaEntrada = null;
        java.util.Date fecha = ventana.getFechaEntrada().getDate();
        if (fecha != null) {
            fechaEntrada = dateFormat.format(fecha);
        }
        
        String estadoFisico = obtenerEstadoFisico(ventana);
        String estadoTecnico = "Sin Revisar";
        
        return new RegistroEntradaReporteDTO(els, fechaEntrada, falla, estadoFisico, 
            estadoTecnico, remito, idEquipo, nombreEquipo, modelo, marca, serie, aviso, 
            clienteCliente, idCliente, idSucursal, cliente, sucursal);
    }
    
    /**
     * Obtiene estado físico de radio buttons
     */
    private String obtenerEstadoFisico(VentanaAgregarEquipo ventana) {
        Enumeration<?> elements = ventana.getGrupoEstadoFisico().getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = (AbstractButton) elements.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }
    
    /**
     * Verifica caracteres no permitidos
     */
    private boolean verificarCaracteresPermitidos(String... textos) {
        caracteresNoValidos.clear();
        
        for (String texto : textos) {
            if (texto == null) continue;
            
            if (texto.contains("'")) {
                if (!caracteresNoValidos.contains("'")) {
                    caracteresNoValidos.add("'");
                }
            }
            
            String[] simbolos = {";", "\\", "/*", "*/"};
            for (String simbolo : simbolos) {
                if (texto.contains(simbolo) && !caracteresNoValidos.contains(simbolo)) {
                    caracteresNoValidos.add(simbolo);
                }
            }
        }
        
        return !caracteresNoValidos.isEmpty();
    }
    
    /**
     * Parsea valor monetario
     */
    private double parsearMoneda(String valor) {
        try {
            if (valor == null || valor.isEmpty()) {
                return 0.0;
            }
            
            if (monedaFormatter.tieneFormato(valor)) {
                return monedaFormatter.parseAmountGuardar(valor);
            } else {
                return monedaFormatter.parseAmount(valor);
            }
        } catch (Exception ex) {
            return 0.0;
        }
    }
    
    /**
     * Realiza búsqueda
     */
    public void realizarBusqueda(VentanaBusquedaEquipo ventanaBusqueda) {
        String campo = ventanaBusqueda.getComboBuscador().getSelectedItem().toString();
        String texto = ventanaBusqueda.getTextField().getText();
        
        List<Integer> resultados = agenda.buscarEnCampos(campo, texto);
        // Procesar resultados en gestor de búsqueda
    }
    
    /**
     * Getters
     */
    public List<String> getCaracteresNoValidos() {
        return caracteresNoValidos;
    }
}

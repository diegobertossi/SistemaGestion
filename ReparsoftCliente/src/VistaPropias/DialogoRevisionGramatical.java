package VistaPropias;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import tiposPropios.FuenteCambria;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Diálogo para mostrar y navegar por los errores gramaticales encontrados
 * VERSIÓN CON CONTADOR CORREGIDO
 */
public class DialogoRevisionGramatical extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextArea textoOriginalArea;
	private JPanel panelError;
	private JLabel lblMensaje;
	private JLabel lblContexto;
	private JComboBox<String> cmbSugerencias;
	private JButton btnAceptar;
	private JButton btnSiguiente;
	private JButton btnCerrar;
	private JLabel lblContador;

	private List<CorrectorGramaticalAPI.ErrorGramatical> errores;
	private int indiceActual = 0;

	// Para el contador
	private int totalErroresInicial;
	private int erroresCorregidos = 0;

	// Referencia al texto original y al componente que lo contiene
	private JTextComponent textoOriginalComponente;
	private String textoOriginal;
	private StringBuilder textoModificado;
	private boolean huboCambios = false;
	private Color amarilloClaro = new Color(237,232,208);


	/**
	 * Constructor que recibe el componente de texto a modificar
	 */
	public DialogoRevisionGramatical(Frame parent, JTextComponent textoComponente,
			CorrectorGramaticalAPI.ResultadoRevision resultado) {
		super(parent, "Revisor Gramatical", true);
		this.textoOriginalComponente = textoComponente;
		this.textoOriginal = textoComponente.getText();
		this.errores = resultado.getErrores();
		this.textoModificado = new StringBuilder(textoOriginal);
		
		
		// Guardar el total inicial de errores para el contador
		this.totalErroresInicial = (errores != null) ? errores.size() : 0;
		this.erroresCorregidos = 0;

		if (errores == null || errores.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "No se encontraron errores gramaticales.", "Revisión completada",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
			return;
		}

		initComponents();
		configurarVentana();
		mostrarErrorActual();

		// Listener para cuando se cierre la ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarVentana();
			}
		});
	}

	/**
	 * Inicializa los componentes de la interfaz
	 */
	private void initComponents() {
		// Panel principal con BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBackground(SystemColor.inactiveCaption);
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		

	
		// ===== PANEL SUPERIOR: TEXTO ORIGINAL =====
		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.setBackground(SystemColor.activeCaption);

		TitledBorder bordeTitulo = new TitledBorder(BorderFactory.createEmptyBorder(), "TEXTO EN REVISIÓN");
		bordeTitulo.setTitleFont(new Font("Cambria", Font.BOLD, 14));
		Border bordeExterior = BorderFactory.createLineBorder(new Color(0, 128, 128)); // gris claro
		panelTexto.setBorder(BorderFactory.createCompoundBorder(bordeExterior, bordeTitulo));

		textoOriginalArea = new JTextArea(textoOriginal, 8, 60);
		textoOriginalArea.setBackground(amarilloClaro);
		textoOriginalArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		textoOriginalArea.setEditable(false);
		textoOriginalArea.setLineWrap(true);
		textoOriginalArea.setWrapStyleWord(true);
		textoOriginalArea.setFont(new Font("Cambria", Font.PLAIN, 12));

		JScrollPane scrollTexto = new JScrollPane(textoOriginalArea);
		scrollTexto.setPreferredSize(new Dimension(600, 150));
		panelTexto.add(scrollTexto, BorderLayout.CENTER);

		// ===== PANEL CENTRAL: ERROR ACTUAL =====
		panelError = new JPanel(new GridBagLayout());
		panelError.setBackground(SystemColor.activeCaption);

		TitledBorder borde2 = new TitledBorder(BorderFactory.createEmptyBorder(), "ERROR ENCONTRADO");
		borde2.setTitleFont(new Font("Cambria", Font.BOLD, 14)); // Fuente para el título
		panelError.setBorder(BorderFactory.createCompoundBorder(bordeExterior, borde2));


		// Fila 0: Descripción
		GridBagConstraints gbcDescripcion = new GridBagConstraints();
		gbcDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbcDescripcion.insets = new Insets(5, 5, 5, 5);
		gbcDescripcion.gridx = 0;
		gbcDescripcion.gridy = 0;
		gbcDescripcion.gridwidth = 2;
		panelError.add(new JLabel("Descripción:"), gbcDescripcion);

		// Fila 1: Mensaje del error
		GridBagConstraints gbcMensaje = new GridBagConstraints();
		gbcMensaje.fill = GridBagConstraints.HORIZONTAL;
		gbcMensaje.insets = new Insets(5, 5, 5, 5);
		gbcMensaje.gridx = 0;
		gbcMensaje.gridy = 1;
		gbcMensaje.gridwidth = 2;
		lblMensaje = new JLabel();
		lblMensaje.setFont(new Font("Cambria", Font.PLAIN, 12));
		panelError.add(lblMensaje, gbcMensaje);

		// Fila 2: Contexto
		GridBagConstraints gbcContextoLabel = new GridBagConstraints();
		gbcContextoLabel.fill = GridBagConstraints.HORIZONTAL;
		gbcContextoLabel.insets = new Insets(5, 5, 5, 5);
		gbcContextoLabel.gridx = 0;
		gbcContextoLabel.gridy = 2;
		gbcContextoLabel.gridwidth = 2;
		panelError.add(new JLabel("Contexto:"), gbcContextoLabel);

		// Fila 3: Contexto valor
		GridBagConstraints gbcContexto = new GridBagConstraints();
		gbcContexto.fill = GridBagConstraints.HORIZONTAL;
		gbcContexto.insets = new Insets(5, 5, 5, 5);
		gbcContexto.gridx = 0;
		gbcContexto.gridy = 3;
		gbcContexto.gridwidth = 2;
		lblContexto = new JLabel();
		lblContexto.setFont(new Font("Cambria", Font.PLAIN, 12));
		panelError.add(lblContexto, gbcContexto);

		// Fila 4: Sugerencias label
		GridBagConstraints gbcSugerenciasLabel = new GridBagConstraints();
		gbcSugerenciasLabel.fill = GridBagConstraints.HORIZONTAL;
		gbcSugerenciasLabel.insets = new Insets(5, 5, 5, 5);
		gbcSugerenciasLabel.gridx = 0;
		gbcSugerenciasLabel.gridy = 4;
		gbcSugerenciasLabel.gridwidth = 2;
		panelError.add(new JLabel("Sugerencias:"), gbcSugerenciasLabel);

		// Fila 5: Sugerencias combo
		GridBagConstraints gbcSugerencias = new GridBagConstraints();
		gbcSugerencias.fill = GridBagConstraints.HORIZONTAL;
		gbcSugerencias.insets = new Insets(5, 5, 5, 5);
		gbcSugerencias.gridx = 0;
		gbcSugerencias.gridy = 5;
		gbcSugerencias.gridwidth = 2;
		cmbSugerencias = new JComboBox<>();
		cmbSugerencias.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbSugerencias.setPreferredSize(new Dimension(400, 25));
		panelError.add(cmbSugerencias, gbcSugerencias);


		// ===== PANEL INFERIOR: BOTONES =====
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

		panelBotones.setBackground(SystemColor.inactiveCaption);

		btnAceptar = new JButton("ACEPTAR SUGERENCIA");
		btnAceptar.setFont(new Font("Cambria", Font.BOLD, 12));
		btnAceptar.setToolTipText("Reemplazar con la sugerencia seleccionada");
		btnAceptar.addActionListener(e -> aceptarSugerencia());

		btnSiguiente = new JButton("SIGUIENTE ERROR");
		btnSiguiente.setFont(new Font("Cambria", Font.BOLD, 12));
		btnSiguiente.setToolTipText("Ir al siguiente error sin corregir");
		btnSiguiente.addActionListener(e -> siguienteError());

		btnCerrar = new JButton("GUARDAR Y CERRAR");
		btnCerrar.setFont(new Font("Cambria", Font.BOLD, 12));
		btnCerrar.setToolTipText("Guardar cambios y cerrar el revisor");
		btnCerrar.addActionListener(e -> cerrarVentana());

		panelBotones.add(btnAceptar);
		panelBotones.add(btnSiguiente);
		panelBotones.add(btnCerrar);

		// ===== PANEL INFERIOR: CONTADOR =====
		JPanel panelContador = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelContador.setBackground(SystemColor.inactiveCaption);
		lblContador = new JLabel();
		lblContador.setFont(new Font("Cambria", Font.BOLD, 12));
		panelContador.add(lblContador);

		// Agrandar botones
		btnAceptar.setPreferredSize(new Dimension(180, 35));
		btnSiguiente.setPreferredSize(new Dimension(180, 35));
		btnCerrar.setPreferredSize(new Dimension(180, 35));

		// Separar el panel del contador
		panelContador.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // margen superior de 20px

		if (panelError != null) {
			for (Component c : panelError.getComponents()) {
				if (c instanceof JLabel && !c.equals(lblMensaje) && !c.equals(lblContexto) && !c.equals(lblContador)) {

					c.setFont(new Font("Cambria", Font.BOLD, 14));

				}
			}
		}

		// Ensamblar todo
		JPanel panelSur = new JPanel(new BorderLayout());
		panelSur.add(panelBotones, BorderLayout.CENTER);
		panelSur.add(panelContador, BorderLayout.SOUTH);

		mainPanel.add(panelTexto, BorderLayout.NORTH);
		mainPanel.add(panelError, BorderLayout.CENTER);
		mainPanel.add(panelSur, BorderLayout.SOUTH);

		// configurarFuentesDialogo();

		add(mainPanel);
	}

	/**
	 * Configura propiedades de la ventana
	 */
	private void configurarVentana() {
		setSize(700, 550);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
	}

	/**
	 * Muestra el error actual en la interfaz
	 */
	private void mostrarErrorActual() {
		if (errores.isEmpty() || indiceActual < 0 || indiceActual >= errores.size()) {
			return;
		}

		CorrectorGramaticalAPI.ErrorGramatical error = errores.get(indiceActual);

		// Mostrar mensaje
		lblMensaje.setText(error.getMensaje());

		// Mostrar contexto resaltando el error
		String contexto = error.getContexto();
		int offsetContexto = error.getOffsetContexto();
		int longitud = error.getLongitud();

		if (contexto != null && offsetContexto >= 0 && longitud > 0) {
			String parte1 = contexto.substring(0, offsetContexto);
			String errorTexto = contexto.substring(offsetContexto,
					Math.min(offsetContexto + longitud, contexto.length()));
			String parte3 = contexto.substring(Math.min(offsetContexto + longitud, contexto.length()));

			lblContexto.setText(
					"<html>" + parte1 + "<b><font color='red'>" + errorTexto + "</font></b>" + parte3 + "</html>");
		} else {
			lblContexto.setText(contexto);
		}

		// Cargar sugerencias en el combo
		cmbSugerencias.removeAllItems();
		List<String> sugerencias = error.getSugerencias();
		if (sugerencias.isEmpty()) {
			cmbSugerencias.addItem("(No hay sugerencias disponibles)");
			btnAceptar.setEnabled(false);
		} else {
			for (String sugerencia : sugerencias) {
				cmbSugerencias.addItem(sugerencia);
			}
			btnAceptar.setEnabled(true);
		}

		// Actualizar contador
		actualizarContador();

		// Resaltar el error en el texto original
		resaltarErrorEnTexto(error);
	}

	/**
	 * Resalta el error en el área de texto original
	 */
	private void resaltarErrorEnTexto(CorrectorGramaticalAPI.ErrorGramatical error) {
		try {
			int offset = error.getOffset();
			int longitud = error.getLongitud();

			if (offset >= 0 && longitud > 0) {
				textoOriginalArea.setCaretPosition(offset);
				textoOriginalArea.select(offset, offset + longitud);
				// resaltar el texto en color rojo pastel claro para que se vea mejor
				textoOriginalArea.setSelectionColor(new Color(255, 102, 102, 128));
				textoOriginalArea.requestFocusInWindow();
			}
		} catch (IllegalArgumentException e) {
			// Ignorar si la posición no es válida
		}
	}

	/**
	 * Actualiza el texto del contador de errores - VERSIÓN CORREGIDA
	 */
	private void actualizarContador() {
		int erroresPendientes = errores.size();
		int erroresMostrados = totalErroresInicial; // Total original

		if (totalErroresInicial > 0) {
			if (erroresPendientes == 0) {
				// Todos corregidos
				lblContador.setText(String.format("✓ Todos los errores corregidos (%d de %d)", totalErroresInicial,
						totalErroresInicial));
			} else {
				// Mostrar progreso: error actual de pendientes, y total corregidos
				lblContador.setText(String.format("Error %d de %d (Corregidos: %d de %d)", indiceActual + 1,
						erroresPendientes, erroresCorregidos, totalErroresInicial));
			}
		} else {
			lblContador.setText("No hay errores");
		}
	}

	/**
	 * Acepta la sugerencia seleccionada y modifica el texto - VERSIÓN CORREGIDA
	 */
	private void aceptarSugerencia() {
		if (errores.isEmpty() || cmbSugerencias.getSelectedItem() == null) {
			return;
		}

		String sugerencia = (String) cmbSugerencias.getSelectedItem();
		if (sugerencia.startsWith("(No hay")) {
			JOptionPane.showMessageDialog(this, "No hay sugerencias para este error.", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		CorrectorGramaticalAPI.ErrorGramatical error = errores.get(indiceActual);
		String textoErroneo = error.getTextoErroneo();

		int confirmacion = JOptionPane.showConfirmDialog(this,
				String.format("¿Reemplazar \"%s\" por \"%s\"?", textoErroneo, sugerencia), "Confirmar corrección",
				JOptionPane.YES_NO_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION) {
			// Aplicar la corrección en el StringBuilder
			int offset = error.getOffset();
			int longitud = error.getLongitud();

			if (offset >= 0 && longitud > 0) {
				textoModificado.replace(offset, offset + longitud, sugerencia);

				// Actualizar el área de texto para mostrar el cambio
				textoOriginalArea.setText(textoModificado.toString());

				// Marcar que hubo cambios
				huboCambios = true;

				// Incrementar contador de corregidos
				erroresCorregidos++;

				// Ajustar los offsets de los errores restantes
				int diferencia = sugerencia.length() - longitud;
				ajustarOffsetsErrores(offset, diferencia);

				// Eliminar este error de la lista
				errores.remove(indiceActual);

				// Actualizar contador
				actualizarContador();

				// Navegar al siguiente error o cerrar si no hay más
				if (errores.isEmpty()) {
					JOptionPane
							.showMessageDialog(this,
									String.format("¡Has corregido todos los errores (%d de %d)!", erroresCorregidos,
											totalErroresInicial),
									"Revisión completada", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// Ajustar índice si es necesario
					if (indiceActual >= errores.size()) {
						indiceActual = 0;
					}
					mostrarErrorActual();
				}
			}
		}
	}

	/**
	 * Ajusta los offsets de los errores después de una corrección
	 */
	private void ajustarOffsetsErrores(int offsetCorregido, int diferencia) {
		for (CorrectorGramaticalAPI.ErrorGramatical error : errores) {
			if (error.getOffset() > offsetCorregido) {
				error.setOffset(error.getOffset() + diferencia);
			}
		}
	}

	/**
	 * Avanza al siguiente error
	 */
	private void siguienteError() {
		if (errores.isEmpty())
			return;

		indiceActual = (indiceActual + 1) % errores.size();
		mostrarErrorActual();
	}

	/**
	 * Cierra la ventana y aplica los cambios al texto original
	 */
	private void cerrarVentana() {
		if (huboCambios) {
			// Preguntar si quiere guardar los cambios
			int opcion = JOptionPane.showConfirmDialog(this,
					String.format("¿Desea guardar los %d cambios realizados en el texto?", erroresCorregidos),
					"Guardar cambios", JOptionPane.YES_NO_CANCEL_OPTION);

			if (opcion == JOptionPane.YES_OPTION) {
				// Aplicar los cambios al componente original
				textoOriginalComponente.setText(textoModificado.toString());
				dispose();
			} else if (opcion == JOptionPane.NO_OPTION) {
				// Cerrar sin guardar
				dispose();
			}
			// Si es CANCEL_OPTION, no hace nada y sigue en el diálogo
		} else {
			// No hubo cambios, cerrar directamente
			dispose();
		}
	}

	/**
	 * Método estático para mostrar el revisor
	 */
	public static void mostrarRevisor(Frame parent, JTextComponent textoComponente,
			CorrectorGramaticalAPI.ResultadoRevision resultado) {
		if (resultado == null || !resultado.hayErrores()) {
			JOptionPane.showMessageDialog(parent, "No se encontraron errores gramaticales.", "Revisión completada",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		SwingUtilities.invokeLater(() -> {
			new DialogoRevisionGramatical(parent, textoComponente, resultado).setVisible(true);
		});
	}
}
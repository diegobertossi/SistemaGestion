package presentacion.controlador;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.ButtonModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.Agenda;
import persistencia.conexion.Conexion;
import presentacion.vista.PopupProgresoBackup;
import presentacion.vista.VentanaBackUp;
import presentacion.vista.VentanaOpcionesBackup;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.sql.ResultSetMetaData;

public class ControladorBackup implements ActionListener, MouseListener {

	private VentanaBackUp ventanaBackUp;
	private VentanaOpcionesBackup ventanaOpcionesBackup;

	private String rutadefaultBackup = "";

	private String cleverCloudHostBRC = "b1zeyndbfc1bmeiernaw-mysql.services.clever-cloud.com";
	private String cleverCloudPortBRC = "3306";
	private String cleverCloudUserBRC = "uhhm5ckiyyizik8y";
	private String cleverCloudPasswordBRC = "TXJcnVkA9yW9JDaUNg0a";
	private String cleverCloudDatabaseBRC = "b1zeyndbfc1bmeiernaw";

	private String cleverCloudHostBSAS = "bewqn4ds4dxour1xkgu6-mysql.services.clever-cloud.com";
	private String cleverCloudPortBSAS = "3306";
	private String cleverCloudUserBSAS = "uocexuvpspnbuath";
	private String cleverCloudPasswordBSAS = "waHWGTIYsS52IV0ZiOLU";
	private String cleverCloudDatabaseBSAS = "bewqn4ds4dxour1xkgu6";

	private String cleverCloudHost = "";
	private String cleverCloudPort = "";
	private String cleverCloudUser = "";
	private String cleverCloudPassword = "";
	private String cleverCloudDatabase = "";

	@SuppressWarnings("unused")
	private Agenda agenda;

	public ControladorBackup(VentanaBackUp ventanaBackUp, Agenda agenda) {
		this.ventanaBackUp = ventanaBackUp;
		this.agenda = agenda;
		this.ventanaBackUp.getBtnGenerarB().addActionListener(this);
		this.ventanaBackUp.getBtnImportarB().addActionListener(this);

		if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {
			cleverCloudHost = cleverCloudHostBRC;
			cleverCloudPort = cleverCloudPortBRC;
			cleverCloudUser = cleverCloudUserBRC;
			cleverCloudPassword = cleverCloudPasswordBRC;
			cleverCloudDatabase = cleverCloudDatabaseBRC;
		} else if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {
			cleverCloudHost = cleverCloudHostBSAS;
			cleverCloudPort = cleverCloudPortBSAS;
			cleverCloudUser = cleverCloudUserBSAS;
			cleverCloudPassword = cleverCloudPasswordBSAS;
			cleverCloudDatabase = cleverCloudDatabaseBSAS;
		}
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public void actionPerformed(ActionEvent e) {

		ButtonModel seleccion = ventanaBackUp.getGrupoUbicacionServidor().getSelection();

		Date dia = new Date();
		String NombreBackUp = "Backup Reparsoft " + dia.getDate() + "-" + (dia.getMonth() + 1) + "-"
				+ (dia.getYear() + 1900) + ".sql";

		if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {
			rutadefaultBackup = "F:\\els\\Administracion\\Sistema\\Base de datos\\Backups\\";
		} else if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {
			rutadefaultBackup = "F:\\els\\Bariloche\\Administracion\\Sistema\\Base de datos\\Backups\\";
		}

		String NombreBackUpSinExtension = "Backup Reparsoft " + dia.getDate() + "-" + (dia.getMonth() + 1) + "-"
				+ (dia.getYear() + 1900);

		if (ventanaBackUp != null && e.getSource() == ventanaBackUp.getBtnGenerarB()) {
			if (seleccion == ventanaBackUp.getRdbtnLocal().getModel()) {
				ventanaOpcionesBackup = new VentanaOpcionesBackup();
				ventanaOpcionesBackup.getTxtNombreArchivo().addActionListener(this);
				ventanaOpcionesBackup.getTxtRutaArchivo().addActionListener(this);
				ventanaOpcionesBackup.getBtnGuardarLocal().addActionListener(this);
				ventanaOpcionesBackup.getBtnCancelar().addActionListener(this);
				ventanaOpcionesBackup.getBtnCambiarNombre().addActionListener(this);
				ventanaOpcionesBackup.getBtnResetDatos().addActionListener(this);
				ventanaOpcionesBackup.getTxtNombreArchivo().setText(NombreBackUp);
				ventanaOpcionesBackup.getTxtRutaArchivo().setText(rutadefaultBackup);
			} else if (seleccion == ventanaBackUp.getRdbtnRemoto().getModel()) {
				int opcion = JOptionPane.showConfirmDialog(null,
						"Se sobreescribirá el archivo remoto anterior. ¿Desea continuar?", "Confirmar Backup Remoto",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					GenerarBackupMySQLRemotoConSwingWorker(agenda.getUbicacionBase(), cleverCloudHost, cleverCloudPort,
							cleverCloudUser, cleverCloudPassword, cleverCloudDatabase);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione una ubicación");
			}
		}

		if (ventanaBackUp != null && e.getSource() == ventanaBackUp.getBtnImportarB()) {
			if (seleccion == ventanaBackUp.getRdbtnLocal().getModel()) {
				ActualizarBackupMySQLlocal();
			} else if (seleccion == ventanaBackUp.getRdbtnRemoto().getModel()) {
				int opcion = JOptionPane.showConfirmDialog(null,
						"Se sobreescribirá la base de datos local. ¿Desea continuar?", "Confirmar Importación Remota",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					ActualizarBackupMySQLremotoConSwingWorker(agenda.getUbicacionBase(), cleverCloudHost,
							cleverCloudPort, cleverCloudUser, cleverCloudPassword, cleverCloudDatabase);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione una ubicación");
			}
		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnCancelar()) {
			this.ventanaOpcionesBackup.dispose();
			this.ventanaOpcionesBackup = null;
		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnGuardarLocal()) {
			GenerarBackupMySQLLocal();
		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnCambiarNombre()) {
			JFileChooser cambiarNombreRuta = new JFileChooser(rutadefaultBackup);
			FileNameExtensionFilter sqlFilter = new FileNameExtensionFilter("Bases de datos SQL", "sql");
			cambiarNombreRuta.setFileFilter(sqlFilter);
			int resp = cambiarNombreRuta.showSaveDialog(ventanaOpcionesBackup);
			if (resp == JFileChooser.APPROVE_OPTION) {
				ventanaOpcionesBackup.getTxtNombreArchivo()
						.setText(String.valueOf(cambiarNombreRuta.getSelectedFile().getName()) + ".sql");
				ventanaOpcionesBackup.getTxtRutaArchivo()
						.setText(String.valueOf(cambiarNombreRuta.getCurrentDirectory()) + "\\");
			}
		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnResetDatos()) {
			ventanaOpcionesBackup.getTxtNombreArchivo().setText(NombreBackUp);
			ventanaOpcionesBackup.getTxtRutaArchivo().setText(rutadefaultBackup);
		}
	}

	public void ActualizarBackupMySQLremotoConSwingWorker(String ubicacion, String cleverCloudHost,
			String cleverCloudPort, String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {

		ventanaBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getGlassPane().setVisible(true);
		ventanaBackUp.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getBtnGenerarB().setEnabled(false);
		ventanaBackUp.getBtnImportarB().setEnabled(false);

		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() {
				return ActualizarBackupMySQLremoto(ubicacion, cleverCloudHost, cleverCloudPort, cleverCloudUser,
						cleverCloudPassword, cleverCloudDatabase);
			}

			@Override
			protected void done() {
				try {
					get();
				} catch (Exception e) {
					System.err.println("Excepción en el worker de actualización remota: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Ocurrió un error inesperado durante la actualización: " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					ventanaBackUp.getGlassPane().setVisible(false);
					ventanaBackUp.setCursor(Cursor.getDefaultCursor());
					ventanaBackUp.getBtnGenerarB().setEnabled(true);
					ventanaBackUp.getBtnImportarB().setEnabled(true);
				}
			}
		};

		SwingUtilities.invokeLater(() -> worker.execute());
	}

	public void GenerarBackupMySQLRemotoConSwingWorker(String ubicacion, String cleverCloudHost, String cleverCloudPort,
			String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {

		ventanaBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getGlassPane().setVisible(true);
		ventanaBackUp.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getBtnGenerarB().setEnabled(false);
		ventanaBackUp.getBtnImportarB().setEnabled(false);

		JDialog popup = new JDialog();
		popup.setTitle("Procesando");
		popup.setModal(false);
		popup.setSize(300, 100);
		popup.setLocationRelativeTo(ventanaBackUp);
		popup.add(new JLabel("Generando backup remoto, espere...", SwingConstants.CENTER));

		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() {
				return GenerarBackupMySQLRemoto(ubicacion, cleverCloudHost, cleverCloudPort, cleverCloudUser,
						cleverCloudPassword, cleverCloudDatabase);
			}

			@Override
			protected void done() {
				try {
					get();
				} catch (Exception e) {
					System.err.println("Excepción en el worker de backup remoto: " + e.getMessage());
					JOptionPane.showMessageDialog(null,
							"Ocurrió un error inesperado durante el backup: " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					popup.dispose();
					ventanaBackUp.getGlassPane().setVisible(false);
					ventanaBackUp.setCursor(Cursor.getDefaultCursor());
					ventanaBackUp.getBtnGenerarB().setEnabled(true);
					ventanaBackUp.getBtnImportarB().setEnabled(true);
				}
			}
		};

		SwingUtilities.invokeLater(() -> {
			popup.setVisible(true);
			worker.execute();
		});
	}

	private boolean GenerarBackupMySQLRemoto(String ubicacion, String cleverCloudHost, String cleverCloudPort,
			String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {

		ventanaBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getBtnGenerarB().setEnabled(false);
		ventanaBackUp.getBtnImportarB().setEnabled(false);

		PopupProgresoBackup popup = new PopupProgresoBackup(ventanaBackUp, "Generando backup remoto, espere...");
		popup.mostrar();

		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() {
				String archivoTemporal = null;
				try {
					String nombreBaseLocal = (ubicacion.equalsIgnoreCase("Bariloche")) ? "ordenesbrc" : "ordenesbsas";
					archivoTemporal = System.getProperty("java.io.tmpdir") + File.separator + "backup_"
							+ nombreBaseLocal + "_" + System.currentTimeMillis() + ".sql";

					publish(5);

					// 1. Crear dump local
					System.out.println("Creando dump de la base de datos local...");
					String mysqlPath = obtenerRutaMySQL();
					if (mysqlPath == null) {
						JOptionPane.showMessageDialog(null,
								"No se pudo encontrar la ruta de MySQL. Verifique la instalación.");
						return null;
					}

					List<String> comando = Arrays.asList(mysqlPath + "mysqldump", "--host=localhost", "--port=3306",
							"--user=root", "--password=root", "--default-character-set=utf8mb4", "--single-transaction",
							"--routines", "--triggers", "--no-create-db", "--column-statistics=0", nombreBaseLocal);

					ProcessBuilder pb = new ProcessBuilder(comando);
					pb.redirectOutput(new File(archivoTemporal));
					Process proceso = pb.start();
					int codigoSalida = proceso.waitFor();
					if (codigoSalida != 0) {
						JOptionPane.showMessageDialog(null, "Error al crear el archivo de backup local.");
						return null;
					}
					System.out.println("Dump local creado exitosamente.");

					publish(15);

					// 2. Leer sentencias SQL del dump (¡CORREGIDO!)
					System.out.println("Leyendo sentencias SQL del dump...");
					List<String> sentencias = parseSqlStatements(archivoTemporal);
					int totalSentencias = sentencias.size();
					System.out.println("Total de sentencias SQL: " + totalSentencias);

					publish(20);

					// 3. Conectar a Clever Cloud
					System.out.println("Conectando a Clever Cloud...");
					String urlCleverCloud = String.format(
							"jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowMultiQueries=true",
							cleverCloudHost, cleverCloudPort, cleverCloudDatabase);

					try (Connection conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser,
							cleverCloudPassword); Statement stmt = conexionRemota.createStatement()) {

						System.out.println("Conectado a Clever Cloud.");
						publish(25);

						// Configurar charset
						System.out.println("Configurando charset de la conexión remota a utf8mb4...");
						stmt.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
						stmt.execute("SET CHARACTER SET utf8mb4");

						// Limpiar base de datos remota
						System.out.println("Limpiando base de datos remota...");
						limpiarBaseDatos(conexionRemota);

						stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
						stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");

						publish(30);

						// 4. Ejecutar sentencias
						System.out.println("Restaurando backup en Clever Cloud...");
						int sentenciasEjecutadas = 0;
						int sentenciasIgnoradas = 0;

						for (int i = 0; i < totalSentencias; i++) {
							String s = sentencias.get(i).trim();

							if (s.isEmpty() || s.startsWith("--") || s.toUpperCase().startsWith("CREATE DATABASE")
									|| s.toUpperCase().startsWith("USE ")
									|| s.toUpperCase().startsWith("DROP DATABASE")) {
								sentenciasIgnoradas++;
								continue;
							}

							// Saltar comentarios condicionales de mysqldump
							if (s.startsWith("/*!") && s.endsWith("*/")) {
								sentenciasIgnoradas++;
								continue;
							}
							if (s.toUpperCase().contains("CHARACTER_SET_CLIENT")
									|| s.toUpperCase().contains("CHARACTER_SET_RESULTS")
									|| s.toUpperCase().contains("COLLATION_CONNECTION")
									|| s.toUpperCase().contains("SQL_NOTES") || s.toUpperCase().contains("@OLD_")) {
								sentenciasIgnoradas++;
								continue;
							}

							try {
								stmt.execute(s);
								sentenciasEjecutadas++;
							} catch (SQLException ex) {
								if (!ex.getMessage().contains("Access denied")) {
									System.err.println("Error en sentencia (ignorada): " + ex.getMessage());
								}
								sentenciasIgnoradas++;
							}

							int progreso = 30 + (int) ((i + 1) * 65.0 / totalSentencias);
							publish(progreso);
						}

						stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

						System.out.println("Restauración completada:");
						System.out.println("- Sentencias ejecutadas exitosamente: " + sentenciasEjecutadas);
						System.out.println("- Sentencias ignoradas: " + sentenciasIgnoradas);
					}

					publish(100);

					JOptionPane.showMessageDialog(null, "Backup remoto completado exitosamente", "Backup Exitoso",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error durante el backup remoto: " + e.getMessage(),
							"Error en Backup", JOptionPane.ERROR_MESSAGE);
				} finally {
					if (archivoTemporal != null) {
						new File(archivoTemporal).delete();
					}
				}
				return null;
			}

			@Override
			protected void process(java.util.List<Integer> chunks) {
				int ultimo = chunks.get(chunks.size() - 1);
				popup.actualizarProgreso(ultimo);
			}

			@Override
			protected void done() {
				popup.cerrar();
				ventanaBackUp.getGlassPane().setVisible(false);
				ventanaBackUp.getBtnGenerarB().setEnabled(true);
				ventanaBackUp.getBtnImportarB().setEnabled(true);
			}
		};

		SwingUtilities.invokeLater(() -> worker.execute());

		return true;
	}

	// ===================================================================
	// MÉTODO CORREGIDO: ahora respeta escapes (\') de mysqldump
	// ===================================================================
	private List<String> parseSqlStatements(String filePath) throws IOException {
		List<String> statements = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		boolean escaped = false;

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			int c;
			while ((c = reader.read()) != -1) {
				char ch = (char) c;
				sb.append(ch);

				if (escaped) {
					escaped = false;
				} else if (ch == '\\') {
					escaped = true;
				} else if (ch == '\'' && !inDoubleQuote) {
					inSingleQuote = !inSingleQuote;
				} else if (ch == '"' && !inSingleQuote) {
					inDoubleQuote = !inDoubleQuote;
				} else if (ch == ';' && !inSingleQuote && !inDoubleQuote) {
					statements.add(sb.toString().trim());
					sb.setLength(0);
				}
			}
			if (sb.length() > 0 && sb.toString().trim().length() > 0) {
				statements.add(sb.toString().trim());
			}
		}
		return statements;
	}

	// ===================================================================
	// Resto de métodos (sin cambios)
	// ===================================================================

	public boolean ActualizarBackupMySQLremoto(String ubicacion, String cleverCloudHost, String cleverCloudPort,
			String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {

		ventanaBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getGlassPane().setVisible(true);
		ventanaBackUp.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getBtnGenerarB().setEnabled(false);
		ventanaBackUp.getBtnImportarB().setEnabled(false);

		PopupProgresoBackup popup = new PopupProgresoBackup(ventanaBackUp,
				"Actualizando desde servidor remoto, espere...");
		popup.mostrar();

		final boolean[] exitoso = { false };

		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() {
				Connection conexionRemota = null;
				Connection conexionLocal = null;

				try {
					String nombreBaseLocal = (ubicacion.equalsIgnoreCase("Bariloche")) ? "ordenesbrc" : "ordenesbsas";
					System.out.println("Iniciando actualización de " + nombreBaseLocal + " desde Clever Cloud...");

					publish(5);

					System.out.println("Conectando a Clever Cloud para extraer datos...");
					String urlCleverCloud = String.format(
							"jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true&allowMultiQueries=true",
							cleverCloudHost, cleverCloudPort, cleverCloudDatabase);
					conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser, cleverCloudPassword);
					conexionRemota.setAutoCommit(false);

					try (Statement stmtCharsetRemoto = conexionRemota.createStatement()) {
						stmtCharsetRemoto.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
						stmtCharsetRemoto.execute("SET CHARACTER SET utf8mb4");
					}

					System.out.println("Conectado a Clever Cloud exitosamente");
					publish(10);

					System.out.println("Conectando a la base de datos local...");
					Conexion instanciaConexion = Conexion.getConexion(ubicacion);
					conexionLocal = instanciaConexion.getSQLConexion();
					if (conexionLocal == null || conexionLocal.isClosed()) {
						JOptionPane.showMessageDialog(null, "Error: No se pudo conectar a la base de datos local");
						return false;
					}
					conexionLocal.setAutoCommit(false);

					try (Statement stmtCharset = conexionLocal.createStatement()) {
						stmtCharset.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
						stmtCharset.execute("SET CHARACTER SET utf8mb4");
					}

					System.out.println("Conectado a la base de datos local exitosamente");
					publish(15);

					System.out.println("Limpiando base de datos local...");
					limpiarBaseDatos(conexionLocal);
					publish(20);

					try (Statement stmtRemoto = conexionRemota.createStatement()) {
						List<String> tablas = new ArrayList<>();
						try (ResultSet rsTablas = stmtRemoto.executeQuery("SHOW TABLES")) {
							while (rsTablas.next()) {
								tablas.add(rsTablas.getString(1));
							}
						}

						int totalTablas = tablas.size();
						System.out.println("Encontradas " + totalTablas + " tablas para migrar.");

						for (int i = 0; i < totalTablas; i++) {
							String tabla = tablas.get(i);
							migrarTabla(tabla, conexionRemota, conexionLocal);

							int progreso = 20 + (int) ((i + 1) * 75.0 / totalTablas);
							publish(progreso);
						}
					}

					conexionLocal.commit();
					conexionRemota.commit();

					publish(100);

					verificarMigracion(conexionLocal);

					JOptionPane.showMessageDialog(null,
							"Actualización completada exitosamente!\n\n" + "Origen: " + nombreBaseLocal + " REMOTO"
									+ "\nDestino: " + nombreBaseLocal,
							"Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);

					return true;

				} catch (Exception e) {
					e.printStackTrace();
					try {
						if (conexionLocal != null)
							conexionLocal.rollback();
						if (conexionRemota != null)
							conexionRemota.rollback();
					} catch (SQLException rollbackEx) {
						System.err.println("Error durante rollback: " + rollbackEx.getMessage());
					}
					JOptionPane.showMessageDialog(null,
							"Error durante la actualización desde Clever Cloud:\n" + e.getMessage(),
							"Error en Actualización", JOptionPane.ERROR_MESSAGE);
					return false;
				} finally {
					try {
						if (conexionRemota != null && !conexionRemota.isClosed()) {
							conexionRemota.setAutoCommit(true);
							conexionRemota.close();
						}
						if (conexionLocal != null && !conexionLocal.isClosed()) {
							conexionLocal.setAutoCommit(true);
						}
					} catch (Exception e) {
						System.err.println("Error al cerrar recursos: " + e.getMessage());
					}
				}
			}

			@Override
			protected void process(java.util.List<Integer> chunks) {
				int ultimo = chunks.get(chunks.size() - 1);
				popup.actualizarProgreso(ultimo);
			}

			@Override
			protected void done() {
				try {
					exitoso[0] = get();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ventanaBackUp.getGlassPane().setVisible(false);
				ventanaBackUp.setCursor(Cursor.getDefaultCursor());
				ventanaBackUp.getBtnGenerarB().setEnabled(true);
				ventanaBackUp.getBtnImportarB().setEnabled(true);
				popup.cerrar();
			}
		};

		SwingUtilities.invokeLater(() -> worker.execute());

		try {
			worker.get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exitoso[0];
	}

	private void limpiarBaseDatos(Connection conexion) throws SQLException {
		try (Statement stmt = conexion.createStatement()) {
			stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
			List<String> tablas = new ArrayList<>();
			try (ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
				while (rs.next()) {
					tablas.add(rs.getString(1));
				}
			}
			for (String tabla : tablas) {
				stmt.executeUpdate("DROP TABLE IF EXISTS `" + tabla + "`");
			}
			System.out.println("Base de datos limpiada, " + tablas.size() + " tablas eliminadas.");
			stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		}
	}

	@SuppressWarnings("unused")
	private void migrarDatosDirectamente(Connection conexionRemota, Connection conexionLocal) throws SQLException {
		try (Statement stmtRemoto = conexionRemota.createStatement()) {
			List<String> tablas = new ArrayList<>();
			try (ResultSet rsTablas = stmtRemoto.executeQuery("SHOW TABLES")) {
				while (rsTablas.next()) {
					tablas.add(rsTablas.getString(1));
				}
			}
			System.out.println("Encontradas " + tablas.size() + " tablas para migrar.");
			for (String tabla : tablas) {
				migrarTabla(tabla, conexionRemota, conexionLocal);
			}
		}
	}

	private void migrarTabla(String nombreTabla, Connection conexionRemota, Connection conexionLocal)
			throws SQLException {

		System.out.println("Migrando tabla: " + nombreTabla);

		try (Statement stmtRemoto = conexionRemota.createStatement();
				Statement stmtLocal = conexionLocal.createStatement()) {

			stmtLocal.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
			stmtLocal.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
			stmtLocal.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");

			try (ResultSet rsCreate = stmtRemoto.executeQuery("SHOW CREATE TABLE `" + nombreTabla + "`")) {
				if (rsCreate.next()) {
					String createSQL = rsCreate.getString(2);

					createSQL = createSQL.replace("utf8mb3", "utf8mb4");
					createSQL = createSQL.replaceAll("CHARSET=utf8(?!mb4)", "CHARSET=utf8mb4");
					createSQL = createSQL.replaceAll("CHARACTER SET utf8(?!mb4)", "CHARACTER SET utf8mb4");
					createSQL = createSQL.replaceAll("COLLATE utf8_(?!mb4)", "COLLATE utf8mb4_");

					stmtLocal.executeUpdate("DROP TABLE IF EXISTS `" + nombreTabla + "`");

					try {
						stmtLocal.executeUpdate(createSQL);
					} catch (SQLException e) {
						System.err.println("Error al crear tabla con charset modificado, usando SQL original");
						stmtLocal.executeUpdate(rsCreate.getString(2));
					}
				}
			}

			int totalRegistros = 0;
			try (ResultSet rsCount = stmtRemoto.executeQuery("SELECT COUNT(*) FROM `" + nombreTabla + "`")) {
				if (rsCount.next()) {
					totalRegistros = rsCount.getInt(1);
				}
			}

			if (totalRegistros > 0) {
				System.out.println("Tabla `" + nombreTabla + "` tiene " + totalRegistros + " registros.");
				migrarDatosEnLotes(nombreTabla, conexionRemota, conexionLocal, totalRegistros);
			} else {
				System.out.println("Tabla `" + nombreTabla + "` está vacía.");
			}

			stmtLocal.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		}
	}

	private void migrarDatosEnLotes(String nombreTabla, Connection conexionRemota, Connection conexionLocal,
			int totalRegistros) throws SQLException {
		final int TAMANO_LOTE = 1000;
		int registrosProcesados = 0;

		Statement stmtRemoto = null;
		PreparedStatement pstmtLocal = null;
		ResultSet rsData = null;

		try {
			stmtRemoto = conexionRemota.createStatement();
			rsData = stmtRemoto.executeQuery("SELECT * FROM `" + nombreTabla + "` LIMIT 1");
			ResultSetMetaData metaData = rsData.getMetaData();
			int columnCount = metaData.getColumnCount();
			rsData.close();

			StringBuilder insertSQL = new StringBuilder();
			insertSQL.append("INSERT INTO `").append(nombreTabla).append("` (");

			for (int i = 1; i <= columnCount; i++) {
				if (i > 1)
					insertSQL.append(", ");
				insertSQL.append("`").append(metaData.getColumnName(i)).append("`");
			}
			insertSQL.append(") VALUES (");
			for (int i = 1; i <= columnCount; i++) {
				if (i > 1)
					insertSQL.append(", ");
				insertSQL.append("?");
			}
			insertSQL.append(")");

			pstmtLocal = conexionLocal.prepareStatement(insertSQL.toString());

			int offset = 0;
			while (registrosProcesados < totalRegistros) {
				rsData = stmtRemoto
						.executeQuery("SELECT * FROM `" + nombreTabla + "` LIMIT " + TAMANO_LOTE + " OFFSET " + offset);

				while (rsData.next()) {
					for (int i = 1; i <= columnCount; i++) {
						pstmtLocal.setObject(i, rsData.getObject(i));
					}
					pstmtLocal.executeUpdate();
					registrosProcesados++;
				}

				rsData.close();
				offset += TAMANO_LOTE;
				System.out.println("   Procesados " + registrosProcesados + "/" + totalRegistros + " registros");
			}

		} finally {
			if (rsData != null)
				try {
					rsData.close();
				} catch (SQLException e) {
				}
			if (pstmtLocal != null)
				try {
					pstmtLocal.close();
				} catch (SQLException e) {
				}
			if (stmtRemoto != null)
				try {
					stmtRemoto.close();
				} catch (SQLException e) {
				}
		}
	}

	private void verificarMigracion(Connection conexionLocal) throws SQLException {
		try (Statement stmt = conexionLocal.createStatement(); ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
			int totalTablas = 0;
			while (rs.next())
				totalTablas++;
			System.out.println("Verificación completada: " + totalTablas + " tablas creadas.");
		}
	}

	private void ActualizarBackupMySQLlocal() {

		JFileChooser archivoBackup = new JFileChooser(rutadefaultBackup);
		FileNameExtensionFilter sqlFilter = new FileNameExtensionFilter("Bases de datos SQL", "sql");
		archivoBackup.setFileFilter(sqlFilter);

		int resp = archivoBackup.showOpenDialog(ventanaBackUp);

		if (resp == JFileChooser.APPROVE_OPTION) {

			ventanaBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			ventanaBackUp.getGlassPane().setVisible(true);
			ventanaBackUp.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			ventanaBackUp.getBtnGenerarB().setEnabled(false);
			ventanaBackUp.getBtnImportarB().setEnabled(false);

			PopupProgresoBackup popup = new PopupProgresoBackup(ventanaBackUp, "Actualizando Base de Datos, espere...");
			popup.mostrar();

			SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

				@Override
				protected Void doInBackground() {
					Process process = null;
					try {
						File archivoSQL = archivoBackup.getSelectedFile();
						long tamaño = archivoSQL.length();

						if (!archivoSQL.exists() || tamaño == 0) {
							JOptionPane.showMessageDialog(null, "El archivo de backup no existe o está vacío.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return null;
						}

						String mysqlPath = obtenerRutaMySQL();
						if (mysqlPath == null)
							return null;

						String nombreBaseLocal = agenda.getUbicacionBase().equalsIgnoreCase("Bariloche") ? "ordenesbrc"
								: "ordenesbsas";

						System.out.println("Importando backup:");
						System.out.println("Base: " + nombreBaseLocal);
						System.out.println("Archivo: " + archivoSQL.getAbsolutePath());

						List<String> command = new ArrayList<>();
						command.add(mysqlPath + "mysql.exe");
						command.add("--host=localhost");
						command.add("--port=3306");
						command.add("--user=root");
						command.add("--password=root");
						command.add("--default-character-set=utf8mb4");
						command.add(nombreBaseLocal);

						ProcessBuilder pb = new ProcessBuilder(command);
						process = pb.start();

						try (OutputStream os = process.getOutputStream();
								FileInputStream fis = new FileInputStream(archivoSQL)) {
							byte[] buffer = new byte[8192];
							int leido;
							long total = 0;
							while ((leido = fis.read(buffer)) != -1) {
								os.write(buffer, 0, leido);
								total += leido;
								int progreso = (int) ((total * 100) / tamaño);
								publish(progreso);
							}
							os.flush();
						}

						StringBuilder salida = new StringBuilder();
						try (BufferedReader br = new BufferedReader(
								new InputStreamReader(process.getInputStream(), "UTF-8"))) {
							String linea;
							while ((linea = br.readLine()) != null) {
								salida.append(linea).append("\n");
								System.out.println("MySQL: " + linea);
							}
						}

						int exitCode = process.waitFor();
						if (exitCode == 0) {
							JOptionPane.showMessageDialog(null,
									"Base de datos actualizada correctamente.\n\n" + "Base: " + nombreBaseLocal + "\n"
											+ "Archivo: " + archivoSQL.getName(),
									"Importación Exitosa", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Error al importar el backup.\n\n" + salida.toString(),
									"Error MySQL", JOptionPane.ERROR_MESSAGE);
						}

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error al actualizar la base de datos.\n\n" + e.toString(),
								"Error", JOptionPane.ERROR_MESSAGE);
					} finally {
						if (process != null)
							process.destroy();
					}
					return null;
				}

				@Override
				protected void process(java.util.List<Integer> chunks) {
					popup.actualizarProgreso(chunks.get(chunks.size() - 1));
				}

				@Override
				protected void done() {
					ventanaBackUp.getGlassPane().setVisible(false);
					ventanaBackUp.setCursor(Cursor.getDefaultCursor());
					ventanaBackUp.getBtnGenerarB().setEnabled(true);
					ventanaBackUp.getBtnImportarB().setEnabled(true);
					popup.cerrar();
				}
			};

			worker.execute();
		}
	}

	private void GenerarBackupMySQLLocal() {
		ventanaOpcionesBackup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaOpcionesBackup.getGlassPane().setVisible(true);
		ventanaOpcionesBackup.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ventanaBackUp.getBtnGenerarB().setEnabled(false);
		ventanaBackUp.getBtnImportarB().setEnabled(false);

		PopupProgresoBackup popup = new PopupProgresoBackup(ventanaBackUp, "Generando backup local, espere...");
		popup.mostrar();

		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() {
				File backupFile = null;
				Process child = null;
				try {
					String nombreAguardar = ventanaOpcionesBackup.getTxtNombreArchivo().getText();
					String rutaAguardar = ventanaOpcionesBackup.getTxtRutaArchivo().getText();
					backupFile = new File(rutaAguardar + nombreAguardar);

					String nombreBaseLocal = (agenda.getUbicacionBase().equalsIgnoreCase("Bariloche")) ? "ordenesbrc"
							: "ordenesbsas";

					String mysqlPath = obtenerRutaMySQL();
					if (mysqlPath == null) {
						JOptionPane.showMessageDialog(null,
								"No se pudo encontrar la ruta de MySQL. Verifique la instalación.");
						return null;
					}

					System.out.println("Generando backup de: " + nombreBaseLocal);
					System.out.println("Ruta MySQL: " + mysqlPath);
					System.out.println("Archivo destino: " + backupFile.getAbsolutePath());

					List<String> command = new ArrayList<>();
					command.add(mysqlPath + "mysqldump.exe");
					command.add("--host=localhost");
					command.add("--port=3306");
					command.add("--user=root");
					command.add("--password=root");
					command.add("--default-character-set=utf8mb4");
					command.add("--column-statistics=0");
					command.add("--routines");
					command.add("--triggers");
					command.add("--events");
					command.add("--add-drop-database");
					command.add("--add-drop-table");
					command.add("--complete-insert");
					command.add("--extended-insert");
					command.add("--single-transaction");
					command.add(nombreBaseLocal);

					ProcessBuilder pb = new ProcessBuilder(command);
					child = pb.start();

					try (BufferedReader br = new BufferedReader(new InputStreamReader(child.getInputStream(), "UTF-8"));
							OutputStreamWriter fw = new OutputStreamWriter(new java.io.FileOutputStream(backupFile),
									"UTF-8")) {
						String line;
						int lineCount = 0;
						int totalLinesWritten = 0;
						while ((line = br.readLine()) != null) {
							fw.write(line + System.lineSeparator());
							totalLinesWritten++;
							lineCount++;
							if (lineCount % 100 == 0) {
								publish(Math.min(90, (lineCount / 100) % 100));
							}
						}

						System.out.println("Backup completado. Total líneas escritas: " + totalLinesWritten);
					}

					int exitCode = child.waitFor();
					if (exitCode == 0) {
						if (backupFile.exists() && backupFile.length() > 0) {
							long fileSizeKB = backupFile.length() / 1024;
							JOptionPane.showMessageDialog(null,
									"Archivo de backup generado exitosamente.\n" + "Tamaño: " + fileSizeKB + " KB\n"
											+ "Ubicación: " + backupFile.getAbsolutePath(),
									"Backup Exitoso", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"El archivo de backup se generó pero está vacío o no existe.", "Advertencia",
									JOptionPane.WARNING_MESSAGE);
						}
					} else {
						StringBuilder errorMsg = new StringBuilder();
						try (BufferedReader errorReader = new BufferedReader(
								new InputStreamReader(child.getErrorStream()))) {
							String errorLine;
							while ((errorLine = errorReader.readLine()) != null) {
								errorMsg.append(errorLine).append("\n");
							}
						}
						JOptionPane.showMessageDialog(null, "Error al generar el backup. Código de salida: " + exitCode
								+ "\n" + "Error: " + errorMsg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
					}

					publish(100);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al generar el backup: " + e.getMessage() + "\n"
							+ "Detalle: " + e.getClass().getName(), "Error", JOptionPane.ERROR_MESSAGE);
				} finally {
					if (child != null)
						child.destroy();
				}
				return null;
			}

			@Override
			protected void process(java.util.List<Integer> chunks) {
				int ultimo = chunks.get(chunks.size() - 1);
				popup.actualizarProgreso(ultimo);
			}

			@Override
			protected void done() {
				ventanaBackUp.getGlassPane().setVisible(false);
				ventanaOpcionesBackup.getGlassPane().setVisible(false);
				ventanaOpcionesBackup.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				ventanaBackUp.getBtnGenerarB().setEnabled(true);
				ventanaBackUp.getBtnImportarB().setEnabled(true);
				popup.cerrar();
			}
		};

		SwingUtilities.invokeLater(() -> worker.execute());
	}

	private String obtenerRutaMySQL() {
		String[] rutasMySQL = { "C:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\",
				"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\",
				"C:\\Program Files (x86)\\MySQL\\MySQL Server 8.4\\bin\\",
				"C:\\Program Files (x86)\\MySQL\\MySQL Server 8.0\\bin\\",
				"F:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\",
				"F:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\",
				"F:\\Program Files (x86)\\MySQL\\MySQL Server 8.4\\bin\\",
				"F:\\Program Files (x86)\\MySQL\\MySQL Server 8.0\\bin\\",
				"D:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\",
				"D:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\",
				"D:\\Program Files (x86)\\MySQL\\MySQL Server 8.4\\bin\\",
				"D:\\Program Files (x86)\\MySQL\\MySQL Server 8.0\\bin\\",
				"E:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\",
				"E:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\",
				"E:\\Program Files (x86)\\MySQL\\MySQL Server 8.4\\bin\\",
				"E:\\Program Files (x86)\\MySQL\\MySQL Server 8.0\\bin\\",
				"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\",
				"C:\\Program Files (x86)\\MySQL\\MySQL Server 5.5\\bin\\" };

		String[] archivosMySQL = { "mysql.exe", "mysqldump.exe" };

		for (String rutaMySQL : rutasMySQL) {
			boolean rutaValida = true;
			for (String archivo : archivosMySQL) {
				if (!new File(rutaMySQL + archivo).exists()) {
					rutaValida = false;
					break;
				}
			}
			if (rutaValida) {
				System.out.println("Ruta de MySQL encontrada: " + rutaMySQL);
				return rutaMySQL;
			}
		}

		System.err.println("No se encontró una instalación válida de MySQL Server.");
		JOptionPane.showMessageDialog(null,
				"No se pudo encontrar la instalación de MySQL Server.\n"
						+ "Verifique que MySQL esté instalado correctamente.\n"
						+ "Rutas verificadas: MySQL Server 8.4, 8.0 y 5.5",
				"Error - MySQL no encontrado", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
}
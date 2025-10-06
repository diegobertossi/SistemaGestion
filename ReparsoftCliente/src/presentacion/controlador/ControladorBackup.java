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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.xdevapi.Statement;

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
				} else {
					System.out.println("Operación de backup remoto cancelada por el usuario.");
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
						"Se sobreescribirá la base de datos local. ¿Desea continuar?", "Confirmar ImportaciÃ³n Remota",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					ActualizarBackupMySQLremotoConSwingWorker(agenda.getUbicacionBase(), cleverCloudHost,
							cleverCloudPort, cleverCloudUser, cleverCloudPassword, cleverCloudDatabase);
				} else {
					System.out.println("Operación de importación remota cancelada por el usuario.");
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

		SwingUtilities.invokeLater(() -> {
			worker.execute();
		});
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

	

//	private boolean GenerarBackupMySQLRemoto(String ubicacion, String cleverCloudHost, String cleverCloudPort,
//			String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {
//
//		ventanaBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		ventanaBackUp.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		ventanaBackUp.getBtnGenerarB().setEnabled(false);
//		ventanaBackUp.getBtnImportarB().setEnabled(false);
//
//		PopupProgresoBackup popup = new PopupProgresoBackup(ventanaBackUp, "Generando backup remoto, espere...");
//		popup.mostrar();
//
//		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
//			@Override
//			protected Void doInBackground() {
//				String archivoTemporal = null;
//				try {
//					String nombreBaseLocal = (ubicacion.equalsIgnoreCase("Bariloche")) ? "ordenesbrc" : "ordenesbsas";
//					archivoTemporal = System.getProperty("java.io.tmpdir") + File.separator + "backup_"
//							+ nombreBaseLocal + "_" + System.currentTimeMillis() + ".sql";
//
//					// 1. Crear dump local
//					List<String> comando = Arrays.asList("C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump",
//							"--host=localhost", "--port=3306", "--user=root", "--password=root", "--single-transaction",
//							"--routines", "--triggers", "--no-create-db", nombreBaseLocal);
//					ProcessBuilder pb = new ProcessBuilder(comando);
//					pb.redirectOutput(new File(archivoTemporal));
//					Process proceso = pb.start();
//					int codigoSalida = proceso.waitFor();
//					if (codigoSalida != 0) {
//						JOptionPane.showMessageDialog(null, "Error al crear el archivo de backup local.");
//						return null;
//					}
//
//					// 2. Leer sentencias SQL del dump
//					List<String> sentencias = parseSqlStatements(archivoTemporal);
//					int totalSentencias = sentencias.size();
//					int sentenciasEjecutadas = 0;
//					int sentenciasIgnoradas = 0;
//
//					// 3. Conectar a Clever Cloud y limpiar base remota
//					String urlCleverCloud = String.format(
//							"jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true", cleverCloudHost,
//							cleverCloudPort, cleverCloudDatabase);
//					try (Connection conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser,
//							cleverCloudPassword); java.sql.Statement stmt = conexionRemota.createStatement()) {
//
//						limpiarBaseDatos(conexionRemota);
//						stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
//
//						// 4. Ejecutar sentencias y actualizar progreso
//						for (int i = 0; i < totalSentencias; i++) {
//							String s = sentencias.get(i).trim();
//							if (s.isEmpty() || s.startsWith("/*") || s.startsWith("--")
//									|| s.toUpperCase().startsWith("CREATE DATABASE")
//									|| s.toUpperCase().startsWith("USE ")
//									|| s.toUpperCase().startsWith("DROP DATABASE")) {
//								sentenciasIgnoradas++;
//								continue;
//							}
//							try {
//								stmt.execute(s);
//								sentenciasEjecutadas++;
//							} catch (SQLException e) {
//								if (!e.getMessage().contains("Access denied")) {
//									System.err.println("Error menor en sentencia (ignorado): " + e.getMessage());
//								}
//								sentenciasIgnoradas++;
//							}
//							int progreso = (int) (((i + 1) * 100.0) / totalSentencias);
//							publish(progreso);
//						}
//					}
//
//					JOptionPane.showMessageDialog(null, "Backup remoto completado exitosamente", "Backup Exitoso",
//							JOptionPane.INFORMATION_MESSAGE);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					JOptionPane.showMessageDialog(null, "Error durante el backup remoto: " + e.getMessage(),
//							"Error en Backup", JOptionPane.ERROR_MESSAGE);
//				} finally {
//					// Borra el archivo temporal
//					if (archivoTemporal != null) {
//						new File(archivoTemporal).delete();
//					}
//				}
//				return null;
//			}
//
//			@Override
//			protected void process(java.util.List<Integer> chunks) {
//				int ultimo = chunks.get(chunks.size() - 1);
//				popup.actualizarProgreso(ultimo);
//			}
//
//			@Override
//			protected void done() {
//				popup.cerrar();
//				ventanaBackUp.getGlassPane().setVisible(false);
//				ventanaBackUp.getBtnGenerarB().setEnabled(true);
//				ventanaBackUp.getBtnImportarB().setEnabled(true);
//			}
//		};
//
//		SwingUtilities.invokeLater(() -> {
//
//			worker.execute();
//		});
//
//		return true;
//	}
	
	
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

	                publish(5); // Iniciando proceso

	                // 1. Crear dump local
	                System.out.println("Creando dump de la base de datos local...");
	                List<String> comando = Arrays.asList("C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump",
	                        "--host=localhost", "--port=3306", "--user=root", "--password=root", "--single-transaction",
	                        "--routines", "--triggers", "--no-create-db", nombreBaseLocal);
	                ProcessBuilder pb = new ProcessBuilder(comando);
	                pb.redirectOutput(new File(archivoTemporal));
	                Process proceso = pb.start();
	                int codigoSalida = proceso.waitFor();
	                if (codigoSalida != 0) {
	                    JOptionPane.showMessageDialog(null, "Error al crear el archivo de backup local.");
	                    return null;
	                }
	                System.out.println("Dump local creado exitosamente.");

	                publish(15); // Dump creado

	                // 2. Leer sentencias SQL del dump
	                System.out.println("Leyendo sentencias SQL del dump...");
	                List<String> sentencias = parseSqlStatements(archivoTemporal);
	                int totalSentencias = sentencias.size();
	                System.out.println("Total de sentencias SQL: " + totalSentencias);

	                publish(20); // Sentencias leídas

	                // 3. Conectar a Clever Cloud y limpiar base remota
	                System.out.println("Conectando a Clever Cloud...");
	                String urlCleverCloud = String.format(
	                        "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true", cleverCloudHost,
	                        cleverCloudPort, cleverCloudDatabase);
	                
	                try (Connection conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser,
	                        cleverCloudPassword); java.sql.Statement stmt = conexionRemota.createStatement()) {

	                    System.out.println("Conectado a Clever Cloud.");
	                    publish(25); // Conectado

	                    System.out.println("Limpiando base de datos remota...");
	                    limpiarBaseDatos(conexionRemota);
	                    stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
	                    
	                    publish(30); // Base limpiada

	                    // 4. Ejecutar sentencias y actualizar progreso
	                    // Progreso: 30% inicial, 65% para ejecución de sentencias, 5% final
	                    System.out.println("Restaurando backup en Clever Cloud...");
	                    int sentenciasEjecutadas = 0;
	                    int sentenciasIgnoradas = 0;

	                    for (int i = 0; i < totalSentencias; i++) {
	                        String s = sentencias.get(i).trim();
	                        if (s.isEmpty() || s.startsWith("/*") || s.startsWith("--")
	                                || s.toUpperCase().startsWith("CREATE DATABASE")
	                                || s.toUpperCase().startsWith("USE ")
	                                || s.toUpperCase().startsWith("DROP DATABASE")) {
	                            sentenciasIgnoradas++;
	                            continue;
	                        }
	                        try {
	                            stmt.execute(s);
	                            sentenciasEjecutadas++;
	                        } catch (SQLException e) {
	                            if (!e.getMessage().contains("Access denied")) {
	                                System.err.println("Error menor en sentencia (ignorado): " + e.getMessage());
	                            }
	                            sentenciasIgnoradas++;
	                        }
	                        
	                        // Calcular progreso: 30% inicial + 65% proporcional a sentencias ejecutadas
	                        int progreso = 30 + (int) ((i + 1) * 65.0 / totalSentencias);
	                        publish(progreso);
	                    }

	                    System.out.println("Restauración completada:");
	                    System.out.println("- Sentencias ejecutadas exitosamente: " + sentenciasEjecutadas);
	                    System.out.println("- Sentencias ignoradas: " + sentenciasIgnoradas);
	                }

	                publish(100); // Completado

	                JOptionPane.showMessageDialog(null, "Backup remoto completado exitosamente", "Backup Exitoso",
	                        JOptionPane.INFORMATION_MESSAGE);

	            } catch (Exception e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Error durante el backup remoto: " + e.getMessage(),
	                        "Error en Backup", JOptionPane.ERROR_MESSAGE);
	            } finally {
	                // Borra el archivo temporal
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

	    SwingUtilities.invokeLater(() -> {
	        worker.execute();
	    });

	    return true;
	}
	
	
	
	
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
							"jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true",
							cleverCloudHost, cleverCloudPort, cleverCloudDatabase);
					conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser, cleverCloudPassword);
					conexionRemota.setAutoCommit(false);
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
					System.out.println("Conectado a la base de datos local exitosamente");

					publish(15);

					System.out.println("Limpiando base de datos local...");
					limpiarBaseDatos(conexionLocal);

					publish(20);

					// Migrar datos con reporte de progreso
					try (java.sql.Statement stmtRemoto = conexionRemota.createStatement()) {
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

							// Progreso: 20% inicial, 75% para migración, 5% final
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

		SwingUtilities.invokeLater(() -> {
			worker.execute();
		});

		try {
			worker.get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exitoso[0];
	}

	private void limpiarBaseDatos(Connection conexion) throws SQLException {
		try (java.sql.Statement stmt = conexion.createStatement()) {
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

	private void migrarDatosDirectamente(Connection conexionRemota, Connection conexionLocal) throws SQLException {
		try (java.sql.Statement stmtRemoto = conexionRemota.createStatement()) {
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
		try (java.sql.Statement stmtRemoto = conexionRemota.createStatement();
				java.sql.Statement stmtLocal = conexionLocal.createStatement()) {
			stmtLocal.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
			stmtLocal.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
			try (ResultSet rsCreate = stmtRemoto.executeQuery("SHOW CREATE TABLE `" + nombreTabla + "`")) {
				if (rsCreate.next()) {
					String createStatement = rsCreate.getString(2);
					stmtLocal.executeUpdate("DROP TABLE IF EXISTS `" + nombreTabla + "`");
					stmtLocal.executeUpdate(createStatement);
				}
			}
			int totalRegistros = 0;
			try (ResultSet rsCount = stmtRemoto.executeQuery("SELECT COUNT(*) FROM `" + nombreTabla + "`")) {
				if (rsCount.next())
					totalRegistros = rsCount.getInt(1);
			}
			if (totalRegistros > 0) {
				System.out.println("Tabla `" + nombreTabla + "` tiene " + totalRegistros + " registros.");
				migrarDatosEnLotes(nombreTabla, conexionRemota, conexionLocal, totalRegistros);
			} else {
				System.out.println("Tabla `" + nombreTabla + "` está¡ vacía.");
			}
			stmtLocal.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		}
	}

	/**
	 * CORRECCIÃ"N: Migra los datos usando executeUpdate individual para cada
	 * registro para preservar las fechas correctamente, evitando conversiones
	 * automÃ¡ticas del batch processing
	 */
	private void migrarDatosEnLotes(String nombreTabla, Connection conexionRemota, Connection conexionLocal,
			int totalRegistros) throws SQLException {
		final int TAMANO_LOTE = 1000;
		int registrosProcesados = 0;

		java.sql.Statement stmtRemoto = null;
		PreparedStatement pstmtLocal = null;
		ResultSet rsData = null;

		try {
			// Obtener estructura de la tabla
			stmtRemoto = conexionRemota.createStatement();
			rsData = stmtRemoto.executeQuery("SELECT * FROM `" + nombreTabla + "` LIMIT 1");
			ResultSetMetaData metaData = rsData.getMetaData();
			int columnCount = metaData.getColumnCount();
			rsData.close();

			// Preparar el INSERT statement para la BD local
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

			// Procesar datos en lotes
			int offset = 0;
			while (registrosProcesados < totalRegistros) {
				rsData = stmtRemoto
						.executeQuery("SELECT * FROM `" + nombreTabla + "` LIMIT " + TAMANO_LOTE + " OFFSET " + offset);

				while (rsData.next()) {
					// Establecer parÃ¡metros para el INSERT
					for (int i = 1; i <= columnCount; i++) {
						Object valor = rsData.getObject(i);
						pstmtLocal.setObject(i, valor);
					}

					// CORRECCIÓN: usar executeUpdate() individual en lugar de addBatch()
					// para preservar fechas correctamente
					pstmtLocal.executeUpdate();
					registrosProcesados++;
				}

				rsData.close();
				offset += TAMANO_LOTE;

				// Mostrar progreso
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
		try (java.sql.Statement stmt = conexionLocal.createStatement();
				ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
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
					try {
						File nombrebackup = archivoBackup.getSelectedFile();
						long tamañoArchivo = nombrebackup.length();

						Process p = Runtime.getRuntime().exec(
								"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql -uroot -proot ordenesbrc");

						try (OutputStream os = p.getOutputStream();
								FileInputStream fis = new FileInputStream(nombrebackup)) {
							byte[] buffer = new byte[8192];
							int leido;
							long totalLeido = 0;

							while ((leido = fis.read(buffer)) > 0) {
								os.write(buffer, 0, leido);
								totalLeido += leido;

								if (tamañoArchivo > 0) {
									int progreso = (int) ((totalLeido * 100.0) / tamañoArchivo);
									publish(progreso);
								}
							}
						}

						p.waitFor();
						JOptionPane.showMessageDialog(null, "Base de datos actualizada correctamente.",
								"Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error al actualizar la base de datos: " + e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
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
					ventanaBackUp.setCursor(Cursor.getDefaultCursor());
					ventanaBackUp.getBtnGenerarB().setEnabled(true);
					ventanaBackUp.getBtnImportarB().setEnabled(true);
					popup.cerrar();
				}
			};

			SwingUtilities.invokeLater(() -> {
				worker.execute();
			});
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
				try {
					String nombreAguardar = ventanaOpcionesBackup.getTxtNombreArchivo().getText();
					String rutaAguardar = ventanaOpcionesBackup.getTxtRutaArchivo().getText();
					File backupFile = new File(rutaAguardar + nombreAguardar);

					String nombreBaseLocal = (agenda.getUbicacionBase().equalsIgnoreCase("Bariloche")) ? "ordenesbrc"
							: "ordenesbsas";
					Process child = Runtime.getRuntime().exec(
							"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=root --user=root --databases "
									+ nombreBaseLocal);

					// Contar líneas totales para el progreso
					int totalLineas = 0;
					try (BufferedReader br = new BufferedReader(new InputStreamReader(child.getInputStream()))) {
						while (br.readLine() != null)
							totalLineas++;
					}

					// Volver a ejecutar el proceso para escribir el archivo y mostrar progreso
					child = Runtime.getRuntime().exec(
							"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=root --user=root --databases "
									+ nombreBaseLocal);
					try (BufferedReader br = new BufferedReader(new InputStreamReader(child.getInputStream()));
							FileWriter fw = new FileWriter(backupFile)) {
						String line;
						int lineasLeidas = 0;
						while ((line = br.readLine()) != null) {
							fw.write(line + "\n");
							lineasLeidas++;
							if (totalLineas > 0) {
								int progreso = (int) ((lineasLeidas * 100.0) / totalLineas);
								publish(progreso);
							}
						}
					}
					child.waitFor();
					JOptionPane.showMessageDialog(null, "Archivo de backup generado exitosamente.", "Backup Exitoso",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al generar el backup: " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
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
				// ventanaBackUp.setCursor(Cursor.getDefaultCursor());
				ventanaOpcionesBackup.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				ventanaBackUp.getBtnGenerarB().setEnabled(true);
				ventanaBackUp.getBtnImportarB().setEnabled(true);
				popup.cerrar();
			}
		};

		SwingUtilities.invokeLater(() -> {

			worker.execute();
		});

	}


	// Lee el dump y separa sentencias SQL correctamente, ignorando los ; dentro de
	// strings
	private List<String> parseSqlStatements(String filePath) throws IOException {
		List<String> statements = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			int c;
			while ((c = reader.read()) != -1) {
				char ch = (char) c;
				sb.append(ch);

				if (ch == '\'' && !inDoubleQuote) {
					// Maneja comillas simples escapadas
					inSingleQuote = !inSingleQuote;
				} else if (ch == '"' && !inSingleQuote) {
					inDoubleQuote = !inDoubleQuote;
				} else if (ch == ';' && !inSingleQuote && !inDoubleQuote) {
					// Fin de sentencia SQL real
					statements.add(sb.toString().trim());
					sb.setLength(0);
				}
			}
			// Agrega lo que quede (por si el archivo no termina en ;)
			if (sb.length() > 0 && sb.toString().trim().length() > 0) {
				statements.add(sb.toString().trim());
			}
		}
		return statements;
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
package presentacion.controlador;

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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.xdevapi.Statement;

import modelo.Agenda;
import persistencia.conexion.Conexion;
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
	private String cleverCloudPortBRC = "3306" ; 
	private String cleverCloudUserBRC = "uhhm5ckiyyizik8y";
	private String cleverCloudPasswordBRC = "TXJcnVkA9yW9JDaUNg0a"; 
	private String cleverCloudDatabaseBRC = "b1zeyndbfc1bmeiernaw"; 
	
	
	private String cleverCloudHostBSAS = "bewqn4ds4dxour1xkgu6-mysql.services.clever-cloud.com"; 
	private String cleverCloudPortBSAS = "3306" ; 
	private String cleverCloudUserBSAS = "uocexuvpspnbuath";
	private String cleverCloudPasswordBSAS = "waHWGTIYsS52IV0ZiOLU"; 
	private String cleverCloudDatabaseBSAS = "bewqn4ds4dxour1xkgu6"; 
	 
	
	
	

	@SuppressWarnings("unused")
	private Agenda agenda;

	public ControladorBackup(VentanaBackUp ventanaBackUp, Agenda agenda) {

		this.ventanaBackUp = ventanaBackUp;

		this.agenda = agenda;
		this.ventanaBackUp.getBtnGenerarB().addActionListener(this);
		this.ventanaBackUp.getBtnImportarB().addActionListener(this);

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

			String cleverCloudHost = ""; 
			String cleverCloudPort = "" ; 
			String cleverCloudUser = "";
			String cleverCloudPassword = ""; 
			String cleverCloudDatabase = ""; 
			
			
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
//				this.ventanaBackUp.dispose();
//				this.ventanaBackUp = null;

			} else if (seleccion == ventanaBackUp.getRdbtnRemoto().getModel()) {	
				
				
				// Dentro del ActionListener del botón getBtnGenerarB
				int opcion = JOptionPane.showConfirmDialog(null,
						"Se sobre escribirá el archivo anterior remoto. ¿Desea continuar?",
						"Confirmar Backup Remoto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					
					
					// Aquí llamas al método que realiza el backup remoto
					GenerarBackupMySQLRemoto( agenda.getUbicacionBase(),cleverCloudHost, cleverCloudPort,cleverCloudUser, cleverCloudPassword, cleverCloudDatabase);

				} else {
					// Cancelar la operación
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
				
								
				// Dentro del ActionListener del botón getBtnGenerarB
				int opcion = JOptionPane.showConfirmDialog(null,
						"Se sobre escribirá la base local. ¿Desea continuar?",
						"Confirmar importación Remota", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					// Aquí llamas al método que realiza el backup remoto
					ActualizarBackupMySQLremoto(agenda.getUbicacionBase(),cleverCloudHostBRC, cleverCloudPortBRC,cleverCloudUserBRC, cleverCloudPasswordBRC, cleverCloudDatabaseBRC);

				} else {
					// Cancelar la operación
					System.out.println("Operación de backup remoto cancelada por el usuario.");
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

			int resp;
			resp = cambiarNombreRuta.showSaveDialog(ventanaOpcionesBackup);

			if (resp == JFileChooser.APPROVE_OPTION) {

				ventanaOpcionesBackup.getTxtNombreArchivo()
						.setText(String.valueOf(cambiarNombreRuta.getSelectedFile().getName()) + ".sql");
				ventanaOpcionesBackup.getTxtRutaArchivo()
						.setText(String.valueOf(cambiarNombreRuta.getCurrentDirectory()) + "\\");

			} else if (resp == JFileChooser.CANCEL_OPTION) {

			}

		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnResetDatos()) {

			ventanaOpcionesBackup.getTxtNombreArchivo().setText(NombreBackUp);
			ventanaOpcionesBackup.getTxtRutaArchivo().setText(rutadefaultBackup);

		}

	}


/**
 * Actualiza la base de datos local descargando el backup desde Clever Cloud.
 * Versión corregida que soluciona problemas con datos vacíos.
 */
public boolean ActualizarBackupMySQLremoto(String ubicacion, String cleverCloudHost, String cleverCloudPort, 
                                         String cleverCloudUser, String cleverCloudPassword, 
                                         String cleverCloudDatabase) {
    
    Connection conexionRemota = null;
    Connection conexionLocal = null;
    boolean exitoso = false;
    String archivoTemporal = null;
    
    try {
        // Determinar nombre de la base de datos local
        String nombreBaseLocal = "";
        if (ubicacion.compareToIgnoreCase("Bariloche") == 0) {
            nombreBaseLocal = "ordenesbrc";
        } else if (ubicacion.compareToIgnoreCase("Buenos Aires") == 0) {
            nombreBaseLocal = "ordenesbsas";
        } else {
            nombreBaseLocal = ubicacion.toLowerCase().replaceAll("\\s+", "");
        }

        System.out.println("🔄 Iniciando actualización de " + nombreBaseLocal + " desde Clever Cloud...");
        
        // Crear archivo temporal para el dump
        archivoTemporal = System.getProperty("java.io.tmpdir") + File.separator + 
                         "backup_remote_" + cleverCloudDatabase + "_" + System.currentTimeMillis() + ".sql";
        
        // PASO 1: Conectar a Clever Cloud
        System.out.println("🌐 Conectando a Clever Cloud para extraer datos...");
        
        String urlCleverCloud = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true",
                cleverCloudHost, cleverCloudPort, cleverCloudDatabase);
        
        conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser, cleverCloudPassword);
        conexionRemota.setAutoCommit(false); // Importante para consistencia
        
        System.out.println("✅ Conectado a Clever Cloud exitosamente");
        
        // PASO 2: Obtener conexión local
        System.out.println("🏠 Conectando a la base de datos local...");
        
        Conexion instanciaConexion = Conexion.getConexion(ubicacion);
        conexionLocal = instanciaConexion.getSQLConexion();
        
        if (conexionLocal == null || conexionLocal.isClosed()) {
            System.err.println("❌ No se pudo conectar a la base de datos local");
            JOptionPane.showMessageDialog(null, "Error: No se pudo conectar a la base de datos local");
            return false;
        }
        
        conexionLocal.setAutoCommit(false); // Importante para rollback si hay errores
        System.out.println("✅ Conectado a la base de datos local exitosamente");
        
        // PASO 3: Limpiar la base de datos local
        System.out.println("🧹 Limpiando base de datos local...");
        limpiarBaseDatosLocal(conexionLocal);
        
        // PASO 4: Migrar datos tabla por tabla (MÉTODO MEJORADO)
        migrarDatosDirectamente(conexionRemota, conexionLocal);
        
        // PASO 5: Confirmar transacciones
        conexionLocal.commit();
        conexionRemota.commit();
        
        exitoso = true;
        
        // Verificar resultados
        verificarMigracion(conexionLocal);
        
        JOptionPane.showMessageDialog(null, 
            "✅ Actualización completada exitosamente!\n\n" +
            "Origen: " + cleverCloudDatabase + " en Clever Cloud\n" +
            "Destino: " + nombreBaseLocal + " (local)",
            "Actualización Exitosa", 
            JOptionPane.INFORMATION_MESSAGE);
        
    } catch (Exception e) {
        System.err.println("❌ Error durante el proceso de actualización: " + e.getMessage());
        e.printStackTrace();
        
        // Rollback en caso de error
        try {
            if (conexionLocal != null) {
                conexionLocal.rollback();
            }
            if (conexionRemota != null) {
                conexionRemota.rollback();
            }
        } catch (SQLException rollbackEx) {
            System.err.println("❌ Error durante rollback: " + rollbackEx.getMessage());
        }
        
        String mensajeError = "Error durante la actualización desde Clever Cloud:\n" + e.getMessage();
        JOptionPane.showMessageDialog(null, mensajeError, "Error en Actualización", JOptionPane.ERROR_MESSAGE);
        
    } finally {
        // Limpiar recursos
        try {
            if (conexionRemota != null && !conexionRemota.isClosed()) {
                conexionRemota.setAutoCommit(true);
                conexionRemota.close();
                System.out.println("🔌 Conexión a Clever Cloud cerrada");
            }
            if (conexionLocal != null && !conexionLocal.isClosed()) {
                conexionLocal.setAutoCommit(true);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error al cerrar recursos: " + e.getMessage());
        }
        
        // Eliminar archivo temporal si se creó
        if (archivoTemporal != null) {
            File archivoTemp = new File(archivoTemporal);
            if (archivoTemp.exists()) {
                archivoTemp.delete();
            }
        }
    }
    
    return exitoso;
}

/**
 * Limpia la base de datos local eliminando todas las tablas
 */
private void limpiarBaseDatosLocal(Connection conexionLocal) throws SQLException {
    java.sql.Statement stmt = null;
    ResultSet rs = null;
    try {
        stmt = conexionLocal.createStatement();
        
        // Desactivar verificación de claves foráneas
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        
        // Obtener lista de todas las tablas
        rs = stmt.executeQuery("SHOW TABLES");
        List<String> tablas = new ArrayList<>();
        while (rs.next()) {
            tablas.add(rs.getString(1));
        }
        
        // Eliminar todas las tablas existentes
        for (String tabla : tablas) {
            try {
                stmt.executeUpdate("DROP TABLE IF EXISTS `" + tabla + "`");
                System.out.println("🗑️ Tabla eliminada: " + tabla);
            } catch (SQLException e) {
                System.err.println("⚠️ Error al eliminar tabla " + tabla + ": " + e.getMessage());
            }
        }
        
        System.out.println("✅ Base de datos local limpiada, " + tablas.size() + " tablas eliminadas");
        
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
    }
}

/**
 * Migra los datos directamente de una conexión a otra, tabla por tabla
 * Este método evita problemas con archivos temporales y dumps SQL grandes
 */
private void migrarDatosDirectamente(Connection conexionRemota, Connection conexionLocal) throws SQLException {
    java.sql.Statement stmtRemoto = null;
    ResultSet rsTablas = null;
    
    try {
        stmtRemoto = conexionRemota.createStatement();
        
        // Obtener lista de tablas desde la BD remota
        rsTablas = stmtRemoto.executeQuery("SHOW TABLES");
        List<String> tablas = new ArrayList<>();
        while (rsTablas.next()) {
            tablas.add(rsTablas.getString(1));
        }
        
        System.out.println("📋 Encontradas " + tablas.size() + " tablas para migrar");
        
        // Migrar cada tabla
        for (String tabla : tablas) {
            migrarTabla(tabla, conexionRemota, conexionLocal);
        }
        
        // Reactivar verificación de claves foráneas
        java.sql.Statement stmtLocal = conexionLocal.createStatement();
        stmtLocal.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        stmtLocal.close();
        
    } finally {
        if (rsTablas != null) try { rsTablas.close(); } catch (SQLException e) {}
        if (stmtRemoto != null) try { stmtRemoto.close(); } catch (SQLException e) {}
    }
}

/**
 * Migra una tabla específica de la conexión remota a la local
 */
private void migrarTabla(String nombreTabla, Connection conexionRemota, Connection conexionLocal) throws SQLException {
    java.sql.Statement stmtRemoto = null;
    java.sql.Statement stmtLocal = null;
    ResultSet rsCreate = null;
    ResultSet rsData = null;
    
    try {
        System.out.println("🔄 Migrando tabla: " + nombreTabla);
        
        stmtRemoto = conexionRemota.createStatement();
        stmtLocal = conexionLocal.createStatement();
        
        // Desactivar verificación de claves foráneas en la conexión local
        stmtLocal.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        
        // 1. Obtener y ejecutar CREATE TABLE
        rsCreate = stmtRemoto.executeQuery("SHOW CREATE TABLE `" + nombreTabla + "`");
        if (rsCreate.next()) {
            String createStatement = rsCreate.getString(2);
            
            // Ejecutar DROP y CREATE en la BD local
            stmtLocal.executeUpdate("DROP TABLE IF EXISTS `" + nombreTabla + "`");
            stmtLocal.executeUpdate(createStatement);
            
            System.out.println("✅ Estructura de tabla `" + nombreTabla + "` creada");
        }
        
        // 2. Contar registros para mostrar progreso
        ResultSet rsCount = stmtRemoto.executeQuery("SELECT COUNT(*) FROM `" + nombreTabla + "`");
        int totalRegistros = 0;
        if (rsCount.next()) {
            totalRegistros = rsCount.getInt(1);
        }
        rsCount.close();
        
        if (totalRegistros == 0) {
            System.out.println("ℹ️ Tabla `" + nombreTabla + "` está vacía");
            return;
        }
        
        System.out.println("📊 Tabla `" + nombreTabla + "` tiene " + totalRegistros + " registros");
        
        // 3. Migrar datos en lotes para evitar problemas de memoria
        migrarDatosEnLotes(nombreTabla, conexionRemota, conexionLocal, totalRegistros);
        
        System.out.println("✅ Tabla `" + nombreTabla + "` migrada completamente");
        
    } finally {
        if (rsData != null) try { rsData.close(); } catch (SQLException e) {}
        if (rsCreate != null) try { rsCreate.close(); } catch (SQLException e) {}
        if (stmtRemoto != null) try { stmtRemoto.close(); } catch (SQLException e) {}
        if (stmtLocal != null) try { stmtLocal.close(); } catch (SQLException e) {}
    }
}

/**
 * Migra los datos de una tabla en lotes pequeños para mejorar la performance
 * y evitar problemas de memoria con tablas grandes
 */
private void migrarDatosEnLotes(String nombreTabla, Connection conexionRemota, 
                               Connection conexionLocal, int totalRegistros) throws SQLException {
    
    final int TAMANO_LOTE = 1000; // Procesar de a 1000 registros
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
            if (i > 1) insertSQL.append(", ");
            insertSQL.append("`").append(metaData.getColumnName(i)).append("`");
        }
        insertSQL.append(") VALUES (");
        
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) insertSQL.append(", ");
            insertSQL.append("?");
        }
        insertSQL.append(")");
        
        pstmtLocal = conexionLocal.prepareStatement(insertSQL.toString());
        
        // Procesar datos en lotes
        int offset = 0;
        while (registrosProcesados < totalRegistros) {
            rsData = stmtRemoto.executeQuery("SELECT * FROM `" + nombreTabla + "` LIMIT " + TAMANO_LOTE + " OFFSET " + offset);
            
            int registrosEnLote = 0;
            while (rsData.next()) {
                // Establecer parámetros para el INSERT
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rsData.getObject(i);
                    pstmtLocal.setObject(i, valor);
                }
                
                pstmtLocal.executeUpdate();
                registrosEnLote++;
                registrosProcesados++;
            }
            
            rsData.close();
            offset += TAMANO_LOTE;
            
            // Mostrar progreso
            System.out.println("   📈 Procesados " + registrosProcesados + "/" + totalRegistros + " registros");
        }
        
    } finally {
        if (rsData != null) try { rsData.close(); } catch (SQLException e) {}
        if (pstmtLocal != null) try { pstmtLocal.close(); } catch (SQLException e) {}
        if (stmtRemoto != null) try { stmtRemoto.close(); } catch (SQLException e) {}
    }
}

/**
 * Verifica que la migración se haya completado correctamente
 */
private void verificarMigracion(Connection conexionLocal) throws SQLException {
    java.sql.Statement stmt = null;
    ResultSet rs = null;
    
    try {
        stmt = conexionLocal.createStatement();
        rs = stmt.executeQuery("SHOW TABLES");
        
        int totalTablas = 0;
        int totalRegistros = 0;
        
        while (rs.next()) {
            String nombreTabla = rs.getString(1);
            totalTablas++;
            
            // Contar registros en cada tabla
            java.sql.Statement stmtCount = conexionLocal.createStatement();
            ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(*) FROM `" + nombreTabla + "`");
            if (rsCount.next()) {
                int registrosTabla = rsCount.getInt(1);
                totalRegistros += registrosTabla;
                System.out.println("📊 Tabla `" + nombreTabla + "`: " + registrosTabla + " registros");
            }
            rsCount.close();
            stmtCount.close();
        }
        
        System.out.println("✅ Verificación completada:");
        System.out.println("   📋 Tablas creadas: " + totalTablas);
        System.out.println("   📊 Total de registros: " + totalRegistros);
        
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
    }
}

   
	    
	    
	    
	    
	@SuppressWarnings({ "unused", "rawtypes" })
	private void ActualizarBackupMySQLlocal() {
		// TODO Auto-generated method stub
		String bd = "ordenesbrc";
		String login = "root";
		String password = "root";
		String url = "jdbc:mysql://localhost:3306/" + bd;
		String urlcero = "jdbc:mysql://localhost/";
		ConnectionUrl conn = null;

		JFileChooser archivoBackup = new JFileChooser(rutadefaultBackup);

		FileNameExtensionFilter sqlFilter = new FileNameExtensionFilter("Bases de datos SQL", "sql");
		archivoBackup.setFileFilter(sqlFilter);

		int resp;
		resp = archivoBackup.showOpenDialog(ventanaBackUp);
		if (resp == JFileChooser.APPROVE_OPTION) {

			JDialog popup = new JDialog();
			popup.setTitle("Procesando");
			popup.setModal(false);
			popup.setSize(300, 100);
			popup.setLocationRelativeTo(ventanaOpcionesBackup);
			popup.add(new JLabel("Actualizando Base de Datos, espere...", SwingConstants.CENTER));

			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				Statement stm;
				File nombrebackup = null;
				File rutabackup = null;
				int selecGuardaBack = 0;
				int selecRestauraBack = 1;

				@Override
				protected Void doInBackground() {
					try {
						Statement sentencia = null;
						ConnectionUrl coneccionini = null;
						if (selecRestauraBack == 1) {

							try {
								nombrebackup = new File(archivoBackup.getSelectedFile().toString().trim());

								Process p = Runtime.getRuntime().exec(
										"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql -uroot -proot ordenesbrc");

								OutputStream os = p.getOutputStream();
								FileInputStream fis = new FileInputStream(nombrebackup);
								byte[] buffer = new byte[1000];

								int leido = fis.read(buffer);
								while (leido > 0) {
									os.write(buffer, 0, leido);
									leido = fis.read(buffer);
								}

								os.flush();
								os.close();
								fis.close();

								JOptionPane.showMessageDialog(null, "Base Actualizada", "Actualización",
										JOptionPane.INFORMATION_MESSAGE);

							} catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										"Error no se actualizo la DB por el siguiente motivo: " + e.getMessage(),
										"Verificar", JOptionPane.ERROR_MESSAGE);
								popup.dispose();
							}

						} else {
							JOptionPane.showMessageDialog(null, "Ha sido cancelada la actualizacion del Backup");
							popup.dispose();
						}

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Error no se genero el archivo por el siguiente motivo:" + e.getMessage(), "Verificar",
								JOptionPane.ERROR_MESSAGE);
						popup.dispose();
					}
					return null;
				}

				@Override
				protected void done() {
					// Cerrar el popup después de completar el envío
					popup.dispose();

				}
			};

			// Mostrar el popup y ejecutar el SwingWorker
			SwingUtilities.invokeLater(() -> {
				popup.setVisible(true);
				worker.execute();
			});

		}
	}

	private void GenerarBackupMySQLLocal() {

		JDialog popup = new JDialog();
		popup.setTitle("Procesando");
		popup.setModal(false);
		popup.setSize(300, 100);
		popup.setLocationRelativeTo(ventanaOpcionesBackup);
		popup.add(new JLabel("Generando BackUp, espere...", SwingConstants.CENTER));

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() {
				try {
					String nombreAguardar = ventanaOpcionesBackup.getTxtNombreArchivo().getText();
					String rutaAguardar = ventanaOpcionesBackup.getTxtRutaArchivo().getText();

					Runtime runtime = Runtime.getRuntime();
					File backupFile = new File(rutaAguardar + nombreAguardar);
					FileWriter fw = new FileWriter(backupFile);

					Process child = runtime.exec(
							"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=root --user=root --databases ordenesbrc");

//					Process child = runtime.exec(
//							"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump --opt --password=root --user=root --databases ordenesbrc");

					InputStreamReader irs = new InputStreamReader(child.getInputStream());
					BufferedReader br = new BufferedReader(irs);
					String line;
					while ((line = br.readLine()) != null) {
						fw.write(line + "\n");
					}
					fw.close();
					irs.close();
					br.close();

					Object mje = "<html><center>Archivo generado<html>";
					JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Error no se genero el archivo por el siguiente motivo:" + e.getMessage(), "Verificar",
							JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}

			@Override
			protected void done() {
				// Cerrar el popup después de completar el envío
				popup.dispose();

			}
		};

		// Mostrar el popup y ejecutar el SwingWorker
		SwingUtilities.invokeLater(() -> {
			popup.setVisible(true);
			worker.execute();
		});

	}

	public boolean GenerarBackupMySQLRemoto(String ubicacion, String cleverCloudHost, String cleverCloudPort, 
	                                       String cleverCloudUser, String cleverCloudPassword, 
	                                       String cleverCloudDatabase) {
	    
	    Connection conexionRemota = null;
	    FileInputStream fileInput = null;
	    boolean exitoso = false;
	    String archivoTemporal = null;
	    
	    try {
	        // Obtener la instancia de conexión para la ubicación especificada
	        Conexion instanciaConexion = Conexion.getConexion(ubicacion);
	        Connection conexionLocal = instanciaConexion.getSQLConexion();
	        
	        // Validar que tengamos una conexión local activa
	        if (conexionLocal == null || conexionLocal.isClosed()) {
	            System.err.println("❌ No hay conexión local activa para realizar el backup");
	            JOptionPane.showMessageDialog(null, "Error: No hay conexión local activa para realizar el backup");
	            return false;
	        }

	        // Obtener información de la conexión (valores fijos de la clase Conexion)
	        String hostLocal = "localhost";
	        String portLocal = "3306";
	        String userLocal = "root";
	        String passwordLocal = "root";
	        
	        // Determinar nombre de la base de datos actual
	        String nombreBaseLocal = "";
	        if (ubicacion.compareToIgnoreCase("Bariloche") == 0) {
	            nombreBaseLocal = "ordenesbrc";
	        } else if (ubicacion.compareToIgnoreCase("Buenos Aires") == 0) {
	            nombreBaseLocal = "ordenesbsas";
	        } else {
	            nombreBaseLocal = ubicacion.toLowerCase().replaceAll("\\s+", "");
	        }

	        System.out.println("🔄 Iniciando backup de " + nombreBaseLocal + " hacia Clever Cloud...");
	        
	        // Crear archivo temporal para el dump
	        archivoTemporal = System.getProperty("java.io.tmpdir") + File.separator + 
	                         "backup_" + nombreBaseLocal + "_" + System.currentTimeMillis() + ".sql";
	        
	        // PASO 1: Crear dump de la base de datos local usando mysqldump
	        System.out.println("📤 Creando dump de la base de datos local...");
	        
	        ProcessBuilder processBuilder = new ProcessBuilder();
	        
	        // Construir comando mysqldump con opciones optimizadas para Clever Cloud
	        List<String> comando = Arrays.asList(
	            "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump",
	            "--host=" + hostLocal,
	            "--port=" + portLocal,
	            "--user=" + userLocal,
	            "--password=" + passwordLocal,
	            "--single-transaction",           // Consistencia de datos
	            "--routines",                     // Incluir procedimientos y funciones
	            "--triggers",                     // Incluir triggers
	            "--add-drop-database",            // Agregar DROP DATABASE
	            "--add-drop-table",               // Agregar DROP TABLE
	            "--create-options",               // Incluir opciones CREATE
	            "--disable-keys",                 // Mejorar velocidad de importación
	            "--extended-insert",              // Inserción optimizada
	            "--hex-blob",                     // Manejar datos binarios correctamente
	            "--lock-tables=false",            // No bloquear tablas
	            "--no-autocommit",               // Control manual de transacciones
	            "--databases",                    // Especificar que es una base completa
	            nombreBaseLocal
	        );
	        
	        processBuilder.command(comando);
	        processBuilder.redirectOutput(new File(archivoTemporal));
	        processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);
	        
	        Process proceso = processBuilder.start();
	        
	        // Leer errores si los hay
	        StringBuilder errores = new StringBuilder();
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()))) {
	            String linea;
	            while ((linea = reader.readLine()) != null) {
	                errores.append(linea).append("\n");
	            }
	        }
	        
	        int codigoSalida = proceso.waitFor();
	        
	        if (codigoSalida != 0) {
	            System.err.println("❌ Error al crear dump: " + errores.toString());
	            JOptionPane.showMessageDialog(null, "Error al crear dump de la base de datos:\n" + errores.toString());
	            return false;
	        }
	        
	        // Verificar que el archivo se creó y tiene contenido
	        File archivoDump = new File(archivoTemporal);
	        if (!archivoDump.exists() || archivoDump.length() == 0) {
	            System.err.println("❌ El archivo de dump no se creó correctamente");
	            JOptionPane.showMessageDialog(null, "Error: El archivo de backup no se generó correctamente");
	            return false;
	        }
	        
	        System.out.println("✅ Dump creado exitosamente: " + archivoDump.length() + " bytes");
	        
	        // PASO 2: Conectar a Clever Cloud (directamente a la base de datos asignada)
	        System.out.println("🌐 Conectando a Clever Cloud...");
	        
	        
	        
	        String urlCleverCloud = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=true&autoReconnect=true",
	                cleverCloudHost, cleverCloudPort, cleverCloudDatabase);
	        
	            
	        
	        conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser, cleverCloudPassword);
	        
	        System.out.println("✅ Conectado a Clever Cloud exitosamente");
	        
	        // PASO 3: Limpiar la base de datos destino (eliminar tablas existentes)
	        System.out.println("🧹 Limpiando base de datos destino...");
	        java.sql.Statement stmt1 = null;
	        java.sql.ResultSet rs1 = null;
	        try {
	            stmt1 = conexionRemota.createStatement();
	            
	            // Desactivar verificación de claves foráneas para poder eliminar tablas
	            stmt1.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
	            
	            // Obtener lista de todas las tablas
	            rs1 = stmt1.executeQuery("SHOW TABLES");
	            List<String> tablas = new ArrayList<>();
	            while (rs1.next()) {
	                tablas.add(rs1.getString(1));
	            }
	            
	            // Eliminar todas las tablas existentes
	            for (String tabla : tablas) {
	                try {
	                    stmt1.executeUpdate("DROP TABLE IF EXISTS `" + tabla + "`");
	                    System.out.println("🗑️ Tabla eliminada: " + tabla);
	                } catch (SQLException e) {
	                    System.err.println("⚠️ Error al eliminar tabla " + tabla + ": " + e.getMessage());
	                }
	            }
	            
	            // Reactivar verificación de claves foráneas
	            stmt1.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	            
	            System.out.println("✅ Base de datos limpiada, " + tablas.size() + " tablas eliminadas");
	            
	        } finally {
	            if (rs1 != null) {
	                try {
	                    rs1.close();
	                } catch (SQLException e) {
	                    System.err.println("⚠️ Error al cerrar ResultSet: " + e.getMessage());
	                }
	            }
	            if (stmt1 != null) {
	                try {
	                    stmt1.close();
	                } catch (SQLException e) {
	                    System.err.println("⚠️ Error al cerrar statement: " + e.getMessage());
	                }
	            }
	        }
	        
	        // PASO 4: Leer y ejecutar el dump SQL
	        System.out.println("📥 Restaurando backup en Clever Cloud...");
	        
	        StringBuilder sqlContent = new StringBuilder();
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new InputStreamReader(new FileInputStream(archivoTemporal), "UTF-8"));
	            String linea;
	            while ((linea = reader.readLine()) != null) {
	                sqlContent.append(linea).append("\n");
	            }
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    System.err.println("⚠️ Error al cerrar BufferedReader: " + e.getMessage());
	                }
	            }
	        }
	        
	        // Limpiar el dump SQL para Clever Cloud
	        String sqlLimpio = sqlContent.toString()
	            .replaceAll("(?m)^CREATE DATABASE.*$", "") // Eliminar CREATE DATABASE
	            .replaceAll("(?m)^USE.*$", "")              // Eliminar USE database
	            .replaceAll("(?m)^DROP DATABASE.*$", "");   // Eliminar DROP DATABASE
	        
	        // Dividir el contenido en sentencias individuales
	        String[] sentencias = sqlLimpio.split(";");
	        int sentenciasEjecutadas = 0;
	        int totalSentencias = sentencias.length;
	        
	        java.sql.Statement stmt3 = null;
	        try {
	            stmt3 = conexionRemota.createStatement();
	            for (String sentencia : sentencias) {
	                sentencia = sentencia.trim();
	                if (!sentencia.isEmpty() && !sentencia.startsWith("--") && !sentencia.startsWith("/*")) {
	                    try {
	                        stmt3.executeUpdate(sentencia);
	                        sentenciasEjecutadas++;
	                        
	                        // Mostrar progreso cada 100 sentencias
	                        if (sentenciasEjecutadas % 100 == 0) {
	                            System.out.println("📊 Progreso: " + sentenciasEjecutadas + "/" + totalSentencias + " sentencias ejecutadas");
	                        }
	                    } catch (SQLException e) {
	                        // Ignorar errores comunes que no afectan la restauración
	                        if (!e.getMessage().contains("already exists") && 
	                            !e.getMessage().contains("Unknown database") &&
	                            !e.getMessage().contains("Duplicate entry")) {
	                            System.err.println("⚠️ Error en sentencia (continuando): " + e.getMessage());
	                        }
	                    }
	                }
	            }
	        } finally {
	            if (stmt3 != null) {
	                try {
	                    stmt3.close();
	                } catch (SQLException e) {
	                    System.err.println("⚠️ Error al cerrar statement: " + e.getMessage());
	                }
	            }
	        }
	        
	        System.out.println("✅ Backup restaurado exitosamente en Clever Cloud");
	        System.out.println("📊 Total de sentencias ejecutadas: " + sentenciasEjecutadas);
	        
	        // PASO 5: Verificar que los datos se subieron correctamente
	        java.sql.Statement stmt4 = null;
	        java.sql.ResultSet rs = null;
	        try {
	            stmt4 = conexionRemota.createStatement();
	            rs = stmt4.executeQuery("SHOW TABLES");
	            int tablasContadas = 0;
	            while (rs.next()) {
	                tablasContadas++;
	            }
	            System.out.println("✅ Verificación completada: " + tablasContadas + " tablas encontradas en destino");
	        } finally {
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException e) {
	                    System.err.println("⚠️ Error al cerrar ResultSet: " + e.getMessage());
	                }
	            }
	            if (stmt4 != null) {
	                try {
	                    stmt4.close();
	                } catch (SQLException e) {
	                    System.err.println("⚠️ Error al cerrar statement: " + e.getMessage());
	                }
	            }
	        }
	        
	        exitoso = true;
	        JOptionPane.showMessageDialog(null, 
	            "✅ Backup completado exitosamente!\n\n" +
	            "Base de datos: " + nombreBaseLocal + "\n" +
	            "Destino: " + cleverCloudDatabase + " en Clever Cloud\n" +
	            "Sentencias ejecutadas: " + sentenciasEjecutadas,
	            "Backup Exitoso", 
	            JOptionPane.INFORMATION_MESSAGE);
	        
	    } catch (Exception e) {
	        System.err.println("❌ Error durante el proceso de backup: " + e.getMessage());
	        e.printStackTrace();
	        
	        String mensajeError = "Error durante el backup remoto:\n" + e.getMessage();
	        if (e instanceof SQLException) {
	            mensajeError += "\n\nDetalles SQL: " + ((SQLException) e).getSQLState();
	        }
	        
	        JOptionPane.showMessageDialog(null, mensajeError, "Error en Backup", JOptionPane.ERROR_MESSAGE);
	        
	    } finally {
	        // Limpiar recursos
	        try {
	            if (fileInput != null) {
	                fileInput.close();
	            }
	            if (conexionRemota != null && !conexionRemota.isClosed()) {
	                conexionRemota.close();
	                System.out.println("🔌 Conexión a Clever Cloud cerrada");
	            }
	        } catch (Exception e) {
	            System.err.println("⚠️ Error al cerrar recursos: " + e.getMessage());
	        }
	        
	        // Eliminar archivo temporal
	        if (archivoTemporal != null) {
	            File archivoTemp = new File(archivoTemporal);
	            if (archivoTemp.exists()) {
	                if (archivoTemp.delete()) {
	                    System.out.println("🗑️ Archivo temporal eliminado");
	                } else {
	                    System.err.println("⚠️ No se pudo eliminar el archivo temporal: " + archivoTemporal);
	                }
	            }
	        }
	    }
	    
	    return exitoso;
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

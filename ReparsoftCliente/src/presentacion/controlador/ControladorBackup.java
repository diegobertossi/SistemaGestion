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
import javax.swing.JFrame;
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
						"Se sobreescribirá la base de datos local. ¿Desea continuar?", "Confirmar Importación Remota",
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


		JDialog popup = new JDialog();
		popup.setTitle("Procesando");
		popup.setModal(false);
		popup.setSize(300, 100);
		popup.setLocationRelativeTo(ventanaBackUp);
		popup.add(new JLabel("Actualizando desde la nube, espere...", SwingConstants.CENTER));
		popup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

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
		popup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

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

	public boolean ActualizarBackupMySQLremoto(String ubicacion, String cleverCloudHost, String cleverCloudPort,
			String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {
		Connection conexionRemota = null;
		Connection conexionLocal = null;
		boolean exitoso = false;

		try {
			String nombreBaseLocal = (ubicacion.equalsIgnoreCase("Bariloche")) ? "ordenesbrc" : "ordenesbsas";
			System.out.println("Iniciando actualización de " + nombreBaseLocal + " desde Clever Cloud...");

			System.out.println("Conectando a Clever Cloud para extraer datos...");
			String urlCleverCloud = String.format(
					"jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&useServerPrepStmts=false",
					cleverCloudHost, cleverCloudPort, cleverCloudDatabase);
			conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser, cleverCloudPassword);
			conexionRemota.setAutoCommit(false);

			// OPTIMIZACIÓN: Configurar conexión remota para mejor rendimiento
			try (java.sql.Statement stmt = conexionRemota.createStatement()) {
				stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
			}
			System.out.println("Conectado a Clever Cloud exitosamente");

			System.out.println("Conectando a la base de datos local...");
			Conexion instanciaConexion = Conexion.getConexion(ubicacion);
			conexionLocal = instanciaConexion.getSQLConexion();
			if (conexionLocal == null || conexionLocal.isClosed()) {
				JOptionPane.showMessageDialog(null, "Error: No se pudo conectar a la base de datos local");
				return false;
			}
			conexionLocal.setAutoCommit(false);

			// OPTIMIZACIÓN: Configurar conexión local para mejor rendimiento
			try (java.sql.Statement stmt = conexionLocal.createStatement()) {
				stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
				stmt.execute("SET foreign_key_checks = 0");
				stmt.execute("SET unique_checks = 0");
			}
			System.out.println("Conectado a la base de datos local exitosamente");

			System.out.println("Limpiando base de datos local...");
			limpiarBaseDatos(conexionLocal);

			migrarDatosDirectamenteOptimizado1(conexionRemota, conexionLocal);

			conexionLocal.commit();
			conexionRemota.commit();
			exitoso = true;
			verificarMigracion(conexionLocal);

			JOptionPane.showMessageDialog(null,
					"Actualización completada exitosamente!\n\n" + "Origen: " + nombreBaseLocal + " REMOTA"
							+ "\nDestino: " + nombreBaseLocal,
					"Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);

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
			JOptionPane.showMessageDialog(null, "Error durante la actualización desde Clever Cloud:\n" + e.getMessage(),
					"Error en Actualización", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (conexionLocal != null && !conexionLocal.isClosed()) {
					try (java.sql.Statement stmt = conexionLocal.createStatement()) {
						stmt.execute("SET foreign_key_checks = 1");
						stmt.execute("SET unique_checks = 1");
					}
					conexionLocal.setAutoCommit(true);
				}
				if (conexionRemota != null && !conexionRemota.isClosed()) {
					conexionRemota.setAutoCommit(true);
					conexionRemota.close();
				}
			} catch (Exception e) {
				System.err.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return exitoso;
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

	// MÉTODO OPTIMIZADO PARA MIGRACIÓN (remoto -> local)
	private void migrarDatosDirectamenteOptimizado1(Connection conexionRemota, Connection conexionLocal)
			throws SQLException {
		try (java.sql.Statement stmtRemoto = conexionRemota.createStatement()) {
			List<String> tablas = new ArrayList<>();
			try (ResultSet rsTablas = stmtRemoto.executeQuery("SHOW TABLES")) {
				while (rsTablas.next()) {
					tablas.add(rsTablas.getString(1));
				}
			}
			System.out.println("Encontradas " + tablas.size() + " tablas para migrar.");

			// OPTIMIZACIÓN: Ordenar tablas por tamaño (primero las más pequeñas)
			tablas.sort((tabla1, tabla2) -> {
				try {
					int count1 = obtenerConteoTabla(conexionRemota, tabla1);
					int count2 = obtenerConteoTabla(conexionRemota, tabla2);
					return Integer.compare(count1, count2);
				} catch (SQLException e) {
					return 0;
				}
			});

			for (String tabla : tablas) {
				migrarTablaOptimizada(tabla, conexionRemota, conexionLocal);
			}
		}
	}

	// MÉTODO OPTIMIZADO PARA MIGRACIÓN (local -> remoto)
	private void migrarDatosDirectamenteOptimizado(Connection conexionLocal, Connection conexionRemota)
			throws SQLException {
		try (java.sql.Statement stmtLocal = conexionLocal.createStatement()) {
			List<String> tablas = new ArrayList<>();
			try (ResultSet rsTablas = stmtLocal.executeQuery("SHOW TABLES")) {
				while (rsTablas.next()) {
					tablas.add(rsTablas.getString(1));
				}
			}
			System.out.println("Encontradas " + tablas.size() + " tablas para migrar.");

			// OPTIMIZACIÓN: Ordenar tablas por tamaño (primero las más pequeñas para
			// feedback rápido)
			tablas.sort((tabla1, tabla2) -> {
				try {
					int count1 = obtenerConteoTabla(conexionLocal, tabla1);
					int count2 = obtenerConteoTabla(conexionLocal, tabla2);
					return Integer.compare(count1, count2);
				} catch (SQLException e) {
					return 0;
				}
			});

			for (String tabla : tablas) {
				migrarTablaOptimizada(tabla, conexionLocal, conexionRemota);
			}
		}
	}

	private int obtenerConteoTabla(Connection conexion, String tabla) throws SQLException {
		try (java.sql.Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM `" + tabla + "`")) {
			return rs.next() ? rs.getInt(1) : 0;
		}
	}

	// MÉTODO OPTIMIZADO PARA MIGRAR TABLA INDIVIDUAL
	private void migrarTablaOptimizada(String nombreTabla, Connection conexionOrigen, Connection conexionDestino)
			throws SQLException {
		System.out.println("Migrando tabla: " + nombreTabla);
		try (java.sql.Statement stmtOrigen = conexionOrigen.createStatement();
				java.sql.Statement stmtDestino = conexionDestino.createStatement()) {

			stmtDestino.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

			// Crear estructura de tabla
			try (ResultSet rsCreate = stmtOrigen.executeQuery("SHOW CREATE TABLE `" + nombreTabla + "`")) {
				if (rsCreate.next()) {
					String createStatement = rsCreate.getString(2);
					stmtDestino.executeUpdate("DROP TABLE IF EXISTS `" + nombreTabla + "`");
					stmtDestino.executeUpdate(createStatement);
				}
			}

			int totalRegistros = obtenerConteoTabla(conexionOrigen, nombreTabla);

			if (totalRegistros > 0) {
				System.out.println("Tabla `" + nombreTabla + "` tiene " + totalRegistros + " registros.");
				migrarDatosEnLotesOptimizado(nombreTabla, conexionOrigen, conexionDestino, totalRegistros);
			} else {
				System.out.println("Tabla `" + nombreTabla + "` está vacía.");
			}

			stmtDestino.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		}
	}

	// MÉTODO SUPER OPTIMIZADO PARA MIGRAR DATOS EN LOTES
	private void migrarDatosEnLotesOptimizado(String nombreTabla, Connection conexionOrigen, Connection conexionDestino,
			int totalRegistros) throws SQLException {
		// OPTIMIZACIÓN: Lotes dinámicos según tamaño de tabla
		final int TAMANO_LOTE = totalRegistros > 50000 ? 10000
				: totalRegistros > 10000 ? 5000 : totalRegistros > 1000 ? 2000 : 1000;

		System.out.println("Usando lotes de " + TAMANO_LOTE + " registros para esta tabla.");

		ResultSetMetaData metaData;
		int columnCount;
		List<String> columnTypes = new ArrayList<>();

		// Obtener metadatos de columnas
		try (java.sql.Statement stmt = conexionOrigen.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `" + nombreTabla + "` LIMIT 1")) {
			metaData = rs.getMetaData();
			columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				columnTypes.add(metaData.getColumnTypeName(i).toUpperCase());
			}
		}

		int offset = 0;
		while (offset < totalRegistros) {
			// OPTIMIZACIÓN: Construir INSERT con múltiples VALUES
			StringBuilder insertSQL = new StringBuilder("INSERT INTO `" + nombreTabla + "` VALUES ");

			try (java.sql.Statement stmtOrigen = conexionOrigen.createStatement();
					ResultSet rsData = stmtOrigen.executeQuery(
							"SELECT * FROM `" + nombreTabla + "` LIMIT " + TAMANO_LOTE + " OFFSET " + offset)) {

				boolean first = true;
				int batchCount = 0;

				while (rsData.next() && batchCount < TAMANO_LOTE) {
					if (!first)
						insertSQL.append(", ");
					insertSQL.append("(");

					for (int i = 1; i <= columnCount; i++) {
						if (i > 1)
							insertSQL.append(", ");
						Object valor = rsData.getObject(i);

						if (valor == null) {
							insertSQL.append("NULL");
						} else {
							String tipoColumna = columnTypes.get(i - 1);
							insertSQL.append(formatearValorSQL(valor, tipoColumna));
						}
					}
					insertSQL.append(")");
					first = false;
					batchCount++;
				}

				if (batchCount > 0) {
					try (java.sql.Statement stmtDestino = conexionDestino.createStatement()) {
						stmtDestino.executeUpdate(insertSQL.toString());
					}
				}
			}

			offset += TAMANO_LOTE;
			System.out.println("Procesados " + Math.min(offset, totalRegistros) + "/" + totalRegistros + " registros.");

			// OPTIMIZACIÓN: Commit intermedio cada cierta cantidad de lotes
			if (offset % (TAMANO_LOTE * 3) == 0) {
				conexionDestino.commit();
				System.out.println("Commit intermedio realizado.");
			}
		}
	}

	// MÉTODO AUXILIAR PARA FORMATEAR VALORES SQL CORRECTAMENTE
	private String formatearValorSQL(Object valor, String tipoColumna) {
		if (valor == null) {
			return "NULL";
		}

		String valorStr = valor.toString();

		// Tipos que necesitan comillas
		if (tipoColumna.contains("CHAR") || tipoColumna.contains("TEXT") || tipoColumna.contains("DATE")
				|| tipoColumna.contains("TIME") || tipoColumna.contains("ENUM") || tipoColumna.contains("SET")) {

			// Escapar comillas simples
			valorStr = valorStr.replace("'", "''");
			return "'" + valorStr + "'";
		}

		// Tipos numéricos y otros
		return valorStr;
	}

	private void verificarMigracion(Connection conexion) throws SQLException {
		try (java.sql.Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
			int totalTablas = 0;
			while (rs.next())
				totalTablas++;
			System.out.println("Verificación completada: " + totalTablas + " tablas creadas.");
		}
	}

	@SuppressWarnings({ "unused", "rawtypes" })
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
			

			JDialog popup = new JDialog();
			popup.setTitle("Procesando");
			popup.setModal(false);
			popup.setSize(300, 100);
			popup.setLocationRelativeTo(ventanaBackUp);
			popup.add(new JLabel("Actualizando Base de Datos, espere...", SwingConstants.CENTER));
			popup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() {
					try {
						File nombrebackup = archivoBackup.getSelectedFile();
						Process p = Runtime.getRuntime().exec(
								"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql -uroot -proot ordenesbrc");
						try (OutputStream os = p.getOutputStream();
								FileInputStream fis = new FileInputStream(nombrebackup)) {
							byte[] buffer = new byte[1000];
							int leido;
							while ((leido = fis.read(buffer)) > 0) {
								os.write(buffer, 0, leido);
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
				protected void done() {
					popup.dispose();
					ventanaBackUp.getGlassPane().setVisible(false);
					ventanaBackUp.setCursor(Cursor.getDefaultCursor());
					ventanaBackUp.getBtnGenerarB().setEnabled(true);
					ventanaBackUp.getBtnImportarB().setEnabled(true);				}
			};
			SwingUtilities.invokeLater(() -> {
				popup.setVisible(true);
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

		JDialog popup = new JDialog();
		popup.setTitle("Procesando");
		popup.setModal(false);
		popup.setSize(300, 100);
		popup.setLocationRelativeTo(ventanaOpcionesBackup);
		popup.add(new JLabel("Generando BackUp, espere...", SwingConstants.CENTER));
		popup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {
					String nombreAguardar = ventanaOpcionesBackup.getTxtNombreArchivo().getText();
					String rutaAguardar = ventanaOpcionesBackup.getTxtRutaArchivo().getText();
					File backupFile = new File(rutaAguardar + nombreAguardar);
					Process child = Runtime.getRuntime().exec(
							"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=root --user=root --databases ordenesbrc");
					try (InputStreamReader irs = new InputStreamReader(child.getInputStream());
							BufferedReader br = new BufferedReader(irs);
							FileWriter fw = new FileWriter(backupFile)) {
						String line;
						while ((line = br.readLine()) != null) {
							fw.write(line + "\n");
						}
					}
					JOptionPane.showMessageDialog(null, "Archivo de backup generado exitosamente.", "Backup Exitoso",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al generar el backup: " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}

			@Override
			protected void done() {
				popup.dispose();
				ventanaOpcionesBackup.getGlassPane().setVisible(false);
				ventanaOpcionesBackup.setCursor(Cursor.getDefaultCursor());
				ventanaBackUp.getBtnGenerarB().setEnabled(true);
				ventanaBackUp.getBtnImportarB().setEnabled(true);
			}
		};
		SwingUtilities.invokeLater(() -> {
			popup.setVisible(true);
			worker.execute();
		});
	}

	public boolean GenerarBackupMySQLRemoto(String ubicacion, String cleverCloudHost, String cleverCloudPort,
			String cleverCloudUser, String cleverCloudPassword, String cleverCloudDatabase) {
		boolean exitoso = false;
		String archivoTemporal = null;
		Connection conexionLocal = null;
		Connection conexionRemota = null;

		try {
			String nombreBaseLocal = (ubicacion.equalsIgnoreCase("Bariloche")) ? "ordenesbrc" : "ordenesbsas";
			System.out.println("Iniciando backup remoto desde " + nombreBaseLocal + " hacia Clever Cloud...");

			// Conectar a la base de datos local con optimizaciones
			System.out.println("Conectando a la base de datos local...");
			Conexion instanciaConexion = Conexion.getConexion(ubicacion);
			conexionLocal = instanciaConexion.getSQLConexion();
			if (conexionLocal == null || conexionLocal.isClosed()) {
				JOptionPane.showMessageDialog(null, "Error: No se pudo conectar a la base de datos local");
				return false;
			}
			conexionLocal.setAutoCommit(false);

			// OPTIMIZACIÓN: Configurar conexión local para máximo rendimiento
			try (java.sql.Statement stmt = conexionLocal.createStatement()) {
				stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
				stmt.execute("SET foreign_key_checks = 0");
				stmt.execute("SET unique_checks = 0");
			}
			System.out.println("Conectado a la base de datos local exitosamente");

			// Conectar a Clever Cloud con optimizaciones compatibles
			System.out.println("Conectando a Clever Cloud con optimizaciones compatibles...");
			String urlCleverCloud = String.format(
					"jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&useServerPrepStmts=false&cachePrepStmts=false&useLocalSessionState=true&useLocalTransactionState=true",
					cleverCloudHost, cleverCloudPort, cleverCloudDatabase);
			conexionRemota = DriverManager.getConnection(urlCleverCloud, cleverCloudUser, cleverCloudPassword);
			conexionRemota.setAutoCommit(false);

			// OPTIMIZACIÓN CORREGIDA: Solo configuraciones compatibles con servicios cloud
			try (java.sql.Statement stmt = conexionRemota.createStatement()) {
				stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
				stmt.execute("SET foreign_key_checks = 0");
				stmt.execute("SET unique_checks = 0");
				// ELIMINADO: stmt.execute("SET SESSION innodb_flush_log_at_trx_commit = 0");
				// Esta variable es GLOBAL y no está permitida en Clever Cloud
				System.out.println("Configuraciones de sesión aplicadas (omitiendo variables globales no permitidas)");
			}
			System.out.println("Conectado a Clever Cloud exitosamente");

			// Limpiar base de datos remota
			System.out.println("Limpiando base de datos remota...");
			limpiarBaseDatos(conexionRemota);

			// MIGRACIÓN OPTIMIZADA
			System.out.println("Iniciando migración OPTIMIZADA de datos...");
			migrarDatosDirectamenteOptimizado(conexionLocal, conexionRemota);

			conexionRemota.commit();
			exitoso = true;

			// Verificar migración
			verificarMigracion(conexionRemota);

			JOptionPane.showMessageDialog(null,
					"¡Backup remoto completado exitosamente!\n\n" + "Origen: " + nombreBaseLocal + "\n" + "Destino: "
							+ nombreBaseLocal + " REMOTA" + "\n\n" + "Optimizaciones compatibles aplicadas",
					"Backup Exitoso", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conexionRemota != null)
					conexionRemota.rollback();
				if (conexionLocal != null)
					conexionLocal.rollback();
			} catch (SQLException rollbackEx) {
				System.err.println("Error durante rollback: " + rollbackEx.getMessage());
			}
			JOptionPane.showMessageDialog(null, "Error durante el backup remoto:\n" + e.getMessage(), "Error en Backup",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			// Restaurar configuraciones y limpiar recursos
			try {
				if (conexionLocal != null && !conexionLocal.isClosed()) {
					try (java.sql.Statement stmt = conexionLocal.createStatement()) {
						stmt.execute("SET foreign_key_checks = 1");
						stmt.execute("SET unique_checks = 1");
					}
					conexionLocal.setAutoCommit(true);
				}
				if (conexionRemota != null && !conexionRemota.isClosed()) {
					try (java.sql.Statement stmt = conexionRemota.createStatement()) {
						stmt.execute("SET foreign_key_checks = 1");
						stmt.execute("SET unique_checks = 1");
						// NOTA: No intentamos restaurar innodb_flush_log_at_trx_commit porque no
						// podemos modificarla
					}
					conexionRemota.setAutoCommit(true);
					conexionRemota.close();
				}
				if (archivoTemporal != null) {
					new File(archivoTemporal).delete();
				}
			} catch (Exception e) {
				System.err.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return exitoso;
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
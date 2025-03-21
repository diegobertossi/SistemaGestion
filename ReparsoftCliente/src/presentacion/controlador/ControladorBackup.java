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
import java.util.Date;

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

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.vista.VentanaBackUp;
import presentacion.vista.VentanaOpcionesBackup;

public class ControladorBackup implements ActionListener, MouseListener {
	private VentanaBackUp ventanaBackUp;
	private VentanaOpcionesBackup ventanaOpcionesBackup;
	private String rutadefaultBackup = "";

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

		Date dia = new Date();

		String NombreBackUp = "Backup Reparsoft " + dia.getDate() + "-" + (dia.getMonth() + 1) + "-"
				+ (dia.getYear() + 1900) + ".sql";

		//String rutaBackup = "F:\\els\\Administracion\\Sistema\\Base de datos\\Backups\\";
		
		
		//String rutadefaultBackup = "";

		if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {

			rutadefaultBackup = "F:\\els\\Administracion\\Sistema\\Base de datos\\Backups\\";
		} else if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {
			rutadefaultBackup = "F:\\els\\Bariloche\\Administracion\\Sistema\\Base de datos\\Backups\\";
		}


		String NombreBackUpSinExtension = "Backup Reparsoft " + dia.getDate() + "-" + (dia.getMonth() + 1) + "-"
				+ (dia.getYear() + 1900);

		if (ventanaBackUp != null && e.getSource() == ventanaBackUp.getBtnGenerarB()) {

			ventanaOpcionesBackup = new VentanaOpcionesBackup();

			ventanaOpcionesBackup.getTxtNombreArchivo().addActionListener(this);
			ventanaOpcionesBackup.getTxtRutaArchivo().addActionListener(this);
			ventanaOpcionesBackup.getBtnAceptar().addActionListener(this);
			ventanaOpcionesBackup.getBtnCancelar().addActionListener(this);
			ventanaOpcionesBackup.getBtnCambiarNombre().addActionListener(this);
			ventanaOpcionesBackup.getBtnResetDatos().addActionListener(this);

			ventanaOpcionesBackup.getTxtNombreArchivo().setText(NombreBackUp);
			ventanaOpcionesBackup.getTxtRutaArchivo().setText(rutadefaultBackup);

			// F:\els\Administracion\Sistema\Base de datos\Backups

			this.ventanaBackUp.dispose();
			this.ventanaBackUp = null;
		}

		if (ventanaBackUp != null && e.getSource() == ventanaBackUp.getBtnImportarB()) {
			ActualizarBackupMySQL();
		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnCancelar()) {

			this.ventanaOpcionesBackup.dispose();
			this.ventanaOpcionesBackup = null;

		}

		if (ventanaOpcionesBackup != null && e.getSource() == ventanaOpcionesBackup.getBtnAceptar()) {

			GenerarBackupMySQL();

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

	@SuppressWarnings({ "unused", "rawtypes" })
	private void ActualizarBackupMySQL() {
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

	private void GenerarBackupMySQL() {

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

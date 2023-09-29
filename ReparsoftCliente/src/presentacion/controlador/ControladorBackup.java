package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.xdevapi.Statement;

import modelo.Agenda;
import presentacion.vista.VentanaBackUp;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaOpcionesBackup;

public class ControladorBackup implements ActionListener, MouseListener {
	private VentanaBackUp ventanaBackUp;
	private VentanaOpcionesBackup ventanaOpcionesBackup;

	private Agenda agenda;

	public ControladorBackup(VentanaBackUp ventanaBackUp) {

		this.ventanaBackUp = ventanaBackUp;

		this.ventanaBackUp.getBtnGenerarB().addActionListener(this);
		this.ventanaBackUp.getBtnImportarB().addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {

		Date dia = new Date();

		String NombreBackUp = "Backup Reparsoft " + dia.getDate() + "-" + (dia.getMonth() + 1) + "-"
				+ (dia.getYear() + 1900) + ".sql";
		String rutaBackup = "F:\\els\\Administracion\\Sistema\\Base de datos\\Backups\\";

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
			ventanaOpcionesBackup.getTxtRutaArchivo().setText(rutaBackup);

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

			JFileChooser cambiarNombreRuta = new JFileChooser(rutaBackup);

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
			ventanaOpcionesBackup.getTxtRutaArchivo().setText(rutaBackup);

		}

	}

	private void ActualizarBackupMySQL() {
		// TODO Auto-generated method stub
		String bd = "ordenesbrc";
		String login = "root";
		String password = "root";
		String url = "jdbc:mysql://localhost:3306/" + bd;
		String urlcero = "jdbc:mysql://localhost/";
		ConnectionUrl conn = null;

		Statement stm;
		File nombrebackup = null;
		File rutabackup = null;
		int selecGuardaBack = 0;
		int selecRestauraBack = 1;

		String rutadefaultBackup = "F:\\els\\Administracion\\Sistema\\Base de datos\\Backups\\";

		JFileChooser archivoBackup = new JFileChooser(rutadefaultBackup);

		FileNameExtensionFilter sqlFilter = new FileNameExtensionFilter("Bases de datos SQL", "sql");
		archivoBackup.setFileFilter(sqlFilter);

		int resp;
		resp = archivoBackup.showOpenDialog(ventanaBackUp);
		if (resp == JFileChooser.APPROVE_OPTION) {
			try {
				Statement sentencia = null;
				ConnectionUrl coneccionini = null;
				if (selecRestauraBack == 1) {

					try {
						nombrebackup = new File(archivoBackup.getSelectedFile().toString().trim());
						
						//System.out.println(nombrebackup.getName());

//						Process p = Runtime.getRuntime().exec(
//								"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql -uroot -proot ordenesbrc");
						
						//Process po = Runtime.getRuntime().exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql -uroot -proot ordenesbrc");
						
						Process p = Runtime.getRuntime().exec("C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql -uroot -proot ordenesbrc");
						
						
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

						JOptionPane.showMessageDialog(null, "Base Actualizada", "Verificar",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Error no se actualizo la DB por el siguiente motivo: " + e.getMessage(), "Verificar",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ha sido cancelada la actualizacion del Backup");
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Error no se genero el archivo por el siguiente motivo:" + e.getMessage(), "Verificar",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void GenerarBackupMySQL() {

		try {

			String nombreAguardar = ventanaOpcionesBackup.getTxtNombreArchivo().getText();
			String rutaAguardar = ventanaOpcionesBackup.getTxtRutaArchivo().getText();

			Runtime runtime = Runtime.getRuntime();
			File backupFile = new File(rutaAguardar + nombreAguardar);
			FileWriter fw = new FileWriter(backupFile);
			
			Process child = runtime.exec(
					"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=root --user=root --databases ordenesbrc");
			
//			Process child = runtime.exec(
//					"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump --opt --password=root --user=root --databases ordenesbrc");
			
			
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
			
			//JOptionPane.showMessageDialog(null, "Archivo generado", "Verificar", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Error no se genero el archivo por el siguiente motivo:" + e.getMessage(), "Verificar",
					JOptionPane.ERROR_MESSAGE);
		}

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

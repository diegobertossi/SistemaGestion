package persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Conexion {
	private static Conexion instancia;
	private Connection conexion;

	private Conexion(String ubicacion)

	{

		try {
			// String controlador = "org.gjt.mm.mysql.Driver";// remoto
			String controlador = "com.mysql.cj.jdbc.Driver";
			Class.forName(controlador).newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el controlador");
		}

		String DSN = "";

		try {

			if (ubicacion.compareTo("Bariloche") == 0) {

				DSN = "jdbc:mysql://localhost:3306/ordenesbrc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";

			}

			else if (ubicacion.compareTo("Buenos Aires") == 0) {

				DSN = "jdbc:mysql://localhost:3306/ordenesbsas?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";

			}

			String user = "root";
			String password = "root";

			conexion = DriverManager.getConnection(DSN, user, password);

			System.out.println("Conexion exitosa");

		}

		catch (Exception e) {
			System.out.println("Conexion fallida");
			JOptionPane.showMessageDialog(null, "Error al realizar la conexion \n" + e.toString()
					+ "\n \n ------------\n Esta ventana se cerrara....");
		}
	}

	public static Conexion getConexion(String ubicacion) {
		
		System.out.println(ubicacion);
		if (instancia == null) {
			instancia = new Conexion(ubicacion);
		}
		return instancia;
	}

	public Connection getSQLConexion() {
		return conexion;
	}

	public void cerrarConexion() {
		instancia = null;
	}
}

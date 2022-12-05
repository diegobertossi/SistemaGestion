package persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Conexion {
	private static Conexion instancia;
	private Connection conexion;

	private Conexion()

	{

		try {
			//String controlador = "org.gjt.mm.mysql.Driver";// remoto
			String controlador = "com.mysql.cj.jdbc.Driver";
			Class.forName(controlador).newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el controlador");
		}

		try {
			// conexion =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/ordenesbrc"
			// ,"root","root");
			//String DSN = "jdbc:mysql://192.168.0.51:3306/ordenesbrc";
			//String DSN="jdbc:mysql://localhost:3306/ordenesbrc?serverTimezone=UTC";
		
			String DSN="jdbc:mysql://localhost:3306/ordenesbrc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
			String user = "root";
			String password = "root";
			//String password = "root&characterEncoding=utf-8&" + "useUnicode=true";
			conexion = DriverManager.getConnection(DSN, user, password);
			
			System.out.println("Conexion exitosa");

		} catch (Exception e) {
			System.out.println("Conexion fallida");
			JOptionPane.showMessageDialog(null, "Error al realizar la conexion \n" + e.toString()
					+ "\n \n ------------\n Esta ventana se cerrara....");
		}
	}

	public static Conexion getConexion() {
		if (instancia == null) {
			instancia = new Conexion();
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

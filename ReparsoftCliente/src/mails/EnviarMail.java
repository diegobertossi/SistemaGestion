package mails;

import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;


import presentacion.vista.VentanaVisualizarEquipos;

public class EnviarMail {

	
	
	private static boolean enviarAvisoInformePR(String correo, String ELS, String Cliente, String Sucursal) {
		try {
			
			
			// Propiedades de la conexi�n
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.elsweb.com.ar");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", "diego.bertossi@elsweb.com.ar");
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Equipos Diagnosticados ELS<diego.bertossi@elsweb.com.ar>"));

			message.addRecipients(Message.RecipientType.BCC, correo);

			message.setSubject("ELS: "+ELS+" "+Cliente + "-" +Sucursal + " - DIAGNOSTICADO" );
			message.setText("");

			// Lo enviamos.
			Transport t = session.getTransport("smtp");
			t.connect("diego.bertossi@elsweb.com.ar", "Diego1216");
			t.sendMessage(message, message.getAllRecipients());
			
			
			Object mje = "El correo se envi� Exitosamente.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			

			// Cierre.
			t.close();
			return true;
		} catch (Exception e) {
			Object mje = "El correo NO ha sido enviado.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			
			return false;
		}

	}
	
	
	private static void enviarAvisoEquipoTerminadoPR(String correo, String ELS, String Cliente, String Sucursal) {
		try {
			
			
			// Propiedades de la conexi�n
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.elsweb.com.ar");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", "diego.bertossi@elsweb.com.ar");
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Equipos Terminados ELS<diego.bertossi@elsweb.com.ar>"));

			message.addRecipients(Message.RecipientType.BCC, correo);

			message.setSubject("ELS: "+ELS+" "+Cliente + "-" +Sucursal + " - TERMINADO" );
			message.setText("");

			// Lo enviamos.
			Transport t = session.getTransport("smtp");
			t.connect("diego.bertossi@elsweb.com.ar", "Diego1216");
			t.sendMessage(message, message.getAllRecipients());
			
			
			Object mje = "El correo se envi� Exitosamente.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			

			// Cierre.
			t.close();
		
		} catch (Exception e) {
			Object mje = "El correo NO ha sido enviado.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
	
		}

	}
	
	
	private static void enviarAvisoRespuestaClientePR(String correo, String ELS, String Cliente, String Sucursal,String EstadoComercial) {
		try {
			
			
			// Propiedades de la conexi�n
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.elsweb.com.ar");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", "diego.bertossi@elsweb.com.ar");
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Respuesta de Clientes ELS<diego.bertossi@elsweb.com.ar>"));

			message.addRecipients(Message.RecipientType.BCC, correo);

			message.setSubject("ELS: "+ELS+" "+Cliente + "-" +Sucursal + " - " + EstadoComercial );
			message.setText("PROCEDER SEG�N CORRESPONDA");

			// Lo enviamos.
			Transport t = session.getTransport("smtp");
			t.connect("diego.bertossi@elsweb.com.ar", "Diego1216");
			t.sendMessage(message, message.getAllRecipients());
			
			
			Object mje = "El correo se envi� Exitosamente.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			

			// Cierre.
			t.close();
		
		} catch (Exception e) {
			Object mje = "El correo NO ha sido enviado.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
	
		}

	}

	
	
	
	private static boolean enviarInformeAlClientePR(String correo, String Asunto, String Cuerpo,String NombrePDF) {
		try {
			
			
			// Propiedades de la conexi�n
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.elsweb.com.ar");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", "diego.bertossi@elsweb.com.ar");
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ELS <diego.bertossi@elsweb.com.ar>"));

			message.addRecipients(Message.RecipientType.BCC, correo);

			message.setSubject(Asunto);
			message.setText(Cuerpo);
			
			
			BodyPart texto = new javax.mail.internet.MimeBodyPart();
			texto.setText(Cuerpo);
			
//			
			BodyPart adjunto = new javax.mail.internet.MimeBodyPart();
			adjunto.setDataHandler(new DataHandler(new FileDataSource("F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/"+NombrePDF)));
			adjunto.setFileName(NombrePDF);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(texto);
			multipart.addBodyPart(adjunto);

			message.setContent(multipart);

			// Lo enviamos.
			Transport t = session.getTransport("smtp");
			t.connect("diego.bertossi@elsweb.com.ar", "Diego1216");
			t.sendMessage(message, message.getAllRecipients());
			
			
			Object mje = "El correo se envió Exitosamente.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			

			// Cierre.
			t.close();
			return true;
		
		} catch (Exception e) {
			Object mje = "El correo NO ha sido enviado.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return false;
		}

	}
	
	
	
	
	
	
	
	
	public static boolean enviarAvisoInforme( String correo, String ELS, String Cliente, String Sucursal) {

		return enviarAvisoInformePR( correo, ELS, Cliente, Sucursal);

	}




	public static void enviarAvisoEquipoTerminado(String correo, String ELS, String Cliente, String Sucursal) {
		
		enviarAvisoEquipoTerminadoPR( correo, ELS, Cliente, Sucursal);
		
	}


	public static void enviarAvisoRespuestaCliente(String correo, String ELS, String Cliente, String Sucursal, String EstadoComercial) {
		
		enviarAvisoRespuestaClientePR( correo, ELS, Cliente, Sucursal, EstadoComercial);
		
	}
	
	
public static boolean enviarInformeAlCliente(String correo, String Asunto, String Cuerpo,String NombrePDF) {
		
	return enviarInformeAlClientePR( correo, Asunto, Cuerpo, NombrePDF);
		
	}
	
	
	
	
	
	
	
}
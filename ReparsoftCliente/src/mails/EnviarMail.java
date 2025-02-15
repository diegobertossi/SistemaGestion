package mails;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

public class EnviarMail {

    // Configuración de propiedades del servidor SMTP
    private static Properties getMailProperties() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.elsweb.com.ar");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "diego.bertossi@elsweb.com.ar");
        props.setProperty("mail.smtp.auth", "true");
        return props;
    }

    // Método común para enviar correos
    private static boolean enviarCorreo(String from, String correo, String subject, String cuerpo, BodyPart... adjuntos) {
        try {
            // Crear sesión
            Session session = Session.getDefaultInstance(getMailProperties());

            // Crear el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.BCC, correo);
            message.setSubject(subject);

            if (adjuntos != null && adjuntos.length > 0) {
                Multipart multipart = new MimeMultipart();

                // Agregar el cuerpo del mensaje como texto
                BodyPart texto = new MimeBodyPart();
                texto.setText(cuerpo);
                multipart.addBodyPart(texto);

                // Agregar adjuntos
                for (BodyPart adjunto : adjuntos) {
                    multipart.addBodyPart(adjunto);
                }

                message.setContent(multipart);
            } else {
                message.setText(cuerpo);
            }

            // Enviar correo
            Transport t = session.getTransport("smtp");
            t.connect("diego.bertossi@elsweb.com.ar", "Diego1216");
            t.sendMessage(message, message.getAllRecipients());
            t.close();

            JOptionPane.showMessageDialog(null, "El correo se envió exitosamente.", "Confirmación de envío", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El correo NO ha sido enviado.", "Error de envío", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    // Métodos específicos para cada tipo de correo
    public static void enviarAvisoInforme(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Diagnosticados ELS<diego.bertossi@elsweb.com.ar>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - DIAGNOSTICADO";
        enviarCorreo(from, correo, subject, "");
    }

    public static void enviarAvisoEquipoTerminado(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Terminados ELS<diego.bertossi@elsweb.com.ar>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - TERMINADO";
        enviarCorreo(from, correo, subject, "");
    }

    public static void enviarAvisoRespuestaCliente(String correo, String ELS, String Cliente, String Sucursal, String EstadoComercial) {
        String from = "Respuesta de Clientes ELS<diego.bertossi@elsweb.com.ar>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - " + EstadoComercial;
        String cuerpo = "PROCEDER SEGÚN CORRESPONDA";
        enviarCorreo(from, correo, subject, cuerpo);
    }

    public static void enviarInformeAlCliente(String correo, String asunto, String cuerpo, String nombreArchivo) {
        try {
            BodyPart adjunto = new MimeBodyPart();
            if (nombreArchivo.endsWith(".pdf")) {
                adjunto.setDataHandler(new DataHandler(new FileDataSource("F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/" + nombreArchivo)));
            } else if (nombreArchivo.endsWith(".docx")) {
                adjunto.setDataHandler(new DataHandler(new FileDataSource("F:/ELS/Administracion/Sistema/Informes Siemens/" + nombreArchivo)));
            }
            adjunto.setFileName(nombreArchivo);

            String from = "ELS <diego.bertossi@elsweb.com.ar>";
            enviarCorreo(from, correo, asunto, cuerpo, adjunto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo adjuntar el archivo.", "Error", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }
}

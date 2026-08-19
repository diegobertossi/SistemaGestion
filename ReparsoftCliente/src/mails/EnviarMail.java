package mails;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

import util.Config;
import util.RutasSistema;

public class EnviarMail {

    // Si no existe la clave por categoría (smtp.xxx.host), cae a la genérica (smtp.host)
    private static String getHost(String key, String fallback) { return Config.get(key + ".host", Config.get("smtp.host", fallback)); }
    private static String getPort(String key, String fallback) { return Config.get(key + ".port", Config.get("smtp.port", fallback)); }
    private static String getUser(String key, String fallback) { return Config.get(key + ".user", Config.get("smtp.user", fallback)); }
    private static String getPass(String key, String fallback) { return Config.get(key + ".password", Config.get("smtp.password", fallback)); }
    private static String getProto(String key, String fallback) { return Config.get(key + ".protocol", Config.get("smtp.protocol", fallback)); }

    // Método para obtener propiedades del servidor SMTP desde config
    private static Properties getMailProperties(String host, String port, String user, String protocol) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.user", user);
        props.setProperty("mail.smtp.auth", "true");
        if ("smtps".equalsIgnoreCase(protocol) || "ssl".equalsIgnoreCase(protocol)) {
            props.setProperty("mail.smtp.ssl.enable", "true");
        } else {
            props.setProperty("mail.smtp.starttls.enable", "true");
        }
        return props;
    }

    // Método auxiliar para parsear destinatarios (separados por ; , o espacios)
    private static String[] parseDestinatarios(String destinatarios) {
        if (destinatarios == null || destinatarios.trim().isEmpty()) {
            return new String[0];
        }
        // Separar por punto y coma, coma o espacio
        String[] partes = destinatarios.split("[;,\\s]+");
        ArrayList<String> emailsValidos = new ArrayList<>();
        for (String email : partes) {
            email = email.trim();
            if (email.contains("@") && !email.isEmpty()) {
                emailsValidos.add(email);
            }
        }
        return emailsValidos.toArray(new String[0]);
    }

    // Método auxiliar para obtener propiedades SMTP desde config
    private static Properties getMailProperties(String hostKey, String userKey, String passKey) {
        String host = getHost(hostKey, "smtp.gmail.com");
        String port = getPort(hostKey, "587");
        String user = getUser(hostKey, "");
        String pass = getPass(hostKey, "");
        String proto = getProto(hostKey, "smtp");
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.user", user);
        props.setProperty("mail.smtp.auth", "true");
        if ("smtps".equalsIgnoreCase(proto) || "ssl".equalsIgnoreCase(proto)) {
            props.setProperty("mail.smtp.ssl.enable", "true");
        } else {
            props.setProperty("mail.smtp.starttls.enable", "true");
        }
        return props;
    }

    // Resultado del envío sin diálogo
    private static class SendResult {
        final boolean success;
        final String message;

        private SendResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    // Envío raw sin mostrar diálogos (retorna resultado)
    private static SendResult enviarCorreoSinDialogo(String from, String destinatarios, String subject, String cuerpo,
                                                     String hostKey, String userKey, String passKey, BodyPart... adjuntos) {
        String host = getHost(hostKey, "smtp.gmail.com");
        String user = getUser(hostKey, "");
        try {
            String[] listaDestinatarios = parseDestinatarios(destinatarios);

            if (listaDestinatarios.length == 0) {
                return new SendResult(false, "No se encontraron direcciones de correo válidas.");
            }

            String port = getPort(hostKey, "587");
            String pass = getPass(hostKey, "");
            String proto = getProto(hostKey, "smtp");
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.user", user);
            props.setProperty("mail.smtp.auth", "true");
            if ("smtps".equalsIgnoreCase(proto) || "ssl".equalsIgnoreCase(proto)) {
                props.setProperty("mail.smtp.ssl.enable", "true");
            } else {
                props.setProperty("mail.smtp.starttls.enable", "true");
            }
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(getUser(hostKey, ""), getPass(hostKey, ""));
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            for (String email : listaDestinatarios) {
                message.addRecipients(Message.RecipientType.BCC, email);
            }

            message.setSubject(subject);
            message.setSentDate(new Date());

            if (adjuntos != null && adjuntos.length > 0) {
                Multipart multipart = new MimeMultipart();
                BodyPart texto = new MimeBodyPart();
                texto.setText(cuerpo);
                multipart.addBodyPart(texto);
                for (BodyPart adjunto : adjuntos) {
                    multipart.addBodyPart(adjunto);
                }
                message.setContent(multipart);
            } else {
                message.setText(cuerpo);
            }

            Transport.send(message);
            return new SendResult(true, "El correo se envi\u00f3 exitosamente a " + listaDestinatarios.length + " destinatario(s).");
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return new SendResult(false, "Error de autenticaci\u00f3n.\nUsuario: " + user + "\nHost: " + host + "\nVerifique que el usuario y contrase\u00f1a sean correctos.");
        } catch (MessagingException e) {
            e.printStackTrace();
            return new SendResult(false, "Error al enviar el correo: " + e.getMessage());
        }
    }

    // Método original con diálogos (delega a enviarCorreoSinDialogo)
    private static boolean enviarCorreo(String from, String destinatarios, String subject, String cuerpo,
                                        String hostKey, String userKey, String passKey, BodyPart... adjuntos) {
        SendResult result = enviarCorreoSinDialogo(from, destinatarios, subject, cuerpo, hostKey, userKey, passKey, adjuntos);
        JOptionPane.showMessageDialog(null, result.message,
            result.success ? "Confirmaci\u00f3n de env\u00edo" : "Error",
            result.success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        return result.success;
    }

    // Método sobrecargado para enviar correos sin adjuntos
    private static boolean enviarCorreo(String from, String destinatarios, String subject, String cuerpo,
                                        String hostKey, String userKey, String passKey) {
        return enviarCorreo(from, destinatarios, subject, cuerpo, hostKey, userKey, passKey, new BodyPart[0]);
    }

    // enviarAvisoInforme - Usa configuración SMTP avisoinforme
    public static void enviarAvisoInforme(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Diagnosticados ELS <" + getUser("smtp.avisoinforme", "") + ">";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - DIAGNOSTICADO";
        enviarCorreo(from, correo, subject, "",
                     "smtp.avisoinforme", "smtp.avisoinforme", "smtp.avisoinforme");
    }

    // enviarAvisoInforme sin JOptionPane (retorna mensaje)
    public static String enviarAvisoInformeSinDialogo(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Diagnosticados ELS <" + getUser("smtp.avisoinforme", "") + ">";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - DIAGNOSTICADO";
        SendResult r = enviarCorreoSinDialogo(from, correo, subject, "",
                     "smtp.avisoinforme", "smtp.avisoinforme", "smtp.avisoinforme");
        return r.success ? null : r.message;
    }

    // enviarAvisoEquipoTerminado - Usa configuración SMTP equipoterminado
    public static void enviarAvisoEquipoTerminado(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Terminados ELS <" + getUser("smtp.equipoterminado", "") + ">";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - TERMINADO";
        enviarCorreo(from, correo, subject, "",
                     "smtp.equipoterminado", "smtp.equipoterminado", "smtp.equipoterminado");
    }

    // enviarAvisoEquipoTerminado sin JOptionPane (retorna mensaje)
    public static String enviarAvisoEquipoTerminadoSinDialogo(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Terminados ELS <" + getUser("smtp.equipoterminado", "") + ">";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - TERMINADO";
        SendResult r = enviarCorreoSinDialogo(from, correo, subject, "",
                     "smtp.equipoterminado", "smtp.equipoterminado", "smtp.equipoterminado");
        return r.success ? null : r.message;
    }

    // enviarAvisoRespuestaCliente - Usa configuración SMTP respuestacliente
    public static void enviarAvisoRespuestaCliente(String correo, String ELS, String Cliente, String Sucursal, String EstadoComercial) {
        String from = "Respuesta de Clientes ELS <" + getUser("smtp.respuestacliente", "") + ">";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - " + EstadoComercial;
        String cuerpo = "PROCEDER SEGÚN CORRESPONDA";
        enviarCorreo(from, correo, subject, cuerpo,
                     "smtp.respuestacliente", "smtp.respuestacliente", "smtp.respuestacliente");
    }

    // enviarAvisoRespuestaCliente sin JOptionPane (retorna mensaje)
    public static String enviarAvisoRespuestaClienteSinDialogo(String correo, String ELS, String Cliente, String Sucursal, String EstadoComercial) {
        String from = "Respuesta de Clientes ELS <" + getUser("smtp.respuestacliente", "") + ">";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - " + EstadoComercial;
        String cuerpo = "PROCEDER SEGÚN CORRESPONDA";
        SendResult r = enviarCorreoSinDialogo(from, correo, subject, cuerpo,
                     "smtp.respuestacliente", "smtp.respuestacliente", "smtp.respuestacliente");
        return r.success ? null : r.message;
    }

    // Método original (sobrecarga para compatibilidad)
    public static void enviarInformeAlCliente(String correo, String asunto, String cuerpo, String nombreArchivo) {
        enviarInformeAlCliente(correo, asunto, cuerpo, nombreArchivo, null, "Bariloche");
    }
    
    // Método original con ubicación (sobrecarga para compatibilidad)
    public static void enviarInformeAlCliente(String correo, String asunto, String cuerpo, String nombreArchivo, String ubicacion) {
        enviarInformeAlCliente(correo, asunto, cuerpo, nombreArchivo, null, ubicacion);
    }

    // Método modificado con archivos adicionales y ubicación
    public static void enviarInformeAlCliente(String correo, String asunto, String cuerpo, String nombreArchivo, ArrayList<File> archivosAdicionales) {
        enviarInformeAlCliente(correo, asunto, cuerpo, nombreArchivo, archivosAdicionales, "Bariloche");
    }

    // Método principal con todos los parámetros incluyendo ubicación (CORPORATIVO)
    public static void enviarInformeAlCliente(String correo, String asunto, String cuerpo, String nombreArchivo, ArrayList<File> archivosAdicionales, String ubicacion) {
        try {
            String rutaArchivoPDF;
            String rutaArchivoDOCX;
            String hostKey, userKey, passKey;
            String from;
            
            if (ubicacion != null && ubicacion.equalsIgnoreCase("Buenos Aires")) {
                rutaArchivoPDF = RutasSistema.adaptar(Config.get("files.buenosaires.pdf.path", "F:/ELS/Administracion/Sistema/Presupuestos PDF/"));
                rutaArchivoDOCX = RutasSistema.adaptar(Config.get("files.buenosaires.docx.path", "F:/ELS/Administracion/Sistema/Informes Siemens/"));
                hostKey = "smtp.buenosaires";
                userKey = "smtp.buenosaires";
                passKey = "smtp.buenosaires";
                from = "ELS <" + getUser("smtp.buenosaires", "") + ">";
            } else {
                rutaArchivoPDF = RutasSistema.adaptar(Config.get("files.bariloche.pdf.path", "F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/"));
                rutaArchivoDOCX = RutasSistema.adaptar(Config.get("files.bariloche.docx.path", "F:/ELS/Bariloche/Administracion/Sistema/Informes Siemens/"));
                hostKey = "smtp.bariloche";
                userKey = "smtp.bariloche";
                passKey = "smtp.bariloche";
                from = "ELS <" + getUser("smtp.bariloche", "") + ">";
            }
            
            System.out.println("Enviando informe con configuración:");
            System.out.println("  Host: " + getHost(hostKey, ""));
            System.out.println("  User: " + getUser(userKey, ""));
            System.out.println("  Ubicación: " + ubicacion);
            
            ArrayList<BodyPart> adjuntos = new ArrayList<>();
            BodyPart adjuntoPrincipal = new MimeBodyPart();
            File archivoPrincipal;
            
            if (nombreArchivo.endsWith(".pdf")) {
                archivoPrincipal = new File(rutaArchivoPDF + nombreArchivo);
                if (!archivoPrincipal.exists()) {
                    JOptionPane.showMessageDialog(null, "No se encontró el archivo PDF: " + rutaArchivoPDF + nombreArchivo, "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                adjuntoPrincipal.setDataHandler(new DataHandler(new FileDataSource(archivoPrincipal)));
            } else if (nombreArchivo.endsWith(".docx")) {
                archivoPrincipal = new File(rutaArchivoDOCX + nombreArchivo);
                if (!archivoPrincipal.exists()) {
                    JOptionPane.showMessageDialog(null, "No se encontró el archivo DOCX: " + rutaArchivoDOCX + nombreArchivo, "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                adjuntoPrincipal.setDataHandler(new DataHandler(new FileDataSource(archivoPrincipal)));
            } else {
                JOptionPane.showMessageDialog(null, "Formato de archivo no soportado. Use .pdf o .docx", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            adjuntoPrincipal.setFileName(nombreArchivo);
            adjuntos.add(adjuntoPrincipal);
            
            // Agregar archivos adicionales si existen
            if (archivosAdicionales != null && !archivosAdicionales.isEmpty()) {
                for (File archivoExtra : archivosAdicionales) {
                    if (archivoExtra.exists()) {
                        BodyPart adjuntoExtra = new MimeBodyPart();
                        adjuntoExtra.setDataHandler(new DataHandler(new FileDataSource(archivoExtra.getAbsolutePath())));
                        adjuntoExtra.setFileName(archivoExtra.getName());
                        adjuntos.add(adjuntoExtra);
                    } else {
                        System.out.println("Archivo adicional no encontrado: " + archivoExtra.getAbsolutePath());
                    }
                }
            }
            //diego
            // Convertir ArrayList a array
            BodyPart[] arrayAdjuntos = adjuntos.toArray(new BodyPart[0]);

            enviarCorreo(from, correo, asunto, cuerpo, hostKey, userKey, passKey, arrayAdjuntos);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo adjuntar el archivo.", "Error", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    // Versión sin JOptionPane (retorna null en éxito, o mensaje de error)
    public static String enviarInformeAlClienteSinDialogo(String correo, String asunto, String cuerpo,
                                                          String nombreArchivo, ArrayList<File> archivosAdicionales,
                                                          String ubicacion) {
        try {
            String rutaArchivoPDF;
            String rutaArchivoDOCX;
            String hostKey, userKey, passKey;
            String from;

            if (ubicacion != null && ubicacion.equalsIgnoreCase("Buenos Aires")) {
                rutaArchivoPDF = RutasSistema.adaptar(Config.get("files.buenosaires.pdf.path", "F:/ELS/Administracion/Sistema/Presupuestos PDF/"));
                rutaArchivoDOCX = RutasSistema.adaptar(Config.get("files.buenosaires.docx.path", "F:/ELS/Administracion/Sistema/Informes Siemens/"));
                hostKey = "smtp.buenosaires";
                userKey = "smtp.buenosaires";
                passKey = "smtp.buenosaires";
                from = "ELS <" + getUser("smtp.buenosaires", "") + ">";
            } else {
                rutaArchivoPDF = RutasSistema.adaptar(Config.get("files.bariloche.pdf.path", "F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/"));
                rutaArchivoDOCX = RutasSistema.adaptar(Config.get("files.bariloche.docx.path", "F:/ELS/Bariloche/Administracion/Sistema/Informes Siemens/"));
                hostKey = "smtp.bariloche";
                userKey = "smtp.bariloche";
                passKey = "smtp.bariloche";
                from = "ELS <" + getUser("smtp.bariloche", "") + ">";
            }

            ArrayList<BodyPart> adjuntos = new ArrayList<>();
            BodyPart adjuntoPrincipal = new MimeBodyPart();
            File archivoPrincipal;

            if (nombreArchivo.endsWith(".pdf")) {
                archivoPrincipal = new File(rutaArchivoPDF + nombreArchivo);
                if (!archivoPrincipal.exists()) {
                    return "No se encontr\u00f3 el archivo PDF: " + rutaArchivoPDF + nombreArchivo;
                }
                adjuntoPrincipal.setDataHandler(new DataHandler(new FileDataSource(archivoPrincipal)));
            } else if (nombreArchivo.endsWith(".docx")) {
                archivoPrincipal = new File(rutaArchivoDOCX + nombreArchivo);
                if (!archivoPrincipal.exists()) {
                    return "No se encontr\u00f3 el archivo DOCX: " + rutaArchivoDOCX + nombreArchivo;
                }
                adjuntoPrincipal.setDataHandler(new DataHandler(new FileDataSource(archivoPrincipal)));
            } else {
                return "Formato de archivo no soportado. Use .pdf o .docx";
            }

            adjuntoPrincipal.setFileName(nombreArchivo);
            adjuntos.add(adjuntoPrincipal);

            if (archivosAdicionales != null && !archivosAdicionales.isEmpty()) {
                for (File archivoExtra : archivosAdicionales) {
                    if (archivoExtra.exists()) {
                        BodyPart adjuntoExtra = new MimeBodyPart();
                        adjuntoExtra.setDataHandler(new DataHandler(new FileDataSource(archivoExtra.getAbsolutePath())));
                        adjuntoExtra.setFileName(archivoExtra.getName());
                        adjuntos.add(adjuntoExtra);
                    }
                }
            }

            BodyPart[] arrayAdjuntos = adjuntos.toArray(new BodyPart[0]);
            SendResult r = enviarCorreoSinDialogo(from, correo, asunto, cuerpo, hostKey, userKey, passKey, arrayAdjuntos);
            return r.success ? null : r.message;

        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo adjuntar el archivo: " + e.getMessage();
        }
    }
}
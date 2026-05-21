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

public class EnviarMail {

    // Puerto común para todos los servidores SMTP
    private static final String PORT = "587";
    
    // Configuración para Aviso de Informe (Gmail)
    private static final String HOST_AVISO_INFORME = "smtp.gmail.com";
    private static final String USER_AVISO_INFORME = "enviodeinformeels@gmail.com";
    private static final String PASSWORD_AVISO_INFORME = "aejjmhcufyorcdme";
    
    // Configuración para Aviso de Equipo Terminado (Gmail)
    private static final String HOST_EQUIPO_TERMINADO = "smtp.gmail.com";
    private static final String USER_EQUIPO_TERMINADO = "enviodeaceptacion@gmail.com";
    private static final String PASSWORD_EQUIPO_TERMINADO = "jcagbiwpahvcfqrf";
    
    // Configuración para Respuesta al Cliente (Gmail)
    private static final String HOST_RESPUESTA_CLIENTE = "smtp.gmail.com";
    private static final String USER_RESPUESTA_CLIENTE = "enviodeaceptacion@gmail.com";
    private static final String PASSWORD_RESPUESTA_CLIENTE = "jcagbiwpahvcfqrf";
    
    // Configuración para Buenos Aires (Corporativo)
    private static final String HOST_BUENOS_AIRES = "smtp.elsweb.com.ar";
    private static final String USER_BUENOS_AIRES = "els@elsweb.com.ar";
    private static final String PASSWORD_BUENOS_AIRES = "Minu4141";
    
    // Configuración para Bariloche (Corporativo)
    private static final String HOST_BARILOCHE = "smtp.elsweb.com.ar";
    private static final String USER_BARILOCHE = "diego.bertossi@elsweb.com.ar";
    private static final String PASSWORD_BARILOCHE = "Diego1216";

    // Método para obtener propiedades del servidor SMTP según el host
    private static Properties getMailProperties(String host, String user) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.user", user);
        props.setProperty("mail.smtp.auth", "true");
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
                                                     String host, String user, String password, BodyPart... adjuntos) {
        try {
            String[] listaDestinatarios = parseDestinatarios(destinatarios);

            if (listaDestinatarios.length == 0) {
                return new SendResult(false, "No se encontraron direcciones de correo válidas.");
            }

            Properties props = getMailProperties(host, user);
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
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
                                        String host, String user, String password, BodyPart... adjuntos) {
        SendResult result = enviarCorreoSinDialogo(from, destinatarios, subject, cuerpo, host, user, password, adjuntos);
        JOptionPane.showMessageDialog(null, result.message,
            result.success ? "Confirmaci\u00f3n de env\u00edo" : "Error",
            result.success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        return result.success;
    }

    // Método sobrecargado para enviar correos sin adjuntos
    private static boolean enviarCorreo(String from, String destinatarios, String subject, String cuerpo,
                                        String host, String user, String password) {
        return enviarCorreo(from, destinatarios, subject, cuerpo, host, user, password, new BodyPart[0]);
    }

    // Métodos específicos para cada tipo de correo
    
    // enviarAvisoInforme - Usa configuración de aviso de informe (Gmail)
    public static void enviarAvisoInforme(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Diagnosticados ELS <enviodeinformeels@gmail.com>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - DIAGNOSTICADO";
        enviarCorreo(from, correo, subject, "",
                     HOST_AVISO_INFORME, USER_AVISO_INFORME, PASSWORD_AVISO_INFORME);
    }

    // enviarAvisoInforme sin JOptionPane (retorna mensaje)
    public static String enviarAvisoInformeSinDialogo(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Diagnosticados ELS <enviodeinformeels@gmail.com>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - DIAGNOSTICADO";
        SendResult r = enviarCorreoSinDialogo(from, correo, subject, "",
                     HOST_AVISO_INFORME, USER_AVISO_INFORME, PASSWORD_AVISO_INFORME);
        return r.success ? null : r.message;
    }

    // enviarAvisoEquipoTerminado - Usa configuración de equipo terminado (Gmail)
    public static void enviarAvisoEquipoTerminado(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Terminados ELS <enviodeaceptacion@gmail.com>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - TERMINADO";
        enviarCorreo(from, correo, subject, "",
                     HOST_EQUIPO_TERMINADO, USER_EQUIPO_TERMINADO, PASSWORD_EQUIPO_TERMINADO);
    }

    // enviarAvisoEquipoTerminado sin JOptionPane (retorna mensaje)
    public static String enviarAvisoEquipoTerminadoSinDialogo(String correo, String ELS, String Cliente, String Sucursal) {
        String from = "Equipos Terminados ELS <enviodeaceptacion@gmail.com>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - TERMINADO";
        SendResult r = enviarCorreoSinDialogo(from, correo, subject, "",
                     HOST_EQUIPO_TERMINADO, USER_EQUIPO_TERMINADO, PASSWORD_EQUIPO_TERMINADO);
        return r.success ? null : r.message;
    }

    // enviarAvisoRespuestaCliente - Usa configuración de respuesta al cliente (Gmail)
    public static void enviarAvisoRespuestaCliente(String correo, String ELS, String Cliente, String Sucursal, String EstadoComercial) {
        String from = "Respuesta de Clientes ELS <enviodeaceptacion@gmail.com>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - " + EstadoComercial;
        String cuerpo = "PROCEDER SEGÚN CORRESPONDA";
        enviarCorreo(from, correo, subject, cuerpo,
                     HOST_RESPUESTA_CLIENTE, USER_RESPUESTA_CLIENTE, PASSWORD_RESPUESTA_CLIENTE);
    }

    // enviarAvisoRespuestaCliente sin JOptionPane (retorna mensaje)
    public static String enviarAvisoRespuestaClienteSinDialogo(String correo, String ELS, String Cliente, String Sucursal, String EstadoComercial) {
        String from = "Respuesta de Clientes ELS <enviodeaceptacion@gmail.com>";
        String subject = "ELS: " + ELS + " " + Cliente + "-" + Sucursal + " - " + EstadoComercial;
        String cuerpo = "PROCEDER SEGÚN CORRESPONDA";
        SendResult r = enviarCorreoSinDialogo(from, correo, subject, cuerpo,
                     HOST_RESPUESTA_CLIENTE, USER_RESPUESTA_CLIENTE, PASSWORD_RESPUESTA_CLIENTE);
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
            // Determinar la ruta según la ubicación y las credenciales
            String rutaArchivoPDF;
            String rutaArchivoDOCX;
            String host;
            String user;
            String password;
            String from;
            
            if (ubicacion != null && ubicacion.equalsIgnoreCase("Buenos Aires")) {
                rutaArchivoPDF = "F:/ELS/Administracion/Sistema/Presupuestos PDF/";
                rutaArchivoDOCX = "F:/ELS/Administracion/Sistema/Informes Siemens/";
                host = HOST_BUENOS_AIRES;
                user = USER_BUENOS_AIRES;
                password = PASSWORD_BUENOS_AIRES;
                from = "ELS <" + user + ">";
            } else {
                // Por defecto: Bariloche
                rutaArchivoPDF = "F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/";
                rutaArchivoDOCX = "F:/ELS/Administracion/Sistema/Informes Siemens/";
                host = HOST_BARILOCHE;
                user = USER_BARILOCHE;
                password = PASSWORD_BARILOCHE;
                from = "ELS <" + user + ">";
            }
            
            System.out.println("Enviando informe con configuración:");
            System.out.println("  Host: " + host);
            System.out.println("  User: " + user);
            System.out.println("  Ubicación: " + ubicacion);
            
            // Lista para almacenar todos los adjuntos
            ArrayList<BodyPart> adjuntos = new ArrayList<>();
            
            // Agregar el archivo principal (informe PDF o DOCX)
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

            enviarCorreo(from, correo, asunto, cuerpo, host, user, password, arrayAdjuntos);

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
            String host;
            String user;
            String password;
            String from;

            if (ubicacion != null && ubicacion.equalsIgnoreCase("Buenos Aires")) {
                rutaArchivoPDF = "F:/ELS/Administracion/Sistema/Presupuestos PDF/";
                rutaArchivoDOCX = "F:/ELS/Administracion/Sistema/Informes Siemens/";
                host = HOST_BUENOS_AIRES;
                user = USER_BUENOS_AIRES;
                password = PASSWORD_BUENOS_AIRES;
                from = "ELS <" + user + ">";
            } else {
                rutaArchivoPDF = "F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/";
                rutaArchivoDOCX = "F:/ELS/Administracion/Sistema/Informes Siemens/";
                host = HOST_BARILOCHE;
                user = USER_BARILOCHE;
                password = PASSWORD_BARILOCHE;
                from = "ELS <" + user + ">";
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
            SendResult r = enviarCorreoSinDialogo(from, correo, asunto, cuerpo, host, user, password, arrayAdjuntos);
            return r.success ? null : r.message;

        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo adjuntar el archivo: " + e.getMessage();
        }
    }
}
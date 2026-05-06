package consumoAPI;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConsumoAPI {

    // =====================================================================
    // WHATSAPP DESKTOP — APERTURA DIRECTA VIA POWERSHELL
    // =====================================================================

    /**
     * Abre WhatsApp Desktop directamente usando PowerShell Start-Process.
     * Si por cualquier motivo falla, hace fallback automático al navegador
     * con la URL correspondiente al número y mensaje.
     *
     * @param numero  Número internacional sin + ni espacios. Ej: 5491137688372
     * @param mensaje Texto a pre-cargar en la conversación.
     */
	public static void abrirWSP(String numero, String mensaje) {
	    String numeroLimpio = numero.replaceAll("[+\\s\\-]", "");
	    String mensajeCodificado;

	    try {
	        mensajeCodificado = java.net.URLEncoder.encode(mensaje, "UTF-8")
	            .replace("+", "%20");
	    } catch (IOException e) {
	        mensajeCodificado = mensaje;
	    }

	    String uriWSP = "whatsapp://send?phone=" + numeroLimpio + "&text=" + mensajeCodificado;
	    
	    try {
	        // Intentar abrir con Desktop (maneja mejor el protocolo)
	        System.out.println("📱 Intentando abrir WhatsApp: " + uriWSP);
	        Desktop.getDesktop().browse(new URI(uriWSP));
	        System.out.println("✅ Solicitud enviada (WhatsApp Desktop debería abrirse)");
	        
	    } catch (Exception e) {
	        // Si falla (incluye cuando no está instalado), usar navegador
	        System.out.println("⚠️ No se pudo abrir WhatsApp Desktop: " + e.getMessage());
	        System.out.println("🌐 Abriendo WhatsApp Web en navegador...");
	        abrirConNavegador(numeroLimpio, mensajeCodificado);
	    }
	}

	/**
	 * Fallback: abre el navegador predeterminado con WhatsApp Web
	 */
	private static void abrirConNavegador(String numeroLimpio, String mensajeCodificado) {
	    try {
	        String urlStr = "https://web.whatsapp.com/send?phone=" + numeroLimpio
	            + "&text=" + mensajeCodificado;
	        
	        System.out.println("🌐 Abriendo navegador: " + urlStr);
	        Desktop.getDesktop().browse(new URI(urlStr));
	        
	    } catch (URISyntaxException | IOException e) {
	        System.err.println("❌ Error al abrir navegador: " + e.getMessage());
	        JOptionPane.showMessageDialog(null,
	            "No se pudo abrir WhatsApp.\nError: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

    /**
     * @deprecated Usar {@link #abrirWSP(String, String)}.
     * Mantenido por compatibilidad con el código existente.
     */
    @Deprecated
    public static void abrirWSP(String Nombre, String numero, String mensaje) {
        abrirWSP(numero, mensaje);
    }

    // =====================================================================
    // COTIZACIÓN DEL DÓLAR
    // =====================================================================

    public static double[] consultaCotizacionDolar() {
        double[] cotizacionesPromedios = {0, 0};
        try {
        	
            URL url2 = new URL("https://dolarapi.com/v1/dolares");

            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                + "(KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "keep-alive");

            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject cotizacion = jsonArray.getJSONObject(i);
                    if (cotizacion.getString("casa").equals("oficial")) {
                        cotizacionesPromedios[0] = cotizacion.getDouble("venta");
                    }
                    if (cotizacion.getString("casa").equals("blue")) {
                        cotizacionesPromedios[1] = cotizacion.getDouble("venta");
                    }
                }
            } else {
                throw new RuntimeException("Error en la conexión: " + responseCode);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "No se puede conectar a internet.",
                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
            cotizacionesPromedios[0] = -1.0;
            cotizacionesPromedios[1] = -1.0;
        }
        return cotizacionesPromedios;
    }
}
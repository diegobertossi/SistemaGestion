 package consumoAPI;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConsumoAPI {
	
	
	public static double[] consultaCotizacionDolar() {
	    double[] cotizacionesPromedios = { 0, 0 };
	    try {
	        URL url = new URL("https://api.bluelytics.com.ar/v2/latest");
	        URL url2 = new URL("https://dolarapi.com/v1/dolares");
	        
	        // Configuración más robusta de la conexión
	        HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
	        conn.setRequestMethod("GET");
	        //conn.setRequestProperty("User-Agent", "Mozilla/5.0");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
	        conn.setConnectTimeout(5000);
	        conn.setReadTimeout(5000);
	        
	        // Añadir más encabezados de seguridad
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setRequestProperty("Connection", "keep-alive");
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("Código de respuesta: " + responseCode);
	        
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String inputLine;
	            StringBuilder response = new StringBuilder();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            
	            
	            /*URL*/
	            
//	            JSONObject jo = new JSONObject(response.toString());
//	            JSONObject oficialObject = jo.getJSONObject("oficial");
//	            double valorVentaOficial = oficialObject.getDouble("value_sell");
//	            JSONObject blueObject = jo.getJSONObject("blue");
//	            double valorVentaBlue = blueObject.getDouble("value_sell");
	            

	            /*URL2*/
	            JSONArray jsonArray = new JSONArray(response.toString());
	            
	            // Buscar las cotizaciones de oficial y blue
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
	        JOptionPane.showMessageDialog(null, "No se puede conectar a internet.", 
	                                      "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
	        e.printStackTrace();
	        cotizacionesPromedios[0] = -1.0;
	        cotizacionesPromedios[1] = -1.0;
	    }
	    return cotizacionesPromedios;
	}


	public static void abrirWSP(String Nombre, String numero, String mensaje) {

		try {

			Desktop.getDesktop().browse(new URI("https://api.whatsapp.com/send?phone=" + numero + "&text=" + mensaje));

		} catch (URISyntaxException ex) {

			System.out.println(ex);

		} catch (IOException e) {

			System.out.println(e);

		}

	}

}

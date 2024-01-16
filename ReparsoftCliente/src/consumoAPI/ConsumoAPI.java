package consumoAPI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class ConsumoAPI {

//	public static double[] consultaCotizacionDolar() {
//		double[] cotizacionesPromedios = { 0, 0 };
//		int maxRetries = 3;
//
//		for (int attempt = 1; attempt <= maxRetries; attempt++) {
//			try {
//				URL url = new URL("https://api.bluelytics.com.ar/v2/latest");
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setRequestMethod("GET");
//
//				int responseCode = conn.getResponseCode();
//				System.out.println("Response Code: " + responseCode);
//
//				if (responseCode != 200) {
//					throw new RuntimeException("Ocurrió un error " + responseCode);
//				} else {
//					// Parse JSON response
//					StringBuilder informationString = new StringBuilder();
//					try (Scanner scanner = new Scanner(conn.getInputStream())) {
//						while (scanner.hasNext()) {
//							informationString.append(scanner.nextLine());
//						}
//					}
//
//					JSONObject jo = new JSONObject(informationString.toString());
//
//					JSONObject oficialObject = jo.getJSONObject("oficial");
//					double valorPromedioOficial = oficialObject.getDouble("value_avg");
//
//					JSONObject blueObject = jo.getJSONObject("blue");
//					double valorPromedioBlue = blueObject.getDouble("value_avg");
//
//					cotizacionesPromedios[0] = valorPromedioOficial;
//					cotizacionesPromedios[1] = valorPromedioBlue;
//
//					// Return the result if successful
//					return cotizacionesPromedios;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.out.println("Attempt " + attempt + " failed. Retrying...");
//				// Wait for a short time before retrying
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException ex) {
//					ex.printStackTrace();
//				}
//			}
//		}
//
//		// Return default values in case of failure after retries
//		double[] valuesOnError = { -1.0, -1.0 };
//		return valuesOnError;
//
//	}

	public static double[] consultaCotizacionDolar() {

		//double compra = 0, venta = 0;
		double[] cotizacionesPromedios = { 0, 0 };

		try {
			URL url = new URL("https://api.bluelytics.com.ar/v2/latest");
			
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						
			conn.setRequestMethod("GET");
	        conn.connect();
		

			
			int responseCode = conn.getResponseCode();
			System.out.println(responseCode);
			
			
			if (responseCode != 200) {

				throw new RuntimeException("Ocurrió un error " + responseCode);

			} else {

				StringBuilder informationString = new StringBuilder();
				Scanner scanner = new Scanner(url.openStream());

				while (scanner.hasNext()) {

					informationString.append(scanner.nextLine());

				}

				scanner.close();

				JSONObject jo = new JSONObject(informationString.toString());
				
				
				
				JSONObject oficialObject = jo.getJSONObject("oficial");
			    double valorPromedioOficial = oficialObject.getDouble("value_avg");
			    
			    JSONObject blueObject = jo.getJSONObject("blue");
			    double valorPromedioBlue = blueObject.getDouble("value_avg");
			     
			    cotizacionesPromedios[0] = valorPromedioOficial;
			    cotizacionesPromedios[1] = valorPromedioBlue;
			    
			   
				return cotizacionesPromedios;

			}

		} catch (Exception e) {

			
			Object mje = "No se puede conectar a internet.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			double[] valuesOnError = { -1.0, -1.0 }; // Valores de reemplazo en caso de error
	        return valuesOnError;
			

		}

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

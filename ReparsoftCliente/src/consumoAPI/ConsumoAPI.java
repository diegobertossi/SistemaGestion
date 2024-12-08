package consumoAPI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONObject;

public class ConsumoAPI {


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
			    double valorVentaOficial = oficialObject.getDouble("value_sell");
			    
			    JSONObject blueObject = jo.getJSONObject("blue");
			    double valorVentaBlue = blueObject.getDouble("value_sell");
			     
			    cotizacionesPromedios[0] = valorVentaOficial;
			    cotizacionesPromedios[1] = valorVentaBlue;
			    
			   
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

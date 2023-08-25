package consumoAPI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

public class ConsumoAPI {

	public static double[] consultaCotizacionDolar() {

		double compra = 0, venta = 0;
		double[] cotizacionesPromedios = { 0, 0 };

		try {
			// https://www.metaweather.com/api/location/search/?query=Madrid --> no funciona
			// https://api-dolar-argentina.herokuapp.com/api/nacion --> OK
			//URL url = new URL("https://api-dolar-argentina.herokuapp.com/api/nacion");
			
			URL url = new URL("https://api.bluelytics.com.ar/v2/latest");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {

				throw new RuntimeException("Ocurri� un error " + responseCode);

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

	        }catch(IOException e){

	            System.out.println(e);

	        }
		



	}

}

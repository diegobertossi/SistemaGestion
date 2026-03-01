package VistaPropias;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Clase para corrección gramatical usando LanguageTool API
 * VERSIÓN CORREGIDA - CON SOPORTE UTF-8 EXPLÍCITO
 */
public class CorrectorGramaticalAPI {
    
    public static class ErrorGramatical {
        private String mensaje;
        private String mensajeCorto;
        private int offset;
        private int longitud;
        private String contexto;
        private int offsetContexto;
        private List<String> sugerencias;
        private String categoria;
        
        public ErrorGramatical() {
            this.sugerencias = new ArrayList<>();
        }
        
        // Getters y Setters (igual que antes)
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
        
        public String getMensajeCorto() { return mensajeCorto; }
        public void setMensajeCorto(String mensajeCorto) { this.mensajeCorto = mensajeCorto; }
        
        public int getOffset() { return offset; }
        public void setOffset(int offset) { this.offset = offset; }
        
        public int getLongitud() { return longitud; }
        public void setLongitud(int longitud) { this.longitud = longitud; }
        
        public String getContexto() { return contexto; }
        public void setContexto(String contexto) { this.contexto = contexto; }
        
        public int getOffsetContexto() { return offsetContexto; }
        public void setOffsetContexto(int offsetContexto) { this.offsetContexto = offsetContexto; }
        
        public List<String> getSugerencias() { return sugerencias; }
        public void setSugerencias(List<String> sugerencias) { this.sugerencias = sugerencias; }
        
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        
        public String getTextoErroneo() {
            if (contexto != null && offsetContexto >= 0 && longitud > 0) {
                int inicio = offsetContexto;
                int fin = Math.min(inicio + longitud, contexto.length());
                if (inicio < fin && fin <= contexto.length()) {
                    return contexto.substring(inicio, fin);
                }
            }
            return "";
        }
        
        @Override
        public String toString() {
            return "ErrorGramatical{" +
                    "mensaje='" + mensaje + '\'' +
                    ", sugerencias=" + sugerencias +
                    ", textoErroneo='" + getTextoErroneo() + '\'' +
                    '}';
        }
    }
    
    public static class ResultadoRevision {
        private String idioma;
        private List<ErrorGramatical> errores;
        private String textoOriginal;
        
        public ResultadoRevision(String textoOriginal) {
            this.textoOriginal = textoOriginal;
            this.errores = new ArrayList<>();
        }
        
        public String getIdioma() { return idioma; }
        public void setIdioma(String idioma) { this.idioma = idioma; }
        
        public List<ErrorGramatical> getErrores() { return errores; }
        public void setErrores(List<ErrorGramatical> errores) { this.errores = errores; }
        
        public String getTextoOriginal() { return textoOriginal; }
        
        public boolean hayErrores() { return !errores.isEmpty(); }
        
        public int getTotalErrores() { return errores.size(); }
    }
    
    /**
     * Envía texto a la API y devuelve resultado parseado
     * VERSIÓN CORREGIDA
     */
    public static ResultadoRevision revisarTexto(String texto) throws IOException, JsonSyntaxException {
        String respuestaJson = llamarAPI(texto);
        return parsearRespuesta(texto, respuestaJson);
    }
    
    /**
     * Llama a la API y obtiene la respuesta JSON cruda
     * VERSIÓN CORREGIDA - FORZA UTF-8
     */
    private static String llamarAPI(String texto) throws IOException {
        String urlString = "https://api.languagetool.org/v2/check";
        
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        
        // Configurar cabeceras para UTF-8
        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conexion.setRequestProperty("Accept", "application/json; charset=UTF-8");
        conexion.setRequestProperty("Accept-Charset", "UTF-8");
        conexion.setDoOutput(true);
        
        // Preparar parámetros con UTF-8 explícito
        String parametros = "text=" + URLEncoder.encode(texto, StandardCharsets.UTF_8.name()) 
                          + "&language=auto"
                          + "&enabledOnly=false";
        
        // Enviar como bytes UTF-8
        try (DataOutputStream escritor = new DataOutputStream(conexion.getOutputStream())) {
            escritor.write(parametros.getBytes(StandardCharsets.UTF_8));
            escritor.flush();
        }
        
        int codigoRespuesta = conexion.getResponseCode();
        if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
            // Leer error response también en UTF-8
            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(conexion.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder errorResponse = new StringBuilder();
                String linea;
                while ((linea = errorReader.readLine()) != null) {
                    errorResponse.append(linea);
                }
                throw new IOException("Error en la API. Código: " + codigoRespuesta + 
                                    " - " + errorResponse.toString());
            }
        }
        
        // LEER LA RESPUESTA FORZANDO UTF-8 (¡SOLUCIÓN AL PROBLEMA!)
        StringBuilder respuesta = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                respuesta.append(linea);
            }
        }
        
        conexion.disconnect();
        return respuesta.toString();
    }
    
    /**
     * Parsea la respuesta JSON (sin cambios, ya que el String ya está correcto)
     */
    private static ResultadoRevision parsearRespuesta(String textoOriginal, String jsonResponse) {
        ResultadoRevision resultado = new ResultadoRevision(textoOriginal);
        
        JsonObject respuestaJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
        
        if (respuestaJson.has("language")) {
            JsonObject language = respuestaJson.getAsJsonObject("language");
            resultado.setIdioma(language.get("name").getAsString());
        }
        
        if (respuestaJson.has("matches")) {
            JsonArray matches = respuestaJson.getAsJsonArray("matches");
            
            for (int i = 0; i < matches.size(); i++) {
                JsonObject match = matches.get(i).getAsJsonObject();
                ErrorGramatical error = new ErrorGramatical();
                
                if (match.has("message")) {
                    error.setMensaje(match.get("message").getAsString());
                }
                if (match.has("shortMessage")) {
                    error.setMensajeCorto(match.get("shortMessage").getAsString());
                }
                
                if (match.has("offset")) {
                    error.setOffset(match.get("offset").getAsInt());
                }
                if (match.has("length")) {
                    error.setLongitud(match.get("length").getAsInt());
                }
                
                if (match.has("context")) {
                    JsonObject context = match.getAsJsonObject("context");
                    if (context.has("text")) {
                        error.setContexto(context.get("text").getAsString());
                    }
                    if (context.has("offset")) {
                        error.setOffsetContexto(context.get("offset").getAsInt());
                    }
                }
                
                if (match.has("rule")) {
                    JsonObject rule = match.getAsJsonObject("rule");
                    if (rule.has("category")) {
                        JsonObject category = rule.getAsJsonObject("category");
                        error.setCategoria(category.get("name").getAsString());
                    }
                }
                
                if (match.has("replacements")) {
                    JsonArray replacements = match.getAsJsonArray("replacements");
                    List<String> sugerencias = new ArrayList<>();
                    for (int j = 0; j < replacements.size(); j++) {
                        JsonObject replacement = replacements.get(j).getAsJsonObject();
                        if (replacement.has("value")) {
                            sugerencias.add(replacement.get("value").getAsString());
                        }
                    }
                    error.setSugerencias(sugerencias);
                }
                
                resultado.getErrores().add(error);
            }
        }
        
        return resultado;
    }
}
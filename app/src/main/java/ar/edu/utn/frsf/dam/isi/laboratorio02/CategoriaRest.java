package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaRest {

    public CategoriaRest(){}

    // realiza el POST de una categoría al servidor REST
    public void crearCategoria(Categoria c) {
        try{
            //Variables de conexión y stream de escritura y lectura
            HttpURLConnection urlConnection = null;
            DataOutputStream printout =null;
            InputStream in =null;
            //Crear el objeto json que representa una categoria
            JSONObject categoriaJson = new JSONObject();
            categoriaJson.put("nombre",c.getNombre());
            //Abrir una conexión al servidor para enviar el POST
            URL url = new URL("http://10.0.2.2:5000/categorias/");

            //AGREGAMOS ESTO PORQUE NO ANDA DE NINGUNA OTRA FORMA
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestMethod("POST");

            //Obtener el outputStream para escribir el JSON
            printout = new DataOutputStream(urlConnection.getOutputStream());
            String str = categoriaJson.toString();
            byte[] jsonData=str.getBytes("UTF-8");
            printout.write(jsonData);
            printout.flush();

            //Leer la respuesta
            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();

            //Analizar el codigo de lar respuesta
            if( urlConnection.getResponseCode() ==200 || urlConnection.getResponseCode()==201){
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                }
                //analizar los datos recibidos
                Log.d("LAB_04",sb.toString());
            }
            else{
                // lanzar excepcion o mostrar mensaje de error que no se pudo ejecutar la operacion
                throw new JSONException("no se pudo ejecutar la operacion");
            }

            // caputurar todas las excepciones y en el bloque finally cerrar todos los streams y HTTPUrlCOnnection
            if(printout!=null){
                try {
                    printout.close();
                }catch (IOException e2){
                    e2.printStackTrace();
                }finally {
                    printout.close();
                    in.close();

                }
            }
            if(in!=null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    printout.close();
                    in.close();
                }
            }
            if(urlConnection !=null) urlConnection.disconnect();
        }
        catch(JSONException e){
            e.printStackTrace();
        }catch (IOException q){
            q.printStackTrace();
        }

    }

    public List<Categoria> listarTodas(){
        // inicializar variables
        List<Categoria> resultado = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        InputStream in =null;

        try{
            // GESTIONAR LA CONEXION
            URL url = new URL("http://10.0.2.2:5000/categorias/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Type","application/json");
            urlConnection.setRequestMethod("GET");

            // Leer la respuesta
            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();

            // verificar el codigo de respuesta
            if( urlConnection.getResponseCode() ==200 || urlConnection.getResponseCode()==201){
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                }

                // ver datos recibidos
                Log.d("LAB_04",sb.toString());

                // Transformar respuesta a JSON
                JSONTokener tokener = new JSONTokener(sb.toString());
                JSONArray listaCategorias = (JSONArray) tokener.nextValue();

                // iterar todas las entradas del arreglo
                for(int i = 0; i < listaCategorias.length() ; i++){
                    String nombreCategoria = listaCategorias.getJSONObject(i).getString("nombre");
                    int idCategoria = listaCategorias.getJSONObject(i).getInt("id");
                    Categoria cat = new Categoria(idCategoria, nombreCategoria);

                    // analizar cada element del JSONArray
                    // armar una instancia de categoría y agregarla a la lista
                    resultado.add(cat);
                }
            }
            else{
                // lanzar excepcion o mostrar mensaje de error
                // que no se pudo ejecutar la operacion
                //throw new IOException("No se puedo ejecutar la operacion");
                throw new JSONException("No funca");
            }

            //NO OLVIDAR CERRAR inputStream y conexion
            if(in!=null) in.close();
            if(urlConnection !=null) urlConnection.disconnect();
            //retornar resultado

        }catch (IOException io){
            io.printStackTrace();
        }catch (JSONException js){
            js.printStackTrace();
        }

        return resultado;
    }
}

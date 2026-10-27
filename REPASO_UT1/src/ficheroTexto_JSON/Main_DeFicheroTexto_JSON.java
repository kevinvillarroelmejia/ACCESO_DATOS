package ficheroTexto_JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main_DeFicheroTexto_JSON {
	public static final String FICHERO_TXT = "src" + File.separator + "ficheroTexto_JSON" + File.separator
			+ "stock.txt";
	
	public static final String FICHERO_JSON="src"+File.separator+"ficheroTexto_JSON"+File.separator+"articulosJSON";

	public static void main(String[] args) {
		cargarJSON(leerTXT_returnlistaArticulos());
	}

	//leemos el TXT y devuelve un list para escribirlo en el XML
	public static List<Articulo> leerTXT_returnlistaArticulos() {
		List<Articulo> listaArticulos = new ArrayList<Articulo>();
		try (BufferedReader lector = new BufferedReader(new FileReader(FICHERO_TXT))) {
			String linea;
			String referencia="";
			String descripcion="";
			int stock=0;
			referencia=lector.readLine();//comprobando si existe un articulo
			while (referencia  != null && !referencia.equals("")) {
//				System.out.println(linea);
				descripcion=lector.readLine();
				stock=Integer.parseInt(lector.readLine());//lo leemos como String y lo convertimos a int 
				Articulo articulo=new Articulo(referencia, descripcion, stock);
				listaArticulos.add(articulo);
				
				referencia=lector.readLine();//volvemos a leer referencia comprobando si hay otro articulo
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaArticulos;
	}
	
	public static void cargarJSON(List<Articulo> listaArticulos) {
		try (Writer escritor = new FileWriter(FICHERO_JSON)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(listaArticulos, escritor);

		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}

}

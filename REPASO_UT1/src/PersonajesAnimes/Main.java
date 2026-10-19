package PersonajesAnimes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		String rutaAnimes="src"+File.separator+"PersonajesAnimes"+File.separator+"animes.txt";
		String rutaPersonajes="src"+File.separator+"PersonajesAnimes"+File.separator+"personajes.txt";
		HashMap<Integer, String> diccionario=diccionarioAnimes(rutaAnimes);
//		for (Map.Entry<Integer, String> entry : diccionario.entrySet()) {
//		    Integer clave = entry.getKey();
//		    String valor = entry.getValue();
//		    System.out.println(clave +" "+ valor);
//		}
		
	}
	public static HashMap<Integer, String> diccionarioAnimes(String fichero) {
		HashMap<Integer, String> diccionarioAnimes=new HashMap<Integer, String>();
		Integer clave=0;
		String nombreAnime="";
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				
				//CORTAMOS POR EL PRIMER ESPACIO
				int posicion = linea.indexOf(" ");
				clave = Integer.parseInt(linea.substring(0, posicion));
				nombreAnime = linea.substring(posicion + 1);
				diccionarioAnimes.put(clave, nombreAnime);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return diccionarioAnimes;
	}
	
	public static void leerPersonajes(HashMap<Integer, String> animes) {
		
	}

}

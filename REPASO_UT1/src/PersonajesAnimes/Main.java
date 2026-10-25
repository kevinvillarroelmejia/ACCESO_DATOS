package PersonajesAnimes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {

		String rutaAnimes = "src" + File.separator + "PersonajesAnimes" + File.separator + "animes.txt";
		String rutaPersonajes = "src" + File.separator + "PersonajesAnimes" + File.separator + "personajes.txt";
		TreeMap<Integer, String> diccionario = diccionarioAnimes(rutaAnimes);
//		for (Map.Entry<Integer, String> entry : diccionario.entrySet()) {
//		    Integer clave = entry.getKey();
//		    String valor = entry.getValue();
//		    System.out.println(clave +" "+ valor);
//		}
		mostrarAnimesConPersonajes(diccionario, rutaPersonajes);
		mostrarPersonajes_Sin_Anime(diccionario, rutaPersonajes);
	}

	public static TreeMap<Integer, String> diccionarioAnimes(String ficheroAnime) {
		TreeMap<Integer, String> diccionarioAnimes = new TreeMap<Integer, String>();
		Integer clave = 0;
		String nombreAnime = "";
		try (BufferedReader lector = new BufferedReader(new FileReader(ficheroAnime))) {
			String linea;
			while ((linea = lector.readLine()) != null) {

				// CORTAMOS POR EL PRIMER ESPACIO
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

	// PERSONAJES SIN ANIME
	public static void mostrarPersonajes_Sin_Anime(TreeMap<Integer, String> dicAnimes, String ficheroPersonajes)
			throws IOException {
		// por cada entrada del diccionario el fichero se abre y recorre si coinciden
		// las claves
		System.out.println("Personajes sin anime");
		ArrayList<String> personajesSinAnime = new ArrayList<String>();
		try (BufferedReader lector = new BufferedReader(new FileReader(ficheroPersonajes))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int clave = Integer.parseInt(linea.substring(0, posicion));

				String nombre = linea.substring(posicion + 1);
				
				//se reinicia por cada personaje
				boolean encontrado=false;
				for (Map.Entry<Integer, String> entry : dicAnimes.entrySet()) {
					if (clave == entry.getKey()) {
						encontrado=true;
					}
				}
				if(!encontrado) {
					System.out.println("- "+nombre);
				}
			}

		}

	}

	// devuelve arrayList con la lista de los personajes que tengan el mismo codigo
	public static ArrayList<String> personajesDe(int codigo, String ficheroPersonaje) {
		ArrayList<String> personajes = new ArrayList<String>();
		try (BufferedReader lector = new BufferedReader(new FileReader(ficheroPersonaje))) {
			String linea;
			String personaje = "";
			int clavePersonaje;
			while ((linea = lector.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				clavePersonaje = Integer.parseInt(linea.substring(0, posicion).trim());
				if (clavePersonaje == codigo) {
					personaje = linea.substring(posicion + 1).trim();
					personajes.add(personaje);
				}
			}
		} catch (Exception e) {
			e.getMessage();
			System.out.println("Error 1");
		}
		return personajes;
	}

	public static void mostrarAnimesConPersonajes(TreeMap<Integer, String> dicAnimes, String ficheroPersonajes) {
		// por cada entrada del diccionario el fichero se abre y recorre si coinciden
		// las claves
		for (Map.Entry<Integer, String> entry : dicAnimes.entrySet()) {
			// SOLO valor
			System.out.println(entry.getValue());
			ArrayList<String> listaPersonajes = personajesDe(entry.getKey(), ficheroPersonajes);
			// ¿Esta vacia?
			if (listaPersonajes.isEmpty()) {
				System.out.println("- No hay personajes");
			} else {
				for (String n : listaPersonajes) {
					System.out.println("- " + n);
				}
			}
		}
	}

}

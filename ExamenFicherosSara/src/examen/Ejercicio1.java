package examen;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Ejercicio1 {
	public static void main(String[] args) {
		/**
		 * Se que este ejercicio podría estar muchísimo mejor en cuanto a modelado y diseño de métodos,
		 * pero no conseguía hacer bien los returns de la información que necesitaba para poder mostrar
		 * el resultado.
		 * Además de que finalmente no he seguido la guía de solución propuesta y he decidido hacer
		 * tanto un diccionario para los personajes como para los animes y así poder comparar por la key
		 * de cada uno
		 * 
		 */
		String ficheroPersonajes = "/home/alumno/eclipse-workspace/DAM2Boletin1/src/ej1/personajes.txt";
		String ficheroAnime = "/home/alumno/eclipse-workspace/DAM2Boletin1/src/ej1/animes.txt";
		
		HashMap<String, String> diccionarioAnimes = new HashMap<>();
		
		
		// 1. Lectura de fichero de anime para añadir a dccionario
		try {
			File fichero = new File(ficheroAnime);
			Scanner lector = new Scanner(fichero);
			String linea;
			while(lector.hasNextLine()) {
				linea = lector.nextLine();
				linea = linea.replaceFirst(" ", "-");
				String[] elementos = linea.split("-");
				for (String elemento: elementos) {
					// System.out.println(elemento);
					diccionarioAnimes.put(elementos[0], elementos[1]);
				}
			}
			lector.close();
			
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
		
		HashMap<String, String> diccionarioPersonajes = new HashMap<>();
		
		
		// 2. Lectura de fichero de personajes para comparar
		try {
			File fichero = new File(ficheroPersonajes);
			Scanner lector = new Scanner(fichero);
			String linea;
			while(lector.hasNextLine()) {
				linea = lector.nextLine();
				linea = linea.replaceFirst(" ", "-");
				String[] elementos = linea.split("-");
				for (String elemento: elementos) {
					//System.out.println(elemento);
					diccionarioPersonajes.put(elementos[0], elementos[1]);
				}
			}
			lector.close();
			
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
		
		
		// 3. busqueda
		for(Entry<String, String> anime:diccionarioAnimes.entrySet()) {
			//System.out.printf("%s \n",anime.getKey());
			for(Entry<String, String> personaje:diccionarioPersonajes.entrySet()) {
				//System.out.printf("%s \n",personaje.getKey());
				if (anime.getKey().equals(personaje.getKey())) {
					System.out.println(anime.getValue());
					System.out.println("- "+ personaje.getValue());
				}
			}
		}

	}
}

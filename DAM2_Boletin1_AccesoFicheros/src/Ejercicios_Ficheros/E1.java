package Ejercicios_Ficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class E1 {

	private static final String FICHERO_PERSONAJES="datos" + File.separator +  "personajes.txt";;
	private static final String FICHERO_ANIMES="datos" + File.separator + "animes.txt";
	//Boletin 1 - Ejercicio 1
	public static void main(String[] args) {
		try {
			HashMap<Integer, String> animes = leerDAtosAnimes(FICHERO_ANIMES);
			for (Map.Entry<Integer, String> anime : animes.entrySet()) {
				ArrayList<String> personajes = leerPersonajes(anime.getKey(), FICHERO_PERSONAJES);
				System.out.println(anime.getValue());
				if (personajes.size() == 0) {
					System.out.println("No hay personajes");
				} else {
					for (String p : personajes) {
						System.out.println("- " + p);
					}
				}
			}
			ArrayList<String> personajes = leerPersonajesSinAnime(animes, FICHERO_PERSONAJES);
			if (personajes.size() != 0) {
				System.out.println("Personajes sin anime");
				for (String p : personajes) {
					System.out.println("- " + p);
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static HashMap<Integer, String> leerDAtosAnimes(String fichero) throws Exception {
		HashMap<Integer, String> animes = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int num = Integer.parseInt(linea.substring(0, posicion));
				String titulo = linea.substring(posicion + 1);
				animes.put(num, titulo);
			}
		}
		return animes;
	}
	
	private static ArrayList<String> leerPersonajes(int codigo, String fichero) throws Exception {
		ArrayList<String> personajes = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int num = Integer.parseInt(linea.substring(0, posicion));
				if (num == codigo) {
					String nombre = linea.substring(posicion + 1);
					personajes.add(nombre);
				}
			}
		}
		return personajes;
	}
	
	private static ArrayList<String> leerPersonajesSinAnime(HashMap<Integer, String> animesDiccionario, String ficherosPersonajes)throws Exception {
		ArrayList<String> personajes = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(ficherosPersonajes))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int num = Integer.parseInt(linea.substring(0, posicion));
				if (animesDiccionario.containsKey(num) == false) {
					String nombre = linea.substring(posicion + 1);
					personajes.add(nombre);
				}
			}
		}
		return personajes;
	}



}

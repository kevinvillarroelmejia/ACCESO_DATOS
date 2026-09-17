package correccionExamen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ejercicio1 {
	public static void main(String[] args) {
		String fPersonajes = "/home/alumno/personajes.txt";
		String fAnimes = "/home/alumno/animes.txt";
		
		try {
			HashMap<Integer, String> animes = leerDAtosAnimes(fAnimes);
			for (Map.Entry<Integer, String> anime: animes.entrySet()) {
				ArrayList<String> personajes = leerPersonajes(anime.getKey(), fPersonajes);
				System.out.println(anime.getValue());
				if (personajes.size()==0) {
					System.out.println("No hay personajes");
				} else {
					for (String p:personajes) {
						System.out.println("- " + p);
					}
				}
			}
			ArrayList<String> personajes = leerPersonajesSinAnime(animes, fPersonajes);
			if (personajes.size()!=0) {
				System.out.println("Personajes sin anime");
				for (String p:personajes) {
					System.out.println("- " + p);
				}
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}

	private static ArrayList<String> leerPersonajesSinAnime(HashMap<Integer, String> animes, String fichero) throws Exception {
		ArrayList<String> personajes = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int num = Integer.parseInt(linea.substring(0,posicion));
				if (animes.containsKey(num) == false) {
					String nombre = linea.substring(posicion+1);
					personajes.add(nombre);
				}
			}
		}
		return personajes;
	}

	private static ArrayList<String> leerPersonajes(int codigo, String fichero) throws Exception {
		ArrayList<String> personajes = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int num = Integer.parseInt(linea.substring(0,posicion));
				if (num==codigo) {
					String nombre = linea.substring(posicion+1);
					personajes.add(nombre);
				}
			}
		}
		return personajes;
	}

	private static HashMap<Integer, String> leerDAtosAnimes(String fichero) throws Exception {
		HashMap<Integer, String> animes = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				int posicion = linea.indexOf(" ");
				int num = Integer.parseInt(linea.substring(0,posicion));
				String titulo = linea.substring(posicion+1);
				animes.put(num, titulo);
			}
		}
		return animes;
	}
}

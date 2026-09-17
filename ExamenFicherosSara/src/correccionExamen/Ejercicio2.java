package correccionExamen;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import examen.Personaje;

public class Ejercicio2 {
	public static void main(String[] args) {
		String fPersonajes = "/home/alumno/personajes.txt";
		String fAnimes = "/home/alumno/animes.txt";
		String fBinario = "/home/alumno/perosnajes.dat";
		
		try {
			HashMap<Integer, String> animes = leerDAtosAnimes(fAnimes);
			ArrayList<Personaje> listaPersonajes = new ArrayList<>();

			for (Map.Entry<Integer, String> anime: animes.entrySet()) {
				ArrayList<String> personajes = leerPersonajes(anime.getKey(), fPersonajes);
				if (personajes.size()!=0) {
					for (String p:personajes) {
						Personaje objeto = new Personaje(anime.getValue(), p);
						listaPersonajes.add(objeto);
					}
				}
			}
			escribirBinario(listaPersonajes, fBinario);
			ArrayList<Personaje> listaFichero = leerBinario(fBinario);
			ArrayList<String> personajes = leerPersonajesSinAnime(animes, fPersonajes);
			for (Personaje p: listaPersonajes) {
			//	System.out.println(p.mostrarPersonaje());
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}

	private static ArrayList<Personaje> leerBinario(String fichero) throws Exception {
		ArrayList<Personaje> lista = null;
		try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream(fichero))) {
			lista = (ArrayList<Personaje>)lector.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	private static void escribirBinario(ArrayList<Personaje> listaPersonajes, String fichero) throws Exception {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(listaPersonajes);
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

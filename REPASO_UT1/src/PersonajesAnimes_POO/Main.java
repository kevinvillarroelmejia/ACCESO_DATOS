package PersonajesAnimes_POO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


//[MAIN] TXT animes + TXT personajes → DAT (binario) → leer DAT
public class Main {

	public static void main(String[] args) {


		String rutaAnimes = "src" + File.separator + "PersonajesAnimes" + File.separator + "animes.txt";
		String rutaPersonajes = "src" + File.separator + "PersonajesAnimes" + File.separator + "personajes.txt";
		
		String rutaBAT="src"+File.separator+"PersonajesAnimes_POO"+File.separator+"personajes.dat";
		
		TreeMap<Integer, String> diccionario = diccionarioAnimes(rutaAnimes);

		escribirPersonajeDAT(diccionario, rutaPersonajes, rutaBAT);
		leerPersonajeDAT(rutaBAT);
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

	// [BUSCAR] personajes de un código de anime → lista de nombres
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
		}
		return personajes;
	}

	
	// [ESCRIBIR DAT] crea objetos Personaje (anime + nombre) y los guarda en binario
	public static ArrayList<Personaje> escribirPersonajeDAT(TreeMap<Integer, String> dicAnimes, String ficheroPersonajes,String ficheroBAT) {
		// por cada entrada del diccionario el fichero se abre y recorre si coinciden
		// las claves
		ArrayList<Personaje> listaOBjetosPersonaje=new ArrayList<Personaje>();
		for (Map.Entry<Integer, String> entry : dicAnimes.entrySet()) {
			// SOLO valor
			ArrayList<String> listaPersonajes = personajesDe(entry.getKey(), ficheroPersonajes);
			// ¿Esta vacia?
			if (!listaPersonajes.isEmpty()) {
				for (String nombrePersonaje : listaPersonajes) {
					String anime=entry.getValue();
					
					Personaje objectPersonaje=new Personaje(anime,nombrePersonaje);
					listaOBjetosPersonaje.add(objectPersonaje);

				}
			}
		}
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(ficheroBAT))) {
			binario.writeObject(listaOBjetosPersonaje);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return listaOBjetosPersonaje;
	}
	
	
	// [LEER DAT] lee la lista del binario y la imprime
	public static void leerPersonajeDAT(String ficheroBAT) {
		ArrayList<Personaje> lista = null;
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(ficheroBAT))) {
			lista = (ArrayList<Personaje>) binario.readObject();
			for(Personaje persona:lista) {
				System.out.println(persona);
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}

package examen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Ejercicio2 {
	public static void main(String[] args) {
		Personaje p1 = new Personaje("Roronoa Zoro", "One Piece");
		Personaje p2 = new Personaje("Vinsmoke Sanji", "One Piece");
		Personaje p3 = new Personaje("Tanjiro Kamado", "Demon Slayer");
		Personaje p4 = new Personaje("Naruto Uzumaki", "Naruto");
		Personaje p5 = new Personaje("Ken Takakura", "Dan Da Dan");
		ArrayList<Personaje> listaPersonajes = new ArrayList<>();
		listaPersonajes.add(p1);
		listaPersonajes.add(p2);
		listaPersonajes.add(p3);
		listaPersonajes.add(p4);
		listaPersonajes.add(p5);
		String fichero = "/home/alumno/personajes.dat";
		
		escribirPersonajes(listaPersonajes, fichero);
		
		ArrayList<Personaje> listaRecuperada = leerLista(fichero);
		for (Personaje tarea: listaRecuperada) {
			tarea.mostrarPersonaje();
		}
	}
	
	private static void escribirPersonajes(ArrayList<Personaje> listaPersonajes, String fichero) {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(listaPersonajes);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private static ArrayList<Personaje> leerLista(String fichero) {
		ArrayList<Personaje> lista = null;
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
			lista = (ArrayList<Personaje>)binario.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

}

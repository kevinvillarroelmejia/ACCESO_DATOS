package ejs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ej4 {
	public static void main(String[] args) {
		darLaVuelta("origen.txt", "destino.txt");
		
		ArrayList<String> lineas = null;
		Path fichero = Path.of("/home/alumno/fichero1.txt");
		try {
			lineas = (ArrayList<String>) Files.readAllLines(fichero);
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
		for (String linea:lineas) {
			System.out.println(linea);
		}
		
		List<String> alreves = lineas.reversed();
		for (String linea:alreves) {
			System.out.println(linea);
		}
	}
	
	private static void darLaVuelta(String string, String string2) {
		
	}
}
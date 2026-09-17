package ejs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ej4Profe {
	public static void main(String[] args) {
		darlaVuelta("origen.txt", "destino.txt");
	}

	private static void darlaVuelta(String origen, String destino) {
		ArrayList<String> listaOrigen = null;
		ArrayList<String> listaDestino = new ArrayList<>();
		
		listaOrigen = leerFichero(origen);
		if (listaOrigen!=null) {
			for (String linea:listaOrigen) {
				linea = invertirContenido(linea);
				listaDestino.add(0,linea);
			}
		}
		escribirFichero(destino, listaDestino);
		
	}
	
	public static ArrayList<String> leerFichero(String fichero) {
		ArrayList<String> lineas = null;
		Path fich = Path.of("/home/alumno/fichero1.txt");
		try {
			lineas = (ArrayList<String>) Files.readAllLines(fich);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lineas;
	}
	
	public static String invertirContenido(String linea) {
		String invertida = "";
		for (int i=0; i<linea.length(); i++) {
			invertida = linea.charAt(i) + invertida;
		}
		return invertida;
		
	}
	
	public static void escribirFichero(String fichero, ArrayList<String> lista) {
		Path ruta = Path.of(fichero);
		try {
			Files.write(ruta, lista);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
}

package ejs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ej6 {
	public static void main(String[] args) {
		String fSoluciones = "/home/alumno/boletin24ej6/soluciones.txt";
		String fRespuestas = "/home/alumno/boletin24ej6/respuestas.txt";
		String fNotas = "/home/alumno/boletin24ej6/notas.txt";
		int numPreguntas = 10;
		String soluciones[] = new String[numPreguntas];
		HashMap<String, String[]> respuestas = null;
		soluciones = leeSoluciones(fSoluciones);
		respuestas = leeRespuestas(fRespuestas);
		grabaNotas(fNotas, soluciones, respuestas);
	}

	private static String[] leeSoluciones(String fSoluciones) {
		Path fichero = Path.of(fSoluciones);
		String linea = null;
		try {
			linea = Files.readString(fichero);
		} catch (Exception e) {
			System.out.println("Error con el fichero: " + e.getMessage());
		}
		String[] soluciones = linea.split(", ");
		return soluciones;
	}
	
	private static HashMap<String, String[]> leeRespuestas(String fRespuestas) {
		HashMap <String, String[]> diccionario = new HashMap<>();
		try (BufferedReader lector = new BufferedReader (new FileReader(fRespuestas))) {
			String linea = null;
			while ((linea = lector.readLine())!=null) {
				int posicion = linea.indexOf(":");
				String alumno = linea.substring(0,posicion);
				String respuestas = linea.substring(posicion+2);
				diccionario.put(alumno,respuestas.split(", "));
			}
		} catch (Exception e) {
			System.out.println("Error con el fichero: " + e.getMessage());
		}
		return diccionario;
	}
	
	private static void grabaNotas(String fNotas, String[] soluciones, HashMap<String, String[]> respuestas) {
		// Añado lo nota a un fichero
		try (PrintWriter pluma = new PrintWriter(fNotas)) {
			// Recorrer el diccionario de respuestas con un bucle
			for (Map.Entry<String, String[]> respuesta: respuestas.entrySet()) {
				// Print por consola
				System.out.print(respuesta.getKey()+" : ");
				// Print en fichero
				System.out.println(calcularNota(soluciones, respuesta.getValue()));
				pluma.printf("%s: %.1f", respuesta.getKey(), calcularNota(soluciones, respuesta.getValue()));
				pluma.println();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Calculo la nota
	private static double calcularNota(String[] soluciones, String[] respuesta) {
		double nota = 0;
		for (int i=0; i<soluciones.length; i++) {
			
			if (soluciones[i].charAt(0) == respuesta[i].charAt(0)) {
				nota+=1;
			} else {
				nota-=0.3;
			}
		}
		if (nota < 0) {
			nota = 0;
		}
		return nota;
	}

	
}
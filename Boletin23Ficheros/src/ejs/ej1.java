package ejs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ej1 {
	// /home/alumno/hola.txt
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		boolean existe = false;
		String nombreFichero = null;
		while (existe == false) {
			System.out.print("Introduce el nombre del fichero: ");
			nombreFichero = teclado.nextLine();
			existe = existeElFichero(nombreFichero);
			if (existe == false) {
				System.out.printf("El fichero %s no existe\n", nombreFichero);
			}
		}
		
		ArrayList<String> lineas = devuelveContenido(nombreFichero);
		if (lineas != null) {
			System.out.println(lineas);
			System.out.print("Introduce la palabra a buscar: ");
			String palabra = teclado.nextLine();
			System.out.printf("El fichero tiene %d líneas\n", lineas.size());
			int contador = 0;
			for (String linea: lineas) {
				contador +=  cuentaPalabras(linea, palabra);
			}
			System.out.printf("La palabra %s aparece %d veces", palabra, contador);
		} else {
			System.out.println("El fichero está vacío o ha ocurrido un error al leerlo.");
		}
		teclado.close();
	}
		
		public static boolean existeElFichero(String fichero) {
			File f = new File(fichero);
			return (f.isFile());
			
		}
		
		public static ArrayList<String> devuelveContenido(String fichero) {
			ArrayList<String> contenido = null;
			Path ruta = Path.of(fichero);
			try {
				contenido = (ArrayList<String>) Files.readAllLines(ruta);
			} catch (Exception e) {
				System.out.printf("Error con el fichero %s\n", fichero);
				System.out.println(e.getMessage());
			}
			return contenido;
		}
		
		public static int cuentaPalabras(String linea, String palabra) {
			String[] palabras = linea.split("\\s+");
			int contador = 0;
			for (String p:palabras) {
				if (palabra.equals(p)) {
					contador++;
				}
			}
			return contador;
		}
		
		
	}

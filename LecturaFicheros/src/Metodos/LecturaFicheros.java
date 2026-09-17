package Metodos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class LecturaFicheros {
	public static void main(String[] args) {
		// No hay diferencia entre el método 1 y 2 puedo elegir el que quiera
		System.out.println("MÉTODO 1");
		metodo1();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 2");
		metodo2();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 3");
		
		metodo3();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 4");
		metodo4();
		
	}

	public static void metodo1() {
		try {
			// Para leer el fichero usamos bufferedReader que es como el "cursor" que recorre fichero
			// El BufferedReader es obligatorio de cerrar y lo que hace es mover el cursor en cada línea que se lee
			// el fichero es el FileReader
			BufferedReader lector = new BufferedReader(new FileReader("/home/alumno/hola.txt"));

			// Creamos una variable
			String linea;
			
			// Método 1 recorrido: con un do while
			do {
				linea = lector.readLine();
				if (linea != null) {
					System.out.println(linea);
				}
			} while (linea != null);
			
			// Método 2 recorrido: Más compacto en el mimso while todo
			while ((linea = lector.readLine())!= null) {
				System.out.println(linea);
			}

			// Hay que cerrar el lector siempre
			lector.close();
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			// esto devuleve el problema concreto con el fichero en español
			System.out.println(e.getMessage());
		}

	}
	
	public static void metodo2() {
		try {
			File fichero = new File("/home/alumno/hola.txt");
			// El lector en este método es Scanner
			Scanner lector = new Scanner(fichero);
			
			String linea;
			// hasNextLine dice si se puede seguir leyendo o no
			// Elimina automáticamente los \n
			while(lector.hasNextLine()) {
				linea = lector.nextLine();
				System.out.println(linea);
			}
			
			// Cerramos siempre el Scanner lector
			lector.close();
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
	}
	
	public static void metodo3() {
		ArrayList<String> lineas = null;
		// Path no hace falta que vaya dentro e la excepción porque no genera ninguna
		Path fichero = Path.of("/home/alumno/hola.txt");
		try {
			lineas = (ArrayList<String>) Files.readAllLines(fichero);
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
		for (String linea:lineas) {
			System.out.println(linea);
		}
	}
	
	public static void metodo4() {
		// Aquí no se nos han eliminado los \n en los 3 métodos anteriores si
		// Este método además de conservar los \n añade un \n al final del fichero
		// Comprobar si el fichero tiene al final ya un \n añadiría otro al final
		Path fichero = Path.of("/home/alumno/hola.txt");
		String contenido = null;
		try {
			contenido = Files.readString(fichero);
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
		System.out.println(contenido);
	}

}

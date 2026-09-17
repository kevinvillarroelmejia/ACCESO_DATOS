package Metodos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class EscrituraFicheros {
	public static void main(String[] args) {
		System.out.println("MÉTODO 1");
		//metodo1();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 2");
		//metodo2();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 3");
		//metodo3();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 4");
		//metodo4();
		System.out.println("-----------------------");
		System.out.println("MÉTODO 5");
		metodo5();
		
	}
	
	/* MODO ESCRIBIR
	 * Es el método más sencillo y simple pero no el más eficiente para mucho texto
	 * Con esto no teníamos el fichero creado y lo crea en la ubicación indicada
	 * El salto de línea hay que escribirlo a mano con \n sino no lo añade por defecto
	 * Cada vez que se ejecuta este método se borra el archivo y lo vuelve a crear con
	 * todo el contenido
	 * 
	 * MODO AÑADIR
	 * Si en el segundo parametro del constructor le ponemos como true estaríamos
	 * añadiendo contenido en vez de sobrescribir, si no ponemos true estaríamos
	 * sobreescribiendo
	 * 
	 * TRY WITH RESOURCES
	 * Podemos poner la construcción del objeto dentro de los parentesis de try
	 * y así nos evitamos cerrar el fichero y el diseño queda mucho más compacto
	 * */
	public static void metodo1() {
		try(FileWriter pluma = new FileWriter("/home/alumno/fichero.txt", true)) {
			// FileWriter pluma = new FileWriter("/home/alumno/fichero.txt", true);
			pluma.write("Hola mundo escrito\nen un fichero\n");
			pluma.write("Segunda línea\n\n");
			// pluma.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/* MODO ESCRIBIR
	 * En este método se usa Buffer y en vez de escribir directamente en el disco se
	 * escribe en este buffer de memoria y la escritura por lo tanto es mucho más rápida
	 * además nos aseguramos cerrando el Buffer de que todo queda escrito.
	 * Vamos a usar el try with resource ya siempre así es más cómodo y no nos olvidamos
	 * de cerrar nada.
	 * 
	 * MODO AÑADIR
	 * Igual que en el primer método ponemos en el constructor de FileWriter un segundo
	 * parámetro que es "true"
	 * 
	 * NEW LINE
	 * Es un método que añade un salto de línea como el \n y soluciona el problema de que
	 * todos los sistemas de archivos entiendan el salto de línea aún que en el 95% de los
	 * casos el \n es entendido si que es cierto que existen sistemas que no lo interpretan
	 * para esos casos usariamos el método .newLine()
	 * */
	public static void metodo2() {
		try(BufferedWriter pluma = new BufferedWriter(new FileWriter("/home/alumno/fichero.txt", true))) {
			pluma.write("Hola mundo escrito en un fichero");
			pluma.newLine();
			pluma.write("Segunda línea\n");
			pluma.newLine();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/* METODO DE ESCRITURA CON FORMATO COMO PRINTF
	 * Print normal no añade salto de linea al final y println si
	 * Para escribir con formato se usa printf y creamos las varibales para la info
	 * 
	 * Podemos pasar otro parámetro al constructor y le decimos con que sistema
	 * de caracteres estamos grabando
	 * 
	 * MODO AÑADIR
	 * Si quisieramos escribir tendríamos que hacerlo en el método anterior (esta en la ínea comentada)
	 * 
	 * */
	public static void metodo3() {
		try(PrintWriter pluma = new PrintWriter("/home/alumno/fichero.txt", StandardCharsets.UTF_8)) {
		// try(PrintWriter pluma = new PrintWriter(new FileWriter("/home/alumno/fichero.txt", StandardCharsets.UTF_8, true))) {
			pluma.print("Primera linea. ");
			pluma.println("Sigo en la primera línea línea");
			pluma.println("Segunda línea");
			String nombre = "Sara García Martín";
			int edad = 19;
			double sueldo = 2000.33;
			pluma.printf("Nombre: %s. Edad: %d. Sueldo: %.2f", nombre, edad, sueldo);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/* GRABAR UNA LISTA EN UN FICHERO
	 * Se usa el método files.write y como parametro se pasa la ruta del fichero y la lista
	 * El sistema de caracteres es opcional de especificar
	 * 
	 * MODO AÑADIR - Existen 2 opciones
	 * Hay que añadir 2 parámetros más que son StandardOpenOption.CREATE, StandardOpenOption.APPEND
	 * Hay que añadir 1 parámetros más que es StandardOpenOption.APPEND
	 * 
	 * */
	public static void metodo4() {
		// Podemos ponerlo fuera de la zona de errores ya que no produce ninguna excepción
		Path ruta = Paths.get("/home/alumno/fichero.txt");
		ArrayList<String> lineas = new ArrayList<>(List.of("Primera línea", "Segunda línea", "Tercera línea"));
		try {
			// Así estamos añadiendo y especificando el sistema de caracteres
			Files.write(ruta, lineas, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			// Así escribimos, NO añadimos pero especificamos el sistema de caracteres
				// Files.write(ruta, lineas, StandardCharsets.UTF_8);
			// Así solo escribimos ni añadimos ni especficamos el sistema de caracteres
				// Files.write(ruta, lineas);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/* Método para grabar simples String pero pasando por parámetro específicamente lo que queremos que haga
	 * Hace automáticamente la apertura del fichero y lo cierra también
	 * */	
	public static void metodo5() {
		Path ruta = Paths.get("/home/alumno/fichero.txt");
		String contenido = "Hola mundo. Ultimo método de escritura!";
		try {
			Files.writeString(ruta, contenido, StandardCharsets.UTF_8);
			// Podemos no poner el sistema de caracteres: 
				// Files.writeString(ruta, contendio);
			// Para añadir:
				// Files.writeString(ruta, contenido, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}

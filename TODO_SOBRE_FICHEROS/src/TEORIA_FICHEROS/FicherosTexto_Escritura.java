package TEORIA_FICHEROS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================
 * TEORÍA 3 · FICHEROS DE TEXTO — ESCRITURA (Y MODIFICACIÓN)
 * ============================================================
 *
 * Origen:
 *   DAM_JAVA     / Ficheros/FicheroEscritura/FicheroEscritura
 *   DAM_JAVA     / TodoSobreFicheros/ficheroNormal/EscrituraFicherosNormal
 *   DAM_JAVA     / UT7_Ficheros_RA5/FicherosTexto/EscrituraFicherosTexto
 *   ACCESO_DATOS / EscrituraFicheros/Metodos/EscrituraFicheros
 *   (+ Boletín 24, CorreccionExamenOrdinaria, PracticandoExtraordinaria)
 *
 * ¿QUÉ ES?
 *   Grabar texto en un fichero. Si el fichero no existe, SE CREA.
 *
 * DOS MODOS (como en Linux):
 *   ESCRIBIR ( > )  -> si el fichero existe, BORRA su contenido y escribe desde cero.
 *   AÑADIR   ( >> ) -> escribe al FINAL de lo que ya había.
 *
 *   ¿Cómo se pone el modo añadir?
 *     FileWriter / BufferedWriter / PrintWriter -> new FileWriter(ruta, true)
 *     Files.write / Files.writeString           -> StandardOpenOption.CREATE, StandardOpenOption.APPEND
 *
 * EN ESCRITURA ES MUY IMPORTANTE CERRAR (si no, puede no grabarse todo).
 *   -> usamos siempre try-with-resources.
 *
 * ============================================================
 * LAS 5 FORMAS DE ESCRIBIR QUE HEMOS DADO
 * ============================================================
 *
 *   FORMA  CLASE                 PARA QUÉ                           SALTO DE LÍNEA
 *   -----  --------------------  ---------------------------------  ------------------
 *   1      FileWriter            la más simple (la menos eficiente)  \n a mano
 *   2      BufferedWriter        más rápida (escribe en un buffer)   newLine()
 *   3      PrintWriter           con formato: print/println/printf   println() o \n
 *   4      Files.write           grabar una LISTA de golpe           una línea por elemento
 *   5      Files.writeString     grabar un String de golpe           \n a mano
 */
public class FicherosTexto_Escritura {

	/*
	 * ============================================================
	 * FORMA 1 · FileWriter
	 * ============================================================
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: new FileWriter(ruta)  /  new FileWriter(ruta, true)
	 * Para qué sirve: abrir el fichero para escribir (lo crea si no existe).
	 * Qué recibe: la ruta y, opcional, true = modo AÑADIR.
	 *
	 * Nombre: write(String)
	 * Para qué sirve: escribir el texto tal cual.
	 * Qué recibe: String.
	 * Qué devuelve: nada.
	 * OJO: write NO mete el \n, hay que ponerlo nosotros.
	 *
	 * Cómo lo hemos utilizado: tablas de multiplicar, Fibonacci, lista
	 *   de la compra, notas del test, facturación de la veterinaria.
	 *   Para meter formato con write usamos String.format(...) (Ejercicio1_2).
	 * ============================================================
	 */
	public static void forma1_fileWriter() {
		// Con close() a mano
		try {
			FileWriter pluma = new FileWriter("/home/alumno/Escritorio/ficheroEscritura.txt", true);
			pluma.write("hola mundo en un fichero");
			pluma.close(); // IMPORTANTE cerrar el fichero
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
		}
		// Igual pero con try-with-resources (nos ahorramos el close)
		try (FileWriter escritor = new FileWriter("/mnt/temp/java.txt")) {
			escritor.write("Hola, mundo con FileWriter!\n");
			escritor.write("Segunda línea.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * FORMA 2 · BufferedWriter
	 * ============================================================
	 *
	 * En vez de escribir directamente en disco escribe en un buffer de
	 * memoria -> más rápido (no lo notamos con pocos datos).
	 * Se construye encima de un FileWriter (ahí va el true para añadir).
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: newLine()
	 * Para qué sirve: salto de línea que entienden todos los sistemas.
	 * Qué recibe / devuelve: nada.
	 * Nota: SOLO existe en BufferedWriter.
	 * ============================================================
	 */
	public static void forma2_bufferedWriter() {
		try (BufferedWriter pluma = new BufferedWriter(new FileWriter("/home/alumno/fichero.txt", true))) {
			pluma.write("Hola mundo escrito en un fichero");
			pluma.newLine();
			pluma.write("Segunda línea\n");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * FORMA 3 · PrintWriter  (LA MÁS USADA EN LOS EJERCICIOS)
	 * ============================================================
	 *
	 * Tiene los mismos métodos que System.out.
	 *
	 * ------------------------------------------------------------
	 * MÉTODOS
	 * ------------------------------------------------------------
	 * print(texto)    -> escribe sin salto de línea.
	 * println(texto)  -> escribe y salta de línea.  println() solo = salto.
	 * printf(formato, datos...) -> escribe con formato:
	 *     %s texto, %d entero, %.2f decimal con 2 decimales,
	 *     %2d / %3d entero alineado a 2 / 3 posiciones (tablas de multiplicar).
	 *
	 * ------------------------------------------------------------
	 * CONSTRUCTORES QUE HEMOS USADO
	 * ------------------------------------------------------------
	 * new PrintWriter(ruta)                                 -> ESCRIBIR (sobrescribe)
	 * new PrintWriter(ruta, StandardCharsets.UTF_8)         -> ESCRIBIR indicando juego de caracteres
	 * new PrintWriter(new FileWriter(ruta, true))           -> AÑADIR
	 * new PrintWriter(new FileWriter(ruta, StandardCharsets.UTF_8, true)) -> AÑADIR con UTF-8
	 *
	 * StandardCharsets.UTF_8 -> para no tener problemas con tildes y ñ.
	 * ============================================================
	 */
	public static void forma3_printWriter() {
		try (PrintWriter pluma = new PrintWriter("/home/alumno/fichero.txt", StandardCharsets.UTF_8)) {
			pluma.print("Primera linea. ");
			pluma.println("Sigo en la primera línea");
			pluma.println("Segunda línea");
			String nombre = "Ana";
			int edad = 27;
			double sueldo = 1200;
			pluma.printf("NOMBRE: %s. EDAD: %d. SUELDO: %.2f", nombre, edad, sueldo);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// Modo AÑADIR (alta de usuario en login.txt, ejercicio 7)
		try (PrintWriter pluma = new PrintWriter(new FileWriter("/home/alumno/login.txt", true))) {
			pluma.printf("%s:%s", "kevin", "1234");
			pluma.println();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * FORMA 4 · Files.write  (grabar una LISTA)
	 * ============================================================
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: Files.write(ruta, lista, [charset], [opciones])
	 * Para qué sirve: grabar un ArrayList, un elemento por línea.
	 *   Abre y cierra el fichero solo.
	 * Qué recibe:
	 *   Path ruta                    -> Paths.get(...) o Path.of(...)
	 *   ArrayList<String> lista
	 *   StandardCharsets.UTF_8       -> opcional
	 *   StandardOpenOption.CREATE, StandardOpenOption.APPEND -> opcional, modo AÑADIR
	 * Qué devuelve: nada que usemos.
	 *
	 * Cómo lo hemos utilizado: cuando ya tenemos el contenido en una lista:
	 *   aprobados.csv, productos_rebajados.txt, fichero invertido,
	 *   CSV sin la columna de la moneda.
	 *
	 * Las 3 variantes (EscrituraFicheros de ACCESO_DATOS):
	 *   Files.write(ruta, lineas);                                  -> escribe
	 *   Files.write(ruta, lineas, StandardCharsets.UTF_8);          -> escribe con UTF-8
	 *   Files.write(ruta, lineas, StandardCharsets.UTF_8,
	 *               StandardOpenOption.CREATE, StandardOpenOption.APPEND); -> añade
	 * ============================================================
	 */
	public static void forma4_filesWrite() {
		Path ruta = Paths.get("/home/alumno/archivo.txt"); // no lanza excepción, puede ir fuera
		ArrayList<String> lineas = new ArrayList<String>(List.of("Primera linea", "Segunda linea", "Tercera linea"));
		try {
			Files.write(ruta, lineas, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * FORMA 5 · Files.writeString  (grabar un String)
	 * ============================================================
	 *
	 * Nombre: Files.writeString(ruta, contenido, [charset], [opciones])
	 * Para qué sirve: igual que la forma 4, pero con un único String.
	 * Qué recibe: Path, String, y opcionalmente UTF_8 y CREATE, APPEND.
	 * ============================================================
	 */
	public static void forma5_filesWriteString() {
		Path ruta = Paths.get("/home/alumno/archivo.txt");
		String contenido = "Hola mundo. ultimo metodo de escritura";
		try {
			Files.writeString(ruta, contenido, StandardCharsets.UTF_8); // escribe
			Files.writeString(ruta, contenido, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.APPEND); // añade
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * ¿CÓMO SE MODIFICA UN FICHERO DE TEXTO?
	 * ============================================================
	 *
	 * Un fichero de texto es SECUENCIAL: no se puede cambiar una línea
	 * "en su sitio". Lo que hacemos (CorreccionExamenOrdinaria/Ejercicio2_sinMoneda):
	 *
	 *   "SI QUEREMOS SOBREESCRIBIR EL FICHERO TENEMOS QUE ALMACENAR EL
	 *    CONTENIDO QUE DESEAMOS Y VOLVER A ESCRIBIR"
	 *
	 *   1. Leer todas las líneas y guardarlas en un ArrayList (ya modificadas).
	 *   2. Cerrar el lector.
	 *   3. Escribir la lista entera en el MISMO fichero (modo escribir).
	 *
	 * Se ha hecho así en: quitar la moneda del CSV (sinMoneda),
	 *   añadir la edad a empleados.txt (Ejercicio9).
	 * ============================================================
	 */
	public static void ejemploModificar(String fichero) {
		ArrayList<String> sinMonedas = new ArrayList<String>();
		try {
			sinMonedas.add("Pais,Capital,Animal"); // nueva cabecera
			BufferedReader lector = new BufferedReader(new FileReader(fichero));
			String linea;
			lector.readLine(); // saltamos cabecera
			while ((linea = lector.readLine()) != null) {
				String[] campos = linea.split(",");
				sinMonedas.add(campos[0] + "," + campos[1] + "," + campos[3]);
			}
			lector.close();
			Files.write(Path.of(fichero), sinMonedas, StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("Error al leer el fichero");
		}
	}

	/*
	 * ============================================================
	 * LEER Y ESCRIBIR A LA VEZ (dos ficheros)
	 * ============================================================
	 *
	 * En un try-with-resources se pueden abrir dos recursos separados por ;
	 * (Ejercicio8: copiar al destino solo las líneas correctas).
	 * ============================================================
	 */
	public static void ejemploLeerYEscribir(String origen, String destino) {
		try (BufferedReader lector = new BufferedReader(new FileReader(origen));
				PrintWriter pluma = new PrintWriter(destino)) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				pluma.println(linea);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * EXCEPCIONES
	 * ============================================================
	 *  Todas las formas lanzan excepción (IOException): siempre dentro de
	 *  try { } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
	 *  Path.of / Paths.get NO lanzan excepción (pueden ir fuera del try).
	 *
	 * ============================================================
	 * DIFERENCIAS RÁPIDAS
	 * ============================================================
	 *  - FileWriter: lo básico. Base de BufferedWriter y del modo añadir de PrintWriter.
	 *  - BufferedWriter: + rápido, tiene newLine().
	 *  - PrintWriter: formato (printf). La que más sale en exámenes.
	 *  - Files.write / writeString: de golpe, abren y cierran solos, añadir con APPEND.
	 * ============================================================
	 */
	public static void main(String[] args) {
		forma1_fileWriter();
		forma2_bufferedWriter();
		forma3_printWriter();
		forma4_filesWrite();
		forma5_filesWriteString();
	}
}

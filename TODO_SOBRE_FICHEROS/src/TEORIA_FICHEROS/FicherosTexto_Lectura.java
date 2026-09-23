package TEORIA_FICHEROS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * ============================================================
 * TEORÍA 2 · FICHEROS DE TEXTO — LECTURA
 * ============================================================
 *
 * Origen:
 *   DAM_JAVA     / Ficheros/ficherosLectura/FicheroLectura
 *   DAM_JAVA     / TodoSobreFicheros/ficheroNormal/LecturaFicheroNormal
 *   DAM_JAVA     / UT7_Ficheros_RA5/FicherosTexto/LecturaFicherosTexto
 *   ACCESO_DATOS / LecturaFicheros/Metodos/LecturaFicheros
 *   (+ todos los ejercicios de Boletín 23, exámenes y CSV)
 *
 * ¿QUÉ ES?
 *   Un fichero que guarda caracteres legibles por una persona (.txt, .csv).
 *   Se lee de forma SECUENCIAL: de principio a fin, línea a línea.
 *   Un salto de línea es un \n.
 *
 * ¿PARA QUÉ LO HEMOS UTILIZADO?
 *   - Mostrar un fichero por pantalla.
 *   - Contar líneas, palabras, hombres/mujeres, datos válidos...
 *   - Cargar un fichero en un HashMap (login usuario:contraseña,
 *     animes "17 Naruto", respuestas de un test).
 *   - Leer CSV (paises.csv, notas_academia.csv, visitas.txt...).
 *
 * REGLAS QUE SE REPITEN EN TODOS LOS PROYECTOS
 *   - OBLIGATORIO usar excepciones (try/catch).
 *   - OBLIGATORIO cerrar el fichero (close() o try-with-resources).
 *
 * ============================================================
 * LAS 4 FORMAS DE LEER QUE HEMOS DADO
 * ============================================================
 *
 *   FORMA                        CLASES                    RESULTADO           ¿CIERRA SOLO?
 *   ---------------------------  ------------------------  ------------------  -------------
 *   1. BufferedReader            FileReader+BufferedReader línea a línea        No (close / try-with-resources)
 *   2. Scanner                   File + Scanner            línea a línea        No (close)
 *   3. Files.readAllLines        Path + Files              ArrayList<String>    Sí
 *   4. Files.readString          Path + Files              un String entero     Sí
 *
 *   - En 1, 2 y 3 los \n desaparecen (cada línea sale sin él).
 *   - En 4 los \n se conservan -> mostrar con print, NO println.
 *   - "No hay diferencia entre el método 1 y 2, puedo elegir el que quiera" (LecturaFicheros).
 *   - La 1 es la más usada en los ejercicios y exámenes.
 */
public class FicherosTexto_Lectura {

	/*
	 * ============================================================
	 * FORMA 1 · FileReader + BufferedReader
	 * ============================================================
	 *
	 * FileReader     -> representa el fichero.
	 * BufferedReader -> el "cursor" que va leyendo línea a línea.
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: readLine()
	 * Para qué sirve: lee la siguiente línea completa y avanza el cursor.
	 * Qué recibe: nada.
	 * Qué devuelve: String con la línea (sin el \n)
	 *               o null cuando ya no quedan líneas (fin del fichero).
	 * Cómo lo hemos utilizado: dentro de un while hasta que devuelve null.
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: close()
	 * Para qué sirve: cerrar el fichero y liberar recursos.
	 * Qué recibe / devuelve: nada.
	 * Cómo lo hemos utilizado: lector.close() al final del try,
	 *   o no ponerlo usando try-with-resources.
	 * ============================================================
	 */

	// Apertura "a mano" + close() (FicheroLectura.metodo1, E3 de Boletín 1)
	public static void forma1_close(String fichero) {
		try {
			BufferedReader lector = new BufferedReader(new FileReader(fichero));
			String linea;
			while ((linea = lector.readLine()) != null) { // null = fin del fichero
				System.out.println(linea);
			}
			lector.close(); // cerramos y liberamos los recursos
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
	}

	/*
	 * TRY-WITH-RESOURCES
	 *   El objeto se crea dentro de los paréntesis del try.
	 *   Se cierra SOLO al salir del try: no hace falta close().
	 *   Es como lo hacemos en casi todos los ejercicios.
	 */
	public static void forma1_tryWithResources(String fichero) {
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * RECORRIDOS DE readLine() QUE HEMOS VISTO
	 *
	 *  RECORRIDO 1 · do-while (TodoSobreFicheros metodo0: "el más correcto lógicamente")
	 *      do {
	 *          linea = lector.readLine();
	 *          if (linea != null) {
	 *              System.out.println(linea);
	 *          }
	 *      } while (linea != null);
	 *
	 *  RECORRIDO 2 · while compacto (EL QUE USAMOS SIEMPRE)
	 *      while ((linea = lector.readLine()) != null) {
	 *          System.out.println(linea);
	 *      }
	 *
	 *  RECORRIDO 3 · lectura previa fuera del bucle ("de todos los recorridos este es el peor")
	 *      linea = lector.readLine();
	 *      while (linea != null) {
	 *          System.out.println(linea);
	 *          linea = lector.readLine();
	 *      }
	 */
	public static void recorridoDoWhile(String fichero) {
		try {
			FileReader f = new FileReader(fichero); // ES EL PROPIO FICHERO
			BufferedReader lector = new BufferedReader(f); // LO TENEMOS QUE CERRAR
			String linea;
			do {
				linea = lector.readLine();
				if (linea != null)
					System.out.println(linea);
			} while (linea != null);
			lector.close();
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * FORMA 2 · File + Scanner
	 * ============================================================
	 *
	 * El mismo Scanner del teclado, pero en vez de System.in le pasamos un File.
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: hasNextLine()
	 * Para qué sirve: saber si queda alguna línea por leer.
	 * Qué recibe: nada.
	 * Qué devuelve: boolean.
	 *
	 * Nombre: nextLine()
	 * Para qué sirve: leer la siguiente línea completa (elimina el \n).
	 * Qué devuelve: String.
	 *
	 * Cómo lo hemos utilizado: while (lector.hasNextLine()) y cerrar con close().
	 *   En el examen de Sara (examen/Ejercicio1) se leen animes.txt y
	 *   personajes.txt así.
	 * ============================================================
	 */
	public static void forma2_scanner(String ruta) {
		try {
			File fichero = new File(ruta);
			Scanner lector = new Scanner(fichero);
			String linea;
			while (lector.hasNextLine()) {
				linea = lector.nextLine();
				System.out.println(linea);
			}
			lector.close(); // cerramos siempre el Scanner
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * FORMA 3 · Path + Files.readAllLines
	 * ============================================================
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: Path.of(ruta)
	 * Para qué sirve: crear el objeto Path que simboliza el fichero.
	 * Qué recibe: String con la ruta.
	 * Qué devuelve: Path.
	 * Nota: no lanza excepción, puede ir fuera del try.
	 *       (Paths.get(ruta) hace lo mismo; lo usamos en escritura.)
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: Files.readAllLines(path)
	 * Para qué sirve: leer TODO el fichero de golpe (abre y cierra solo).
	 * Qué recibe: Path.
	 * Qué devuelve: List<String>. Cada línea = una celda.
	 *               Lo casteamos a (ArrayList<String>).
	 * Cómo lo hemos utilizado: devuelveContenido() en Boletín 23,
	 *   estadísticas (E5), leer un CSV (LecturaficherosEscrituraBases),
	 *   dar la vuelta a un fichero (ej4Profe).
	 *
	 * OJO: si falla, la lista se queda a null -> comprobar  if (lineas != null)
	 *      antes de recorrerla.
	 * ============================================================
	 */
	public static ArrayList<String> forma3_readAllLines(String fichero) {
		ArrayList<String> lineas = null;
		Path f = Path.of(fichero); // objeto que simboliza el fichero
		try {
			lineas = (ArrayList<String>) Files.readAllLines(f); // leo TODAS las líneas
		} catch (Exception e) {
			System.out.printf("Error con el fichero %s\n", fichero);
			System.out.println(e.getMessage());
		}
		return lineas;
	}

	/*
	 * ============================================================
	 * FORMA 4 · Path + Files.readString
	 * ============================================================
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: Files.readString(path)
	 * Para qué sirve: leer TODO el fichero en un único String.
	 * Qué recibe: Path.
	 * Qué devuelve: String con el contenido, CON sus \n.
	 * Cómo lo hemos utilizado:
	 *   - comparar dos ficheros con equals (Boletín 23 E4),
	 *   - leer la línea de soluciones de un test (ejercicio_6).
	 *
	 * OJO: mostrar con System.out.print, no println (el fichero ya trae sus \n).
	 * ============================================================
	 */
	public static void forma4_readString(String ruta) {
		Path fichero = Path.of(ruta);
		String contenido = null;
		try {
			contenido = Files.readString(fichero);
		} catch (Exception e) {
			System.out.println("Error con el fichero");
			System.out.println(e.getMessage());
		}
		System.out.print(contenido);
	}

	/*
	 * ============================================================
	 * PROCESAR CADA LÍNEA (CSV Y FORMATOS PROPIOS)
	 * ============================================================
	 *
	 * Un CSV es un fichero de TEXTO: se lee igual y cada línea se separa.
	 *
	 * Formatos que han salido en los ejercicios:
	 *
	 *   FORMATO                                 CÓMO SE SEPARA
	 *   --------------------------------------  -----------------------------------------
	 *   Australia,Canberra,Dólar,Canguro        linea.split(",")
	 *   Ana Garcia;Ingles;7.50                  linea.split(";")
	 *   17 Naruto                               indexOf(" ") + substring
	 *   usuario:contraseña                      indexOf(":") + substring
	 *   Claudia: A, B, C                        indexOf(":") + substring(posicion + 2) + split(", ")
	 *   A, C, C, D                              split(", ")  o  split(",\\s*")
	 *   palabras con varios espacios            split("\\s+")
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: split(separador)
	 * Para qué sirve: partir la línea en campos.
	 * Qué recibe: String con el separador (expresión regular).
	 *   "\\s+"   = uno o más espacios
	 *   ",\\s*"  = coma seguida de 0 o más espacios
	 * Qué devuelve: String[] con los campos.
	 *
	 * Nombre: indexOf(texto)
	 * Qué devuelve: int con la posición de la primera aparición, -1 si no está.
	 *
	 * Nombre: substring(inicio) / substring(inicio, fin)
	 * Qué devuelve: el trozo de String (fin no incluido).
	 * ============================================================
	 */

	// Ejemplo mínimo: "17 Naruto" -> clave 17, valor "Naruto" (E1 Boletín 1)
	public static void ejemploIndexOfSubstring(String linea) {
		int posicion = linea.indexOf(" ");
		int num = Integer.parseInt(linea.substring(0, posicion));
		String titulo = linea.substring(posicion + 1);
		System.out.println(num + " -> " + titulo);
	}

	/*
	 * TRUCOS DE CSV QUE HEMOS USADO
	 *
	 *  - Saltar la cabecera: un readLine() antes del while (Ejercicio1_Paises).
	 *  - Ignorar líneas mal formadas: if (campos.length == 4) { ... }
	 *  - Línea con un número mal escrito: try/catch dentro del while
	 *    para que una línea mala no pare la lectura (E1_NotasAcademicas).
	 */
	public static void ejemploCSV(String fichero) {
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			lector.readLine(); // saltar cabecera
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] campos = linea.split(",");
				if (campos.length == 4) { // ignorar líneas erróneas
					System.out.println(campos[0] + " - " + campos[1]);
				}
			}
		} catch (Exception e) {
			System.out.println("Error al leer: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * EXCEPCIONES QUE APARECEN EN LECTURA
	 * ============================================================
	 *
	 *  Exception              -> la que capturamos casi siempre: catch (Exception e)
	 *  IOException            -> catch (IOException e) en E3 de Boletín 1
	 *  FileNotFoundException  -> la importamos: salta si el fichero no existe
	 *  NumberFormatException  -> Integer.parseInt / Double.parseDouble con un texto
	 *                            que no es número (E10: contar datos inválidos)
	 *
	 *  Qué hacemos en el catch:
	 *   - System.out.println("Error: " + e.getMessage());   (lo normal)
	 *   - System.err.println(...)                            (sale en rojo)
	 *   - e.printStackTrace();                               (traza completa)
	 *   - return null;  -> el que llama sabe que ha fallado (E11 login)
	 *
	 *  Otra forma (métodos de E1 Boletín 1 y examen):
	 *   el método pone  throws Exception  y el try/catch se hace en el main.
	 * ============================================================
	 */
	public static void ejemploNumberFormat(String linea) {
		try {
			double valor = Double.parseDouble(linea.trim());
			System.out.println("Dato válido: " + valor);
		} catch (NumberFormatException e) {
			System.out.println("Dato inválido encontrado: " + linea);
		}
	}

	public static void main(String[] args) {
		String fichero = "/home/alumno/hola.txt";
		forma1_tryWithResources(fichero);
		forma2_scanner(fichero);
		ArrayList<String> lineas = forma3_readAllLines(fichero);
		if (lineas != null) {
			System.out.println("Líneas: " + lineas.size());
		}
		forma4_readString(fichero);
	}
}

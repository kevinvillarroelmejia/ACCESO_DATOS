package TEORIA_FICHEROS;

import java.io.RandomAccessFile;
import java.util.HashMap;

/*
 * ============================================================
 * TEORÍA 6 · ACCESO ALEATORIO (RandomAccessFile)
 * ============================================================
 *
 * Origen:
 *   ACCESO_DATOS / FicherosBinarios/teoria/apuntesProfe   (versión del profesor)
 *   ACCESO_DATOS / FicherosBinarios/teoria/AcesosAFicheros
 *   DAM_JAVA     / UT7_Ficheros_RA5/FicherosBinarios_AccesoAletorio/Apuntes_AccesoAleatorio
 *   DAM_JAVA     / Ficheros_accesoAletorio, TodoSobreFicheros/accesoAletorioFicheros
 *   DAM_JAVA     / UT7_Ficheros_RA5/AccesoAletorio_IA, PracticandoExtraordinaria/RA8_AccesoAletorio
 *
 * ============================================================
 * SECUENCIAL vs ALEATORIO
 * ============================================================
 *   SECUENCIAL (todo lo visto antes): se lee/escribe de principio a fin.
 *     Para llegar a un dato hay que pasar por los anteriores.
 *   ALEATORIO: "ACCEDER DONDE QUEREMOS" (Teoriaficheros). Vamos
 *     directamente a un dato concreto sin pasar por los demás.
 *     "Es similar al acceso de base de datos."
 *
 *   HANDICAP: todo lo que se escribe tiene que tener TAMAÑO FIJO.
 *     Así la posición de cada registro se puede calcular.
 *
 * ============================================================
 * TAMAÑOS FIJOS QUE HEMOS USADO
 * ============================================================
 *   int     -> 4 bytes
 *   double  -> 8 bytes
 *   boolean -> 1 byte
 *   char    -> 2 bytes
 *   String  -> NO tiene tamaño fijo: lo convertimos en N caracteres
 *              rellenando con espacios (escribirNombre).
 *
 *   Registro de la agenda = nombre (20 chars) + edad (int):
 *     TAMANYO_REGISTRO = TAMANYO_NOMBRE * 2 + TAMANYO_EDAD = 20*2 + 4 = 44 bytes
 *   Registro solo con nombre (agenda de 5 huecos, parking):
 *     TAMANYO_REGISTRO = TAMANYO_NOMBRE * 2
 *
 *   Los tamaños van en CONSTANTES globales (static final): así se
 *   cambian en un único punto.
 *
 * ============================================================
 * CÓMO SE ABRE
 * ============================================================
 *   new RandomAccessFile(fichero, "r")   -> solo lectura.
 *                                           Excepción si el fichero no existe.
 *   new RandomAccessFile(fichero, "rw")  -> lectura y escritura.
 *                                           Crea el fichero si no existe.
 *                                           Si existe NO lo borra (escribe encima).
 *   No existe modo solo escritura ("w").
 *   (El profesor nombra también "rws" y "rwd": no los hemos usado.)
 *
 *   Siempre en try-with-resources. Los métodos llevan  throws Exception
 *   y el try/catch está en el main.
 */
public class AccesoAleatorio {

	static final int TAMANYO_NOMBRE = 20; // caracteres. Un carácter ocupa 2 bytes
	static final int TAMANYO_EDAD = 4;    // bytes. Un entero ocupa 4 bytes
	static final int TAMANYO_REGISTRO = TAMANYO_NOMBRE * 2 + TAMANYO_EDAD; // 44

	/*
	 * ============================================================
	 * MÉTODOS DE RandomAccessFile QUE HEMOS USADO
	 * ============================================================
	 *
	 *  Nombre         Qué recibe   Qué devuelve  Para qué sirve
	 *  -------------  -----------  ------------  -------------------------------------------
	 *  length()       nada         long          tamaño del fichero en bytes
	 *  seek(pos)      long         nada          situar el cursor en el byte pos (desde 0)
	 *  writeChar(c)   char         nada          escribe un carácter (2 bytes)
	 *  writeInt(n)    int          nada          escribe un entero (4 bytes)
	 *  readChar()     nada         char          lee un carácter
	 *  readInt()      nada         int           lee un entero
	 *
	 *  Cada lectura/escritura AVANZA el cursor. Al abrir, el cursor está en 0.
	 *
	 *  OJO (fallo de Ficheros_accesoAletorio): raf.write(c) graba 1 solo byte,
	 *  no 2 -> el registro no mide lo que dice la constante. Usar writeChar(c).
	 * ============================================================
	 */

	/*
	 * ============================================================
	 * POSICIÓN DE UN REGISTRO
	 * ============================================================
	 *   El registro 1 empieza en el byte 0:
	 *     long posicion = (registro - 1) * TAMANYO_REGISTRO;
	 *
	 *   ¿Existe el registro?   if (posicion >= raf.length())  -> NO existe
	 *   ¿Cuántos registros hay? raf.length() / TAMANYO_REGISTRO
	 *   Añadir al final:       raf.seek(raf.length())
	 * ============================================================
	 */

	// ESCRIBIR NOMBRE con tamaño fijo: letras del nombre + espacios hasta TAMANYO_NOMBRE
	private static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
		char[] chars = new char[TAMANYO_NOMBRE];
		for (int i = 0; i < TAMANYO_NOMBRE; i++) {
			if (i < nombre.length()) {
				chars[i] = nombre.charAt(i);
			} else {
				chars[i] = ' '; // rellenar con espacios
			}
		}
		for (char c : chars) {
			raf.writeChar(c); // 2 bytes cada uno
		}
	}

	// LEER NOMBRE con tamaño fijo: leer TAMANYO_NOMBRE chars y quitar espacios con trim()
	private static String leerNombre(RandomAccessFile raf) throws Exception {
		String nombre = "";
		for (int i = 0; i < TAMANYO_NOMBRE; i++) {
			char c = raf.readChar();
			nombre = nombre + c;
		}
		return nombre.trim();
	}

	// CREAR: recorre el HashMap y escribe nombre + edad de cada uno
	public static void crearRegistro(String fichero, HashMap<String, Integer> agenda) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			for (String nombre : agenda.keySet()) {
				escribirNombre(raf, nombre);
				raf.writeInt(agenda.get(nombre)); // los enteros se graban con 4 bytes
			}
			System.out.println("Archivo creado con " + agenda.size() + " registros");
			System.out.println("Tamaño total del archivo: " + raf.length() + " bytes");
		}
	}

	// LEER UN REGISTRO: seek a su posición y leer
	public static void leerRegistro(String fichero, int registro) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
			long offset = (registro - 1) * TAMANYO_REGISTRO;
			if (offset >= raf.length()) {
				System.out.println("No existe el registro " + registro);
				System.out.println("El registro mas alto es el " + raf.length() / TAMANYO_REGISTRO);
			} else {
				raf.seek(offset);
				String nombre = leerNombre(raf);
				if (nombre.charAt(0) == '*')
					System.out.println("El registro " + registro + " está marcado para ser eliminado");
				else {
					int edad = raf.readInt();
					System.out.printf("Registro %d: '%s', %d años%n", registro, nombre, edad);
				}
			}
		}
	}

	// LEER TODOS: calcular cuántos hay y leerlos seguidos (sin seek: el cursor avanza solo)
	public static void leerTodosLosRegistros(String fichero) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
			int numRegistros = (int) raf.length() / TAMANYO_REGISTRO;
			for (int i = 0; i < numRegistros; i++) {
				String nombre = leerNombre(raf);
				int edad = raf.readInt();
				if (nombre.charAt(0) != '*')
					System.out.printf("Registro %d: '%s', %d años%n", i + 1, nombre, edad);
			}
		}
	}

	// MODIFICAR: seek, comprobar que no está borrado, volver a seek y escribir encima
	public static void modificarRegistro(String fichero, int registro, String nombreNuevo, int edadNueva)
			throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			long offset = (registro - 1) * TAMANYO_REGISTRO;
			if (offset >= raf.length()) {
				System.out.println("No existe el registro " + registro);
			} else {
				raf.seek(offset);
				String nombre = leerNombre(raf); // al leer el cursor avanza...
				if (nombre.charAt(0) != '*') {
					raf.seek(offset);          // ...por eso volvemos a la posición
					escribirNombre(raf, nombreNuevo);
					raf.writeInt(edadNueva);
					System.out.println("Registro " + registro + " modificado");
				} else
					System.out.println("El registro " + registro + " no puede ser modificado porque está marcado para ser eliminado");
			}
		}
	}

	// AÑADIR: seek al final
	public static void anyadeRegistro(String fichero, String nombre, int edad) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			raf.seek(raf.length());
			escribirNombre(raf, nombre);
			raf.writeInt(edad);
			System.out.println("Registro añadido");
		}
	}

	/*
	 * BORRAR = BORRADO LÓGICO
	 *   No se quita el registro del fichero: se cambia el primer carácter
	 *   del nombre por '*'. Al leer, si nombre.charAt(0) == '*' se salta.
	 *   Un registro borrado no se puede modificar ni volver a borrar.
	 *
	 *   Variante (agenda de 5 huecos, parking): se crea el fichero con todas
	 *   las posiciones a "*" = hueco libre, y se escribe solo si el hueco
	 *   empieza por '*'.
	 */
	public static void borrarRegistro(String fichero, int registro) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			long offset = (registro - 1) * TAMANYO_REGISTRO;
			if (offset >= raf.length()) {
				System.out.println("No existe el registro " + registro);
			} else {
				raf.seek(offset);
				String nombre = leerNombre(raf);
				if (nombre.charAt(0) == '*')
					System.out.println("El registro " + registro + " ya había sido borrado");
				else {
					nombre = '*' + nombre.substring(1);
					raf.seek(offset);
					escribirNombre(raf, nombre);
					System.out.println("Registro " + registro + " eliminado con éxito");
				}
			}
		}
	}

	/*
	 * ============================================================
	 * EXCEPCIONES
	 * ============================================================
	 *  "otra forma de tratar las excepciones: se capturan todas desde el
	 *   programa principal; en los métodos añadimos throws Exception.
	 *   Cuando se produce la excepción el método deja de ejecutarse y
	 *   vuelve al programa principal y se ejecuta allí el catch" (apuntesProfe)
	 *
	 *  writeChar/readChar... lanzan IOException -> si el método no pone
	 *  throws Exception, no compila.
	 *  En Agenda_AccesoAletorio: throws FileNotFoundException, Exception.
	 * ============================================================
	 */
	public static void main(String[] args) {
		String fichero = "registros.dat";
		HashMap<String, Integer> agenda = new HashMap<>();
		agenda.put("Isabel", 35);
		agenda.put("Marcos", 51);
		agenda.put("José María", 57);
		agenda.put("Luis", 23);
		try {
			crearRegistro(fichero, agenda);
			leerRegistro(fichero, 2);
			modificarRegistro(fichero, 2, "José Miguel", 56);
			leerTodosLosRegistros(fichero);
			borrarRegistro(fichero, 3);
			leerRegistro(fichero, 3);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}

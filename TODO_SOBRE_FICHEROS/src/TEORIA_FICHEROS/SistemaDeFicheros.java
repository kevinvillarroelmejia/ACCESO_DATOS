package TEORIA_FICHEROS;

import java.io.File;
import java.io.FileWriter;

/*
 * ============================================================
 * TEORÍA 1 · SISTEMA DE FICHEROS CON LA CLASE File
 * ============================================================
 *
 * Origen:
 *   ACCESO_DATOS / Ficheros_sistemaFisico  (Ficheros1Sistema, FicherosSara, ApuntesFicheros_IA)
 *   DAM_JAVA     / Boletin23_Ficheros E1, E2  (isFile)
 *   DAM_JAVA     / UT7_Ficheros_RA5  (exists en Agenda_AccesoAletorio y TablasMultiplicar)
 *
 * ¿QUÉ ES?
 *   File es un objeto que REPRESENTA una ruta (fichero o directorio).
 *   new File(ruta) NO crea nada en disco: solo es una referencia.
 *
 * ¿PARA QUÉ LO HEMOS UTILIZADO?
 *   - Saber la carpeta desde la que se ejecuta el programa.
 *   - Comprobar si un fichero o directorio existe antes de usarlo.
 *   - Crear la carpeta de configuración de la aplicación (DAM2/kevin).
 *   - Crear un fichero vacío (config.txt).
 *   - Ver el espacio libre en disco.
 *
 * ============================================================
 * RUTAS
 * ============================================================
 *
 *   RUTA RELATIVA: se calcula desde el directorio de trabajo
 *                  (en Eclipse, la raíz del proyecto).
 *                  Ej: "datos" + File.separator + "animes.txt"
 *
 *   RUTA ABSOLUTA: la ruta completa.
 *                  Ej: "/home/alumno/hola.txt"   (Linux, clase)
 *                      "D:\\IES FRANCISCO DE GOYA\\DAM\\PROGRAMACION\\paises.csv"   (Windows, casa)
 *                  OJO: en Java la \ se escribe doble: \\
 *
 *   File.separator -> "\" en Windows y "/" en Linux.
 *                     Con él la ruta funciona en los dos sistemas.
 *
 *   Directorios especiales:
 *     "."  = directorio actual
 *     ".." = directorio padre
 *
 *   Las rutas las guardamos en CONSTANTES (private static final)
 *   porque no cambian nunca.
 */
public class SistemaDeFicheros {

	// Constantes de ruta (así lo hacemos en Ficheros1Sistema / FicherosSara)
	private static final String DIR_CONFIG = "DAM2" + File.separator + "kevin";
	private static final String ARCHIVO_CONFIG = DIR_CONFIG + File.separator + "config.txt";

	/*
	 * ============================================================
	 * MÉTODO
	 * ============================================================
	 *
	 * Nombre: getAbsolutePath()
	 *
	 * Para qué sirve: devuelve la ruta completa del File.
	 *
	 * Qué recibe: nada.
	 *
	 * Qué devuelve: String con la ruta absoluta.
	 *
	 * Cómo lo hemos utilizado: con new File(".") para ver en qué carpeta
	 *   se está ejecutando el programa (y así saber dónde buscará las
	 *   rutas relativas). También System.getProperty("user.dir").
	 *
	 * ============================================================
	 */
	public static void ejemploDirectorioActual() {
		File directorioActual = new File(".");
		System.out.println(directorioActual.getAbsolutePath());
		System.out.println(System.getProperty("user.dir")); // base de las rutas relativas
	}

	/*
	 * ============================================================
	 * MÉTODO
	 * ============================================================
	 *
	 * Nombre: exists()
	 *
	 * Para qué sirve: saber si la ruta existe (fichero O directorio).
	 *
	 * Qué recibe: nada.
	 *
	 * Qué devuelve: boolean. true si existe.
	 *
	 * Cómo lo hemos utilizado:
	 *   - Para comprobar si la carpeta de configuración ya existía
	 *     (primera ejecución de la app o no).
	 *   - Para crear el fichero de acceso aleatorio solo si no existe
	 *     (Agenda_AccesoAletorio).
	 *   - Antes de leer la tabla de multiplicar (Ejercicio1_2).
	 *
	 * ============================================================
	 */
	public static void ejemploExists() {
		File dirConfig = new File(DIR_CONFIG);
		if (dirConfig.exists()) {
			System.out.println("El directorio " + DIR_CONFIG + " existe");
		} else {
			System.out.println("El directorio " + DIR_CONFIG + " NO existe");
		}
	}

	/*
	 * ============================================================
	 * MÉTODO
	 * ============================================================
	 *
	 * Nombre: isFile()
	 *
	 * Para qué sirve: saber si existe Y además es un fichero
	 *   (no un directorio).
	 *
	 * Qué recibe: nada.
	 *
	 * Qué devuelve: boolean.
	 *
	 * Cómo lo hemos utilizado: en el método existeElFichero() de
	 *   Boletín 23 para pedir el nombre del fichero por teclado
	 *   hasta que sea válido.
	 *   (En E1/E2 aparecen comentadas como alternativas f.exists() y f.isDirectory().)
	 *
	 * ============================================================
	 */
	public static boolean existeElFichero(String fichero) {
		File f = new File(fichero);
		return (f.isFile());
	}

	/*
	 * ============================================================
	 * MÉTODO
	 * ============================================================
	 *
	 * Nombre: mkdir() / mkdirs()
	 *
	 * Para qué sirve: crear directorios.
	 *   mkdir()  -> crea UN solo nivel (falla si el padre no existe).
	 *   mkdirs() -> crea TODA la estructura (DAM2/kevin). Usar este.
	 *
	 * Qué recibe: nada.
	 *
	 * Qué devuelve: boolean. true si lo ha creado, false si no.
	 *
	 * Cómo lo hemos utilizado: si la carpeta de configuración no existe,
	 *   la creamos. Si mkdirs() devuelve false NO seguimos creando el fichero.
	 *
	 * OJO (error de Ficheros1Sistema): poner  if (dirConfig.mkdir())
	 *   es la lógica AL REVÉS. Lo correcto es  if (!dirConfig.mkdirs())
	 *   o  if (dirConfig.mkdirs() == false)  (FicherosSara).
	 *
	 * ============================================================
	 */
	public static boolean ejemploMkdirs() {
		File dirConfig = new File(DIR_CONFIG);
		boolean crearFichero = true;
		if (dirConfig.exists() == false) {
			if (dirConfig.mkdirs() == false) { // false = ERROR
				crearFichero = false;
				System.out.println("No he podido crear el directorio");
			}
		}
		return crearFichero;
	}

	/*
	 * ============================================================
	 * CREAR UN FICHERO: LAS 2 FORMAS QUE HEMOS VISTO
	 * ============================================================
	 *
	 * FORMA 1 (la mejor): new FileWriter(ruta, true)
	 *   - Si el fichero no existe lo crea. Si existe NO lo borra (true = añadir).
	 *   - Si falla lanza excepción, así que si pasamos de esa línea el
	 *     fichero existe seguro.
	 *   - Hay que cerrarlo: close().
	 *
	 * FORMA 2: createNewFile()
	 *   Qué devuelve: boolean
	 *     true  -> lo ha creado
	 *     false -> ya existía O no ha podido crearlo (no sabemos cuál)
	 *   Por eso después hay que comprobar con exists().
	 *
	 * ============================================================
	 */
	public static void ejemploCrearFichero() throws Exception {
		// FORMA 1
		FileWriter escritor = new FileWriter(ARCHIVO_CONFIG, true);
		System.out.println("Archivo creado (o ya existía)");
		escritor.close(); // SIEMPRE cerrar

		// FORMA 2
		File archivo = new File(ARCHIVO_CONFIG);
		if (archivo.createNewFile()) {
			System.out.println("Archivo creado");
		} else {
			System.out.println("No puedo crearlo o ya existe");
		}
	}

	/*
	 * ============================================================
	 * MÉTODO
	 * ============================================================
	 *
	 * Nombre: getFreeSpace()
	 *
	 * Para qué sirve: espacio libre en el disco donde está la ruta.
	 *   (Muchas veces no se puede crear un fichero por falta de espacio.)
	 *
	 * Qué recibe: nada.
	 *
	 * Qué devuelve: long, en BYTES. Si la ruta no existe devuelve 0.
	 *
	 * Cómo lo hemos utilizado: pasarlo a GB dividiendo 3 veces entre 1024.
	 *   OJO: con enteros, si queda menos de 1 GB sale 0.
	 *        Para tener decimales dividir entre 1024.0.
	 *
	 * ============================================================
	 */
	public static void ejemploEspacioLibre() {
		File dirConfig = new File(DIR_CONFIG);
		long espacio = dirConfig.getFreeSpace() / 1024 / 1024 / 1024;
		System.out.println("Espacio libre en disco (GB): " + espacio);
		double espacioExacto = dirConfig.getFreeSpace() / 1024.0 / 1024.0 / 1024.0;
		System.out.println("Con decimales: " + espacioExacto);
	}

	/*
	 * ============================================================
	 * EXCEPCIONES
	 * ============================================================
	 *
	 * Todo el código va dentro de try { } catch (Exception e) { }
	 * porque FileWriter y createNewFile() lanzan excepción.
	 *
	 * Mostrar SIEMPRE el mensaje: e.getMessage()
	 * (si no, no sabes qué ha fallado).
	 *
	 * ============================================================
	 * ERRORES TÍPICOS (de ApuntesFicheros_IA)
	 * ============================================================
	 *
	 * 1. mkdir en vez de mkdirs cuando hay 2 niveles (DAM2/kevin).
	 * 2. if (mkdir()) en vez de if (!mkdirs()) -> lógica al revés.
	 * 3. Dividir bytes entre enteros -> 0 GB si queda menos de 1 GB.
	 * 4. Ruta relativa que no encuentra el fichero -> revisar user.dir.
	 * 5. Doble extensión oculta (paises.csv.csv) o espacio delante.
	 * 6. Olvidar close() (o no usar try-with-resources).
	 * 7. catch de Exception sin mostrar e.getMessage().
	 * ============================================================
	 */
	public static void main(String[] args) {
		try {
			ejemploDirectorioActual();
			ejemploExists();
			if (ejemploMkdirs()) {
				ejemploCrearFichero();
			}
			ejemploEspacioLibre();
		} catch (Exception e) {
			System.out.println("Error al manipular los ficheros: " + e.getMessage());
		}
	}
}

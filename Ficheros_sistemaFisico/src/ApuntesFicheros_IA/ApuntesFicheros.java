package ApuntesFicheros_IA;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ApuntesFicheros {

	// ===================== 1. RUTAS =====================
	// Constantes: el nombre no cambia nunca -> private static final
	// File.separator = \ en Windows y / en Linux (así funciona en los dos)
	// Ruta RELATIVA: se calcula desde el directorio de trabajo (raíz del proyecto en Eclipse)
	private static final String DIR_CONFIG = "DAM2" + File.separator + "kevin";
	private static final String ARCHIVO_CONFIG = DIR_CONFIG + File.separator + "config.txt";

	public static void main(String[] args) {
		try {
			// ===================== 2. DIRECTORIO ACTUAL =====================
			// "."  = directorio actual
			// ".." = directorio padre
			File directorioActual = new File(".");
			System.out.println(directorioActual.getAbsolutePath()); // ruta completa
			System.out.println(System.getProperty("user.dir")); // base de las rutas relativas

			// ===================== 3. COMPROBAR / CREAR DIRECTORIO =====================
			// new File(...) NO crea nada en disco, solo es una referencia a la ruta
			File dirConfig = new File(DIR_CONFIG);
			boolean crearFichero = true;

			if (dirConfig.exists()) { // exists() -> true si existe (fichero o directorio)
				System.out.println("El directorio " + DIR_CONFIG + " existe");
			} else {
				System.out.println("El directorio " + DIR_CONFIG + " NO existe");

				// mkdir()  -> crea UN solo nivel (falla si el padre no existe)
				// mkdirs() -> crea TODA la estructura (DAM2/kevin). Usar SIEMPRE este.
				// Los dos devuelven boolean: true si lo ha creado, false si no
				if (!dirConfig.mkdirs()) { // OJO: false = ERROR (cuidado con no olvidar el !)
					crearFichero = false;
					System.out.println("No he podido crear el directorio");
				}
			}

			// ===================== 4. CREAR FICHERO -- EL MEJOR =====================
			if (crearFichero) {
				// MÉTODO 1 (el mejor): FileWriter con true (append)
				// - Si el fichero no existe, lo crea. Si existe, NO lo borra.
				// - Si falla, lanza excepción, así que salimos con el fichero garantizado.
				FileWriter escritor = new FileWriter(ARCHIVO_CONFIG, true);
				System.out.println("Archivo creado (o ya existía)");
				escritor.close(); // SIEMPRE cerrar

				// MÉTODO 2: createNewFile()
				// - true  -> lo ha creado
				// - false -> ya existía O no ha podido crearlo (no sabemos cuál)
				// - Por eso hay que comprobar después con exists()
				File archivo = new File(ARCHIVO_CONFIG);
				if (archivo.createNewFile()) {
					System.out.println("Archivo creado");
				} else {
					System.out.println("No puedo crearlo o ya existe");
				}
			}

			// ===================== 5. ESPACIO LIBRE EN DISCO =====================
			// getFreeSpace() devuelve BYTES
			// Bytes -> GB: dividir entre 1024 tres veces (KB, MB, GB)
			// Si queda menos de 1 GB con long sale 0 -> usar 1024.0 para tener decimales
			// Si la ruta no existe devuelve 0
			long espacio = dirConfig.getFreeSpace() / 1024 / 1024 / 1024;
			System.out.println("Espacio libre en disco (GB): " + espacio);
			double espacioExacto = dirConfig.getFreeSpace() / 1024.0 / 1024.0 / 1024.0;
			System.out.println("Con decimales: " + espacioExacto);

			// ===================== 6. LEER UN FICHERO =====================
			// try-with-resources: cierra el BufferedReader solo, sin llamar a close()
			try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_CONFIG))) {
				String linea;
				while ((linea = lector.readLine()) != null) { // null = fin del fichero
					System.out.println(linea);
				}
			}

		} catch (Exception e) {
			// Mostrar SIEMPRE el mensaje, si no, no sabes qué ha fallado
			System.out.println("Error al manipular los ficheros: " + e.getMessage());
		}
	}
}

/*
 * ===================== CHULETA RÁPIDA =====================
 *
 * File f = new File(ruta)      -> solo referencia, no crea nada
 * f.exists()                   -> ¿existe?
 * f.isFile() / f.isDirectory() -> ¿es fichero? / ¿es directorio?
 * f.mkdir()                    -> crea 1 directorio
 * f.mkdirs()                   -> crea varios (mejor este)
 * f.createNewFile()            -> crea fichero vacío (true/false)
 * f.getAbsolutePath()          -> ruta completa
 * f.getFreeSpace()             -> bytes libres
 * f.list()                     -> nombres de lo que hay dentro de un directorio
 * f.delete()                   -> borra (true/false)
 *
 * FileWriter(ruta)             -> escribe y SOBRESCRIBE
 * FileWriter(ruta, true)       -> escribe AÑADIENDO al final (append)
 * BufferedReader + readLine()  -> lee línea a línea, devuelve null al final
 *
 * ERRORES TÍPICOS
 * 1. mkdir en vez de mkdirs cuando hay 2 niveles (DAM2/kevin)
 * 2. Poner if (mkdir()) en vez de if (!mkdirs()) -> lógica al revés
 * 3. Dividir bytes en GB con enteros -> sale 0 si hay menos de 1 GB
 * 4. Ruta relativa que no encuentra el fichero -> revisar user.dir
 * 5. Fichero con doble extensión oculta (paises.csv.csv) o espacio delante
 * 6. Olvidar close() (o no usar try-with-resources)
 * 7. Catch de Exception sin mostrar e.getMessage()
 */
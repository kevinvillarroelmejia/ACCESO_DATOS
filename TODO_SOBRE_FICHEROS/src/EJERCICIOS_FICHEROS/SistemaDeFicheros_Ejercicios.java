package EJERCICIOS_FICHEROS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/*
 * ============================================================
 * EJERCICIOS · SISTEMA DE FICHEROS (clase File)
 * ============================================================
 * Teoría: TEORIA/SistemaDeFicheros.java
 *
 * Cada ejercicio es una clase interna con su propio main.
 * En Eclipse: botón derecho sobre el main que quieras -> Run As.
 * ============================================================
 */
public class SistemaDeFicheros_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Carpeta de configuración de la aplicación
	// Practica: exists, mkdirs, createNewFile, getFreeSpace
	// Origen: ACCESO_DATOS/Ficheros_sistemaFisico/FicherosSistema_Sara/FicherosSara
	//         (misma idea que FicherosSistema/Ficheros1Sistema de Kevin)
	// Enunciado (de los comentarios): comprobar si es la primera vez que se
	//   ejecuta la app mirando si existe el directorio DAM2/<nombre>. Si no
	//   existe, crearlo, y crear dentro el fichero config.txt. Mostrar el
	//   espacio libre en disco.
	// ============================================================
	static class Ejercicio1_DirectorioConfig {

		private static final String DIR_CONFIG = "DAM2" + File.separator + "saragarcia";
		private static final String ARCHIVO_CONFIG = DIR_CONFIG + File.separator + "config.txt";

		public static void main(String[] args) {
			try {
				// "." = directorio actual
				File directorioActual = new File(".");
				System.out.println(directorioActual.getAbsolutePath());

				File dirConfig = new File(DIR_CONFIG);
				boolean crearFichero = true;
				if (dirConfig.exists() == true) {
					System.out.println("El directorio " + DIR_CONFIG + " existe");
				} else {
					System.out.println("El directorio " + DIR_CONFIG + " no existe");
					// mkdirs crea DAM2 y DAM2/saragarcia (mkdir solo crearía un nivel)
					if (dirConfig.mkdirs() == false) {
						crearFichero = false;
						System.out.println("No he podido crear el directorio");
					}
				}

				if (crearFichero) {
					File archivo = new File(ARCHIVO_CONFIG);
					// createNewFile: false = ya existe O no se ha podido crear
					if (archivo.createNewFile()) {
						System.out.println("Archivo creado");
					} else {
						System.out.println("No puedo crearlo o ya existe");
					}
				}

				long espacio = dirConfig.getFreeSpace() / 1024 / 1024 / 1024;
				System.out.println("Espacio libre en el disco: " + espacio);

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 2 · Configuración completa: crear con FileWriter y leer
	// Practica: user.dir, mkdirs, FileWriter(ruta, true) para crear,
	//           createNewFile, espacio con decimales, leer el fichero
	// Origen: ACCESO_DATOS/Ficheros_sistemaFisico/ApuntesFicheros_IA/ApuntesFicheros
	// ============================================================
	static class Ejercicio2_ConfigCompleta {

		private static final String DIR_CONFIG = "DAM2" + File.separator + "kevin";
		private static final String ARCHIVO_CONFIG = DIR_CONFIG + File.separator + "config.txt";

		public static void main(String[] args) {
			try {
				File directorioActual = new File(".");
				System.out.println(directorioActual.getAbsolutePath()); // ruta completa
				System.out.println(System.getProperty("user.dir"));     // base de las rutas relativas

				File dirConfig = new File(DIR_CONFIG);
				boolean crearFichero = true;
				if (dirConfig.exists()) {
					System.out.println("El directorio " + DIR_CONFIG + " existe");
				} else {
					System.out.println("El directorio " + DIR_CONFIG + " NO existe");
					if (!dirConfig.mkdirs()) { // OJO: false = ERROR
						crearFichero = false;
						System.out.println("No he podido crear el directorio");
					}
				}

				if (crearFichero) {
					// MÉTODO 1 (el mejor): si no existe lo crea, si existe no lo borra
					FileWriter escritor = new FileWriter(ARCHIVO_CONFIG, true);
					System.out.println("Archivo creado (o ya existía)");
					escritor.close();

					// MÉTODO 2: createNewFile
					File archivo = new File(ARCHIVO_CONFIG);
					if (archivo.createNewFile()) {
						System.out.println("Archivo creado");
					} else {
						System.out.println("No puedo crearlo o ya existe");
					}
				}

				long espacio = dirConfig.getFreeSpace() / 1024 / 1024 / 1024;
				System.out.println("Espacio libre en disco (GB): " + espacio);
				double espacioExacto = dirConfig.getFreeSpace() / 1024.0 / 1024.0 / 1024.0;
				System.out.println("Con decimales: " + espacioExacto);

				try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_CONFIG))) {
					String linea;
					while ((linea = lector.readLine()) != null) {
						System.out.println(linea);
					}
				}

			} catch (Exception e) {
				System.out.println("Error al manipular los ficheros: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 3 · Pedir un fichero por teclado hasta que exista
	// Practica: isFile() + bucle con bandera
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E1_ficheros (primera parte)
	//         ACCESO_DATOS/Boletin23Ficheros/ejs/ej1, DAM_JAVA/PracticandoAccesoFicheros/E1
	// Pasos (comentarios del propio ejercicio):
	//   PRIMERO PEDIMOS POR TECLADO EL NOMBRE DEL FICHERO
	//   HACEMOS UN MÉTODO QUE NOS DIGA SI EL FICHERO EXISTE O NO
	//   SI NO EXISTE, VOLVEMOS A PEDIR NOMBRE DE FICHERO
	// (El ejercicio completo, con la lectura, está en FicherosTexto_Lectura_Ejercicios, Ej. 3)
	// ============================================================
	static class Ejercicio3_PedirHastaQueExista {

		public static void main(String[] args) {
			Scanner teclado = new Scanner(System.in);
			boolean existe = false; // bandera
			String nombreFichero = null;
			while (existe == false) {
				System.out.print("Introduce el nombre del fichero: ");
				nombreFichero = teclado.nextLine();
				existe = existeElFichero(nombreFichero);
				if (existe == false) {
					System.out.printf("El fichero %s no existe\n", nombreFichero);
				}
			}
			System.out.println("Fichero válido: " + nombreFichero);
			teclado.close();
		}

		public static boolean existeElFichero(String fichero) {
			File f = new File(fichero);
			// return (f.exists());      // true si existe fichero O directorio
			return (f.isFile());         // true si existe y es un fichero
		}
	}
}

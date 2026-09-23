package EJERCICIOS_FICHEROS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;

/*
 * ============================================================
 * EJERCICIOS · ACCESO ALEATORIO (RandomAccessFile)
 * ============================================================
 * Teoría: TEORIA/AccesoAleatorio.java
 *
 *   1 Agenda nombre+edad: CRUD completo con borrado lógico
 *   2 Agenda de 5 huecos (solo nombre) creada vacía con "*"
 *   3 Aparcamiento (misma técnica que el 2 + buscar y liberar)
 *   4 La porra (enunciado; en los repos está sin terminar)
 * ============================================================
 */
public class AccesoAleatorio_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Agenda con registros de tamaño fijo (nombre 20 chars + edad int)
	// Practica: crear, leer uno, leer todos, modificar, añadir al final, borrado lógico con '*'
	// Origen: ACCESO_DATOS/FicherosBinarios/teoria/AcesosAFicheros (tu versión en clase)
	//   (misma práctica en DAM_JAVA/Ficheros_accesoAletorio y TodoSobreFicheros, pero allí
	//    escribirNombre usa raf.write(c) -> 1 byte en vez de 2 y leerRegistro tiene el if al revés)
	// Registro = TAMANYO_NOMBRE*2 + 4 = 44 bytes
	// ============================================================
	static class Ejercicio1_AgendaCRUD {
		static final int TAMANYO_NOMBRE = 20;
		static final int TAMANYO_REGISTRO = (TAMANYO_NOMBRE * 2) + 4;

		public static void main(String[] args) {
			String fichero = "/home/alumno/agenda.dat";
			HashMap<String, Integer> agenda = new HashMap<>();
			agenda.put("Alejandro", 33);
			agenda.put("Luis", 24);
			agenda.put("Ana", 32);
			agenda.put("Elvira", 41);
			// Excepciones: se capturan aquí; los métodos llevan throws Exception
			try {
				crearAgenda(fichero, agenda);
				leerRegistro(fichero, 2);                     // existe
				leerRegistro(fichero, 5);                     // no existe
				modificarRegistro(fichero, 2, "Ana Maria", 33);
				leerRegistro(fichero, 2);
				// nuevoRegistro(fichero, "Manuel", 41);      // comentado: añade uno cada vez que se ejecuta
				leerTodosRegistros(fichero);
				borrarRegistro(fichero, 3);
				leerRegistro(fichero, 3);                     // comprobación de que está borrado
				modificarRegistro(fichero, 3, "Antonia Manuela", 33); // no se puede: está borrado
				leerTodosRegistros(fichero);                  // no sale el registro borrado
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		public static void crearAgenda(String fichero, HashMap<String, Integer> agenda) throws Exception {
			// "r" solo lectura, "rw" lectura y escritura. No hay solo escritura.
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				for (String nombre : agenda.keySet()) {
					int edad = agenda.get(nombre);
					escribirNombre(raf, nombre);
					raf.writeInt(edad);
				}
				// length() devuelve el tamaño del fichero en bytes
				System.out.println("Agenda creada. Tamaño: " + raf.length() + " bytes");
			}
		}

		public static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
			char[] chars = new char[TAMANYO_NOMBRE];
			// letras del nombre y, cuando se acaban, espacios hasta llenar el array
			for (int i = 0; i < TAMANYO_NOMBRE; i++) {
				if (i < nombre.length())
					chars[i] = nombre.charAt(i);
				else
					chars[i] = ' ';
			}
			for (char c : chars)
				raf.writeChar(c);
		}

		public static void leerRegistro(String fichero, int registro) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
				long posicion = TAMANYO_REGISTRO * (registro - 1);
				if (posicion >= raf.length()) {
					System.out.println("El registro " + registro + " no existe");
					System.out.println("El registro mas alto es el " + raf.length() / TAMANYO_REGISTRO);
				} else {
					raf.seek(posicion); // situamos el cursor en la posición
					String nombre = leerNombre(raf);
					if (nombre.charAt(0) != '*') {
						int edad = raf.readInt();
						System.out.printf("Registro: %d - Nombre: %s. Edad: %d\n", registro, nombre, edad);
					} else
						System.out.println("El registro " + registro + " esta marcado para ser eliminado");
				}
			}
		}

		public static String leerNombre(RandomAccessFile raf) throws Exception {
			String nombre = "";
			for (int i = 0; i < TAMANYO_NOMBRE; i++) {
				char c = raf.readChar();
				nombre = nombre + c;
			}
			return nombre.trim(); // sin los espacios de relleno
		}

		public static void modificarRegistro(String fichero, int registro, String nombre, int edad) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				long posicion = TAMANYO_REGISTRO * (registro - 1);
				if (posicion >= raf.length()) {
					System.out.println("El registro " + registro + " no existe");
					System.out.println("El registro mas alto es el " + raf.length() / TAMANYO_REGISTRO);
				} else {
					raf.seek(posicion);
					String nombreAnterior = leerNombre(raf);
					if (nombreAnterior.charAt(0) == '*')
						System.out.println("El registro " + registro + " esta marcado para borrar no se puede modificar");
					else {
						raf.seek(posicion);
						escribirNombre(raf, nombre);
						raf.writeInt(edad);
						System.out.println("Registro " + registro + " modificado correctamente");
					}
				}
			}
		}

		public static void nuevoRegistro(String fichero, String nombre, int edad) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				raf.seek(raf.length()); // nos situamos al final del fichero
				escribirNombre(raf, nombre);
				raf.writeInt(edad);
				System.out.println("Registro añadido correctamente");
			}
		}

		public static void leerTodosRegistros(String fichero) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
				int numRegistro = (int) (raf.length() / TAMANYO_REGISTRO);
				for (int i = 1; i <= numRegistro; i++) {
					String nombre = leerNombre(raf);
					int edad = raf.readInt();
					if (nombre.charAt(0) != '*') {
						System.out.println("-----------------------------------------");
						System.out.printf("Registro: %d - Nombre: %s. Edad: %d\n", i, nombre, edad);
					}
				}
			}
		}

		public static void borrarRegistro(String fichero, int registro) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				long posicion = TAMANYO_REGISTRO * (registro - 1);
				if (posicion >= raf.length()) {
					System.out.println("El registro " + registro + " no existe");
					System.out.println("El registro mas alto es el " + raf.length() / TAMANYO_REGISTRO);
				} else {
					raf.seek(posicion);
					String nombre = leerNombre(raf);
					if (nombre.charAt(0) == '*') {
						System.out.println("El registro " + registro + " ya esta borrado");
					} else {
						String nombreBorrado = '*' + nombre.substring(1);
						raf.seek(posicion);
						escribirNombre(raf, nombreBorrado);
						System.out.println("El registro " + registro + " se ha borrado");
					}
				}
			}
		}
	}

	// ============================================================
	// EJERCICIO 2 · Agenda de 5 contactos con posiciones vacías
	// Practica: crear el fichero solo si no existe (File.exists) con 5 huecos "*",
	//           escribir en una posición solo si está libre, listar las ocupadas
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/AccesoAletorio_IA/Agenda_AccesoAletorio
	// Enunciado (del propio fichero):
	//   Crea un programa que gestione una agenda de 5 contactos en un fichero binario
	//   agenda.dat usando RandomAccessFile. Cada contacto almacena únicamente un nombre
	//   con un tamaño fijo. Las posiciones vacías se marcan con el carácter *. Debe:
	//   - Crear el fichero con las 5 posiciones vacías.
	//   - Añadir un contacto en una posición concreta (1-5). Si ya está ocupada, error.
	//   - Leer un contacto de una posición concreta. Si está vacía, indicarlo.
	//   - Listar todos los contactos que hay en la agenda.
	// Registro = solo el nombre = TAMAYO_NOMBRE * 2 bytes
	// ============================================================
	static class Ejercicio2_Agenda5Huecos {
		static final int TAMAYO_NOMBRE = 20;
		static final int TAMANYO_REGISTRO = TAMAYO_NOMBRE * 2;

		public static void main(String[] args) {
			String rutaBinario = "agenda.dat";
			try {
				File f = new File(rutaBinario);
				if (!f.exists()) {
					crearFicheroVacio(rutaBinario);
				}
				escribirContactoPosicion(rutaBinario, 2, "Kevin");
				escribirContactoPosicion(rutaBinario, 3, "Rubi");
				escribirContactoPosicion(rutaBinario, 1, "Marioli");
				escribirContactoPosicion(rutaBinario, 5, "Reny");
				leerContactoPorPosicion(rutaBinario, 2);
				leerTodoElFichero(rutaBinario);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// CREA EL FICHERO CON 5 HUECOS "*"
		public static void crearFicheroVacio(String fichero) throws FileNotFoundException, Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				for (int i = 0; i < 5; i++) {
					escribirNombre(raf, "*");
				}
			}
		}

		public static void escribirContactoPosicion(String fichero, int registro, String nombreContacto)
				throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				long offset = (registro - 1) * TAMANYO_REGISTRO;
				if (offset >= raf.length()) {
					System.out.println("No existe el registro " + registro);
				} else {
					raf.seek(offset);
					String nombre = leerNombre(raf);
					if (nombre.charAt(0) == '*') { // hueco libre
						raf.seek(offset);
						escribirNombre(raf, nombreContacto);
						System.out.println("Contacto " + nombreContacto + " añadido en posicion " + registro); // el original no ponía el número
					} else {
						System.out.println("La posición ya está ocupada por " + nombre);
					}
				}
			}
		}

		public static void escribirNombre(RandomAccessFile raf, String nombreContacto) throws Exception {
			char[] chars = new char[TAMAYO_NOMBRE];
			for (int i = 0; i < TAMAYO_NOMBRE; i++) {
				if (i < nombreContacto.length()) {
					chars[i] = nombreContacto.charAt(i);
				} else {
					chars[i] = ' ';
				}
			}
			for (char c : chars) {
				raf.writeChar(c);
			}
		}

		public static void leerTodoElFichero(String fichero) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
				int numRegistros = (int) raf.length() / TAMANYO_REGISTRO;
				System.out.println("===AGENDA COMPLETA===");
				for (int i = 0; i < numRegistros; i++) {
					raf.seek(i * TAMANYO_REGISTRO);
					String nombre = leerNombre(raf);
					int posicion = i + 1;
					if (nombre.charAt(0) != '*') {
						System.out.println("Posicion " + posicion + ":" + nombre);
					}
				}
			}
		}

		public static void leerContactoPorPosicion(String fichero, int posicion) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				long offset = (posicion - 1) * TAMANYO_REGISTRO;
				if (offset >= raf.length()) {
					System.out.println("No existe el registro " + posicion);
				} else {
					raf.seek(offset);
					String nombre = leerNombre(raf);
					System.out.println(nombre);
				}
			}
		}

		public static String leerNombre(RandomAccessFile raf) throws Exception {
			String nombre = "";
			for (int i = 0; i < TAMAYO_NOMBRE; i++) {
				nombre = nombre + raf.readChar();
			}
			return nombre.trim();
		}
	}

	// ============================================================
	// EJERCICIO 3 · Aparcamiento de 20 plazas (matrícula de 10 caracteres)
	// Practica: crear vacío con "*", aparcar en una plaza libre, liberar una plaza
	//           (volver a poner "*"), buscar una matrícula recorriendo con seek,
	//           mostrar plazas ocupadas
	// Origen: DAM_JAVA/PracticandoExtraordinaria/RA8_AccesoAletorio/Aparcamiento
	// ============================================================
	static class Ejercicio3_Aparcamiento {
		static final int TAMAYO_NOMBRE = 10;
		static final int TAMAYO_REGISTRO = TAMAYO_NOMBRE * 2;

		public static void main(String[] args) {
			String ficheroDAT = "parking.dat";
			try {
				crearFicheroVacio(ficheroDAT); // en el original está comentado tras la primera ejecución
				aparcarVehiculo(ficheroDAT, 2, "6201HMZ");
				// liberarPlazaParking(ficheroDAT, 2);
				buscarPlazaParkingPorMatricula(ficheroDAT, "6201HMZ"); // en el original "6201HM" (no la encuentra)
				mostrarTodoParking(ficheroDAT);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public static void crearFicheroVacio(String fichero) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				for (int i = 0; i < 20; i++) {
					escribirNombre(raf, "*");
				}
			}
		}

		public static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
			char[] chars = new char[TAMAYO_NOMBRE];
			for (int i = 0; i < TAMAYO_NOMBRE; i++) {
				if (i < nombre.length()) {
					chars[i] = nombre.charAt(i);
				} else {
					chars[i] = ' ';
				}
			}
			for (char c : chars) {
				raf.writeChar(c);
			}
		}

		private static String leerNombre(RandomAccessFile raf) throws Exception {
			String nombre = "";
			for (int i = 0; i < TAMAYO_NOMBRE; i++) {
				nombre = nombre + raf.readChar();
			}
			return nombre.trim();
		}

		public static void aparcarVehiculo(String fichero, int posicion, String matricula) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				long offset = (posicion - 1) * TAMAYO_REGISTRO;
				if (offset >= raf.length()) {
					System.out.println("No existe el registro " + posicion);
				} else {
					raf.seek(offset);
					String nombre = leerNombre(raf);
					if (nombre.charAt(0) == '*') {
						raf.seek(offset);
						escribirNombre(raf, matricula);
						System.out.println("Vehiculo " + matricula + " añadido en posicion " + posicion); // el original no ponía el número
					} else {
						System.out.println("La posición ya está ocupada por " + nombre);
					}
				}
			}
		}

		public static void liberarPlazaParking(String fichero, int plazaParkingPosicion) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				long offset = (plazaParkingPosicion - 1) * TAMAYO_REGISTRO;
				if (offset >= raf.length()) {
					System.out.println("No existe el registro " + plazaParkingPosicion);
				} else {
					raf.seek(offset);
					String nombre = leerNombre(raf);
					if (nombre.charAt(0) != '*') {
						raf.seek(offset);
						escribirNombre(raf, "*");
						System.out.println("Plaza " + plazaParkingPosicion + " liberada");
					} else {
						System.out.println("La plaza ya esta liberada");
					}
				}
			}
		}

		public static void buscarPlazaParkingPorMatricula(String fichero, String matricula) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
				int numRegistros = (int) raf.length() / TAMAYO_REGISTRO;
				System.out.println(" --BUSQUEDA VEHICULO-- ");
				for (int i = 0; i < numRegistros; i++) {
					raf.seek(i * TAMAYO_REGISTRO);
					String nombre = leerNombre(raf);
					int posicion = i + 1;
					if (nombre.equalsIgnoreCase(matricula)) {
						System.out.println("Posicion " + posicion + ":" + nombre);
					}
				}
			}
		}

		public static void mostrarTodoParking(String fichero) throws Exception {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
				int numRegistros = (int) raf.length() / TAMAYO_REGISTRO;
				int ocupadas = 0; // en el original se llamaba "asteriscos" y se imprimía numRegistros - asteriscos (salían las libres)
				System.out.println("===ESTADO DEL PARKING===");
				for (int i = 0; i < numRegistros; i++) {
					raf.seek(i * TAMAYO_REGISTRO);
					String nombre = leerNombre(raf);
					int posicion = i + 1;
					if (nombre.charAt(0) != '*') {
						System.out.println("Plaza " + posicion + ": " + nombre);
						ocupadas++;
					}
				}
				System.out.println("Plazas ocupadas " + ocupadas + "/" + numRegistros);
			}
		}
	}

	// ============================================================
	// EJERCICIO 4 · La porra (100 boletos, nombre de 30 caracteres)
	// Practica: diseñar el tamaño de registro y crear el fichero
	// Origen: ACCESO_DATOS/FicherosBinarios/ejPorra/ejPorraFicheroAleatorios
	//         DAM_JAVA/laPorra_Apuestas/Main
	// Enunciado (de los comentarios): "fichero que nos guarde los 100 números";
	//   "añadir persona al número"; listar participantes; hacer el sorteo.
	//   Llamadas del main: apuestaPorNumero(5, "Pepe Morón"), apuestaPorNumero(4, "Rocío López"),
	//   apuestaPorNumero(1, "Antonio Barbas"), apuestaPorNumero(4, "Paquita") -> el 4 ya está cogido.
	//
	// ESTADO EN LOS REPOSITORIOS: SIN TERMINAR. Solo están hechos crearFichero y
	//   la preparación del nombre de tamaño fijo. apuestaPorNumero, hacerSorteo y
	//   listarParticipantes están vacíos, así que aquí NO se inventa su solución.
	//   Para practicar: resuélvelo con las mismas técnicas del Ejercicio 2
	//   (huecos, seek a (numero - 1) * TAMANYOREGISTRO, escribir si está libre).
	// ============================================================
	static class Ejercicio4_LaPorra {
		static final int NOMBOLETOS = 100;
		static final int TAMANYONOMBRE = 30;
		static final int TAMANYOREGISTRO = TAMANYONOMBRE * 2;

		public static void main(String[] args) {
			String fichero = "/home/alumno/porra.txt";
			crearFichero(fichero);
		}

		public static void crearFichero(String fichero) {
			try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
				System.out.println("Fichero creado. Tamaño: " + raf.length() + " bytes");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		// Lo que hay hecho: preparar el nombre con tamaño fijo (falta el bucle de writeChar)
		public static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
			char[] chars = new char[TAMANYONOMBRE];
			for (int i = 0; i < TAMANYONOMBRE; i++) {
				if (i < nombre.length())
					chars[i] = nombre.charAt(i);
				else
					chars[i] = ' ';
			}
		}
	}
}

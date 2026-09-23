package EJERCICIOS_FICHEROS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * ============================================================
 * EJERCICIOS · FICHEROS DE TEXTO — LECTURA
 * ============================================================
 * Teoría: TEORIA/FicherosTexto_Lectura.java
 *
 * Ordenados de menor a mayor dificultad.
 * Cada ejercicio es una clase interna con su propio main.
 * Las rutas son las originales de cada proyecto (/home/alumno/... en clase).
 * ============================================================
 */
public class FicherosTexto_Lectura_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Mostrar un CSV por pantalla
	// Practica: BufferedReader + readLine + close() a mano + IOException
	// Origen: ACCESO_DATOS/DAM2_Boletin1_AccesoFicheros/Ejercicios_Ficheros/E3
	// Fichero: datos/paises.csv
	//   País,Capital,Moneda,Animal
	//   Australia,Canberra,Dólar australiano,Canguro
	// ============================================================
	static class Ejercicio1_MostrarPaises {
		private static final String FICHERO_PAISES = "datos" + File.separator + "paises.csv";

		public static void main(String[] args) {
			leerFichero();
		}

		public static void leerFichero() {
			try {
				BufferedReader lector = new BufferedReader(new FileReader(FICHERO_PAISES));
				String linea;
				while ((linea = lector.readLine()) != null) {
					System.out.println(linea);
				}
				lector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ============================================================
	// EJERCICIO 2 · Contar las palabras de un fichero
	// Practica: readLine + trim + isEmpty + split("\\s+")
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E3
	// ============================================================
	static class Ejercicio2_ContarPalabras {
		public static void main(String[] args) {
			Scanner teclado = new Scanner(System.in);
			System.out.print("Introduce el nombre del fichero: ");
			String fichero = teclado.nextLine();
			teclado.close();
			contarPalabras(fichero);
		}

		public static void contarPalabras(String fichero) {
			int numPalabras = 0;
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					// trim() elimina espacios al principio y al final de la línea
					linea = linea.trim();
					if (linea.isEmpty() == false) {
						// split("\\s+") divide por uno o más espacios seguidos
						String[] palabras = linea.split("\\s+");
						numPalabras += palabras.length;
					}
				}
			} catch (Exception e) {
				System.out.println("Error al leer el fichero: " + e.getMessage());
				return;
			}
			System.out.println("El fichero contiene " + numPalabras + " palabras");
		}
	}

	// ============================================================
	// EJERCICIO 3 · Cuántas veces aparece una palabra en un fichero
	// Practica: isFile (pedir hasta que exista) + Files.readAllLines + split
	// Origen: ACCESO_DATOS/Boletin23Ficheros/ejs/ej1
	//         DAM_JAVA/Boletin23_Ficheros/Ficheros/E1_ficheros (misma solución)
	// Pasos (comentarios de E1_ficheros):
	//   pedir el nombre del fichero; si no existe, volver a pedirlo;
	//   con un fichero válido, otra función devuelve un ArrayList con su contenido;
	//   pedir una palabra; una función cuenta cuántas veces aparece en una línea
	//   y se ejecuta para cada línea, acumulando en un contador.
	// ============================================================
	static class Ejercicio3_ContarUnaPalabra {
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
				for (String linea : lineas) {
					contador += cuentaPalabras(linea, palabra);
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
			for (String p : palabras) {
				if (palabra.equals(p)) {
					contador++;
				}
			}
			return contador;
		}
	}

	// ============================================================
	// EJERCICIO 4 · ¿Tienen dos ficheros el mismo contenido?
	// Practica: Files.readString + equals
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E4
	// ============================================================
	static class Ejercicio4_CompararFicheros {
		public static void main(String[] args) {
			if (compararFicheros("fichero1.txt", "fichero2.txt")) {
				System.out.println("El contenido de los ficheros es el mismo");
			} else {
				System.out.println("El contenido de los ficheros no es el mismo");
			}
		}

		public static boolean compararFicheros(String fichero1, String fichero2) {
			String contenido1 = null;
			String contenido2 = null;
			// Leemos los dos ficheros como String y comparamos
			try {
				contenido1 = Files.readString(Path.of(fichero1));
				contenido2 = Files.readString(Path.of(fichero2));
			} catch (Exception e) {
				System.out.println("Error al leer los ficheros: " + e.getMessage());
				return false;
			}
			// equals compara el contenido carácter a carácter
			return contenido1.equals(contenido2);
		}
	}

	// ============================================================
	// EJERCICIO 5 · Estadísticas: hombres, mujeres y estatura media
	// Practica: Files.readAllLines + recorrer la lista + parseDouble
	// Origen: ACCESO_DATOS/Boletin23Ficheros/ejs/ej5  (DAM_JAVA/Boletin23 E5 lo hace en 3 métodos)
	// Fichero estadisticas.txt: cada línea es "Hombre", "Mujer" o una altura.
	// ============================================================
	static class Ejercicio5_Estadisticas {
		public static void main(String[] args) {
			Path nombreFichero = Path.of("/home/alumno/estadisticas.txt");
			ArrayList<String> lineas = null;
			try {
				lineas = (ArrayList<String>) Files.readAllLines(nombreFichero);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			if (lineas != null) {
				int contadorHombre = 0;
				int contadorMujer = 0;
				double sumaAlturas = 0;
				for (String linea : lineas) {
					if (linea.equals("Hombre")) {
						contadorHombre++;
					} else if (linea.equals("Mujer")) {
						contadorMujer++;
					} else {
						sumaAlturas += Double.parseDouble(linea);
					}
				}
				double media = sumaAlturas / (contadorHombre + contadorMujer);
				System.out.printf("Hombres: %d.\n", contadorHombre);
				System.out.printf("Mujeres: %d.\n", contadorMujer);
				System.out.printf("Estatura media: %.2f", media);
			} else {
				System.out.println("Error o fichero vacío.");
			}
		}
	}

	// ============================================================
	// EJERCICIO 6 · Datos válidos e inválidos (mínimo, máximo, media)
	// Practica: try/catch DENTRO del while con NumberFormatException
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E10
	//   (DAM_JAVA/PracticandoAccesoFicheros/E10 hace lo mismo con readAllLines
	//    y un método esConvertibleADouble)
	// ============================================================
	static class Ejercicio6_DatosValidos {
		public static void main(String[] args) {
			analizarDatos("datos.txt");
		}

		public static void analizarDatos(String fichero) {
			int numValidos = 0;
			int numInvalidos = 0;
			double suma = 0;
			double minimo = Double.MAX_VALUE;
			double maximo = Double.MIN_VALUE;
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					// Si falla la conversión, es un dato inválido
					try {
						double valor = Double.parseDouble(linea.trim());
						numValidos++;
						suma += valor;
						if (valor < minimo) minimo = valor;
						if (valor > maximo) maximo = valor;
					} catch (NumberFormatException e) {
						numInvalidos++;
						System.out.println("Dato inválido encontrado: " + linea);
					}
				}
			} catch (Exception e) {
				System.out.println("Error al leer el fichero: " + e.getMessage());
				return;
			}
			System.out.println("Número de datos válidos: " + numValidos);
			System.out.println("Número de datos inválidos: " + numInvalidos);
			if (numValidos > 0) {
				double media = suma / numValidos;
				System.out.println("Mínimo: " + minimo);
				System.out.println("Máximo: " + maximo);
				System.out.printf("Media aritmética: %.3f%n", media);
			}
		}
	}

	// ============================================================
	// EJERCICIO 7 · Login con un fichero usuario:contraseña
	// Practica: cargar un fichero en un HashMap con indexOf(":") + substring
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E11
	//         (ACCESO_DATOS/Boletin23Ficheros/ejs/ej11: misma lectura)
	// Fichero login.txt:
	//   kevin:1234
	//   ana:abcd
	// ============================================================
	static class Ejercicio7_Login {
		public static void main(String[] args) {
			String fichero = "/home/alumno/login.txt";
			HashMap<String, String> usuarios = leerFichero(fichero);
			// Si usuarios es null hubo un error al leer el fichero
			if (usuarios == null) {
				return;
			}
			if (usuarios.size() == 0) {
				System.out.println("El fichero de usuarios está vacío");
				return;
			}
			comprobarLogin(usuarios);
		}

		public static HashMap<String, String> leerFichero(String fichero) {
			HashMap<String, String> diccionario = new HashMap<>();
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					int posicion = linea.indexOf(":");
					String usuario = linea.substring(0, posicion);
					String contrasena = linea.substring(posicion + 1);
					diccionario.put(usuario, contrasena);
				}
			} catch (Exception e) {
				System.out.println("Fichero inexistente o imposible acceder a él");
				return null;
			}
			return diccionario;
		}

		public static void comprobarLogin(HashMap<String, String> usuarios) {
			Scanner teclado = new Scanner(System.in);
			System.out.print("Usuario: ");
			String usuario = teclado.nextLine();
			System.out.print("Contraseña: ");
			String contrasena = teclado.nextLine();
			teclado.close();
			if (usuarios.containsKey(usuario) == false) {
				System.out.println("Usuario no encontrado");
			} else if (usuarios.get(usuario).equals(contrasena) == false) {
				System.out.println("Contraseña incorrecta");
			} else {
				System.out.println("Login correcto. Bienvenido, " + usuario);
			}
		}
	}

	// ============================================================
	// EJERCICIO 8 · Notas de un módulo por Resultado de Aprendizaje
	// Practica: indexOf(":") + substring + split(",\\s*") + parseDouble
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E9
	//   (ACCESO_DATOS/Boletin23Ficheros/ej9 lo hace con una clase Alumno y
	//    además lo guarda en binario: ver FicherosBinarios_Objetos_Ejercicios)
	// Fichero Redes.txt (nombre del módulo = nombre del fichero):
	//   Ana Pérez: 7, 4.5, 8, 6, 9
	// Salida: alumnos con todo aprobado y, por cada RA, los suspensos.
	// ============================================================
	static class Ejercicio8_NotasPorRA {
		public static void main(String[] args) {
			analizarNotas("Redes.txt");
		}

		public static void analizarNotas(String fichero) {
			String nombreModulo = fichero.replace(".txt", "");
			ArrayList<String> aprobados = new ArrayList<>();
			// Para cada RA (5 en total), guardamos los suspensos
			ArrayList<String>[] suspensosPorRA = new ArrayList[5];
			for (int i = 0; i < 5; i++) {
				suspensosPorRA[i] = new ArrayList<>();
			}
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					int posicion = linea.indexOf(":");
					String alumno = linea.substring(0, posicion).trim();
					String notasTxt = linea.substring(posicion + 2).trim();
					String[] notasStr = notasTxt.split(",\\s*");
					boolean todoAprobado = true;
					for (int i = 0; i < notasStr.length; i++) {
						double nota = Double.parseDouble(notasStr[i].trim());
						if (nota < 5) {
							suspensosPorRA[i].add(alumno);
							todoAprobado = false;
						}
					}
					if (todoAprobado) {
						aprobados.add(alumno);
					}
				}
			} catch (Exception e) {
				System.out.println("Error al leer el fichero: " + e.getMessage());
				return;
			}
			System.out.println("Módulo: " + nombreModulo);
			System.out.println("Alumnos/as con todo aprobado:");
			if (aprobados.isEmpty()) {
				System.out.println("  Ninguno");
			} else {
				for (String a : aprobados) {
					System.out.println(a);
				}
			}
			System.out.println("Resultados de aprendizaje y alumnos suspensos:");
			for (int i = 0; i < 5; i++) {
				if (suspensosPorRA[i].isEmpty()) {
					System.out.println("RA" + (i + 1) + ": Todos aprobados");
				} else {
					String suspensos = "";
					for (int j = 0; j < suspensosPorRA[i].size(); j++) {
						suspensos += suspensosPorRA[i].get(j);
						if (j < suspensosPorRA[i].size() - 1) {
							suspensos += ", ";
						}
					}
					System.out.println("RA" + (i + 1) + ": " + suspensos);
				}
			}
		}
	}

	// ============================================================
	// EJERCICIO 9 · Agenda con registros de varias líneas
	// Practica: llamar a readLine() varias veces dentro del mismo while
	//           (un registro ocupa 3 o 4 líneas) + validar cada campo
	// Origen: DAM_JAVA/Boletin23_Ficheros/Ficheros/E8
	// Formato agenda.txt: nombre / [apellido] / categoría (Familia, Amigo, Conocido) / edad
	// ============================================================
	static class Ejercicio9_AgendaVariasLineas {
		static final String[] CATEGORIAS_VALIDAS = { "Familia", "Amigo", "Conocido" };

		static class Contacto {
			String nombre;
			String apellido; // puede ser null si no tiene apellido
			String categoria;
			int edad;

			public Contacto(String nombre, String apellido, String categoria, int edad) {
				this.nombre = nombre;
				this.apellido = apellido;
				this.categoria = categoria;
				this.edad = edad;
			}

			public void mostrar() {
				String nombreCompleto = (apellido != null) ? nombre + " " + apellido : nombre;
				System.out.println(nombreCompleto + " [" + categoria + "] - " + edad + " años");
			}
		}

		public static void main(String[] args) {
			ArrayList<Contacto> agenda = leerAgenda("agenda.txt");
			System.out.println("Contactos cargados: " + agenda.size());
			for (Contacto c : agenda) {
				c.mostrar();
			}
		}

		public static ArrayList<Contacto> leerAgenda(String fichero) {
			ArrayList<Contacto> lista = new ArrayList<>();
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					String nombre = linea;
					String apellido = null;
					String categoria = null;
					String edadTxt = null;
					// La siguiente línea puede ser apellido o categoría
					String siguiente = lector.readLine();
					if (siguiente == null) {
						System.out.println("Registro incompleto, se descarta: " + nombre);
						break;
					}
					if (esCategoriaValida(siguiente)) {
						categoria = siguiente;
					} else {
						apellido = siguiente;
						String sigCategoria = lector.readLine();
						if (sigCategoria == null || esCategoriaValida(sigCategoria) == false) {
							System.out.println("Categoria invalida en registro: " + nombre + ", se descarta");
							continue;
						}
						categoria = sigCategoria;
					}
					edadTxt = lector.readLine();
					if (edadTxt == null || esEntero(edadTxt) == false) {
						System.out.println("Edad invalida en registro: " + nombre + ", se descarta");
						continue;
					}
					int edad = Integer.parseInt(edadTxt);
					lista.add(new Contacto(nombre, apellido, categoria, edad));
				}
			} catch (Exception e) {
				System.out.println("Error al leer el fichero: " + e.getMessage());
			}
			return lista;
		}

		public static boolean esCategoriaValida(String linea) {
			for (String cat : CATEGORIAS_VALIDAS) {
				if (cat.equals(linea)) {
					return true;
				}
			}
			return false;
		}

		public static boolean esEntero(String linea) {
			try {
				Integer.parseInt(linea);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	// ============================================================
	// EJERCICIO 10 · Países del CSV (examen ordinaria RA5, corrección)
	// Practica: saltar cabecera, split(","), validar número de campos,
	//           guardar cada columna en un ArrayList
	// Origen: DAM_JAVA/CorreccionExamenOrdinaria/RA5/Ejercicio1_Paises
	//   (tu versión del examen: UT7_Ficheros_RA5/ExamenOrdinaria/E1_RA5)
	// Salida: "Nombres: Australia, Canadá, ... y Nueva Zelanda"
	// ============================================================
	static class Ejercicio10_PaisesCSV {
		static String fichero = "D:\\IES FRANCISCO DE GOYA\\DAM\\PROGRAMACION\\paises.csv";

		public static void main(String[] args) {
			try {
				BufferedReader lector = new BufferedReader(new FileReader(fichero));
				ArrayList<String> paises = new ArrayList<>();
				ArrayList<String> capitales = new ArrayList<>();
				ArrayList<String> monedas = new ArrayList<>();
				ArrayList<String> animales = new ArrayList<>();
				lector.readLine(); // saltar cabecera
				String linea;
				while ((linea = lector.readLine()) != null) {
					String[] campos = linea.split(",");
					if (campos.length == 4) { // ignorar líneas erróneas
						paises.add(campos[0]);
						capitales.add(campos[1]);
						monedas.add(campos[2]);
						animales.add(campos[3]);
					}
				}
				lector.close();
				if (paises.size() == 0) {
					System.out.println("No hay datos de ningún país en el fichero");
				} else {
					System.out.println("Países en el fichero: " + paises.size());
					System.out.println("Nombres: " + formatear(paises));
					System.out.println("Las capitales de los mismos son: " + formatear(capitales));
					System.out.println("Sus monedas oficiales son: " + formatear(monedas));
					System.out.println("Sus animales mas representativos son " + formatear(animales));
				}
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
		}

		public static String formatear(ArrayList<String> lista) {
			String resultado = "";
			for (int i = 0; i < lista.size(); i++) {
				if (i == lista.size() - 1 && lista.size() > 1) {
					resultado += "y " + lista.get(i);
				} else if (i == lista.size() - 1) {
					resultado += lista.get(i);
				} else {
					resultado += lista.get(i) + ", ";
				}
			}
			return resultado;
		}
	}

	// ============================================================
	// EJERCICIO 11 · Biblioteca: libros por género, más antiguo y más reciente
	// Practica: CSV con ";" + contadores + guardar el array de la línea
	// Origen: DAM_JAVA/PracticandoExtraordinaria/RA5_FicherosTexto/E2_Biblioteca
	// Fichero biblioteca.csv: Titulo;Autor;Genero;Año
	//   Dracula;BramStoker;Terror;1897
	// ============================================================
	static class Ejercicio11_Biblioteca {
		public static void main(String[] args) {
			String fichero = "biblioteca.csv";
			lectura1(fichero);
		}

		public static void lectura1(String fichero) {
			try {
				BufferedReader lector = new BufferedReader(new FileReader(fichero));
				String linea;
				String[] lista = new String[4];
				int novela = 0;
				int terror = 0;
				int cienciaFiccion = 0;
				int historia = 0;
				int fantasia = 0;
				int masAntiguo = Integer.MAX_VALUE;
				int masReciente = 0;
				String[] libroAntiguo = new String[4];
				String[] libroReciente = new String[4];
				while ((linea = lector.readLine()) != null) {
					lista = linea.split(";");
					if (lista[2].equalsIgnoreCase("Novela")) {
						novela++;
					} else if (lista[2].equalsIgnoreCase("Terror")) {
						terror++;
					} else if (lista[2].equalsIgnoreCase("Ciencia ficcion")) {
						cienciaFiccion++;
					} else if (lista[2].equalsIgnoreCase("historia")) {
						historia++;
					} else if (lista[2].equalsIgnoreCase("fantasia")) {
						fantasia++;
					}
					int fechaAntiguo = Integer.parseInt(lista[3]);
					if (masAntiguo > fechaAntiguo) {
						libroAntiguo = lista;
						masAntiguo = fechaAntiguo;
					}
					int fechaReciente = Integer.parseInt(lista[3]);
					if (fechaReciente > masReciente) {
						masReciente = fechaReciente;
						libroReciente = lista;
					}
				}
				System.out.println("-- LIBROS POR GENERO --");
				System.out.println("Novela " + novela);
				System.out.println("Terror " + terror);
				System.out.println("Cienca ficcion " + cienciaFiccion);
				System.out.println("Historia " + historia);
				System.out.println("Fantasia " + fantasia);
				System.out.println();
				System.out.println("Libro mas antiguo: " + libroAntiguo[0] + "(" + libroAntiguo[1] + ")" + libroAntiguo[3]);
				System.out.println("Libros mas reciente: " + libroReciente[0] + "(" + libroReciente[1] + ")" + libroReciente[3]);
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// ============================================================
	// EJERCICIO 12 · EXAMEN: animes y sus personajes (dos ficheros relacionados)
	// Practica: cargar un fichero en HashMap<Integer, String>, recorrerlo con
	//           Map.Entry y releer el segundo fichero por cada entrada.
	//           Métodos con throws Exception y try/catch en el main.
	// Origen: ACCESO_DATOS/ExamenFicherosSara/correccionExamen/Ejercicio1 (corrección)
	//         = ACCESO_DATOS/DAM2_Boletin1_AccesoFicheros/Ejercicios_Ficheros/E1
	//   (tu versión: DAM_JAVA/UT7_Ficheros_RA5/ExamenAnime/Ejercicio1)
	// Ficheros:
	//   animes.txt      17 Naruto / 22 Dan Da Dan / 4 Demon Slayer / 3 One Piece / 6 Hunter x Hunter
	//   personajes.txt  4 Tanjiro Kamado / 17 Naruto Uzumaki / 3 Vinsmoke Sanji / 5 Shinji Ikari ...
	// Salida: cada anime con sus personajes ("No hay personajes" si no tiene)
	//         y al final los "Personajes sin anime" (código que no está en animes.txt).
	// ============================================================
	static class Ejercicio12_AnimesPersonajes {
		public static void main(String[] args) {
			String fPersonajes = "/home/alumno/personajes.txt";
			String fAnimes = "/home/alumno/animes.txt";
			try {
				HashMap<Integer, String> animes = leerDAtosAnimes(fAnimes);
				for (Map.Entry<Integer, String> anime : animes.entrySet()) {
					ArrayList<String> personajes = leerPersonajes(anime.getKey(), fPersonajes);
					System.out.println(anime.getValue());
					if (personajes.size() == 0) {
						System.out.println("No hay personajes");
					} else {
						for (String p : personajes) {
							System.out.println("- " + p);
						}
					}
				}
				ArrayList<String> personajes = leerPersonajesSinAnime(animes, fPersonajes);
				if (personajes.size() != 0) {
					System.out.println("Personajes sin anime");
					for (String p : personajes) {
						System.out.println("- " + p);
					}
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		private static HashMap<Integer, String> leerDAtosAnimes(String fichero) throws Exception {
			HashMap<Integer, String> animes = new HashMap<>();
			try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = br.readLine()) != null) {
					int posicion = linea.indexOf(" ");
					int num = Integer.parseInt(linea.substring(0, posicion));
					String titulo = linea.substring(posicion + 1);
					animes.put(num, titulo);
				}
			}
			return animes;
		}

		private static ArrayList<String> leerPersonajes(int codigo, String fichero) throws Exception {
			ArrayList<String> personajes = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = br.readLine()) != null) {
					int posicion = linea.indexOf(" ");
					int num = Integer.parseInt(linea.substring(0, posicion));
					if (num == codigo) {
						String nombre = linea.substring(posicion + 1);
						personajes.add(nombre);
					}
				}
			}
			return personajes;
		}

		private static ArrayList<String> leerPersonajesSinAnime(HashMap<Integer, String> animes, String fichero)
				throws Exception {
			ArrayList<String> personajes = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = br.readLine()) != null) {
					int posicion = linea.indexOf(" ");
					int num = Integer.parseInt(linea.substring(0, posicion));
					if (animes.containsKey(num) == false) {
						String nombre = linea.substring(posicion + 1);
						personajes.add(nombre);
					}
				}
			}
			return personajes;
		}
	}
}

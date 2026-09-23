package EJERCICIOS_FICHEROS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * ============================================================
 * EJERCICIOS · FICHEROS DE TEXTO — ESCRITURA, LECTURA+ESCRITURA Y MODIFICACIÓN
 * ============================================================
 * Teoría: TEORIA/FicherosTexto_Escritura.java
 *
 * Ordenados de menor a mayor dificultad:
 *   1-4   solo escribir
 *   5-9   leer un fichero y escribir otro
 *   10-11 modificar un fichero (leer todo -> cambiar -> reescribir)
 *   12-13 exámenes / prácticas completas
 * ============================================================
 */
public class FicherosTexto_Escritura_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Tabla de multiplicar en un fichero
	// Practica: PrintWriter(ruta) + printf con %2d / %3d + println
	// Origen: ACCESO_DATOS/Boletin24Ficheros/ejs/ej1
	//   (DAM_JAVA/Boletin24_EscrituraFichura/E1 igual pero con PrintWriter(new FileWriter(...)))
	// Enunciado: pedir un número del 1 al 10 y grabar su tabla en tabla-N.txt
	// ============================================================
	static class Ejercicio1_TablaMultiplicar {
		public static void main(String[] args) {
			Scanner teclado = new Scanner(System.in);
			System.out.print("Escribe un número: ");
			int num;
			try {
				num = teclado.nextInt();
				if (num < 1 || num > 10) {
					System.out.println("El número tiene que estar entre el 1 y 10.");
				}
				try (PrintWriter escritura = new PrintWriter("/home/alumno/tabla-" + String.valueOf(num) + ".txt")) {
					for (int i = 1; i < 11; i++) {
						int resultado = num * i;
						escritura.printf("%d x %2d = %3d", num, i, resultado);
						escritura.println();
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			} catch (Exception e) {
				System.out.println("Esto no es un número entero.");
			}
			teclado.close();
		}
	}

	// ============================================================
	// EJERCICIO 2 · Tabla de multiplicar: escribir con FileWriter y leer si existe
	// Practica: FileWriter.write + String.format + File.exists + leer
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/Boletin24_FicherosTexto/Ejercicio1_2_TablasMultiplicar
	// ============================================================
	static class Ejercicio2_TablaFileWriter {
		public static void main(String[] args) {
			Scanner teclado = new Scanner(System.in);
			System.out.println("Numero tabla multiplicar: ");
			int numero = teclado.nextInt();
			escribirTabla(numero);
			leerTablaFichero(numero);
		}

		public static void escribirTabla(int numeroTabla) {
			// Para AÑADIR al final: segundo parámetro true
			String nombreFichero = String.format("tabla-%d.txt", numeroTabla);
			try (FileWriter escritor = new FileWriter("/home/alumno/Escritorio/" + nombreFichero)) {
				for (int i = 1; i <= 10; i++) {
					escritor.write(String.format("%2d x %2d = %3d\n", numeroTabla, i, numeroTabla * i));
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static void leerTablaFichero(int numeroTabla) {
			String rutaFichero = "/home/alumno/Escritorio/tabla-" + numeroTabla + ".txt";
			File existenciaFichero = new File(rutaFichero);
			if (existenciaFichero.exists()) {
				try {
					BufferedReader lector = new BufferedReader(new FileReader(rutaFichero));
					String linea;
					while ((linea = lector.readLine()) != null) {
						System.out.println(linea);
					}
					lector.close();
				} catch (Exception e) {
					System.out.println("Error al leer: " + e.getMessage());
				}
			} else {
				System.out.println("El fichero no existe");
			}
		}
	}

	// ============================================================
	// EJERCICIO 3 · Serie de Fibonacci en un fichero
	// Practica: FileWriter.write en un bucle (separador ", " salvo el último)
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/Boletin24_FicherosTexto/Ejercicio3_Fibonacci
	// ============================================================
	static class Ejercicio3_Fibonacci {
		public static void main(String[] args) {
			String rutaFichero = "D:\\IES FRANCISCO DE GOYA\\DAM\\PROGRAMACION\\fibonacci.txt";
			ficheroFibonacci(rutaFichero, 3);
			leerFibonacci(rutaFichero);
		}

		public static void ficheroFibonacci(String rutaFichero, int cantidadNumeros) {
			try (FileWriter escritor = new FileWriter(rutaFichero)) {
				int num0 = 0;
				int num1 = 1;
				if (cantidadNumeros < 2) {
					System.out.println("Error la cantidad tiene que ser mayor a 2");
				} else {
					for (int i = 0; i < cantidadNumeros; i++) {
						if (i < cantidadNumeros - 1) {
							escritor.write(num0 + ", ");
						} else {
							escritor.write("" + num0);
						}
						int nuevo = num1 + num0;
						num0 = num1;
						num1 = nuevo;
					}
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static void leerFibonacci(String rutaFichero) {
			try {
				BufferedReader lector = new BufferedReader(new FileReader(rutaFichero));
				String linea;
				while ((linea = lector.readLine()) != null) {
					System.out.println(linea);
				}
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 4 · Lista de la compra
	// Practica: pedir datos por teclado en un do-while y escribirlos con FileWriter
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/Boletin24_FicherosTexto/Ejercicio5_ListaCompra
	// OJO (duda del propio código): después de nextDouble() hay que hacer un
	//   teclado.nextLine() para "limpiar" el salto de línea antes del siguiente nextLine().
	// ============================================================
	static class Ejercicio4_ListaCompra {
		public static void main(String[] args) {
			String rutaFichero = "D:\\IES FRANCISCO DE GOYA\\DAM\\PROGRAMACION\\compra.txt";
			escribirListaCompraFichero(rutaFichero);
		}

		public static void escribirListaCompraFichero(String rutaFichero) {
			try (FileWriter escritor = new FileWriter(rutaFichero)) {
				String siNo = "";
				String nombreArticulo = "";
				double cantidad = 0;
				double precioUnidad = 0;
				Scanner teclado = new Scanner(System.in);
				String linea = "";
				int contadorArticulos = 0;
				double precioCompraTotal = 0;
				do {
					System.out.print("Introduce un articulo: ");
					nombreArticulo = teclado.nextLine();
					System.out.print("Introduce cantidad: ");
					cantidad = teclado.nextDouble();
					System.out.print("Introduce precio: ");
					precioUnidad = teclado.nextDouble();
					linea = cantidad + " " + nombreArticulo + "\n";
					escritor.write(linea);
					contadorArticulos++;
					precioCompraTotal = precioCompraTotal + (cantidad * precioUnidad);
					teclado.nextLine();
					System.out.print("Quieres seguir introduciendo articulos en la lista (si/no): ");
					siNo = teclado.nextLine();
				} while (siNo.equalsIgnoreCase("si"));
				System.out.println("Tu lista de la compra se en encuentra en el fichero " + rutaFichero);
				escritor.write("Total de articulos en la lista: " + contadorArticulos + "\n");
				escritor.write("Precio de la compra " + precioCompraTotal);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// ============================================================
	// EJERCICIO 5 · Alta de un usuario nuevo en login.txt (MODO AÑADIR)
	// Practica: leer a HashMap + validar + PrintWriter(new FileWriter(ruta, true))
	// Origen: ACCESO_DATOS/Boletin24Ficheros/ejs/ej7
	//         DAM_JAVA/Boletin24_EscrituraFichura/ejercicio7, Boletin23_Ficheros/E7
	// Enunciado (comentarios del ejercicio):
	//   Dar de alta un usuario. Si ya existe -> error. Ni usuario ni contraseña
	//   pueden tener ":". Pedir la contraseña 2 veces (tienen que ser iguales).
	//   Escribir en el fichero usando append.
	// ============================================================
	static class Ejercicio5_AltaUsuario {
		public static void main(String[] args) {
			String fichero = "/home/alumno/login.txt";
			HashMap<String, String> diccionario = leerFichero(fichero);
			nuevoUsuario(diccionario, fichero);
		}

		public static HashMap<String, String> leerFichero(String fichero) {
			HashMap<String, String> diccionario = new HashMap<>();
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					int posicion = linea.indexOf(":");
					diccionario.put(linea.substring(0, posicion), linea.substring(posicion + 1));
				}
				if (diccionario.size() == 0) {
					System.out.println("Fichero vacío");
				}
			} catch (Exception e) {
				System.out.println("Fichero inexistente o imposible acceder a él");
			}
			return diccionario;
		}

		public static void nuevoUsuario(HashMap<String, String> diccionario, String fichero) {
			Scanner lector = new Scanner(System.in);
			System.out.print("Escribe un nombre de usuario nuevo: ");
			String usuario = lector.nextLine();
			System.out.print("Escribe una constraseña de usuario: ");
			String password = lector.nextLine();
			System.out.print("Escribe la misma constraseña de usuario: ");
			String passwordRep = lector.nextLine();
			lector.close();
			if (password.equals(passwordRep) == false) {
				System.out.println("Las contraseñas no coinciden");
			} else if (diccionario.containsKey(usuario)) {
				System.out.println("El usuario ya existe");
			} else if (usuario.indexOf(":") >= 0 || password.indexOf(":") >= 0) {
				System.out.println("Ni el nombre de usuario ni la contraseña pueden contener el caracter ':'");
			} else {
				System.out.println("Grabando en el fichero...");
				grabarUsuarioFichero(usuario, password, fichero);
			}
		}

		private static void grabarUsuarioFichero(String usuario, String password, String fichero) {
			try (PrintWriter pluma = new PrintWriter(new FileWriter(fichero, true))) {
				pluma.printf("%s:%s", usuario, password);
				pluma.println();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 6 · Dar la vuelta a un fichero
	// Practica: Files.readAllLines + invertir cada línea + add(0, linea) + Files.write
	// Origen: ACCESO_DATOS/Boletin24Ficheros/ejs/ej4Profe (solución del profesor)
	//   (tu versión: DAM_JAVA/UT7_Ficheros_RA5/Boletin24_FicherosTexto/Ejercicio4_invertirFicheroYTexto,
	//    que da la vuelta a la lista con lineas.reversed() y escribe con Files.write(..., UTF_8))
	// Enunciado: la última línea pasa a ser la primera y cada línea se escribe al revés.
	//   origen.txt: uno / dos dos / tres y cuatro
	// ============================================================
	static class Ejercicio6_DarLaVuelta {
		public static void main(String[] args) {
			darlaVuelta("origen.txt", "destino.txt");
		}

		private static void darlaVuelta(String origen, String destino) {
			ArrayList<String> listaOrigen = null;
			ArrayList<String> listaDestino = new ArrayList<>();
			listaOrigen = leerFichero(origen);
			if (listaOrigen != null) {
				for (String linea : listaOrigen) {
					linea = invertirContenido(linea);
					listaDestino.add(0, linea); // la meto en la primera posición
				}
			}
			escribirFichero(destino, listaDestino);
		}

		public static ArrayList<String> leerFichero(String fichero) {
			ArrayList<String> lineas = null;
			Path fich = Path.of(fichero);
			try {
				lineas = (ArrayList<String>) Files.readAllLines(fich);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return lineas;
		}

		public static String invertirContenido(String linea) {
			String invertida = "";
			for (int i = 0; i < linea.length(); i++) {
				invertida = linea.charAt(i) + invertida;
			}
			return invertida;
		}

		public static void escribirFichero(String fichero, ArrayList<String> lista) {
			Path ruta = Path.of(fichero);
			try {
				Files.write(ruta, lista);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 7 · Corregir un test y grabar las notas
	// Practica: Files.readString (una línea) + BufferedReader a HashMap<String, String[]>
	//           + recorrer con Map.Entry + PrintWriter.printf
	// Origen: ACCESO_DATOS/Boletin24Ficheros/ejs/ej6
	//         DAM_JAVA/Boletin24_EscrituraFichura/ejercicio_6 (usa split(",\\s*"))
	//   (versión con FileWriter: DAM_JAVA/UT7_Ficheros_RA5/Boletin24_FicherosTexto/Ejercicio6_NotasDiccionario)
	// Ficheros:
	//   soluciones.txt  A, C, C, D, B, A, D, A, B, A
	//   respuestas.txt  Claudia Pasón: A, B, B, A, B, A, C, A, B, A
	// Nota: +1 por acierto, -0.3 por fallo, mínimo 0.  notas.txt -> "Claudia Pasón: 4.8"
	// ============================================================
	static class Ejercicio7_NotasTest {
		public static void main(String[] args) {
			String fSoluciones = "/home/alumno/boletin24ej6/soluciones.txt";
			String fRespuestas = "/home/alumno/boletin24ej6/respuestas.txt";
			String fNotas = "/home/alumno/boletin24ej6/notas.txt";
			int numPreguntas = 10;
			String soluciones[] = new String[numPreguntas];
			HashMap<String, String[]> respuestas = null;
			soluciones = leeSoluciones(fSoluciones);
			respuestas = leeRespuestas(fRespuestas);
			grabaNotas(fNotas, soluciones, respuestas);
		}

		private static String[] leeSoluciones(String fSoluciones) {
			Path fichero = Path.of(fSoluciones);
			String linea = null;
			try {
				linea = Files.readString(fichero);
			} catch (Exception e) {
				System.out.println("Error con el fichero: " + e.getMessage());
			}
			String[] soluciones = linea.split(", ");
			return soluciones;
		}

		private static HashMap<String, String[]> leeRespuestas(String fRespuestas) {
			HashMap<String, String[]> diccionario = new HashMap<>();
			try (BufferedReader lector = new BufferedReader(new FileReader(fRespuestas))) {
				String linea = null;
				while ((linea = lector.readLine()) != null) {
					int posicion = linea.indexOf(":");
					String alumno = linea.substring(0, posicion);
					String respuestas = linea.substring(posicion + 2); // salta ": "
					diccionario.put(alumno, respuestas.split(", "));
				}
			} catch (Exception e) {
				System.out.println("Error con el fichero: " + e.getMessage());
			}
			return diccionario;
		}

		private static void grabaNotas(String fNotas, String[] soluciones, HashMap<String, String[]> respuestas) {
			try (PrintWriter pluma = new PrintWriter(fNotas)) {
				for (Map.Entry<String, String[]> respuesta : respuestas.entrySet()) {
					System.out.print(respuesta.getKey() + " : ");
					System.out.println(calcularNota(soluciones, respuesta.getValue()));
					pluma.printf("%s: %.1f", respuesta.getKey(), calcularNota(soluciones, respuesta.getValue()));
					pluma.println();
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		private static double calcularNota(String[] soluciones, String[] respuesta) {
			double nota = 0;
			for (int i = 0; i < soluciones.length; i++) {
				if (soluciones[i].charAt(0) == respuesta[i].charAt(0)) {
					nota += 1;
				} else {
					nota -= 0.3;
				}
			}
			if (nota < 0) {
				nota = 0;
			}
			return nota;
		}
	}

	// ============================================================
	// EJERCICIO 8 · Validar la sintaxis de un fichero y copiar las líneas correctas
	// Practica: leer y escribir A LA VEZ: dos recursos en el mismo try-with-resources
	// Origen: DAM_JAVA/Boletin24_EscrituraFichura/Escritura/Ejercicio8
	// Formato de cada línea: Apellidos, Nombre;Puesto;Salario
	//   Las correctas van a salida.txt, las incorrectas se muestran por consola.
	// ============================================================
	static class Ejercicio8_ValidarSintaxis {
		public static void main(String[] args) {
			String origen = "/home/alumno/origen.txt";
			String destino = "/home/alumno/salida.txt";
			verificarSintaxis(origen, destino);
		}

		public static void verificarSintaxis(String origen, String destino) {
			try (BufferedReader lector = new BufferedReader(new FileReader(origen));
					PrintWriter pluma = new PrintWriter(destino)) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					if (lineaEsCorrecta(linea)) {
						pluma.println(linea);
					} else {
						System.out.println("Línea incorrecta: " + linea);
					}
				}
				System.out.println("Proceso completado. Las líneas correctas están en: " + destino);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		public static boolean lineaEsCorrecta(String linea) {
			String[] partes = linea.split(";");
			if (partes.length != 3) {
				return false;
			}
			String campoNombre = partes[0];
			String campoPuesto = partes[1];
			String campoSalario = partes[2];
			int posicionComa = campoNombre.indexOf(",");
			if (posicionComa < 0) {
				return false;
			}
			String apellidos = campoNombre.substring(0, posicionComa).trim();
			String nombre = campoNombre.substring(posicionComa + 1).trim();
			if (soloLetrasYEspacios(apellidos) == false) return false;
			if (soloLetrasYEspacios(nombre) == false) return false;
			if (apellidos.isEmpty() || nombre.isEmpty()) return false;
			if (campoPuesto.trim().isEmpty()) {
				return false;
			}
			try {
				double salario = Double.parseDouble(campoSalario.trim());
				if (salario < 0) return false;
			} catch (Exception e) {
				return false;
			}
			return true;
		}

		public static boolean soloLetrasYEspacios(String texto) {
			for (int i = 0; i < texto.length(); i++) {
				char c = texto.charAt(i);
				if (Character.isLetter(c) == false && c != ' ') {
					return false;
				}
			}
			return true;
		}
	}

	// ============================================================
	// EJERCICIO 9 · Tienda online: filtrar productos baratos a otro fichero
	// Practica: leer CSV -> ArrayList -> Files.write(..., UTF_8) -> volver a leer
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/TiendaOnline_FicheroTexto/TiendaOnline
	// productos.txt: Mouse,Electrónica,25.50
	// ============================================================
	static class Ejercicio9_TiendaOnline {
		static String fichero = "productos.txt";
		static String ficheroRebajados = "productos_rebajados.txt";

		public static void main(String[] args) {
			mostrarProductosMenorPrecio(40);
		}

		public static void mostrarProductosMenorPrecio(double filtroPrecio) {
			ArrayList<String> lineasAprobadas = new ArrayList<String>();
			// LEEMOS Y FILTRAMOS
			try {
				BufferedReader lector = new BufferedReader(new FileReader(fichero));
				String linea;
				String[] lista = new String[3];
				double precioLinea = 0;
				while ((linea = lector.readLine()) != null) {
					lista = linea.split(",");
					if (lista.length == 3) {
						precioLinea = Double.parseDouble(lista[2]);
						if (precioLinea < filtroPrecio) {
							System.out.println(linea);
							lineasAprobadas.add(linea);
						}
					}
				}
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
				e.printStackTrace();
			}
			// ESCRIBIR
			Path ruta = Paths.get(ficheroRebajados);
			try {
				Files.write(ruta, lineasAprobadas, StandardCharsets.UTF_8);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			// LEEMOS Y MOSTRAMOS LOS PRODUCTOS ENCONTRADOS
			try {
				BufferedReader lector = new BufferedReader(new FileReader(ficheroRebajados));
				String linea2;
				String[] lista2 = new String[3];
				double precioMedio = 0;
				int contador = 0;
				while ((linea2 = lector.readLine()) != null) {
					lista2 = linea2.split(",");
					precioMedio = precioMedio + Double.parseDouble(lista2[2]);
					contador++;
				}
				precioMedio = precioMedio / contador;
				System.out.println("Precio medio " + precioMedio + "\nProductos encontrados " + contador);
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// ============================================================
	// EJERCICIO 10 · MODIFICAR: quitar la columna de la moneda de un CSV
	// Practica: leer todo a un ArrayList (ya modificado) -> cerrar -> Files.write
	//           sobre el MISMO fichero
	// Origen: DAM_JAVA/CorreccionExamenOrdinaria/RA5/Ejercicio2_sinMoneda
	//   (tu versión del examen: UT7_Ficheros_RA5/ExamenOrdinaria/E2_RA5, que lo
	//    escribe en otro fichero paises1.csv)
	// "SI QUEREMOS SOBREESCRIBIR EL FICHERO TENEMOS QUE ALMACENAR EL CONTENIDO
	//  QUE DESEAMOS Y VOLVER A ESCRIBIR"
	// ============================================================
	static class Ejercicio10_QuitarMoneda {
		static String fichero = "D:\\IES FRANCISCO DE GOYA\\DAM\\PROGRAMACION\\paises2.csv";

		public static void main(String[] args) {
			quitarMoneda();
		}

		public static void quitarMoneda() {
			try {
				ArrayList<String> sinMonedas = new ArrayList<String>();
				sinMonedas.add("Pais,Capital,Animal");
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
	}

	// ============================================================
	// EJERCICIO 11 · MODIFICAR: añadir la edad a cada empleado
	// Practica: leer líneas a una lista -> pedir un dato por línea ->
	//           reescribir el mismo fichero con PrintWriter (sin true = sobrescribe)
	// Origen: DAM_JAVA/Boletin24_EscrituraFichura/Escritura/Ejercicio9
	// empleados.txt: Apellidos, Nombre;Puesto;Salario   ->   ...;Salario;Edad
	// La edad tiene que ser un entero entre 18 y 66.
	// ============================================================
	static class Ejercicio11_AnadirEdad {
		public static void main(String[] args) {
			String fichero = "/home/alumno/empleados.txt";
			ArrayList<String> lineas = leerFichero(fichero);
			if (lineas == null || lineas.size() == 0) {
				System.out.println("El fichero está vacío o no existe");
				return;
			}
			ArrayList<String> lineasConEdad = añadirEdades(lineas);
			escribirFichero(fichero, lineasConEdad);
		}

		public static ArrayList<String> leerFichero(String fichero) {
			ArrayList<String> lista = new ArrayList<>();
			try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					lista.add(linea);
				}
			} catch (Exception e) {
				System.out.println("Error al leer el fichero: " + e.getMessage());
				return null;
			}
			return lista;
		}

		public static ArrayList<String> añadirEdades(ArrayList<String> lineas) {
			Scanner teclado = new Scanner(System.in);
			ArrayList<String> lineasConEdad = new ArrayList<>();
			for (String linea : lineas) {
				String[] campos = linea.split(";");
				int posicionComa = campos[0].indexOf(",");
				String apellidos = campos[0].substring(0, posicionComa).trim();
				String nombre = campos[0].substring(posicionComa + 2).trim();
				System.out.print(nombre + " " + apellidos + ". ¿Cuál es su edad? ");
				int edad = 0;
				boolean edadValida = false;
				while (edadValida == false) {
					try {
						edad = Integer.parseInt(teclado.nextLine());
						if (edad < 18 || edad >= 67) {
							System.out.print("La edad debe estar entre 18 y 66 años: ");
						} else {
							edadValida = true;
						}
					} catch (Exception e) {
						System.out.print("Eso no es un número entero. Introduce la edad: ");
					}
				}
				lineasConEdad.add(linea + ";" + edad);
			}
			teclado.close();
			return lineasConEdad;
		}

		public static void escribirFichero(String fichero, ArrayList<String> lineas) {
			// Sin true -> sobrescribe el fichero original
			try (PrintWriter pluma = new PrintWriter(fichero)) {
				for (String linea : lineas) {
					pluma.println(linea);
				}
				System.out.println("Fichero actualizado correctamente");
			} catch (Exception e) {
				System.out.println("Error al escribir el fichero: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 12 · Academia de idiomas: medias por idioma y fichero de aprobados
	// Practica: CSV ";" con líneas mal formadas (try/catch dentro del while)
	//           + Files.write(ruta, lista, UTF_8)
	// Origen: DAM_JAVA/PracticandoExtraordinaria/RA5_FicherosTexto/E1_NotasAcademicas
	// notas_academia.csv: Ana Garcia;Ingles;7.50   (hay líneas como "Pedro;Ingles" o "Maria;Frances;abc")
	// ============================================================
	static class Ejercicio12_NotasAcademia {
		public static void main(String[] args) {
			String ficheroNotasAcademicas = "notas_academia.csv";
			String ficheroAprobados = "aprobados.csv";
			lecturaNotasAcamicas(ficheroNotasAcademicas, ficheroAprobados);
		}

		public static void lecturaNotasAcamicas(String fichero, String ficheroAprobados) {
			ArrayList<String> arrayListLineas = new ArrayList<String>();
			try {
				BufferedReader lector = new BufferedReader(new FileReader(fichero));
				String linea;
				String[] lista = new String[3];
				double notaMediaIngles = 0;
				double notaMediaFrances = 0;
				double notaMediaAleman = 0;
				int numAlumnosAleman = 0;
				int numAlumnosFrances = 0;
				int numAlumnosIngles = 0;
				double notaMasAlta = 0;
				String alumnoNotaMasAlta = "";
				while ((linea = lector.readLine()) != null) {
					try {
						lista = linea.split(";");
						if (lista.length == 3) {
							if (lista[1].equalsIgnoreCase("Ingles")) {
								numAlumnosIngles++;
								notaMediaIngles = notaMediaIngles + Double.parseDouble(lista[2]);
							} else if (lista[1].equalsIgnoreCase("Frances")) {
								numAlumnosFrances++;
								notaMediaFrances = notaMediaFrances + Double.parseDouble(lista[2]);
							} else if (lista[1].equalsIgnoreCase("Aleman")) {
								numAlumnosAleman++;
								notaMediaAleman = notaMediaAleman + Double.parseDouble(lista[2]);
							}
							double nota = Double.parseDouble(lista[2]);
							if (nota > notaMasAlta) {
								notaMasAlta = nota;
								alumnoNotaMasAlta = linea;
							}
							if (Double.parseDouble(lista[2]) >= 5) {
								arrayListLineas.add(linea);
							}
						}
					} catch (Exception e) {
						System.out.println("Linea ignorada (formato incorrecto):" + linea);
					}
				}
				System.out.println(" -- MEDIAS POR IDIOMA --");
				System.out.printf("Ingles: %.2f\n", notaMediaIngles / numAlumnosIngles);
				System.out.printf("Frances: %.2f\n", notaMediaFrances / numAlumnosFrances);
				System.out.printf("Aleman: %.2f\n", notaMediaAleman / numAlumnosAleman);
				System.out.println("Alumno con nota mas alta: " + alumnoNotaMasAlta);
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
			// ESCRIBIMOS
			Path ruta = Paths.get(ficheroAprobados);
			try {
				Files.write(ruta, arrayListLineas, StandardCharsets.UTF_8);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 13 · EXAMEN (IA) Veterinaria: facturación por dueño
	// Practica: CSV ";" + validar campos + dos HashMap (total y nº de visitas)
	//           + FileWriter para el informe + dueño que más ha gastado
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/Examen_IA_Veterinaria/Ejercicio1_RA5
	// visitas.txt:     Laura Garcia;Luna;Perro;45.50   (hay "LINEA MAL FORMATEADA" y "...;abc")
	// facturacion.txt: Laura Garcia:105.7(3)
	// ============================================================
	static class Ejercicio13_Veterinaria {
		static String ficheroVisitas = "visitas.txt";
		static String ficheroFacturacion = "facturacion.txt";

		public static void main(String[] args) {
			leerVisitas();
		}

		public static void leerVisitas() {
			try {
				BufferedReader lector = new BufferedReader(new FileReader(ficheroVisitas));
				String linea;
				ArrayList<String> lineasMalFormato = new ArrayList<String>();
				HashMap<String, Double> listaClientes = new HashMap<String, Double>();
				HashMap<String, Integer> conteoClientes = new HashMap<String, Integer>();
				int contador = 0;
				String clienteNombreTop = "";
				Double clienteMontoTop = 0.0;
				String[] lineaLista = new String[4];
				while ((linea = lector.readLine()) != null) {
					lineaLista = linea.split(";");
					if (lineaLista.length != 4) {
						lineasMalFormato.add(linea);
					} else {
						try {
							Double importe = Double.parseDouble(lineaLista[3]);
							if (listaClientes.containsKey(lineaLista[0])) {
								listaClientes.put(lineaLista[0],
										listaClientes.get(lineaLista[0]) + Double.parseDouble(lineaLista[3]));
								conteoClientes.put(lineaLista[0], conteoClientes.get(lineaLista[0]) + 1);
							} else {
								conteoClientes.put(lineaLista[0], 1);
								listaClientes.put(lineaLista[0], importe);
							}
							contador++;
						} catch (Exception e) {
							lineasMalFormato.add(linea);
						}
					}
				}
				lector.close();
				try (FileWriter escritor = new FileWriter(ficheroFacturacion)) {
					System.out.println("=========Gastos por dueño=========");
					for (Map.Entry<String, Double> cliente : listaClientes.entrySet()) {
						escritor.write(cliente.getKey() + ":" + cliente.getValue() + "("
								+ conteoClientes.get(cliente.getKey()) + ")\n");
						System.out.println(cliente.getKey() + ":" + cliente.getValue() + "("
								+ conteoClientes.get(cliente.getKey()) + ")");
					}
					System.out.println("=========Resumen=========");
					System.out.println("Total de visitas validas: " + contador);
					System.out.println("Total de visitas ignoradas: " + lineasMalFormato.size());
					for (Map.Entry<String, Double> cliente : listaClientes.entrySet()) {
						if (cliente.getValue() > clienteMontoTop) {
							clienteMontoTop = cliente.getValue();
							clienteNombreTop = cliente.getKey();
						}
					}
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
				}
				System.out.println("Dueño que mas ha gastado: " + clienteNombreTop + " (" + clienteMontoTop + ")");
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
		}
	}
}

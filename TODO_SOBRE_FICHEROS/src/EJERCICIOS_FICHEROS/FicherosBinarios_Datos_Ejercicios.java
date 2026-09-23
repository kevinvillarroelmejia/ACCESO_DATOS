package EJERCICIOS_FICHEROS;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

/*
 * ============================================================
 * EJERCICIOS · FICHEROS BINARIOS DE DATOS (DataInputStream / DataOutputStream)
 * ============================================================
 * Teoría: TEORIA/FicherosBinarios_Datos.java
 * ============================================================
 */
public class FicherosBinarios_Datos_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Grabar y leer datos de distintos tipos
	// Practica: writeInt/Double/Boolean/UTF/Char y leerlos EN EL MISMO ORDEN
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/ficherosBinarios/Apuntes_FicherosBinarios
	//   (mismo ejercicio en ACCESO_DATOS/FicherosBinarios/teoria/Main y DAM_JAVA/FicherosBinarios)
	// Prueba: cambia el orden de un readX y mira qué pasa.
	// ============================================================
	static class Ejercicio1_Primitivos {
		public static void main(String[] args) {
			String fichero = "/home/alumno/binario.dat";
			escribirFichero(fichero);
			leerFichero(fichero);
		}

		public static void escribirFichero(String fichero) {
			try (DataOutputStream binario = new DataOutputStream(new FileOutputStream(fichero))) {
				binario.writeInt(42);
				binario.writeDouble(3.14159);
				binario.writeBoolean(true);
				binario.writeUTF("Hola Mundo");
				binario.writeChar('A');
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		// HAY QUE LEER EN EL MISMO ORDEN Y TIPOS QUE SE ESCRIBIERON
		public static void leerFichero(String fichero) {
			try (DataInputStream binario = new DataInputStream(new FileInputStream(fichero))) {
				int entero = binario.readInt();
				double decimal = binario.readDouble();
				boolean bool = binario.readBoolean();
				String texto = binario.readUTF();
				char caracter = binario.readChar();
				System.out.println("Entero: " + entero);
				System.out.println("Double: " + decimal);
				System.out.println("Booleano: " + bool);
				System.out.println("String: " + texto);
				System.out.println("Char: " + caracter);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 2 · Recuento de votos (4 partidos -> 4 enteros en votos.dat)
	// Practica: DataInput/OutputStream con BufferedInput/OutputStream,
	//           saber si el fichero existe intentando abrirlo,
	//           modificar = leer todo -> cambiar el array -> grabar todo
	// Origen: DAM_JAVA/CorreccionExamenOrdinaria/RA8/RA8_PersistenciaDeObjetos
	//   "ESTE EJERCICIO SE PODIA RESOLVER CON FICHEROS BINARIOS SIN OBJETOS"
	//   (la versión con un HashMap serializado está en FicherosBinarios_Objetos_Ejercicios, Ej. 8)
	// Enunciado: partidos PA, PB, PC, PD. Si votos.dat no existe se crea con 4 ceros.
	//   ayadirVotos(partido, votos) suma y muestra los votos hasta el momento.
	//   verResultados(censo) muestra el % de escrutinio.
	// ============================================================
	static class Ejercicio2_Votos {
		static String fichero = "votos.dat";
		static String[] partidos = { "PA", "PB", "PC", "PD" };

		public static void main(String[] args) {
			inicializarFichero(fichero);
			ayadirVotos("PC", 30);
			verResultados(3500);
		}

		private static void verResultados(int censo) {
			int suma = 0;
			int[] votos = leerFichero();
			for (int i = 0; i < 4; i++) {
				suma += votos[i];
			}
			double escrutinio = (double) (suma * 100) / censo;
			System.out.printf("Resultados con un %.2f%% de escrutinio:\n", escrutinio);
			verVotos(votos);
		}

		public static void ayadirVotos(String partido, int voto) {
			int[] votos = leerFichero();
			int encontrado = -1;
			for (int i = 0; i < 4 && encontrado == -1; i++) {
				if (partido.equals(partidos[i])) {
					encontrado = i;
				}
			}
			if (encontrado == -1) {
				System.out.println("Ese partido no se presenta a las elecciones");
			} else {
				votos[encontrado] += voto;
				grabarFichero(votos);
				System.out.println("Nuevos votos para el partido " + partido + ":" + voto);
				System.out.println("Votos hasta el momento: ");
				verVotos(votos);
			}
		}

		private static void verVotos(int[] votos) {
			for (int i = 0; i < 4; i++) {
				System.out.printf("Partido %s: %d votos\n", partidos[i], votos[i]);
			}
		}

		private static int[] leerFichero() {
			int[] votos = new int[4];
			try (DataInputStream f = new DataInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
				for (int i = 0; i < 4; i++) {
					votos[i] = f.readInt();
				}
			} catch (Exception e) {
				System.out.println("ERROR AL LEER EL FICHERO" + e.getMessage());
			}
			return votos;
		}

		public static void grabarFichero(int[] votos) {
			try (DataOutputStream f = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)))) {
				for (int i = 0; i < 4; i++) {
					f.writeInt(votos[i]);
				}
			} catch (Exception e) {
				System.out.println("Error al crear el fichero");
			}
		}

		public static void inicializarFichero(String fichero) {
			boolean noExiste = false;
			// si no se puede abrir para leer -> no existe
			try (DataInputStream f = new DataInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
				System.out.println("El fichero existe");
			} catch (Exception e) {
				System.out.println("El fichero no existe. Lo voy a crear...");
				noExiste = true;
			}
			if (noExiste == true) {
				try (DataOutputStream f = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)))) {
					for (int i = 0; i < 4; i++) {
						f.writeInt(0);
					}
				} catch (Exception e) {
					System.out.println("Error al crear el fichero");
				}
			}
		}
	}

	// ============================================================
	// EJERCICIO 3 · Partidas: de CSV a binario y consultas sobre el binario
	// Practica: leer un CSV y escribir cada línea como writeUTF + writeUTF + writeInt;
	//           recorrer el binario con while (binario.available() > 0)
	// Origen: DAM_JAVA/PracticandoExtraordinaria/RA8_SoloFicherosBinario/PartidasMinecraft
	// partidas.csv: KevinLopez;Minecraft;4500
	// Consultas: listar todo, partidas de un juego, mejor jugador, media de un juego.
	// ============================================================
	static class Ejercicio3_PartidasMinecraft {
		public static void main(String[] args) {
			String ficheroTextoCSV = "partidas.csv";
			String ficheroBinario = "partidas.dat";
			lecturaEscritura(ficheroTextoCSV, ficheroBinario);
			System.out.println();
			lecturaBinario(ficheroBinario);
			System.out.println();
			mostrarLineasPorJuego(ficheroBinario, "Minecraft");
			System.out.println();
			juegadorMayorPuntacion(ficheroBinario);
			System.out.println();
			calcularMediaPuntosPorJuego(ficheroBinario, "LeagueofLegends");
		}

		public static void lecturaEscritura(String ficheroTexto, String ficheroBinario) {
			String[] listaLinea = new String[3];
			try {
				// LECTURA CSV
				BufferedReader lector = new BufferedReader(new FileReader(ficheroTexto));
				String linea;
				// EL DataOutputStream FUERA DEL WHILE: LEEMOS DE UN CSV Y ESCRIBIMOS EN UN BINARIO
				try (DataOutputStream binario = new DataOutputStream(new FileOutputStream(ficheroBinario))) {
					while ((linea = lector.readLine()) != null) {
						listaLinea = linea.split(";");
						binario.writeUTF(listaLinea[0]);
						binario.writeUTF(listaLinea[1]);
						int puntuacion = Integer.parseInt(listaLinea[2]);
						binario.writeInt(puntuacion);
					}
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
				}
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
		}

		public static void lecturaBinario(String ficheroBinario) {
			try (DataInputStream binario = new DataInputStream(new FileInputStream(ficheroBinario))) {
				while (binario.available() > 0) { // bytes que quedan por leer
					String nombreJugador = binario.readUTF();
					String juego = binario.readUTF();
					int puntos = binario.readInt();
					System.out.println("NOMBRE JUGADOR: " + nombreJugador + " " + puntos + " " + juego);
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static void mostrarLineasPorJuego(String ficheroBinario, String juego) {
			try (DataInputStream binario = new DataInputStream(new FileInputStream(ficheroBinario))) {
				while (binario.available() > 0) {
					String nombreJugador = binario.readUTF();
					String videoJuego = binario.readUTF();
					int puntos = binario.readInt();
					if (videoJuego.equalsIgnoreCase(juego)) {
						System.out.println("NOMBRE JUGADOR: " + nombreJugador + " " + puntos + " " + juego);
					}
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static void juegadorMayorPuntacion(String ficheroBinario) {
			try (DataInputStream binario = new DataInputStream(new FileInputStream(ficheroBinario))) {
				int puntosMasAltos = 0;
				String mejorJugador = "";
				String juegoMejorjugador = "";
				while (binario.available() > 0) {
					String nombreJugador = binario.readUTF();
					String videoJuego = binario.readUTF();
					int puntos = binario.readInt();
					if (puntos > puntosMasAltos) {
						puntosMasAltos = puntos;
						mejorJugador = nombreJugador;
						juegoMejorjugador = videoJuego;
					}
				}
				System.out.println("-- Mejor jugador --");
				System.out.println(mejorJugador + "(" + juegoMejorjugador + ") - " + puntosMasAltos);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static void calcularMediaPuntosPorJuego(String ficheroBinario, String nombreJuego) {
			try (DataInputStream binario = new DataInputStream(new FileInputStream(ficheroBinario))) {
				int contadorJuego = 0;
				double mediaPuntos = 0;
				while (binario.available() > 0) {
					String nombreJugador = binario.readUTF();
					String videoJuego = binario.readUTF();
					int puntos = binario.readInt();
					if (videoJuego.equalsIgnoreCase(nombreJuego)) {
						contadorJuego++;
						mediaPuntos = mediaPuntos + (double) puntos;
					}
				}
				if (contadorJuego == 0) {
					System.out.println("No hay juegos con este nombre");
				} else {
					mediaPuntos = mediaPuntos / contadorJuego;
					System.out.printf("\nMedia de puntos del juego %s %.2f", nombreJuego, mediaPuntos);
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
}

package TEORIA_FICHEROS;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * ============================================================
 * TEORÍA 4 · FICHEROS BINARIOS DE DATOS (DataInputStream / DataOutputStream)
 * ============================================================
 *
 * Origen:
 *   ACCESO_DATOS / FicherosBinarios/teoria/Main  (escribirFicheroBinario, leerFicheroBinario)
 *   DAM_JAVA     / FicherosBinarios, TodoSobreFicheros/ficherosBinarios
 *   DAM_JAVA     / UT7_Ficheros_RA5/ficherosBinarios/Apuntes_FicherosBinarios
 *   DAM_JAVA     / CorreccionExamenOrdinaria/RA8 (votos)
 *   DAM_JAVA     / PracticandoExtraordinaria/RA8_SoloFicherosBinario (available)
 *
 * ¿QUÉ ES UN FICHERO BINARIO? (FicherosBinarios/teoria/Main)
 *   - Se guarda en binario, no como texto: no es comprensible para el usuario
 *     y si alguien lo edita puede estropearlo.
 *   - Se usa cuando no queremos que un usuario externo lo modifique.
 *   - Ocupa menos espacio y la lectura/escritura es más rápida.
 *   - Al grabar SIEMPRE hay que indicar el TIPO de dato (writeInt, writeDouble...).
 *   - Extensión: .dat o .bin
 *
 * OUTPUT / INPUT (desde el punto de vista de la persona):
 *   OUTPUT = los datos salen de mí al fichero  -> ESCRIBIR -> ...OutputStream
 *   INPUT  = los datos entran del fichero a mí -> LEER     -> ...InputStream
 *
 * ACCESO: secuencial (de principio a fin, en orden).
 *
 * ============================================================
 * CÓMO SE ABRE
 * ============================================================
 *   ESCRIBIR: new DataOutputStream(new FileOutputStream(fichero))
 *   LEER:     new DataInputStream(new FileInputStream(fichero))
 *
 *   Con buffer (CorreccionExamenOrdinaria/RA8_PersistenciaDeObjetos):
 *   new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)))
 *   new DataInputStream(new BufferedInputStream(new FileInputStream(fichero)))
 *
 *   FileOutputStream(fichero) SOBRESCRIBE el fichero (lo crea si no existe).
 *   Siempre dentro de try-with-resources (se cierra solo).
 */
public class FicherosBinarios_Datos {

	/*
	 * ============================================================
	 * MÉTODOS DE ESCRITURA (DataOutputStream)
	 * ============================================================
	 *
	 *   MÉTODO          QUÉ RECIBE     QUÉ ESCRIBE
	 *   --------------  -------------  -----------------------------
	 *   writeInt(n)     int            un entero
	 *   writeDouble(d)  double         un decimal
	 *   writeBoolean(b) boolean        true/false
	 *   writeChar(c)    char           un carácter
	 *   writeUTF(s)     String         un texto (guarda también su longitud)
	 *
	 *   No devuelven nada. Lanzan excepción si hay problema -> try/catch.
	 *   "Siempre que se escriba hay que especificar el tipo de dato que se escribe"
	 * ============================================================
	 */
	public static void escribirFicheroBinario(String fichero) {
		// Abrimos un fichero para escritura: los datos salen de mí
		try (DataOutputStream binario = new DataOutputStream(new FileOutputStream(fichero))) {
			binario.writeInt(3456);
			binario.writeDouble(3.1415);
			binario.writeBoolean(false);
			binario.writeChar('X');
			binario.writeUTF("Hola mundo binario");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * MÉTODOS DE LECTURA (DataInputStream)
	 * ============================================================
	 *
	 *   readInt()  readDouble()  readBoolean()  readChar()  readUTF()
	 *   No reciben nada. Devuelven el dato del tipo indicado.
	 *
	 *   ¡MUY IMPORTANTE! Leer en el MISMO ORDEN y con los MISMOS TIPOS
	 *   con los que se escribió.
	 *
	 *   "Si intentamos leer algún tipo de dato más que no existe o no está
	 *    en el fichero nos genera una excepción."
	 * ============================================================
	 */
	public static void leerFicheroBinario(String fichero) {
		// Para la lectura es Input: los datos entran en mí
		try (DataInputStream binario = new DataInputStream(new FileInputStream(fichero))) {
			System.out.println(binario.readInt());
			System.out.println(binario.readDouble());
			System.out.println(binario.readBoolean());
			System.out.println(binario.readChar());
			System.out.println(binario.readUTF());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * MÉTODO
	 * ============================================================
	 *
	 * Nombre: available()
	 *
	 * Para qué sirve: saber cuántos bytes quedan por leer.
	 *
	 * Qué recibe: nada.
	 *
	 * Qué devuelve: int con el número de bytes que quedan.
	 *
	 * Cómo lo hemos utilizado: para RECORRER un binario con muchos registros
	 *   cuando no sabemos cuántos hay (PartidasMinecraft):
	 *     while (binario.available() > 0) { leer un registro completo }
	 *
	 * ============================================================
	 */
	public static void recorrerConAvailable(String ficheroBinario) {
		try (DataInputStream binario = new DataInputStream(new FileInputStream(ficheroBinario))) {
			while (binario.available() > 0) { // bytes que quedan
				String nombreJugador = binario.readUTF();
				String juego = binario.readUTF();
				int puntos = binario.readInt();
				System.out.println(nombreJugador + " " + puntos + " " + juego);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * RECORRER CUANDO SABEMOS CUÁNTOS DATOS HAY
	 * ============================================================
	 * Si el número de datos es fijo (4 partidos -> 4 int) basta un for.
	 * (CorreccionExamenOrdinaria/RA8_PersistenciaDeObjetos)
	 * ============================================================
	 */
	public static int[] leerVotos(String fichero) {
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

	public static void grabarVotos(String fichero, int[] votos) {
		try (DataOutputStream f = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)))) {
			for (int i = 0; i < 4; i++) {
				f.writeInt(votos[i]);
			}
		} catch (Exception e) {
			System.out.println("Error al crear el fichero");
		}
	}

	/*
	 * ============================================================
	 * ¿CÓMO SE MODIFICA?
	 * ============================================================
	 *  Igual que el texto: es secuencial. Se leen todos los datos a memoria
	 *  (array), se cambian y se vuelve a grabar todo (ayadirVotos en RA8).
	 *
	 * ============================================================
	 * ¿CÓMO SABER SI EL FICHERO EXISTE? (truco de RA8)
	 * ============================================================
	 *  Intentar abrirlo para leer: si salta la excepción, no existe y lo creamos.
	 * ============================================================
	 */
	public static void inicializarFichero(String fichero) {
		boolean noExiste = false;
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

	/*
	 * ============================================================
	 * EXCEPCIONES
	 * ============================================================
	 *  catch (Exception e) en todos los métodos.
	 *  Salta excepción si: el fichero no existe al leer, se lee más de lo que hay,
	 *  o se lee con un tipo distinto al que se grabó.
	 *
	 * ============================================================
	 * DIFERENCIA CON TEXTO Y CON OBJETOS
	 * ============================================================
	 *  Texto      -> legible, todo son String, se lee por líneas.
	 *  Data...    -> binario, dato a dato con su tipo (int, double, UTF...).
	 *  Object...  -> binario, objetos enteros (ver FicherosBinarios_Objetos).
	 * ============================================================
	 */
	public static void main(String[] args) {
		String fichero = "/home/alumno/binario.dat";
		escribirFicheroBinario(fichero);
		leerFicheroBinario(fichero);
	}
}

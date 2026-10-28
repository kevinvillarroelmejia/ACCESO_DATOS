package EJERCICIOS_FICHEROS;

import java.io.File;
import java.io.RandomAccessFile;

/*
 * ============================================================
 * ACCESO ALEATORIO - EJEMPLO TONTO: CALCULAR REGISTROS
 * ============================================================
 * Cada REGISTRO = 1 pokémon = codigo (int) + peso (double)
 *   int    -> 4 bytes
 *   double -> 8 bytes
 *   TOTAL  -> 12 bytes por pokémon (SIEMPRE el mismo tamaño)
 *
 * Así queda el fichero con 3 pokémon (36 bytes):
 *
 *   byte:  0      4          12     16         24     28         36
 *          |codigo| peso     |codigo| peso     |codigo| peso     |
 *          |  25  |   6.0    | 152  |   6.4    | 155  |   7.9    |
 *          |<--- registro 1 ->|<--- registro 2 ->|<--- registro 3 ->|
 *
 * LAS 3 CUENTAS:
 *   ¿Cuántos registros hay?      length() / TAMANYO_REGISTRO    -> 36 / 12 = 3
 *   ¿Dónde empieza el registro N? (N - 1) * TAMANYO_REGISTRO    -> registro 2: 1 * 12 = 12
 *   ¿Dónde está el peso del N?    inicio del N + 4 (saltar el int) -> registro 3: 24 + 4 = 28
 * ============================================================
 */
public class AccesoAletorio2 {

	public static final String FICHERO = "src" + File.separator + "EJERCICIOS_FICHEROS" + File.separator
			+ "pokemon_aleatorio.dat";

	public static final int TAMANYO_CODIGO = 4;                                // int
	public static final int TAMANYO_PESO = 8;                                  // double
	public static final int TAMANYO_REGISTRO = TAMANYO_CODIGO + TAMANYO_PESO;  // 12

	// [MAIN] crear 3 registros → contar → leer uno → modificar uno → leer todos
	public static void main(String[] args) {
		try {
			crearFichero();
			System.out.println("Registros: " + contarRegistros());   // 36 / 12 = 3
			leerRegistro(2);                                          // va al byte 12
			modificarPeso(3, 8.0);                                    // va al byte 28
			leerTodos();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// [ESCRIBIR] crea el fichero con 3 pokémon (codigo + peso), uno detrás de otro
	public static void crearFichero() throws Exception {
		new File(FICHERO).delete();                                   // empezar de cero en cada prueba
		try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "rw")) {
			raf.writeInt(25);      raf.writeDouble(6.0);              // registro 1: bytes 0-11
			raf.writeInt(152);     raf.writeDouble(6.4);              // registro 2: bytes 12-23
			raf.writeInt(155);     raf.writeDouble(7.9);              // registro 3: bytes 24-35
			System.out.println("Tamaño del fichero: " + raf.length() + " bytes");   // 36
		}
	}

	// [CONTAR] nº de registros = tamaño del fichero / tamaño de un registro
	public static long contarRegistros() throws Exception {
		long total;
		try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "r")) {
			total = raf.length() / TAMANYO_REGISTRO;
		}
		return total;
	}

	// [LEER UNO] salta directamente al registro N y lo lee (sin pasar por los anteriores)
	public static void leerRegistro(int numero) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "r")) {
			long posicion = (numero - 1) * TAMANYO_REGISTRO;          // registro 2 → (2-1)*12 = 12
			if (numero < 1 || posicion >= raf.length()) {
				System.out.println("El registro " + numero + " no existe");
			} else {
				raf.seek(posicion);                                   // cursor al byte 12
				int codigo = raf.readInt();                           // lee 4 bytes → cursor en 16
				double peso = raf.readDouble();                       // lee 8 bytes → cursor en 24
				System.out.println("Registro " + numero + " (byte " + posicion + "): " + codigo + " - " + peso);
			}
		}
	}

	// [MODIFICAR] cambia SOLO el peso del registro N (el código no se toca)
	public static void modificarPeso(int numero, double nuevoPeso) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "rw")) {
			long posicion = (numero - 1) * TAMANYO_REGISTRO + TAMANYO_CODIGO;   // 24 + 4 = 28
			raf.seek(posicion);
			raf.writeDouble(nuevoPeso);                               // machaca los 8 bytes del peso
			System.out.println("Peso del registro " + numero + " cambiado a " + nuevoPeso + " (byte " + posicion + ")");
		}
	}

	// [LEER TODOS] recorre los registros uno a uno: el registro i empieza en i * 12
	public static void leerTodos() throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "r")) {
			long total = raf.length() / TAMANYO_REGISTRO;
			for (int i = 0; i < total; i++) {
				raf.seek(i * TAMANYO_REGISTRO);                       // 0, 12, 24
				int codigo = raf.readInt();
				double peso = raf.readDouble();
				System.out.println("Registro " + (i + 1) + ": " + codigo + " - " + peso);
			}
		}
	}
}
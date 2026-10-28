package c03_TXT_a_BBDD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 * COMBINACIÓN 03: TXT → BBDD
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.txt
 *          una línea por pokémon:  25, Pikachu, 6.0
 * SALIDA:  tabla pokemon de pokemondb (INSERT)
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER TXT: SEPARADOR y el orden de los campos[ ] en leerTXT / escribirTXT
 *   - ESCRIBIR BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_TXT_a_BBDD {

	public static final String PAQUETE = "c03_TXT_a_BBDD";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_TXT = "src" + File.separator + PAQUETE + File.separator + "pokemon.txt";

	// CAMBIAR: lo que separa los datos en cada línea del TXT
	public static final String SEPARADOR = ",";

	// CAMBIAR: conexión según dónde estés
	// CASA:  jdbc:mysql://localhost:3306/pokemondb    root   / abc123
	// CLASE: jdbc:mysql://172.17.0.1:3306/pokemondb   alumno / abc123
	public static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";

	// [MAIN] TXT → BBDD
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerTXT(FICHERO_TXT);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			escribirBBDD(conexion, listaPokemon);      // 2. ESCRIBIR el destino
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		// Para repetir la prueba, borra lo insertado en MySQL: DELETE FROM pokemon WHERE numero_pokedex > 151;
	}

	// ============================ LEER TXT ============================

	// [LEER TXT] cada línea "codigo, nombre, peso" → lista de pokémon
	public static List<Pokemon> leerTXT(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea = lector.readLine();                      // 1ª lectura ANTES del while
			while (linea != null) {
				if (!linea.trim().isEmpty()) {                       // salta líneas en blanco
					String[] campos = linea.split(SEPARADOR);
					int codigo = Integer.parseInt(campos[0].trim());       // CAMBIAR: posición de cada dato
					String nombre = campos[1].trim();
					double peso = Double.parseDouble(campos[2].trim());
					listaPokemon.add(new Pokemon(codigo, nombre, peso));
				}
				linea = lector.readLine();                           // releer al FINAL (si no, bucle infinito)
			}
		} catch (Exception e) {
			System.out.println("Error al leer el TXT: " + e.getMessage());
		}
		return listaPokemon;
	}

	// ============================ ESCRIBIR BBDD ============================

	// [ESCRIBIR BBDD] lista de pokémon → INSERT en la tabla pokemon (salta los que ya existen)
	public static void escribirBBDD(Connection conexion, List<Pokemon> listaPokemon) throws SQLException {
		int filasInsertadas = 0;
		// CAMBIAR: tabla y columnas. altura es NOT NULL en pokemondb y no la tenemos → se guarda 0
		PreparedStatement insert = conexion.prepareStatement(
				"INSERT INTO pokemon (numero_pokedex, nombre, peso, altura) VALUES (?, ?, ?, ?)");
		for (Pokemon pokemon : listaPokemon) {
			if (existePokemon(conexion, pokemon.getCodigo())) {
				System.out.println("Ya existe, no se inserta: " + pokemon);
			} else {
				insert.setInt(1, pokemon.getCodigo());                // los ? se numeran de izquierda a derecha
				insert.setString(2, pokemon.getNombre());
				insert.setDouble(3, pokemon.getPeso());
				insert.setDouble(4, 0);
				filasInsertadas = filasInsertadas + insert.executeUpdate();   // executeUpdate → nº de filas
				System.out.println("Insertado: " + pokemon);
			}
		}
		System.out.println("Filas insertadas: " + filasInsertadas);
	}

	// [COMPROBAR] ¿existe ya ese numero_pokedex en la tabla? → true/false
	public static boolean existePokemon(Connection conexion, int codigo) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"SELECT numero_pokedex FROM pokemon WHERE numero_pokedex = ?");
		query.setInt(1, codigo);
		ResultSet resultado = query.executeQuery();
		boolean existe = resultado.next();                          // hay fila → existe
		return existe;
	}

	// ============================ MOSTRAR ============================

	// [MOSTRAR] imprime la lista leída, para comprobar que la lectura ha ido bien
	public static void mostrarLista(List<Pokemon> listaPokemon) {
		System.out.println("Pokémon leídos: " + listaPokemon.size());
		for (Pokemon pokemon : listaPokemon) {
			System.out.println(" - " + pokemon);
		}
	}
}

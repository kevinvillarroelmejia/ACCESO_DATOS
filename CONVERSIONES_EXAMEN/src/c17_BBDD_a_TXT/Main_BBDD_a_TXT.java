package c17_BBDD_a_TXT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 * COMBINACIÓN 17: BBDD → TXT
 * ---------------------------------------------------------------------
 * ENTRADA: tabla pokemon de pokemondb
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * SALIDA:  pokemon.txt
 *          una línea por pokémon:  25, Pikachu, 6.0
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - ESCRIBIR TXT: SEPARADOR y el orden de los campos[ ] en leerTXT / escribirTXT
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_BBDD_a_TXT {

	public static final String PAQUETE = "c17_BBDD_a_TXT";

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

	// [MAIN] BBDD → TXT
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			listaPokemon = leerBBDD(conexion);          // 1. LEER el origen → lista
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		mostrarLista(listaPokemon);
		escribirTXT(listaPokemon, FICHERO_TXT);      // 2. ESCRIBIR el destino
	}

	// ============================ LEER BBDD ============================

	// [LEER BBDD] SELECT de la tabla pokemon → lista de pokémon
	public static List<Pokemon> leerBBDD(Connection conexion) throws SQLException {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		// CAMBIAR: tabla y columnas (añade WHERE ... = ? si el enunciado filtra)
		PreparedStatement query = conexion.prepareStatement(
				"SELECT numero_pokedex, nombre, peso FROM pokemon ORDER BY numero_pokedex");
		ResultSet resultado = query.executeQuery();
		while (resultado.next()) {                                        // una vuelta por fila
			int codigo = resultado.getInt("numero_pokedex");
			String nombre = resultado.getString("nombre");
			double peso = resultado.getDouble("peso");
			listaPokemon.add(new Pokemon(codigo, nombre, peso));
		}
		return listaPokemon;
	}

	// ============================ ESCRIBIR TXT ============================

	// [ESCRIBIR TXT] lista de pokémon → una línea "codigo, nombre, peso" por pokémon
	public static void escribirTXT(List<Pokemon> listaPokemon, String fichero) {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero))) {   // sobrescribe el fichero
			for (Pokemon pokemon : listaPokemon) {
				escritor.write(pokemon.getCodigo() + SEPARADOR + " " + pokemon.getNombre() + SEPARADOR + " "
						+ pokemon.getPeso());                                // CAMBIAR: formato de la línea
				escritor.newLine();
			}
			System.out.println("TXT creado: " + fichero);
		} catch (Exception e) {
			System.out.println("Error al escribir el TXT: " + e.getMessage());
		}
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

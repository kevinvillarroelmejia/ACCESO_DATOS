package c16_BBDD_a_JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * =====================================================================
 * COMBINACIÓN 16: BBDD → JSON
 * ---------------------------------------------------------------------
 * ENTRADA: tabla pokemon de pokemondb
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * SALIDA:  pokemon.json
 *          { "pokemons": [ { "codigo": 25, "nombre": "Pikachu", "peso": 6.0 }, ... ] }
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - ESCRIBIR JSON: @SerializedName de Pokedex (raíz) y los atributos de Pokemon (claves del JSON)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_BBDD_a_JSON {

	public static final String PAQUETE = "c16_BBDD_a_JSON";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_JSON = "src" + File.separator + PAQUETE + File.separator + "pokemon.json";

	// CAMBIAR: conexión según dónde estés
	// CASA:  jdbc:mysql://localhost:3306/pokemondb    root   / abc123
	// CLASE: jdbc:mysql://172.17.0.1:3306/pokemondb   alumno / abc123
	public static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";

	// [MAIN] BBDD → JSON
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			listaPokemon = leerBBDD(conexion);          // 1. LEER el origen → lista
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		mostrarLista(listaPokemon);
		escribirJSON(listaPokemon, FICHERO_JSON);      // 2. ESCRIBIR el destino
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

	// ============================ ESCRIBIR JSON ============================

	// [ESCRIBIR JSON] lista de pokémon → JSON con raíz "pokemons" (clase contenedora Pokedex)
	public static void escribirJSON(List<Pokemon> listaPokemon, String fichero) {
		Pokedex pokedex = new Pokedex(listaPokemon);                // la lista dentro de la raíz
		try (Writer escritor = new FileWriter(fichero)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();   // JSON en vertical
			gson.toJson(pokedex, escritor);                         // SIN raíz sería: gson.toJson(listaPokemon, escritor)
			System.out.println("JSON creado: " + fichero);
		} catch (Exception e) {
			System.out.println("Error al escribir el JSON: " + e.getMessage());
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

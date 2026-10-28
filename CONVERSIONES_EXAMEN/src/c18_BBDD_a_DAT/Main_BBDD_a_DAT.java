package c18_BBDD_a_DAT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 * COMBINACIÓN 18: BBDD → DAT
 * ---------------------------------------------------------------------
 * ENTRADA: tabla pokemon de pokemondb
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * SALIDA:  pokemon.dat
 *          un ArrayList<Pokemon> guardado con writeObject
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - ESCRIBIR DAT: nada si el .dat lo creas tú; si te lo dan, la clase tiene que llamarse igual y estar en el mismo paquete
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_BBDD_a_DAT {

	public static final String PAQUETE = "c18_BBDD_a_DAT";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_DAT = "src" + File.separator + PAQUETE + File.separator + "pokemon.dat";

	// CAMBIAR: conexión según dónde estés
	// CASA:  jdbc:mysql://localhost:3306/pokemondb    root   / abc123
	// CLASE: jdbc:mysql://172.17.0.1:3306/pokemondb   alumno / abc123
	public static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";

	// [MAIN] BBDD → DAT
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			listaPokemon = leerBBDD(conexion);          // 1. LEER el origen → lista
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		mostrarLista(listaPokemon);
		escribirDAT(listaPokemon, FICHERO_DAT);      // 2. ESCRIBIR el destino
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

	// ============================ ESCRIBIR DAT ============================

	// [ESCRIBIR DAT] lista de pokémon → binario de objetos (writeObject de la lista entera)
	public static void escribirDAT(List<Pokemon> listaPokemon, String fichero) {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(new ArrayList<Pokemon>(listaPokemon));   // ArrayList es Serializable
			System.out.println("DAT creado: " + fichero);
		} catch (Exception e) {
			System.out.println("Error al escribir el DAT: " + e.getMessage());
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

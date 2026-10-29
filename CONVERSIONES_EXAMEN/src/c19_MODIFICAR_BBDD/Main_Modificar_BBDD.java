package c19_MODIFICAR_BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * MODIFICAR LA BBDD: INSERT, UPDATE y DELETE en la tabla pokemon
 * Los tres se ejecutan con executeUpdate() → devuelve las filas afectadas (0 = no encontró nada)
 */
public class Main_Modificar_BBDD {

	// CAMBIAR: conexión según dónde estés
	// CASA:  jdbc:mysql://localhost:3306/pokemondb    root   / abc123
	// CLASE: jdbc:mysql://172.17.0.1:3306/pokemondb   alumno / abc123
	public static final String URL = "jdbc:mysql://172.17.0.1:3306/pokemondb";
	public static final String USUARIO = "alumno";
	public static final String PASSWORD = "abc123";

	// [MAIN] prueba de INSERT → UPDATE → DELETE
	public static void main(String[] args) {
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			if (existePokemon(conexion, 152)) {
				System.out.println("El 152 ya existe, no se inserta");
			} else {
				insertarPokemon(conexion, 152, "Chikorita", 6.4, 0.9);
			}
			modificarPeso(conexion, 152, 7.0);
			borrarPokemon(conexion, 152);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// [COMPROBAR] ¿existe ese numero_pokedex? → true/false
	public static boolean existePokemon(Connection conexion, int numero) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"SELECT numero_pokedex FROM pokemon WHERE numero_pokedex = ?");
		query.setInt(1, numero);
		ResultSet resultado = query.executeQuery();
		boolean existe = resultado.next();
		return existe;
	}

	// [INSERT] añade un pokémon a la tabla → filas insertadas
	public static int insertarPokemon(Connection conexion, int numero, String nombre, double peso, double altura) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"INSERT INTO pokemon (numero_pokedex, nombre, peso, altura) VALUES (?, ?, ?, ?)");
		query.setInt(1, numero);
		query.setString(2, nombre);
		query.setDouble(3, peso);
		query.setDouble(4, altura);
		int filas = query.executeUpdate();
		System.out.println("Insertados: " + filas);
		return filas;
	}

	// [UPDATE] cambia el peso de un pokémon por su número → filas modificadas
	public static int modificarPeso(Connection conexion, int numero, double nuevoPeso) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"UPDATE pokemon SET peso = ? WHERE numero_pokedex = ?");
		query.setDouble(1, nuevoPeso);
		query.setInt(2, numero);
		int filas = query.executeUpdate();
		System.out.println("Modificados: " + filas);
		return filas;
	}

	// [DELETE] borra un pokémon por su número (antes, sus filas en las tablas hijas) → filas borradas
	public static int borrarPokemon(Connection conexion, int numero) throws SQLException {
		PreparedStatement borrarTipos = conexion.prepareStatement(
				"DELETE FROM pokemon_tipo WHERE numero_pokedex = ?");
		borrarTipos.setInt(1, numero);
		borrarTipos.executeUpdate();

		PreparedStatement borrarEvoluciones = conexion.prepareStatement(
				"DELETE FROM evoluciona_de WHERE pokemon_evolucionado = ? OR pokemon_origen = ?");
		borrarEvoluciones.setInt(1, numero);
		borrarEvoluciones.setInt(2, numero);
		borrarEvoluciones.executeUpdate();

		PreparedStatement query = conexion.prepareStatement(
				"DELETE FROM pokemon WHERE numero_pokedex = ?");
		query.setInt(1, numero);
		int filas = query.executeUpdate();
		System.out.println("Borrados: " + filas);
		return filas;
	}
}
package E6_TiposPokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main_PokemonPorTipo {

	public static void main(String[] args) {

		String usuario = "root";
		String password = "abc123";

		// si estoy en casa localhost:3306
		// si estoy en clase 172.17.0.1:3306
		String server = "jdbc:mysql://localhost:3306/pokemondb";

		try (Connection conexion = DriverManager.getConnection(server, usuario, password)) {
			pokemonPorTipo(conexion, "dragon");
		} catch (SQLException e) {
			e.getMessage();
		}

	}

	public static void pokemonPorTipo(Connection conexion, String tipo) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"SELECT pokemon.numero_pokedex,pokemon.nombre,pokemon.peso,pokemon.altura FROM pokemon JOIN pokemon_tipo ON pokemon_tipo.numero_pokedex = pokemon.numero_pokedex JOIN tipo ON tipo.id_tipo = pokemon_tipo.id_tipo WHERE tipo.nombre = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setString(1, tipo);
		ResultSet resultadoTipo = query.executeQuery();// para saber las lineas
		resultadoTipo.last();

		if (!existeTipo(conexion, tipo)) { //COMPRUEBA SI EXISTE EL TIPO
			System.out.println(tipo+" no es un tipo reconocido en esta pokedex");
		}else {
			if (resultadoTipo.getRow() == 0) {//COMPRUEBA QUE DEL TIPO QUE EXISTE SI HAY LINEAS
				System.out.println("No existe ninugn pokemon de ese tipo");
			} else {
				resultadoTipo.beforeFirst();// volvemos al principio
				System.out.println("Los pokemon de tipo Dragon son: ");
				while (resultadoTipo.next()) {// devuelve un boolean
					System.out.println(resultadoTipo.getInt("numero_pokedex") + " - " + resultadoTipo.getString("nombre"));
					System.out.println(
							"Peso: " + resultadoTipo.getDouble("peso") + " Altura: " + resultadoTipo.getDouble("altura"));
					System.out.println("Tipos: "+tiposDePokemon(conexion, resultadoTipo.getInt("numero_pokedex"))); //sus tipos
				}
			}
		}
	}

	// true o false si el tipo existe
	public static boolean existeTipo(Connection conexion, String tipo) throws SQLException {
		boolean existe = false;
		PreparedStatement query = conexion.prepareStatement("select id_tipo FROM tipo WHERE nombre=?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setString(1, tipo);
		ResultSet resultadoTipo = query.executeQuery();
		resultadoTipo.last();
		if (resultadoTipo.getRow() > 0) {
			existe = true;
		}
		return existe;
	}

	public static String tiposDePokemon(Connection conexion, int numeroPokex) throws SQLException {
		String tipos = "";
		PreparedStatement query = conexion.prepareStatement(
				"SELECT tipo.nombre FROM tipo JOIN pokemon_tipo ON pokemon_tipo.id_tipo = tipo.id_tipo WHERE pokemon_tipo.numero_pokedex = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setInt(1, numeroPokex);
		ResultSet resultadoTipo = query.executeQuery();
		while (resultadoTipo.next()) {
			tipos = tipos + resultadoTipo.getString("nombre");
			if(!resultadoTipo.isLast()) { //CONTEMPLAMOS LA COMA AL FINAL
				tipos=tipos+", ";
			}
		}
		return tipos;
	}

}

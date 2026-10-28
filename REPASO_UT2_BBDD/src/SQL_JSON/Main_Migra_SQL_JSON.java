package SQL_JSON;

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
import com.google.gson.JsonObject;


public class Main_Migra_SQL_JSON {
	
	
	public static String FICHERO_JSON="src"+File.separator+"SQL_JSON"+File.separator+"basesPokemon.json";
	public static void main(String[] args) {

		// si estoy en casa localhost:3306
		// si estoy en clase 172.17.0.1:3306
		try (Connection conexion = Pool_ConexionesSimultaneas.getConnection()) {
			List<Pokemon> listaPokemon=pokemonPorTipo(conexion,"dragon");
			cargarJSON(listaPokemon);
			
		} catch (SQLException e) {
			e.getMessage();
		}		
	}
	
	// [ESCRIBIR JSON] lista de pokémon → JSON
	public static void cargarJSON(List<Pokemon> listaPokemon) {
		try (Writer escritor = new FileWriter(FICHERO_JSON)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonObject raiz = new JsonObject();                   //PARA CREAR LA RAIZ 
			raiz.add("pokemons", gson.toJsonTree(listaPokemon));  
			
			gson.toJson(raiz, escritor);
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}
	
	// [LEER BD] devuelve los pokémon de un tipo con sus tipos
	public static List<Pokemon> pokemonPorTipo(Connection conexion, String tipo) throws SQLException {
		List<Pokemon> listaPokemon=new ArrayList<Pokemon>();
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
					int numPokedex=resultadoTipo.getInt("numero_pokedex");
					String nombre=resultadoTipo.getString("nombre");
					double peso=resultadoTipo.getDouble("peso");
					double altura=resultadoTipo.getDouble("altura");
					String tipo1;
					String tipo2;
					ArrayList<String> listaTipos=tiposDePokemon(conexion, numPokedex);
					if(listaTipos.size()==1) {
						Pokemon pokemon=new Pokemon(numPokedex, nombre, peso, altura, listaTipos.get(0));
						listaPokemon.add(pokemon);
					}else {
						Pokemon pokemon=new Pokemon(numPokedex, nombre, peso, altura, listaTipos.get(0),listaTipos.get(1));
						listaPokemon.add(pokemon);
					}
				}
			}
		}
		return listaPokemon;
	}
	// [COMPROBAR] ¿existe el tipo? → true/false
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
	// [DETALLE BD] devuelve los tipos de un pokémon
	public static ArrayList<String> tiposDePokemon(Connection conexion, int numeroPokex) throws SQLException {
		ArrayList<String> listaTipos = new  ArrayList<String>();
		PreparedStatement query = conexion.prepareStatement(
				"SELECT tipo.nombre FROM tipo JOIN pokemon_tipo ON pokemon_tipo.id_tipo = tipo.id_tipo WHERE pokemon_tipo.numero_pokedex = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setInt(1, numeroPokex);
		ResultSet resultadoTipo = query.executeQuery();
		while (resultadoTipo.next()) {
			 listaTipos.add(resultadoTipo.getString("nombre"));//consigiendo el nombre de los tipos
		}
		return listaTipos;
	}
	
	
		

}

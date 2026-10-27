package pokemon_TXT_JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Main_pokemon_TXT_JSON {

	public static final String FICHERO_TXT = "src" + File.separator + "pokemon_TXT_JSON" + File.separator
			+ "pokemon.txt";
	public static final String FICHERO_JSON = "src" + File.separator + "pokemon_TXT_JSON" + File.separator
			+ "pokemon.json";

	public static void main(String[] args) {

		List<Pokemon> listaPokemon = leerPokemonTXT(FICHERO_TXT);
		cargarJSON(listaPokemon);

	}

	public static void cargarJSON(List<Pokemon> listaArticulos) {
		try (Writer escritor = new FileWriter(FICHERO_JSON)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(listaArticulos, escritor);

		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}

	public static List<Pokemon> leerPokemonTXT(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea;

			String idPokemon = "";
			String nombrePokemon = "";
			String peso = "";
			String altura = "";
			String tipo1 = "";
			String tipo2 = "";

			while ((linea = lector.readLine()) != null) {
				String[] lineaLista = linea.trim().split(",");
				if (lineaLista.length == 5) {
					idPokemon = lineaLista[0].trim();
					nombrePokemon = lineaLista[1].trim();
					peso = lineaLista[2].trim();
					altura = lineaLista[3].trim();
					tipo1 = lineaLista[4].trim();
					Pokemon pokemon = new Pokemon(idPokemon, nombrePokemon, peso, altura, tipo1);
					listaPokemon.add(pokemon);
				} else if (lineaLista.length == 6) {
					idPokemon = lineaLista[0].trim();
					nombrePokemon = lineaLista[1].trim();
					peso = lineaLista[2].trim();
					altura = lineaLista[3].trim();
					tipo1 = lineaLista[4].trim();
					tipo2 = lineaLista[5].trim();
					Pokemon pokemon = new Pokemon(idPokemon, nombrePokemon, peso, altura, tipo1, tipo2);
					listaPokemon.add(pokemon);
				}
			}
		} catch (IOException e) {
			e.getStackTrace();
		}
		return listaPokemon;
	}

}

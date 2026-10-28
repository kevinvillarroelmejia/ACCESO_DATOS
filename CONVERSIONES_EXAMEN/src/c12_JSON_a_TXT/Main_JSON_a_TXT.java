package c12_JSON_a_TXT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/*
 * =====================================================================
 * COMBINACIÓN 12: JSON → TXT
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.json
 *          { "pokemons": [ { "codigo": 25, "nombre": "Pikachu", "peso": 6.0 }, ... ] }
 * SALIDA:  pokemon.txt
 *          una línea por pokémon:  25, Pikachu, 6.0
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER JSON: @SerializedName de Pokedex (raíz) y los atributos de Pokemon (claves del JSON)
 *   - ESCRIBIR TXT: SEPARADOR y el orden de los campos[ ] en leerTXT / escribirTXT
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_JSON_a_TXT {

	public static final String PAQUETE = "c12_JSON_a_TXT";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_JSON = "src" + File.separator + PAQUETE + File.separator + "pokemon.json";
	public static final String FICHERO_TXT = "src" + File.separator + PAQUETE + File.separator + "pokemon.txt";

	// CAMBIAR: lo que separa los datos en cada línea del TXT
	public static final String SEPARADOR = ",";

	// [MAIN] JSON → TXT
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerJSON(FICHERO_JSON);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirTXT(listaPokemon, FICHERO_TXT);      // 2. ESCRIBIR el destino
	}

	// ============================ LEER JSON ============================

	// [LEER JSON] {"pokemons": [...]} → lista de pokémon (con la clase contenedora Pokedex)
	public static List<Pokemon> leerJSON(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (Reader lector = new FileReader(fichero)) {
			Gson gson = new Gson();
			Pokedex pokedex = gson.fromJson(lector, Pokedex.class);       // JSON → objeto raíz
			if (pokedex != null && pokedex.getPokemons() != null) {
				listaPokemon = pokedex.getPokemons();                     // la raíz tiene la lista dentro
			}
		} catch (Exception e) {
			System.out.println("Error al leer el JSON: " + e.getMessage());
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

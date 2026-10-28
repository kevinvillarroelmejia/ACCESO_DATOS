package c13_JSON_a_DAT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/*
 * =====================================================================
 * COMBINACIÓN 13: JSON → DAT
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.json
 *          { "pokemons": [ { "codigo": 25, "nombre": "Pikachu", "peso": 6.0 }, ... ] }
 * SALIDA:  pokemon.dat
 *          un ArrayList<Pokemon> guardado con writeObject
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER JSON: @SerializedName de Pokedex (raíz) y los atributos de Pokemon (claves del JSON)
 *   - ESCRIBIR DAT: nada si el .dat lo creas tú; si te lo dan, la clase tiene que llamarse igual y estar en el mismo paquete
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_JSON_a_DAT {

	public static final String PAQUETE = "c13_JSON_a_DAT";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_JSON = "src" + File.separator + PAQUETE + File.separator + "pokemon.json";
	public static final String FICHERO_DAT = "src" + File.separator + PAQUETE + File.separator + "pokemon.dat";

	// [MAIN] JSON → DAT
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerJSON(FICHERO_JSON);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirDAT(listaPokemon, FICHERO_DAT);      // 2. ESCRIBIR el destino
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

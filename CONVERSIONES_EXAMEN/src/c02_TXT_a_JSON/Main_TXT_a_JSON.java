package c02_TXT_a_JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * =====================================================================
 * COMBINACIÓN 02: TXT → JSON
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.txt
 *          una línea por pokémon:  25, Pikachu, 6.0
 * SALIDA:  pokemon.json
 *          { "pokemons": [ { "codigo": 25, "nombre": "Pikachu", "peso": 6.0 }, ... ] }
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER TXT: SEPARADOR y el orden de los campos[ ] en leerTXT / escribirTXT
 *   - ESCRIBIR JSON: @SerializedName de Pokedex (raíz) y los atributos de Pokemon (claves del JSON)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_TXT_a_JSON {

	public static final String PAQUETE = "c02_TXT_a_JSON";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_TXT = "src" + File.separator + PAQUETE + File.separator + "pokemon.txt";
	public static final String FICHERO_JSON = "src" + File.separator + PAQUETE + File.separator + "pokemon.json";

	// CAMBIAR: lo que separa los datos en cada línea del TXT
	public static final String SEPARADOR = ",";

	// [MAIN] TXT → JSON
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerTXT(FICHERO_TXT);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirJSON(listaPokemon, FICHERO_JSON);      // 2. ESCRIBIR el destino
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

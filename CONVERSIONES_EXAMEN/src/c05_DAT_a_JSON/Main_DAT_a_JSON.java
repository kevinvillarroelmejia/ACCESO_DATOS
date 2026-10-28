package c05_DAT_a_JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * =====================================================================
 * COMBINACIÓN 05: DAT → JSON
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.dat
 *          un ArrayList<Pokemon> guardado con writeObject
 * SALIDA:  pokemon.json
 *          { "pokemons": [ { "codigo": 25, "nombre": "Pikachu", "peso": 6.0 }, ... ] }
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER DAT: nada si el .dat lo creas tú; si te lo dan, la clase tiene que llamarse igual y estar en el mismo paquete
 *   - ESCRIBIR JSON: @SerializedName de Pokedex (raíz) y los atributos de Pokemon (claves del JSON)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_DAT_a_JSON {

	public static final String PAQUETE = "c05_DAT_a_JSON";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_DAT = "src" + File.separator + PAQUETE + File.separator + "pokemon.dat";
	public static final String FICHERO_JSON = "src" + File.separator + PAQUETE + File.separator + "pokemon.json";

	// [MAIN] DAT → JSON
	public static void main(String[] args) {
		crearDATdePrueba(FICHERO_DAT);   // SOLO PARA PROBAR: si en el examen te dan el .dat, quita esta línea

		List<Pokemon> listaPokemon = leerDAT(FICHERO_DAT);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirJSON(listaPokemon, FICHERO_JSON);      // 2. ESCRIBIR el destino
	}

	// ============================ LEER DAT ============================

	// [LEER DAT] binario de objetos (ArrayList<Pokemon>) → lista de pokémon
	// OJO: el .dat guarda el paquete de la clase: solo se lee con la MISMA clase Pokemon que lo escribió
	@SuppressWarnings("unchecked")
	public static List<Pokemon> leerDAT(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		File f = new File(fichero);
		if (f.exists() && f.length() > 0) {
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(f))) {
				listaPokemon = (ArrayList<Pokemon>) binario.readObject();   // se lee la lista entera de golpe
			} catch (Exception e) {
				System.out.println("Error al leer el DAT: " + e.getMessage());
			}
		} else {
			System.out.println("El fichero binario no existe o está vacío: " + fichero);
		}
		return listaPokemon;
	}

	// [PRUEBA] si no existe el .dat, lo crea con 4 pokémon de ejemplo (si en el examen te lo dan, no lo llames)
	public static void crearDATdePrueba(String fichero) {
		File f = new File(fichero);
		if (!f.exists()) {
			ArrayList<Pokemon> ejemplo = new ArrayList<Pokemon>();
			ejemplo.add(new Pokemon(25, "Pikachu", 6.0));
			ejemplo.add(new Pokemon(152, "Chikorita", 6.4));
			ejemplo.add(new Pokemon(155, "Cyndaquil", 7.9));
			ejemplo.add(new Pokemon(158, "Totodile", 9.5));
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(f))) {
				binario.writeObject(ejemplo);
				System.out.println("DAT de prueba creado: " + fichero);
			} catch (Exception e) {
				System.out.println("Error al crear el DAT de prueba: " + e.getMessage());
			}
		}
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

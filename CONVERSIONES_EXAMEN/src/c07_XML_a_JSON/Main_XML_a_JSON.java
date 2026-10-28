package c07_XML_a_JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * =====================================================================
 * COMBINACIÓN 07: XML → JSON
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.xml
 *          <pokemons><pokemon><codigo>25</codigo><nombre>Pikachu</nombre><peso>6.0</peso></pokemon>...</pokemons>
 * SALIDA:  pokemon.json
 *          { "pokemons": [ { "codigo": 25, "nombre": "Pikachu", "peso": 6.0 }, ... ] }
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER XML: RAIZ_XML y las ETIQUETA_... (nombres de las etiquetas del enunciado)
 *   - ESCRIBIR JSON: @SerializedName de Pokedex (raíz) y los atributos de Pokemon (claves del JSON)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_XML_a_JSON {

	public static final String PAQUETE = "c07_XML_a_JSON";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_XML = "src" + File.separator + PAQUETE + File.separator + "pokemon.xml";
	public static final String FICHERO_JSON = "src" + File.separator + PAQUETE + File.separator + "pokemon.json";

	// CAMBIAR: nombres de las etiquetas del XML del enunciado
	public static final String RAIZ_XML = "pokemons";          // <pokemons> ... </pokemons>
	public static final String ETIQUETA_POKEMON = "pokemon";   // <pokemon> ... </pokemon> (uno por pokémon)
	public static final String ETIQUETA_CODIGO = "codigo";     // <codigo>25</codigo>
	public static final String ETIQUETA_NOMBRE = "nombre";     // <nombre>Pikachu</nombre>
	public static final String ETIQUETA_PESO = "peso";         // <peso>6.0</peso>

	// [MAIN] XML → JSON
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerXML(FICHERO_XML);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirJSON(listaPokemon, FICHERO_JSON);      // 2. ESCRIBIR el destino
	}

	// ============================ LEER XML ============================

	// [LEER XML] cada <pokemon> con <codigo>, <nombre>, <peso> → lista de pokémon
	public static List<Pokemon> leerXML(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(fichero));                    // XML del disco → árbol
			NodeList nodosPokemon = doc.getElementsByTagName(ETIQUETA_POKEMON);   // todos los <pokemon>
			for (int i = 0; i < nodosPokemon.getLength(); i++) {
				Element elementoPokemon = (Element) nodosPokemon.item(i);
				int codigo = Integer.parseInt(textoDe(elementoPokemon, ETIQUETA_CODIGO));   // en XML todo es String
				String nombre = textoDe(elementoPokemon, ETIQUETA_NOMBRE);
				double peso = Double.parseDouble(textoDe(elementoPokemon, ETIQUETA_PESO));
				listaPokemon.add(new Pokemon(codigo, nombre, peso));
			}
		} catch (Exception e) {
			System.out.println("Error al leer el XML: " + e.getMessage());
		}
		return listaPokemon;
	}

	// [LEER XML] texto de una etiqueta hija: <nombre>Pikachu</nombre> → "Pikachu"
	public static String textoDe(Element padre, String etiqueta) {
		return padre.getElementsByTagName(etiqueta).item(0).getTextContent().trim();
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

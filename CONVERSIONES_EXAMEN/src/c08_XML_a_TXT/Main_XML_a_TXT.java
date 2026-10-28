package c08_XML_a_TXT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * =====================================================================
 * COMBINACIÓN 08: XML → TXT
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.xml
 *          <pokemons><pokemon><codigo>25</codigo><nombre>Pikachu</nombre><peso>6.0</peso></pokemon>...</pokemons>
 * SALIDA:  pokemon.txt
 *          una línea por pokémon:  25, Pikachu, 6.0
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER XML: RAIZ_XML y las ETIQUETA_... (nombres de las etiquetas del enunciado)
 *   - ESCRIBIR TXT: SEPARADOR y el orden de los campos[ ] en leerTXT / escribirTXT
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_XML_a_TXT {

	public static final String PAQUETE = "c08_XML_a_TXT";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_XML = "src" + File.separator + PAQUETE + File.separator + "pokemon.xml";
	public static final String FICHERO_TXT = "src" + File.separator + PAQUETE + File.separator + "pokemon.txt";

	// CAMBIAR: lo que separa los datos en cada línea del TXT
	public static final String SEPARADOR = ",";

	// CAMBIAR: nombres de las etiquetas del XML del enunciado
	public static final String RAIZ_XML = "pokemons";          // <pokemons> ... </pokemons>
	public static final String ETIQUETA_POKEMON = "pokemon";   // <pokemon> ... </pokemon> (uno por pokémon)
	public static final String ETIQUETA_CODIGO = "codigo";     // <codigo>25</codigo>
	public static final String ETIQUETA_NOMBRE = "nombre";     // <nombre>Pikachu</nombre>
	public static final String ETIQUETA_PESO = "peso";         // <peso>6.0</peso>

	// [MAIN] XML → TXT
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerXML(FICHERO_XML);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirTXT(listaPokemon, FICHERO_TXT);      // 2. ESCRIBIR el destino
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

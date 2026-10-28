package c01_TXT_a_XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * =====================================================================
 * COMBINACIÓN 01: TXT → XML
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.txt
 *          una línea por pokémon:  25, Pikachu, 6.0
 * SALIDA:  pokemon.xml
 *          <pokemons><pokemon><codigo>25</codigo><nombre>Pikachu</nombre><peso>6.0</peso></pokemon>...</pokemons>
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER TXT: SEPARADOR y el orden de los campos[ ] en leerTXT / escribirTXT
 *   - ESCRIBIR XML: RAIZ_XML y las ETIQUETA_... (nombres de las etiquetas del enunciado)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_TXT_a_XML {

	public static final String PAQUETE = "c01_TXT_a_XML";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_TXT = "src" + File.separator + PAQUETE + File.separator + "pokemon.txt";
	public static final String FICHERO_XML = "src" + File.separator + PAQUETE + File.separator + "pokemon.xml";

	// CAMBIAR: lo que separa los datos en cada línea del TXT
	public static final String SEPARADOR = ",";

	// CAMBIAR: nombres de las etiquetas del XML del enunciado
	public static final String RAIZ_XML = "pokemons";          // <pokemons> ... </pokemons>
	public static final String ETIQUETA_POKEMON = "pokemon";   // <pokemon> ... </pokemon> (uno por pokémon)
	public static final String ETIQUETA_CODIGO = "codigo";     // <codigo>25</codigo>
	public static final String ETIQUETA_NOMBRE = "nombre";     // <nombre>Pikachu</nombre>
	public static final String ETIQUETA_PESO = "peso";         // <peso>6.0</peso>

	// [MAIN] TXT → XML
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerTXT(FICHERO_TXT);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirXML(listaPokemon, FICHERO_XML);      // 2. ESCRIBIR el destino
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

	// ============================ ESCRIBIR XML ============================

	// [ESCRIBIR XML] lista de pokémon → XML nuevo con raíz <pokemons>
	public static void escribirXML(List<Pokemon> listaPokemon, String fichero) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();                   // árbol vacío (conversión: XML siempre nuevo)
			Element raiz = doc.createElement(RAIZ_XML);             // FUERA del for: la raíz, una vez
			doc.appendChild(raiz);
			for (Pokemon pokemon : listaPokemon) {                  // DENTRO del for: un <pokemon> por objeto
				Element elementoPokemon = doc.createElement(ETIQUETA_POKEMON);
				anyadirHijo(doc, elementoPokemon, ETIQUETA_CODIGO, String.valueOf(pokemon.getCodigo()));
				anyadirHijo(doc, elementoPokemon, ETIQUETA_NOMBRE, pokemon.getNombre());
				anyadirHijo(doc, elementoPokemon, ETIQUETA_PESO, String.valueOf(pokemon.getPeso()));
				raiz.appendChild(elementoPokemon);                  // <pokemon> dentro de <pokemons>
			}
			grabarXML(doc, fichero);                                // grabar UNA vez, después del for
			System.out.println("XML creado: " + fichero);
		} catch (Exception e) {
			System.out.println("Error al escribir el XML: " + e.getMessage());
		}
	}

	// [ESCRIBIR XML] crea <etiqueta>texto</etiqueta> y la cuelga del padre (setTextContent solo admite String)
	public static void anyadirHijo(Document doc, Element padre, String etiqueta, String texto) {
		Element hijo = doc.createElement(etiqueta);
		hijo.setTextContent(texto);
		padre.appendChild(hijo);
	}

	// [ESCRIBIR XML] guarda el Document en el fichero, con sangría (comodín)
	public static void grabarXML(Document doc, String fichero) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(fichero));
		transformer.transform(source, result);
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

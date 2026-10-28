package c04_DAT_a_XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * COMBINACIÓN 04: DAT → XML
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.dat
 *          un ArrayList<Pokemon> guardado con writeObject
 * SALIDA:  pokemon.xml
 *          <pokemons><pokemon><codigo>25</codigo><nombre>Pikachu</nombre><peso>6.0</peso></pokemon>...</pokemons>
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER DAT: nada si el .dat lo creas tú; si te lo dan, la clase tiene que llamarse igual y estar en el mismo paquete
 *   - ESCRIBIR XML: RAIZ_XML y las ETIQUETA_... (nombres de las etiquetas del enunciado)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_DAT_a_XML {

	public static final String PAQUETE = "c04_DAT_a_XML";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_DAT = "src" + File.separator + PAQUETE + File.separator + "pokemon.dat";
	public static final String FICHERO_XML = "src" + File.separator + PAQUETE + File.separator + "pokemon.xml";

	// CAMBIAR: nombres de las etiquetas del XML del enunciado
	public static final String RAIZ_XML = "pokemons";          // <pokemons> ... </pokemons>
	public static final String ETIQUETA_POKEMON = "pokemon";   // <pokemon> ... </pokemon> (uno por pokémon)
	public static final String ETIQUETA_CODIGO = "codigo";     // <codigo>25</codigo>
	public static final String ETIQUETA_NOMBRE = "nombre";     // <nombre>Pikachu</nombre>
	public static final String ETIQUETA_PESO = "peso";         // <peso>6.0</peso>

	// [MAIN] DAT → XML
	public static void main(String[] args) {
		crearDATdePrueba(FICHERO_DAT);   // SOLO PARA PROBAR: si en el examen te dan el .dat, quita esta línea

		List<Pokemon> listaPokemon = leerDAT(FICHERO_DAT);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		escribirXML(listaPokemon, FICHERO_XML);      // 2. ESCRIBIR el destino
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

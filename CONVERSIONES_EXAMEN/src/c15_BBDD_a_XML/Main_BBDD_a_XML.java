package c15_BBDD_a_XML;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * COMBINACIÓN 15: BBDD → XML
 * ---------------------------------------------------------------------
 * ENTRADA: tabla pokemon de pokemondb
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * SALIDA:  pokemon.xml
 *          <pokemons><pokemon><codigo>25</codigo><nombre>Pikachu</nombre><peso>6.0</peso></pokemon>...</pokemons>
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - ESCRIBIR XML: RAIZ_XML y las ETIQUETA_... (nombres de las etiquetas del enunciado)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_BBDD_a_XML {

	public static final String PAQUETE = "c15_BBDD_a_XML";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_XML = "src" + File.separator + PAQUETE + File.separator + "pokemon.xml";

	// CAMBIAR: nombres de las etiquetas del XML del enunciado
	public static final String RAIZ_XML = "pokemons";          // <pokemons> ... </pokemons>
	public static final String ETIQUETA_POKEMON = "pokemon";   // <pokemon> ... </pokemon> (uno por pokémon)
	public static final String ETIQUETA_CODIGO = "codigo";     // <codigo>25</codigo>
	public static final String ETIQUETA_NOMBRE = "nombre";     // <nombre>Pikachu</nombre>
	public static final String ETIQUETA_PESO = "peso";         // <peso>6.0</peso>

	// CAMBIAR: conexión según dónde estés
	// CASA:  jdbc:mysql://localhost:3306/pokemondb    root   / abc123
	// CLASE: jdbc:mysql://172.17.0.1:3306/pokemondb   alumno / abc123
	public static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";

	// [MAIN] BBDD → XML
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			listaPokemon = leerBBDD(conexion);          // 1. LEER el origen → lista
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		mostrarLista(listaPokemon);
		escribirXML(listaPokemon, FICHERO_XML);      // 2. ESCRIBIR el destino
	}

	// ============================ LEER BBDD ============================

	// [LEER BBDD] SELECT de la tabla pokemon → lista de pokémon
	public static List<Pokemon> leerBBDD(Connection conexion) throws SQLException {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		// CAMBIAR: tabla y columnas (añade WHERE ... = ? si el enunciado filtra)
		PreparedStatement query = conexion.prepareStatement(
				"SELECT numero_pokedex, nombre, peso FROM pokemon ORDER BY numero_pokedex");
		ResultSet resultado = query.executeQuery();
		while (resultado.next()) {                                        // una vuelta por fila
			int codigo = resultado.getInt("numero_pokedex");
			String nombre = resultado.getString("nombre");
			double peso = resultado.getDouble("peso");
			listaPokemon.add(new Pokemon(codigo, nombre, peso));
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

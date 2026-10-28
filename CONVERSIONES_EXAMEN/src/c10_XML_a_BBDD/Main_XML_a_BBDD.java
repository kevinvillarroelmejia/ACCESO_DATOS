package c10_XML_a_BBDD;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * =====================================================================
 * COMBINACIÓN 10: XML → BBDD
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.xml
 *          <pokemons><pokemon><codigo>25</codigo><nombre>Pikachu</nombre><peso>6.0</peso></pokemon>...</pokemons>
 * SALIDA:  tabla pokemon de pokemondb (INSERT)
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER XML: RAIZ_XML y las ETIQUETA_... (nombres de las etiquetas del enunciado)
 *   - ESCRIBIR BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_XML_a_BBDD {

	public static final String PAQUETE = "c10_XML_a_BBDD";

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

	// [MAIN] XML → BBDD
	public static void main(String[] args) {
		List<Pokemon> listaPokemon = leerXML(FICHERO_XML);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			escribirBBDD(conexion, listaPokemon);      // 2. ESCRIBIR el destino
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		// Para repetir la prueba, borra lo insertado en MySQL: DELETE FROM pokemon WHERE numero_pokedex > 151;
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

	// ============================ ESCRIBIR BBDD ============================

	// [ESCRIBIR BBDD] lista de pokémon → INSERT en la tabla pokemon (salta los que ya existen)
	public static void escribirBBDD(Connection conexion, List<Pokemon> listaPokemon) throws SQLException {
		int filasInsertadas = 0;
		// CAMBIAR: tabla y columnas. altura es NOT NULL en pokemondb y no la tenemos → se guarda 0
		PreparedStatement insert = conexion.prepareStatement(
				"INSERT INTO pokemon (numero_pokedex, nombre, peso, altura) VALUES (?, ?, ?, ?)");
		for (Pokemon pokemon : listaPokemon) {
			if (existePokemon(conexion, pokemon.getCodigo())) {
				System.out.println("Ya existe, no se inserta: " + pokemon);
			} else {
				insert.setInt(1, pokemon.getCodigo());                // los ? se numeran de izquierda a derecha
				insert.setString(2, pokemon.getNombre());
				insert.setDouble(3, pokemon.getPeso());
				insert.setDouble(4, 0);
				filasInsertadas = filasInsertadas + insert.executeUpdate();   // executeUpdate → nº de filas
				System.out.println("Insertado: " + pokemon);
			}
		}
		System.out.println("Filas insertadas: " + filasInsertadas);
	}

	// [COMPROBAR] ¿existe ya ese numero_pokedex en la tabla? → true/false
	public static boolean existePokemon(Connection conexion, int codigo) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"SELECT numero_pokedex FROM pokemon WHERE numero_pokedex = ?");
		query.setInt(1, codigo);
		ResultSet resultado = query.executeQuery();
		boolean existe = resultado.next();                          // hay fila → existe
		return existe;
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

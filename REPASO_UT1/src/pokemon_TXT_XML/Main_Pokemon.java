package pokemon_TXT_XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/*
 * . Tenemos un archivo de texto con un listado de pokemons de esta forma:
152, Chikorita, 6.4, 0.9, Planta
158, Totodile, 9.5, 0.6, Agua
163, Hoothoot, 21.2, 0.7, Normal, Volador
155, Cyndaquil, 7.9, 0.5, Fuego
167, Spinarak, 8.5, 0.5, Bicho, Veneno
Donde el primer dato es el código de la pokedex, el segundo el nombre, el tercero el peso, el
cuarto la altura y el último el tipo (o tipos puesto que pueden ser dos).
Conviértelo en un archivo con la misma información pero con estructura XML. Si hay dos tipos
ponlos en la misma línea. Así:
<tipo>Bicho, Veneno</tipo>
 * 
 * */

public class Main_Pokemon {
	public static final String FICHERO_TXT = "src" + File.separator + "pokemon_TXT_XML" + File.separator
			+ "pokemon.txt";
	public static final String FICHERO_XML = "src" + File.separator + "pokemon_TXT_XML" + File.separator
			+ "pokemonXML.xml";

	public static void main(String[] args) throws Exception {

		List<Pokemon> listaPokemon = leerPokemonTXT(FICHERO_TXT);

		guardar_pokemon(FICHERO_XML, listaPokemon);

	}

	// METODO PARA GUARDAR EL XML LOGICO AL DISCO FISICO
	public static void grabarXML(Document doc, String fichero) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(fichero);
		transformer.transform(source, result);
	}

	// si no estoy mal necesitamos este metodo para hacer referencia al XML
	//Document: Es un arbol de etiquetas
	private static Document leerOCrearXML(String fichero, String nombreRaiz) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc;
		File f = new File(fichero);
		if (f.exists() && f.length() > 0) {
			doc = builder.parse(f); // existe: lo leemos
		} else {
			doc = builder.newDocument(); // no existe: árbol vacío
			Element raiz = doc.createElement(nombreRaiz); // y le creamos la raíz
			doc.appendChild(raiz);
		}
		return doc;
	}
	
	//Para guardar en disco el XML el flujo es:
	//metodo leerOCrearXML(ficheroXML, "pokemons");
	//hacer las modificaciones en el XMl
	//METODO cargarXMl
	public static void guardar_pokemon(String ficheroXML, List<Pokemon> listaPokemon) throws Exception {
		Document document = leerOCrearXML(ficheroXML, "pokemons");
		Element raiz = document.getDocumentElement(); // CAMBIO: cogemos <pokemons>

		for (Pokemon pokemon : listaPokemon) {

			Element elementoPokemon = document.createElement("pokemon"); // <pokemon> de este pokémon
			
			Element elementoIDPokemon = document.createElement("id");
			elementoIDPokemon.setTextContent(pokemon.getIdPokedex());
			elementoPokemon.appendChild(elementoIDPokemon);// para que cuelgue de pokemon

			
			Element elementoNombre = document.createElement("nombre");
			elementoNombre.setTextContent(pokemon.getNombrePokemon());
			elementoPokemon.appendChild(elementoNombre);

			Element elementoPeso = document.createElement("peso");
			elementoPeso.setTextContent(pokemon.getPeso());
			elementoPokemon.appendChild(elementoPeso);

			Element elementoAltura = document.createElement("altura");
			elementoAltura.setTextContent(pokemon.getAltura());
			elementoPokemon.appendChild(elementoAltura);

			String tipos = pokemon.getTipo();
			// TODO PREGUNTAR SI ESTO SE PUEDE HACER DE OTRO FORMA
			if (pokemon.getTipo2() != null) {
				tipos = tipos + ", " + pokemon.getTipo2();
			}
			Element elementoTipos = document.createElement("tipo");
			elementoTipos.setTextContent(tipos);
			elementoPokemon.appendChild(elementoTipos);

			raiz.appendChild(elementoPokemon); // <pokemon> dentro de <pokemons>
		}
		grabarXML(document, ficheroXML);
	}

	public static void guardarEnDisco_XML(Document doc, String fichero) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(fichero);
		transformer.transform(source, result);
	}

	public static List<Pokemon> leerPokemonTXT(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea;

			String idPokemon = "";
			String nombrePokemon = "";
			String peso = "";
			String altura = "";
			String tipo1 = "";
			String tipo2 = "";

			while ((linea = lector.readLine()) != null) {
				String[] lineaLista = linea.trim().split(",");
				if (lineaLista.length == 5) {
					idPokemon = lineaLista[0].trim();
					nombrePokemon = lineaLista[1].trim();
					peso = lineaLista[2].trim();
					altura = lineaLista[3].trim();
					tipo1 = lineaLista[4].trim();
					Pokemon pokemon = new Pokemon(idPokemon, nombrePokemon, peso, altura, tipo1);
					listaPokemon.add(pokemon);
				} else if (lineaLista.length == 6) {
					idPokemon = lineaLista[0].trim();
					nombrePokemon = lineaLista[1].trim();
					peso = lineaLista[2].trim();
					altura = lineaLista[3].trim();
					tipo1 = lineaLista[4].trim();
					tipo2 = lineaLista[5].trim();
					Pokemon pokemon = new Pokemon(idPokemon, nombrePokemon, peso, altura, tipo1, tipo2);
					listaPokemon.add(pokemon);
				}
			}
		} catch (IOException e) {
			e.getStackTrace();
		}
		return listaPokemon;
	}
}

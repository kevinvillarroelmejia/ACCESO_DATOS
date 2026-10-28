package Elegir_XML_o_JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

import pokemon_TXT_XML.Pokemon;

public class Main_Elegir_XML_JSON {

	public static final String FICHERO_JSON = "";
	public static final String FICHERO_XML = "";
	
	public static final String FICHERO_TXT="";

	public static void main(String[] args) throws Exception {
		
		List<Pokemon> listaPokemon=leerPokemonTXT(FICHERO_TXT);
		
		guardar_pokemon(FICHERO_TXT, listaPokemon);
		
		//CREO QUE YA TENGO LA PARTE DE xml HECHA FALTA EL json Y CREAR LOS OBJETOS CON UN PARAMETRO sTRING QUE SEA JSON O xmL Y COMPARE SI ES UNA U OTRA EJECUTAR x FUNCION 
		

	}



	// ==================METODOS PARA XML=================================
	
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
		guardarEnDisco_XML(document, ficheroXML);
	}

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
	
	
	public static void guardarEnDisco_XML(Document doc, String fichero) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(fichero);
		transformer.transform(source, result);
	}

	//METODO QUE LEE UN FICHERO Y VUELVE UNA LISTA
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


	// ==========================================================
}

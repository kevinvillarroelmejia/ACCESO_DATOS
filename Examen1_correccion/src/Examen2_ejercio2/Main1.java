package Examen2_ejercio2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main1 {

	public static final String FICHERO_XMl="src"+File.separator+"Examen2_ejercio2"+File.separator+"pokedex.xml";
	
	public static final String FICHERO_TXT="src"+File.separator+"pokemon.txt";

	public static final String RAIZ_XML="pokedex";
	
	public static final String ETIQUETA_POKEMON = "pokemon";  
	public static final String ETIQUETA_NOMBRE = "nombre";    
	public static final String ETIQUETA_TIPO="tipo";
	
	
	public static void main(String[] args) throws Exception {

		List<Pokemon> listaPokemon=listaPokemon(FICHERO_XMl);
		escribirTXT(listaPokemon, FICHERO_TXT);

		
	}
	
	public static List<Pokemon> listaPokemon(String ficheroXML) throws Exception{
		List<Pokemon> listPokemon = new ArrayList<Pokemon>();
		Document document = leerOCrearXML(ficheroXML, RAIZ_XML);
		
		NodeList nodeListPokemon = document.getElementsByTagName("pokemon");
		for (int i = 0; i < nodeListPokemon.getLength(); i++) {
			Node nodo = nodeListPokemon.item(i);
			Element pokemonElement = (Element) nodo;
			
//			int  idNivel=Integer.parseInt(pokemonElement.getElementsByTagName(ETIQUETA_POKEMON).item(0).getTextContent());
			String nombrePokemon=pokemonElement.getElementsByTagName(ETIQUETA_NOMBRE).item(0).getTextContent();
			String tipoPokemon=pokemonElement.getElementsByTagName(ETIQUETA_TIPO).item(0).getTextContent();
			
			//quite los otros atributos para probar si eran funcionaba
			Pokemon pokemon=new Pokemon(nombrePokemon, tipoPokemon);
			listPokemon.add(pokemon);
		}
		return listPokemon;
	}
	

	public static void escribirTXT(List<Pokemon> listaPokemon, String fichero) {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero))) {  
			escritor.write("=== POKEDEX DEL PROFESOR OAK ===");
			for (Pokemon pokemon : listaPokemon) {
				escritor.write( pokemon.getTipo());  
				escritor.newLine();
			}
		} catch (Exception e) {
			System.out.println("Error al escribir el TXT: " + e.getMessage());
		}
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
	
	

}

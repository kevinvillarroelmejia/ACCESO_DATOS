package pokemon_TXT_XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ficheroTexto_JSON.Articulo;

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

	public static void main(String[] args) {
		leerPokemonTXT(FICHERO_TXT);
	}
	
	public static void guardar_pokemon(String ficheroXML,List<Pokemon> listaPokemon) throws Exception{
		/*
		 * si o si tenemos que utizamos el leerXMl de agendaXML limpio
		 * 
		 * Recorrer la lista e ir guardando uno a uno con los getters de pokemon
		 * seria hacer como el ayadirContacto de agendaXMl
		 * */
		
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

			int idPokemon = 0;
			String nombrePokemon = "";
			float peso = 0;
			float altura = 0;
			String tipo1 = "";
			String tipo2 = "";

			while ((linea = lector.readLine()) != null) {
				String[] lineaLista = linea.trim().split(",");
				if (lineaLista.length == 5) {
					idPokemon = Integer.parseInt(lineaLista[0].trim());
					nombrePokemon = lineaLista[1].trim();
					peso = Float.parseFloat(lineaLista[2].trim());
					altura = Float.parseFloat(lineaLista[3].trim());
					tipo1 = lineaLista[4].trim();
					Pokemon pokemon = new Pokemon(idPokemon, nombrePokemon, peso, altura, tipo1);
					listaPokemon.add(pokemon);
				} else if (lineaLista.length == 6) {
					idPokemon = Integer.parseInt(lineaLista[0].trim());
					nombrePokemon = lineaLista[1].trim();
					peso = Float.parseFloat(lineaLista[2].trim());
					altura = Float.parseFloat(lineaLista[3].trim());
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

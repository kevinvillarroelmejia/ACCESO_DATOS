package Contactos_Migrar_XML_JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 1. Lee un archivo con la estructura que hemos usado en clase para la Agenda XML y
conviérteleo en un archivo JSON, también con la estructura que hemos usado. Puesto que el
JSON tiene un dato mas (DNI) déjalo en blanco. Trata de reutilizar todas las funciones que
puedas de lo que hemos hecho en clase*/

public class Main_Agenda {

	public static final String FICHERO_XML = "src" + File.separator + "Contactos_Migrar_XML_JSON" + File.separator
			+ "agenda.xml";
	public static final String FICHERO_JSON = "src" + File.separator + "Contactos_Migrar_XML_JSON" + File.separator
			+ "agenda.json";

	
	// [MAIN] XML agenda → JSON agenda
	public static void main(String[] args) throws Exception {

		List<Contacto> listaAgenda = leerAgendaXML(FICHERO_XML);
		cargarJSON(listaAgenda);

	}
	// [ESCRIBIR JSON] lista de contactos → JSON con raíz "agenda" (clase Agenda)
	public static void cargarJSON(List<Contacto> listaAgenda) {
		Agenda agenda = new Agenda(listaAgenda); 
		try (Writer escritor = new FileWriter(FICHERO_JSON)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			//toJson escribe el JSON con la misma forma que tiene agenda
			gson.toJson(agenda, escritor);

		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}
	
	
	// [LEER XML] agenda.xml → lista de contactos (nombre + teléfono)
	public static List<Contacto> leerAgendaXML(String ficheroXML) throws Exception {
		List<Contacto> listContactos = new ArrayList<Contacto>();

		Document document = leerOCrearXML(ficheroXML, "Agenda");

		NodeList nodeListContacto = document.getElementsByTagName("contacto");
		// TODO RECOREMOS EL NodeList para que es cada contacto con sus etiquetas
		// en este caso solo tenemos nombre telefono y dni
		for (int i = 0; i < nodeListContacto.getLength(); i++) {
			Node nodo = nodeListContacto.item(i);
			Element contactoElement = (Element) nodo;

			String nombre = contactoElement.getElementsByTagName("nombre").item(0).getTextContent();
			int telefono = Integer.parseInt(contactoElement.getElementsByTagName("telefono").item(0).getTextContent());

			Contacto contacto = new Contacto(nombre, telefono);
			listContactos.add(contacto);

		}

		return listContactos;
	}

	// [LEER XML] abre el XML si existe; si no, lo crea con su raíz (comodín)
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

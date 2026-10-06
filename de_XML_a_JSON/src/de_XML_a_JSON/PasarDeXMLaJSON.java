package de_XML_a_JSON;

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

public class PasarDeXMLaJSON {

	public static void main(String[] args) throws Exception {

//		leerAgenda("agenda.xml");
		List<Contacto> listaAgenda=leerAgenda_crearObjeto("agenda.xml");
		cargarContactos_a_XML(listaAgenda, "agenda.json");
	}

	// metodo para leer el XML
	private static Document leerXML(String fichero) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		return doc;
	}

	private static List<Contacto> leerAgenda_crearObjeto(String fichero) throws Exception {
		List<Contacto> listaContacto = new ArrayList<Contacto>();
		Document doc = leerXML(fichero);
		// CREAMOS UNA LISTA ITERABLE
		// cogiendo elemento por etiqueda del XML
		NodeList listaContactos = doc.getElementsByTagName("contacto");

		// Recorremos la lista de contactos
		for (int i = 0; i < listaContactos.getLength(); i++) {
			// cojo el elemento i y lo guardo en el objeto contacto
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			// consigiendo SOLO el nombre del contacto ---> jose maria
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
			String dni = contacto.getElementsByTagName("dni").item(0).getTextContent();
			Contacto pooContacto = new Contacto(nombre, telefono, dni);
			listaContacto.add(pooContacto);
		}
		return listaContacto;
	}

	private static void cargarContactos_a_XML(List<Contacto> contactos, String ruta) {
		Agenda agenda = new Agenda();
		agenda.setContactos(contactos);
		try (Writer escritor = new FileWriter(ruta)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(agenda, escritor);

		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}

}

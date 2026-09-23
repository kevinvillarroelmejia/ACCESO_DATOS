package XML_Ficheros;

//import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import org.w3c.dom.*;
public class AgendaXML {
	public static void main(String[] args) throws Exception {
//		leerAgenda("agenda.xml");
		existeContacto("kevin villarroel", "agenda.xml");
	}

	// =======METODO PARA LEER AGENDA======== TEORIA============
	private static void leerAgenda(String fichero) throws Exception {
		// Leemos el XML y lo almacenamos en el objeto doc
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
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
			// consigiendo SOLO el telefono del contacto --->11122233
			String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
			System.out.println(nombre + " - " + telefono);
//			existeContacto(nombre);
		}
	}

	private static void existeContacto(String nombrePedido, String fichero) throws Exception {

		// Leemos el XML y lo almacenamos en el objeto doc
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);

		// CREAMOS UNA LISTA ITERABLE
		// cogiendo elemento por etiqueda del XML
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		
		boolean encontrado=false;
		String nombreAgenda="";
		// Recorremos la lista de contactos
		for (int i = 0; i < listaContactos.getLength()&& encontrado==false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			
			nombreAgenda = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
			
			if(nombreAgenda.equalsIgnoreCase(nombrePedido)) {
				encontrado=true;
			}
		}
		
		if(encontrado) {
			System.out.println("Contacto encontrado: "+nombreAgenda);
		}else {
			System.out.println("Contacto NO ENCONTRADO");
		}
		
	}

}

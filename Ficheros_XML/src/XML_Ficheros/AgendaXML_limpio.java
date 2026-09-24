package XML_Ficheros;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AgendaXML_limpio {


	public static void main(String[] args) throws Exception {
		// mostrarAgenda("agenda.xml");
		buscarContactoPorNombre("agenda.xml", "Sara García");
		eliminarContacto("agenda.xml", "Sara García");
		aniadirContacto("agenda.xml", "Miriam García", "222222222");
		aniadirContacto("agenda.xml", "Sara García", "111111111");
		modificarTelefono("agenda.xml", "Sara García", "333333333");
	}

	public static void mostrarAgenda(String fichero) throws Exception {
		Document doc = leerXML(fichero);
		// Creamos una lista iterable para recuperar los elementos
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		// Recorremos la lista de contactos
		for (int i = 0; i < listaContactos.getLength(); i++) {
			// Cojo el elemento i y lo guardo en el objeto contacto
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			// el item 0 se refiere al único elemento que hay entro de <nombre>
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
			System.out.println(nombre + " - " + telefono);
		}
	}

	// MÉTODOS CLAVE -------------------------------------------------
	public static Document leerXML(String fichero) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(fichero);
	}

	public static void grabarXML(Document doc, String fichero) throws Exception {
		/*
		 * Tenemos que guardar el XML ya que todos estos cambios de eliminación o añadir
		 * contenido se realizan en memoria y no directamente sobre el XMl así que
		 * tenemos que usar ____ para actualizarlo y que se vean los cambios reflejados
		 */
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(fichero);
		transformer.transform(source, result);
	}
	// ---------------------------------------------------------------------------

	// MÉTODOS DE FUNCIONALIDADES
	// ------------------------------------------------------
	/* Método para buscar el teléfono de una persona en concreto por su nombre */
	public static void buscarContactoPorNombre(String fichero, String nom) throws Exception {
		Document doc = leerXML(fichero);
		NodeList listaContactos = doc.getElementsByTagName("contacto");

		boolean existe = false;

		for (int i = 0; i < listaContactos.getLength() && existe == false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();

			if (nombre.equalsIgnoreCase(nom)) {
				existe = true;
				String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
				System.out.println("El teléfono de " + nom + " es " + telefono);
			}
		}
		if (existe == false) {
			System.out.println("No existe ningún contacto con nombre " + nom);
		}
	}

	/* Eliminar contacto */
	public static void eliminarContacto(String fichero, String nom) throws Exception {
		Document doc = leerXML(fichero);
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		boolean existe = false;
		for (int i = 0; i < listaContactos.getLength() && existe == false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();

			if (nombre.equalsIgnoreCase(nom)) {
				existe = true;
				Element raiz = doc.getDocumentElement();
				raiz.removeChild(contacto);
				System.out.println("Contacto " + nom + " eliminado");
				grabarXML(doc, fichero);
			}
		}
		if (existe == false) {
			System.out.println(nom + " no se puede eliminar ya que no existe contacto con ese nombre.");
		}
	}

	private static void aniadirContacto(String fichero, String nombreNuevo, String telefonoNuevo) throws Exception {
		Document doc = leerXML(fichero);
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		boolean existe = false;
		for (int i = 0; i < listaContactos.getLength() && existe == false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if (nombre.equalsIgnoreCase(nombreNuevo)) {
				existe = true;
				System.out.println("Contacto: " + nombreNuevo + " añadido");
			}
		}
		if (existe == true) {
			System.out.println("Ya existe un contacto de nombre: " + nombreNuevo);
		} else {
			Element nuevoContacto = doc.createElement("contacto");
			Element elementoNombre = doc.createElement("nombre");
			Element elementoTelefono = doc.createElement("telefono");
			// Para meter el contenido de lo que pasamos por parámetro en el elemnto
			// correspondiente:
			elementoNombre.setTextContent(nombreNuevo);
			elementoTelefono.setTextContent(telefonoNuevo);
			nuevoContacto.appendChild(elementoNombre);
			nuevoContacto.appendChild(elementoTelefono);
			Element raiz = doc.getDocumentElement();
			raiz.appendChild(nuevoContacto);
			grabarXML(doc, fichero);
		}
	}

	private static void modificarTelefono(String fichero, String nombreContacto, String nuevoTelefono)
			throws Exception {
		Document doc = leerXML(fichero);
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		boolean existe = false;
		for (int i = 0; i < listaContactos.getLength() && existe == false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if (nombre.equalsIgnoreCase(nombreContacto)) {
				existe = true;
				Element telefono = (Element)contacto.getElementsByTagName("telefono").item(0);
				telefono.setTextContent(nuevoTelefono);
				grabarXML(doc, fichero);
				System.out.println("Teléfono modificado a " + nuevoTelefono +" en el contacto " + nombreContacto);
			}
		}
		if (existe == false) {
			System.out.println("No existe contacto de nombre " + nombreContacto + " por lo que no puede ser modificado");
		}
	}

	// ---------------------------------------------------------------------------
}
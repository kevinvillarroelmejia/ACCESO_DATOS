package XML_Ficheros;

//import javax.swing.text.Document;
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

//import org.w3c.dom.*;
public class AgendaXML {
	public static void main(String[] args) throws Exception {
//		leerAgenda("agenda.xml");

//		nuevoContacto("agenda.xml", "Sara Garcia", "000999888");

		String contactoBuscar = "kevin villarroel";
		eliminarContacto2("agenda.xml", contactoBuscar);
	}

	// ======================================================================
	// METODOS ESENCIALES
	private static Document leerXML(String fichero) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		return doc;
	}

	private static void grbarXML(String fichero, Document doc) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(fichero);
		transformer.transform(source, result);
	}
	// ======================================================================

	// =======METODO PARA LEER AGENDA======== TEORIA============
	private static void leerAgenda(String fichero) throws Exception {
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
			// consigiendo SOLO el telefono del contacto --->11122233
			String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
			System.out.println(nombre + " - " + telefono);
//			existeContacto(nombre);
		}
	}

	private static Element existeContacto(String nombrePedido, String fichero) throws Exception {
		Document doc = leerXML(fichero);

		// CREAMOS UNA LISTA ITERABLE
		// cogiendo elemento por etiqueda del XML
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		
		Element contacto=null;
		
		boolean encontrado = false;
		// Recorremos la lista de contactos
		for (int i = 0; i < listaContactos.getLength() && encontrado == false; i++) {
			Node nodo = listaContactos.item(i);
			contacto = (Element) nodo;
		}
		return contacto;
	}

	/* Eliminar contacto */
	public static void eliminarContacto2(String fichero, String nom) throws Exception {
		Document doc = leerXML(fichero);

		NodeList listaContactos = doc.getElementsByTagName("contacto");

		boolean existe = false;
			if (existeContacto("Kevin villarroel", fichero)!=null) {
				existe = true;
				Element raiz = doc.getDocumentElement();
				raiz.removeChild(existeContacto("Kevin villarroel", fichero));
				System.out.println("Contacto " + nom + " fue eliminado");
				/*
				 * Tenemos que guardar el XML ya que todos estos cambios de eliminación o añadir
				 * contenido se realizan en EN UNA COPIA DEL XML y no directamente sobre el XMl
				 * así que tenemos que usar Transformer para actualizarlo y que se vean los
				 * cambios reflejados
				 */
				grbarXML(fichero, doc);
			}else {
				System.out.println("No se puedo eliminar contacto");
			}
	}

	/* Eliminar contacto */
	public static void nuevoContacto(String fichero, String nombreNuevo, String telefonoNuevo) throws Exception {
		Document doc = leerXML(fichero);

		NodeList listaContactos = doc.getElementsByTagName("contacto");

		boolean existe = false;

		for (int i = 0; i < listaContactos.getLength() && existe == false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if (nombre.equalsIgnoreCase(nombreNuevo)) {
				existe = true;
			}
		}
		if (existe) {
			System.out.println("EL CONTACTO " + nombreNuevo + " YA EXISTE");
		} else {

			Element nuevoContacto = doc.createElement("contacto");

			Element elementoNombre = doc.createElement("nombre");
			Element elementoTelefono = doc.createElement("telefono");

			elementoNombre.setTextContent(nombreNuevo);
			elementoTelefono.setTextContent(telefonoNuevo);

			nuevoContacto.appendChild(elementoNombre);
			nuevoContacto.appendChild(elementoTelefono);

			Element raiz = doc.getDocumentElement();
			raiz.appendChild(nuevoContacto);

			grbarXML(fichero, doc);

			System.out.println(nombreNuevo + " --> Nuevo contacto creado...");

		}
	}

	public static void modificarTelefono(String fichero, String nombreModificado) throws Exception {
		Document doc = leerXML(fichero);

		NodeList listaContactos = doc.getElementsByTagName("contacto");

		boolean existe = false;

		for (int i = 0; i < listaContactos.getLength() && existe == false; i++) {
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if (nombre.equalsIgnoreCase(nombreModificado)) {
				existe = true;
				Element telefono = (Element) contacto.getElementsByTagName("telefono").item(0);
				telefono.setTextContent(nombreModificado);
				grbarXML(fichero, doc);
				System.out.println("Telefono modificado en el contacto "+nombreModificado);
			}
		}
	}

//	private static void eliminarContacto(String nombreEliminado,String fichero)throws Exception{
////existeContacto(nombreEliminado, fichero);
//if(existeContacto(nombreEliminado, "agenda.xml")) {
//	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	DocumentBuilder builder = factory.newDocumentBuilder();
//	Document doc = builder.parse(fichero);
//	Element raiz=doc.getDocumentElement();
//	raiz.removeChild(co)
//	
//}else {
//	System.out.println(nombreEliminado+" NO EXISTE");
//}
//}

}

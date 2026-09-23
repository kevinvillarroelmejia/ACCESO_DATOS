package EJERCICIOS_FICHEROS;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * ============================================================
 * EJERCICIOS · XML (lectura con DOM)
 * ============================================================
 * Teoría: TEORIA/XML_DOM.java
 * Origen de todo: ACCESO_DATOS/Ficheros_XML (AgendaXML.java + agenda.xml).
 * En DAM_JAVA no hay ningún ejercicio de XML.
 *
 * agenda.xml (en la raíz del proyecto, por eso la ruta es solo "agenda.xml"):
 *
 *   <?xml version="1.0" encoding="UTF-8" standalone="no"?>
 *   <agenda>
 *       <contacto>
 *           <nombre>jose maria</nombre>
 *           <telefono>11122233</telefono>
 *       </contacto>
 *       <contacto>
 *           <nombre>kevin villarroel</nombre>
 *           <telefono>999888777</telefono>
 *       </contacto>
 *   </agenda>
 * ============================================================
 */
public class XML_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Listar la agenda
	// Practica: DocumentBuilderFactory -> DocumentBuilder -> parse -> getElementsByTagName
	//           -> recorrer NodeList -> (Element) -> getTextContent
	// Origen: AgendaXML.leerAgenda  (marcado "TEORIA" en el código)
	// Salida:  jose maria - 11122233
	//          kevin villarroel - 999888777
	// ============================================================
	static class Ejercicio1_LeerAgenda {
		public static void main(String[] args) throws Exception {
			leerAgenda("agenda.xml");
		}

		private static void leerAgenda(String fichero) throws Exception {
			// Leemos el XML y lo almacenamos en el objeto doc
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fichero);
			// CREAMOS UNA LISTA ITERABLE cogiendo elemento por etiqueta del XML
			NodeList listaContactos = doc.getElementsByTagName("contacto");

			// Recorremos la lista de contactos
			for (int i = 0; i < listaContactos.getLength(); i++) {
				// cojo el elemento i y lo guardo en el objeto contacto
				Node nodo = listaContactos.item(i);
				Element contacto = (Element) nodo;
				// consiguiendo SOLO el nombre del contacto ---> jose maria
				String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
				// consiguiendo SOLO el teléfono del contacto ---> 11122233
				String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
				System.out.println(nombre + " - " + telefono);
			}
		}
	}

	// ============================================================
	// EJERCICIO 2 · ¿Existe un contacto en la agenda?
	// Practica: recorrer la NodeList con una BANDERA en la condición del for
	//           (para en cuanto lo encuentra) + equalsIgnoreCase
	// Origen: AgendaXML.existeContacto  (es lo que ejecuta el main del proyecto)
	// Salida: "Contacto encontrado: kevin villarroel" o "Contacto NO ENCONTRADO"
	// ============================================================
	static class Ejercicio2_ExisteContacto {
		public static void main(String[] args) throws Exception {
			existeContacto("kevin villarroel", "agenda.xml");
		}

		private static void existeContacto(String nombrePedido, String fichero) throws Exception {
			// Leemos el XML y lo almacenamos en el objeto doc
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fichero);

			NodeList listaContactos = doc.getElementsByTagName("contacto");

			boolean encontrado = false;
			String nombreAgenda = "";
			// Recorremos la lista de contactos hasta el final o hasta encontrarlo
			for (int i = 0; i < listaContactos.getLength() && encontrado == false; i++) {
				Node nodo = listaContactos.item(i);
				Element contacto = (Element) nodo;

				nombreAgenda = contacto.getElementsByTagName("nombre").item(0).getTextContent();
				String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();

				if (nombreAgenda.equalsIgnoreCase(nombrePedido)) {
					encontrado = true;
				}
			}

			if (encontrado) {
				System.out.println("Contacto encontrado: " + nombreAgenda);
			} else {
				System.out.println("Contacto NO ENCONTRADO");
			}
		}
	}
}

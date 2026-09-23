package TEORIA_FICHEROS;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

/*
 * ============================================================
 * TEORÍA 7 · XML — LECTURA CON DOM
 * ============================================================
 *
 * Origen: ACCESO_DATOS / Ficheros_XML
 *   - src/XML_Ficheros/AgendaXML.java  (leerAgenda y existeContacto)
 *   - agenda.xml
 *   - Teoria  (fichero de texto: XML, CSV, JSON + ejemplos de formato)
 *
 * En DAM_JAVA NO hay XML. Todo sale de este proyecto.
 * Lo que hemos hecho con XML es SOLO LEER (no escribir ni modificar).
 *
 * ============================================================
 * ¿QUÉ ES?
 * ============================================================
 *   Un fichero de TEXTO con los datos organizados en ETIQUETAS.
 *   Formatos que nombra el fichero Teoria: XML, CSV, JSON.
 *
 *   El mismo dato en CSV y en XML (fichero Teoria):
 *
 *   CSV:  jose maria, 333444555, 57
 *
 *   XML:
 *   <?xml version="1.0" encoding="UTF-8" standalone="no"?>
 *   <agenda>                         <- elemento raíz
 *       <contacto>                   <- un elemento por contacto
 *           <nombre>jose maria</nombre>       <- etiqueta hija con texto
 *           <telefono>11122233</telefono>
 *       </contacto>
 *       <contacto>
 *           <nombre>kevin villarroel</nombre>
 *           <telefono>999888777</telefono>
 *       </contacto>
 *   </agenda>
 *
 * ============================================================
 * ¿QUÉ ES DOM?
 * ============================================================
 *   Se lee el XML entero y se guarda en memoria en un objeto Document
 *   (un árbol de nodos). Después se pide la lista de nodos de una
 *   etiqueta y se recorre como un array.
 *
 * ============================================================
 * CLASES QUE HEMOS USADO
 * ============================================================
 *   javax.xml.parsers.DocumentBuilderFactory  -> fábrica que crea el DocumentBuilder
 *   javax.xml.parsers.DocumentBuilder         -> lee (parsea) el fichero
 *   org.w3c.dom.Document                      -> el XML entero en memoria
 *   org.w3c.dom.NodeList                      -> lista de nodos (la recorremos con for)
 *   org.w3c.dom.Node                          -> un nodo de la lista
 *   org.w3c.dom.Element                       -> un nodo que es una ETIQUETA
 *
 *   OJO con el import de Document: tiene que ser org.w3c.dom.Document.
 *   En AgendaXML aparece comentado  //import javax.swing.text.Document;
 *   (Eclipse puede proponer el de swing, que NO es el bueno).
 *   También aparece comentado  //import org.w3c.dom.*;  (importar todo).
 */
public class XML_DOM {

	/*
	 * ============================================================
	 * PASO 1 · ABRIR / LEER EL XML  (siempre las mismas 3 líneas)
	 * ============================================================
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: DocumentBuilderFactory.newInstance()
	 * Para qué sirve: crear la fábrica.
	 * Qué recibe: nada.  Qué devuelve: DocumentBuilderFactory.
	 *
	 * Nombre: factory.newDocumentBuilder()
	 * Para qué sirve: crear el objeto que lee el XML.
	 * Qué recibe: nada.  Qué devuelve: DocumentBuilder.
	 *
	 * Nombre: builder.parse(fichero)
	 * Para qué sirve: leer el fichero XML y guardarlo en memoria.
	 * Qué recibe: String con la ruta ("agenda.xml", relativa al proyecto).
	 * Qué devuelve: Document.
	 *
	 * Cómo lo hemos utilizado: al principio de leerAgenda y de existeContacto.
	 * ============================================================
	 */
	public static Document abrirXML(String fichero) throws Exception {
		// Leemos el XML y lo almacenamos en el objeto doc
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		return doc;
	}

	/*
	 * ============================================================
	 * PASO 2 · SACAR LA LISTA DE NODOS DE UNA ETIQUETA
	 * ============================================================
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: getElementsByTagName(etiqueta)
	 * Para qué sirve: coger todos los elementos con esa etiqueta.
	 * Qué recibe: String con el nombre de la etiqueta ("contacto").
	 * Qué devuelve: NodeList (lista iterable).
	 * Cómo lo hemos utilizado:
	 *   - doc.getElementsByTagName("contacto")      -> todos los contactos
	 *   - contacto.getElementsByTagName("nombre")   -> la etiqueta hija
	 *     (se llama sobre un Element, no solo sobre el Document)
	 *
	 * ------------------------------------------------------------
	 * MÉTODOS DE NodeList
	 * ------------------------------------------------------------
	 * getLength() -> int, número de nodos (como el length de un array).
	 * item(i)     -> Node, el nodo de la posición i (empieza en 0).
	 * ============================================================
	 */

	/*
	 * ============================================================
	 * PASO 3 · RECORRER Y LEER EL TEXTO DE CADA ETIQUETA
	 * ============================================================
	 *
	 * 1. Node nodo = listaContactos.item(i);
	 * 2. Element contacto = (Element) nodo;      -> CAST a Element
	 *    (hace falta para poder llamar a getElementsByTagName sobre él)
	 * 3. contacto.getElementsByTagName("nombre").item(0).getTextContent()
	 *      getElementsByTagName("nombre") -> lista de <nombre> dentro del contacto
	 *      .item(0)                       -> el primero (solo hay uno)
	 *      .getTextContent()              -> el TEXTO de dentro: "jose maria"
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: getTextContent()
	 * Para qué sirve: obtener el texto que hay entre <etiqueta> y </etiqueta>.
	 * Qué recibe: nada.
	 * Qué devuelve: String.
	 * Nota: devuelve también los espacios que haya dentro de la etiqueta.
	 *   En el fichero Teoria las etiquetas llevan un espacio delante
	 *   (<nombre> jose maria</nombre>); en agenda.xml no.
	 * ============================================================
	 */
	public static void leerAgenda(String fichero) throws Exception {
		// Leemos el XML y lo almacenamos en el objeto doc
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);

		// CREAMOS UNA LISTA ITERABLE cogiendo el elemento por etiqueta
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

	/*
	 * ============================================================
	 * EXCEPCIONES
	 * ============================================================
	 *  newDocumentBuilder() y parse() lanzan excepciones.
	 *  En AgendaXML todos los métodos llevan  throws Exception  y el
	 *  main también:  public static void main(String[] args) throws Exception
	 *
	 * ============================================================
	 * ESQUEMA PARA EL EXAMEN
	 * ============================================================
	 *   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	 *   DocumentBuilder builder = factory.newDocumentBuilder();
	 *   Document doc = builder.parse(fichero);
	 *   NodeList lista = doc.getElementsByTagName("ETIQUETA_PADRE");
	 *   for (int i = 0; i < lista.getLength(); i++) {
	 *       Element e = (Element) lista.item(i);
	 *       String dato = e.getElementsByTagName("ETIQUETA_HIJA").item(0).getTextContent();
	 *   }
	 * ============================================================
	 */
	public static void main(String[] args) throws Exception {
		leerAgenda("agenda.xml");
	}
}

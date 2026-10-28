package Contactos_Migrar_XML_JSON;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 1. Lee un archivo con la estructura que hemos usado en clase para la Agenda XML y
conviérteleo en un archivo JSON, también con la estructura que hemos usado. Puesto que el
JSON tiene un dato mas (DNI) déjalo en blanco. Trata de reutilizar todas las funciones que
puedas de lo que hemos hecho en clase*/

public class Main_Agenda {

	
	
	
	public static void main(String[] args) {
		
		
		
	}
	
	
	
	
	public static void leerAgendaXML(String ficheroXML,List<Contacto> listaContacto) throws Exception {
		Document document=leerOCrearXML(ficheroXML, "Agenda");
		
		NodeList listaContactos=document.getElementsByTagName("contacto");
		//TODO RECOREMOS EL NodeList para que es cada contacto con sus etiquetas
		//en este caso solo tenemos nombre telefono y dni
		for(int i = 0; i < listaContactos.getLength(); i++) {
			
		}
		
	}
	//Document: Es un arbol de etiquetas
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

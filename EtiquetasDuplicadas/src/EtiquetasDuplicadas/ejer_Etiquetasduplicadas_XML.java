package EtiquetasDuplicadas;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ejer_Etiquetasduplicadas_XML {

	public static void main(String[] args) {
		
		
		/*
		 * En JSON no podemos tener etiquetas con el mismo nombre
		 * en json si no tenemos la estructura de lista se queda con el ultimo
		 * 
		 * En JSON no existe atributos de las etiquetas 
		 * En XML si existe atributos de las etiquetas*/
		
		String fichero ="agenda.xml";
		try {
			Document doc=leerXML(fichero);
			
			
			
			//HACIENDO LISTA CONTACTOS
			NodeList listaContactos=doc.getElementsByTagName("contacto");
			System.out.println("Contactos: "+listaContactos.getLength());
			for(int i=0;i<listaContactos.getLength();i++) {
				Element contactoElement=(Element)listaContactos.item(i);
				//SACANDO NOMBRE DEL XML (CUIDADO CON EL IMPORT ESTE ES EL BUENO import org.w3c.dom.*;)
				String nombre=contactoElement.getElementsByTagName("nombre").item(0).getTextContent();
				System.out.println("Nombre contacto ");
			
				
				
				//HACIENDO LISTA DE TELEFONOS DE LOS CONTACTOS y RECORRIENDOLA
				NodeList listaTelefonos=contactoElement.getElementsByTagName("telefono");
				for(int x=0;x<listaTelefonos.getLength();x++) {
					Element telefonoElement=(Element)listaTelefonos.item(x);
					//SACANDO ATRIBUTO
					String tipo=telefonoElement.getAttribute("tipo");
					//SACANDO NOMBRE DEL XML (CUIDADO CON EL IMPORT ESTE ES EL BUENO import org.w3c.dom.*;)
					String telefono=telefonoElement.getTextContent();
					System.out.println(" - "+telefono+" ("+tipo+")");
				}

			}
			
		}catch (Exception e) {
			System.out.println("Error main");
		}
	}
	
	// MÉTODOS CLAVE -------------------------------------------------
	public static Document leerXML(String fichero) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(fichero);
	}

}

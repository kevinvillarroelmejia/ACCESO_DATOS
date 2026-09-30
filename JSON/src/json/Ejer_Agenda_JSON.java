package json;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;

public class Ejer_Agenda_JSON {

	public static void main(String[] args) {
		
		
		final String ruta="AgendaJSON.json";
		leerAgenda(ruta);
		
	}
	
	private static void leerAgenda2(String ruta) {

		
			List<Contacto> contactos=cargarListaAgenda(ruta);
			if(contactos!=null) {
				for(Contacto c:contactos) {
					c.mostrar();
				}
			}
			
			
		
	}
	

	private static List<Contacto> cargarListaAgenda(String ruta) {
		return null;
	}

	private static void leerAgenda(String ruta) {

		try(Reader lector=new FileReader(ruta)) {
			Gson gson=new Gson();
			Agenda agenda=gson.fromJson(lector, Agenda.class);
			
			//GSON funciona solo con List
			List<Contacto> contactos=agenda.getContactos();
			
			for(Contacto c:contactos) {
				c.mostrar();
			}
			
		} catch (Exception e) {
			System.out.println("error al leer el fichero");
			System.out.println(e.getMessage());
		}
		
	}

}

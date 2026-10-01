package json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ejer_Agenda_JSON {

	public static void main(String[] args) {
		
		
		final String ruta="AgendaJSON.json";
		leerAgenda(ruta);
		
		Contacto nuevoContacto=new Contacto("Kevin", "1112220000V", "888777333");
		//con este metodo creamos y GUARDAMOS en el JSON
		crearContacto(nuevoContacto,ruta);
		
	}
	
	private static void crearContacto(Contacto nuevoContacto, String ruta) {
		List<Contacto> contactos=cargarListaAgenda(ruta);
		boolean encontrado=false;
		if(contactos!=null) {
			for(Contacto c:contactos) {
				//COMPARAR NOMBRES DE CONTACTOS
				if(c.getNombre().equalsIgnoreCase(nuevoContacto.getNombre())) {
					//si coinciden...
					encontrado=true;
				}
				if(encontrado==false) {
					contactos.add(nuevoContacto);
					guardarAgenda(contactos,ruta);
				}
			}
		}
	}

	//metodo para guardar en un JSON un objeto POO
	private static void guardarAgenda(List<Contacto> contactos, String ruta) {
		Agenda agenda=new Agenda();
		agenda.setContactos(contactos);
		try (Writer escritor=new FileWriter(ruta)){
			//si no importa la 
//			Gson gson=new Gson();
			//para que quede mas bonito y de forma vertical hacerlo con .setPrettyPrinting().create();
			Gson gson=new GsonBuilder().setPrettyPrinting().create();
			
			
			gson.toJson(agenda,escritor);
		} catch (Exception e) {
			System.out.println("Error "+e.getMessage());
		}
	}

	private static void leerAgenda2(String ruta) {
			List<Contacto> contactos=cargarListaAgenda(ruta);
			if(contactos!=null) {
				for(Contacto c:contactos) {
					System.out.println(c);
				}
			}
	}
	
	
	
	private static List<Contacto> cargarListaAgenda(String ruta) {
		List<Contacto> contactos=null;
		try (Reader lector=new FileReader(ruta)){
			Gson gson=new Gson();
//			Agenda agenda=gson.fromJson(lector, Agenda.class);
//			contactos=agenda.getContactos();
			contactos=gson.fromJson(lector, Agenda.class).getContactos();
			
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			
		}
		
		return null;
	}

	private static void leerAgenda(String ruta) {

		try(Reader lector=new FileReader(ruta)) {
			Gson gson=new Gson();
			Agenda agenda=gson.fromJson(lector, Agenda.class);
			
			//GSON funciona solo con List
			List<Contacto> contactos=agenda.getContactos();
			
			for(Contacto c:contactos) {
				System.out.println(c);
			}
			
		} catch (Exception e) {
			System.out.println("error al leer el fichero");
			System.out.println(e.getMessage());
		}
		
	}

}

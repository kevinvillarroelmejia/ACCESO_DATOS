package teoria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

/* Para que una clase se pueda escribir y leer de fichero hay que escribir
 * "implements Serializable" en caso de que una clase hija queremos que
 * sea Serializable pondremos esta funcionalidad a la clase padre y las hijas
 * heredarán esta función
 * 
 * Cuando grabamos un objeto grabamos todos los atributos menos: 
 * 1. Los datos estáticos no se graban ya que pertenecen a la clase y no al objecto
 * 2. Si tenemos algún atributo concreto que no queremos guardar como contraseña etc
 * hay que definirlo con la característica
 */

public class Tarea implements Serializable {
	private String id;
	private String titulo;
	// No se guardará ya que es transient
	// private transient int prioridad;
	private int prioridad;
	private boolean completada;
	// No se guardará ya que es estático
	private static ArrayList<Tarea> listaTareas = new ArrayList<>();

	public Tarea(String id, String titulo, int prioridad, boolean completada) {
		this.id = id;
		this.titulo = titulo;
		this.prioridad = prioridad;
		this.completada = completada;
		Tarea.listaTareas.add(this);
	}
	
	public static void leerFicheroTareas(String fichero) {
		try (BufferedReader lector = new BufferedReader (new FileReader(fichero))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] lista = linea.split(":");
				boolean completado = false;
				if (lista[3].equals(("1"))) {
					completado = true;
				}
				new Tarea(lista[0], lista[1], Integer.parseInt(lista[2]), completado);
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void mostrarTarea() {
		String completada = " ";
		if (this.completada == true) {
			completada = "X";
		}
		System.out.printf("%s [%s] %s [Prioridad: %d]\n", completada, this.id, this.titulo, this.prioridad);
	}
	
	public static void mostrarTareas() {
		for (Tarea tarea:Tarea.listaTareas) {
			tarea.mostrarTarea();
		}
	}
	
	public static void escribirFicheroTareas(String fichero) {
		try(PrintWriter pluma = new PrintWriter(fichero)) {
			for (Tarea tarea:Tarea.listaTareas) {
				int completada = 0;
				if (tarea.completada == true) {
					completada = 1;
				}
				pluma.printf("%s:%s:%d:%d", tarea.id, tarea.titulo, tarea.prioridad, completada);
				pluma.println();
			}	
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static ArrayList<Tarea> ordenarFicheroPorPrioridad(ArrayList<Tarea> listaTareas) {
	    ArrayList<Tarea> ordenada = new ArrayList<>();
	    while (!(listaTareas.size()!= 0)) {
	        Tarea mayor = listaTareas.get(0);
	        for (Tarea tarea : listaTareas) {
	            if (tarea.prioridad > mayor.prioridad) {
	                mayor = tarea;
	            }
	        }
	        listaTareas.remove(mayor);
	        ordenada.add(mayor);
	    }
	    return ordenada;
	}

	public static ArrayList<Tarea> getListaTareas() {
		return Tarea.listaTareas;
	}
}

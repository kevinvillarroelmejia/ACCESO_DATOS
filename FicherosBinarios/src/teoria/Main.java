package teoria;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		/*	---------- ARCHIVOS BINARIOS O ALEATORIOS ----------
		 *  Cuando no queremos que un usuario exiterno modifique nuestro programa.
		 * Ocupa mucho menos espacio ya que se guardan en un formato más compromido.
		 * El acceso a la información para lectura y escritura también es mucho más rápido.
		 * Cuando grabamos siempre tenemos que idicar el tipo de dato ya que alamacenamos
		 * en binario y no en String como con los ficheros de texto.
		 * Tiene persistencia de objectos más cómoda que con los ficheros de texto
		 * La información que se guarda en el fichero no es comprensible para el usuario,
		 * además si se edita el contenido podríamos tener problemas.
		 */
		// Extensión para los binarios es .dat o .bin
		String fichero = "/home/alumno/binario.dat";
		// escribirFicheroBinario(fichero);
		// leerFicheroBinario(fichero);
		Tarea t1 = new Tarea("E34", "Aprender a grabar objetos en Java", 9, false);
		grabarTarea(t1, fichero);
		
		/*Tarea tareaRecuperada = leerTarea(fichero);
		if (tareaRecuperada != null) {
			tareaRecuperada.mostrarTarea();
		} */
		
		Tarea t2 = new Tarea("P41", "No perder la racha de Doulingo", 5, true);
		Tarea t3 = new Tarea("X15", "Comprar comida", 6, true);
		Tarea t4 = new Tarea("T44", "Limpiar los baños", 5, true);
		Tarea t5 = new Tarea("G66", "Quedar con los amigos", 7, true);
		System.out.println("------------- CREACIÓN DE LISTA DE TAREAS INICIAL -------------");
		ArrayList<Tarea> lista = new ArrayList<>(List.of(t1,t2,t3,t4,t5));
		grabarLista(lista, fichero);
		ArrayList<Tarea> listaRecuperada = leerLista(fichero);
		for (Tarea tarea: listaRecuperada) {
			tarea.mostrarTarea();
		}
		System.out.println("------------- AÑADIR TAREAS A LA LISTA -------------");
		Tarea t6 = new Tarea("G92", "Estudiar Java", 9, false);
		ArrayList<Tarea> listaRecuperadaNuva = leerLista(fichero);
		listaRecuperadaNuva.add(t6);
		grabarLista(listaRecuperada, fichero);
		for (Tarea tarea: listaRecuperadaNuva) {
			tarea.mostrarTarea();
		}
	}

	public static void escribirFicheroBinario (String fichero) {
		// Con esto abrimos un fichero para escritura desde el punto de vista de la persona, los datos salen de mi
		try (DataOutputStream binario = new DataOutputStream(new FileOutputStream(fichero))) {
			// Siempre que se escriba hay que especificar el tipo de dato que se escribe
			binario.writeInt(3456);
			binario.writeDouble(3.1415);
			binario.writeBoolean(false);
			binario.writeChar('X');
			binario.writeUTF("Hola mundo binario");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private static void leerFicheroBinario(String fichero) {
		// Para la lectura es Input desde el punto de vista de la persona, los datos entran en mi
		try (DataInputStream binario = new DataInputStream(new FileInputStream(fichero))) {
			// Muy importante leer en el mimso orden de datos en el que hemos grabado la info
			System.out.println(binario.readInt());
			System.out.println(binario.readDouble());
			System.out.println(binario.readBoolean());
			System.out.println(binario.readChar());
			System.out.println(binario.readUTF());
			/* Si intentamos leer algún tipo de dato más que no existe o no está en el fichero
			nos genera una excepción */
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private static void grabarTarea(Tarea tarea, String fichero) {
		// Para escribir objetos en binario usamos la clase ObjectOutputStream
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			// Con el método writeObject grabamos el objecto no hace falta castear
			binario.writeObject(tarea);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private static Tarea leerTarea(String fichero) {
		Tarea tarea = null;
		// Para leer objetos en binario usamos la clase ObjectInputStream
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
			// Obligatoriamente hay que castear (Tarea) el tipo de objecto que estamos leyendo
			tarea = (Tarea)binario.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return tarea;
	}
	
	// Método para grabar objetos en una lista
	private static void grabarLista(ArrayList<Tarea> lista, String fichero) {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(lista);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	// Método para recuperar la lista grabada en fichero
	private static ArrayList<Tarea> leerLista(String fichero) {
		ArrayList<Tarea> lista = null;
		// Para leer objetos en binario usamos la clase ObjectInputStream
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
			// Obligatoriamente hay que castear (Tarea) el tipo de objecto que estamos leyendo
			lista = (ArrayList<Tarea>)binario.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}
}

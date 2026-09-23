package TEORIA_FICHEROS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================
 * TEORÍA 5 · FICHEROS BINARIOS DE OBJETOS (SERIALIZACIÓN / PERSISTENCIA)
 * ============================================================
 *
 * Origen:
 *   ACCESO_DATOS / FicherosBinarios/teoria/Main + Tarea
 *   ACCESO_DATOS / ExamenFicherosSara (Personaje), Boletin23Ficheros/ej9 (Alumno)
 *   ACCESO_DATOS / DAM2_Boletin1_AccesoFicheros/Teoriaficheros
 *   DAM_JAVA     / FicherosBinarios, TodoSobreFicheros/ficherosBinarios
 *   DAM_JAVA     / UT7_Ficheros_RA5/ficherosBinarios (Pokemon), TareaFicheroBinario,
 *                  ExamenAnime, Examen_IA_Veterinaria, ExamenOrdinaria/E1_RA8
 *   DAM_JAVA     / PracticandoExtraordinaria/RA8_Serializacion (Gym)
 *
 * ¿QUÉ ES?
 *   Guardar OBJETOS enteros en un fichero binario y recuperarlos después.
 *   Es la PERSISTENCIA DE OBJETOS: "Persistencia de objetos --> SOLO CON
 *   --> FICHEROS BINARIOS" (Teoriaficheros).
 *
 * ¿PARA QUÉ LO HEMOS UTILIZADO?
 *   Guardar una Tarea, una lista de Tareas, Personajes, Pokémon, Clientes
 *   del gym, Visitas de la veterinaria, un HashMap de votos...
 *
 * ============================================================
 * 1. LA CLASE TIENE QUE SER Serializable
 * ============================================================
 *
 *   public class Tarea implements Serializable { ... }
 *
 *   - Sin esto, writeObject lanza excepción.
 *   - HERENCIA: se pone en la clase PADRE y las hijas ya lo son
 *     (PokemonLegendario extends Pokemon).
 *
 *   ¿QUÉ SE GRABA? Todos los atributos MENOS:
 *     1. Los static -> pertenecen a la clase, no al objeto.
 *     2. Los transient -> atributos que no queremos guardar
 *        (por seguridad: una contraseña...).
 *        private transient int prioridad;  -> al recuperar vale 0.
 */
public class FicherosBinarios_Objetos {

	// Clase de ejemplo (resumen de FicherosBinarios/teoria/Tarea)
	static class Tarea implements Serializable {
		private String id;
		private String titulo;
		private int prioridad;               // con "transient" delante no se guardaría
		private boolean completada;
		private static ArrayList<Tarea> listaTareas = new ArrayList<>(); // static: NO se guarda

		public Tarea(String id, String titulo, int prioridad, boolean completada) {
			this.id = id;
			this.titulo = titulo;
			this.prioridad = prioridad;
			this.completada = completada;
			Tarea.listaTareas.add(this);
		}

		public void mostrarTarea() {
			String completada = " ";
			if (this.completada == true) {
				completada = "X";
			}
			System.out.printf("%s [%s] %s [Prioridad: %d]\n", completada, this.id, this.titulo, this.prioridad);
		}
	}

	/*
	 * ============================================================
	 * 2. GRABAR UN OBJETO
	 * ============================================================
	 *
	 * Cómo se abre: new ObjectOutputStream(new FileOutputStream(fichero))
	 *   (FileOutputStream sobrescribe el fichero; si no existe lo crea)
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: writeObject(objeto)
	 * Para qué sirve: grabar el objeto entero.
	 * Qué recibe: el objeto (Serializable). No hace falta castear.
	 * Qué devuelve: nada.
	 * ============================================================
	 */
	public static void grabarTarea(Tarea tarea, String fichero) {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(tarea);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * ============================================================
	 * 3. LEER UN OBJETO
	 * ============================================================
	 *
	 * Cómo se abre: new ObjectInputStream(new FileInputStream(fichero))
	 *
	 * ------------------------------------------------------------
	 * MÉTODO
	 * ------------------------------------------------------------
	 * Nombre: readObject()
	 * Para qué sirve: recuperar el objeto grabado.
	 * Qué recibe: nada.
	 * Qué devuelve: Object -> OBLIGATORIO castear:  (Tarea) binario.readObject()
	 *
	 * Si falla devolvemos null -> comprobar  if (tareaRecuperada != null)
	 * ============================================================
	 */
	public static Tarea leerTarea(String fichero) {
		Tarea tarea = null;
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
			tarea = (Tarea) binario.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return tarea;
	}

	/*
	 * ============================================================
	 * 4. GRABAR Y LEER UNA LISTA  (LO QUE MÁS SALE EN LOS EXÁMENES)
	 * ============================================================
	 *
	 * La lista entera se graba como UN ÚNICO objeto con writeObject(lista).
	 * "COMO HE GUARDADO UNA LISTA RECUPERO UNA LISTA":
	 *     lista = (ArrayList<Tarea>) binario.readObject();
	 *
	 * También lo hemos hecho con un HashMap (recuento de votos, E1_RA8):
	 *     diccionario = (HashMap<String, Integer>) binario.readObject();
	 * ============================================================
	 */
	public static void grabarLista(ArrayList<Tarea> lista, String fichero) {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(lista);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static ArrayList<Tarea> leerLista(String fichero) {
		ArrayList<Tarea> lista = null;
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
			lista = (ArrayList<Tarea>) binario.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	/*
	 * ============================================================
	 * 5. AÑADIR O MODIFICAR  (no hay modo "añadir")
	 * ============================================================
	 *
	 *   1. Recuperar la lista del fichero (readObject).
	 *   2. Añadir / cambiar en memoria (lista.add(...), objeto.setEstado(...), diccionario.put(...)).
	 *   3. Volver a grabar la lista entera (writeObject).
	 *
	 * Hecho así en: añadir tarea G92 / V55, añadir Lapras al fichero de
	 * Pokémon, cambiar la suscripción de un cliente del gym, marcar una tarea,
	 * sumar votos a un partido.
	 * ============================================================
	 */
	public static void ejemploAnadir(String fichero) {
		ArrayList<Tarea> listaRecuperada = leerLista(fichero);
		if (listaRecuperada != null) {
			listaRecuperada.add(new Tarea("G92", "Estudiar Java", 9, false));
			grabarLista(listaRecuperada, fichero);
		}
	}

	/*
	 * ============================================================
	 * MOSTRAR LOS OBJETOS RECUPERADOS
	 * ============================================================
	 *  - Con un método propio: tarea.mostrarTarea() / poke.mostrar()
	 *  - Con toString() sobrescrito (@Override) y System.out.println(objeto)
	 *    (Personaje de ExamenAnime, Cliente del gym, Visitas, Tarea de UT7).
	 *
	 * ============================================================
	 * EXCEPCIONES
	 * ============================================================
	 *  catch (Exception e) en todos.
	 *  Saltan si: la clase no es Serializable, el fichero no existe,
	 *  o el cast no corresponde con lo grabado.
	 *  En E1_RA8 el catch de la primera lectura está vacío a propósito:
	 *  si el fichero aún no existe, se empieza con un HashMap vacío.
	 * ============================================================
	 */
	public static void main(String[] args) {
		String fichero = "/home/alumno/binario.dat";
		Tarea t1 = new Tarea("E34", "Aprender a grabar objetos en Java", 9, false);
		grabarTarea(t1, fichero);
		Tarea tareaRecuperada = leerTarea(fichero);
		if (tareaRecuperada != null) {
			tareaRecuperada.mostrarTarea();
		}

		Tarea t2 = new Tarea("P41", "No perder la racha de Duolingo", 5, true);
		ArrayList<Tarea> lista = new ArrayList<>(List.of(t1, t2));
		grabarLista(lista, fichero);
		ArrayList<Tarea> listaRecuperada = leerLista(fichero);
		for (Tarea tarea : listaRecuperada) {
			tarea.mostrarTarea();
		}
	}
}

package EJERCICIOS_FICHEROS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ============================================================
 * EJERCICIOS · FICHEROS BINARIOS DE OBJETOS (SERIALIZACIÓN)
 * ============================================================
 * Teoría: TEORIA/FicherosBinarios_Objetos.java
 *
 * Ordenados de menor a mayor dificultad:
 *   1-3 grabar/leer objetos y listas creados en el código
 *   4-7 de fichero de TEXTO a lista de objetos en BINARIO (típico de examen)
 *   8   serializar un HashMap
 * ============================================================
 */
public class FicherosBinarios_Objetos_Ejercicios {

	// ============================================================
	// EJERCICIO 1 · Lista de tareas: grabar, recuperar y añadir una más
	// Practica: implements Serializable, transient, static no se graba,
	//           writeObject(lista) / (ArrayList<...>) readObject()
	// Origen: DAM_JAVA/FicherosBinarios/FicherosBinarios (+ TareaFicherosBinarios)
	//   (igual en TodoSobreFicheros y en ACCESO_DATOS/FicherosBinarios/teoria/Main)
	// Fíjate: prioridad es transient -> al recuperar sale "Prioridad: 0".
	// ============================================================
	static class Ejercicio1_ListaTareas {

		// TENEMOS QUE SERIALIZAR LA CLASE
		// SI UNA CLASE TIENE HERENCIA EL Serializable SE PONE EN LA PADRE
		static class TareaFicherosBinarios implements Serializable {
			private String identificador;
			private String titulo;
			private transient int prioridad; // transient: no se graba en disco
			private boolean estadoTarea;
			// LOS DATOS ESTÁTICOS NO SE GRABAN EN DISCO
			private static ArrayList<TareaFicherosBinarios> listaTarea = new ArrayList<TareaFicherosBinarios>();

			public TareaFicherosBinarios(String identificador, String titulo, int prioridad, boolean estadoTarea) {
				this.identificador = identificador;
				this.titulo = titulo;
				this.prioridad = prioridad;
				this.estadoTarea = estadoTarea;
				TareaFicherosBinarios.listaTarea.add(this);
			}

			public void mostrarTarea() {
				String completada = "";
				if (this.estadoTarea == true) {
					completada = "X";
				}
				System.out.printf("%s [%s] %s (Prioridad: %d)\n", completada, this.identificador, this.titulo,
						this.prioridad);
			}
		}

		public static void main(String[] args) {
			String fichero = "/home/alumno/binario.dat"; // puede ser .bin o .dat
			TareaFicherosBinarios t1 = new TareaFicherosBinarios("E34", "Aprende a grabar objetos con java", 9, false);
			TareaFicherosBinarios t2 = new TareaFicherosBinarios("X15", "COMPRAR COMIDA", 5, true);
			TareaFicherosBinarios t3 = new TareaFicherosBinarios("T44", "Limpiar los baños", 4, true);
			TareaFicherosBinarios t4 = new TareaFicherosBinarios("R56", "Quedar con los amigos", 7, true);
			ArrayList<TareaFicherosBinarios> listaTareas = new ArrayList<TareaFicherosBinarios>(List.of(t1, t2, t3, t4));
			grabarLista(listaTareas, fichero);

			// AÑADIR: recuperar, añadir a la lista y volver a grabar
			ArrayList<TareaFicherosBinarios> listaRecuperada = leerListaTareas(fichero);
			TareaFicherosBinarios tnuevo = new TareaFicherosBinarios("V55", "Planificador", 10, false);
			listaRecuperada.add(tnuevo);
			grabarLista(listaRecuperada, fichero);
			for (TareaFicherosBinarios tarea : listaRecuperada) {
				tarea.mostrarTarea();
			}
		}

		public static TareaFicherosBinarios leerTarea(String fichero) {
			TareaFicherosBinarios tarea = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				tarea = (TareaFicherosBinarios) binario.readObject();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			return tarea;
		}

		public static ArrayList<TareaFicherosBinarios> leerListaTareas(String fichero) {
			ArrayList<TareaFicherosBinarios> lista = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				lista = (ArrayList<TareaFicherosBinarios>) binario.readObject();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			return lista;
		}

		public static void grabarLista(ArrayList<TareaFicherosBinarios> listaTareas, String fichero) {
			// ObjectOutputStream permite grabar objetos en el fichero
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
				binario.writeObject(listaTareas);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 2 · Pokémon: guardar UN objeto, UNA LISTA y añadir a la lista
	// Practica: cast obligatorio al leer, herencia y Serializable
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/ficherosBinarios/MainPokemon_FicherosBinarios + Pokemon
	// (De la clase Pokemon solo se copian los atributos, constructores y mostrar();
	//  el método de combate no tiene que ver con ficheros.)
	// ============================================================
	static class Ejercicio2_Pokemon {

		// Para poder grabar los objetos y darles persistencia tienen que ser Serializables
		static class Pokemon implements Serializable {
			private int codigo;
			private String nombre;
			private String[] tipo = new String[2];
			protected int pv;
			private Pokemon evolucion = null;
			// Los datos estáticos, si los hubiera, no se guardan
			// Si no quisiéramos guardar un atributo: private transient String password;

			public Pokemon(int c, String n, String t) {
				this.codigo = c;
				this.nombre = n;
				this.tipo[0] = t;
				this.pv = (int) ((Math.random() * 51) + 50);
			}

			public Pokemon(int c, String n, String t1, String t2) {
				this.codigo = c;
				this.nombre = n;
				this.tipo[0] = t1;
				this.tipo[1] = t2;
				this.pv = (int) ((Math.random() * 51) + 50);
			}

			public void mostrar() {
				System.out.println("------------------");
				System.out.println(this.codigo + " - " + this.nombre);
				if (this.tipo[1] == null)
					System.out.println("Tipo: " + this.tipo[0]);
				else
					System.out.println("Tipos: " + this.tipo[0] + ", " + this.tipo[1]);
				if (this.evolucion != null)
					System.out.println("Evoluciona en: " + this.evolucion.nombre);
				System.out.println("PV: " + this.pv);
				System.out.println("------------------");
			}

			public void setEvolucion(Pokemon p) {
				this.evolucion = p;
			}
		}

		// En las clases hijas no hace falta poner Serializable: si el padre lo es, las hijas también
		static class PokemonLegendario extends Pokemon {
			public PokemonLegendario(int c, String n, String t) {
				super(c, n, t);
				this.pv = (int) ((Math.random() * 201) + 100);
			}
		}

		public static void main(String[] args) {
			String fichero = "/home/josemaria/binario.bin";
			// Guardar y recuperar UN objeto
			Pokemon pokemon = new Pokemon(6, "Charizard", "Fuego", "Volador");
			guardarPokemon(pokemon, fichero);
			Pokemon pokemonRecuperado = recuperarPokemon(fichero);
			if (pokemonRecuperado != null)
				pokemonRecuperado.mostrar();

			// Guardar y recuperar UNA LISTA de objetos
			ArrayList<Pokemon> listaPokemons = new ArrayList<>(List.of(
					new Pokemon(1, "Bulbasaur", "Planta"),
					new Pokemon(6, "Charizard", "Fuego", "Volador"),
					new Pokemon(25, "Pikachu", "Eléctrico"),
					new Pokemon(7, "Squirtle", "Agua")));
			guardarListaPokemons(listaPokemons, fichero);
			ArrayList<Pokemon> listaRecuperada = recuperarListaPokemons(fichero);
			for (Pokemon poke : listaRecuperada) {
				poke.mostrar();
			}

			// AÑADIR a un fichero existente: recuperar, añadir a la lista, volver a guardar
			Pokemon p7 = new Pokemon(131, "Lapras", "Agua", "Hielo");
			listaRecuperada = recuperarListaPokemons(fichero);
			listaRecuperada.add(p7);
			guardarListaPokemons(listaRecuperada, fichero);
		}

		public static void guardarPokemon(Pokemon pokemon, String fichero) {
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
				binario.writeObject(pokemon);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		// RECUPERAR un objeto (cast obligatorio al leer)
		public static Pokemon recuperarPokemon(String fichero) {
			Pokemon pokemon = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				pokemon = (Pokemon) binario.readObject();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			return pokemon;
		}

		// GUARDAR una lista (se guarda como un único objeto)
		public static void guardarListaPokemons(ArrayList<Pokemon> lista, String fichero) {
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
				binario.writeObject(lista);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static ArrayList<Pokemon> recuperarListaPokemons(String fichero) {
			ArrayList<Pokemon> lista = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				lista = (ArrayList<Pokemon>) binario.readObject();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			return lista;
		}
	}

	// ============================================================
	// EJERCICIO 3 · Tareas con prioridad (enum): marcar una tarea y ver las pendientes
	// Practica: objeto con enum y setter, modificar en memoria y volver a grabar,
	//           toString() para mostrar
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/TareaFicheroBinario/Main + Tarea
	// ============================================================
	static class Ejercicio3_TareasPendientes {

		static class Tarea implements Serializable {
			private String descripcion;
			private Prioridad prioridad;
			private boolean estado;

			public Tarea(String descripcion, Prioridad prioridad, boolean estado) {
				this.descripcion = descripcion;
				this.prioridad = prioridad;
				this.estado = estado;
			}

			@Override
			public String toString() {
				String linea = "Descripcion: " + this.descripcion + "\nPrioridad: " + this.prioridad + "\nEstado: " + this.estado;
				return linea;
			}

			public enum Prioridad {
				alta, media, baja
			}

			public boolean isEstado() {
				return estado;
			}

			public void setEstado(boolean estado) {
				this.estado = estado;
			}
		}

		public static void main(String[] args) {
			Tarea t1 = new Tarea("barrer", Tarea.Prioridad.alta, false);
			Tarea t2 = new Tarea("gym", Tarea.Prioridad.media, true);
			Tarea t3 = new Tarea("comer", Tarea.Prioridad.baja, false);
			ArrayList<Tarea> listaTareas = new ArrayList<Tarea>(List.of(t1, t2, t3));
			String fichero = "tareas.dat";
			escribirTarea(fichero, listaTareas);
			leerTareasIncompletas(fichero);
			System.out.println();
			marcarTarea(t3, listaTareas);
			escribirTarea(fichero, listaTareas);
			leerTareasIncompletas(fichero);
		}

		public static void escribirTarea(String fichero, ArrayList<Tarea> listaTareas) {
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
				binario.writeObject(listaTareas);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		public static void leerTareasIncompletas(String fichero) {
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				// COMO HE GUARDADO UNA LISTA RECUPERO UNA LISTA
				ArrayList<Tarea> listaTarea = (ArrayList<Tarea>) binario.readObject();
				for (Tarea tarea : listaTarea) {
					if (tarea.isEstado() == false) {
						System.out.println(tarea);
					}
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static void marcarTarea(Tarea tarea, ArrayList<Tarea> listaTareas) {
			for (Tarea elemento : listaTareas) {
				if (elemento == tarea) {
					elemento.setEstado(true);
				}
			}
		}
	}

	// ============================================================
	// EJERCICIO 4 · Notas de alumnos: de texto a lista de objetos y guardarla en binario
	// Practica: leer "Nombre: n1, n2, n3, n4, n5" -> objetos Alumno en una lista
	//           static -> writeObject(lista)
	// Origen: ACCESO_DATOS/Boletin23Ficheros/ej9 (Alumno + Main)
	//   (tu versión sin terminar: DAM_JAVA/Boletin23_Ficheros/E9_Notas)
	// ============================================================
	static class Ejercicio4_AlumnosBinario {

		static class Alumno implements Serializable {
			private String alumno;
			private double[] ras;
			private static String nombreModulo;
			private static ArrayList<Alumno> listaAlumnos = new ArrayList<>();

			public Alumno(String alumno, double[] ras) {
				this.alumno = alumno;
				this.ras = ras;
			}

			public static void leerAlumnos(String fichero) {
				try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
					String linea;
					Alumno.nombreModulo = fichero.substring(0, fichero.length() - 4);
					while ((linea = lector.readLine()) != null) {
						String[] elementos = linea.split(": ");
						String nombre = elementos[0];
						String[] ras = elementos[1].split(", ");
						double[] notas = new double[5];
						for (int i = 0; i < 5; i++) {
							notas[i] = Double.parseDouble(ras[i]);
						}
						Alumno alumno = new Alumno(nombre, notas);
						Alumno.listaAlumnos.add(alumno);
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			public static void procesarNotasAlumnos() {
				System.out.printf("Módulo: %s\n", Alumno.nombreModulo);
				System.out.println("Alumnos/as con todo aprobado: ");
				int contador = 0;
				for (Alumno alumno : Alumno.listaAlumnos) {
					if (alumno.todoAprobado() == true) {
						System.out.println(alumno.alumno);
						contador++;
					}
				}
				if (contador == 0) {
					System.out.println("No hay ningún alumno con todos los RAs aprobados.");
				}
				System.out.println();
				System.out.println("Resultados de aprendizaje y alumnos suspensos:");
				for (int i = 1; i <= 5; i++) {
					System.out.printf("RA%d: ", i);
					Alumno.suspensosPorRA(i - 1);
					System.out.println();
				}
			}

			private static void suspensosPorRA(int n) {
				int contador = 0;
				for (Alumno alumno : Alumno.listaAlumnos) {
					if (alumno.ras[n] < 5) {
						if (contador == 0) {
							System.out.printf("%s ", alumno.alumno);
						} else {
							System.out.printf(",%s ", alumno.alumno);
						}
						contador++;
					}
				}
				if (contador == 0) {
					System.out.println("Todos aprobados");
				}
			}

			public boolean todoAprobado() {
				boolean todoAprobado = true;
				for (double nota : this.ras) {
					if (nota < 5) {
						todoAprobado = false;
					}
				}
				return todoAprobado;
			}

			public static void salvarAlumnosBinario(String fichero) {
				try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
					binario.writeObject(Alumno.listaAlumnos);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}

		public static void main(String[] args) {
			String fichero = "/home/alumno/boletin23ej9/redes.txt";
			String ficheroBinario = "/home/alumno/boletin23ej9/redes.bin";
			Alumno.leerAlumnos(fichero);
			Alumno.procesarNotasAlumnos();
			Alumno.salvarAlumnosBinario(ficheroBinario);
		}
	}

	// ============================================================
	// EJERCICIO 5 · EXAMEN: personajes de anime a un fichero binario
	// Practica: dos ficheros de texto -> lista de objetos Personaje -> .dat -> leer y mostrar
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/ExamenAnime/Ejercicio2Main + Personaje
	//   (corrección del profesor: ACCESO_DATOS/ExamenFicherosSara/correccionExamen/Ejercicio2)
	// Ficheros: animes.txt ("17 Naruto") y personajes.txt ("17 Naruto Uzumaki")
	// Salida al leer el .dat: "Naruto Uzumaki(Naruto)"
	// ============================================================
	static class Ejercicio5_PersonajesAnime {

		static class Personaje implements Serializable {
			private String tituloAnime;
			private String nombrePersonaje;

			public Personaje(String titulo, String nombrePersonaje) {
				this.tituloAnime = titulo;
				this.nombrePersonaje = nombrePersonaje;
			}

			@Override
			public String toString() {
				String linea = this.nombrePersonaje + "(" + this.tituloAnime + ")";
				return linea;
			}
		}

		static String rutaPersonajes = "personajes.txt";
		static String rutaAnimes = "animes.txt";
		static String rutaFicheroDat = "personajes.dat";

		public static void main(String[] args) {
			HashMap<Integer, String> diccionarioAnimes = lecturaAnimes();
			ArrayList<Personaje> lista = listaPersonajeCoincidencia(diccionarioAnimes);
			leerFichero();
		}

		// leemos fichero animes
		public static HashMap<Integer, String> lecturaAnimes() {
			HashMap<Integer, String> dicAnimes = new HashMap<Integer, String>();
			try {
				BufferedReader lector = new BufferedReader(new FileReader(rutaAnimes));
				String linea;
				while ((linea = lector.readLine()) != null) {
					int posicionPimerEspacio = linea.indexOf(" ");
					int clave = Integer.parseInt(linea.substring(0, posicionPimerEspacio));
					String titulo = linea.substring(posicionPimerEspacio + 1);
					dicAnimes.put(clave, titulo);
				}
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
			return dicAnimes;
		}

		public static ArrayList<Personaje> listaPersonajeCoincidencia(HashMap<Integer, String> diccionarioAnime) {
			ArrayList<Personaje> listaPersonajes = new ArrayList<Personaje>();
			try {
				String linea;
				for (Map.Entry<Integer, String> anime : diccionarioAnime.entrySet()) {
					BufferedReader lector = new BufferedReader(new FileReader(rutaPersonajes));
					while ((linea = lector.readLine()) != null) {
						int clavePersonaje = Integer.parseInt(linea.substring(0, linea.indexOf(" ")));
						if (clavePersonaje == anime.getKey()) {
							String nombrePersonaje = linea.substring(linea.indexOf(" ") + 1);
							Personaje personaje = new Personaje(anime.getValue(), nombrePersonaje);
							listaPersonajes.add(personaje);
						}
					}
					lector.close(); // en el original faltaba este close()
				}
				try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(rutaFicheroDat))) {
					binario.writeObject(listaPersonajes);
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
				e.printStackTrace();
			}
			return listaPersonajes;
		}

		public static void leerFichero() {
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(rutaFicheroDat))) {
				ArrayList<Personaje> lista = (ArrayList<Personaje>) binario.readObject();
				for (Personaje p : lista) {
					System.out.println(p);
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// ============================================================
	// EJERCICIO 6 · Gimnasio: CSV -> clientes.dat, consultas y modificar un cliente
	// Practica: texto ";" -> objetos -> writeObject; leer la lista para filtrar;
	//           MODIFICAR = leer lista -> setSuscripcion -> volver a grabar
	// Origen: DAM_JAVA/PracticandoExtraordinaria/RA8_Serializacion/Gym_Main + Cliente
	// clientes_gym.csv: PedroSanchez;basico;3
	// ============================================================
	static class Ejercicio6_Gym {

		static class Cliente implements Serializable {
			private String nombre;
			private String suscripcion;
			private int mesesRestanteContrato;

			public Cliente(String nombre, String suscripcion, int mesesRestanteContrato) {
				this.nombre = nombre;
				this.suscripcion = suscripcion;
				this.mesesRestanteContrato = mesesRestanteContrato;
			}

			public String getNombre() {
				return nombre;
			}

			@Override
			public String toString() {
				String linea = this.nombre + " (" + this.suscripcion + ") - " + this.mesesRestanteContrato;
				return linea;
			}

			public String getSuscripcion() {
				return suscripcion;
			}

			public void setSuscripcion(String suscripcion) {
				this.suscripcion = suscripcion;
			}

			public int getMesesRestanteContrato() {
				return mesesRestanteContrato;
			}
		}

		public static void main(String[] args) {
			String rutaClientesGYM = "clientes_gym.csv";
			String rutaClientes = "clientes.dat";
			escribirClientes(rutaClientesGYM, rutaClientes);
			clientesPremiunElite(rutaClientes);
			clientesVencePronto(rutaClientes);
			cambiarSuscripcion(rutaClientes, "PedroSanchez");
		}

		public static void escribirClientes(String ficheroLectura, String ficheroEscritura) {
			Cliente cliente = null;
			ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
			try {
				BufferedReader lector = new BufferedReader(new FileReader(ficheroLectura));
				String linea;
				String[] listaCliente = new String[3];
				while ((linea = lector.readLine()) != null) {
					listaCliente = linea.split(";");
					cliente = new Cliente(listaCliente[0], listaCliente[1], Integer.parseInt(listaCliente[2]));
					listaClientes.add(cliente);
				}
				lector.close();
			} catch (Exception e) {
				System.out.println("Error al leer: " + e.getMessage());
			}
			// GUARDANDO LISTA
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(ficheroEscritura))) {
				binario.writeObject(listaClientes);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		// COMO HE GUARDADO UNA LISTA RECUPERO UNA LISTA
		public static void clientesPremiunElite(String ficheroBinario) {
			ArrayList<Cliente> lista = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(ficheroBinario))) {
				lista = (ArrayList<Cliente>) binario.readObject();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			System.out.println("Cliente premium o elite");
			for (Cliente cliente : lista) {
				if (cliente.getSuscripcion().equalsIgnoreCase("premium") || cliente.getSuscripcion().equalsIgnoreCase("elite")) {
					System.out.println(cliente);
				}
			}
		}

		public static void clientesVencePronto(String ficheroBinario) {
			ArrayList<Cliente> lista = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(ficheroBinario))) {
				lista = (ArrayList<Cliente>) binario.readObject();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			System.out.println("Clientes con poco contrato");
			for (Cliente cliente : lista) {
				if (cliente.getMesesRestanteContrato() <= 2) {
					System.out.println(cliente);
				}
			}
		}

		public static void cambiarSuscripcion(String ficheroBinario, String nombreCliente) {
			ArrayList<Cliente> lista = null;
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(ficheroBinario))) {
				lista = (ArrayList<Cliente>) binario.readObject();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
			System.out.println("Cambiar suscripcion cliente");
			for (Cliente cliente : lista) {
				if (cliente.getNombre().equalsIgnoreCase(nombreCliente)) {
					cliente.setSuscripcion("elite");
					System.out.println("Suscripcion cambiada: ");
					System.out.println(cliente);
				}
			}
			// GUARDANDO LISTA
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(ficheroBinario))) {
				binario.writeObject(lista);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	// ============================================================
	// EJERCICIO 7 · EXAMEN (IA) Veterinaria: visitas a binario y consultas
	// Practica: texto ";" (solo líneas con 4 campos) -> ArrayList<Visitas> -> .dat;
	//           leer el .dat, filtrar por especie y buscar la visita más cara
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/Examen_IA_Veterinaria/Examen2_RA8_Main + Visitas
	// visitas.txt: Laura Garcia;Luna;Perro;45.50
	// OJO: la línea "Ana Lopez;Rocky;Perro;abc" tiene 4 campos -> parseDouble lanza
	//   excepción y se sale del while: solo se guardan las visitas leídas hasta ahí.
	// ============================================================
	static class Ejercicio7_VeterinariaBinario {

		static class Visitas implements Serializable {
			private String nombreCliente;
			private String nombreMascota;
			private String especie;
			private double costeVisita;

			public Visitas(String nombreCliente, String nombreMascota, String especie, double costeVisita) {
				this.nombreCliente = nombreCliente;
				this.nombreMascota = nombreMascota;
				this.especie = especie;
				this.costeVisita = costeVisita;
			}

			public String getEspecie() {
				return especie;
			}

			public double getCosteVisita() {
				return costeVisita;
			}

			@Override
			public String toString() {
				String linea = "- " + this.nombreCliente + " llevo a " + this.nombreMascota;
				return linea;
			}
		}

		static String ficheroVisitas = "visitas.txt";
		static String binarioVisitasDat = "visitasBinario.dat";

		public static void main(String[] args) {
			ArrayList<Visitas> listaVisitas = leerVisitasGuardasVisitas();
			leerBinarioVisitas("Gato");
		}

		public static ArrayList<Visitas> leerVisitasGuardasVisitas() {
			ArrayList<Visitas> listaVisita = new ArrayList<Visitas>();
			Visitas visita = null;
			try {
				BufferedReader lector = new BufferedReader(new FileReader(ficheroVisitas));
				String linea;
				String[] lineaVisita = new String[4];
				while ((linea = lector.readLine()) != null) {
					lineaVisita = linea.split(";");
					if (lineaVisita.length == 4) {
						visita = new Visitas(lineaVisita[0], lineaVisita[1], lineaVisita[2],
								Double.parseDouble(lineaVisita[3]));
						listaVisita.add(visita);
					}
				}
				lector.close(); // en el original faltaba este close()
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage()); // en el original: e.getMessage(); sin imprimir
			}
			// ESCRIBIMOS TODO EL ARRAYLIST EN EL BINARIO
			try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(binarioVisitasDat))) {
				escritor.writeObject(listaVisita);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			return listaVisita;
		}

		public static void leerBinarioVisitas(String especieAnimal) {
			ArrayList<Visitas> lista = new ArrayList<Visitas>();
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(binarioVisitasDat))) {
				lista = (ArrayList<Visitas>) binario.readObject();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			Visitas visitaMasCara = lista.get(0); // asigna el primer objeto
			for (Visitas visita : lista) {
				if (visita.getEspecie().equalsIgnoreCase(especieAnimal)) {
					System.out.println(visita);
				}
				if (visita.getCosteVisita() > visitaMasCara.getCosteVisita()) {
					visitaMasCara = visita;
				}
			}
			System.out.println("La visita mas cara: " + visitaMasCara + "(" + visitaMasCara.getCosteVisita() + ")");
		}
	}

	// ============================================================
	// EJERCICIO 8 · EXAMEN ORDINARIA RA8: recuento de votos guardando un HashMap
	// Practica: serializar un HashMap<String, Integer>; si el fichero aún no
	//           existe, el catch vacío deja el HashMap vacío; leer -> sumar -> grabar
	// Origen: DAM_JAVA/UT7_Ficheros_RA5/ExamenOrdinaria/E1_RA8
	//   (versión con DataStreams y 4 int: FicherosBinarios_Datos_Ejercicios, Ej. 2)
	// ============================================================
	static class Ejercicio8_VotosHashMap {
		public static void main(String[] args) {
			String ficheroDAT = "recuentoVotos.dat";
			// (en el original estas llamadas están comentadas después de haber creado el fichero)
			escribirVotos(ficheroDAT, "PC", 134);
			escribirVotos(ficheroDAT, "PA", 10);
			escrutinio(ficheroDAT, 1730);
		}

		public static void escribirVotos(String fichero, String nombrePartido, int numVotos) {
			HashMap<String, Integer> diccionario = new HashMap<String, Integer>();
			// LEEMOS POR PRIMERA VEZ (si no existe el fichero, seguimos con el HashMap vacío)
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				diccionario = (HashMap<String, Integer>) binario.readObject();
			} catch (Exception e) {
			}
			// MODIFICAMOS
			if (diccionario.containsKey(nombrePartido)) {
				diccionario.put(nombrePartido, diccionario.get(nombrePartido) + numVotos);
			} else {
				diccionario.put(nombrePartido, numVotos);
			}
			// ESCRIBIMOS
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
				binario.writeObject(diccionario);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			System.out.println("Nuevos votos para el partido " + nombrePartido + ": " + numVotos);
			System.out.println("Votos hasta el momento: ");
			for (Map.Entry<String, Integer> partidoVotos : diccionario.entrySet()) {
				System.out.println("Partido " + partidoVotos.getKey() + ": " + partidoVotos.getValue());
			}
		}

		public static void escrutinio(String fichero, int censoPersonas) {
			HashMap<String, Integer> diccionario = new HashMap<String, Integer>();
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
				diccionario = (HashMap<String, Integer>) binario.readObject();
				int totalVotos = 0;
				double escrutinio = 0;
				for (Map.Entry<String, Integer> elementos : diccionario.entrySet()) {
					totalVotos = totalVotos + elementos.getValue();
				}
				escrutinio = ((double) totalVotos / censoPersonas) * 100;
				System.out.println("Resultados con un " + (int) escrutinio + "% de escrutinio:");
				for (Map.Entry<String, Integer> partidoVotos : diccionario.entrySet()) {
					System.out.println("Partido " + partidoVotos.getKey() + ": " + partidoVotos.getValue());
				}
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
}

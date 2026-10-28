package c06_DAT_a_BBDD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 * COMBINACIÓN 06: DAT → BBDD
 * ---------------------------------------------------------------------
 * ENTRADA: pokemon.dat
 *          un ArrayList<Pokemon> guardado con writeObject
 * SALIDA:  tabla pokemon de pokemondb (INSERT)
 *          tabla pokemon(numero_pokedex, nombre, peso, altura)
 * IDEA:    LEER el origen → List<Pokemon> → ESCRIBIR el destino
 * EN EL EXAMEN CAMBIA:
 *   - LEER DAT: nada si el .dat lo creas tú; si te lo dan, la clase tiene que llamarse igual y estar en el mismo paquete
 *   - ESCRIBIR BBDD: URL / USUARIO / PASSWORD y el SQL (tabla y columnas)
 *   - la clase Pokemon (atributos del enunciado)
 * =====================================================================
 */
public class Main_DAT_a_BBDD {

	public static final String PAQUETE = "c06_DAT_a_BBDD";

	// CAMBIAR: nombres de los ficheros (van dentro del paquete, en src)
	public static final String FICHERO_DAT = "src" + File.separator + PAQUETE + File.separator + "pokemon.dat";

	// CAMBIAR: conexión según dónde estés
	// CASA:  jdbc:mysql://localhost:3306/pokemondb    root   / abc123
	// CLASE: jdbc:mysql://172.17.0.1:3306/pokemondb   alumno / abc123
	public static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";

	// [MAIN] DAT → BBDD
	public static void main(String[] args) {
		crearDATdePrueba(FICHERO_DAT);   // SOLO PARA PROBAR: si en el examen te dan el .dat, quita esta línea

		List<Pokemon> listaPokemon = leerDAT(FICHERO_DAT);   // 1. LEER el origen → lista
		mostrarLista(listaPokemon);
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			escribirBBDD(conexion, listaPokemon);      // 2. ESCRIBIR el destino
		} catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());   // CON println, si no, no ves el error
		}
		// Para repetir la prueba, borra lo insertado en MySQL: DELETE FROM pokemon WHERE numero_pokedex > 151;
	}

	// ============================ LEER DAT ============================

	// [LEER DAT] binario de objetos (ArrayList<Pokemon>) → lista de pokémon
	// OJO: el .dat guarda el paquete de la clase: solo se lee con la MISMA clase Pokemon que lo escribió
	@SuppressWarnings("unchecked")
	public static List<Pokemon> leerDAT(String fichero) {
		List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
		File f = new File(fichero);
		if (f.exists() && f.length() > 0) {
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(f))) {
				listaPokemon = (ArrayList<Pokemon>) binario.readObject();   // se lee la lista entera de golpe
			} catch (Exception e) {
				System.out.println("Error al leer el DAT: " + e.getMessage());
			}
		} else {
			System.out.println("El fichero binario no existe o está vacío: " + fichero);
		}
		return listaPokemon;
	}

	// [PRUEBA] si no existe el .dat, lo crea con 4 pokémon de ejemplo (si en el examen te lo dan, no lo llames)
	public static void crearDATdePrueba(String fichero) {
		File f = new File(fichero);
		if (!f.exists()) {
			ArrayList<Pokemon> ejemplo = new ArrayList<Pokemon>();
			ejemplo.add(new Pokemon(25, "Pikachu", 6.0));
			ejemplo.add(new Pokemon(152, "Chikorita", 6.4));
			ejemplo.add(new Pokemon(155, "Cyndaquil", 7.9));
			ejemplo.add(new Pokemon(158, "Totodile", 9.5));
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(f))) {
				binario.writeObject(ejemplo);
				System.out.println("DAT de prueba creado: " + fichero);
			} catch (Exception e) {
				System.out.println("Error al crear el DAT de prueba: " + e.getMessage());
			}
		}
	}

	// ============================ ESCRIBIR BBDD ============================

	// [ESCRIBIR BBDD] lista de pokémon → INSERT en la tabla pokemon (salta los que ya existen)
	public static void escribirBBDD(Connection conexion, List<Pokemon> listaPokemon) throws SQLException {
		int filasInsertadas = 0;
		// CAMBIAR: tabla y columnas. altura es NOT NULL en pokemondb y no la tenemos → se guarda 0
		PreparedStatement insert = conexion.prepareStatement(
				"INSERT INTO pokemon (numero_pokedex, nombre, peso, altura) VALUES (?, ?, ?, ?)");
		for (Pokemon pokemon : listaPokemon) {
			if (existePokemon(conexion, pokemon.getCodigo())) {
				System.out.println("Ya existe, no se inserta: " + pokemon);
			} else {
				insert.setInt(1, pokemon.getCodigo());                // los ? se numeran de izquierda a derecha
				insert.setString(2, pokemon.getNombre());
				insert.setDouble(3, pokemon.getPeso());
				insert.setDouble(4, 0);
				filasInsertadas = filasInsertadas + insert.executeUpdate();   // executeUpdate → nº de filas
				System.out.println("Insertado: " + pokemon);
			}
		}
		System.out.println("Filas insertadas: " + filasInsertadas);
	}

	// [COMPROBAR] ¿existe ya ese numero_pokedex en la tabla? → true/false
	public static boolean existePokemon(Connection conexion, int codigo) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"SELECT numero_pokedex FROM pokemon WHERE numero_pokedex = ?");
		query.setInt(1, codigo);
		ResultSet resultado = query.executeQuery();
		boolean existe = resultado.next();                          // hay fila → existe
		return existe;
	}

	// ============================ MOSTRAR ============================

	// [MOSTRAR] imprime la lista leída, para comprobar que la lectura ha ido bien
	public static void mostrarLista(List<Pokemon> listaPokemon) {
		System.out.println("Pokémon leídos: " + listaPokemon.size());
		for (Pokemon pokemon : listaPokemon) {
			System.out.println(" - " + pokemon);
		}
	}
}

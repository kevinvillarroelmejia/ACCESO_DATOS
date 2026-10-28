package SQL_JSON;                                    // paquete del ejercicio

import java.sql.Connection;                          // tipo de la conexión que devolvemos
import java.sql.SQLException;                        // error que puede lanzar getConnection()

import org.apache.commons.dbcp2.BasicDataSource;     // el pool de Apache (necesita los 3 .jar de Apache)

// Clase sin main: solo gestiona un pool de conexiones a MySQL
public class Pool_ConexionesSimultaneas {

	// El pool: guarda las conexiones abiertas. static = uno solo para todo el programa
	private static BasicDataSource dataSource;

	// Bloque estático: se ejecuta UNA vez, cuando Java carga la clase (antes de usarla)
	static {
		dataSource = new BasicDataSource();          // crea el pool vacío

		//CREDENCIALES DE LA CONEXION
		String url = "jdbc:mysql://localhost:3306/pokemondb";
		String usuario = "alumno";
		String password = "abc123";

		dataSource.setUrl(url);                      // a qué servidor y base de datos se conecta
		dataSource.setUsername(usuario);             // con qué usuario
		dataSource.setPassword(password);            // con qué contraseña

		dataSource.setInitialSize(5);                // al empezar abre 5 conexiones y las deja esperando
		dataSource.setMaxTotal(100);                 // máximo 100 conexiones a la vez; si se piden más, esperan

		dataSource.setPoolPreparedStatements(true);  // guarda en caché los PreparedStatement para reutilizarlos
		dataSource.setMaxOpenPreparedStatements(50); // como mucho 50 PreparedStatement en caché por conexión
	}

	// Presta una conexión del pool. static: se llama sin crear objeto (Pool_ConexionesSimultaneas.getConnection())
	// Al cerrarla (fin del try-with-resources) no se cierra de verdad: vuelve al pool
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();           // devuelve una conexión libre del pool
	}
}
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Pool_ConexionesSimultaneas {

	//sin main

	//bloque se inicializacion estatica JAVA lo crea incluso antes del MAIN
	
	
	//objeto de los librerias que hemos IMPORTADO
	private static  BasicDataSource dataSource;
	
	//BLQUE ESTATICO
	//esto se ejecutara antes que se ejecute el programa
	static {
		dataSource=new BasicDataSource();
		
		//CREDENCIALES DE LA CONEXION
		String url="jdbc:mysql://172.17.0.1/DAM2";
		String usuario="alumno";
		String password="abc123";
		
		dataSource.setUrl(url);
		dataSource.setUsername(usuario);
		dataSource.setPassword(password);
		
		//un pool de cinco conexiones ENTRARAN DE 5 EN 5?
		dataSource.setInitialSize(5);
		
		//maximo seran 100 conexiones
		dataSource.setMaxTotal(100);
		
		
		//un pool de 50 de preparetStatement
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(50);
		

	}
	//METODO QUE GESTIONA LAS CONEXIONES
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	
}

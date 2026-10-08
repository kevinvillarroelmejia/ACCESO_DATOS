import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PrimeroConexion {

	public static void main(String[] args) {

		//LA DIRECCION IP DEL DOCKER
		//si queremos utilizar una 
		//base de datos especifica se pone despues de la / de la ip
		String url="jdbc:mysql://172.17.0.1/DAM2";
		String usuario="alumno";
		String password="abc123";
		
		//CUANDO ES UN QUERY COMPLEJA QUE NECESITA COSAS DE NUESTRO 
		//PROGRAMA ES MEJOR PreparedStatement
		
		//QUERY DEVUELVE DATOS
		//UPDATE DEVUELVE EXITO O FRANCASO
		try {
			Connection conexion=DriverManager.getConnection(url,usuario,password);
			
			//obteniendo datos
			//Statement para lanzar querys
			Statement sql=conexion.createStatement();
			//resulset es como ArrayList con un puntero 
			//apuntando a la posicion antes de la primera
			ResultSet resultado=sql.executeQuery("SELECT * FROM ALUMNOS");
			while(resultado.next()) {
				//Cada vez que hace next avanza en la tabla
				System.out.println(resultado.getString("EMAIL"));
			}
			conexion.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}

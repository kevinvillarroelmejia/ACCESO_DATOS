import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PrimeroConexion {

	public static void main(String[] args) {

		//LA DIRECCION IP DEL DOCKER
		//si queremos utilizar una 
		//base de datos especifica se pone despues de la / de la ip

		
		//CUANDO ES UN QUERY COMPLEJA QUE NECESITA COSAS DE NUESTRO 
		//PROGRAMA ES MEJOR PreparedStatement
		
		//QUERY DEVUELVE DATOS
		//UPDATE DEVUELVE EXITO O FRANCASO
		
		//UILIZAMOS TRY WITH RESOURCE
		try(Connection conexion=Pool_ConexionesSimultaneas.getConnection()) {
			//obteniendo datos
			//Statement para lanzar querys
			Statement sql=conexion.createStatement();
			
			//para desactivar el autoCommit
			conexion.setAutoCommit(false);
			
			sql.executeUpdate("DELETE FROM ALUMNOS WHERE EMAIL='kevinvillarroel@madrid.org'");
			//resulset es como ArrayList con un puntero 
			//apuntando a la posicion antes de la primera
			ResultSet resultado=sql.executeQuery("SELECT * FROM ALUMNOS");
			while(resultado.next()) {
				//Cada vez que hace next avanza en la tabla
				System.out.println(resultado.getString("EMAIL"));
			}
			
			//si queremos que se guarden los cambios tengo que hacer un commit manual
			
			//datos atomicos INVESTIGAR QUE SON Y COMO SE TRABAJAN CON ELLOS
			
			
			conexion.commit();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}

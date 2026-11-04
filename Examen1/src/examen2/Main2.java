package examen2;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;



public class Main2 {

	public static final String rutaJSON="src"+File.separator+"examen2"+File.separator+"marvel.json";
	
	public static final String URL = "jdbc:mysql://172.17.0.1:3306/ucm";
	
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";
	public static void main(String[] args) {

		
		//LO HE EJECUTADO EN CLASE Y ME FUNCIONA
		List<Marvel> listaMarvel=leerJSON(rutaJSON);
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
//			if(existeBBDD(URL, USUARIO, PASSWORD)) {
				escribirBBDD(conexion, listaMarvel);
				mostrarPelicula(conexion);
//			}
		}catch (SQLException e) {
			System.out.println("Error de BBDD: " + e.getMessage());  
		}
	}
	
	//leemos la lista y vamos metiendo los datos
	public static void escribirBBDD(Connection conexion, List<Marvel> listaMarvel) throws SQLException {
		int filasInsertadas = 0;
		PreparedStatement insert = conexion.prepareStatement(
				"INSERT INTO peliculas (titulo, anio, fase, director) VALUES (?, ?, ?, ?)");
		for (Marvel marvel : listaMarvel) {
				
				insert.setString(1, marvel.getTitulo());                
				insert.setInt(2, marvel.getAnio());
				insert.setInt(3, marvel.getFase());
				insert.setString(4, marvel.getDirector());
				
				filasInsertadas = filasInsertadas + insert.executeUpdate();
			}
		}
	
	
	public static void existeBBDD(String server, String usuario, String passwd) {
		boolean bandera=false;
		String query3 = "create table if not exists peliculas( titulo varchar (150) primary key, anio int not null,fase int not null,director varchar(100) not null);";

		try (Connection conexion = DriverManager.getConnection(server, usuario, passwd)) {
			System.out.println("Conexion realizada con exito");
			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(query3);
			bandera=true;
		} catch (Exception e) {
			System.out.println("ERROR CREACION BASE DE DATOS");
			e.printStackTrace();
		}
	}
	
	public static List<Marvel> leerJSON(String fichero) {
		List<Marvel> listaMarvels = new ArrayList<Marvel>();
		try (Reader lector = new FileReader(fichero)) {
			Gson gson = new Gson();
			Marvels marvels = gson.fromJson(lector, Marvels.class);       
			if (marvels != null && marvels.getMarvels() != null) {
				listaMarvels = marvels.getMarvels();                     
			}
		} catch (Exception e) {
			System.out.println("Error al leer el JSON: " + e.getMessage());
		}
		return listaMarvels;
	}
	
	public static void mostrarPelicula(Connection conexion) throws SQLException {
		PreparedStatement query = conexion.prepareStatement(
				"SELECT * FROM ucm.peliculas;");
		ResultSet resultado = query.executeQuery();
		while (resultado.next()) {
			System.out.println(resultado.getInt("anio")+" (Fase "+resultado.getInt("fase")+") - "+resultado.getString("titulo")+" ["+resultado.getString("director")+"]");
			
		}
		
	}
	

	

}

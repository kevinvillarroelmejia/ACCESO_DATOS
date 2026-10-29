package Examen1;

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

	public static final String rutaJSON="src"+File.separator+"Examen1"+File.separator+"marvel.json";
	public static final String URL_SERVIDOR = "jdbc:mysql://172.17.0.1:3306/";
	
	public static final String URL=URL_SERVIDOR+"ucm";
	
	public static final String USUARIO = "root";
	public static final String PASSWORD = "abc123";
	public static void main(String[] args) {

		
		//LO HE EJECUTADO EN CLASE Y ME FUNCIONA
		List<Marvel> listaMarvel=leerJSON(rutaJSON);
		existeBBDD(URL_SERVIDOR, USUARIO, PASSWORD);
		try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
			escribirBBDD(conexion, listaMarvel);
			mostrarPelicula(conexion);
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
		String crearBD = "CREATE DATABASE IF NOT EXISTS ucm";
		String crearTabla = "CREATE TABLE IF NOT EXISTS ucm.peliculas ("
				+ "titulo VARCHAR(150) PRIMARY KEY, anio INT NOT NULL, "
				+ "fase INT NOT NULL, director VARCHAR(100) NOT NULL)";		
		try (Connection conexion = DriverManager.getConnection(server, usuario, passwd)) {
			System.out.println("Conexion realizada con exito");
			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(crearBD);
			consulta.executeUpdate(crearTabla);
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

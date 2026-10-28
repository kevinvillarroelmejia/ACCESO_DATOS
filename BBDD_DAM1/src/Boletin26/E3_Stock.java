package Boletin26;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class E3_Stock {

	public static void main(String[] args) {
		//root --> casa 
		//admin --> clase
		
		/*
		 * INSERT INTO tabla (columna1, columna2, columna3) VALUES (?, ?, ?)"
		 * SELECT * FROM tabla WHERE columna = ?ç
		 * INSERT INTO tabla (columna1, columna2, columna3) VALUES (?, ?, ?)
		 * UPDATE tabla SET columna1 = ?, columna2 = ? WHERE columna3 = ?
		 * DELETE FROM tabla WHERE columna = ?
		 * */
		
		String usuario = "root";
		String password = "1234";
		String server = "jdbc:mysql://localhost:3306/classicmodels";
		try (Connection conexion = DriverManager.getConnection(server, usuario, password)) {
			System.out.println("Conexion realizada con exito");
			consultarStock(conexion, 15);
		} catch (SQLException e) {
//			e.getMessage();
			e.printStackTrace();
		}

	}
	private static void consultarStock(Connection conexion, int numero) throws SQLException {
		PreparedStatement query = conexion
				.prepareStatement("SELECT productName,productCode,quantityInStock FROM products WHERE quantityInStock <= ? ;"
						,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setInt(1, numero);
		ResultSet resultado = query.executeQuery();
		resultado.last();
		if (resultado.getRow() == 0) {
			System.out.println(" No se ha encontrado ningún producto con un stock inferior al indicado.");
		} else {
			resultado.beforeFirst();
			while (resultado.next()) {
				//almacenamos columnas
				String codigoProducto = resultado.getString("productCode");
			    String nombre = resultado.getString("productName");
			    int stock = resultado.getInt("quantityInStock");
			    
			    //buscamos en cuantos pedidos aparece
				query = conexion.prepareStatement(" select count(*) from orderdetails where productCode = ? ;",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				query.setString(1, codigoProducto);
				ResultSet resultadoPedidos=query.executeQuery();
				resultadoPedidos.next();
			    int pedidos = resultadoPedidos.getInt(1);
			    System.out.println(nombre + " | Stock: " + stock + " | Aparece en " + pedidos + " pedidos");
			}
		}
	}
}

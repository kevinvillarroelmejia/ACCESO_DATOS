package Boletin26;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class E1_apellidoEmpleado {

	public static void main(String[] args) {

		String usuario = "root";
		String password = "1234";

		String server = "jdbc:mysql://localhost:3306/classicmodels";

		try (Connection conexion = DriverManager.getConnection(server, usuario, password)) {
			System.out.println("Conexion realizada con exito");

			Scanner teclado = new Scanner(System.in);
			System.out.println("Apellido empleado: ");
			String apellido = teclado.nextLine();
			empleadoApellido(conexion, apellido);
		} catch (SQLException e) {
			System.out.println("ERROR 1" + e.getMessage());
		}
	}

	// Busca empleados por apellido y lista quién les reporta (subordinados)
	public static void empleadoApellido(Connection conexion, String apellido) throws SQLException {
		PreparedStatement query = conexion.prepareStatement("SELECT * from employees where lastName = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setString(1, apellido);
		ResultSet resultadoEmpleado = query.executeQuery();
		resultadoEmpleado.last();
		if (resultadoEmpleado.getRow() == 0) {
			System.out.println("No existe ningun empleado con ese apellido");
		} else {
			resultadoEmpleado.beforeFirst();
			while (resultadoEmpleado.next()) {
				System.out.println("Nombre empleado: " + resultadoEmpleado.getString("firstName"));
				int idEmpleado = resultadoEmpleado.getInt("employeeNumber");
				query = conexion.prepareStatement("SELECT firstName,lastName from employees where reportsTo = ?",
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				query.setInt(1, idEmpleado);
				ResultSet resultadoReportes = query.executeQuery();
				resultadoReportes.last();
				if (resultadoReportes.getRow() == 0) {
					System.out.println("A este empleado no lo reporto nadie");
				} else {
					resultadoReportes.beforeFirst();
					while (resultadoReportes.next()) {
						System.out.println("Empleado que le reporto: " + resultadoReportes.getString("firstName")
								+ "\nApellido: " + resultadoReportes.getString("lastName"));
					}
				}
			}
		}
	}

}

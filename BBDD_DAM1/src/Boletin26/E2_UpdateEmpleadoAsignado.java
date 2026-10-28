package Boletin26;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class E2_UpdateEmpleadoAsignado {

	public static void main(String[] args) {

		String usuario = "root";
		String password = "1234";
		String server = "jdbc:mysql://localhost:3306/classicmodels";
		try (Connection conexion = DriverManager.getConnection(server, usuario, password)) {
			System.out.println("Conexion realizada con exito");

			Scanner teclado = new Scanner(System.in);
			System.out.println("Nombre completo del cliente: ");
			String nombreCompleto = teclado.nextLine();
			modificarEmpleadoAsignado(conexion, nombreCompleto, 1166);
		} catch (SQLException e) {
			System.out.println("ERROR 1" + e.getMessage());
			e.printStackTrace();
		}
	}
	// Before start of result set SI DA ESTE ERROR ES POR QUE FALTA EL NEXT
	
	// [MODIFICAR BD] cambia el empleado asignado a un cliente (UPDATE, classicmodels)
	private static void modificarEmpleadoAsignado(Connection conexion, String nombreCompleto, int numeroEmpleadoNuevo)
			throws SQLException {
		boolean banderaEmpleado = false;
		boolean banderaCliente=false;
		PreparedStatement query = conexion.prepareStatement("select * from customers where customerName = ?;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setString(1, nombreCompleto);
		ResultSet resultadoCliente = query.executeQuery();
		resultadoCliente.last();
		// contemplar que el cliente no existe
		if (resultadoCliente.getRow() == 0) {
			System.out.println("Este cliente no existe");
		} else {
			banderaCliente = true;
			int numeroEmpleado = resultadoCliente.getInt("salesRepEmployeeNumber");
			if (resultadoCliente.wasNull()) {
				System.out.println("Este cliente no tiene empleado asignado");
			}
		}

		// que el numero del empleado sea erroneo
		query = conexion.prepareStatement("select * from employees where employeeNumber = ?;",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query.setInt(1, numeroEmpleadoNuevo);
		ResultSet resultadoEmpleado = query.executeQuery();
		resultadoEmpleado.last();
		if (resultadoEmpleado.getRow() == 0) {
			System.out.println("Este numero de empleado no existe");
//			resultadoEmpleado.beforeFirst();
		} else {
			banderaEmpleado = true;
		}
		if (banderaCliente&&banderaEmpleado) {//si las dos banderas son true...
			// CONSULTA ANTIGUO EMPLEADO
			query = conexion.prepareStatement(
					"SELECT employees.*,salesRepEmployeeNumber FROM customers JOIN employees ON employees.employeeNumber = customers.salesRepEmployeeNumber where customers.customerName = ?;",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			query.setString(1, nombreCompleto);
			ResultSet resultadoBueno = query.executeQuery();

			// o que el cliente no tenga asignado ningun empleado
			while (resultadoBueno.next()) {
				// MOSTRANDO ANTIGUO EMPLEADO
				System.out.println("Nombre ANITGUO empleado: " + resultadoBueno.getString("firstName")
						+ "\nApellido ANITGUO empleado: " + resultadoBueno.getString("lastName"));
			}
			// CONSULTA NUEVO EMPLEADO
			query = conexion.prepareStatement(
					"SELECT employees.firstName,employees.lastName from employees where employeeNumber=?");
			query.setInt(1, numeroEmpleadoNuevo);
			ResultSet resultadoNuevoEmpleado = query.executeQuery();
			while (resultadoNuevoEmpleado.next()) {
				System.out.println("Nombre NUEVO empleado: " + resultadoNuevoEmpleado.getString("firstName")
						+ "\nApellido NUEVO empleado: " + resultadoNuevoEmpleado.getString("lastName"));
			}
			// ASIGNAR NUEVO EMPLEADO
			query = conexion.prepareStatement(
					"update customers set salesRepEmployeeNumber= ? where customers.customerName = ?;");
			query.setInt(1, numeroEmpleadoNuevo);
			query.setString(2, nombreCompleto);
			int resultadoUpdate = query.executeUpdate();
			System.out.println("Filas afectadas: " + resultadoUpdate);
		}
	}

}

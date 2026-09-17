package ejs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class ej7 {
	public static void main(String[] args) {
		String fichero = "/home/alumno/login.txt";
		HashMap<String, String> diccionario = leerFichero(fichero);
		// comprobarUsuario(diccionario);
		nuevoUsuario(diccionario, fichero);
	}

	public static HashMap<String, String> leerFichero(String fichero) {
		HashMap<String, String> diccionario = new HashMap<>();
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea;
			while ((linea = lector.readLine())!=null) {
				int posicion = linea.indexOf(":");
				diccionario.put(linea.substring(0,posicion), linea.substring(posicion+1));
			}
			if (diccionario.size() == 0) {
				System.out.println("Fichero vacío");
			}
		} catch (Exception e) {
			System.out.println("Fichero inexistente o imposible acceder a él");
		}
		return diccionario;

	}

	public static void comprobarUsuario(HashMap<String, String> diccionario) {
		Scanner lector = new Scanner(System.in);
		System.out.print("Escribe un usuario para buscar en nuestro registro: ");
		String usuario = lector.nextLine();
		System.out.print("Escribe la constraseña del usuario: ");
		String password = lector.nextLine();
		lector.close();
		if (diccionario.containsKey(usuario) == false) {
			System.out.println("Usuario no encontrado");
		} else if (diccionario.containsValue(password) == false) {
			System.out.println("Constraseña incorrecta");
		} else {
			System.out.println("Login correcto");
		}
	}
	public static void nuevoUsuario(HashMap<String, String> diccionario, String fichero) {
		// Dar de alta usuario que ya exista error (que no tenga ":")
		// Si el usuario no existe pedimos la contraseña 2 veces (que sean iguales y que tenga ":")
		// Escribimos en el fichero usando append
		Scanner lector = new Scanner(System.in);
		System.out.print("Escribe un nombre de usuario nuevo: ");
		String usuario = lector.nextLine();
		System.out.print("Escribe una constraseña de usuario: ");
		String password = lector.nextLine();
		System.out.print("Escribe la misma constraseña de usuario: ");
		String passwordRep = lector.nextLine();
		lector.close();
		if (password.equals(passwordRep) == false) {
			System.out.println("Las contraseñas no coinciden");
		} else if (diccionario.containsKey(usuario)) {
			System.out.println("El usuario ya existe");
		} else if (usuario.indexOf(":")>=0 || password.indexOf(":")>=0) {
			System.out.println("Ni el nombre de usuario ni la contraseña pueden contener el caracter ':'");
		} else {
			System.out.println("Grabando en el fichero...");
			grabarUsuarioFichero(usuario, password, fichero);
		}
	}

	private static void grabarUsuarioFichero(String usuario, String password, String fichero) {
		try(PrintWriter pluma = new PrintWriter(new FileWriter("/home/alumno/login.txt", true))) {
			pluma.printf("%s:%s", usuario, password);
			pluma.println();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}
}

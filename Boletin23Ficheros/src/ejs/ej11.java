package ejs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class ej11 {
	public static void main(String[] args) {
		String fichero = "/home/alumno/login.txt";
		HashMap<String, String> diccionario = leerFichero(fichero);
		comprobarUsuario(diccionario);
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
}

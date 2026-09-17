package ejs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ej5 {
	public static void main(String[] args) {
		Path nombreFichero = Path.of("/home/alumno/estadisticas.txt");
		ArrayList<String> lineas = null;
		try {
			lineas = (ArrayList<String>)Files.readAllLines(nombreFichero);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		if (lineas != null) {
			int contadorHombre = 0;
			int contadorMujer = 0;
			double sumaAlturas = 0;
			for (String linea: lineas) {
				if (linea.equals("Hombre")) {
					contadorHombre++;
				} else if (linea.equals("Mujer")) {
					contadorMujer++;
				} else {
					sumaAlturas+=Double.parseDouble(linea);
				}
			}
			double media = sumaAlturas/(contadorHombre+contadorMujer);
			System.out.printf("Hombres: %d.\n", contadorHombre);
			System.out.printf("Mujeres: %d.\n", contadorMujer);
			System.out.printf("Estatura media: %.2f", media);
		} else {
			System.out.println("Error o fichero vacío.");
		}
		
	}
}

package Ejercicios_Ficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class E3 {
	private static final String FICHERO_PAISES = "datos" + File.separator + "paises.csv";

	public static void main(String[] args) throws IOException {
		leerFichero();
	}

	public static void leerFichero()  {
		try {
			BufferedReader lector = new BufferedReader(new FileReader(FICHERO_PAISES));
			String linea;
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
			}
			lector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

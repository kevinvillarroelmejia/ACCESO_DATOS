package Paises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MainPaisesCSV_Lectura {

	public static void main(String[] args) {
		String ficheroCSV = "src" + File.separator + "Paises" + File.separator + "paises.csv";

		leerCSV_Paises(ficheroCSV);

	}

	public static void leerCSV_Paises(String ficheroCSV) {
		try (BufferedReader lector = new BufferedReader(new FileReader(ficheroCSV))) {
			String linea;
			String[] listaLinea = null;

			ArrayList<String> arrayListPaises = new ArrayList<String>();
			ArrayList<String> arrayListCapitales = new ArrayList<String>();
			ArrayList<String> arrayListMonedas = new ArrayList<String>();
			ArrayList<String> arrayListAnimales = new ArrayList<String>();

			lector.readLine();
			
				while ((linea = lector.readLine()) != null) {
					listaLinea = linea.split(",");
					if (listaLinea.length == 4) {
						arrayListPaises.add(listaLinea[0]);
						arrayListCapitales.add(listaLinea[1]);
						arrayListMonedas.add(listaLinea[2]);
						arrayListAnimales.add(listaLinea[3]);
					}
				}
				
				//CONTEMPLA SI NO HAY DATOS EN EL FICHERO
				if(arrayListPaises.isEmpty()) {
					System.out.println("No hay datos de ningun pais en el fichero");
				}else {
					System.out.print("Nombres: ");
					contemplarComa(arrayListPaises);

					System.out.print("\nLas capitales de los mismos son: ");
					contemplarComa(arrayListCapitales);

					System.out.print("\nSus monedas oficiales son: ");
					contemplarComa(arrayListMonedas);

					System.out.print("\nSus animales mas representativos son: ");
					contemplarComa(arrayListAnimales);
				}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	// METODO QUE TE CONTEMPLA AL COMA
	private static void contemplarComa(ArrayList<String> lista) {
		for (int i = 0; i < lista.size(); i++) {
				if (i == lista.size() - 1) {
					System.out.print(lista.get(i));
				} else if (i == lista.size() - 2) {
					System.out.print(lista.get(i) + " y ");
				} else {
					System.out.print(lista.get(i) + ", ");
				}
			}
		}
	}



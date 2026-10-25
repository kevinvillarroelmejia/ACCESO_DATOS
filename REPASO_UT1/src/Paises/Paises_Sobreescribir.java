package Paises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Paises_Sobreescribir {
	/*
	 * 
	 * 4. Escribe un programa en Java que lea del fichero del ejercicio anterior y
	 * lo sobreescriba con el mismo contenido pero prescindiendo del nombre de la
	 * moneda. Por ejemplo, si leemos un fichero con el contenido del ejemplo
	 * mostrado antes debería de escribir lo siguiente: País,Capital,Animal
	 * Australia,Canberra,Canguro Canadá,Ottawa,Castor Madagascar,Antananarivo,Lémur
	 * China,Pekín,Oso panda gigante Perú,Lima,Vicuña Nueva Zelanda,Wellington,Kiwi
	 * NOTAS: - Las consideraciones del ejercicio anterior son todas aplicables a
	 * este: el fichero podría tener otro contenido o estar vacío. La primera línea
	 * es de cabecera y no se toca. Las líneas erróneas (con una estructura
	 * diferente de 4 palabras separadas por coma) se ignoran y no se escriben en el
	 * fichero de nuevo, etc. - El fichero que escribimos con el resultado debe de
	 * llamarse igual que el original (paises.csv)
	 */

	public static void main(String[] args) {

		String ficheroCSV = "src" + File.separator + "Paises" + File.separator + "paises2.csv";
		sobreEscribirCSV(ficheroCSV);
	}
	
	public static void  sobreEscribirCSV(String ficheroCSV) {
		ArrayList<String> listaSinMonedas=new ArrayList<String>();
		try{
			listaSinMonedas.add("paises,Capital,Animal");
			BufferedReader lector = new BufferedReader(new FileReader(ficheroCSV));
			String linea;
			lector.readLine();
			while ((linea = lector.readLine()) != null) {
				String[] listaLinea = linea.split(",");
				if(listaLinea.length==4) {
					listaSinMonedas.add(listaLinea[0]+","+listaLinea[1]+","+listaLinea[3]);
				}
			}
			lector.close();
			Files.write(Path.of(ficheroCSV), listaSinMonedas, StandardCharsets.UTF_8);
		}catch (Exception e) {
		}
	}
}

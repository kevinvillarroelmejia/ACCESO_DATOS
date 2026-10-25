package Coordenadas;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Main_Coordenadas_DAT {

	public static void main(String[] args) {
		String ficheroDAT = "src" + File.separator + "Coordenadas" + File.separator + "coordenadas.dat";

		recorrerConAvailable(ficheroDAT);
		
	}
	public static void recorrerConAvailable(String ficheroBinario) {
		try (DataInputStream binario = new DataInputStream(new FileInputStream(ficheroBinario))) {
			while (binario.available() > 0) { // bytes que quedan
				int idSatelite = binario.readInt();
				float latitud=binario.readFloat();
				String longitud=""+binario.readFloat();
				// OJO: si sumo char con + y a la izquierda no hay ningún String, Java suma sus
				// códigos numéricos y el resultado es un int (ej. 'A'+'C'+'T'+'V' = 302), no "ACTV".
				// Para concatenar letras la suma tiene que empezar con un String (aunque sea "").
				
				String estado1=""+binario.readChar();
				String estado2=""+binario.readChar();
				String estado3=""+binario.readChar();
				String estado4=""+binario.readChar();

				String estadoConvertido=String.valueOf(estado1+estado2+estado3+estado4);

				System.out.println("Satelite ID: "+idSatelite+" | Posicion: ("+latitud+", "+longitud+") | Estado: "+estadoConvertido);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}

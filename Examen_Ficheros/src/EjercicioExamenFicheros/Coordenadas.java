package EjercicioExamenFicheros;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Coordenadas {
	/*
	 * 7. La agencia espacial para la que trabajas ha interceptado un archivo
	 * binario secreto llamado coordenadas.dat que ta habrás descargado del aula
	 * virtual. Este archivo contiene la ubicación de un satélite perdido La
	 * estructura del archivo es la siguiente: - El archivo contiene una secuencia
	 * de registros. No sabemos cuantos son - Cada registro ocupa exactamente 20
	 * bytes con la siguiente estructura: - ID del Satélite: Un número entero (int,
	 * 4 bytes). - Latitud: Un número decimal (float, 4 bytes). - Longitud: Un
	 * número decimal (float, 4 bytes). - Código de Estado: Cuatro caracteres de
	 * texto (char a 2bytes cada uno, un total de 8bytes) Te muestro a continuación
	 * el contenido de los dos primeros registros con el formato con el que debes de
	 * mostrarlos en pantalla: 
	 * 
	 * Satélite ID: 101 | Posición: (40,4167, -3,7037) | Estado: ACTV 
	 * Satélite ID: 202 | Posición: (-22,9068, -43,1729) | Estado: WARN
	 * 
	 * Te han encargado que crees un programa en Java que lea de este archivo
	 * binario y muestre todos los registros que están guardados en él de la forma
	 * que se ha descrito
	 */
	final static String ruta="coordenadas.dat";
	
	//ESTA SOLUCION ES MEJOR POR QUE FUNCIONA POR QUE FUNCIONA CON CUALQUIER TAMAÑO DE FICHEROS
	public static void main(String[] args) {
		final int TAMAYO_REGISTROS=20;//tamaño de cada linea
		try (DataInputStream fichero=new DataInputStream(new FileInputStream(ruta))){
			File ficheroFisico=new File(ruta);//REFERENCIA AL FICHERO
			//NECESITA CASTEO
			final int NUM_REGISTROS=(int)ficheroFisico.length()/TAMAYO_REGISTROS;
			System.out.println("SATELITES Y COORDENADAS");
			for(int i=0;i<NUM_REGISTROS;i++) { //SI NO SABEMOS EL TAMAÑO DEL FICHERO PODEMOS UTILIZAR WHILE (TRUE)
				//LEYENDO 
				int id=fichero.readInt();
				float latitud=fichero.readFloat();
				float longitud=fichero.readFloat();
				String estado="";
				for(int j=0;j<4	;j++) {
					estado+=fichero.readChar();
				}
				System.out.printf("Satelite ID: %d | Posicion: (%4f,%4f) | Estado: %s\n",id,latitud,longitud,estado);
			}
		}catch (Exception e) {
			System.out.println("ERROR AL LEER EL FICHERO - 1 ");
		}

	}
}

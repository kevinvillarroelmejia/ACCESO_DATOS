package FicherosSistema;

import java.io.File;

public class Ficheros1Sistema {

	//constante por que siempre sera ese nombre
	private static final String DIR_CONFIG="DAM2";
	
	public static void main(String[] args) {

		//Vamos a trabajar con ficheros
		//con esto estamos simbolizando el directorio actual --> .
		File directorioActual= new File(".");
		
		//con esto obtengo la ruta absoluta de la direccion del fichero
		System.out.println(directorioActual.getAbsolutePath());
		
		
		
		
		
	}

}

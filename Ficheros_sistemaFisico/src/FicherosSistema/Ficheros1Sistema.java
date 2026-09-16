package FicherosSistema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ficheros1Sistema {

	//constante por que siempre sera ese nombre
	//File.separator identifica el separador es decir en windows \ y si estamos en Linux /
	private static final String DIR_CONFIG="DAM2"+File.separator+"josemaria";
	private static final String variosDirectorios="DAM3/kevinrashid";
	private static final String ficheroTXT="DAM3"+File.separator+"kevinrashid"+File.separator+"config.txt";
	
	public static void main(String[] args)  {
		
		
		//Vamos a trabajar con ficheros
		//con esto estamos simbolizando el directorio actual --> .
		File directorioActual= new File(".");
		//con esto obtengo la ruta absoluta de la direccion del fichero
		System.out.println(directorioActual.getAbsolutePath());
		
		//objeto creado para comprobar si existe el fichero en la ruta actual
		File dirConfig=new File(DIR_CONFIG);
		if(dirConfig.exists()) {
			System.out.println("El directorio "+DIR_CONFIG+" existe");
		}else {
			System.out.println("El directorio "+DIR_CONFIG+" NO existe");
			//CREANDO DIRECTORIO
			dirConfig.mkdir();
		}
		File dirConfigVariosFicheros=new File(variosDirectorios);
		//creando varios DIRECTORIOS a la vez mkdirs en plural
		dirConfigVariosFicheros.mkdirs();
		
		
		//CREANDO UN .txt dentro de la ruta del directorio actual
		//ahora dentro de kevinrashid necesitamos crear un fichero config.txt
		try {
			FileWriter txt=new FileWriter(ficheroTXT,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}

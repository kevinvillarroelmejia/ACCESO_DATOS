package FicherosSistema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ficheros1Sistema {

	// constante por que siempre sera ese nombre
	// File.separator identifica el separador es decir en windows \ y si estamos en
	// Linux /
	private static final String DIR_CONFIG = "DAM2" + File.separator + "josemaria";
	private static final String variosDirectorios = "DAM3/kevinrashid";
	private static final String ficheroTXT = "DAM3" + File.separator + "kevinrashid" + File.separator + "config.txt";

	// REFERENCIA DE RUTA RELATIVA AL FICHERO FICHERO QUE QUIERO CREAR
	private static final String ARCHIVO_CONFIG = DIR_CONFIG + File.separator + "config.txt";

	public static void main(String[] args) {
		try {
			// Vamos a trabajar con ficheros
			// con esto estamos simbolizando el directorio actual --> .
			File directorioActual = new File(".");
			// con esto obtengo la ruta absoluta de la direccion del fichero
			System.out.println(directorioActual.getAbsolutePath());

			// objeto creado para comprobar si existe el fichero en la ruta actual
			File dirConfig = new File(DIR_CONFIG);
		
			
			boolean crearFichero = true;
			// REPASAR ESTO
			if (dirConfig.exists()) {
				System.out.println("El directorio " + DIR_CONFIG + " existe");
			} else {
				System.out.println("El directorio " + DIR_CONFIG + " NO existe");
				// CREANDO DIRECTORIO
				if (dirConfig.mkdir()) { // devuelve un BOOLEAN
					crearFichero = false;
					System.out.println("No he podido crear el directorio");
				}
				;
			}
			// =====METODO PARA CREAR UN FICHERO QUE NO EXISTE=====
			if (crearFichero) { // SI ES VERDADERO...
				FileWriter escritor = new FileWriter(ARCHIVO_CONFIG, true);
				if (escritor == null) {
					System.out.println("No he podido crear elñ archivo");
				} else {
					System.out.println("Archivo creado (o ya existia)");
					escritor.close();
					//ESTE ES OTRO METODO PERO PODEMOS SALIR DE AQUI SIN SABER SI SE HA CREADO, mejor utilizar el anterior
					File archivo=new File(ARCHIVO_CONFIG);
					if(archivo.createNewFile()) {//devuelve un boolean
						System.out.println("Archivo creado");
					}else {
						System.out.println("No puedo crearlo o ya existe");
					}
				}
			}
			//PARA SABER EL ESPACIO EN DISCO
			//para ponerlo en en GB
			//Se divide (/) o se multiplica (*)
			//por que te lo da en bytes
			long espacio=dirConfig.getFreeSpace()/1024/1024/1024;
			System.out.println("Espacio libre en disco "+espacio);
			
			
			/*
			File dirConfigVariosFicheros = new File(variosDirectorios);
			// creando varios DIRECTORIOS a la vez mkdirs en plural
			dirConfigVariosFicheros.mkdirs();

			// CREANDO UN .txt dentro de la ruta del directorio actual
			// ahora dentro de kevinrashid necesitamos crear un fichero config.txt
			 */
		} catch (Exception e) {
			System.out.println("Error al manipular los ficheros");

		}
		
	}
}

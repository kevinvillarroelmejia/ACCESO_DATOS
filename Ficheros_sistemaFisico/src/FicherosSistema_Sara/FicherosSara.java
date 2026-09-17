package FicherosSistema_Sara;

import java.io.File;

public class FicherosSara {

	/*
	 * Definimos una variable final y estética con el nombre de nuestra app para
	 * comprobar si existe o no, hacemos una ruta relativa
	 */

	/*
	 * Para que nuestra ruta funcione tanto en Windows como en linux debemos usar el
	 * File.separator para que identifique el separador entre directorios de nuestra
	 * ruta según el S.O que estemos usando
	 */

	/*
	 * Para que quedara "bonito" tendríamos que pedir por teclado al usuario el
	 * nombre del directorio que quisiera poner, y en base a su nombre añadiríamos
	 * el direcorio
	 */

	private static final String DIR_CONFIG = "DAM2" + File.separator + "saragarcia";
	// Creamos una constante para el archivo de configuración
	private static final String ARCHIVO_CONFIG = DIR_CONFIG + File.separator + "config.txt";

	public static void main(String[] args) {
		// Envolvemos todo en un try catch para la creacción de ficheros
		try {

			/*
			 * Un usuario puede ver la ruta del workspace pero el programa no sabe
			 * donde se encuentran los archivos con los que se trabaja, hay que
			 * decirselo al programa
			 * Existen 2 directorios especiales el . que indica el directorio actual
			 * y con .. indicamos el directorio padre del actual
			 */

			File directorioActual = new File(".");
			// Con getAbsolutePath extraemos la ruta actual
			System.out.println(directorioActual.getAbsolutePath());
			/*
			 * Debemos de comprobar si es la primera vez que se ha ejecutado nuestra app
			 * o no, para ello miraremos si existe ya en la ruta un directorio con el nombre
			 * de nuestra app
			 */

			File dirConfig = new File(DIR_CONFIG);

			/*
			 * .exists nos dice si existe o no el archivo o directorio al que hace
			 * referencia el objeto que hemos creado
			 */
			boolean crearFichero = true;
			if (dirConfig.exists() == true) {
				System.out.println("El directorio " + DIR_CONFIG + " existe");
			} else {
				System.out.println("El directorio " + DIR_CONFIG + " no existe");
				// Creamos el directorio si no existe, pero solo puede crear un directorio
				/*
				 * Creamos el directorio si no existe, pero mkdir solo puede crear un
				 * directorio no puede crear una estructura de más de un directorio como
				 * por ejemplo DAM2/saragarcia, para eso tendremos que usar mkdirs que funciona
				 * tanto con un directorio como con 2 así que mejor usar ese
				 * mkdirs devuelve una variable booleana
				 */
				// dirConfig.mkdir();
				// dirConfig.mkdirs();

				if (dirConfig.mkdirs() == false) {
					crearFichero = false;
					System.out.println("No he podido crear el directorio");
				}
			}

			/* -- MÉTODOS DE CREACCIÓN DE FICHEROS -- */
			/*
			 * Una vez que se ejecuta la creacción de directorios vamos a crear el fichero
			 * de
			 * configuración dentro de la carpeta
			 */
			// 1. MÉTODO

			/*
			 * if (crearFichero == true) {
			 * FileWriter escritor = new FileWriter(DIR_CONFIG+File.separator+"config.txt",
			 * true);
			 * if (escritor == null) {
			 * System.out.println("No se ha podido crear el archivo");
			 * } else {
			 * System.out.println("Archivo creado (o ya existía)");
			 * escritor.close();
			 * }
			 * }
			 */

			// 2. MÉTODO

			File archivo = new File(ARCHIVO_CONFIG);

			/*
			 * .createNewFile tenemos que comprobar depsués de usarlo si podemos escribir en el fichero
			 * o no aún, ya que devuelve un booleano true si lo ha creado o un booleano false si por
			 * algún motivo no puede crear el archivo o ya existe, no salimos con garantía de que exista
			 * ya que si el false que nos devuelve es porque por alguna razón no puede crear el archivo,
			 * nos devolvería un false sin el archivo creado, el primer método si que salimos con la
			 * garantía de que el fichero esta creado.
			 * En este caso deberíamos de usar .exist para comprobar que nuestro archivo se ha creado,
			 * requiere una comprovación extra que el primer método no necesita
			 */

			if (archivo.createNewFile()) {
				System.out.println("Archivo creado");
			} else {
				System.out.println("No puedo crearlo o ya existe");

			}

			/*
			 * A veces los errores por los que no se pueden crear diretorios o archivos es
			 * por falta de
			 * 
			 * espacio en el disco por lo que podemos usar .getFreeSpace para extraer el
			 * espacio libre en
			 * 
			 * disco en bytes, si queremos extraer en GB dividiremos entre 1024 3 veces.
			 * 
			 * En el caso de que no quedara ni un GB de espacio en el disco deberíamos tener
			 * en cuenta
			 * 
			 * los decimales ya que sino nos daría de resultado 0 cuando no sería el
			 * resultado real
			 * 
			 */

			long espacio = dirConfig.getFreeSpace() / 1024 / 1024 / 1024;

			System.out.println("Espacio libre en el disco: " + espacio);

		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());

		}

	}

}

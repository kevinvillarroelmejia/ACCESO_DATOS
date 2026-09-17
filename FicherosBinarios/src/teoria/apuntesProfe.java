package teoria;

import java.io.RandomAccessFile;
import java.util.HashMap;

public class apuntesProfe {

	// Usar variables globales está mal visto y hay que limitarlo lo mas posible
	// Usar constantes globales, sin embargo, resulta muy útil.
	// En este caso definimos aquí los tamaños de los datos que vamos a usar de forma
	// que podamos cambiarlo en un único punto si lo necesitamos
	static final int TAMANYO_NOMBRE = 20; // caracteres. un caracter ocupa 2 bytes
	static final int TAMANYO_EDAD = 4; // bytes. un entero ocupa 4 bytes
	static final int TAMANYO_REGISTRO = TAMANYO_NOMBRE *2 + TAMANYO_EDAD;
	
	public static void main(String[] args) {
		// El acceso aleatorio a un fichero nos permite dirigirnos directamente a un dato concreto sin necesidad
		// de pasar por los demas. Lo mas importante para ello es que los "registros" de datos tengan un
		// tamaño fijo.

		// Vamos a crear una pequeña agenda en un fichero. Los registros se componen de nombre y edad
		// Cargamos los datos en un diccionario inicialmente
		String fichero = "registros.dat";
		HashMap<String, Integer> agenda = new HashMap<>();
		agenda.put("Isabel", 35);
		agenda.put("Marcos", 51);
		agenda.put("José María", 57);
		agenda.put("Luis", 23);
		
		try {
			// creamos el fichero
			crearRegistro(fichero, agenda);
			// leemos el registro número 2
			leerRegistro(fichero,2);
			// modificamos el contenido del registro número 2
			modificarRegistro(fichero, 2, "José Miguel", 56);
			// intentamos modificar un registro que no existe
			modificarRegistro(fichero, 200, "Luis Miguel", 31);
			leerRegistro(fichero,2);
			// intentamos leer un registro que no existe
			leerRegistro(fichero,500);
			// leemos todos los registros
			leerTodosLosRegistros(fichero);
			// Añadimos un registro nuevo
			//anyadeRegistro(fichero,"Armando", 35);
			leerRegistro(fichero,5);
			// Borramos el registro 3
			borrarRegistro(fichero,3);
			// tratamos de borrar, leer o modificar un registro marcado como borrado
			borrarRegistro(fichero,3);
			leerRegistro(fichero,3);
			modificarRegistro(fichero, 3, "José Miguel", 56);
			leerTodosLosRegistros(fichero);
			
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	// otra forma de tratar las excepciones: se capturan todas desde el programa principal
	// en los métodos añadimos la clausula throws Exception (o el nombre de la excepción concreta)
	// cuando se produzca esa excepción el método deja de ejecutarse y vuelve al programa principal
	// y se ejecuta allí el bloque catch
	public static void crearRegistro (String fichero, HashMap<String, Integer> agenda) throws Exception{
		// el modo rw permite leer y escribir y crea el fichero si no existe. No existe modo w como en los ficheros de texto
		// los modos rws y rwd son similares pero escriben directamente a disco y no a cache.
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {

			for (String nombre : agenda.keySet()) {
                escribirNombre(raf, nombre);
                raf.writeInt(agenda.get(nombre));  // Los enteros se graban con 4 bytes
            }      
            System.out.println("Archivo creado con " + agenda.size() + " registros");
            // length nos devuelve el tamaño en bytes del archivo. Nos va a ayudar a ver que todo vaya bien
            System.out.println("Tamaño total del archivo: " + raf.length() + " bytes");
        }
	}
	
	// Método para escribir un nombre con tamaño fijo
    private static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
    	// Crear un array de caracteres del tamaño fijo
        char[] chars = new char[TAMANYO_NOMBRE];     
        // Llenar con espacios o con el nombre
        for (int i = 0; i < TAMANYO_NOMBRE; i++) {
            if (i < nombre.length()) {
                chars[i] = nombre.charAt(i);
            } else {
                chars[i] = ' ';  // Rellenar con espacios
            }
        }
        // Escribir cada caracter como char (2 bytes cada uno)
        for (char c : chars) {
            raf.writeChar(c);
        }
    }
    
    private static void leerRegistro(String fichero, int registro) throws Exception {
    	// el modo r es de solo lectura. provoca excepción si el fichero no existe
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
            // Calcular la posicion donde empezamos a leer
    		// El registro 1 es el primero (y empieza en la posición 0)
            long offset = (registro-1) * TAMANYO_REGISTRO;
            if(offset >=raf.length()) {
            	System.out.println("No existe el registro " + registro);
            	System.out.println("El registro mas alto es el " + raf.length()/TAMANYO_REGISTRO);
            }
            else {
            	// seek nos permite posicionarnos en un punto del fichero
            	// offset es un número de bytes a partir del principio del fichero
            	raf.seek(offset);
            	String nombre = leerNombre(raf);
            	if(nombre.charAt(0)=='*')
            		System.out.println("El registro " + registro + " está marcado para ser eliminado");
            	else {
            		int edad = raf.readInt();
            		System.out.printf("Registro %d: '%s', %d años%n", registro, nombre, edad);
            	}
            }
        }
    }
    
    private static String leerNombre(RandomAccessFile raf) throws Exception {
        String nombre ="";
        // Leer cada caracter
        for (int i = 0; i < TAMANYO_NOMBRE; i++) {
            char c = raf.readChar();
            nombre = nombre + c;
        }
        return nombre.trim();  // trim() para eliminar los espacios en blanco
    }
    
    private static void modificarRegistro(String fichero, int registro, String nombreNuevo, int edadNueva) throws Exception{
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            long offset = (registro-1) * TAMANYO_REGISTRO;
            if(offset>=raf.length()) {
            	System.out.println("No existe el registro " + registro);
            	System.out.println("El registro mas alto es el " + raf.length()/TAMANYO_REGISTRO);
            }
            else {
            	raf.seek(offset);
            	String nombre = leerNombre(raf);
            	if(nombre.charAt(0)!='*') {
                	raf.seek(offset);
                	escribirNombre(raf, nombreNuevo);
                	raf.writeInt(edadNueva);
                	System.out.println("Registro " + registro + " modificado");
            	}
            	else
            		System.out.println("El registro " + registro + " no puede ser modificado porque está marcado para ser eliminado");
            }
        }
    }
    
    private static void leerTodosLosRegistros(String fichero) throws Exception {
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
    		// calculamos el número de registros a partir del tamaño del fichero
    		int numRegistros = (int)raf.length()/TAMANYO_REGISTRO;
    		for(int i=0; i< numRegistros; i++) {
    			String nombre = leerNombre(raf);
   				int edad = raf.readInt();
   				if(nombre.charAt(0)!='*')
   					System.out.printf("Registro %d: '%s', %d años%n", i+1, nombre, edad);
    		}
        }
    }
    
    private static void anyadeRegistro(String fichero, String nombre, int edad) throws Exception{
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            raf.seek(raf.length());
            escribirNombre(raf, nombre);
            raf.writeInt(edad);
            System.out.println("Registro añadido");
        }
    }    
    
    private static void borrarRegistro(String fichero, int registro) throws Exception {
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            long offset = (registro-1) * TAMANYO_REGISTRO;
            if(offset >=raf.length()) {
            	System.out.println("No existe el registro " + registro);
            	System.out.println("El registro mas alto es el " + raf.length()/TAMANYO_REGISTRO);
            }
            else {
            	raf.seek(offset);
            	String nombre = leerNombre(raf);
            	// usaremos un * para reemplazar el primer caracter como marca de borrado
           		if (nombre.charAt(0) == '*')
           			System.out.println("El registro " + registro + " ya había sido borrado");
           		else {
           			nombre = '*' + nombre.substring(1);
           			raf.seek(offset);
           			escribirNombre(raf,nombre);
           			System.out.println("Registro " + registro + " eliminado con éxito");
           		}
           		
            }
        }
    }
}

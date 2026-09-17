package ejPorra;

import java.io.RandomAccessFile;
import java.util.HashMap;

public class ejPorraFicheroAleatorios {
	static final int NOMBOLETOS = 100;
	static final int TAMANYONOMBRE=30;
	static final int TAMANYOREGISTRO=TAMANYONOMBRE*2;
	public static void main(String[] args) {
		//fichero que nos guarde los 100 numeros
		//añadir perosna al numero
		String fichero = "/home/alumno/porra.txt";
		crearFichero(fichero);
		apuestaPorNumero(5,"Pepe Morón",fichero);
		apuestaPorNumero(4,"Rocío López",fichero);
		apuestaPorNumero(1,"Antonio Barbas",fichero);
		apuestaPorNumero(4,"Paquita",fichero);
		listarParticipantes(fichero);
		String ganador = hacerSorteo(fichero);
		if(ganador!=null)
			System.out.println("El ganador es: "+ganador);
	
	}
	public static void crearFichero(String fichero) {
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"rw")){
			
			System.out.println("Fichero creada. Tamaño: "+raf.length()+" bytes");
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	public static void escribirNombre(RandomAccessFile raf, String nombre)throws Exception {
		char[] chars = new char[TAMANYONOMBRE];
		for(int i=0;i<TAMANYONOMBRE;i++) {
			if(i<nombre.length())
				chars[i] = nombre.charAt(i);
			else
				chars[i]=' ';
		}
	}
	public static void apuestaPorNumero(int registro, String nombre,String fichero) {
		
	}
	public static String hacerSorteo(String fichero) {
		String nombre="";
		
		return nombre;
	}
	private static void listarParticipantes(String fichero) {
		
	}

}
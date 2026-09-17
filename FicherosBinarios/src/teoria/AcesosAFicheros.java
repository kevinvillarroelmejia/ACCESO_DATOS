package teoria;

import java.io.RandomAccessFile;
import java.util.HashMap;

public class AcesosAFicheros {
	
	static final int TAMANYO_NOMBRE=20;
	static final int TAMANYO_REGISTRO=(TAMANYO_NOMBRE*2)+4;

			
	public static void main(String[] args) {
		//Hasta ahora hemos visto acceso secuencial, ahora vamos a ver acceso aleatorio
		//Es similar al acceso de base de datos
		//Handicap: Todo lo que escribamos tiene que tener un tamaño fijo
		//--------------------------------------------------------------------------------------
		//TAMAÑOS FIJOS:
		//int --> 4 bytes
		//double --> 8 bytes
		//boolean --> 1 bytes
		//char --> 2 bytes
		//--------------------------------------------------------------------------------------
		String fichero="/home/alumno/agenda.dat";
		//Vamos a meter los datos en un diccionario:
		HashMap<String,Integer>agenda=new HashMap<>();
		agenda.put("Alejandro",33);
		agenda.put("Luis",24);
		agenda.put("Ana",32);
		agenda.put("Elvira",41);
		//--------------------------------------------------------------------------------------
		//Nueva forma de excepciones, hacemos la excepcion en el propio main y en el final de todos los metodos asociados ponemos throws Exception.
		try {
			crearAgenda(fichero,agenda);
			leerRegistro(fichero,2);//Este tiene que leerlo
			leerRegistro(fichero,5);//Este no tiene que leerlo porque no existe
			modificarRegistro(fichero,2,"Ana Maria",33);//Ahora queremos modificar el segundo registro "Segun Chema la parte mas guay" :x
			leerRegistro(fichero,2);
			leerRegistro(fichero,3);
			/* Lo comento para que no me añada constantemente nuevoRegistro(fichero,"Jose Antonio",56); añadir nuevos registros */
			leerRegistro(fichero,5);
			//nuevoRegistro(fichero,"Manuel",41);
			leerTodosRegistros(fichero);
			borrarRegistro(fichero,6);//función de borrar registros
			leerRegistro(fichero,6);// comporbacion de que esta borrado
			modificarRegistro(fichero,6,"Antonia Manuela",33);//comprobacion de que no se puede modificar porque esta borrado
			leerTodosRegistros(fichero);//Comprobacion de que no sale el regsitro
		}catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	public static void crearAgenda(String fichero, HashMap<String,Integer>agenda) throws Exception{
		//Recorremos diccionario usando un try de este nuevo metodo para no tener que cerrarlo, no se pone catch porque lo hicimos antes.
		//Tenemos 2 modos, "r" solo lectura y "rw" para lectura y escritura, no hay solo escritura.
		//Si el fichero existe, sobreescribe
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"rw")){
			for(String nombre:agenda.keySet()) {
				int edad = agenda.get(nombre);
				//vamos a hacer un metodo a parte para fijar un tamaño al String
				escribirNombre(raf,nombre);
				//Escribimos ya la edad porque sabemos su tamaño fijo
				raf.writeInt(edad);
			}
			//El metodo length puesto en un fichero te devuelve su tamaño
			System.out.println("Agenda creada. Tamaño: "+raf.length()+" bytes");
		}	
	}
	public static void escribirNombre(RandomAccessFile raf, String nombre)throws Exception {
		//Vamos a usar una constante global para que sea mas comodo
		char[] chars = new char[TAMANYO_NOMBRE];
		//Lo que hace este bucle es meter las letras que tenga el nombre en el array y cuando termine, meter espacios en blanco para rellenar todo el array.
		for(int i=0;i<TAMANYO_NOMBRE;i++) {
			if(i<nombre.length())
				chars[i] = nombre.charAt(i);
			else
				chars[i]=' ';
		}
		//Aqui nos sale error de IOException, para solucionarlo tienes que poner throws Exception en el metodo
		for(char c:chars)
			raf.writeChar(c);
	}
	public static void leerRegistro(String fichero,int registro)throws Exception {
		//Vamos a solamente leer el fichero
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"r")){
			long posicion = TAMANYO_REGISTRO * (registro-1);
			//Hacemos un if que si el registro que quieres ver tiene un numero mas alto de los que hay nos de un error
			if(posicion>=raf.length()) {
				System.out.println("El registro "+registro+" no existe");
				System.out.println("El registro mas alto es el "+raf.length()/TAMANYO_REGISTRO);
			}
			else {
				//El .seek es para situarnos con el cursor en la posicion que indicas
				raf.seek(posicion);
				//Vamos a hacer una funcion para leer el nombre igual que cuando lo escribimos
				String nombre = leerNombre(raf);
				if(nombre.charAt(0)!='*') {
					int edad = raf.readInt();
					System.out.printf("Registro: %d - Nombre: %s. Edad: %d\n",registro,nombre, edad);
				}	
				else
					System.out.println("El registro "+registro+" esta marcado para ser eliminado");
			}
		}
	}
	public static String leerNombre(RandomAccessFile raf)throws Exception {
		String nombre="";
		for(int i=0;i<TAMANYO_NOMBRE;i++) {
			char c=raf.readChar();
			nombre = nombre + c;
		}
		//Para que nos devuelva el nombre sin espacios
		return nombre.trim();
	}
	public static void modificarRegistro(String fichero, int registro, String nombre, int edad) throws Exception{
		//como vamos a escribir utilizamos el metodo rw
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"rw")){
			//Primero comprobamos si existe el registro
			long posicion = TAMANYO_REGISTRO * (registro-1);
			if(posicion>=raf.length()) {
				System.out.println("El registro "+registro+" no existe");
				System.out.println("El registro mas alto es el "+raf.length()/TAMANYO_REGISTRO);
			}
			else {
				//modificamos el registro
				raf.seek(posicion);
				String nombreAnterior =leerNombre(raf);
				if (nombreAnterior.charAt(0)=='*')
					System.out.println("El registro "+registro+" esta marcado para borrar no se puede modificar");
				else {
					raf.seek(posicion);
					escribirNombre(raf,nombre);
					raf.writeInt(edad);
					System.out.println("Registro "+registro+" modificado correctamente");
				}
			}
		}
	}
	public static void nuevoRegistro(String fichero,String nombre, int edad)throws Exception {
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"rw")){
			//primero nos situamos en el final del fichero
			raf.seek(raf.length());
			//Y ahora escribimos todo
			escribirNombre(raf,nombre);
			raf.writeInt(edad);
			//Si le damos al play nos va a crear un registro nuevo constantemente
			System.out.println("Registro añadido correctamente");
		}	
	}
	public static void leerTodosRegistros(String fichero)throws Exception {
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"r")){
			long posicion = TAMANYO_REGISTRO;
			int numRegistro = (int)(raf.length()/TAMANYO_REGISTRO);
			for(int i=1;i<=numRegistro;i++) {
				String nombre = leerNombre(raf);
				int edad = raf.readInt();
				if(nombre.charAt(0)!='*') {
					System.out.println("-----------------------------------------");
					System.out.printf("Registro: %d - Nombre: %s. Edad: %d\n",i,nombre, edad);
				}
			}
		}
	}
	public static void borrarRegistro(String fichero, int registro)throws Exception {
		try(RandomAccessFile raf = new RandomAccessFile(fichero,"rw")){
			long posicion = TAMANYO_REGISTRO * (registro-1);
			if(posicion>=raf.length()) {
				System.out.println("El registro "+registro+" no existe");
				System.out.println("El registro mas alto es el "+raf.length()/TAMANYO_REGISTRO);
			}
			else {
				raf.seek(posicion);
				String nombre = leerNombre(raf);
				if (nombre.charAt(0)=='*') {
					System.out.println("El registro "+registro+" ya esta borrado");
				}
				else {
					String nombreBorrado='*'+nombre.substring(1);
					raf.seek(posicion);
					escribirNombre(raf,nombreBorrado);
					System.out.println("El registro "+registro+" se ha borrado");
				}
			}
		}
	}
}

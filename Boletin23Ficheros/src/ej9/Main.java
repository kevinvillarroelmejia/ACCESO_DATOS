package ej9;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String fichero = "/home/alumno/boletin23ej9/redes.txt";
		String ficheroBinario = "/home/alumno/boletin23ej9/redes.bin";
		Alumno.leerAlumnos(fichero);
		Alumno.procesarNotasAlumnos();
		Alumno.salvarAlumnosBinario(ficheroBinario);
		
	}
	
	
}

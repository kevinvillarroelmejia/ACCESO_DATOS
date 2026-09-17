package ej9;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Alumno implements Serializable {
	private String alumno;
	private double[] ras;
	private static String nombreModulo;
	private static ArrayList<Alumno> listaAlumnos = new ArrayList<>();
	
	public Alumno(String alumno, double[] ras) {
		this.alumno = alumno;
		this.ras = ras;
	}
	
	public static void leerAlumnos(String fichero) {
		try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
			String linea;
			Alumno.nombreModulo = fichero.substring(0,fichero.length()-4);
			while ((linea = lector.readLine()) != null) {
				String[] elementos = linea.split(": ");
				String nombre = elementos[0];
				String[] ras = elementos[1].split(", ");
				double[] notas = new double[5];
				for (int i=0; i<5; i++) {
					notas[i] = Double.parseDouble(ras[i]);
				}
				Alumno alumno = new Alumno(nombre,notas);
				Alumno.listaAlumnos.add(alumno);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void procesarNotasAlumnos() {
		System.out.printf("Módulo: %s\n", Alumno.nombreModulo);
		System.out.println("Alumnos/as con todo aprobado: ");
		int contador = 0;
		for (Alumno alumno: Alumno.listaAlumnos) {
			// System.out.println(alumno.alumno);
			if (alumno.todoAprobado() == true) {
				System.out.println(alumno.alumno);
				contador++;
			}
		}
		if (contador == 0) {
			System.out.println("No hay ningún alumno con todos los RAs aprobados.");
		}
		System.out.println();
		System.out.println("Resultados de aprendizaje y alumnos suspensos:");
		for (int i=1; i<=5; i++) {
			System.out.printf("RA%d: ", i);
			Alumno.suspensosPorRA(i-1);
			System.out.println();
		}

	}

	private static void suspensosPorRA(int n) {
		int contador = 0;
		for (Alumno alumno:Alumno.listaAlumnos) {
			if (alumno.ras[n]<5) {
				if (contador == 0) {
					System.out.printf("%s ", alumno.alumno);
				} else {
					System.out.printf(",%s ", alumno.alumno);
				}
				contador++;
			}
		}
		if (contador == 0) {
			System.out.println("Todos aprobados");
		}
	}

	public boolean todoAprobado() {
		boolean todoAprobado = true;
		for (double nota:this.ras) {
			if (nota<5) {
				todoAprobado = false;
			}
		}
		return todoAprobado;
	}

	public static void salvarAlumnosBinario(String fichero) {
		try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
			binario.writeObject(Alumno.listaAlumnos);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}

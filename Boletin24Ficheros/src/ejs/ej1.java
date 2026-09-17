package ejs;

import java.io.PrintWriter;
import java.util.Scanner;

public class ej1 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		System.out.print("Escribe un número: ");
		int num;
		try {
			num = teclado.nextInt();
			if (num<1 || num>10) {
				System.out.println("El número tiene que estar entre el 1 y 10.");
			}
			try(PrintWriter escritura = new PrintWriter("/home/alumno/tabla-"+String.valueOf(num)+".txt")) {
				for (int i=1; i<11; i++) {
					int resultado = num*i;
					escritura.printf("%d x %2d = %3d", num,i, resultado);
					escritura.println();
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Esto no es un número entero.");
		}
		teclado.close();
	}
}
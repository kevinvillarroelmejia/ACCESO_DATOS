import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Conversiones {

	public static void main(String[] args) {

		String textoEntero = "33";
		String textoConDecimales = "33.56";
		int entero = 42;
		double conDecimales = 44.67;

		// de char a String
		char c = 'a';
		String s = String.valueOf(c);

		// de char a entero
		String txtPin = "231";
		for (int i = 0; i < txtPin.length(); i++) {
			int cifra = (int) txtPin.charAt(i) - 48;
		}
		// DE CHAR A ENTERO
		for (int i = 0; i < txtPin.length(); i++) {
			int n = Character.getNumericValue(txtPin.charAt(i));
		}

		// de texto(String) a entero(int)
		int num1 = Integer.parseInt(textoEntero);// convertimos texto a numero
		System.out.println(num1);

		// de texto(String) a decimales(doble)
		double num2 = Double.parseDouble(textoConDecimales);// lo mismo que el anterior pero con Doubles
		System.out.println(num2);

		// de entero(int) a texot(String)
		String texto1 = String.valueOf(entero);
		System.out.println(texto1);

		// de decimales(double) a texto(String)
		String texto2 = String.valueOf(conDecimales);
		System.out.println(texto2);

		// entero(int) a decimales(double)
		double num3 = (double) entero;

		// de decimales(double) a entero(int)
		// pero NO redondea
		int num4 = (int) conDecimales;

		// para redondear
		int num5 = (int) Math.round(conDecimales);
		System.out.println(num5);

		// para redondear con solo 4 decimales
		final double pi = 3.14159;
		double piRedondeado = (double) Math.round(pi * 10000) / 1000;
		// double piRedondeado2=Math.round(pi*10000)/1000.0;
		// que divida entre un numero que sea decimal como en
		// este caso 1000.0 para que decimales

		// para que devuelva el numero maximo
		int max = Math.max(539, 3);
		System.out.println(max);
		// para que duevla el minimo
		double mim = Math.min(539.4, 3);

		// para convertir un array int a texto
		int diasDelMes[] = new int[12];
		String texto = Arrays.toString(diasDelMes);

		// TODO CONVIRTIENDO ArrayList HashSet Vectores/Array
		System.out.println();
		// De un ArrayList a un HashSet
		ArrayList<Integer> numeroArrayList = new ArrayList<>(
				List.of(1, 4, 5, 6, 7, 8, 8, 6, 5, 3, 2, 1, 1, 3, 4, 5, 6, 7, 8, 7));
		System.out.println("ArrayList normal duplicados " + numeroArrayList);

		// Convirtiendo
		HashSet<Integer> conjuntoNumeros = new HashSet<Integer>(Arrays.asList(1, 44, 55, 67, 77, 23, 15));
		ArrayList<Integer> listaNumeros = new ArrayList<Integer>(List.of(2, 55, 6, 2, 3, 77, 8, 55, 1, 2, 6));
		int[] vector = { 4, 5, 6, 22, 4, 1, 7, 9 };

		// Convirtiendo un vector en un ArrayList
		ArrayList<Integer> vectorLista = new ArrayList<Integer>();
		for (int n : vector) {
			vectorLista.add(n);
		}

		// Convirtiendo un vector en un HashSet
		HashSet<Integer> vectorConjunto = new HashSet<Integer>();
		for (int n : vector) {
			vectorConjunto.add(n);
		}

		// Convirtiendo un HashSet/ArrayList a Vector/Array
		// necesitamos un vector vacio
		int[] vectorListaNumeros = new int[listaNumeros.size()];
		int x = 0;
		for (int n : listaNumeros) {
			vectorListaNumeros[x] = n;
			x++;
		}

		// TODO Convirtiendo en un HashSet para eliminar duplicados
		HashSet<Integer> numerosConHashSet = new HashSet<Integer>(numeroArrayList);
		System.out.println("HashSet sin duplicados " + numerosConHashSet);
	}

}

package PrintF;

public class PrintF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String formato = "%-15s %-10s %-8s\n";
        
        // Imprimir línea de cabecera
        System.out.printf(formato, "Producto", "Cantidad", "Precio");
        System.out.println("----------------------------------------"); // Separador visual
 
        // Imprimir las filas con los datos correspondientes
        System.out.printf(formato, "Manzanas", "50", "$1.50");
        System.out.printf(formato, "Leche", "120", "$0.99");
        System.out.printf(formato, "Pan Integral", "5", "$2.10");
        System.out.printf(formato, "Galletas", "1000", "$3.50");
	}

}

package json;

public class Contacto {

	private String nombre;
	private String telefono;
	private String dni;
	
	public Contacto(String nombre,String telefono,String dni) {
		this.nombre=nombre;
		this.telefono=telefono;
		this.dni=dni;
	}

	public void mostrar() {
		System.out.println("----INFORMACION DE CONTACTO----");
		System.out.println("Nombre "+this.nombre);
		System.out.println("Telefono "+this.telefono);
		System.out.println("DNI: "+this.dni+"\n");
	}
	
	
}

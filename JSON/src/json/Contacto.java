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
	
	@Override
	public String toString() {
		return "------INFORMACION DE CONTACTO------\n"
				+ "Nombre "+this.nombre+"\n"+
				"Telefono "+this.telefono+"\n"+
				"DNI: "+this.dni+"\n";
	}

	public String getNombre() {
		return nombre;
	}
	
	
	

	
	
}

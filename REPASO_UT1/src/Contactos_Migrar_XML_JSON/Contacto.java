package Contactos_Migrar_XML_JSON;

public class Contacto {
	private String nombre;
	private int telefono;

	
	public Contacto(String nombre,int telefono) {
		this.nombre=nombre;
		this.telefono=telefono;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public int getTelefono() {
		return telefono;
	}
}

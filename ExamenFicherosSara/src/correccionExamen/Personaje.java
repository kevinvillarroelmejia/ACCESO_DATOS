package correccionExamen;

import java.io.Serializable;

public class Personaje implements Serializable {
	public String nombre;
	public String titulo;
	
	public Personaje(String titulo, String nombre) {
		this.titulo = titulo;
		this.nombre = nombre;
	}

	public String mostrarPersonaje() {
		System.out.printf("%s (%s)\n", this.nombre, this.titulo);
		return this.nombre + "(" + this.titulo + ")";
	}
}

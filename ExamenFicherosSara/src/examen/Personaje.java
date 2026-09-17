package examen;

import java.io.Serializable;

public class Personaje implements Serializable {
	public String tituloAnime;
	public String nombre;
	
	public Personaje(String nombre, String tituloAnime) {
		this.nombre = nombre;
		this.tituloAnime = tituloAnime;
	}

	public void mostrarPersonaje() {
		System.out.printf("%s (%s)\n", this.nombre, this.tituloAnime);
		
	}
}

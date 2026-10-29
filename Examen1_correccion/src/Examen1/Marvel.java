package Examen1;

import java.util.List;

public class Marvel {
	
	private String titulo;
	private int anio;
	private int fase;
	private String director;
	
	
	public Marvel(String titulo,int anio,int fase,String director) {
		this.titulo=titulo;
		this.anio=anio;
		this.fase=fase;
		this.director=director;
	}

	public String getTitulo() {
		return titulo;
	}

	public int getAnio() {
		return anio;
	}

	public int getFase() {
		return fase;
	}

	public String getDirector() {
		return director;
	}
	//se que no tengo que mostrarlo de aqui lo hice para comprobar que los lee
	//comprobacion de que los lee correctamente
	@Override
	public String toString() {
		return anio+" "+titulo;
	}
	
	

}

package PersonajesAnimes_POO;

import java.io.Serializable;

public class Personaje implements Serializable{

	
	private String tituloAnime;
	private String nombrePersonaje;
	
	
	public Personaje(String tituloAnime,String nombrePersonaje) {
		this.tituloAnime=tituloAnime;
		this.nombrePersonaje=nombrePersonaje;
	}
	
	@Override
	public String toString() {
		return this.nombrePersonaje+" ("+this.tituloAnime+")";
	}
	
	
	
}

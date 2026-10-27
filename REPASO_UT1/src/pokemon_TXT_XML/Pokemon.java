package pokemon_TXT_XML;

public class Pokemon {
	
	private int idPokedex;
	private String nombrePokemon;
	private float  peso;
	private float  altura;
	private String tipo;
	
	public Pokemon(int id,String nombre,float  peso,float  altura,String tipo) {
		this.idPokedex=id;
		this.nombrePokemon=nombre;
		this.peso=peso;
		this.altura=altura;
		this.tipo=tipo;
	}
	public Pokemon(int id,String nombre,float  peso,float  altura,String tipo1, String tipo2) {
		this.idPokedex=id;
		this.nombrePokemon=nombre;
		this.peso=peso;
		this.altura=altura;
		this.tipo=tipo;
		this.tipo=tipo;
	}
	
	
	
	
}

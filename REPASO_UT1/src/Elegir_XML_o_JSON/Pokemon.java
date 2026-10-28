package Elegir_XML_o_JSON;

public class Pokemon {
	private String idPokedex;
	private String nombrePokemon;
	private String  peso;
	private String  altura;
	private String tipo1;
	private String tipo2;
	
	public Pokemon(String id,String nombre,String  peso,String  altura,String tipo1) {
		this.idPokedex=id;
		this.nombrePokemon=nombre;
		this.peso=peso;
		this.altura=altura;
		this.tipo1=tipo1;
	}
	public Pokemon(String id,String nombre,String  peso,String  altura,String tipo1, String tipo2) {
		this.idPokedex=id;
		this.nombrePokemon=nombre;
		this.peso=peso;
		this.altura=altura;
		this.tipo1=tipo1;
		this.tipo2=tipo2;
	}
	
	public String getIdPokedex() {
		return idPokedex;
	}
	public String getNombrePokemon() {
		return nombrePokemon;
	}
	public String getPeso() {
		return peso;
	}
	public String getAltura() {
		return altura;
	}
	public String getTipo() {
		return tipo1;
	}
	
	public String getTipo2() {
		return tipo2;
	}
	
	
	
	
}

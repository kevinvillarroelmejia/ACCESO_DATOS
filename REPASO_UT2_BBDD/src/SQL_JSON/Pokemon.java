package SQL_JSON;

public class Pokemon {
	private int idPokemon;
	private String nombre;
	private double peso;
	private double altura;
	
	private String tipo1;
	private String tipo2;
	
	
	
	public Pokemon(int id,String nombre,double peso,double altura,String tipo1) {
		this.idPokemon=id;
		this.nombre=nombre;
		this.peso=peso;
		this.altura=altura;
		this.tipo1=tipo1;
	}

	public Pokemon(int id,String nombre,double peso,double altura,String tipo1,String tipo2) {
		this.idPokemon=id;
		this.nombre=nombre;
		this.peso=peso;
		this.altura=altura;
		this.tipo1=tipo1;
		this.tipo2=tipo2;
	}

	private int getIdPokemon() {
		return idPokemon;
	}

	private String getNombre() {
		return nombre;
	}

	private double getPeso() {
		return peso;
	}

	private double getAltura() {
		return altura;
	}

	private String getTipo1() {
		return tipo1;
	}

	private String getTipo2() {
		return tipo2;
	}
	
	
	

}

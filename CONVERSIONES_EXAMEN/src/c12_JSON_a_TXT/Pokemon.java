package c12_JSON_a_TXT;

import java.io.Serializable;

// [CLASE] Pokémon: codigo (int), nombre (String), peso (double)
// Serializable → para el binario (.dat).  Constructor vacío → para Gson.
// CAMBIAR: los atributos según el enunciado. En JSON, Gson empareja los atributos con las claves POR NOMBRE
// (si la clave del JSON se llama distinto: @SerializedName("clave") encima del atributo)
public class Pokemon implements Serializable {

	private static final long serialVersionUID = 1L;   // fijo: así el .dat no da InvalidClassException

	private int codigo;
	private String nombre;
	private double peso;

	public Pokemon() {
	}

	public Pokemon(int codigo, String nombre, double peso) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.peso = peso;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return codigo + " - " + nombre + " - " + peso + " kg";
	}
}

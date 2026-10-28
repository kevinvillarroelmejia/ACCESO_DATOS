package c12_JSON_a_TXT;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

// [CLASE] raíz del JSON: { "pokemons": [ ... ] }  (como la clase Agenda de clase)
public class Pokedex {

	@SerializedName("pokemons")   // CAMBIAR: nombre de la raíz del JSON del enunciado
	private List<Pokemon> pokemons;

	public Pokedex() {
		this.pokemons = new ArrayList<Pokemon>();
	}

	public Pokedex(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}
}

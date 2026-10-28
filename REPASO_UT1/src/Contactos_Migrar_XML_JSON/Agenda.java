package Contactos_Migrar_XML_JSON;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Agenda {

	@SerializedName("agenda")

	// Una lista de contactos
	private List<Contacto> listaContactos;

	public Agenda(List<Contacto> listaContactos) {
		this.listaContactos = listaContactos;
	}
	
//	lista de contactos para luego pasarsela al JSON 
	public List<Contacto> getListaContactos() {
		return listaContactos;
	}
	
	

}

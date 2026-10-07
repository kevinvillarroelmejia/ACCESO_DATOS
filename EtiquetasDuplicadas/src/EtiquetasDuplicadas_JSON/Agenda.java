package EtiquetasDuplicadas_JSON;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Agenda {

	//Ponemos la raiz
	@SerializedName("agenda")
	
	private List<Contacto> contactos;

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}


	
}

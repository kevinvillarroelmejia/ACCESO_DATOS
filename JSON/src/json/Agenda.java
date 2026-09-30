package json;

import java.util.List;
import com.google.gson.annotations.SerializedName;
public class Agenda {
	
	//Ponemos la raiz
	@SerializedName("agenda")
	
	//es como un arrayList
	private List<Contacto> contactos;

	
	
	public List<Contacto> getContactos() {
		return contactos;
	}

}

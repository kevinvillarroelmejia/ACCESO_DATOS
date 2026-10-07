package EtiquetasDuplicadas_JSON;

import java.util.List;

public class Contacto {
	
	private String nombre;
	private List<String> telefono;
	private String dni;
	
	public Contacto(String nombre, List<String> listaContactos, String dni) {
		this.nombre = nombre;
		this.telefono=listaContactos;
		this.dni = dni;
	}
	
	
	@Override
	public String toString() {
		
		String entrada= "Nombre: "+this.nombre+"\n"+
				"DNI "+this.dni+"\nTelefono";
		for(String tlf:this.telefono) {
			entrada+="\n - "+tlf;
		}
		return entrada;
	}

	public String getNombre() {
		return nombre;
	}

	public void setTelefono(List<String> telefono) {
		this.telefono = telefono;
	}

}

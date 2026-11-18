package com.dam2.kevin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //la clase que creemos lo convierte en una tabla
@Table(name = "employees")
public class Empleados {
	
	@Id //OLBIGATORIO
	@Column(name="employeeNumber", nullable=false)
	private int id;
	
	
	
	@Column(name ="firstName", length = 50, nullable = false)
	private String nombre;
	
	@Column(name ="lastName", length = 150, nullable = false)
	private String apellidos;
	
	@Column(name="email",length = 100)
	private String email;
	

	
	public Empleados() {
	}
	
	public Empleados(String nombre, String apellidos) {
		this.nombre=nombre;
		this.apellidos=apellidos;
		
	}


	private String getNombre() {
		return nombre;
	}

	private String getApellidos() {
		return apellidos;
	}
	
	@Override
	public String toString() {
		return "Nombre "+nombre+"\nApellidos: "+apellidos+"\nEmail: "+email+"\n";
	}
	
	

}

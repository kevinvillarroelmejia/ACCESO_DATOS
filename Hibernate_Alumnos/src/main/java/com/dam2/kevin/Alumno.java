package com.dam2.kevin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //la clase que creemos lo convierte en una tabla
@Table(name = "alumnos")
public class Alumno {
	
	
	@Id
	@Column(name ="dni",length = 9,nullable = false)
	private String dni;
	
	@Column(name ="nombre", length = 50, nullable = false)
	private String nombre;
	
	@Column(name ="apellidos", length = 150, nullable = false)
	private String apellidos;
	
	@Column(nullable = false)
	private int edad;
	
	@Column(nullable = false)
	private double matricula;
	
	public Alumno() {
	}
	
	public Alumno(String dni,String nombre, String apellidos,int edad,double matricula) {
		this.dni=dni;
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.edad=edad;
		this.matricula=matricula;
	}

	private String getDni() {
		return dni;
	}

	private String getNombre() {
		return nombre;
	}

	private String getApellidos() {
		return apellidos;
	}

	private int getEdad() {
		return edad;
	}

	private double getMatricula() {
		return matricula;
	}
	
	@Override
	public String toString() {
		return "DNI: "+dni+"\n"+"Nombre: "+nombre+"\nApellidos: "+apellidos;
	}
	
	

}

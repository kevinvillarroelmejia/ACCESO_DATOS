package com.dam2.kevin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos") //CAMBIAR EL NOMBRE DE LA TABLA
public class Producto {
	@Id //clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) //campo autoImcrementable
	private Long id;
	
@Column(name = "producto", length = 100, nullable = false, unique = true) 
	private String nombre;
	
@Column(nullable = false) //para que no sea not null
	private double precio;

	//TODO HIBERNATE NECESITA UN CONSTRUTCTOR VACIO SI O SI, SI NO FALLA
//JPA exige siempre un constructor sin argumentos
	public Producto() {
	}

	public Producto(String nombre, double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}
}
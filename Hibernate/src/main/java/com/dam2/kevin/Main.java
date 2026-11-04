package com.dam2.kevin;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
	
	// Atributo estático para deshabilitar el logger de hibernate
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	public static void main(String[] args) {
		
		//===============HACIENDO INSERT===============
		// Le decimos a hibernate que solo muestre los errores y no mensajes informativos
		
		LOG_HIBERNATE.setLevel(Level.SEVERE);//va mostrar solo los errores
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU"); 
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin(); //
		
		Producto p = new Producto("Teclado", 25.5);
		em.persist(p);
		em.getTransaction().commit();
		System.out.println("Producto guardado con id " + p.getId());
		
		em.close();
		emf.close();

	}

}

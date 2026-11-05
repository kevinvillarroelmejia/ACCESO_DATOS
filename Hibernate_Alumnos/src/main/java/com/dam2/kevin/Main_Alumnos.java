package com.dam2.kevin;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main_Alumnos {
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	public static void main(String[] args) {

		LOG_HIBERNATE.setLevel(Level.SEVERE);// va mostrar solo los errores

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("goyaPU");
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			
			em.persist(new Alumno("22211133F","Adrian","Limachi",21,100));
			em.persist(new Alumno("55667744K","Sara","Limachi",23,100));
			em.persist(new Alumno("22211133F","Sergio","Limachi",33,100));
			em.persist(new Alumno("22211133F","Jordan","Limachi",11,100));
			em.persist(new Alumno("22211133F","Ale","Limachi",45,100));

			em.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Error "+e.getMessage());
		}

		em.close();
		emf.close();

	}
}

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

		// ===============HACIENDO INSERT===============
		// Le decimos a hibernate que solo muestre los errores y no mensajes
		// informativos

		LOG_HIBERNATE.setLevel(Level.SEVERE);// va mostrar solo los errores

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU");
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin(); //

//			Producto p = new Producto("Teclado", 25.5);
//			em.persist(p);

			//ESTA SERIA OTRA FORMA DE GUARDAR OBJETOS EN LA BBDD
			em.persist(new Producto("Monitor", 27));
			em.persist(new Producto("Raton Gamer", 20));
			em.persist(new Producto("Fuente de alimentacion", 130));

			em.getTransaction().commit();
//			System.out.println("Producto guardado con id " + p.getId());
			
			
		} catch (Exception e) {
			System.out.println("Error "+e.getMessage());
			em.getTransaction().rollback();//si da error hace un rollback

		}
// 		TODO ELIMINAR UN PRODUCTO
//		if(p!=null) {
//			em.remove(p);
//		}
//		em.getTransaction().commit();

		em.close();
		emf.close();

	}

}

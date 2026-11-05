package com.dam2.kevin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main2 {
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	public static void main(String[] args) {
		LOG_HIBERNATE.setLevel(Level.SEVERE);// va mostrar solo los errores

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU");
		EntityManager em = emf.createEntityManager();

		try {
			//Producto es la clase
			//esto es como hace un "SELECT * FROM PROCUCTO"
			String jpql="FROM Producto";
			
			String jpql2="FROM Producto p WHERE p.precio < 100";
			
			String jpql3="FROM Producto p WHERE p.nombre LIKE 'Teclado%' ORDER BY p.precio DESC";
			
			//devuelve una lista de PRODUCTO de la base de datos
			List<Producto> listaProductos=em.createQuery(jpql,Producto.class).getResultList();
			for(Producto p:listaProductos) {
				System.out.println(p);
			}
			System.out.println("Total de productos en la tabla "+listaProductos.size());

		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.getStackTrace();
		}
		em.close();
		emf.close();

	}

}

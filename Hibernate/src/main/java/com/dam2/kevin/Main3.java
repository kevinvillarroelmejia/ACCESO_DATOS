package com.dam2.kevin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main3 {
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	public static void main(String[] args) {

		LOG_HIBERNATE.setLevel(Level.SEVERE);// va mostrar solo los errores

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU");
		EntityManager em = emf.createEntityManager();

		try {
			
			String jqplParametrizada="FROM Producto p WHERE p.precio > ?1 AND p.nombre LIKE ?2";
			List<Producto> lista1=em.createQuery(jqplParametrizada, Producto.class)
					.setParameter(1,20) // asignandole a ?2 --> 100
					.setParameter(2,"Mon%") //
					.getResultList();
			for (Producto p : lista1) {
				System.out.println(p);
			}
			System.out.println("======================================================");
			
			
			
			//Recogiendo SOLO dos campos
			String jpqlNombrePrecio = "Select p.nombre, p.precio  FROM Producto p";
			List<Object[]> lista = em.createQuery(jpqlNombrePrecio, Object[].class).getResultList();
			for (Object[] p : lista) {
				System.out.println((String)p[0]+" - "+(Double)p[1]);
			}

			
			
			System.out.println("======================================================");

			
			
			// recogiendo solo los nombres STRING
			String jpqlNOMBRES = "Select p.nombre  FROM Producto p";
			// devuelve una de String con los nombres de la base de datos
			List<String> listaProductos = em.createQuery(jpqlNOMBRES, String.class).getResultList();
			for (String p : listaProductos) {
				System.out.println(p);
			}
			System.out.println("Total de productos en la tabla " + listaProductos.size());

			
			
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.getStackTrace();
		}
		em.close();
		emf.close();
	}

}

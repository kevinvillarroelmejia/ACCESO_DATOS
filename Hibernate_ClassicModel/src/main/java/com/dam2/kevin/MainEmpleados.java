package com.dam2.kevin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainEmpleados {
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	public static void main(String[] args) {
		LOG_HIBERNATE.setLevel(Level.SEVERE);// va mostrar solo los errores

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("goyaPU");
		EntityManager em = emf.createEntityManager();

		try {
			//Producto es la clase
			//esto es como hace un "SELECT * FROM PROCUCTO"
			String jpql="FROM Empleados";
			
			//devuelve una lista de PRODUCTO de la base de datos
			List<Empleados> listaEmpleados=em.createQuery(jpql,Empleados.class).getResultList();
			for(Empleados p:listaEmpleados) {
				System.out.println(p);
			}
			System.out.println("Total de productos en la tabla "+listaEmpleados.size());

		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.getStackTrace();
		}
		em.close();
		emf.close();
	}

}

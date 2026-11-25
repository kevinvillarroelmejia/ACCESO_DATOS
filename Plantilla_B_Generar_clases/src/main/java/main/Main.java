package main;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// CUANDO TENGAS LAS CLASES GENERADAS: agrega import model.*; y usa las clases en vez de texto

public class Main {

	// NO CAMBIA: para que Hibernate solo muestre los errores
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	// Abre la conexion, lanza el listado de prueba y la cierra
	public static void main(String[] args) {
		LOG_HIBERNATE.setLevel(Level.SEVERE);

		// CAMBIA (opcional): el nombre tiene que ser igual que el persistence-unit
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU");
		EntityManager em = emf.createEntityManager();

		try {
			// PRUEBA DE CONEXION: si lista sin error, la conexion esta bien hecha
			listadoNombres(em);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			// e.printStackTrace(); // descomenta para ver el error completo
		}
		em.close();
		emf.close();
	}

	// Lista un atributo de todas las filas de una entidad (JPQL usa el nombre de la CLASE generada)
	public static void listadoNombres(EntityManager em) {
		String jpql = "SELECT pais.name FROM Countries pais"; // CAMBIA: clase generada y atributo (ejemplo del PDF)
		List<String> listaNombres = em.createQuery(jpql, String.class).getResultList();
		System.out.println("--- Nombres ---");
		for (String nombre : listaNombres) {
			System.out.println(nombre);
		}
		System.out.println("Total: " + listaNombres.size());
	}
}

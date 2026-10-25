package Elecciones_Binario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Main_Elecciones {

	private static final String FICHERO_DAT = "src" + File.separator + "Elecciones_Binario" + File.separator
			+ "elecciones.dat";

	public static void main(String[] args) {

		anyadirVotos("PC", 200);
		System.out.println();
		anyadirVotos("PA", 100);
		anyadirVotos("PB", 400);
		anyadirVotos("PD", 2);


		verResultados(1730);
	}

	public static void anyadirVotos(String nombrePartido, int numAyadirVotos) {

		boolean encontrado = false;
		ArrayList<PartidoPolitico> listaPartidosPoliticos = new ArrayList<PartidoPolitico>();
		File fichero = new File(FICHERO_DAT);
		// COMPROBANDO QUE EXISTE Y QUE NO ESTA VACIO
		if (fichero.exists() && fichero.length() > 0) {
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(FICHERO_DAT))) {
				listaPartidosPoliticos = (ArrayList<PartidoPolitico>) binario.readObject();
				encontrado = true;
				sumarVotos(nombrePartido, numAyadirVotos, encontrado, listaPartidosPoliticos);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		} else {
			PartidoPolitico pa = new PartidoPolitico("PA", 0);
			PartidoPolitico pb = new PartidoPolitico("PB", 0);
			PartidoPolitico pc = new PartidoPolitico("PC", 0);
			PartidoPolitico pd = new PartidoPolitico("PD", 0);
			listaPartidosPoliticos.add(pa);
			listaPartidosPoliticos.add(pb);
			listaPartidosPoliticos.add(pc);
			listaPartidosPoliticos.add(pd);
			encontrado = true; // LA PARTIDA ESTA LISTA
			sumarVotos(nombrePartido, numAyadirVotos, encontrado, listaPartidosPoliticos);
		}

		// TENEMOS QUE COMPROBAR QUE TODO ESTA BIEN PARA ---> ESCRIBIR LAS
		// MODIFICACIONES
		if (encontrado) {
			try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(FICHERO_DAT))) {
				binario.writeObject(listaPartidosPoliticos);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// VOLVEMOS A LEER LAS MODIFICACIONES
		try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(FICHERO_DAT))) {
			// COMPROBANDO QUE EXISTE Y QUE NO ESTA VACIO
			System.out.println("Votos hasta el momento: ");
			listaPartidosPoliticos = (ArrayList<PartidoPolitico>) binario.readObject();
			for (PartidoPolitico partido : listaPartidosPoliticos) {
				System.out.println(partido);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	// extraemos codigo que se repetia --> suma votos al partido
	private static void sumarVotos(String nombrePartido, int numAyadirVotos, boolean encontrado,
			ArrayList<PartidoPolitico> listaPartidosPoliticos) {
		for (PartidoPolitico persona : listaPartidosPoliticos) {
			if (persona.getNombrePartidoPolitico().equalsIgnoreCase(nombrePartido)) {
				System.out.println("Nuevos votos para el partido " + nombrePartido + ": " + numAyadirVotos);
				persona.setNumVotos(persona.getNumVotos() + numAyadirVotos);
			}
		}
	}

	public static void verResultados(int censo) {
		ArrayList<PartidoPolitico> listaPartidosPoliticos = new ArrayList<PartidoPolitico>();
		File fichero = new File(FICHERO_DAT);
		int porcentaje=0;
		int totalVotos=0;
		// COMPROBANDO QUE EXISTE Y QUE NO ESTA VACIO
		if (fichero.exists() && fichero.length() > 0) {
			try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(FICHERO_DAT))) {
				listaPartidosPoliticos = (ArrayList<PartidoPolitico>) binario.readObject();
				for(PartidoPolitico partido:listaPartidosPoliticos) {
					totalVotos=totalVotos+partido.getNumVotos();
				}
				porcentaje=totalVotos*100/censo;
				System.out.println("Resultados con un "+porcentaje+"% de escrutinio");
				Collections.sort(listaPartidosPoliticos);
				for(PartidoPolitico partido:listaPartidosPoliticos) {
					System.out.println(partido);
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}else {
			System.out.println("Problemas con el fichero");
		}
	}

}

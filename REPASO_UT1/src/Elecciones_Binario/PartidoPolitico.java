package Elecciones_Binario;

import java.io.Serializable;
import java.util.Collections;

public class PartidoPolitico implements Serializable,Comparable<PartidoPolitico>{
	
	
	private String nombrePartidoPolitico;
	private int numVotos;
	
	
	public PartidoPolitico(String nombrePPolitico,int numVotos) {
		this.nombrePartidoPolitico=nombrePPolitico;
		this.numVotos=numVotos;
	}
	
	
	public String getNombrePartidoPolitico() {
		return nombrePartidoPolitico;
	}
	public void setNumVotos(int numVotos) {
		this.numVotos = numVotos;
	}
	
	public int getNumVotos() {
		return numVotos;
	}
	
	@Override
	public String toString() {
		return "Partido "+this.nombrePartidoPolitico+": "+this.numVotos+" votos";
	}


	//ORDENAR de mayor a menor
	//EN EL MAIN NECESITAS PASARLO POR ESTO 				
	//Collections.sort(listaPartidosPoliticos);

	@Override
	public int compareTo(PartidoPolitico otro) {
		int devolver=0;
		if(this.numVotos>otro.numVotos) {
			devolver=-1;
		}else if(this.numVotos<otro.numVotos) {
			devolver=1;
		}
		return devolver;
	}
	
 }

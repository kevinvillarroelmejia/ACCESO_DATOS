package Examen2_ejercio2;

import java.io.Serializable;

public class Pokemon implements Serializable{
	
		private int codigo;
		private int nivel;
		private String nombre;
		private String tipo;
		
		public Pokemon(String nombre,String tipo) {
//			this.codigo=id;
//			this.nivel=nivel;
			this.nombre=nombre;
			this.tipo=tipo;
		}

		public int getCodigo() {
			return codigo;
		}

		public int getNivel() {
			return nivel;
		}

		public String getNombre() {
			return nombre;
		}

		public String getTipo() {
			return tipo;
		}
		
		
		
		
		

}

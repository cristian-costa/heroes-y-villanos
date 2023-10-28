package com.heroesyvillanos;

import java.util.Map;

public class Personaje extends Competidor {
	private String nombre;
	private String nombreFantasia;
	private Map<Caracteristica, Integer> caracteristicas;
	
	Personaje(String nombre, String nombreFantasia, Map<Caracteristica, Integer> caracteristicas) throws Exception{
		if(!esNombreValido(nombre)) {
			throw new Exception("Nombre invalido");
		}
		
		if(!esNombreValido(nombreFantasia)) {
			throw new Exception("Nombre fantasia invalido");
		}
		
		this.nombre = nombre;
		this.nombreFantasia = nombreFantasia;
		this.caracteristicas = caracteristicas;
		estaDentroDeLiga = false;
	}
	
	@Override
	public int getPromedioCaracteristica(Caracteristica c) {
		return caracteristicas.get(c);
	}
	
	@Override
	protected int getSumaCaracteristica(Caracteristica c) {
		return caracteristicas.get(c);
	}

	@Override
	protected int getCantidadCompetidores() {
		return 1;
	}
	
	@Override
	public String toString() {
		return nombre + ", " + nombreFantasia;
	}
}

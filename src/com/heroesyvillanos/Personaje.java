package com.heroesyvillanos;

import java.util.Map;


public class Personaje extends Competidor {
	private String nombre;
	private String nombreFantasia;
	private Map<Caracteristica, Integer> caracteristicas;
	
	Personaje(String nombre, String nombreFantasia, Map<Caracteristica, Integer> caracteristicas) throws IllegalArgumentException{
		if(!esNombreValido(nombre)) {
			throw new IllegalArgumentException("Nombre invalido");
		}
		
		if(!esNombreValido(nombreFantasia)) {
			throw new IllegalArgumentException("Nombre fantasia invalido");
		}
		
		if(caracteristicas.size() < Caracteristica.values().length) {
			throw new IllegalArgumentException("Faltan valores para alguna/s caracteristica");
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
	
	public String getNombreFantasia() {
		return nombreFantasia;
	}
}

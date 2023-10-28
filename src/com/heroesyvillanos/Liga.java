package com.heroesyvillanos;

import java.util.List;
import java.util.Map;

public class Liga extends Competidor {
	private String nombreLiga;
	private List<Competidor> competidores;	
	private Map<Caracteristica, Integer> cache_promedio_caracteristicas;
	
	Liga(String nombre, List<Competidor> competidores) throws Exception{
		if(!esNombreValido(nombre)) {
			throw new Exception("Nombre invalido");
		}
		
		nombreLiga = nombre;
		this.competidores = competidores;
		this.updateCacheCaracteristicas();
	}
	
	protected void agregarCompetidorALiga(Competidor c) throws Exception {
		if(!tipoCompetidor.equals(c.tipoCompetidor)) {
			throw new Exception("No se puede agregar un personaje/liga a una liga con distintos tipos de competidor"); 
		}
		
		if(!c.puedeEntrarEnLiga()){
			throw new Exception("Este personaje ya pertenece a una liga");
		}
		
		competidores.add(c);
		c.setEstaDentroDeLiga(true);
		this.updateCacheCaracteristicas();
	}
	
	@Override
	protected int getPromedioCaracteristica(Caracteristica c) {
		return cache_promedio_caracteristicas.get(c);
	}
	
	private void updateCacheCaracteristicas() {
		int cantComp = this.getCantidadCompetidores();
		for (Caracteristica c : Caracteristica.values()) {
			int value = this.getSumaCaracteristica(c) / cantComp;
			cache_promedio_caracteristicas.put(c, value);
		}
	}
	
	@Override
	protected int getCantidadCompetidores() {
		int sum = 0;
		for(Competidor comp : competidores) {
			sum += comp.getCantidadCompetidores();
		}
		return sum;
	}
	
	protected int getSumaCaracteristica(Caracteristica c) {
		int sum = 0;
		for(Competidor comp : competidores) {
			sum += comp.getSumaCaracteristica(c);
		}
		return sum;
	}
		
	public List<Competidor> getCompetidores() {
		return competidores;
	}
	
	@Override
	public String toString() {
		return nombreLiga;
	}
	
}

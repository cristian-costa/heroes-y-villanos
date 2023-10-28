package com.heroesyvillanos;

import java.util.List;

public class Liga implements Competidor {
	private String nombreLiga;
	private List<Competidor> competidores; // puede contener personajes y ligas
	private boolean tipoCompetidor; // true para heroes, false para villanos, cambiar por algo mejor
	
	@Override
	public boolean esGanador(Competidor competidor, Caracteristica c) {
		// Determina si la liga es ganadora contra otro competidor basandose en una caracteristica especifica. 
		// Ojo que si da empate se usa la caracteristica que sigue.
		return false;
	}
	
	public void agregarCompetidorALiga() {
		// Agrega personaje o liga a otra liga
	}
	
	@Override
	public int getPromedioCaracteristica(Caracteristica c) {
		// Obtiene valor promedio de una caracteristica especifica en todos los competidores de la liga
		return 0;
	}
	
	// Getters y Setters
	public String getNombreLiga() {
		return nombreLiga;
	}
	
	public void setNombreLiga(String nombreLiga) {
		this.nombreLiga = nombreLiga;
	}
	
	public List<Competidor> getCompetidores() {
		return competidores;
	}
	
	public void setCompetidores(List<Competidor> competidores) {
		this.competidores = competidores;
	}
	
	public boolean isTipoCompetidor() {
		return tipoCompetidor;
	}
	
	public Liga(String nombreLiga, List<Competidor> competidores, boolean tipoCompetidor) {
		super();
		this.nombreLiga = nombreLiga;
		this.competidores = competidores;
		this.tipoCompetidor = tipoCompetidor;
	}

	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		sb.append("Nombre Liga = "+this.nombreLiga+";");
		for ( Competidor c : competidores) {
			Personaje pj = (Personaje) c;
			sb.append(pj.getNombreFantasia() +";");
		}
		return sb.toString();
	}
}

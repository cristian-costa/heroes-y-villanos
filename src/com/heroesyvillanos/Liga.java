package com.heroesyvillanos;

import java.util.List;

public class Liga implements Competidor, Comparable<Competidor> {
	private String nombreLiga;
	private List<Competidor> competidores; // puede contener personajes y ligas
	private boolean tipoCompetidor; // true para heroes, false para villanos, cambiar por algo mejor
	
	@Override
	public boolean esGanador(Competidor competidor, Caracteristica c) {
		boolean resultado = false;
		// Determina si el personaje es ganador contra otro competidor basandose en una caracteristica especifica. 
		// Ojo que si da empate se usa la caracteristica que sigue.
		new Ordenamiento().compararPor(c);
		resultado = this.compareTo(competidor) > 0;
		new Ordenamiento().setearOrdenCaracteristicasPorDefecto(); //qué pasa si no es static?
		return resultado;
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

	@Override
	public int compareTo(Competidor c) {
		int resultado = 0;
		
		for (Caracteristica caracteristicaDeComparacion : Ordenamiento.ordenCaracteristicas) {
			resultado =
				this.getPromedioCaracteristica(caracteristicaDeComparacion) -
				c.getPromedioCaracteristica(caracteristicaDeComparacion);
			if (resultado != 0) {
				break;
			}
		}
		return resultado;
	}
}

package com.heroesyvillanos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Ordenamiento implements Comparator<Competidor> {
	private static List<Caracteristica> ordenCaracteristicasPorDefecto = new LinkedList<Caracteristica>(
		Arrays.asList(
			Caracteristica.VELOCIDAD,
			Caracteristica.FUERZA,
			Caracteristica.RESISTENCIA,
			Caracteristica.DESTREZA
		)
	);
	public List<Caracteristica> ordenCaracteristicas = new LinkedList<Caracteristica>();
	
	public Ordenamiento() {
		setearOrdenCaracteristicasPorDefecto();
	}

	/**
	 * Comparator: se define el criterio de comparación para el sort
	 * */
	@Override	
	public int compare(Competidor c1, Competidor c2) {
		int resultado = 0;
		for (Caracteristica caracteristicaDeComparacion : ordenCaracteristicas) {
			resultado =
				c1.getPromedioCaracteristica(caracteristicaDeComparacion) -
				c2.getPromedioCaracteristica(caracteristicaDeComparacion);
			if (resultado != 0) {
				break;
			}
		}
		return resultado;
	}
	
	/** Se define el orden de criterios para la comparacion, 
	 * partiendo desde la caracteristica principal y 
	 * recorriendo las otras según el orden por defecto
	 * */
	public void compararPor(Caracteristica caracteristicaPrincipal) {
		int indiceCaracteristicaPrincipal;
		
		ordenCaracteristicas.clear();
		
		indiceCaracteristicaPrincipal = ordenCaracteristicasPorDefecto.indexOf(caracteristicaPrincipal);
		ordenCaracteristicas.addAll(indiceCaracteristicaPrincipal, ordenCaracteristicasPorDefecto);
		ordenCaracteristicas.addAll(ordenCaracteristicasPorDefecto.subList(0, indiceCaracteristicaPrincipal));
	}
	
	public Ordenamiento setearOrdenCaracteristicasPorDefecto() {
		ordenCaracteristicas.clear();
		ordenCaracteristicas.addAll(ordenCaracteristicasPorDefecto);
		return this;
	}
	
	public Ordenamiento setearOrdenCaracteristicas(List<Caracteristica> caracteristicas) {
		ordenCaracteristicas.clear();
		ordenCaracteristicas.addAll(caracteristicas);
		return this;
	}
}

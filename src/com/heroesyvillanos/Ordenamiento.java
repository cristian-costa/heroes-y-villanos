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
	public static List<Caracteristica> ordenCaracteristicas = new LinkedList<Caracteristica>();
	
	public Ordenamiento() {
		setearOrdenCaracteristicasPorDefecto();
	}

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
	
	public void compararPor(Caracteristica caracteristicaPrincipal) {
		int indiceCaracteristicaPrincipal;
		
		ordenCaracteristicas.clear();
		indiceCaracteristicaPrincipal = ordenCaracteristicasPorDefecto.indexOf(caracteristicaPrincipal);
		/*
		 * En base al orden por defecto, se agrega desde la característica indicada
		 * hasta el final y desde el inicio hasta la característica indicada
		 */
		ordenCaracteristicas.addAll(indiceCaracteristicaPrincipal, ordenCaracteristicasPorDefecto);
		ordenCaracteristicas.addAll(ordenCaracteristicasPorDefecto.subList(0, indiceCaracteristicaPrincipal));
	}
	
	public void setearOrdenCaracteristicasPorDefecto() {
		ordenCaracteristicas.clear();
		ordenCaracteristicas.addAll(ordenCaracteristicasPorDefecto);
	}
}
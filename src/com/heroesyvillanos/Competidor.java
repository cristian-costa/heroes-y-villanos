package com.heroesyvillanos;

public interface Competidor {
	int getPromedioCaracteristica(Caracteristica c);
	boolean esGanador(Competidor competidor, Caracteristica c);
	String getNombre();
}

package com.heroesyvillanos;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PersonajeTest {

	@Test
	void testConstructorNormal() {
		String nombre = "peter";
		String nombreFantasia = "peter";
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		try {
			new Personaje(nombre, nombreFantasia, caracteristicas);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testConstructorError() {
		String nombre = "peter";
		String nombreFantasia = "spider-man";
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 10),
				Map.entry(Caracteristica.FUERZA, 10),
				Map.entry(Caracteristica.RESISTENCIA, 10),
				Map.entry(Caracteristica.DESTREZA, 10)
		);
		Map<Caracteristica, Integer> caracteristicasError = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 10),
				Map.entry(Caracteristica.FUERZA, 10)
		);
		
		try {
			//Caso faltan caracteristicas
			new Personaje(nombre, nombreFantasia, caracteristicasError);
			
			//Caso nombres invalidos
			new Personaje(nombre, "", caracteristicas);
			new Personaje("", nombreFantasia, caracteristicas);
			
		} catch (IllegalArgumentException e) {
			return;
		}
		
		fail();
	}
	
	@Test
	void testMethods() {
		String nombre = "peter";
		String nombreFantasia = "spider-man";
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 10),
				Map.entry(Caracteristica.FUERZA, 10),
				Map.entry(Caracteristica.RESISTENCIA, 10),
				Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Personaje p = new Personaje(nombre, nombreFantasia, caracteristicas);
		
		//Test de get promedio valores caracteristicas
		for(Caracteristica c : Caracteristica.values()) {
			assertEquals(
					caracteristicas.get(c).intValue(), 
					p.getPromedioCaracteristica(c)
			);
		}
		
		//Test de get suma valores caracteristicas
		for(Caracteristica c : Caracteristica.values()) {
			assertEquals(
					caracteristicas.get(c).intValue(), 
					p.getSumaCaracteristica(c)
			);
		}
		
		//Test de cantidad competidores, caso personaje==1
		assertEquals(1, p.getCantidadCompetidores());
	}
	
}

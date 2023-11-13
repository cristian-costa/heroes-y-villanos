package com.heroesyvillanos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;

public class Juego {
	private List<Personaje> personajes = new ArrayList<Personaje>();
	private List<Liga> ligas = new ArrayList<Liga>();
    private Menu menu = new Menu();
    
    private static final String pathPersonajesIn = "src/personajes.in";
    private static final String pathLigasIn = "src/ligas.in";
    
    public void bienvenida() {
    	menu.mostrarBienvenida();
    }
    public void menuPrincipal(){
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuPrincipal");
    	
    	try {
    		switch(seleccion) {
		        case 1:
		            menuPersonajes();
		            break;
		        case 2:
		            menuLigas();
		            break;
		        case 3:
		            menuCombates();
		            break;
		        case 4:
		            menuReportes();
		            break;
		        case 5:
		        	System.exit(0);
		            break;
	    	}
		} catch (Exception e) {
			menu.mostrarTextoExcepcion(e);
		}
    }
    
 // ========== INICIO MENU PERSONAJES ==========
    
    private void menuPersonajes() throws Exception{
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuPersonajes");

        switch (seleccion) {
            case 1:
        		this.personajes = cargarPersonajesDesdeArchivo(pathPersonajesIn);
                break;
            case 2:
                crearPersonaje();
                break;
            case 3:
            	menu.mostrarTitulo("listarPersonajes");
				listarPersonajes();
				break;
            case 4:
				guardarPersonajesEnArchivo(this.personajes, pathPersonajesIn);
                break;
            case 5:
            	menuPrincipal();
                break;
        }
    }
        
	private ArrayList<Personaje> cargarPersonajesDesdeArchivo(String path) throws Exception {
		
		menu.mostrarTitulo("cargarPersonajes");
		// Inicializo array de personajes
		ArrayList<Personaje> listaPersonaje = new ArrayList<Personaje>();
		
		try (
			BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String line;	            
	            line = br.readLine(); //leo la cabacera para ignorarla
	            TipoCompetidor heovi = TipoCompetidor.VILLANO;
	            
	            while ((line = br.readLine()) != null) {
	            	String[] atributos = line.split(",");
	            	Map<Caracteristica, Integer> caract = new HashMap<Caracteristica, Integer>();
	            	
	            	caract.put(Caracteristica.VELOCIDAD, 	Integer.valueOf(atributos[3].trim()));
	            	caract.put(Caracteristica.FUERZA, 		Integer.valueOf(atributos[4].trim()));
	            	caract.put(Caracteristica.RESISTENCIA, 	Integer.valueOf(atributos[5].trim()));
	            	caract.put(Caracteristica.DESTREZA, 	Integer.valueOf(atributos[6].trim()));
	            	
	            	if (atributos[0].equals("Héroe")) {
	            		heovi = TipoCompetidor.HEROE;
	            	} else if (atributos[0].equals("Villano")) {
	            		heovi = TipoCompetidor.VILLANO;
	            	} else {
	            		menu.throwException("tipoCompetidor");
	            	}
	            	
	            	Personaje p = new Personaje(atributos[1], atributos[2], caract, heovi);
	            	
	            	listaPersonaje.add(p);
	            }
	            
	            br.close();
	            
		} catch (FileNotFoundException e) {	
			menu.throwException("fileNotFound", path);
		} catch (IOException e) {
			menu.throwException("io");
		}
	        
		menu.mostrarFinal("cargarPersonajes");
		
		return listaPersonaje;
	}
	
	private void guardarPersonajesEnArchivo(List<Personaje> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarPersonajes");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			
			writer.write("Héroe/Villano, NombreReal, NombrePersonaje, Velocidad, Fuerza, Resistencia, Destreza");
			writer.newLine();
			for (Personaje personaje : lista) {
				writer.write(personaje.toFileLine());
				writer.newLine();
			}
        }catch (IOException e) {
        	menu.throwException("io");
        }
		menu.mostrarFinal("guardarPersonajes");
	}
	
	private void crearPersonaje() throws Exception{
		boolean error;
		TipoCompetidor tipo;
		String nombreFantasia = "";
		String nombreReal = "";
		Map<Caracteristica, Integer> mapCaracteristicas = new HashMap<>();
		
		menu.mostrarTitulo("crearPersonaje");
		error = false;
		
		do {
			nombreFantasia = menu.registrarPalabra("crearPersonajeNombreFantasia", error);
			error = false;

			if(nombreFantasia.isBlank()) {
				error = true;
			}
			if (!personajes.isEmpty()) {
				for (Personaje personaje : personajes) {
					if (personaje.getNombreFantasia().equals(nombreFantasia)) {
						error = true;
						break;
					}
				}
			} 
		} while (error);
			
		tipo = menu.ingresoTipo(true);
		
		error = false;
		
		do {
			nombreReal = menu.registrarPalabra("crearPersonajeNombreReal", error);
			error = false;
			if(nombreReal.isBlank()) {
				error = true;
			}
		} while (error);
			
		mapCaracteristicas = menu.registrarCaracteristicas();
	
		personajes.add(new Personaje(nombreReal, nombreFantasia, mapCaracteristicas, tipo));
		
		menu.mostrarFinal("crearPersonaje");
	}
	
	private void listarPersonajes() throws Exception {
		if (!personajes.isEmpty()) {
			menu.listarCompetidores(personajes);
		} else {
			menu.throwException("listaPersonajesVacia");
		}		
	}
	
	// ========== FIN MENU PERSONAJES ==========
	
	// ========== INICIO MENU LIGAS ==========

    private void menuLigas() throws Exception {
    	
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuLigas");

        switch (seleccion) {
            case 1:
        		this.ligas = cargarLigasDesdeArchivo(this.personajes, pathLigasIn);
            	break;
            case 2:
                crearLiga();
                break;
            case 3:
            	menu.mostrarTitulo("listarLigas");
				listarLigas();
                break;
            case 4:
				guardarLigasEnArchivo(this.ligas, pathLigasIn);
                break;
            case 5:
            	agregaCompetidorALiga(1); // Agrega personaje
                break;
            case 6:
            	agregaCompetidorALiga(2); // Agrega Liga
                break;
            case 7:
            	menuPrincipal();
                break;
        }
    }
    
	private ArrayList<Liga> cargarLigasDesdeArchivo(List<Personaje>listPersonaje, String path) throws Exception {
		
		menu.mostrarTitulo("cargarLigas");
		ArrayList<Liga> listaLiga = new ArrayList<Liga>();
		
        try (
        	BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            TipoCompetidor heovi = TipoCompetidor.VILLANO;
            
            while ((line = br.readLine()) != null) {   	
            	ArrayList<Competidor> competidores = new ArrayList<Competidor>();          	
            	String[] ligaString = line.split(",");
            	
            	for (String personajeNombre : ligaString) {
            		// Agrego los personaje
            		for (Personaje per : listPersonaje) {						
						if(per.getNombreFantasia().trim().equals(personajeNombre.trim() )) {
							heovi = per.isTipoCompetidor();
							competidores.add(per);
						}						
					}
            		// Agrego las ligas
            		for(Liga lig : listaLiga) {
            			if(lig.getNombre().trim().equals(personajeNombre.trim() )) {
            				heovi=lig.isTipoCompetidor();
							competidores.add(lig);
						}
            		}
				}
            	
            	// Ver que estoy mandando en heovi, pareciera que estoy mandando por defecto heovi = villano
            	if(!competidores.isEmpty()) {
            		listaLiga.add(new Liga(ligaString[0], competidores, heovi));
            	}          
            }
            
        } catch (FileNotFoundException e) {	        	
        	menu.throwException("fileNotFound", path);  	
        } catch (IOException e) {
        	menu.throwException("io");
        }
        
        menu.mostrarFinal("cargarLigas");
		return listaLiga;
	}
	
	private void guardarLigasEnArchivo(List<Liga> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarLigas");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			for (Liga liga : lista) {
				writer.write(liga.toFileLine());
				writer.newLine();
			}			
			
        }catch (IOException e) {
        	menu.throwException("io");
        }
		menu.mostrarFinal("guardarLigas");
	}
	
	private boolean cadenaValida(String s) {
		if (s.isBlank() || s.isEmpty())
			return false;
		return true;
	}
	
	private boolean existeLigaEnMemoria(String s) {
		for(int i=0;i<ligas.size();i++)	{
			if(ligas.get(i).getNombreLiga().contains(s))
				return true;
		}
		return false;
	}
	
	private void crearLiga() throws Exception{
		String nombreLiga;
		TipoCompetidor tipoLiga;
		int indexLiga;
		boolean error;
		
		menu.mostrarTitulo("crearLiga");
		error = false;
		
		do {
			nombreLiga = menu.registrarPalabra("crearLigaNombreLiga", error);
			error = false;

			if (!cadenaValida(nombreLiga) || existeLigaEnMemoria(nombreLiga)) {
				error = true;
			}
		} while (error);
		
		tipoLiga = menu.ingresoTipo(false);
		
		ligas.add(new Liga(nombreLiga, tipoLiga));
		
		menu.mostrarFinal("crearLiga");
		
		indexLiga = ligas.size() - 1;

		// AGREGO PERSONAJE O LIGA
		int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("crearLigaAgregarCompetidor");

		switch (seleccion) {
        case 1:
        	agregaPersonajeALiga(indexLiga);
        	break;
        case 2:
        	agregaLigaALiga(indexLiga);
            break;
        case 3:
			menuLigas();
            break;
		}
	}
	
	private void agregaCompetidorALiga(int opcion) throws Exception{
		
		menu.mostrarTitulo("ligaAgregarCompetidor");
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		if(opcion == 1) {
			agregaPersonajeALiga(seleccion);
		} else {
			agregaLigaALiga(seleccion);
		}
	}
	
	private void agregaPersonajeALiga (int indexLiga) throws Exception{
		
		menu.mostrarTitulo("ligaAgregarPersonaje");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(personajes);
		
		ligas.get(indexLiga).agregarCompetidorALiga(personajes.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarPersonaje");
	}
	
	private void agregaLigaALiga (int indexLiga) throws Exception {
		
		menu.mostrarTitulo("ligaAgregarLiga");

		if (ligas.isEmpty()) {
			menu.throwException("listaLigasVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		ligas.get(indexLiga).agregarCompetidorALiga(ligas.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarLiga");
	}
	
	private void listarLigas() throws Exception{
		if(!ligas.isEmpty()) {
			menu.listarCompetidores(ligas);
		} else {
			menu.throwException("listaLigasVacia");
		}
		
	}
	
	// ========== FIN MENU LIGAS ==========
	
	// ========== INICIO MENU COMBATES ==========

    private void menuCombates() throws Exception{
    	
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuCombates");

    	switch (seleccion) {
        case 1:
        	menuRealizarCombate();
            break;
        case 2:
        	mostrarReglas();
            break;
        case 3:
            menuPrincipal();
            break;
    	}
    }

    private void menuRealizarCombate() throws Exception{
    	try {
    		menu.mostrarTitulo("realizarCombateComp1");
        	Competidor comp1 = seleccionarCompetidor();
        	menu.mostrarTitulo("realizarCombateComp2");
        	Competidor comp2 = seleccionarCompetidor();
        	Caracteristica car = seleccionarCaracteristica();
        	combatir(comp1, comp2, car);
		} catch (NullPointerException e) {
			menu.throwException("combateLigaVacia");
		}
    }
    
    private Competidor seleccionarCompetidor() throws Exception {
    	int seleccionTipoCompetidor = 0;
    	int seleccionCompetidor = 0;

    	seleccionTipoCompetidor = menu.mostrarMenuConInstruccionOpciones("seleccionarTipoCompetidor");
    	
    	switch (seleccionTipoCompetidor) {
        case 1:
        	menu.mostrarTitulo("listarPersonajes");
        	if (personajes.isEmpty()) {
    			menu.throwException("listaPersonajesVacia");
    		}
        	//listarPersonajes();
        	seleccionCompetidor = menu.seleccionarCompetidores(personajes);
        	menu.mostrarResultado("seleccionarPersonaje", 1, personajes.get(seleccionCompetidor - 1).toString());
            break;
        case 2:
        	menu.mostrarTitulo("listarLigas");
        	if (ligas.isEmpty()) {
    			menu.throwException("listaLigasVacia");
    		}
        	//listarLigas();
        	seleccionCompetidor = menu.seleccionarCompetidores(ligas);
        	menu.mostrarResultado("seleccionarLiga", 1, ligas.get(seleccionCompetidor - 1).toString());
            break;
    	}
    	
        return seleccionTipoCompetidor == 1 ? personajes.get(seleccionCompetidor - 1) : ligas.get(seleccionCompetidor - 1);
    }
    
    private Caracteristica seleccionarCaracteristica() {
    	Caracteristica[] caracteristicas = Caracteristica.values();
    	int seleccion = 0;
    	
    	seleccion = menu.seleccionarCaracteristica();
        
    	
        menu.mostrarResultado("seleccionarCaracteristica", 1, caracteristicas[seleccion - 1].toString());
        return caracteristicas[seleccion - 1];
    	
    }
    
	private void combatir(Competidor c1, Competidor c2, Caracteristica car) throws Exception {
		
		menu.mostrarTitulo("combatir");
		int resultado = c1.esGanador(c2, car);
		
		
		if (resultado > 0) {
			menu.mostrarResultado("combatir", 1, c1.toString()); //gana c1
		} else if (resultado < 0) {
			menu.mostrarResultado("combatir", 1, c2.toString()); //gana c2
		} else {
			menu.mostrarResultado("combatir", 2, ""); //empate
		}
        
	}
	
	private void mostrarReglas() {
		
		menu.mostrarReglas();

	}
    
	// ========== FIN MENU COMBATES ==========

	// ========== INICIO MENU REPORTES ==========

    private void menuReportes() throws Exception {
    	
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuReportes");

    	switch (seleccion) {
	        case 1:
	        	menuObtenerVencedoresContra();
	            break;
	        case 2:
	            List<Caracteristica> criterios = seleccionarCriteriosOrdenamiento();
	            boolean ascendente = seleccionarAscendente();
	            listadoOrdenadoPorCaracteristica(criterios, ascendente);
	            break;
	        case 3:
	            menuPrincipal();
	            break;
	    }
    }
    
    /**
	* Toma un competidor y una caracteristica,
	* obtiene lista de competidores que vencerian a un
	* competidor basandose en una caracteristica
     * @throws Exception 
	*/
	private void menuObtenerVencedoresContra() throws Exception {
		Caracteristica caracteristicaEvaluativa;
		List<Caracteristica> caracteristicas;
		Personaje personajeAEvaluar;
		
		menu.mostrarTitulo("obtenerVencedoresContra");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		int seleccionPersonaje = menu.seleccionarCompetidores(personajes);
		
    	personajeAEvaluar = personajes.get(seleccionPersonaje - 1);
    	
    	menu.mostrarTitulo("seleccionarCaracteristica");
    	caracteristicas = menuSeleccionarCaracteristica(null);
    	if (caracteristicas.isEmpty()) {
    		menuReportes();
    		return;
    	}
    	else {
    		caracteristicaEvaluativa = caracteristicas.get(0);
    	}
		
		menu.mostrarFinal("obtenerVencedoresContra");
		List<Competidor> venc = obtenerVencedoresContra(personajeAEvaluar, caracteristicaEvaluativa);
		if (venc.isEmpty()) {
			menu.throwException("listaVencedoresVacia");
		}
		menu.listarCompetidores(venc);
    }
    
	private List<Competidor> obtenerVencedoresContra(Competidor retador, Caracteristica caracteristica) throws Exception {
		List<Competidor> contrincantes = new ArrayList<Competidor>();
		List<Competidor> vencedores = new ArrayList<Competidor>();
		
		contrincantes.addAll(ligas);
		contrincantes.addAll(personajes);

		for (Competidor contrincante : contrincantes) {
			try {
				if (contrincante.esGanador(retador, caracteristica) > 0) {
					vencedores.add(contrincante);
				}
			} catch (Exception e) {
				// si recibimos excepcion por mismo tipo, no consideramos al personaje como vencedor del otro y no hacemos nada
			}
			
		}
		return vencedores;
	}
	
	private List<Caracteristica> seleccionarCriteriosOrdenamiento() {
		
		List<Caracteristica> criterios = new LinkedList<Caracteristica>();
		
		menu.mostrarTitulo("seleccionarCriterios");
		
		criterios = menuSeleccionarCaracteristica(criterios);
		if (criterios.isEmpty()) {
			return criterios;
		}
		criterios = menuAgregarCaracteristica(criterios);
		return criterios;
	}
	
	private List<Caracteristica> menuSeleccionarCaracteristica(List<Caracteristica> criterios) {
    	Caracteristica caracteristicaEvaluativa = null;
    	
    	if (criterios == null) {
    		criterios = new LinkedList<Caracteristica>();
    	}
    	caracteristicaEvaluativa = seleccionarCaracteristica();
    	
		if (criterios.indexOf(caracteristicaEvaluativa) >= 0) {
			menu.mostrarError("seleccionarCaracteristica2");
			caracteristicaEvaluativa = null;
		}
		else {
			criterios.add(caracteristicaEvaluativa);
		}
		return criterios;
    }
    
	private List<Caracteristica> menuAgregarCaracteristica (List<Caracteristica> criterios) {
		boolean agregarCaracteristica = true;
		
		while(agregarCaracteristica) {
			int seleccion = menu.mostrarMenuConInstruccionOpciones("agregarCaracteristica");
			switch (seleccion) {
				case 1: 
					criterios = menuSeleccionarCaracteristica(criterios);
					break;
				case 2:
					agregarCaracteristica = false;
					break;
			}
		}
		
		return criterios;
	}
	
	private boolean seleccionarAscendente() {
		int seleccion = menu.mostrarMenuConInstruccionOpciones("seleccionarAscendente");
		return (seleccion == 1) ? true : false;
	}
	
	private void listadoOrdenadoPorCaracteristica(List<Caracteristica> criterios, boolean ascendente) throws Exception{
		
		if (criterios == null || criterios.isEmpty()) {
			menu.mostrarError("listadoOrdenado");
			criterios = new LinkedList<Caracteristica>();
			criterios.addAll(new Ordenamiento().ordenCaracteristicas);
		}
		List<Competidor> personajesOrdenados = new ArrayList<Competidor>();
		Ordenamiento orden = new Ordenamiento();
		orden.setearOrdenCaracteristicas(criterios);
		
		if (personajes == null || personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		personajesOrdenados.addAll(personajes);
		Collections.sort(personajesOrdenados, orden);
		if (ascendente) {
			Collections.reverse(personajesOrdenados);
		}
		menu.listarCompetidores(personajesOrdenados);
	}
	
	// ========== FIN MENU REPORTES ==========
}


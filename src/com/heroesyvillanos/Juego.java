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
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Collections;

public class Juego {
	private List<Personaje> personajes = new ArrayList<Personaje>();
	private List<Liga> ligas = new ArrayList<Liga>();
    private Menu menu = new Menu();
    private Scanner scanner = new Scanner(System.in);
    
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
              try {
                this.personajes = cargarPersonajesDesdeArchivo(pathPersonajesIn);
              } catch (FileNotFoundException e) {
            	  System.err.println(e.getMessage()); // manejor del error. yo devuelvo una excepcion, este por si no esta el archivo 
              } catch (IOException e) {
            	  System.err.println(e.getMessage()); // Este por si falla la lectura del archvo  
              }
              break;
              
            case 2:
                crearPersonaje();
                break;
                
            case 3:
    	      try {
    	    	  menu.mostrarTitulo("listarPersonajes");
    	    	  listarPersonajes();
    	      } catch (Exception e) {
    	    	  System.out.println(e.getMessage());
    	      }
    	      break;
            case 4:
    		    try {
    		    	guardarPersonajesEnArchivo(this.personajes, pathPersonajesIn);
    		    } catch (IOException e) {
    		    	System.err.println(e.getMessage());
    		    }
                break;
            case 5:
                menuPrincipal();
                break;
          }
    }
    
    // CASE 1: Cargar personajes desde archivo
    public ArrayList<Personaje> cargarPersonajesDesdeArchivo(String path) throws Exception {
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
	            	
	            	if (atributos[0].equals("Heroe")) {
	            		heovi = TipoCompetidor.HEROE;
	            	} else if (atributos[0].equals("Villano")) {
	            		heovi = TipoCompetidor.VILLANO;
	            	} else {
	            		menu.throwException("tipoCompetidor");
	            	}
	            	
	            	Personaje p = new Personaje(atributos[1].trim(), atributos[2].trim(), caract, heovi);
	            	
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
	
	// CASE 2: Crear personaje
	public void crearPersonaje() {
		boolean error = false;
		TipoCompetidor tipo;
		String nombreFantasia = "";
		String nombreReal = "";
		Map<Caracteristica, Integer> mapCaracteristicas = new HashMap<>();;
		
        System.out.println("");
		System.out.println("Creando personaje...");
		
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
		
		do {
			nombreReal = menu.registrarPalabra("crearPersonajeNombreReal", error);
			error = false;
			if(nombreReal.isBlank()) {
				error = true;
			}
		} while (error);
		
		tipo = menu.ingresoTipo(true);
		error = false;
		
		mapCaracteristicas = menu.registrarCaracteristicas();
		
		personajes.add(new Personaje(nombreReal, nombreFantasia, mapCaracteristicas, tipo));
		
		menu.mostrarFinal("crearPersonaje");
	}
	
	// CASE 3: Listado de personajes
	public void listarPersonajes() throws Exception {
		if (!personajes.isEmpty()) {
			menu.listarCompetidores(personajes);
		} else {
			menu.throwException("listaPersonajesVacia");
		}		
	}
	
    // CASE 4: Guardar personajes en archivo
	public void guardarPersonajesEnArchivo(List<Personaje> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarPersonajes");
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			writer.write("Héroe/Villano, NombreReal, NombrePersonaje, Velocidad, Fuerza, Resistencia, Destreza");
			writer.newLine();
			for (Personaje personaje : lista) {
				writer.write(personaje.toFileLine());
				writer.newLine();
			}
        } catch (IOException e) {
        	menu.throwException("io");
        }
		menu.mostrarFinal("guardarPersonajes");
	}
	
	// ========== FIN MENU PERSONAJES - INICIO MENU LIGAS ==========

	private void menuLigas() throws Exception {        
        int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuLigas");
        
        switch (seleccion) {
        case 1:
        	try {
        		this.ligas = cargarLigasDesdeArchivo(this.personajes, pathLigasIn);
    		}catch (FileNotFoundException e) {
    			  System.err.println(e.getMessage());
    		}catch (IOException e) {
    			  System.err.println(e.getMessage()); 
    		}
        	break;
        case 2:
            crearLiga();
            break;
        case 3:
			try {
				menu.mostrarTitulo("listarLigas");
				listarLigas();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
            break;
        case 4:
			try {
				guardarLigasEnArchivo(this.ligas, pathLigasIn);
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
            break;
        case 5:
        	ingresaNombreLigaAgregaCompetidor(); 
            break;
        case 6:
        	menuPrincipal();
            break;
        }
    }
  
	// CASE 1: Cargar ligas desde archivo
  	public ArrayList<Liga> cargarLigasDesdeArchivo(List<Personaje>listPersonaje, String path) throws Exception {
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
		
	// CASE 2: Crear liga
	public void crearLiga() throws Exception{
		String nombreLiga;
		TipoCompetidor tipoLiga;
		int indexLiga;
		boolean error = false;
		
		menu.mostrarTitulo("crearLiga");

		do {
			nombreLiga = menu.registrarPalabra("crearLigaNombreLiga", error);
			error = false;
			if (!validaCadena(nombreLiga) || existeLigaEnMemoria(nombreLiga)) {
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
			if (!personajes.isEmpty()) {
				agregaPersonajeALiga(indexLiga);
			}
			else
				System.out.println("NO HAY PERSONAJES EN MEMORIA");
			break;
		case 2:
			if (ligas.size()>1 ) {  //la unica liga cargada es la actual
				agregaLigaALiga(indexLiga);
			}
			else
				System.out.println("NO HAY SUFICIENTES LIGAS CARGADAS");
			break;
		case 3:
			menuLigas();
			break;
		default:
			System.out.print("La opcion ingresada es incorrecta:");
		}
	}
	
	// CASE 3: Listado de ligas
	private void listarLigas() throws Exception{
		if(!ligas.isEmpty()) {
			menu.listarCompetidores(ligas);
		} else {
			menu.throwException("listaLigasVacia");
		}
	}
	
  	// CASE 4: Guardar ligas en archivo
	public void guardarLigasEnArchivo(List<Liga> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarLigas");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			for (Liga liga : lista) {
				writer.write(liga.toFileLine());
				writer.newLine();
			}			
        } catch (IOException e) {
        	menu.throwException("io");
        }
		
		menu.mostrarFinal("guardarLigas");
	}
	
	// CASE 5: Agregar competidor a liga	
	private void ingresaNombreLigaAgregaCompetidor() throws Exception{
		// INGRESO NOMBRE de LIGA a agregar competidores
		boolean seguir=true;
		int indiceLigaAgregar;
		
		this.listarLigas();

		System.out.println("Ingrese el numero de liga al que desea agregar competidores: ");
		indiceLigaAgregar = scanner.nextInt();
		do {
			if (indiceLigaAgregar > 0 && indiceLigaAgregar <= ligas.size()) 
				seguir=false;
			else {
				System.out.println("Opcion incorrecta, ingrese una valida: ");
				indiceLigaAgregar = scanner.nextInt();
				seguir=true;
			}
		} while(seguir);
			seleccionaCompetidorAAgregar(indiceLigaAgregar-1);
	}
		
	private void seleccionaCompetidorAAgregar(int indexLiga)  throws Exception{
		int opcion;
		boolean seguir;
		do {
			seguir = false;
			System.out.println("AGREGAR COMPETIDORES A LA LIGA...... '" + ligas.get(indexLiga).getNombre() + "'....... ");
			System.out.println(" 1. Agregar un Personaje");
			System.out.println(" 2. Agregar una Liga");
			System.out.println(" 3. Volver al menu de Ligas: ");

			opcion = scanner.nextInt();
			switch (opcion) {
			case 1:
				if (!personajes.isEmpty()) {
					scanner.nextLine();
					agregaPersonajeALiga(indexLiga);
					seguir = true;
				}
				else
					System.out.println("NO HAY PERSONAJES EN MEMORIA");
				seguir=true;
				break;
			case 2:
				if (ligas.size()>1 ) {  //la unica liga cargada es la actual
					scanner.nextLine();
					agregaLigaALiga(indexLiga);
				}
				else
					System.out.println("NO HAY SUFICIENTES LIGAS CARGADAS");
				seguir = true;
				break;
			case 3:
				seguir = false;
				break;
			default: {
				System.out.print("La opcion ingresada es incorrecta:");
				seguir = true;
				}
			}
		} while (seguir);
	}
	
	private void agregaPersonajeALiga(int indexLiga) throws Exception {
		menu.mostrarTitulo("ligaAgregarPersonaje");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(personajes);
		
		ligas.get(indexLiga).agregarCompetidorALiga(personajes.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarPersonaje");
	}
	
	private void agregaLigaALiga(int indexLiga) throws Exception {
		menu.mostrarTitulo("ligaAgregarLiga");
		
		if (ligas.isEmpty()) {
			menu.throwException("listaLigasVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		if (!ligas.get(seleccion-1).getCompetidores().contains(ligas.get(indexLiga))) { // evita que la liga entre en una liga que forma parte de ella
			ligas.get(indexLiga).agregarCompetidorALiga(ligas.get(seleccion - 1));
		} else {
			System.out.println("Liga no valida.");
		}
		
		menu.mostrarFinal("ligaAgregarLiga");
	}
	
	// AUXILIARES MENU LIGAS
	private boolean validaCadena(String s) {
		if (s.isBlank() || s.isEmpty())
			return false;
		return true;
	}
	
	private boolean existeLigaEnMemoria(String s) {
		for(int i=0;i<ligas.size();i++)	{
			if(ligas.get(i).getNombreLiga().equalsIgnoreCase(s))
				return true;
		}
		return false;
	}
	
	// ========== FIN MENU LIGAS - INICIO MENU COMBATES==========
			
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

	// CASE 1: Realizar combate
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
    
	public void combatir(Competidor c1, Competidor c2, Caracteristica car) throws Exception {
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
	
	// CASE 2: Mostrar reglas
	private void mostrarReglas() {
		menu.mostrarReglas();
	}
    
	// ========== FIN MENU COMBATES - INICIO MENU REPORTES==========

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
    
    // CASE 1: Toma un competidor y una caracteristica, obtiene lista de competidores que vencerian a un competidor basandose en una caracteristica
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
    	
       	if (personajeAEvaluar == null) {
    		menuReportes();
    		return;
    	}
    	
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
		
		TipoCompetidor tipoRetador = retador.getTipoCompetidor();
		
		contrincantes.addAll(ligas);		
		contrincantes.addAll(personajes);
		contrincantes.removeIf(c -> (tipoRetador == c.getTipoCompetidor()));

		for (Competidor contrincante : contrincantes) {
			if (contrincante.getTipoCompetidor() == retador.getTipoCompetidor()) {
				break;
			}
			if (contrincante.esGanador(retador, caracteristica) > 0) {
				vencedores.add(contrincante);
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
	
	public void listadoOrdenadoPorCaracteristica(List<Caracteristica> criterios, boolean ascendente) {
		if (criterios == null || criterios.isEmpty()) {
			menu.mostrarError("listadoOrdenado");
			criterios = new LinkedList<Caracteristica>();
			criterios.addAll(new Ordenamiento().getOrdenCaracteristicas());
		}
		List<Competidor> personajesOrdenados = new ArrayList<Competidor>();
		Ordenamiento orden = new Ordenamiento();
		orden.setearOrdenCaracteristicas(criterios);
		
		if (personajes == null || personajes.isEmpty()) {
			return;
		}
		personajesOrdenados.addAll(personajes);
		Collections.sort(personajesOrdenados, orden);
		if (!ascendente) {
			Collections.reverse(personajesOrdenados);
		}
		menu.listarCompetidores(personajesOrdenados);
	}
	
	// ========== FIN MENU REPORTES ==========
}


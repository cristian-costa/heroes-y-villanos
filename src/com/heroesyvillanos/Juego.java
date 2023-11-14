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
	// El juego tiene una lista de personajes y de ligas, así como un menu
	private List<Personaje> personajes = new ArrayList<Personaje>();
	private List<Liga> ligas = new ArrayList<Liga>();
    private Menu menu = new Menu();
    
    // Rutas de los archivos de entrada para personajes y ligas
    private static final String pathPersonajesIn = "src/personajes.in";
    private static final String pathLigasIn = "src/ligas.in";
    
    /**
     * Muestra el mensaje de bienvenida en la interfaz de usuario, invocando el método
     * correspondiente en la instancia de la clase Menu.
     */
    public void bienvenida() {
    	menu.mostrarBienvenida();
    }
    
    /**
     * Muestra el menú principal y gestiona la selección del usuario, redirigiendo a los distintos
     * menús (personajes, ligas, combates, reportes) o terminando la ejecución del programa.
     * 
     * @throws Exception cuando ocurre un error durante la ejecución de las operaciones del menú.
     */
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
    
    /**
     * Gestiona las opciones del menú relacionadas con los personajes, permitiendo al usuario
     * cargar, crear, listar y guardar personajes, además de regresar al menú principal.
     * 
     * @throws Exception cuando ocurre un error al ejecutar las operaciones del menú de personajes.
     */
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
        
    /**
     * Carga personajes desde un archivo proporcionado, procesando y generando una lista de personajes
     * en función de la información en el archivo de texto.
     *
     * @param path la ruta del archivo desde donde se cargan los personajes.
     * @return una lista de personajes generada a partir de los datos del archivo.
     * @throws Exception si ocurre un error durante la carga de personajes, como la lectura de archivo o la creación de personajes.
     */
	private ArrayList<Personaje> cargarPersonajesDesdeArchivo(String path) throws Exception {
		
		menu.mostrarTitulo("cargarPersonajes");
		// Inicializo array de personajes
		ArrayList<Personaje> listaPersonaje = new ArrayList<Personaje>();
		
		try (
			BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String line;
	            // La cabecera es leída pero la descartaremos       
	            line = br.readLine(); 
	            TipoCompetidor heovi = TipoCompetidor.VILLANO;
	            
	            while ((line = br.readLine()) != null) {
	            	String[] atributos = line.split(",");
	            	Map<Caracteristica, Integer> caract = new HashMap<Caracteristica, Integer>();
	            	
	            	// Mapeo de las características al HashMap
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
	            	
	            	// Intentamos crear un personaje con todos los datos de la línea.
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
	
	/**
	 * Guarda la información de los personajes de una lista en un archivo especificado.
	 *
	 * @param lista la lista de personajes a ser guardados en el archivo.
	 * @param path la ruta del archivo donde se guardarán los personajes.
	 * @throws Exception si ocurre un error durante el proceso de escritura en el archivo.
	 */
	private void guardarPersonajesEnArchivo(List<Personaje> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarPersonajes");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			// Escribimos la cabecera del archivo
			writer.write("Héroe/Villano, NombreReal, NombrePersonaje, Velocidad, Fuerza, Resistencia, Destreza");
			writer.newLine();
			// Escribimos cada personaje de nuestra lista de personajes
			for (Personaje personaje : lista) {
				writer.write(personaje.toFileLine());
				writer.newLine();
			}
        }catch (IOException e) {
        	menu.throwException("io");
        }
		menu.mostrarFinal("guardarPersonajes");
	}
	
	/**
	 * Crea un nuevo personaje a partir de la información ingresada por el usuario.
	 * 
	 * @throws Exception si se produce un error durante el proceso de creación del personaje.
	 */
	private void crearPersonaje() throws Exception{
		boolean error;
		TipoCompetidor tipo;
		String nombreFantasia = "";
		String nombreReal = "";
		Map<Caracteristica, Integer> mapCaracteristicas = new HashMap<>();
		
		menu.mostrarTitulo("crearPersonaje");
		error = false;
		
		do {
			// La validación de las palabras que ingresa el usuario las realizamos en Juego con un do While.
			nombreFantasia = menu.registrarPalabra("crearPersonajeNombreFantasia", error);
			error = false;

			if(nombreFantasia.isBlank()) {
				error = true;
			}
			
			// Para cada personaje ya existente, controlamos que no exista uno con el mismo nombreFantasia
			if (!personajes.isEmpty()) {
				for (Personaje personaje : personajes) {
					if (personaje.getNombreFantasia().equals(nombreFantasia)) {
						error = true;
						break;
					}
				}
			} 
		} while (error);
			
		// Usamos true para indicarle a ingresoTipo que es un personaje
		tipo = menu.ingresoTipo(true);
		
		error = false;
		
		do {
			// La validación de las palabras que ingresa el usuario las realizamos en Juego con un do While.
			nombreReal = menu.registrarPalabra("crearPersonajeNombreReal", error);
			error = false;
			if(nombreReal.isBlank()) {
				error = true;
			}
		} while (error);
			
		mapCaracteristicas = menu.registrarCaracteristicas();
	
		// Intentamos crear un personaje con todos los datos ingresados.
		personajes.add(new Personaje(nombreReal, nombreFantasia, mapCaracteristicas, tipo));
		
		menu.mostrarFinal("crearPersonaje");
	}
	
	/**
	 * Lista todos los personajes en caso de que la lista no esté vacía.
	 * 
	 * @throws Exception si la lista de personajes está vacía.
	 */
	private void listarPersonajes() throws Exception {
		if (!personajes.isEmpty()) {
			menu.listarCompetidores(personajes);
		} else {
			menu.throwException("listaPersonajesVacia");
		}		
	}
	
	// ========== FIN MENU PERSONAJES ==========
	
	// ========== INICIO MENU LIGAS ==========

	/**
	 * Presenta un menú de opciones para administrar las ligas. Las opciones incluyen cargar y guardar ligas desde y hacia un archivo,
	 * crear nuevas ligas, listar las ligas existentes y agregar competidores (personajes o ligas) a las ligas.
	 * Además, tiene la opción de regresar al menú principal.
	 * 
	 * @throws Exception si ocurre un error durante la manipulación de las ligas.
	 */
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
    
    /**
     * Carga las ligas desde un archivo especificado, construyendo y devolviendo una lista de ligas con sus respectivos competidores.
     * Se espera que el archivo contenga datos que definan las ligas y los nombres de los competidores.
     * 
     * @param listPersonaje Una lista de personajes que se utiliza para asociar a los competidores dentro de las ligas.
     * @param path La ruta del archivo desde el cual se cargarán las ligas.
     * @return Una lista de ligas creadas a partir de la información del archivo.
     * @throws Exception si hay problemas al leer o interpretar el archivo, o si ocurren errores en el proceso de carga.
     */
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
            		// Agrego los personajes
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
	
	/**
	 * Guarda la información de las ligas en un archivo especificado.
	 * Cada liga se escribe como una línea en el archivo.
	 * 
	 * @param lista La lista de ligas a guardar en el archivo.
	 * @param path La ruta del archivo en el que se guardarán las ligas.
	 * @throws Exception Si hay problemas al escribir en el archivo o si se presentan errores durante el proceso de guardado.
	 */
	private void guardarLigasEnArchivo(List<Liga> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarLigas");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			// Escribimos cada liga en una línea
			for (Liga liga : lista) {
				writer.write(liga.toFileLine());
				writer.newLine();
			}			
			
        }catch (IOException e) {
        	menu.throwException("io");
        }
		menu.mostrarFinal("guardarLigas");
	}
	
	/**
	 * Verifica si una cadena de texto es válida, es decir, si no está vacía o es nula.
	 * 
	 * @param s La cadena de texto a verificar.
	 * @return Devuelve true si la cadena no está vacía ni es nula, de lo contrario, devuelve false.
	 */
	private boolean cadenaValida(String s) {
		if (s.isBlank() || s.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * Verifica si existe una liga en la memoria a partir de una subcadena proporcionada.
	 * 
	 * @param s La subcadena a buscar en el nombre de las ligas.
	 * @return Devuelve true si al menos una liga contiene la subcadena en su nombre, de lo contrario, devuelve false.
	 */
	private boolean existeLigaEnMemoria(String s) {
		// Si de todas las ligas al menos una contiene en el nombre el string, entonces ya existe
		for(int i=0;i<ligas.size();i++)	{
			if(ligas.get(i).getNombreLiga().contains(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Permite la creación de una liga. Se introduce un nombre para la liga y se elige su tipo (Héroe o Villano).
	 * 
	 * @throws Exception si hay un problema al intentar crear la liga.
	 */
	private void crearLiga() throws Exception{
		String nombreLiga;
		TipoCompetidor tipoLiga;
		int indexLiga;
		boolean error;
		
		menu.mostrarTitulo("crearLiga");
		error = false;
		
		// La validación de las palabras que ingresa el usuario las realizamos en Juego con un do While.
		do {
			nombreLiga = menu.registrarPalabra("crearLigaNombreLiga", error);
			error = false;

			if (!cadenaValida(nombreLiga) || existeLigaEnMemoria(nombreLiga)) {
				error = true;
			}
		} while (error);
		
		// Usamos false para indicarle a ingresoTipo que es una liga
		tipoLiga = menu.ingresoTipo(false);
		
		ligas.add(new Liga(nombreLiga, tipoLiga));
		
		menu.mostrarFinal("crearLiga");
		
		// El index de la liga recien creada es el último, lo usaremos si elegimos agregar competidores a esta misma liga
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
	
	/**
	 * Permite agregar un competidor a una liga específica. 
	 * Selecciona el competidor (ya sea personaje o liga) que se agregará a la liga, según la opción elegida. 
	 * Si la opción es 1, se agrega un personaje. Si la opción es 2, se agrega una liga.
	 * 
	 * @param opcion Opción que indica si se va a agregar un personaje (1) o una liga (2).
	 * @throws Exception si hay un problema al intentar agregar el competidor a la liga.
	 */
	private void agregaCompetidorALiga(int opcion) throws Exception{
		
		menu.mostrarTitulo("ligaAgregarCompetidor");
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		// Opcion 1: agregar personaje, opcion 2: agregar liga
		if(opcion == 1) {
			agregaPersonajeALiga(seleccion - 1);
		} else {
			agregaLigaALiga(seleccion - 1);
		}
	}
	
	/**
	 * Agrega un personaje a una liga específica según el índice de la liga proporcionado.
	 * 
	 * @param indexLiga El índice de la liga a la que se agregará el personaje.
	 * @throws Exception Si hay un problema al intentar agregar el personaje a la liga.
	 */
	private void agregaPersonajeALiga (int indexLiga) throws Exception{
		
		menu.mostrarTitulo("ligaAgregarPersonaje");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(personajes);
		
		// Intentamos agregar a la liga pasada por parámetro el personaje seleccionado
		ligas.get(indexLiga).agregarCompetidorALiga(personajes.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarPersonaje");
	}
	
	/**
	 * Agrega una liga a una liga específica según el índice de la liga proporcionado.
	 * 
	 * @param indexLiga El índice de la liga a la que se agregará la nueva liga.
	 * @throws Exception Si hay un problema al intentar agregar la liga a la otra liga.
	 */
	private void agregaLigaALiga (int indexLiga) throws Exception {
		
		menu.mostrarTitulo("ligaAgregarLiga");

		if (ligas.isEmpty()) {
			menu.throwException("listaLigasVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		// Intentamos agregar a la liga pasada por parámetro la liga seleccionada
		ligas.get(indexLiga).agregarCompetidorALiga(ligas.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarLiga");
	}
	
	/**
	 * Lista todas las ligas existentes. Si no hay ligas, se lanza una excepción.
	 * 
	 * @throws Exception Si la lista de ligas está vacía.
	 */
	private void listarLigas() throws Exception{
		if(!ligas.isEmpty()) {
			menu.listarCompetidores(ligas);
		} else {
			menu.throwException("listaLigasVacia");
		}
		
	}
	
	// ========== FIN MENU LIGAS ==========
	
	// ========== INICIO MENU COMBATES ==========

	/**
	 * Maneja las opciones del menú de combates, permitiendo al usuario seleccionar entre realizar un combate, ver las reglas o regresar al menú principal.
	 *
	 * @throws Exception Cuando se produce un error durante la ejecución del menú de combates.
	 */
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

    /**
     * Inicia y controla el proceso para realizar un combate entre dos competidores, permitiendo al usuario seleccionar los 2 competidores y la característica a utilizar.
     *
     * @throws Exception Cuando se produce un error durante la ejecución del menú de realizar combate.
     */
    private void menuRealizarCombate() throws Exception{
    	// Seleccionamos 2 competidores y una característica, y los hacemos combatir
    	try {
    		menu.mostrarTitulo("realizarCombateComp1");
        	Competidor comp1 = seleccionarCompetidor();
        	menu.mostrarTitulo("realizarCombateComp2");
        	Competidor comp2 = seleccionarCompetidor();
        	Caracteristica car = seleccionarCaracteristica();
        	combatir(comp1, comp2, car);
		} catch (NullPointerException e) {
			// Sólo para el caso NullPointerException sabemos que la liga está vacía.
			// Otras excepciones llevarán el mensaje creado al lanzarlas y no se catchearan
			menu.throwException("combateLigaVacia");
		}
    }
    
    /**
     * Permite al usuario seleccionar un competidor, ya sea un personaje o una liga, mostrando y manejando las opciones disponibles.
     *
     * @return El competidor seleccionado por el usuario.
     * @throws Exception Cuando se produce un error durante la selección del competidor.
     */
    private Competidor seleccionarCompetidor() throws Exception {
    	int seleccionTipoCompetidor = 0;
    	int seleccionCompetidor = 0;

    	seleccionTipoCompetidor = menu.mostrarMenuConInstruccionOpciones("seleccionarTipoCompetidor");
    	
    	switch (seleccionTipoCompetidor) {
		    case 1:
		    	// Listamos y seleccionamos personaje, y mostramos la selección
		    	menu.mostrarTitulo("listarPersonajes");
		    	if (personajes.isEmpty()) {
					menu.throwException("listaPersonajesVacia");
				}
		    	//listarPersonajes();
		    	seleccionCompetidor = menu.seleccionarCompetidores(personajes);
		    	menu.mostrarResultado("seleccionarPersonaje", 1, personajes.get(seleccionCompetidor - 1).toString());
		        break;
		    case 2:
		    	// Listamos y seleccionamos liga, y mostramos la selección
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
    
    /**
     * Permite al usuario seleccionar una característica para el combate, mostrando y manejando las opciones disponibles.
     *
     * @return La característica seleccionada por el usuario.
     */
    private Caracteristica seleccionarCaracteristica() {
    	Caracteristica[] caracteristicas = Caracteristica.values();
    	int seleccion = 0;
    	
    	seleccion = menu.seleccionarCaracteristica();
        
    	
        menu.mostrarResultado("seleccionarCaracteristica", 1, caracteristicas[seleccion - 1].toString());
        return caracteristicas[seleccion - 1];
    	
    }
    
    /**
     * Realiza un enfrentamiento entre dos competidores basado en una característica específica, determinando el resultado del combate.
     *
     * @param c1  El primer competidor en el combate.
     * @param c2  El segundo competidor en el combate.
     * @param car La característica utilizada para el combate.
     * @throws Exception Cuando ocurre un error durante el combate.
     */
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
	
	/**
	 * Muestra en la consola las reglas del juego.
	 */
	private void mostrarReglas() {
		
		menu.mostrarReglas();

	}
    
	// ========== FIN MENU COMBATES ==========

	// ========== INICIO MENU REPORTES ==========

	/**
	 * Proporciona un menú para acceder a los distintos reportes disponibles.
	 * Selecciona entre mostrar los vencedores en combates, ordenar el listado por criterios
	 * o regresar al menú principal.
	 * 
	 * @throws Exception Si ocurre un error durante la generación de reportes.
	 */
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
     * Despliega un menú para elegir un personaje y una característica. Luego, muestra los
     * competidores que vencen al personaje seleccionado con respecto a la característica elegida.
     * 
     * @throws Exception Si ocurre un error al obtener la lista de competidores vencedores o
     * al listarlos en la interfaz.
     */
	private void menuObtenerVencedoresContra() throws Exception {
		Caracteristica caracteristicaEvaluativa;
		List<Caracteristica> caracteristicas;
		Personaje personajeAEvaluar;
		
		menu.mostrarTitulo("obtenerVencedoresContra");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		
		// Seleccionamos un personaje
		int seleccionPersonaje = menu.seleccionarCompetidores(personajes);
		
    	personajeAEvaluar = personajes.get(seleccionPersonaje - 1);
    	
    	menu.mostrarTitulo("seleccionarCaracteristica");
    	
    	// Seleccionamos una característica
    	caracteristicas = menuSeleccionarCaracteristica(null);
    	if (caracteristicas.isEmpty()) {
    		menuReportes();
    		return;
    	}
    	else {
    		caracteristicaEvaluativa = caracteristicas.get(0);
    	}
		
		menu.mostrarFinal("obtenerVencedoresContra");
		
		// Obtenemos la lista de competidores que lo vencen
		List<Competidor> venc = obtenerVencedoresContra(personajeAEvaluar, caracteristicaEvaluativa);
		if (venc.isEmpty()) {
			menu.throwException("listaVencedoresVacia");
		}
		menu.listarCompetidores(venc);
    }
    
	/**
	 * Obtiene la lista de competidores que vencen al retador con respecto a una característica dada,
	 * considerando tanto las ligas como los personajes existentes.
	 * 
	 * @param retador El competidor a evaluar.
	 * @param caracteristica La característica a evaluar.
	 * @return Una lista de competidores que vencen al retador con la característica especificada.
	 * @throws Exception Si ocurre un error al evaluar los competidores o al agregarlos a la lista de vencedores.
	 */
	private List<Competidor> obtenerVencedoresContra(Competidor retador, Caracteristica caracteristica) throws Exception {
		List<Competidor> contrincantes = new ArrayList<Competidor>();
		List<Competidor> vencedores = new ArrayList<Competidor>();
		
		// Consideramos todas las ligas y personajes
		contrincantes.addAll(ligas);
		contrincantes.addAll(personajes);

		// Para cada contrincante vemos si le gana a nuestro retador
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
	
	/**
	 * Permite al usuario seleccionar los criterios de ordenamiento, comenzando con la elección de una característica
	 * y ofreciendo la opción de agregar más características.
	 *
	 * @return Una lista de Caracteristicas seleccionadas para el ordenamiento.
	 */
	private List<Caracteristica> seleccionarCriteriosOrdenamiento() {
		
		List<Caracteristica> criterios = new LinkedList<Caracteristica>();
		
		menu.mostrarTitulo("seleccionarCriterios");
		
		// Primero seleccionamos una característica
		criterios = menuSeleccionarCaracteristica(criterios);
		if (criterios.isEmpty()) {
			return criterios;
		}
		// Podemos agregar más características o no hacerlo
		criterios = menuAgregarCaracteristica(criterios);
		return criterios;
	}
	
	/**
	 * Permite al usuario seleccionar una Característica, agregándola a la lista de criterios si aún no está presente.
	 *
	 * @param criterios La lista actual de Características seleccionadas como criterios de ordenamiento.
	 * @return La lista actualizada de Características seleccionadas para el ordenamiento.
	 */
	private List<Caracteristica> menuSeleccionarCaracteristica(List<Caracteristica> criterios) {
    	Caracteristica caracteristicaEvaluativa = null;
    	
    	if (criterios == null) {
    		criterios = new LinkedList<Caracteristica>();
    	}
    	// Seleccionamos característica
    	caracteristicaEvaluativa = seleccionarCaracteristica();
    	
		if (criterios.indexOf(caracteristicaEvaluativa) >= 0) {
			// Característica ya ingresada a los criterios
			menu.mostrarError("seleccionarCaracteristica2");
			caracteristicaEvaluativa = null;
		}
		else {
			criterios.add(caracteristicaEvaluativa);
		}
		return criterios;
    }
    
	/**
	 * Permite al usuario agregar múltiples Características a la lista de criterios de ordenamiento.
	 *
	 * @param criterios La lista actual de Características seleccionadas como criterios de ordenamiento.
	 * @return La lista actualizada de Características seleccionadas para el ordenamiento.
	 */
	private List<Caracteristica> menuAgregarCaracteristica (List<Caracteristica> criterios) {
		boolean agregarCaracteristica = true;
		
		// Podemos hacerlo tantas veces como queramos
		// Una vez que agregamos las n características que existansiempre dará error hasta que elijamos parar de agregar
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
	
	/**
	 * Permite al usuario seleccionar si el ordenamiento debe ser ascendente o no.
	 *
	 * @return Booleano que indica si el ordenamiento debe ser ascendente (true) o descendente (false).
	 */
	private boolean seleccionarAscendente() {
		int seleccion = menu.mostrarMenuConInstruccionOpciones("seleccionarAscendente");
		return (seleccion == 1) ? true : false;
	}
	
	/**
	 * Genera un listado de competidores ordenados por las características especificadas, de manera ascendente o descendente.
	 *
	 * @param criterios   Lista de características por las que se va a ordenar.
	 * @param ascendente  Indica si el ordenamiento será ascendente (true) o descendente (false).
	 * @throws Exception  Si la lista de criterios está vacía o nula, o si la lista de personajes es nula o está vacía.
	 */
	private void listadoOrdenadoPorCaracteristica(List<Caracteristica> criterios, boolean ascendente) throws Exception{
		
		if (criterios == null || criterios.isEmpty()) {
			// Si no hay criterios, usamos el orden por defecto de las características notificando al usuario
			menu.mostrarError("listadoOrdenado");
			criterios = new LinkedList<Caracteristica>();
			criterios.addAll(new Ordenamiento().ordenCaracteristicas);
		}
		List<Competidor> personajesOrdenados = new ArrayList<Competidor>();
		// Instancia de Ordenamiento
		Ordenamiento orden = new Ordenamiento();
		orden.setearOrdenCaracteristicas(criterios);
		
		if (personajes == null || personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		// Añadimos y ordenamos
		personajesOrdenados.addAll(personajes);
		Collections.sort(personajesOrdenados, orden);
		if (ascendente) {
			Collections.reverse(personajesOrdenados);
		}
		menu.listarCompetidores(personajesOrdenados);
	}
	
	// ========== FIN MENU REPORTES ==========
}


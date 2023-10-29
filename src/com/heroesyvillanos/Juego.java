package com.heroesyvillanos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Juego {
	private List<Personaje> personajes;
	private List<Liga> ligas;
    private Scanner scanner = new Scanner(System.in);
    private static EnumMap<Caracteristica, String> caracteristicasMenu;
    
    private static void inicializarCaracteristicasMenu() {
    	caracteristicasMenu = new EnumMap<Caracteristica, String>(Caracteristica.class);
    	caracteristicasMenu.put(Caracteristica.VELOCIDAD, "1. Velocidad");
    	caracteristicasMenu.put(Caracteristica.FUERZA, "2. Fuerza");
    	caracteristicasMenu.put(Caracteristica.RESISTENCIA, "3. Resistencia");
    	caracteristicasMenu.put(Caracteristica.DESTREZA, "4. Destreza");
    }
    
    //inicialización
    static {
    	inicializarCaracteristicasMenu();
    }
    
    public void mostrarMenu() {
        boolean continuar = true;

        while(continuar) {
            System.out.println("");
            System.out.println("===== Menú Principal =====");
            System.out.println("1. Administración de Personajes");
            System.out.println("2. Administración de Ligas");
            System.out.println("3. Realización de combates");
            System.out.println("4. Reportes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch(opcion) {
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
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
 // ========== INICIO MENU PERSONAJES ==========
    
    private void menuPersonajes() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("===== Administración de Personajes =====");
            System.out.println("1. Carga desde archivo");
            System.out.println("2. Creación");
            System.out.println("3. Listado");
            System.out.println("4. Guardar en archivo todos los personajes");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();

            switch (seleccion) {
                case 1:
                    cargarPersonajesDesdeArchivo();
                    break;
                case 2:
                    crearPersonaje();
                    break;
                case 3:
                    listarPersonajes();
                    break;
                case 4:
                    guardarPersonajesEnArchivo();
                    break;
                case 5:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
        
	public void cargarPersonajesDesdeArchivo() {
        System.out.println("");
		System.out.println("Cargando personajes desde archivo...");
	}
	
	public void crearPersonaje() {
        System.out.println("");
		System.out.println("Creando personaje...");
	}
	
	public void listarPersonajes() {
        System.out.println("");
		System.out.println("Listando personajes...");
	}
	
	public void guardarPersonajesEnArchivo() {
        System.out.println("");
		System.out.println("Guardando personajes en archivo...");
	}
	
	// ========== FIN MENU PERSONAJES ==========
	
	// ========== INICIO MENU LIGAS ==========

    private void menuLigas() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("===== Administración de Ligas =====");
            System.out.println("1. Carga desde archivo");
            System.out.println("2. Creación");
            System.out.println("3. Listado");
            System.out.println("4. Guardar en archivo todas las ligas");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();

            switch (seleccion) {
                case 1:
                    cargarLigasDesdeArchivo();
                    break;
                case 2:
                    crearLiga();
                    break;
                case 3:
                    listarLigas();
                    break;
                case 4:
                    guardarLigasEnArchivo();
                    break;
                case 5:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
	public void cargarLigasDesdeArchivo() {
        System.out.println("");
		System.out.println("Cargando ligas desde archivo...");
	}
	
	public void crearLiga() {
        System.out.println("");
		System.out.println("Creando liga...");
	}
	
	public void listarLigas() {
        System.out.println("");
		System.out.println("Listando ligas...");
	}
	
	public void guardarLigasEnArchivo() {
        System.out.println("");
		System.out.println("Guardando ligas en archivo...");
	}
	
	// ========== FIN MENU LIGAS ==========
	
	// ========== INICIO MENU COMBATES ==========

    private void menuCombates() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("===== Realización de Combates =====");
            System.out.println("1. Personaje contra Liga");
            System.out.println("2. Liga contra Liga");
            System.out.println("3. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();

            switch (seleccion) {
                case 1:
                	// Personaje contra liga
                	realizarCombate();
                    break;
                case 2:
                	// Liga contra liga
                	realizarCombate();
                    break;
                case 3:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
	public void realizarCombate() {
		// Toma dos competidores y una caracteristica
        System.out.println("");
		System.out.println("Realizando combate...");
	}
    
	// ========== FIN MENU COMBATES ==========

	// ========== INICIO MENU REPORTES ==========

    private void menuReportes() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("===== Reportes =====");
            System.out.println("1. Personajes o ligas que vencen a un personaje dado");
            System.out.println("2. Listado ordenado de personajes por características");
            System.out.println("3. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();

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
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

	private void menuObtenerVencedoresContra() {
    	/*
		Toma un competidor y una caracteristica,
		obtiene lista de competidores que vencerian a un
		competidor basandose en una caracteristica
		*/
    	Personaje personajeAEvaluar = menuSeleccionarPersonaje();
    	if (personajeAEvaluar == null)
    	{
    		menuReportes();
    		return;
    	}
    	
    	Caracteristica caracteristicaEvaluativa = menuSeleccionarCaracteristica();
    	if (caracteristicaEvaluativa == null)
    	{
    		menuReportes();
    		return;
    	}
		
		System.out.println("Vencedores:");
		System.out.println(obtenerVencedoresContra(personajeAEvaluar, caracteristicaEvaluativa));
    }
    
    private Personaje menuSeleccionarPersonaje() {
    	Personaje personajeAEvaluar = null;
    	String entradaUsuario;
    	
    	System.out.println("Seleccione un personaje de la siguiente lista o ingrese 0 para salir:");
		listarPersonajes();
		while (personajeAEvaluar == null) {
			entradaUsuario = scanner.nextLine();
			if (entradaUsuario == "0") return null;
			
			personajeAEvaluar = obtenerPersonajePorNombreFantasia(entradaUsuario);			
			if (personajeAEvaluar == null) {
				System.out.println("El personaje ingresado no existe.");
			}
		}
		return personajeAEvaluar;
    }
    
    private Caracteristica menuSeleccionarCaracteristica() {
    	Caracteristica caracteristicaEvaluativa = null;
    	int entradaUsuario;
    	
    	System.out.println("Seleccione una caracteristica o ingrese 0 para salir:");
		listarCaracteristicas();
		while (caracteristicaEvaluativa == null) {
			entradaUsuario = scanner.nextInt();
			if (entradaUsuario == 0) return null;
			
			//Ver si se puede quitar el hardcode utilizando el mismo EnumMap (o hacer otro?)
			switch (entradaUsuario) {
				case 1: caracteristicaEvaluativa = Caracteristica.VELOCIDAD;
				case 2: caracteristicaEvaluativa = Caracteristica.FUERZA;
				case 3: caracteristicaEvaluativa = Caracteristica.RESISTENCIA;
				case 4: caracteristicaEvaluativa = Caracteristica.DESTREZA;
				default: System.out.println("La caracteristica seleccionada no existe.");
			}
		}
		return caracteristicaEvaluativa;
    }
    
    private Personaje obtenerPersonajePorNombreFantasia(String nombre) {
    	for (Personaje personaje : personajes) {
    		if (personaje.getNombreFantasia() == nombre) {
    			return personaje;
    		}
    	}
    	return null;
    }
    
    private void listarCaracteristicas() {
    	System.out.println(caracteristicasMenu.values());
    }
	
	private List<Competidor> obtenerVencedoresContra(Competidor retador, Caracteristica caracteristica) {
		List<Competidor> contrincantes = new ArrayList<Competidor>();
		List<Competidor> vencedores = new ArrayList<Competidor>();
		
		contrincantes.addAll(ligas);
		contrincantes.addAll(personajes);
		
		for (Competidor contrincante : contrincantes) {
			if (contrincante.esGanador(retador, caracteristica)) {
				vencedores.add(contrincante);
			}
		}
		return vencedores;
	}
	
	private List<Caracteristica> seleccionarCriteriosOrdenamiento() {
		List<Caracteristica> criterios = new LinkedList<Caracteristica>();
		Caracteristica caracteristicaSeleccionada;
		boolean salida = false;
		boolean agregarCaracteristica = true;		
		
		while(!salida)
		{
			caracteristicaSeleccionada = menuSeleccionarCaracteristica();
			
			if (caracteristicaSeleccionada == null) {
				return criterios;
			}			
			if (criterios.indexOf(caracteristicaSeleccionada) >= 0) {
				System.out.println("La característica seleccionada ya fue seleccionada previamente. No pueden repetirse.");
			}
			else
			{
				criterios.add(caracteristicaSeleccionada);
			}
			
			while(agregarCaracteristica) {
				System.out.println("Ingrese 1 para agregar una nueva característica o 0 para salir:");
				switch (scanner.nextInt()) {
					case 0: {
						agregarCaracteristica = false;
						return criterios;
					}
					case 1: {}
					default: System.out.println("Ingrese 1 para agregar una nueva característica o 0 para salir:");
				}
			}
		}
		return criterios;
	}
	
	private boolean seleccionarAscendente() {
		while(true) {
			System.out.println("Ingrese 1 para mostrar en orden ascendente, 0 para orden descendente:");
			switch (scanner.nextInt()) {
				case 0: return false;
				case 1: return true;
				default: System.out.println("El valor ingresado no es válido. Intente nuevamente.");
			}
		}
	}
	
	public void listadoOrdenadoPorCaracteristica(List<Caracteristica> criterios, boolean ascendente) {
		List<Competidor> personajesOrdenados = new ArrayList<Competidor>();
		personajesOrdenados.addAll(personajes);
		Collections.sort(personajesOrdenados, new Ordenamiento());
		if (ascendente) { //probar si es así o al revés
			Collections.reverse(personajesOrdenados);
		}
		System.out.println(personajesOrdenados);
	}
	
	// ========== INICIO MENU REPORTES ==========
}

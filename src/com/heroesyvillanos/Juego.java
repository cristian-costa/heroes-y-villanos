package com.heroesyvillanos;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Juego {
	private List<Personaje> personajes = new ArrayList<>();
	private List<Liga> ligas = new ArrayList<>();
	private Ordenamiento ordenamiento;
    private Scanner scanner = new Scanner(System.in);
    
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
					try {
						listarPersonajes();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
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
		boolean seguir;
		String nombreFantasia = "";
		String nombreReal = "";
		Map<Caracteristica, Integer> mapCaracteristicas = new HashMap<>();;
		
        System.out.println("");
		System.out.println("Creando personaje...");
		System.out.println("Ingrese nombre de fantasía: ");
		seguir = true;
		scanner.nextLine(); //consumir enter pendiente de nextInt()
		while (seguir) {
			seguir = false;
			nombreFantasia = scanner.nextLine();
			
			if (!personajes.isEmpty()) {
				for (Personaje personaje : personajes) {
				    if (personaje.getNombreFantasia().equals(nombreFantasia)) {
				    	System.out.println("Personaje ya existente, ingrese nuevamente: ");
				    	seguir = true;
				        break;
				    }
				}
			}
		}
		
		System.out.println("");
		System.out.println("Ingrese nombre real: ");
		
//		seguir = true;
//		while (seguir = true) {
//			seguir = false;
			nombreReal = scanner.nextLine();
//		}
			
		System.out.println("");
		System.out.println("Ingrese valores para las características: ");
		
		Caracteristica[] caracteristicas = Caracteristica.values();
		
		
    	for(int i = 0; i < caracteristicas.length; i++) {
    		
    		System.out.println((i + 1) + ". " + caracteristicas[i] + " valor: ");
    		seguir = true;
    		
    		while (seguir) {
    			int valor = scanner.nextInt();
    			if (valor > 0) {
    				seguir = false;
    				mapCaracteristicas.put(caracteristicas[i], valor);
    			} else {
    				System.out.println("Valor incorrecto, intente nuevamente: ");
    			}
    		}
    	}
    	
    	personajes.add(new Personaje(nombreReal, nombreFantasia, mapCaracteristicas));
		
	}
	
	public void listarPersonajes() throws Exception {
		if (!personajes.isEmpty()) {
			System.out.println("");
			System.out.println("Listando personajes...");
			for (int i = 0; i < personajes.size(); i++) {
				System.out.println((i+1) + ". " + personajes.get(i));
			}
		} else {
			throw new Exception("La lista de personajes está vacía."); 
		}
		
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
					try {
						listarLigas();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
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
	
	public void listarLigas() throws Exception{
		if(!ligas.isEmpty()) {
			System.out.println("");
			System.out.println("Listando ligas...");
			for (int i = 0; i < ligas.size(); i++) {
				System.out.println((i+1) + ". " + ligas.get(i));
			}
		} else {
			throw new Exception("La lista de ligas está vacía."); 
		}
		
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
            System.out.println("===== Combates =====");
            System.out.println("1. Realizar combate");
            System.out.println("2. Ver reglas de combate");
            System.out.println("3. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();

            switch (seleccion) {
                case 1:
                	realizarCombateMenu();
                    break;
                case 2:
                	mostrarReglas();
                    break;
                case 3:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
    public void realizarCombateMenu() {
    	try {
    		System.out.println("");
    		System.out.println("Elegir primer competidor:");
        	Competidor comp1 = seleccionarCompetidor();
        	System.out.println("");
    		System.out.println("Elegir segundo competidor:");
        	Competidor comp2 = seleccionarCompetidor();
        	Caracteristica car = seleccionarCaracteristica();
        	
        	combatir(comp1, comp2, car);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println("Regresando al menú de combates");
		}
    	
    }
    
    public Competidor seleccionarCompetidor() throws Exception {
    	boolean seguir = true;
    	int seleccionTipoCompetidor = 0;
    	int seleccionCompetidor = 0;

        while (seguir) {
            System.out.println("1. Personaje");
            System.out.println("2. Liga");
            System.out.print("Seleccione una opción: ");
            
            seleccionTipoCompetidor = scanner.nextInt();

            switch (seleccionTipoCompetidor) {
                case 1:
                	listarPersonajes();
                	seguir = false;
                    break;
                case 2:
                	listarLigas();
                	seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        
        seguir = true;
        
        while (seguir) {
        	System.out.print("Seleccione una opción: ");
        	
        	seleccionCompetidor = scanner.nextInt();
        	
        	if ((seleccionTipoCompetidor == 1 && //Personaje
        			(seleccionCompetidor < 1 || seleccionCompetidor > personajes.size())) || //Fuera de rango personaje
        		(seleccionTipoCompetidor == 2 && //Liga
        			(seleccionCompetidor < 1 || seleccionCompetidor > ligas.size()))) { //Fuera de rango liga
        		System.out.println("Opción no válida. Intente de nuevo.");
        	}
        	else {
        		seguir = false;
        	}
        }        
        
        System.out.println("Elegiste: " + (seleccionTipoCompetidor == 1 ? personajes.get(seleccionCompetidor - 1) : ligas.get(seleccionCompetidor - 1)));
        return seleccionTipoCompetidor == 1 ? personajes.get(seleccionCompetidor - 1) : ligas.get(seleccionCompetidor - 1);
    }
    
    public Caracteristica seleccionarCaracteristica() {
    	System.out.println("Características: ");
    	Caracteristica[] caracteristicas = Caracteristica.values();
    	int seleccion = 0;
    	listarCaracteristicas();
    	System.out.println("");
		System.out.println("Ingresar opción de característica elegida: ");
		boolean seguir = true;

        while (seguir) {
        	seleccion = scanner.nextInt();
        	
        	if(seleccion < 0 || seleccion > caracteristicas.length) {
        		System.out.println("Opción no válida. Intente de nuevo.");
        	} else {
        		seguir = false;
        	}
        }
        
        System.out.println("Elegiste: " + caracteristicas[seleccion - 1]);
        return caracteristicas[seleccion - 1];
    	
    }
    
    public void listarCaracteristicas() {
    	Caracteristica[] caracteristicas = Caracteristica.values();
    	for(int i = 0; i < caracteristicas.length; i++) {
    		System.out.println((i + 1) + ". " + caracteristicas[i]);
    	}
    }
    
	public void combatir(Competidor c1, Competidor c2, Caracteristica car) {
		// Toma dos competidores y una caracteristica
		System.out.println("");
		System.out.println("Combatiendo...");
		int resultado = c1.esGanador(c2, car);
		
		if (resultado > 0) {
			System.out.println("El ganador es: " + c1);
		} else if (resultado < 0) {
			System.out.println("El ganador es: " + c2);
		} else {
			System.out.println("La pelea termina en empate" );
		}
        
	}
	
	public void mostrarReglas() {
        System.out.println("");
		System.out.println("===== Reglas de combate =====");
		System.out.println("Para decidir quién es el ganador en un combate entre dos competidores se utiliza el valor de una de");
		System.out.println("las características. En caso de empate, se decide por el valor de otra característica (en el orden ");
		System.out.println("establecido, y volviendo a comenzar si fuera necesario).");
		System.out.println("Por ejemplo: Si dos contendientes decidieran competir por Fuerza, y empatan, definen por ");
		System.out.println("Resistencia. Si hubiera otro empate, definen por Destreza. Ante otro empate, definen por");
		System.out.println("Velocidad. Si resulta en empate, será empate finalmente.");
		System.out.println("El orden es:");
		listarCaracteristicas();
		System.out.println("Pueden enfrentarse personajes contra personajes, personajes contra ligas, o ligas contra ligas.");
		System.out.println("Cuando se trata de una liga, el valor de cada característica se determina como el promedio de los ");
		System.out.println("valores de esa característica entre todos los personajes que componen la liga.");

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
                    obtenerVencedoresContra();
                    break;
                case 2:
                    // TO DO: seleccionar las características por las que quieres ordenar
                    List<Caracteristica> criterios = seleccionarCriteriosOrdenamiento();
                    listadoOrdenadoPorCaracteristica(criterios);
                    break;
                case 3:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
	public void obtenerVencedoresContra() {
        System.out.println("");
		// Toma un competidor y una caracteristica, obtiene lista de competidores que vencerian a un competidor basandose en una caracteristica
		System.out.println("Obtiene listado de personajes o ligas que vencen a un personaje dado");
	}
	
	private List<Caracteristica> seleccionarCriteriosOrdenamiento() {
        System.out.println("");
		System.out.println("Obtiene listado ordenado de personajes por caracteristicas...");
		return null;
	}
	
	public void listadoOrdenadoPorCaracteristica(List<Caracteristica> criterios) {
		// Devuelve lista de personajes ordenada por las caracteristicas especificadas.
	}
	
	// ========== INICIO MENU REPORTES ==========
}

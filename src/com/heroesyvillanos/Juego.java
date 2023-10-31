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

public class Juego {
	private ArrayList<Personaje> personajes;
	private ArrayList <Liga> ligas;
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
                	try {
                		this.personajes = cargarPersonajesDesdeArchivo("src/personajes.in");
                	}catch (FileNotFoundException e) {
						// manejor del error. yo devuelvo una excepcion, este por si no esta el archivo 
					}catch (IOException e) {
						// Este por si falla la lectura del archvo  
					}
                    
                    break;
                case 2:
                    crearPersonaje();
                    break;
                case 3:
                    listarPersonajes();
                    break;
                case 4:
					try {
						guardarPersonajesEnArchivo(this.personajes, "src/personajes.in");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    break;
                case 5:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
        
	public ArrayList<Personaje> cargarPersonajesDesdeArchivo(String path) throws IOException {
		
		 
		 ArrayList<Personaje> listaPersonaje = new ArrayList<Personaje>();

	        try (
	       
	        	BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String line;	            
	            line = br.readLine();//leo la cabacera para ignorarla
	            boolean heovi = false;
	            while ((line = br.readLine()) != null) {
	            	    	
	            	String[] atributos = line.split(",");
	            	Map<Caracteristica, Integer> caract = new HashMap<Caracteristica, Integer>();
	            	
	            	caract.put(Caracteristica.VELOCIDAD, 	Integer.valueOf(atributos[3].trim()));
	            	caract.put(Caracteristica.FUERZA, 		Integer.valueOf(atributos[4].trim()));
	            	caract.put(Caracteristica.RESISTENCIA, 	Integer.valueOf(atributos[5].trim()));
	            	caract.put(Caracteristica.DESTREZA, 	Integer.valueOf(atributos[6].trim()));
	            	
	            	
	            	
	            	if (atributos[0].equals("Héroe")) {
	            		heovi = true;
	            	}else if(atributos[0].equals("Villano")) {
	            		heovi = false;
	            	}else {
	            		System.err.println("NO EXISTE TIPO DE PARTICIPANTE");
	            		return null; 
	            	}
	            	
	            	
	            	Personaje p = new Personaje(atributos[1], atributos[2], caract,heovi);
	            	
	            	listaPersonaje.add(p);
	            }
	            br.close();
	            
	        }catch (FileNotFoundException e) {	        	
	        	throw new FileNotFoundException("No se pudo encontrar el archivo: " + path);
				        	
	        }catch (IOException e) {
	        	throw new IOException("Error al leer o escribir el archivo");
	        }
	        
	        return listaPersonaje;
		}
	
	public void crearPersonaje() {
        System.out.println("");
		System.out.println("Creando personaje...");
	}
	
	public void listarPersonajes() {
        System.out.println("");
		System.out.println("Listando personajes...");
	}
	
	public void guardarPersonajesEnArchivo(ArrayList<Personaje> lista, String path) throws IOException {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			
			writer.write("Héroe/Villano, NombreReal, NombrePersonaje, Velocidad, Fuerza, Resistencia, Destreza");
			writer.newLine();
			for (Personaje personaje : lista) {
				writer.write(personaje.toFileLine());
				writer.newLine();
			}
        }catch (IOException e) {
        	throw new IOException("Error al leer o escribir el archivo");
        }
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
                	try {
                		this.ligas = cargarLigasDesdeArchivo(this.personajes, "src/ligas.in");
            		}catch (FileNotFoundException e) {
            			// manejor del error. yo devuelvo una excepcion, este por si no esta el archivo 
            		}catch (IOException e) {
            			// Este por si falla la lectura del archvo  
            		}
            
                    break;
                case 2:
                    crearLiga();
                    break;
                case 3:
                    listarLigas();
                    break;
                case 4:
					try {
						guardarLigasEnArchivo(this.ligas,"src/ligas.in");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    break;
                case 5:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
	public ArrayList<Liga> cargarLigasDesdeArchivo(ArrayList<Personaje>listPersonaje, String path) throws IOException {
		
		ArrayList<Liga> listaLiga = new ArrayList<Liga>();
        try (
       
        	BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean heovi = false;
            while ((line = br.readLine()) != null) {
            	    	
            	ArrayList<Competidor> competidores = new ArrayList<Competidor>();          	
            	String[] ligaString = line.split(",");
            	
            	for (String personajeNombre : ligaString) {
            		
            		//agrego los personaje
            		for (Personaje per : listPersonaje) {						
						
						if(per.getNombreFantasia().trim().equals(personajeNombre.trim() )) {
							heovi=per.isTipoCompetidor();
							competidores.add(per);
						}
												
					}
            		//agrego las ligas
            		for(Liga lig : listaLiga) {
            			if(lig.getNombre().trim().equals(personajeNombre.trim() )) {
            				heovi=lig.isTipoCompetidor();
							competidores.add(lig);
						}
            		}
				}
            	
            	if(!competidores.isEmpty()) {
            		listaLiga.add(new Liga(ligaString[0], competidores,heovi));
            	}          
            }
            
        }catch (FileNotFoundException e) {	        	
        	throw new FileNotFoundException("No se pudo encontrar el archivo: "+ path);
			        	
        }catch (IOException e) {
        	throw new IOException("Error al leer o escribir el archivo");
        }
        
		return listaLiga;
	}
	
	
	
	public void crearLiga() {
        System.out.println("");
		System.out.println("Creando liga...");
	}
	
	public void listarLigas() {
        System.out.println("");
		System.out.println("Listando ligas...");
	}
	
	public void guardarLigasEnArchivo(ArrayList<Liga> lista, String path) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			for (Liga liga : lista) {
				writer.write(liga.toFileLine());
				writer.newLine();
			}			
			
        }catch (IOException e) {
        	throw new IOException("Error al leer o escribir el archivo");
        }
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

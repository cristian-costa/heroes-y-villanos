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
    private Scanner scanner = new Scanner(System.in);
    
    public void mostrarMenu() throws Exception {
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
                		this.personajes = cargarPersonajesDesdeArchivo("archivos/personajes.in");
                	} catch (FileNotFoundException e) {
						// manejor del error. yo devuelvo una excepcion, este por si no esta el archivo 
					} catch (IOException e) {
						// Este por si falla la lectura del archvo  
					}
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
					try {
						guardarPersonajesEnArchivo(this.personajes, "archivos/personajes.in");
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
	            	
	            	caract.put(Caracteristica.VELOCIDAD, 	Integer.valueOf(atributos[3].strip()));
	            	caract.put(Caracteristica.FUERZA, 		Integer.valueOf(atributos[4].strip()));
	            	caract.put(Caracteristica.RESISTENCIA, 	Integer.valueOf(atributos[5].strip()));
	            	caract.put(Caracteristica.DESTREZA, 	Integer.valueOf(atributos[6].strip()));
	            	
	            	if (atributos[0].equalsIgnoreCase("Héroe") || atributos[0].equalsIgnoreCase("Heroe")) {
	            		heovi = TipoCompetidor.HEROE;
	            	} else if (atributos[0].equalsIgnoreCase("Villano")) {
	            		heovi = TipoCompetidor.VILLANO;
	            	} else {
	            		System.err.println("NO EXISTE TIPO DE PARTICIPANTE");
	            		return null; 
	            	}
	            	
	            	Personaje p = new Personaje(atributos[1].strip(), atributos[2].strip(), caract, heovi);
	            	
	            	listaPersonaje.add(p);
	            }
	            
	            br.close();
	            
		} catch (FileNotFoundException e) {	        	
			throw new FileNotFoundException("No se pudo encontrar el archivo: " + path);
		} catch (IOException e) {
			throw new IOException("Error al leer o escribir el archivo");
		}
	        
		return listaPersonaje;
	}
	
	public void guardarPersonajesEnArchivo(List<Personaje> lista, String path) throws IOException {
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
	
	public void crearPersonaje() {
		boolean seguir;
		TipoCompetidor tipo;
		String nombreFantasia = "";
		String nombreReal = "";
		Map<Caracteristica, Integer> mapCaracteristicas = new HashMap<>();;
		
        System.out.println("");
		System.out.println("Creando personaje...");
	
		do {
			System.out.println("Ingrese nombre de fantasía: ");
			seguir = true;
			scanner.nextLine(); // consumir enter pendiente de nextInt()
			
			while (seguir) {
				seguir = false;
				nombreFantasia = scanner.nextLine().strip();

				if (!personajes.isEmpty()) {
					for (Personaje personaje : personajes) {
						if (personaje.getNombreFantasia().equalsIgnoreCase(nombreFantasia)) {
							System.out.println("Personaje ya existente, ingrese nuevamente: ");
							seguir = true;
							break;
						}
					}
				}
			}
			
			tipo = ingresoTipo("Personaje");

			System.out.println("");
			System.out.println("Ingrese nombre real: ");

			// seguir = true;
			// while (seguir = true) {
				// seguir = false;
				nombreReal = scanner.nextLine().strip();
			// }

			System.out.println("");
			System.out.println("Ingrese valores para las características: ");

			Caracteristica[] caracteristicas = Caracteristica.values();

			for (int i = 0; i < caracteristicas.length; i++) {
				System.out.println((i + 1) + ". " + caracteristicas[i] + " valor: ");
				seguir = true;
				int valor;
				
				while (seguir) {
					try {
						valor = scanner.nextInt();
						if (valor > 0) {
							seguir = false;
							mapCaracteristicas.put(caracteristicas[i], valor);
						} else {
							System.out.println("Valor incorrecto, intente nuevamente: ");
						}
					} catch(Exception E) {
						scanner.nextLine();
						System.out.println("Valor incorrecto, intente nuevamente: ");
						seguir = true;
					}
				}
			}

			seguir = false;
			
			try {
				personajes.add(new Personaje(nombreReal, nombreFantasia, mapCaracteristicas, tipo)); 
			} catch (Exception E) {
				System.out.println(E.getMessage() + " PRESIONE ENTER PARA REINGRESAR DATOS... ");
				seguir = true;
			}
		} while (seguir);

		System.out.println("PERSONAJE CREADO EXITOSAMENTE!");
	}
	
	private TipoCompetidor ingresoTipo(String s) {
		boolean seguir;
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		int aux=0;
		
		System.out.print("Ingrese tipo de " + s +"(1)Heroe / (2)Villano : ");
		do {
			seguir = false;
			try { // MANEJO DE EXCEPCION PARA EL CASO DE INGRESO DE STRING
				aux = Integer.parseInt(scanner.nextLine()); // LO LEO COMO STRING PARA QUE LEA TODA LA LINEA Y NO DEJE NADA EN EL BUFFER
				while (aux != 1 && aux != 2) {
					System.out.print("La opcion ingresada es incorrecta, ingrese 1 para Heroe o 2 para Villano : ");
					aux = Integer.parseInt(scanner.nextLine());
				}
				if (aux == 1)
					tipo = TipoCompetidor.HEROE;
				if (aux == 2)
					tipo = TipoCompetidor.VILLANO;

			} catch (Exception ex) {
				System.out.print("La opcion ingresada es incorrecta, ingrese 1 para Heroe o 2 para Villano : ");
				seguir = true;
			}
		} while (seguir == true);
		
		return tipo;
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
	
	// ========== FIN MENU PERSONAJES ==========
	
	// ========== INICIO MENU LIGAS ==========

    private void menuLigas() throws Exception {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("===== Administración de Ligas =====");
            System.out.println("1. Carga desde archivo");
            System.out.println("2. Creación");
            System.out.println("3. Listado");
            System.out.println("4. Guardar en archivo todas las ligas");
            System.out.println("5. Agregar Competidores a Liga");
            System.out.println("6. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();

            switch (seleccion) {
                case 1:
                	try {
                		this.ligas = cargarLigasDesdeArchivo(this.personajes, "archivos/ligas.in");
            		}catch (FileNotFoundException e) {
            			// Manejo del error. yo devuelvo una excepcion, este por si no esta el archivo 
            		}catch (IOException e) {
            			// Este por si falla la lectura del archvo  
            		}
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
					try {
						guardarLigasEnArchivo(this.ligas,"archivos/ligas.in");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    break;
                case 5:
                	scanner.nextLine();          
                	ingresaNombreLigaAgregaCompetidor(); 
                    break;
                case 6:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
	public ArrayList<Liga> cargarLigasDesdeArchivo(List<Personaje>listPersonaje, String path) throws Exception {
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
						if(per.getNombreFantasia().strip().equalsIgnoreCase(personajeNombre.strip())) {
							heovi = per.isTipoCompetidor();
							competidores.add(per);
						}						
					}
            		// Agrego las ligas
            		for(Liga lig : listaLiga) {
            			if(lig.getNombre().strip().equalsIgnoreCase(personajeNombre.strip())) {
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
        	throw new FileNotFoundException("No se pudo encontrar el archivo: "+ path);     	
        } catch (IOException e) {
        	throw new IOException("Error al leer o escribir el archivo");
        }
        
		return listaLiga;
	}
	
	public void guardarLigasEnArchivo(List<Liga> lista, String path) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			for (Liga liga : lista) {
				writer.write(liga.toFileLine());
				writer.newLine();
			}			
			
        }catch (IOException e) {
        	throw new IOException("Error al leer o escribir el archivo");
        }
	}
	
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
	
	public void crearLiga() throws Exception{
		System.out.println("");
		System.out.println("Creando liga...");
		String nombreLiga;
		boolean valido, seguir;
		TipoCompetidor tipoLiga;
		int indexLiga;
		scanner.nextLine();

		do {
			// INGRESO NOMBRE de LIGA
			System.out.print("Ingrese nombre de la nueva Liga: ");
			nombreLiga = scanner.nextLine();

			do {
				valido = true;
				if (!validaCadena(nombreLiga) || existeLigaEnMemoria(nombreLiga)) {
					System.out.print("El nombre de Liga ingresado no es valido o ya fue cargado ingrese uno valido: ");
					nombreLiga = scanner.nextLine();
					valido = false;
				}

			} while (!valido);

			tipoLiga = ingresoTipo("Liga");

			try { // creo liga vacia
				ligas.add(new Liga(nombreLiga, tipoLiga));
				seguir = false;
			} catch (Exception E) {
				System.out.println(E.getMessage() + " Debe reingresar datos...");
				seguir = true;
			}

		} while (seguir);
		
		indexLiga = ligas.size() - 1;

		// AGREGO PERSONAJE O LIGA
		seleccionaCompetidorAAgregar(indexLiga);
	}
	
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
		}while(seguir);
		
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
		boolean seguir=true;
		int indicePersonaje;
		
		this.listarPersonajes();
		System.out.println("Ingrese el numero de personaje que desea agregar: ");
		indicePersonaje = scanner.nextInt();
		
		do {
			if(indicePersonaje > 0 && indicePersonaje <= personajes.size() ) {
					try {
						ligas.get(indexLiga).agregarCompetidorALiga(personajes.get(indicePersonaje-1));
						System.out.println("Personaje CARGADO CORRECTAMENTE A LA LIGA");
					} catch (Exception E) {
						System.out.println(E.getMessage());
					}
					seguir=false;
			}
			else {
				System.out.println("Opcion incorrecta, ingrese una valida: ");
				indicePersonaje = scanner.nextInt();
				seguir=true;
			}
		}while(seguir);
	}
	
	private void agregaLigaALiga(int indexLiga) throws Exception {
		boolean seguir=true;
		int indiceLigaAgregar;
    
		this.listarLigas();
		System.out.println("Ingrese el numero de liga que desea agregar: ");
		indiceLigaAgregar = scanner.nextInt();

		do {
			if (indiceLigaAgregar > 0 && indiceLigaAgregar <= ligas.size()) {
				seguir=false;
				if (!ligas.get(indiceLigaAgregar-1).getCompetidores().contains(ligas.get(indexLiga))) { // evita que la liga
																										// entre en una liga
																										// que forma parte de
																										// ella
					try {
						ligas.get(indexLiga).agregarCompetidorALiga(ligas.get(indiceLigaAgregar-1));
						System.out.println("Liga CARGADA CORRECTAMENTE");
					} catch (Exception E) {
						System.out.println(E.getMessage());
					}
					
				}
				else
					System.out.println("Liga no valida.");
			}
			else {
				System.out.println("Opcion incorrecta, ingrese una valida: ");
				indiceLigaAgregar = scanner.nextInt();
				seguir=true;
			}
		}while(seguir);
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
    
	public void combatir(Competidor c1, Competidor c2, Caracteristica car) throws Exception {
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

    private void menuReportes() throws Exception {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("===== Reportes =====");
            System.out.println("1. Personajes o ligas que vencen a un personaje dado");
            System.out.println("2. Listado ordenado de personajes por características");
            System.out.println("3. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int seleccion = scanner.nextInt();
            scanner.nextLine(); //consumir enter

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
		
    	personajeAEvaluar = menuSeleccionarPersonaje();
    	if (personajeAEvaluar == null) {
    		menuReportes();
    		return;
    	}
    	
    	caracteristicas = menuSeleccionarCaracteristica(null);
    	if (caracteristicas.isEmpty()) {
    		menuReportes();
    		return;
    	}
    	else {
    		caracteristicaEvaluativa = caracteristicas.get(0);
    	}
		
		System.out.println("Vencedores:");
		System.out.println(obtenerVencedoresContra(personajeAEvaluar, caracteristicaEvaluativa));
    }
	
    private Personaje menuSeleccionarPersonaje() throws Exception {
    	Personaje personajeAEvaluar = null;
    	String entradaUsuario;
    	
    	System.out.println("Seleccione un personaje de la siguiente lista según su nombre de fantasía o ingrese 0 para salir:");
		listarPersonajes();
		while (personajeAEvaluar == null) {
			entradaUsuario = scanner.nextLine();
			if (entradaUsuario.equals("0")) return null;
			
			personajeAEvaluar = obtenerPersonajePorNombreFantasia(entradaUsuario);			
			if (personajeAEvaluar == null) {
				System.out.println("El personaje ingresado no existe.");
			}
		}
		return personajeAEvaluar;
    }
    
    private Personaje obtenerPersonajePorNombreFantasia(String nombre) {
    	if (personajes == null) return null;
    	for (Personaje personaje : personajes) {
    		if (personaje.getNombreFantasia().strip().equals(nombre)) {
    			return personaje;
    		}
    	}
    	return null;
    }
    
	private List<Competidor> obtenerVencedoresContra(Competidor retador, Caracteristica caracteristica) throws Exception {
		List<Competidor> contrincantes = new ArrayList<Competidor>();
		List<Competidor> vencedores = new ArrayList<Competidor>();
		
		TipoCompetidor tipoRetador = retador.getTipoCompetidor();
		
		contrincantes.addAll(ligas);		
		contrincantes.addAll(personajes);
		contrincantes.removeIf(c -> (tipoRetador == c.getTipoCompetidor()));

		// VER -> Ver esta logiva de contrincante.esGanador. antes retornaba un boolean, ahora un int. Creo que si es negativo es que no es ganador, pero no se como se implemento.
		// Agrego la condicion de que sea > 0 para considerarlo ganador, por ahi hay que modificar esta logica, lo agrego para que compile.
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
		//Se puede utilizar la clase ordenamiento? Vale la pena hacer una clase especifica para la lista de criterios?
		List<Caracteristica> criterios = new LinkedList<Caracteristica>();
		
		criterios = menuSeleccionarCaracteristica(criterios); //si se pasa por referencia, no hace falta el "criterios =" ?
		if (criterios.isEmpty()) {
			return criterios;
		}
		criterios = menuAgregarCaracteristica(criterios); //si se pasa por referencia, no hace falta el "criterios =" ?
		return criterios;
	}
	
	private List<Caracteristica> menuSeleccionarCaracteristica(List<Caracteristica> criterios) {
    	Caracteristica caracteristicaEvaluativa = null;
    	int entradaUsuario;
    	
    	if (criterios == null) {
    		criterios = new LinkedList<Caracteristica>();
    	}
    	
    	System.out.println("Seleccione una caracteristica o ingrese 0 para salir:");
		listarCaracteristicas();
		while (caracteristicaEvaluativa == null) {
			entradaUsuario = scanner.nextInt();
			if (entradaUsuario == 0) return criterios;
			
			//Ver si se puede quitar el hardcode utilizando el mismo EnumMap (o hacer otro?)
			switch (entradaUsuario) {
				case 1:
					caracteristicaEvaluativa = Caracteristica.VELOCIDAD;
					break;
				case 2:
					caracteristicaEvaluativa = Caracteristica.FUERZA;
					break;
				case 3:
					caracteristicaEvaluativa = Caracteristica.RESISTENCIA;
					break;
				case 4:
					caracteristicaEvaluativa = Caracteristica.DESTREZA;
					break;
				default: System.out.println("La caracteristica seleccionada no existe.");
			}
			if (criterios.indexOf(caracteristicaEvaluativa) >= 0) {
				System.out.println("La característica seleccionada ya fue seleccionada previamente. No pueden repetirse.");
				caracteristicaEvaluativa = null;
			}
			else {
				criterios.add(caracteristicaEvaluativa);
			}
		}
		return criterios;
    }
    
	private List<Caracteristica> menuAgregarCaracteristica (List<Caracteristica> criterios) {
		final String mensaje = "Ingrese 1 para agregar una nueva característica o 0 para salir:";
		boolean agregarCaracteristica = true;
		int entradaUsuario;
		
		while(agregarCaracteristica) {
			System.out.println(mensaje);
			entradaUsuario = scanner.nextInt();
			switch (entradaUsuario) {
				case 0: {
					agregarCaracteristica = false;
					return criterios;
				}
				case 1: criterios = menuSeleccionarCaracteristica(criterios);
					break;
				default: System.out.println(mensaje);
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
		if (criterios == null || criterios.isEmpty()) {
			System.out.println("No se estableció un orden específico de características. Se utilizará el orden por defecto.");
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
		System.out.println(personajesOrdenados);
	}
	
	// ========== FIN MENU REPORTES ==========
}


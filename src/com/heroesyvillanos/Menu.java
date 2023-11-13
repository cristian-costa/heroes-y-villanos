package com.heroesyvillanos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Menu {
	
	private Scanner scanner = new Scanner(System.in);
	private static final Locale es_AR = new Locale("es", "AR");
	private static final ResourceBundle bundle = ResourceBundle.getBundle("resources.text", es_AR);
	
	
	public int mostrarMenuConTituloOpcionesSeleccion(String nombreMenu){
		ArrayList<String> opciones = new ArrayList<String>();
		
		String titulo = bundle.getString(nombreMenu + ".titulo");
		boolean continuar = true;
		int i = 1;
		while(continuar) {
			try {
				opciones.add(bundle.getString(nombreMenu + ".opcion" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
		opciones.add(bundle.getString(nombreMenu + ".salir"));
		
		String seleccion = bundle.getString(nombreMenu + ".seleccion");
		String error = bundle.getString(nombreMenu + ".error");
		
		mostrarMenuEnConsola(titulo, opciones, seleccion);
		
		int opcion = 0;
		continuar = true;
		
        while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
        
        return opcion;
		
	}
	
	public int mostrarMenuConInstruccionOpciones(String nombreMenu){
		ArrayList<String> opciones = new ArrayList<String>();
		
		String instruccion = bundle.getString(nombreMenu + ".instruccion");
		boolean continuar = true;
		int i = 1;
		while(continuar) {
			try {
				opciones.add(bundle.getString(nombreMenu + ".opcion" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
		String error = bundle.getString(nombreMenu + ".error");
		
		mostrarMenuEnConsola(instruccion, opciones);
		
		int opcion = 0;
		continuar = true;
		
        while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
        
        return opcion;
		
	}
	
	private void mostrarMenuEnConsola(String titulo, ArrayList<String> opciones, String seleccion) {
		animarTexto("");
		animarTexto(titulo);
		for(String opcion : opciones) {
			animarTexto(opcion);
		}
		animarTexto(seleccion);
		
	}
	
	private void mostrarMenuEnConsola(String instruccion, ArrayList<String> opciones) {
		animarTexto("");
		animarTexto(instruccion);
		for(String opcion : opciones) {
			animarTexto(opcion);
		}
		
	}
	
	private void mostrarTextoEnConsola(String s) {
		animarTexto("");
		animarTexto(s);
		
	}
	
	private void mostrarTextoEnConsolaSinSeparacion(String s) {
		animarTexto(s);
		
	}
	
	private int registrarOpcion(ArrayList<String> opciones){
		String op = scanner.nextLine();
		for(String opcion : opciones) {
			if (op != "" && opcion.startsWith(op)) {
				String[] splitOpcion = opcion.split("\\.");
				if (splitOpcion.length > 0) {
		            int numeroOpcion = Integer.parseInt(splitOpcion[0]);
		            return numeroOpcion;
				}
			}
		}
		return 0;
	}

	public String registrarPalabra(String nombreMenu, boolean error) {
		
		String instruccion = bundle.getString(nombreMenu + ".instruccion");
		if (error) {
			String err = bundle.getString(nombreMenu + ".error");
			mostrarTextoEnConsola(err);
		}
		
		mostrarTextoEnConsola(instruccion);
		String nombre = registrarString();
		return nombre;
	}
	
	private String registrarString() {
		String s = scanner.nextLine();
		return s;
	}
	
	public TipoCompetidor ingresoTipo(boolean personaje) {
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		int seleccion = 0;
		
		if (personaje) {
			seleccion = mostrarMenuConInstruccionOpciones("crearPersonajeTipo");
		} else {
			seleccion = mostrarMenuConInstruccionOpciones("crearLigaTipo");
		}
		
		if (seleccion == 1) {
			tipo = TipoCompetidor.HEROE;
		} else {
			tipo = TipoCompetidor.VILLANO;
		}
		
		return tipo;
	}

	public void mostrarTextoExcepcion(Exception e) {
		mostrarTextoEnConsola(e.getMessage());
		
	}

	public void mostrarTitulo(String nombreMenu) {
		String s = bundle.getString(nombreMenu + ".titulo");
		mostrarTextoEnConsola(s);
	}
	
	public void mostrarFinal(String nombreMenu) {
		String s = bundle.getString(nombreMenu + ".final");
		mostrarTextoEnConsola(s);
	}

	public Map<Caracteristica, Integer> registrarCaracteristicas() {
		Map<Caracteristica, Integer> caracteristicasValores = new HashMap<>();
		Caracteristica[] caracteristicas = Caracteristica.values();		
		String titulo = bundle.getString("registrarCaracteristica.titulo");
		mostrarTextoEnConsola(titulo);
		
		int valor = 0;
		boolean error;
		
		for (int i = 0; i < caracteristicas.length; i++) {
			error = false;
			do {
				valor = registrarValorCaracteristica((i+1) + "- " + caracteristicas[i] + ". ", error);
				error = false;
				if (valor > 0) {
					caracteristicasValores.put(caracteristicas[i], valor);
				} else {
					error = true;
				}
			} while (error);
			
		}
		return caracteristicasValores;
	}
	
	public void listarCaracteristicas() {
		Caracteristica[] caracteristicas = Caracteristica.values();
    	for(int i = 0; i < caracteristicas.length; i++) {
    		mostrarTextoEnConsolaSinSeparacion((i + 1) + "- " + caracteristicas[i]);
    	}
	}
	
	public int seleccionarCaracteristica() {
		
		ArrayList<String> opciones = new ArrayList<>();
		int opcion = 0;
		String instruccion = bundle.getString("seleccionarCaracteristica.instruccion");
		String error = bundle.getString("seleccionarCaracteristica.error");
		int i = 0;
		
		for (Caracteristica caracteristica : Caracteristica.values()) {
		    opciones.add((i+1) + ". " + caracteristica.name());
		    mostrarTextoEnConsolaSinSeparacion((i+1) + ". " + caracteristica.name());
		    i++;
		}
		
		mostrarTextoEnConsola(instruccion);
		
		boolean continuar = true;
		while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
		
		return opcion;
	}

	private int registrarValorCaracteristica(String caracteristicaTexto, boolean error) {
		String instruccion = bundle.getString("registrarCaracteristica.instruccion");
		if (error) {
			String err = bundle.getString("registrarCaracteristica.error");
			mostrarTextoEnConsola(err);
		}
		
		mostrarTextoEnConsola(caracteristicaTexto + instruccion);
		
		String val = scanner.nextLine();
		int numeroValor = 0;
		if (!val.isEmpty()) {
			try {
				numeroValor = Integer.parseInt(val);
			} catch (Exception e) {
				numeroValor = 0;
			}
		}
		return numeroValor;
	}

	public void mostrarBienvenida() {
		String titulo = bundle.getString("bienvenida.titulo");
		mostrarTextoEnConsola(titulo);
		
	}

	public void listarCompetidores(List<? extends Competidor> competidores) {
		for (int i = 0; i < competidores.size(); i++) {
			mostrarTextoEnConsolaSinSeparacion((i+1) + "- " + competidores.get(i));
		}
		
	}

	public int seleccionarCompetidores(List<? extends Competidor> competidores) {
		ArrayList<String> opciones = new ArrayList<String>();
		int opcion = 0;
		String instruccion = bundle.getString("ligaAgregarCompetidor.instruccion");
		String error = bundle.getString("ligaAgregarCompetidor.error");
		boolean continuar = true;
		
		for (int i = 0; i < competidores.size(); i++) {
			mostrarTextoEnConsolaSinSeparacion((i+1) + ". " + competidores.get(i));
			opciones.add((i+1) + ". " + competidores.get(i).toString());
		}
		
		mostrarTextoEnConsola(instruccion);
		
		while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
		
		return opcion;
	}

	public void mostrarResultado(String nombreMenu, int i, String s2) {
		String s1 = bundle.getString(nombreMenu + ".resultado" + i);
		mostrarTextoEnConsola(s1 + s2);
		
	}

	public void mostrarReglas() {
		boolean continuar = true;
		int i = 1;
		
		//bloque 1
		while(continuar) {
			try {
				mostrarTextoEnConsolaSinSeparacion(bundle.getString("mostrarReglasBloque1.l" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
		
		//caracteristicas
		listarCaracteristicas();
		
		continuar = true;
		i = 1;
		//bloque 2
		while(continuar) {
			try {
				mostrarTextoEnConsolaSinSeparacion(bundle.getString("mostrarReglasBloque2.l" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
	}

	public void mostrarError(String nombreMenu) {
		String s = bundle.getString(nombreMenu + ".error");
		mostrarTextoEnConsola(s);
	}
	
	public void throwException(String nombreMenu) throws Exception{
		String s = bundle.getString(nombreMenu + ".exception");
		throw new Exception(s);
	}
	
	public void throwException(String nombreMenu, String s2) throws Exception{
		String s1 = bundle.getString(nombreMenu + ".exception");
		throw new Exception(s1 + s2);
	}
	
	private void animarTexto(String text) {
		if (text.isBlank()) {
			try{
		        Thread.sleep(100);//pausa entre caracteres
		    }catch(InterruptedException ex){
		        Thread.currentThread().interrupt();
		    }
		}
		int i;
		for(i = 0; i < text.length(); i++){
		    System.out.printf("%c", text.charAt(i));
		    try{
		        Thread.sleep(5);//pausa entre caracteres
		    }catch(InterruptedException ex){
		        Thread.currentThread().interrupt();
		    }
		}
		System.out.printf("%n");
	}

}

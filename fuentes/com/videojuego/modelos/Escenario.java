package com.videojuego.modelos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Escenario {
	private Integer ancho = 0;
	private Integer alto = 0;
	private char[][] mapa;
	Path rutaEscenario;


	public Escenario(Path rutaEscenario) throws IOException {
		List<String> lineas = Files.readAllLines(rutaEscenario);
		cargarDimensiones(lineas.get(0));
		mapa = crearMapaDesdeLineas(lineas.subList(1, lineas.size()));
	}

	public void cargarDimensiones(String primeraLinea) {
        String[] dimensiones = primeraLinea.split(" "); // Separar las dimensiones por espacio
        	
        ancho = Integer.parseInt(dimensiones[0]); // Asignar el ancho
        alto = Integer.parseInt(dimensiones[1]); // Asignar el alto
	}

	public char[][] crearMapaDesdeLineas(List<String> lineas) throws IOException {
	    List<char[]> mapaLista = new ArrayList<>();
    	for (String linea : lineas) {
        	String[] elementos = linea.trim().split(" ");
        	List<Character> fila = new ArrayList<>();
    		for (String elemento : elementos) {
        		int cantidad = Integer.parseInt(elemento.substring(0, elemento.length() - 1));
        		char tipo = elemento.charAt(elemento.length() - 1);
        		// Añadir el tipo de carácter la cantidad de veces indicada
        		for (int i = 0; i < cantidad; i++) {
            		fila.add(tipo);
        		}
    		}
    		mapaLista.add(convertirListaAArray(fila)); //Pasa la fila a un char[]
    	}
    	return mapaLista.toArray(new char[0][]); //Convierte la List<char[]> en char[][]
	}

	private char[] convertirListaAArray(List<Character> lista) {
    	// Convertir la lista de caracteres a un array de chars
    	char[] array = new char[lista.size()];
    	for (int i = 0; i < lista.size(); i++) {
        	array[i] = lista.get(i);
    	}
    	return array;
	}
	
	public char[][] getMapa() {
		return mapa;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public char getCelda(int fila, int columna) {
    	return this.mapa[fila][columna];
	}

}
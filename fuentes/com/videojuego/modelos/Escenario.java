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
		cargarDimensiones(rutaEscenario);
		mapa = leerEscenario(rutaEscenario);
	}


	public void cargarDimensiones(Path rutaArchivo) {
		try {
	       	List<String> lineas = Files.readAllLines(rutaArchivo);
 
        	for (String linea : lineas) {
        	}

        	String primeraLinea = lineas.get(0); // Obtener la primera línea
        	String[] dimensiones = primeraLinea.split(" "); // Separar las dimensiones por espacio
        	
        	ancho = Integer.parseInt(dimensiones[0]); // Asignar el ancho
        	alto = Integer.parseInt(dimensiones[1]); // Asignar el alto
    	} catch (IOException e) {
        	e.printStackTrace();
    	}
	}

	private List<String> leerLineasDesdeArchivo(Path rutaArchivo) throws IOException {
    	return Files.readAllLines(rutaArchivo);
	}

	public char[][] leerEscenario(Path rutaArchivo) throws IOException {
    	List<String> lineas = leerLineasDesdeArchivo(rutaArchivo);

    	// Ignorar la primera línea
    	lineas = lineas.subList(1, lineas.size()); 

	    List<char[]> mapaLista = procesarLineasALineaMapa(lineas);

    	return convertirListaAMatriz(mapaLista);
	}

	private List<char[]> procesarLineasALineaMapa(List<String> lineas) {
    	List<char[]> mapaLista = new ArrayList<>();

    	for (String linea : lineas) {
        	String[] elementos = linea.trim().split(" ");
        	List<Character> fila = procesarElementosDeLinea(elementos);
        
        	// Convertir la lista de caracteres a un array de chars
        	char[] filaArray = convertirListaAArray(fila);
        	mapaLista.add(filaArray);
    	}
		return mapaLista;
	}

	private List<Character> procesarElementosDeLinea(String[] elementos) {
    	List<Character> fila = new ArrayList<>();
    	for (String elemento : elementos) {
        	int cantidad = Integer.parseInt(elemento.substring(0, elemento.length() - 1));
        	char tipo = elemento.charAt(elemento.length() - 1);
        
        	// Añadir el tipo de carácter la cantidad de veces indicada
        	for (int i = 0; i < cantidad; i++) {
            	fila.add(tipo);
        	}
    	}
    	return fila;
	}

	private char[] convertirListaAArray(List<Character> lista) {
    	// Convertir la lista de caracteres a un array de chars
    	char[] array = new char[lista.size()];
    	for (int i = 0; i < lista.size(); i++) {
        	array[i] = lista.get(i);
    	}
    	return array;
	}

	private char[][] convertirListaAMatriz(List<char[]> mapaLista) {
    	// Convertir la lista de arrays en una matriz bidimensional
    	char[][] mapa = new char[mapaLista.size()][];
    	for (int i = 0; i < mapaLista.size(); i++) {
        	mapa[i] = mapaLista.get(i);
    	}
    	return mapa;
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
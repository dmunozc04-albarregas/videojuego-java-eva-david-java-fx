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
	private int nivel;


	public Escenario(Path rutaEscenario) throws Exception {
		this.nivel = Integer.parseInt(
    		rutaEscenario.getFileName().toString().replaceAll("[^0-9]", "")
		);
		List<String> lineas = Files.readAllLines(rutaEscenario);
		// Comprobación de que el archivo no esté vacío
    	if (lineas.isEmpty()) {
        	throw new Exception("El archivo de escenario está vacío.");
    	}
		cargarDimensiones(lineas.get(0));
		mapa = crearMapaDesdeLineas(lineas.subList(1, lineas.size()));
	}

	public void cargarDimensiones(String primeraLinea) throws Exception{
        String[] dimensiones = primeraLinea.split(" "); // Separar las dimensiones por espacio
        // Comprobar que haya exactamente dos valores para las dimensiones
    	if (dimensiones.length != 2) {
        	throw new IOException("Formato inválido en las dimensiones. Se esperaba dos números separados por espacio.");
    	}
    	try {
    		ancho = Integer.parseInt(dimensiones[0]); // Asignar el ancho
        	alto = Integer.parseInt(dimensiones[1]); // Asignar el alto
    		// Comprobar que ambos valores sean positivos
        	if (ancho <= 0 || alto <= 0) {
            	throw new IOException("Las dimensiones deben ser números positivos.");
        	}
        	// Asignar los valores a las variables del escenario
        	this.ancho = ancho;
        	this.alto = alto;
    	} catch (NumberFormatException e) {
        	throw new IOException("Las dimensiones deben ser números válidos. Error en: " + primeraLinea, e);
    	}
	}

	public char[][] crearMapaDesdeLineas(List<String> lineas) throws IOException {
	    List<char[]> mapaLista = new ArrayList<>();
    	for (String linea : lineas) {
        	String[] elementos = linea.trim().split(" ");
        	List<Character> fila = new ArrayList<>();
    		for (String elemento : elementos) {
    			// Validar formato: uno o más dígitos seguidos de una letra
            	if (!elemento.matches("\\d+[A-Za-z]")) { // puedes ajustar las letras si solo permites ciertas
                	throw new IOException("Formato inválido en elemento: " + elemento);
            	}
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

	public int getNivel() {
		return nivel;
	}

	public char getCelda(int fila, int columna) {
    	return this.mapa[fila][columna];
	}

}
package com.videojuego.modelos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

/**
 * Representa un escenario del videojuego, cargado desde un archivo de texto.
 * El escenario incluye sus dimensiones y un mapa de caracteres que describe
 * el entorno del juego. 
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class Escenario {
	private Integer ancho = 0;
	private Integer alto = 0;
	private char[][] mapa;
	Path rutaEscenario;
	/**
	 * Constructor vacío por defecto.
	 */
	public Escenario(){

	}
	/**
	 * Constructor que carga un escenario desde un archivo de texto.
	 * @param rutaEscenario Ruta del archivo del escenario.
	 * @throws Exception si el archivo está vacío o tiene un formato incorrecto.
	 */
	public Escenario(Path rutaEscenario) throws Exception {
		List<String> lineas = Files.readAllLines(rutaEscenario);
		// Comprobación de que el archivo no esté vacío
    	if (lineas.isEmpty()) {
        	throw new Exception("El archivo de escenario está vacío.");
    	}
    	this.rutaEscenario = rutaEscenario;
		cargarDimensiones(lineas.get(0));
		mapa = crearMapaDesdeLineas(lineas.subList(1, lineas.size()));
	}
	/**
	 * Establece la ruta del archivo del escenario.
	 * @param ruta Ruta al archivo de escenario.
	 */
	public void setRutaEscenario(Path ruta) {
    	this.rutaEscenario = ruta;
	}
	/**
	 * Carga las dimensiones del escenario a partir de una línea de texto.
	 * @param primeraLinea Línea con formato "ancho alto".
	 * @throws IOException si el formato es inválido o los valores no son positivos.
	 */
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
        	this.ancho = ancho;
        	this.alto = alto;
    	} catch (NumberFormatException e) {
        	throw new IOException("Las dimensiones deben ser números válidos. Error en: " + primeraLinea, e);
    	}
	}
	/**
	 * Crea el mapa del escenario a partir de las líneas del archivo.
	 * @param lineas Lista de líneas (sin incluir la de dimensiones).
	 * @return Mapa bidimensional de caracteres representando el escenario.
	 * @throws IOException si el formato de algún elemento es inválido.
	 */
	public char[][] crearMapaDesdeLineas(List<String> lineas) throws IOException {
	    List<char[]> mapaLista = new ArrayList<>();
    	for (String linea : lineas) {
        	String[] elementos = linea.trim().split(" ");
        	List<Character> fila = new ArrayList<>(); //Versión en objeto de char.
    		for (String elemento : elementos) {
    			// Validar formato: uno o más dígitos seguidos de una letra
            	if (!elemento.matches("\\d+[A-Za-z]")) { 
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
	/**
	 * Convierte una lista de caracteres en un array de caracteres.
	 * @param lista Lista de caracteres.
	 * @return Array de caracteres.
	 */
	private char[] convertirListaAArray(List<Character> lista) {
    	// Convertir la lista de caracteres a un array de chars
    	char[] array = new char[lista.size()];
    	for (int i = 0; i < lista.size(); i++) {
        	array[i] = lista.get(i);
    	}
    	return array;
	}
	/**
	 * Devuelve el mapa del escenario.
	 * @return Mapa bidimensional de caracteres.
	 */
	public char[][] getMapa() {
		return mapa;
	}
	/**
	 * Devuelve el ancho del escenario.
	 * @return Ancho en número de columnas.
	 */
	public int getAncho() {
		return ancho;
	}
	/**
	 * Devuelve el alto del escenario.
	 * @return Alto en número de filas.
	 */
	public int getAlto() {
		return alto;
	}
	/**
	 * Devuelve el carácter de una celda específica del mapa.
	 * @param fila    Fila de la celda.
	 * @param columna Columna de la celda.
	 * @return Carácter en la posición indicada.
	 */
	public char getCelda(int fila, int columna) {
    	return this.mapa[fila][columna];
	}

}
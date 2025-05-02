package com.videojuego.modelos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Clase jugador con la cual se istancias los juagdores y sus diferentes métodos.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class Jugador implements Serializable{
	private String nombreUsuario;
	private String email;
	private int fila; // Agregado: posición fila del jugador
    private int columna; // Agregado: posición columna del jugador

	/**
	 * Constructor sin parametrizar de la clase Jugador.
	 */
	public Jugador(){
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.fila = fila;
        this.columna = columna;
	}

	/**
	 * Constructor parametrizado de la clase Jugador.
	 * @param nombreUsuario Nombre de usuario del jugador.
	 * @param email Correo electrónico del jugador.
	 * @param fila Posición fila del jugador.
     * @param columna Posición columna del jugador.
	 */
	public Jugador(String nombreUsuario, String email){
		this.nombreUsuario = nombreUsuario;
		this.email = email;
	}

	/**
	 * Método para comprobar si existe el usuario.
	 * @param nombreUsuario Nombre del jugador a comprobar.
	 */
	public boolean comprobarExistenciaJugador(String nombreUsuario){
       Path pathJugadores = Paths.get("fuentes/com/videojuego/jugador");
       try (DirectoryStream<Path> flujoDatos = Files.newDirectoryStream(pathJugadores)) {

       		for(Path fichero : flujoDatos){
				String nombreFichero = fichero.getFileName().toString();

       			if(nombreFichero.equals(nombreUsuario + ".bin")){
       				return true;
       			}
       		}
       }
       catch(Exception e){
       	e.printStackTrace();
       }
       return false;
    }

    /**
	 * Método para crear el jugador en caso de que no exista.
	 * @param nombreUsuario Nombre de usuario del jugador.
	 * @param email Correo electrónico del jugador.
	 */
	public void crearJugador(String nombreUsuario, String email){
		List<Jugador> contenidoFicheroJugador = new ArrayList<>();
		contenidoFicheroJugador.add(new Jugador(nombreUsuario, email));
        Path rutaJugadoresRegistrados = Paths.get("fuentes/com/videojuego/jugador/" + nombreUsuario + ".bin");

        try (OutputStream ficheroJugador = Files.newOutputStream(rutaJugadoresRegistrados)) {

        	ObjectOutputStream flujoSalida = new ObjectOutputStream(ficheroJugador);

        	flujoSalida.writeObject(contenidoFicheroJugador);

        	System.out.println("Jugador registrado correctamente.");

        } catch(Exception e){
        	e.printStackTrace();
        }
	}

	/**
	 * Setter para el nombre de usuario del jugador.
	 */
	public void setNombreUsuario(String nombreUsuario){
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Setter para el correo electrónico del jugador.
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
     * Setter para la posición fila del jugador.
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

	/**
     * Setter para la posición columna del jugador.
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

	/**
	 * Método para obtener el nombre de usuario.
	 */
	public String getNombreUsuario(){
		return nombreUsuario;
	}

	/**
	 * Método para obtener el correo electrónico del jugador.
	 */
	public String getEmail(){
		return email;
	}

	/**
     * Getter para la posición fila del jugador.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Getter para la posición columna del jugador.
     */
    public int getColumna() {
        return columna;
    }
}
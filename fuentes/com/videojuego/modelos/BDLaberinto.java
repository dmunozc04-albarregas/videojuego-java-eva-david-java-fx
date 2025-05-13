package com.videojuego.modelos;

import com.videojuego.controladores.ControladorEscenario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que gestiona la base de datos de puntuaciones del videojuego. 
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class BDLaberinto {
	private static final String URL = "jdbc:sqlite:puntuaciones.db";
	private static Escenario escenario = new Escenario();
	private static Jugador jugador = new Jugador();
	private static int puntuacion = 0;
	
	/**
     * Crea la tabla 'puntuaciones' si no existe y la rellena con valores por defecto si está vacía.
     */
	public static void crearTabla(){
		try(Connection conexion = DriverManager.getConnection(URL)) {
			String sql = "CREATE TABLE IF NOT EXISTS puntuaciones ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombreUsuario TEXT NOT NULL,"
				+ "puntos INTEGER NOT NULL"
				+ ");";

			Statement sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);
			//Insertar 10 filas por defecto solo si la tabla está vacía
        	String comprobarVacio = "SELECT COUNT(*) FROM puntuaciones;";
        	ResultSet rs = sentencia.executeQuery(comprobarVacio);
        	rs.next();
        	int cantidad = rs.getInt(1);

	        if (cantidad == 0) {
	        	 // Insertar 10 jugadores por defecto
            	for (int i = 0; i < 10; i++) {
    	        	String insertPorDefecto = "INSERT INTO puntuaciones (nombreUsuario, puntos) VALUES ('-', 0);";
        	    	sentencia.executeUpdate(insertPorDefecto);
            	}
        	}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
     * Calcula la puntuación final basada en el nivel, número de golpes y tiempo.
     * Luego intenta insertarla en el top 10.
     * @param nombreUsuario Nombre del jugador.
     * @param controladorEscenario Controlador del escenario actual.
     */
	public static void calcularPuntuacion(String nombreUsuario, ControladorEscenario controladorEscenario){
		int nivel = controladorEscenario.getNivel();
		Integer contadorGolpes = controladorEscenario.getNumeroDeGolpes();
		Integer tiempo = controladorEscenario.getTiempo();
		Integer puntuacionMaxima = 1500;
		int puntuacionFinal = 0;

		switch (nivel) {
        	case 1:
            	puntuacionFinal = (int) Math.floor(puntuacionMaxima - (contadorGolpes * 5 + tiempo * 1.2));
            	break;
        	case 2:
            	puntuacionFinal = (int) Math.floor(puntuacionMaxima - (contadorGolpes * 4 + tiempo));
            	break;
        	case 3:
            	puntuacionFinal = (int) Math.floor(puntuacionMaxima - (contadorGolpes * 3 + tiempo * 0.7));
            	break;
        	case 4:
            	puntuacionFinal = (int) Math.floor(puntuacionMaxima - (contadorGolpes * 2 + tiempo * 0.5));
            	break;
        	default:
            	puntuacionFinal = 0;
    	}
    	setPuntuacion(puntuacionFinal);
    	insertarPuntuacion(nombreUsuario, puntuacionFinal);
	}
	/**
     * Inserta una puntuación en la base de datos solo si entra en el top 10.
     * @param nombreUsuario Nombre del jugador.
     * @param puntos Puntuación obtenida.
     */
	public static void insertarPuntuacion(String nombreUsuario, Integer puntos) {
	    String sqlInsertar = "INSERT INTO puntuaciones(nombreUsuario, puntos) VALUES (?, ?)";
	    String sqlTop10 = "SELECT puntos FROM puntuaciones ORDER BY puntos DESC LIMIT 10";

	    try (Connection conexion = DriverManager.getConnection(URL)) {
	        Statement stmt = conexion.createStatement();
	        ResultSet rs = stmt.executeQuery(sqlTop10);

	        boolean insertar = false;

	        // Si hay menos de 10 puntuaciones, siempre se inserta
	        List<Integer> topPuntos = new ArrayList<>();
	        while (rs.next()) {
	            topPuntos.add(rs.getInt("puntos"));
	        }

	        if (topPuntos.size() < 10) {
	            insertar = true;
	        } else {
	            // Si la puntuación es mejor que la más baja del top 10, también se inserta
	            int menorTop = topPuntos.get(topPuntos.size() - 1);
	            if (puntos > menorTop) {
	                insertar = true;
	            }
	        }

	        if (insertar) {
	            PreparedStatement ps = conexion.prepareStatement(sqlInsertar);
	            ps.setString(1, nombreUsuario);
	            ps.setInt(2, puntos);
	            ps.executeUpdate();
	        } else {
	            System.out.println("La puntuación no entra en el top 10.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error insertando: " + e.getMessage());
	    }
	}
 	/**
     * Obtiene una lista con los 10 jugadores con mayor puntuación.
     * @return Lista de los 10 mejores jugadores.
     */
	public static List<Jugador> obtenerTop10() {
		List<Jugador> top10 = new ArrayList<>();
		String sql = "SELECT nombreUsuario, puntos FROM puntuaciones ORDER BY puntos DESC LIMIT 10";

        try (Connection conexion = DriverManager.getConnection(URL);
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            while (resultado.next()) {
				String nombreUsuario = resultado.getString("nombreUsuario");
            	int puntos = resultado.getInt("puntos");
            	top10.add(new Jugador(nombreUsuario, puntos));
            }
        } catch (SQLException e) {
            System.out.println("Error mostrando top: " + e.getMessage());
        } 
        return top10;
	}
	/**
     * Devuelve la última puntuación calculada.
     * @return Puntuación final.
     */
	public static int getPuntuacion() {
        return puntuacion;
    }

    public static void setPuntuacion(int nuevaPuntuacion) {
        puntuacion = nuevaPuntuacion;
    }
}
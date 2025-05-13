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

public class BDLaberinto {
	private static final String URL = "jdbc:sqlite:puntuaciones.db";
	private static Escenario escenario = new Escenario();
	private static Jugador jugador = new Jugador();
	//private static ControladorEscenario controladorEscenario = new ControladorEscenario ();

	public static void crearTabla(){
		try(Connection conexion = DriverManager.getConnection(URL)) {
			System.out.println("Conexión realizada");
			String sql = "CREATE TABLE IF NOT EXISTS puntuaciones ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombreUsuario TEXT NOT NULL,"
				+ "puntos INTEGER NOT NULL"
				+ ");";

			Statement sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);
			System.out.println("Tabla creada.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void calcularPuntuacion(String nombreUsuario, ControladorEscenario controladorEscenario){
		//char nivel = escenario.getNivel();
		Integer contadorGolpes = controladorEscenario.getNumeroDeGolpes();
		Integer tiempo = controladorEscenario.getTiempo();
		Integer puntuacionMaxima = 1500;
		Integer puntuacionFinal = 0;

		/*switch(nivel){
			case '1':
				puntuacionFinal = (int) Math.floor((puntuacionMaxima - ((contadorGolpes * 2) + (tiempo * 0.5))));
				System.out.println(puntuacionFinal);
				break;
		}*/

		puntuacionFinal = (int) Math.floor((puntuacionMaxima - ((contadorGolpes * 2) + (tiempo * 0.5))));

		System.out.println(controladorEscenario.getNumeroDeGolpes());
		System.out.println(controladorEscenario.getTiempo());
		insertarPuntuacion(nombreUsuario, puntuacionFinal);
	}

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
	            System.out.println("Puntuación insertada correctamente.");
	        } else {
	            System.out.println("La puntuación no entra en el top 10.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error insertando: " + e.getMessage());
	    }
	}


	public static List<Jugador> obtenerTop10() {
		List<Jugador> top10 = new ArrayList<>();
		String sql = "SELECT nombreUsuario, puntos FROM puntuaciones ORDER BY puntos DESC LIMIT 10";

        try (Connection conexion = DriverManager.getConnection(URL);
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("TOP 10:");
            while (resultado.next()) {
				String nombreUsuario = resultado.getString("nombreUsuario");
            	int puntos = resultado.getInt("puntos");
            	top10.add(new Jugador(nombreUsuario, puntos));            }

        } catch (SQLException e) {
            System.out.println("Error mostrando top: " + e.getMessage());
        } 
        return top10;
	}
}
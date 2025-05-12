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
	private static ControladorEscenario controladorEscenario = new ControladorEscenario ();

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

	public static void calcularPuntuacion(String nombreUsuario){
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

		System.out.println(nombreUsuario);
		//insertarPuntuacion(jugador.getNombreUsuario(), puntuacionFinal);
	}

	public static void insertarPuntuacion(String nombreUsuario, Integer puntos) {
		Integer contador = 0;
		boolean actualizado = false;
		String sql = "INSERT INTO puntuaciones(nombreUsuario, puntos) VALUES (?, ?)";
		String sqlTop10 = "SELECT puntos FROM puntuaciones ORDER BY puntos DESC LIMIT 10";
		try (Connection conexion = DriverManager.getConnection(URL)){
        	Statement stmt = conexion.createStatement();
	        ResultSet rs = stmt.executeQuery(sqlTop10);

			do{
			   if (rs.next()) {
		            int puntosExistente = rs.getInt("puntos");
		            
		            if (puntos > puntosExistente) {
		                PreparedStatement ps = conexion.prepareStatement(sql);
		                ps.setString(1, nombreUsuario);
		                ps.setInt(2, puntos);
		                ps.executeUpdate();
		                actualizado = true;
		            }

		            else {
	                    PreparedStatement ps = conexion.prepareStatement(sql);
	                    ps.setString(1, nombreUsuario);
	                    ps.setInt(2, puntos);
	                    ps.executeUpdate();
	                    actualizado = true;
                	}

                	contador++;
				}
			}
			while(contador < 11 || actualizado == true);
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
/*package com.videojuego.modelos;

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

	public static void insertarPuntuacion(String nombreUsuario, int puntos) {
		String sql = "INSERT INTO puntuaciones(nombreUsuario, puntos) VALUES (?, ?)";
		try (Connection conexion = DriverManager.getConnection(URL);
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(sql)) {
            sentenciaPreparada.setString(1, nombreUsuario);
            sentenciaPreparada.setInt(2, puntos);
            sentenciaPreparada.executeUpdate();
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
}*/
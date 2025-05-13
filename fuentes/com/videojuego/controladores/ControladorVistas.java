package com.videojuego.controladores;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * Clase ControladorVistas que gestiona la inicialización y visualización de las vistas del videojuego.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorVistas extends Controlador {

	private Stage ventana;
	private Scene vista1;
	/**
     * Constructor vacío para la clase ControladorVistas.
     * Este constructor no inicializa nada, se usa en algunos contextos donde la ventana no está definida.
     */
	public ControladorVistas(){}
	/**
     * Constructor que inicializa la ventana, la vista de la pantalla de inicio, y configura
     * los elementos visuales y de sonido del videojuego.
     * @param ventana El objeto Stage que representa la ventana principal del juego.
     */
	public ControladorVistas(Stage ventana){
		this.ventana = ventana;
		ventana.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
	    Font.loadFont(getClass().getClassLoader().getResourceAsStream("PressStart2P.ttf"), 10);
	    Controlador.reproducirMusica();
		vista1 = Controlador.cargarVista("/com/videojuego/vistas/pantalla_splash.fxml");
		vista1.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());
		ventana.setScene(vista1);
		ventana.centerOnScreen();
		ventana.setTitle("Inicio videojuego");
		ventana.show();
	}
}

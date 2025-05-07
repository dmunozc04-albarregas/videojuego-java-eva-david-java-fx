package com.videojuego.controladores;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorVistas extends Controlador {

	private Stage ventana;
	private Scene vista1;

	public ControladorVistas(Stage ventana){
		this.ventana = ventana;
		ventana.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
	    Font.loadFont(getClass().getClassLoader().getResourceAsStream("PressStart2P.ttf"), 10);

		vista1 = Controlador.cargarVista("/com/videojuego/vistas/pantalla_splash.fxml");
		vista1.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());
		ventana.setScene(vista1);
		ventana.centerOnScreen();
		ventana.setTitle("Inicio videojuego");
		ventana.show();
	}
}

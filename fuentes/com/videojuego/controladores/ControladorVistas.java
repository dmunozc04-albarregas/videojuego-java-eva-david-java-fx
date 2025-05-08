package com.videojuego.controladores;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import java.io.IOException;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ControladorVistas extends Controlador {

	private Stage ventana;
	private Scene vista1;

	public ControladorVistas(Stage ventana){
		this.ventana = ventana;
		ventana.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
	    Font.loadFont(getClass().getClassLoader().getResourceAsStream("PressStart2P.ttf"), 10);

	    String pathMusica = "recursos/8_bit.mp3";

	    Media media = new Media(new File(pathMusica).toURI().toString());

	    //Media Player
	    MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir en bucle
        mediaPlayer.play(); // Iniciar reproducción


		vista1 = Controlador.cargarVista("/com/videojuego/vistas/pantalla_splash.fxml");
		vista1.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());
		ventana.setScene(vista1);
		ventana.centerOnScreen();
		ventana.setTitle("Inicio videojuego");
		ventana.show();
	}
}

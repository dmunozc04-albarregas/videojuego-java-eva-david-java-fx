package com.videojuego.controladores;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML; 
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;    


public abstract class Controlador {

    private static MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        try {
            // Cargar la fuente personalizada en el controlador
            Font.loadFont(getClass().getResource("/PressStart2P.ttf").toExternalForm(), 10);
        } catch (Exception e) {
            // Si ocurre un error al cargar la fuente, se captura y muestra el mensaje de error
            e.printStackTrace();
        }
    }

    public static void reproducirMusica(){
        String pathMusica = "recursos/pacman.mp3";

        Media media = new Media(new File(pathMusica).toURI().toString());

        //Media Player
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir en bucle
        mediaPlayer.play(); // Iniciar reproducción
    }

    public static Scene cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(Controlador.class.getResource(rutaFXML));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR FATAL. No se encuentra la vista");
            System.exit(-1);
            return null;
        }
    }

    public static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro de jugador"); 
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void pararMusica(){
        mediaPlayer.stop();
    }
}
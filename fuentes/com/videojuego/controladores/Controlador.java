package com.videojuego.controladores;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML; 
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;    

/**
 * Clase abstracta que proporciona funcionalidades comunes para todos los controladores del juego.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public abstract class Controlador {

    private static MediaPlayer mediaPlayer;
    /**
     * Inicializa el controlador cargando la fuente personalizada.
     */
    @FXML
    public void initialize() {
        try {
            // Cargar la fuente personalizada en el controlador
            Font.loadFont(getClass().getResource("/PressStart2P.ttf").toExternalForm(), 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Reproduce música de fondo en bucle.
     */
    public static void reproducirMusica(){
        String pathMusica = "recursos/pacman.mp3";

        Media media = new Media(new File(pathMusica).toURI().toString());

        //Media Player
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir en bucle
        mediaPlayer.play(); // Iniciar reproducción
    }
    /**
     * Carga una vista FXML y devuelve la escena asociada.
     * @param rutaFXML Ruta relativa al archivo FXML.
     * @return Escena cargada desde el archivo.
     */
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
    /**
     * Muestra una alerta informativa con un mensaje dado.
     * @param mensaje Texto que se mostrará en la alerta.
     */
    public static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro de jugador"); 
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    /**
     * Detiene la música actualmente en reproducción.
     */
    public static void pararMusica(){
        mediaPlayer.stop();
    }
}
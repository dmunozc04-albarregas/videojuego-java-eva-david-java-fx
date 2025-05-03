package com.videojuego.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;

import java.io.IOException;

public class ControladorSplash {

    @FXML
    private Button btnComenzar;

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
    
    @FXML
    private void ventanaAccesoUsuario() {
        try {
            //Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 10);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/solicitar_usuario.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) btnComenzar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Acceso Jugador");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

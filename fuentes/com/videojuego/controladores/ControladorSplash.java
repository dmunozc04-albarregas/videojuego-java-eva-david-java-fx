package com.videojuego.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

public class ControladorSplash extends Controlador {
    @FXML
    private Button btnComenzar;

    @FXML
    private void ventanaAccesoUsuario() {
        Scene scene = Controlador.cargarVista("/com/videojuego/vistas/solicitar_usuario.fxml");
        Stage stage = (Stage) btnComenzar.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Solicitud jugador");
    }
}

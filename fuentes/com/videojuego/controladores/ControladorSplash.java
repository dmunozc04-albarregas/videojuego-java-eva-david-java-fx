package com.videojuego.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;

/**
 * Clase ControladorSplash que gestiona la pantalla de inicio (Splash) del videojuego.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorSplash extends Controlador {
    @FXML
    private Button btnComenzar;
    /**
     * Cambia la escena actual a la vista de solicitud del jugador.
     */
    @FXML
    private void ventanaAccesoUsuario() {
        Scene scene = Controlador.cargarVista("/com/videojuego/vistas/solicitar_usuario.fxml");
        Stage stage = (Stage) btnComenzar.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Solicitud jugador");
    }
}

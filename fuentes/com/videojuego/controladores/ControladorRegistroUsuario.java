package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
//import com.videojuego.modelos.Escenario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControladorRegistroUsuario{

    private Jugador jugador = new Jugador();

    @FXML
    private Button btnRegistro;

    @FXML
    private TextField nombreUsuarioRegistro;

    @FXML
    private TextField correoUsuarioRegistro;

    // Método que recibe el nombre de usuario desde la ventana anterior
    public void setNombreUsuario(String nombre) {
        nombreUsuarioRegistro.setText(nombre); 
    }

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
    private void validarRegistro() {
        String nombreUsuario = nombreUsuarioRegistro.getText().trim();
        String correoUsuario = correoUsuarioRegistro.getText().trim();

        if (nombreUsuario.isEmpty() || correoUsuario.isEmpty()) {
            mostrarAlerta("Por favor, introduza un nombre de usuario / email.");
            return;
        } 
        else if(!correoUsuario.contains("@")){
            mostrarAlerta("Correo no válido, asegúrate que contiene un @");
            return;
        }
        else {
            jugador.crearJugador(nombreUsuario, correoUsuario);
            mostrarAlerta("Jugador creado correctamente, entrando al juego...");
            ventanaControladorMenu();
        }
    }

    private void ventanaControladorMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/vistaMenu.fxml"));
            Parent raizMenu = loader.load();

            Scene escenaMenu = new Scene(raizMenu);
            
            Stage menu = new Stage();
            menu.setScene(escenaMenu);
            menu.setTitle("Menú juego");

            menu.initModality(Modality.APPLICATION_MODAL);

            Stage ventanaActual = (Stage) btnRegistro.getScene().getWindow();
            ventanaActual.close();
            menu.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("ERROR al cargar la ventana del menú.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro de jugador"); 
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

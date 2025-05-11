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

public class ControladorRegistroUsuario extends Controlador {

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
    private void validarRegistro() {
        String nombreUsuario = nombreUsuarioRegistro.getText().trim();
        String correoUsuario = correoUsuarioRegistro.getText().trim();

        if (nombreUsuario.isEmpty() || correoUsuario.isEmpty()) {
            Controlador.mostrarAlerta("Por favor, introduza un nombre de usuario / email.");
            return;
        } 
        else if(!correoUsuario.contains("@")){
            Controlador.mostrarAlerta("Correo no válido, asegúrate que contiene un @");
            return;
        }
        else {
            jugador.crearJugador(nombreUsuario, correoUsuario);
            Controlador.mostrarAlerta("Jugador creado correctamente, entrando al juego...");
            ventanaControladorMenu();
        }
    }

    private void ventanaControladorMenu() {
        Scene escenaMenu = Controlador.cargarVista("/com/videojuego/vistas/vistaMenu.fxml");    
        Stage menu = (Stage) btnRegistro.getScene().getWindow();
        menu.setScene(escenaMenu);
        menu.setTitle("Elije el nivel");
        menu.centerOnScreen();
    }
}

package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;

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

/**
 * Clase ControladorRegistroUsuario que gestiona el proceso de registro de un usuario en el juego.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorRegistroUsuario extends Controlador {

    private Jugador jugador = new Jugador();

    @FXML
    private Button btnRegistro;

    @FXML
    private TextField nombreUsuarioRegistro;

    @FXML
    private TextField correoUsuarioRegistro;

    /**
     * Establece el nombre de usuario en el campo de texto del formulario de registro.
     * @param nombre El nombre de usuario a establecer.
     */    
    public void setNombreUsuario(String nombre) {
        nombreUsuarioRegistro.setText(nombre); 
    }
    /**
     * Valida la información del registro del jugador.
     */
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
    /**
     * Carga la vista del menú principal después de un registro exitoso.
     * Cambia la escena actual al menú principal, permitiendo que el jugador elija el nivel.
     */
    private void ventanaControladorMenu() {
        Scene escenaMenu = Controlador.cargarVista("/com/videojuego/vistas/vistaMenu.fxml");    
        Stage menu = (Stage) btnRegistro.getScene().getWindow();
        menu.setScene(escenaMenu);
        menu.setTitle("Elije el nivel");
        menu.centerOnScreen();
    }

   
}

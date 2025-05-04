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

public class ControladorAccesoUsuario {

    @FXML
    private TextField nombreUsuarioAComprobar;

    @FXML
    private Button btnValidarUsuario;

    private Jugador jugador = new Jugador();

    public void setNombreUsuario(String nombre) {
        nombreUsuarioAComprobar.setText(nombre);
    }

    @FXML
    private void comprobarNombreUsuario() {
        String nombreUsuario = nombreUsuarioAComprobar.getText().trim();

        if (nombreUsuario.isEmpty()) {
            mostrarAlerta("Por favor, introduzca un nombre de usuario.");
            return;
        }

        else if (jugador.comprobarExistenciaJugador(nombreUsuario)) {
            mostrarAlerta("¡Entrando al juego...");
            ventanaControladorMenu();
        } else {
            //mostrarAlerta("El usuario no existe.");
            ventanaRegistroUsuario();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Verificación de jugador");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
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

    private void ventanaRegistroUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/registrar_usuario.fxml"));
            Parent root = loader.load();

            ControladorRegistroUsuario controlador = loader.getController();
            controlador.setNombreUsuario(nombreUsuarioAComprobar.getText().trim());

            Scene scene = new Scene(root);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setTitle("Registrar Jugador");

            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(nombreUsuarioAComprobar.getScene().getWindow());

            Stage stageActual = (Stage) nombreUsuarioAComprobar.getScene().getWindow();
            stageActual.close();
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
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

            menu.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("ERROR al cargar la ventana del menú.");
        }
    }

}

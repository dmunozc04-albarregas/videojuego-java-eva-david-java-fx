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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controlador encargado de gestionar el acceso del usuario al juego.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorAccesoUsuario {

    @FXML
    private TextField nombreUsuarioAComprobar;

    @FXML
    private Button btnValidarUsuario;

    private Jugador jugador = new Jugador();
    private static String nombreUsuario = null;
    /**
     * Comprueba si el nombre de usuario introducido existe.  
     * Si existe, accede al menú del juego; si no, abre la ventana de registro.
     */
    @FXML
    private void comprobarNombreUsuario() {
        nombreUsuario = nombreUsuarioAComprobar.getText().trim();

        if (nombreUsuario.isEmpty()) {
            Controlador.mostrarAlerta("Por favor, introduzca un nombre de usuario.");
            return;
        }

        else if (jugador.comprobarExistenciaJugador(nombreUsuario)) {
            Controlador.mostrarAlerta("¡Entrando al juego...");
            ventanaControladorMenu();
        } else {
            ventanaRegistroUsuario();
        }
    }
    /**
     * Abre la ventana de registro de usuario en una ventana modal.
     */
    private void ventanaRegistroUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/registrar_usuario.fxml"));
            Parent root = loader.load();

            ControladorRegistroUsuario controlador = loader.getController();
            controlador.setNombreUsuario(nombreUsuarioAComprobar.getText().trim());

            Scene scene = new Scene(root);
            Stage modalStage = new Stage();
            modalStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
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
    /**
     * Abre la ventana del menú principal del juego.
     */
    public void ventanaControladorMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/vistaMenu.fxml"));
            Parent raizMenu = loader.load();

            Scene escenaMenu = new Scene(raizMenu);
            
            Stage menu = new Stage();
            menu.setScene(escenaMenu);
            menu.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            menu.setTitle("Menú juego");

            menu.initModality(Modality.APPLICATION_MODAL);

            Stage ventanaActual = (Stage) nombreUsuarioAComprobar.getScene().getWindow();
            ventanaActual.close();
            menu.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Controlador.mostrarAlerta("ERROR al cargar la ventana del menú.");
        }
    }
    /**
     * Devuelve el nombre del usuario introducido.
     * @return nombre del usuario
     */
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    /**
     * Establece el nombre del usuario en el campo de texto.
     * @param nombre nombre a establecer
     */
    public void setNombreUsuario(String nombre) {
        nombreUsuarioAComprobar.setText(nombre);
    }

}

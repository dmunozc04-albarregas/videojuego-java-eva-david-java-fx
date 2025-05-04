package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.controladores.ControladorEscenario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.scene.text.Text;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.Modality;

public class ControladorMenu {
    private Jugador jugador = new Jugador();
    private ControladorEscenario controladorEscenario;
    private int nivel;
    private Stage stage;
    private Scene escenaMenu;

    @FXML
    private Text nivelTexto;

    @FXML
    private Button btnNivel1;

    @FXML
    private Button btnNivel2;

    @FXML
    private Button btnNivel3;

    @FXML
    private Button btnNivel4;

    public ControladorMenu() {
    }

    public ControladorMenu(Stage stage) {
        this.stage = stage;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
    private void seleccionarNivel(ActionEvent event) {
        Button boton = (Button) event.getSource();        
        Stage stage = (Stage) boton.getScene().getWindow();

        if(boton == btnNivel1) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario1.txt"), this);
        }
        if(boton == btnNivel2) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario2.txt"), this);
        }
        if(boton == btnNivel3) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario3.txt"), this);
        }
        if(boton == btnNivel4) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario4.txt"), this);
        }
    }

    public void mostrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/vistaMenu.fxml"));
            Parent raizMenu = loader.load();

            Scene escenaMenu = new Scene(raizMenu);
            
            Stage menu = new Stage();
            menu.setScene(escenaMenu);
            menu.setTitle("Menú juego");

            menu.initModality(Modality.APPLICATION_MODAL);

            menu.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo mostrar el menú.");
        }
    }



    /*private void iniciarJuego(int nivel, Stage stage) {
        try {
            // Cargar la vista del juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/nivel1.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del juego
            controladorJuego = loader.getController();
            controladorJuego.setJugador(jugador);
            setNivel(nivel);

            controladorJuego.actualizarTextoNivel(nivel);

            // Crear nueva ventana
            stage.setScene(new Scene(root));
            stage.setTitle("Juego - Nivel " + nivel);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la vista del juego: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
}
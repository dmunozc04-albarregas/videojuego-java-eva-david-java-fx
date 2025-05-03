package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.text.Text;

public class ControladorMenu {
    private Jugador jugador = new Jugador();
    //private ControladorEscenario controladorEscenario;
    int nivel;

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

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    @FXML
    private void seleccionarNivel(ActionEvent event) {
        Button boton = (Button) event.getSource();
        int nivel = Integer.parseInt(boton.getText().replace("Nivel ", ""));
        //int nivel;

        Stage stage = (Stage) boton.getScene().getWindow();

        /*if(boton == btnNivel1) {
            iniciarJuego(1, stage);
        }

        if(boton == btnNivel2) {
            iniciarJuego(2, stage);
        }
        if(boton == btnNivel3) {
            iniciarJuego(3, stage);
        }
        if(boton == btnNivel4) {
            iniciarJuego(4, stage);
        }*/
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
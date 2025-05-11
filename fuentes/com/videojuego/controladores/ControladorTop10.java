package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.modelos.BDLaberinto;

import java.util.List;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ControladorTop10 {

    @FXML
    private VBox contenedorTop10;

    private BDLaberinto bdLaberinto;
    private Font fuente;

    public ControladorTop10() {
        bdLaberinto = new BDLaberinto();
    }

    public void initialize() {
        fuente = Font.loadFont(getClass().getResource("/PressStart2P.ttf").toExternalForm(), 12);
        List<Jugador> listaTop10 = BDLaberinto.obtenerTop10();
        contenedorTop10.setSpacing(10); 

        int totalMostrado = 0;
        int espacioColumna = 60;

        for (Jugador j : listaTop10) {
            String textoNombre = j.getNombreUsuario();
            int valorPuntos = j.getPuntos();

            if (textoNombre == null || textoNombre.trim().isEmpty()) {
                textoNombre = "-";
                valorPuntos = 0;
            }

            agregarFila(textoNombre, valorPuntos, espacioColumna);
            totalMostrado++;
        }

        while (totalMostrado < 10) {
            agregarFila("-", 0, 100);
            totalMostrado++;
        }
    }

    private void agregarFila(String nombreStr, int puntosValor, int espacioColumna) {
        Label nombre = new Label(nombreStr);
        Label puntos = new Label(String.valueOf(puntosValor));

        
        nombre.setFont(fuente);
        puntos.setFont(fuente);

        nombre.setStyle("-fx-text-fill: white");
        puntos.setStyle("-fx-text-fill: white");

        nombre.setAlignment(Pos.CENTER_LEFT);
        puntos.setAlignment(Pos.CENTER_RIGHT);

        HBox fila = new HBox(espacioColumna); // espacio entre columnas
        fila.setAlignment(Pos.CENTER);
        HBox.setHgrow(nombre, Priority.ALWAYS);
    HBox.setHgrow(puntos, Priority.ALWAYS);
        fila.getChildren().addAll(nombre, puntos);

        contenedorTop10.getChildren().add(fila);
    }

}
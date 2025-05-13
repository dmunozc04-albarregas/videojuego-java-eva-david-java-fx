package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.modelos.BDLaberinto;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * Clase ControladorTop10 que gestiona la visualización del ranking de los 10 mejores jugadores.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorTop10 {

    @FXML
    private VBox contenedorTop10;
    private Font fuente;
    /**
     * Inicializa la vista del top 10 de jugadores, cargando los jugadores desde la base de datos 
     * y mostrando sus nombres y puntos.
     */
    public void initialize() {
        fuente = Font.loadFont(getClass().getResourceAsStream("/PressStart2P.ttf"), 12);

        List<Jugador> listaTop10 = BDLaberinto.obtenerTop10();
        contenedorTop10.setSpacing(10); 

        for (Jugador j : listaTop10) {
            String textoNombre = j.getNombreUsuario();
            int valorPuntos = j.getPuntos();

            agregarFila(textoNombre, valorPuntos);
        }
    }
    /**
     * Agrega una fila con los datos de un jugador (nombre y puntos) a la vista del Top 10.
     * @param nombreStr El nombre del jugador.
     * @param puntosValor Los puntos del jugador.
     * @param espacioColumna El espacio entre las columnas de nombre y puntos.
     */
    private void agregarFila(String nombreStr, int puntosValor) {
        Label nombre = new Label(nombreStr);
        Label puntos = new Label(String.valueOf(puntosValor));

        nombre.setFont(fuente);
        puntos.setFont(fuente);

        // Ancho fijo para alineación perfecta
        nombre.setMinWidth(130);
        nombre.setMaxWidth(130);
        puntos.setMinWidth(60);
        puntos.setMaxWidth(60);

        // Alineación dentro del Label
        nombre.setStyle("-fx-text-fill: white;");
        puntos.setStyle("-fx-text-fill: white;");

       // Crea un contenedor para cada columna y alínea su contenido
        HBox contenedorNombre = new HBox(nombre);
        HBox contenedorPuntos = new HBox(puntos);

        contenedorNombre.setMinWidth(130);
        contenedorPuntos.setMinWidth(60);

        contenedorNombre.setStyle("-fx-alignment: center-left;");
        contenedorPuntos.setStyle("-fx-alignment: center-right;");

        HBox fila = new HBox();
        fila.getChildren().addAll(contenedorNombre, contenedorPuntos);

        contenedorTop10.getChildren().add(fila);
    }

}
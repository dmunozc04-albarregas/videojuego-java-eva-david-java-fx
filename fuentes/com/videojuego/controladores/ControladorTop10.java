package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.modelos.BDLaberinto;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class ControladorTop10 {

    @FXML
    private VBox contenedorTop10;
    private Font fuente;


    public void initialize() {
        fuente = Font.loadFont(getClass().getResourceAsStream("/PressStart2P.ttf"), 12);

        List<Jugador> listaTop10 = BDLaberinto.obtenerTop10();
        contenedorTop10.setSpacing(10); 

        int totalMostrado = 0;
        int espacioColumna = 0;

        for (Jugador j : listaTop10) {
            String textoNombre = j.getNombreUsuario();
            int valorPuntos = j.getPuntos();

            if (textoNombre == null || textoNombre.trim().isEmpty()) {
                textoNombre = "-";
                valorPuntos = 0;
            }
            System.out.println("Jugador: " + j.getNombreUsuario() + " -> " + j.getPuntos());

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

    HBox fila = new HBox(espacioColumna);
    fila.getChildren().addAll(contenedorNombre, contenedorPuntos);

    contenedorTop10.getChildren().add(fila);
}

}
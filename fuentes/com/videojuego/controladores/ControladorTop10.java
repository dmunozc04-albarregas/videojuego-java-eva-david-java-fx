/*package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.modelos.BDLaberinto;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class ControladorTop10 {

    @FXML
    private TableView<Jugador> tablaTop10;

    @FXML
    private TableColumn<Jugador, String> colNombre;

    @FXML
    private TableColumn<Jugador, Integer> colPuntos;

    @FXML
    public void initialize() {
    	// Asociar propiedades de la clase Jugador a las columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));

        // Obtener y mostrar los datos
        List<Jugador> top10 = BDLaberinto.obtenerTop10();
        tablaTop10.getItems().addAll(top10);
    }
}*/
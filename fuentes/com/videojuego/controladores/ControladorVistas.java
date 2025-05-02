package com.videojuego.controladores;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ControladorVistas {

	private Stage ventana;
	private Scene vista1;

	public ControladorVistas(Stage ventana){
		this.ventana = ventana;

		vista1 = cargarVista1();
		ventana.setScene(vista1);
		ventana.setTitle("Inicio videojuego");
		ventana.show();
	}

	public Scene cargarVista1(){
	   	try{
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/pantalla_splash.fxml"));
        	Parent raiz = fxmlLoader.load();
			vista1 = new Scene(raiz);
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("ERROR FATAL. No se encuentra la vista");
			System.exit(-1);
		}
        return vista1;
	}
}

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorMenu {
    private Jugador jugador = new Jugador();
    private ControladorEscenario controladorEscenario;
    //private ControladorTop10 controladorTop10;

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

    @FXML
    private Button btnAyuda;

    @FXML
    private Button btnTop10;

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

        String escenarioRuta = "";
        switch (boton.getId()) {
            case "btnNivel1":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario1.txt";
                break;
            case "btnNivel2":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario2.txt";
                break;
            case "btnNivel3":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario3.txt";
                break;
            case "btnNivel4":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario4.txt";
                break;
            case "btnAyuda": mostrarAyuda();
                return;
            case "btnTop10": mostrarTop10();
                return;
            default: 
                return;
        }

        controladorEscenario = new ControladorEscenario(stage, Paths.get(escenarioRuta), this);
        
        /*if (boton == btnNivel1) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario1.txt"), this);
        } else if (boton == btnNivel2) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario2.txt"), this);
        } else if (boton == btnNivel3) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario3.txt"), this);
        } else if (boton == btnNivel4) {
            controladorEscenario = new ControladorEscenario(stage, Paths.get("fuentes/com/videojuego/escenarios/escenario4.txt"), this);
        } else if (boton == btnAyuda) {
            mostrarAyuda();
        //} else if (boton == btnTop10) {
            //controladorTop10 = new ControladorTop10(stage, "/com/videojuego/vistas/vistaTop10.fxml", this);
        }*/
    }

    public void mostrar() {
        escenaMenu = Controlador.cargarVista("/com/videojuego/vistas/vistaMenu.fxml");
        Stage menu = new Stage();
        menu.setScene(escenaMenu);
        menu.centerOnScreen();
        menu.setTitle("Menú juego");
        menu.initModality(Modality.APPLICATION_MODAL); // Si quieres bloquear otras ventanas
        menu.show();
    }

    public void cerrarVentanaActual(Button boton) {
        Stage ventanaActual = (Stage) boton.getScene().getWindow();
        ventanaActual.close();
    }    

    @FXML
    private void mostrarAyuda() {
        try {
            Scene escenaAyuda = Controlador.cargarVista("/com/videojuego/vistas/vistaAyuda.fxml");
            Stage ventanaAyuda = new Stage();
            ventanaAyuda.setTitle("Ayuda");
            ventanaAyuda.setScene(escenaAyuda);
            ventanaAyuda.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            ventanaAyuda.centerOnScreen();
            ventanaAyuda.initModality(Modality.APPLICATION_MODAL);
            ventanaAyuda.setOnHidden(e -> {
                mostrar();
            });
            
            cerrarVentanaActual(btnAyuda);
            ventanaAyuda.show();
        }catch (Exception e) {
            e.printStackTrace();
            Controlador.mostrarAlerta("Error al mostrar la ventana ayuda");
        }
    }

    @FXML
    private void mostrarTop10() {
        try {
            Scene escenaTop10 = Controlador.cargarVista("/com/videojuego/vistas/vistaTop10.fxml");

            Stage ventanaTop10 = new Stage();
            ventanaTop10.setTitle("Top 10 jugadores");
            ventanaTop10.setScene(escenaTop10);
            ventanaTop10.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            ventanaTop10.centerOnScreen();
            ventanaTop10.initModality(Modality.APPLICATION_MODAL);
            ventanaTop10.setOnHidden(e -> {
                mostrar();
            });
            
            cerrarVentanaActual(btnTop10);
            ventanaTop10.show();
        }catch (Exception e) {
            e.printStackTrace();
            Controlador.mostrarAlerta("Error al mostrar la ventana ayuda");
        }
    }
}
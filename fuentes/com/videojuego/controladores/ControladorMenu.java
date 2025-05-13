package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.controladores.ControladorEscenario;
import com.videojuego.modelos.BDLaberinto;

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

/**
 * Clase que gestiona las interacciones del menu. El controlador gestiona los botones de 
 * selección de nivel, permite cargar diferentes escenarios en función del nivel seleccionado 
 * y proporciona acceso a la funcionalidad de ayuda y a la visualización del top 10 de jugadores.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorMenu {
    private Jugador jugador = new Jugador();
    private ControladorEscenario controladorEscenario;

    private int nivel;
    private Stage stage;
    private Scene escenaMenu;

    @FXML
    private Text nivelTexto;

    @FXML
    private Button btnNivel1, btnNivel2, btnNivel3, btnNivel4, btnAyuda, btnTop10;
    /**
     * Constructor sin parámetros.
     */
    public ControladorMenu() {
    }
    /**
     * Constructor con el parámetro Stage.
     * @param stage El escenario de la ventana principal.
     */
    public ControladorMenu(Stage stage) {
        this.stage = stage;
    }
    /**
     * Establece el jugador actual.
     * @param jugador El jugador que se asignará.
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    /**
     * Obtiene el jugador actual.
     * @return El jugador actual.
     */
    public Jugador getJugador() {
        return jugador;
    }
    /**
     * Establece el nivel actual.
     * @param nivel El nivel a establecer.
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    /**
     * Establece el nivel actual.
     * @param nivel El nivel a establecer.
     */
    public int getNivel() {
        return nivel;
    }
    /**
     * Establece el escenario de la ventana principal.
     * @param stage El escenario que se usará en la ventana.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
     /**
     * Inicializa el controlador. Carga la fuente personalizada y crea la tabla de laberinto.
     */
    @FXML
    public void initialize() {
        try {
            // Cargar la fuente personalizada en el controlador
            Font.loadFont(getClass().getResource("/PressStart2P.ttf").toExternalForm(), 10);
            BDLaberinto.crearTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Maneja la selección de nivel, cargando el escenario correspondiente y pasando al controlador adecuado.
     * @param event El evento de acción del botón que se ha pulsado.
     */
    @FXML
    private void seleccionarNivel(ActionEvent event) {
        Button boton = (Button) event.getSource();        
        Stage stage = (Stage) boton.getScene().getWindow();

        String escenarioRuta = "";
        int nivel = 0;
        switch (boton.getId()) {
            case "btnNivel1":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario1.txt";
                nivel = 1;
                break;
            case "btnNivel2":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario2.txt";
                nivel = 2;
                break;
            case "btnNivel3":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario3.txt";
                nivel = 3;
                break;
            case "btnNivel4":
                escenarioRuta = "fuentes/com/videojuego/escenarios/escenario4.txt";
                nivel = 4;
                break;
            case "btnAyuda": mostrarAyuda();
                return;
            case "btnTop10": mostrarTop10();
                return;
            default: 
                return;
        }
        controladorEscenario = new ControladorEscenario(stage, Paths.get(escenarioRuta), this, nivel);
    }
    /**
     * Muestra el menú del juego en una nueva ventana.
     */
    public void mostrar() {
        escenaMenu = Controlador.cargarVista("/com/videojuego/vistas/vistaMenu.fxml");
        Stage menu = new Stage();
        menu.setScene(escenaMenu);
        menu.centerOnScreen();
        menu.setTitle("Menú juego");
        menu.initModality(Modality.APPLICATION_MODAL); // Si quieres bloquear otras ventanas
        menu.show();
    }
    /**
     * Cierra la ventana actual del botón que llama a este método.
     * @param boton El botón que activó la acción.
     */
    public void cerrarVentanaActual(Button boton) {
        Stage ventanaActual = (Stage) boton.getScene().getWindow();
        ventanaActual.close();
    }    
    /**
     * Muestra la ventana de ayuda en una nueva ventana modal.
     */
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
    /**
     * Muestra la ventana del Top 10 de jugadores en una nueva ventana modal.
     */
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
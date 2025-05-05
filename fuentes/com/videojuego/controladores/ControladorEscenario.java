package com.videojuego.controladores;

import com.videojuego.modelos.Jugador;
import com.videojuego.modelos.Escenario;
import com.videojuego.modelos.BDLaberinto;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ControladorEscenario {
	private Stage ventana;
	private Scene vista1;
	private Scene vista2;
	private ControladorMenu controladorMenu;

	@FXML
	private GridPane gridPane; //Organiza los componentes en filas y columnas, similar a una tabla.

	private StackPane[][] stackPanes;
	Path rutaEscenario;	
	int alto;
	int ancho;
	private int filaPersonaje = 1;
	private int colPersonaje = 1;

	private Image imgEscenario;
	private ImageView ivPersonaje;

	private static final Integer LADO = 28;
	private static final Integer LOBST = 56;
	
	private Escenario escenario;
	private Jugador jugador;

	public ControladorEscenario(){}

	public ControladorEscenario(Stage ventana, Path rutaEscenario, ControladorMenu controladorMenu) {
		try{
			this.ventana = ventana;
			this.controladorMenu = controladorMenu;
			this.jugador = controladorMenu.getJugador();
        	cargarEscenario(rutaEscenario);
        	inicializarVista();
        }catch(IOException e) {
        	e.printStackTrace();
        	System.out.println("Error al cargar el escenario");
        }
    }

    private void cargarEscenario(Path rutaEscenario) throws IOException{
    	 // Cargar escenario y sus dimensiones
        this.escenario = new Escenario(rutaEscenario);
        alto = escenario.getAlto();
        ancho = escenario.getAncho();
        escenario.cargarDimensiones(rutaEscenario);
        stackPanes = new StackPane[alto][ancho];
        imgEscenario = new Image(this.getClass().getResourceAsStream("/fantasy_tiles.png"));
    }

    private void inicializarVista() {
    	BDLaberinto.crearTabla();
    	//Cargamos las vistas del Controlador
		vista1 = cargarVista(this, "vista1");
		vista2 = cargarVista(this, "vista2");

		//Componemos la vista
		HBox raizVista1 = (HBox) vista1.getRoot();
		VBox raizVista2 = (VBox) vista2.getRoot();
		raizVista1.getChildren().add(raizVista2);

		crearGrid(alto, ancho);

		// Configurar controles de teclado
        configurarControlesTeclado();

        // Inicializar la ventana
        ventana.setScene(vista1);
        ventana.centerOnScreen();
        ventana.setTitle("Laberinto");
        ventana.show();

        // Mostrar el escenario
        mostrarEscenario(escenario.getMapa());
    }

    private void configurarControlesTeclado() {
    	vista1.setOnKeyPressed(event -> {
    		// Si la tecla presionada es una de las teclas de dirección
    		switch (event.getCode()) {
        		case UP:
            		if (filaPersonaje > 0) {
                		moverPersonaje(filaPersonaje - 1, colPersonaje); // Mueve hacia arriba
            		}
            	break;
        		case DOWN:
            		if (filaPersonaje < alto - 1) {
                		moverPersonaje(filaPersonaje + 1, colPersonaje); // Mueve hacia abajo
            		}
            		break;
        		case LEFT:
            		if (colPersonaje > 0) {
                		moverPersonaje(filaPersonaje, colPersonaje - 1); // Mueve hacia la izquierda
            		}
            		break;
        		case RIGHT:
            		if (colPersonaje < ancho - 1) {
                		moverPersonaje(filaPersonaje, colPersonaje + 1); // Mueve hacia la derecha
            		}
            		break;
        		default:
            		return; // No hacer nada si la tecla no es de movimiento
    		}
		});
	}

	public void mostrarEscenario(char[][] mapa) {
	   	for (int i = 0; i < mapa.length; i++) {
    	    for (int j = 0; j < mapa[i].length; j++) {
         	    ImageView imageView = (ImageView) stackPanes[i][j].getChildren().get(0);
        		imageView.setViewport(obtenerViewport(mapa[i][j]));
        		imageView.setFitWidth(31);
        		imageView.setFitHeight(31);
        		imageView.setPreserveRatio(false);
        		imageView.setImage(imgEscenario);
    		}
    	}
    	inicializarPersonaje();
  	   	moverPersonaje(1, 0);
	}

    private void inicializarPersonaje() {
        Image imgPersonaje = new Image(this.getClass().getResourceAsStream("/personaje_animado.gif"));
        ivPersonaje = new ImageView(imgPersonaje);
        ivPersonaje.setFitHeight(30);
        ivPersonaje.setPreserveRatio(true);
        //Rectangle2D vpPersonaje = new Rectangle2D(1*100, 2*100, 100, 100);
		//ivPersonaje.setViewport(vpPersonaje);
    }

	private Rectangle2D obtenerViewport(char tipo) {
	    switch (tipo) {
    	    case 'O':
         	   return new Rectangle2D(2*LOBST, 0.4*LOBST, LOBST, LOBST);
        	case 'B':
           		return new Rectangle2D(18.5* LADO, 7* LADO, LADO, LADO);
           	case 'P':
           		return new Rectangle2D(2.35*LADO, 1.8*LADO, LADO, LADO);
        	case 'F':
     		    return new Rectangle2D(2.35*LADO, 1.8*LADO, LADO, LADO);
        	default:
            	return new Rectangle2D(4.5*LADO, 7* LADO, LADO, LADO);
    	}
	}

	private void crearGrid(Integer alto, Integer ancho) {
		for(int i = 0; i < alto; i++) {
        	gridPane.getRowConstraints().add(new RowConstraints()); 
		}
		for(int i = 0; i < ancho; i++) {
        	gridPane.getColumnConstraints().add(new ColumnConstraints()); 
		}
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				crearStackPane(i, j);
			}
		}
	}

	private void crearStackPane(int fila, int columna) {
        StackPane stackPane = new StackPane();
        stackPanes[fila][columna] = stackPane;
        ImageView imageView = new ImageView(imgEscenario);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setPreserveRatio(true);
        stackPane.getChildren().add(imageView);
        gridPane.add(stackPane, columna, fila);
    }

	private void moverPersonaje(int nuevaFila, int nuevaCol) {
    	char tipoCelda = escenario.getMapa()[nuevaFila][nuevaCol];

    	 // Verifica si es la celda de salida
    	if (tipoCelda == 'F') {
    		moverPersonajeConAnimacion(nuevaFila, nuevaCol);
 		    mostrarAlerta("¡Enhorabuena! Has llegado al final...");
        	terminarNivel(); // Terminar nivel si es celda de portal
        	return;

    	}

    	moverPersonajeConAnimacion(nuevaFila, nuevaCol);
    }

    private void moverPersonajeConAnimacion(int nuevaFila, int nuevaCol) {
    	//stackPanes[nuevaFila][nuevaCol].getChildren().add(ivPersonaje);

    	// Evitar movimiento redundante
    	if (filaPersonaje == nuevaFila && colPersonaje == nuevaCol) {
        	return;
    	}

    	// Eliminar de la celda anterior
    	stackPanes[filaPersonaje][colPersonaje].getChildren().remove(ivPersonaje);

    	// Añadir a la nueva celda si no está ya
    	if (!stackPanes[nuevaFila][nuevaCol].getChildren().contains(ivPersonaje)) {
        	stackPanes[nuevaFila][nuevaCol].getChildren().add(ivPersonaje);
    	}

    	// Actualizar posición
    	filaPersonaje = nuevaFila;
    	colPersonaje = nuevaCol;
	}

	/*public void iniciarJuego(Path rutaEscenario) {
		try{
			char[][] mapa = cargarEscenario(rutaEscenario);

			for (int i = 0; i < mapa.length; i++) {
    			for (int j = 0; j < mapa[i].length; j++) {
        			//System.out.print(mapa[i][j] + " ");
    			}
    			//System.out.println();  // Para saltar a la siguiente línea después de cada fila
			}
			mostrarEscenario(mapa);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}*/

	private Scene cargarVista(ControladorEscenario controlador, String nombre) {
        Scene vista = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/" + nombre + ".fxml"));
            fxmlLoader.setController(controlador);
            Parent raiz = fxmlLoader.load();
            vista = new Scene(raiz);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR FATAL. No se encuentra la vista " + nombre + ".");
            System.exit(-1);
        }
        return vista;
    }

    private void terminarNivel() {
    	BDLaberinto.insertarPuntuacion(jugador.getNombreUsuario(), jugador.getPuntos());
    	PauseTransition espera = new PauseTransition(Duration.seconds(2));
    	espera.setOnFinished(event -> {
        	//ventanaTop10();
        	ventana.close();
	        controladorMenu.mostrar();
    	});
    	espera.play();
	}

	private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Saliendo del juego..."); 
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /*private void ventanaTop10() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/videojuego/vistas/top10.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Top 10 jugadores");
			stage.setScene(new Scene(root));
			stage.show();
    	} catch (IOException e) {
        	e.printStackTrace(); 
    	}	
    }*/

}
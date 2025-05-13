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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import java.io.IOException;
import java.nio.file.Path;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;    

/**
 * Controlador encargado de gestionar las interacciones del escenario en el juego.
 * Administra el movimiento del personaje, la visualización del mapa, el cronómetro y la música.
 * @author David Muñoz - Eva Retamar
 * Licencia GPL v3. Fecha 03 2025
 */
public class ControladorEscenario extends Controlador {
	private Stage ventana;
	private Scene vista1;
	private Scene vista2;
	private ControladorMenu controladorMenu;
	private ControladorVistas controladorVistas;

	@FXML
	private GridPane gridPane; //Organiza los componentes en filas y columnas, similar a una tabla.

	@FXML
	private Label contadorGolpes;
	Integer contadorDeGolpes = 0;

	@FXML
	private Label cronometroLabel;
	private int segundos = 0;
	private Timeline timeLine;

	@FXML
	private Label labelChoque;

	private int nivel;

	private StackPane[][] stackPanes;
	private Path rutaEscenario;	
	private int alto;
	private int ancho;
	private int filaPersonaje = 1;
	private int colPersonaje = 1;

	private Image imgEscenario;
	private ImageView ivPersonaje;

	private static final Integer LADO = 28;
	private static final Integer LOBST = 56;
	private static boolean puertaBloqueada = false;
	private static MediaPlayer mediaPlayerEscenario;
	
	private Escenario escenario;
	private Jugador jugador;
	/**
     * Constructor por defecto.
     */
	public ControladorEscenario(){}
	/**
     * Inicializa el controlador con los parámetros proporcionados y
     * carga el escenario correspondiente.
     * @param ventana          La ventana principal de la aplicación (Stage) donde se mostrará la escena.
     * @param rutaEscenario    La ruta al archivo del escenario que se va a cargar.
     * @param controladorMenu  El controlador del menú principal para gestionar el acceso a la configuración y la interacción del usuario.
     * @param nivel            El nivel actual del juego, que determinará qué escenario se carga y su dificultad.
     */
	public ControladorEscenario(Stage ventana, Path rutaEscenario, ControladorMenu controladorMenu, int nivel) {
		try{
			this.ventana = ventana;
			this.controladorMenu = controladorMenu;
			this.nivel = nivel;
			Controlador.pararMusica();
			reproducirMusicaEscenario();
			ventana.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
			this.jugador = controladorMenu.getJugador();
			try {
				cargarEscenario(rutaEscenario);
			}catch(Exception e) {
				System.out.println("Se produjo un error al cargar el escenario: " + e.getMessage());
    			e.printStackTrace();
			}
        	inicializarVista();
    	} catch (Exception e) {  
        	e.printStackTrace();
        	Controlador.mostrarAlerta("Error al cargar el escenario");
    	}
    }
    /**
     * Carga el escenario desde el archivo de texto proporcionado.
     * @param rutaEscenario Ruta del archivo de escenario
     * @throws Exception Si ocurre un error al cargar el escenario
     */
    private void cargarEscenario(Path rutaEscenario) throws Exception{
    	 //Cargar escenario y sus dimensiones
        this.escenario = new Escenario(rutaEscenario);
        alto = escenario.getAlto();
        ancho = escenario.getAncho();
        stackPanes = new StackPane[alto][ancho];
        imgEscenario = new Image(this.getClass().getResourceAsStream("/fantasy_tiles.png"));
    }
    /**
     * Inicializa la vista, crea la interfaz gráfica y configura el control del teclado.
     */
    private void inicializarVista() {
    	//Cargamos las vistas del Controlador
		vista1 = cargarVistaConControlador(this, "vista1");
		vista2 = cargarVistaConControlador(this, "vista2");

		//Componemos la vista
		HBox raizVista1 = (HBox) vista1.getRoot();
		AnchorPane raizVista2 = (AnchorPane) vista2.getRoot();
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
    /**
     * Reproduce la música de fondo para el escenario.
     */
    private void reproducirMusicaEscenario(){
    	String pathMusica = "recursos/8_bit.mp3";

        Media media = new Media(new File(pathMusica).toURI().toString());

        //Media Player
        mediaPlayerEscenario = new MediaPlayer(media);
        mediaPlayerEscenario.setCycleCount(MediaPlayer.INDEFINITE); // Repetir en bucle
        mediaPlayerEscenario.play(); // Iniciar reproducción
    }
    /**
     * Detiene la música de fondo.
     */
    private void pararMusicaEscenario(){
    	mediaPlayerEscenario.stop();
    }
    /**
     * Configura los controles de teclado para mover al jugador por el escenario.
     */
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
	/**
     * Muestra el escenario en la interfaz gráfica.
     * @param mapa El mapa del escenario a mostrar
     */
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
    	iniciarCronometro();
    	inicializarPersonaje();
  	   	posicionarJugador();
	}
	/**
     * Inicializa el personaje en el escenario con la imagen correspondiente.
     */
    private void inicializarPersonaje() {
        Image imgPersonaje = new Image(this.getClass().getResourceAsStream("/personaje_animado.gif"));
        ivPersonaje = new ImageView(imgPersonaje);
        ivPersonaje.setFitHeight(30);
        ivPersonaje.setPreserveRatio(true);
    }
    /**
     * Devuelve la porción de la imagen que corresponde a un tipo de celda en el escenario.
     * @param tipo El tipo de celda ('O', 'B', 'P', 'F', etc.)
     * @return El área de la imagen correspondiente a la celda
     */
	private Rectangle2D obtenerViewport(char tipo) {
	    switch (tipo) {
    	    case 'O':
         	   return new Rectangle2D(2*LOBST, 0.4*LOBST, LOBST, LOBST);
        	case 'B':
           		return new Rectangle2D(4.5* LADO, 7* LADO, LADO, LADO);
           	case 'P':
           		return new Rectangle2D(2.35*LADO, 1.8*LADO, LADO, LADO);
        	case 'F':
     		    return new Rectangle2D(2.35*LADO, 1.8*LADO, LADO, LADO);
        	default:
            	return new Rectangle2D(18.5*LADO, 7* LADO, LADO, LADO);
    	}
	}
	/**
     * Crea una grilla para organizar los elementos visuales del escenario.
     * @param alto Número de filas en el escenario
     * @param ancho Número de columnas en el escenario
     */
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
	/**
     * Crea un StackPane para cada celda en el escenario.
     * @param fila Fila donde se coloca el StackPane
     * @param columna Columna donde se coloca el StackPane
     */
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
    /**
     * Posiciona al jugador en el escenario en la celda correspondiente.
     */
    private void posicionarJugador() {
    	char[][] mapa = escenario.getMapa();
    	for (int i = 0; i < mapa.length; i++) {
    		for (int j = 0; j < mapa[i].length; j++) {
    			if (mapa[i][j] == 'P') {
    				filaPersonaje = i;
    				colPersonaje = j;
    				stackPanes[i][j].getChildren().add(ivPersonaje);
    				puertaBloqueada = true;
    				return;
    			}
    		}
    	}
    }
    /**
     * Mueve al personaje a la nueva posición especificada.
     * @param nuevaFila Nueva fila a la que se mueve el personaje
     * @param nuevaCol Nueva columna a la que se mueve el personaje
     */
	private void moverPersonaje(int nuevaFila, int nuevaCol) {
    	char tipoCelda = escenario.getMapa()[nuevaFila][nuevaCol];

    	switch(tipoCelda){
    		case 'F':
    			actualizarPosicionPersonaje(nuevaFila, nuevaCol);
    			String tiempoFinal = cronometroLabel.getText();
    			timeLine.stop();
 		    	Controlador.mostrarAlerta("¡Enhorabuena! Has llegado al final..." + "\n" + "Tiempo tardado: " + tiempoFinal + "\n" + "Número de golpes: " + contadorDeGolpes + "\n" + "Puntuación: " + BDLaberinto.getPuntuacion());
 		    	System.out.println(segundos);
        		terminarNivel(); // Terminar nivel si es celda de portal
        		return;

        	case 'O', 'B':
        		//Controlador.mostrarAlerta("Te has chocado!!!");
        		mensajeAlerta("¡Te has chocado!");	
        		contadorDeGolpes++;
        		contadorGolpes.setText(contadorDeGolpes.toString());
    			return;

    		case 'P':
    			if(!puertaBloqueada){
    				puertaBloqueada = true;
    				actualizarPosicionPersonaje(nuevaFila, nuevaCol);
    			}
    			else{
    				mensajeAlerta("Esta puerta está bloqueada");
    			}
    			return;
    		default:
    			actualizarPosicionPersonaje(nuevaFila, nuevaCol);
    	}
    }
    /**
     * Actualiza la posición del personaje en el escenario.
     * @param nuevaFila  La nueva fila a la que se mueve el personaje.
     * @param nuevaCol   La nueva columna a la que se mueve el personaje.
     */
    private void actualizarPosicionPersonaje(int nuevaFila, int nuevaCol) {
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
	/**
 	 * Carga una vista FXML y la asocia con el controlador proporcionado. 
	 * @param controlador El controlador que gestionará la vista cargada.
	 * @param nombre      El nombre del archivo FXML (sin la extensión).
	 * @return La escena cargada con la vista y el controlador asociados.
	 */
	private Scene cargarVistaConControlador(ControladorEscenario controlador, String nombre) {
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
    /**
	 * Inicia un cronómetro que actualiza cada segundo y muestra el tiempo transcurrido
	 * en un formato "mm:ss" en el label correspondiente.
	 */
	private void iniciarCronometro() {
	    timeLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
	        segundos++;

	        int minutos = segundos / 60;
	        int restoSegundos = segundos % 60;

	        String tiempoFormateado = String.format("%02d:%02d", minutos, restoSegundos);
	        cronometroLabel.setText(tiempoFormateado);
	    }));

	    timeLine.setCycleCount(Timeline.INDEFINITE);
	    timeLine.play();
	}
	/**
	 * Muestra un mensaje de alerta en pantalla durante 1 segundo.
	 * @param texto El texto que se mostrará en el mensaje de alerta.
	 */
	private void mensajeAlerta(String texto){
		labelChoque.setText(texto);
		labelChoque.setVisible(true);
		PauseTransition espera = new PauseTransition(Duration.seconds(1));
    	espera.setOnFinished(event -> {
        	labelChoque.setVisible(false);
    	});
    	espera.play();
	}
	/**
	 * Termina el nivel actual, detiene la música del escenario y calcula la puntuación del jugador.
	 * Luego cierra la ventana del nivel y muestra el menú principal.
	 */
    private void terminarNivel() {
    	pararMusicaEscenario();
    	Controlador.reproducirMusica();
    	ControladorAccesoUsuario controladorAccesoUsuario = new ControladorAccesoUsuario();
    	BDLaberinto.calcularPuntuacion(controladorAccesoUsuario.getNombreUsuario(), this);
    	//ventanaTop10();
    	ventana.close();
        controladorMenu.mostrar();
	}
	/**
	 * Establece el nivel actual del juego.
	 * 
	 * @param nivel El nuevo nivel a establecer.
	 */
	public void setNivel(int nivel) {
	    this.nivel = nivel;
	}
	/**
	 * Obtiene el nivel actual del juego.
	 * 
	 * @return El nivel actual.
	 */
	public int getNivel() {
    	return nivel;
	}
	/**
	 * Obtiene el número total de golpes realizados por el jugador.
	 * 
	 * @return El número de golpes.
	 */
	public Integer getNumeroDeGolpes(){
    	return contadorDeGolpes;
    }
	/**
	 * Obtiene el tiempo transcurrido en segundos.
	 * 
	 * @return El tiempo transcurrido en segundos.
	 */
    public Integer getTiempo(){
    	return segundos;
    }
}
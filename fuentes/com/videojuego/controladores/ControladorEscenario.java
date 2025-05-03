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

public class ControladorPrincipal {
	private Stage ventana;
	private Scene vista1;
	private Scene vista2;

	@FXML
	private GridPane gridPane; //Organiza los componentes en filas y columnas, similar a una tabla.

	private StackPane[][] stackPanes;
	Path rutaEscenario = Paths.get("escenario1.txt");	
	private Integer ancho = 0;
	private Integer alto = 0;
	private int filaPersonaje = 1;
	private int colPersonaje = 1;

	private Image imgEscenario;
	private ImageView ivPersonaje;

	private static final Integer LADO = 28;

	public ControladorPrincipal(Stage ventana) {
		
		cargarDimensiones(rutaEscenario);
		stackPanes = new StackPane[alto][ancho];
		imgEscenario = new Image(this.getClass().getResourceAsStream("/fantasy_tiles.png"));

		this.ventana = ventana;

		//Cargamos las vistas del Controlador
		vista1 = cargarVista(this, "vista1");
		vista2 = cargarVista(this, "vista2");

		//Componemos la vista
		HBox raizVista1 = (HBox) vista1.getRoot();
		VBox raizVista2 = (VBox) vista2.getRoot();
		raizVista1.getChildren().add(raizVista2);

		crearGrid(alto, ancho);

		//Creación los viewports del escenario
		/*Rectangle2D vpSuelo = new Rectangle2D(5*LADO, 2*LADO, LADO, LADO);
		Rectangle2D vpEsquinaSuperiorIzquierda = new Rectangle2D(0*LADO, 3*LADO, LADO, LADO);
		Rectangle2D vpEsquinaSuperiorDerecha = new Rectangle2D(2*LADO, 3*LADO, LADO, LADO);
		Rectangle2D vpEsquinaInferiorIzquierda = new Rectangle2D(0*LADO, 5*LADO, LADO, LADO);
		Rectangle2D vpEsquinaInferiorDerecha = new Rectangle2D(2*LADO, 5*LADO, LADO, LADO);
		Rectangle2D vpBordeSuperior = new Rectangle2D(1*LADO, 5*LADO, LADO, LADO);
		Rectangle2D vpBordeInferior = new Rectangle2D(1*LADO, 3*LADO, LADO, LADO);
		Rectangle2D vpBordeLateralIzquierdo = new Rectangle2D(2*LADO, 4*LADO, LADO, LADO);
		Rectangle2D vpBordeLateralDerecho = new Rectangle2D(0*LADO, 4*LADO, LADO, LADO);
		Rectangle2D vpPared = new Rectangle2D(4*LADO, 2*LADO, LADO, LADO);
		Rectangle2D vpColumna = new Rectangle2D(2*LADO, 2*LADO, LADO, LADO);
		Rectangle2D vpPuerta = new Rectangle2D(1*LADO, 0*LADO, LADO, LADO);

		//Creación del personaje
		Image imgPersonaje = new Image(this.getClass().getResourceAsStream("/personaje.png"));
		ivPersonaje = new ImageView(imgPersonaje);
		ivPersonaje.setFitHeight(60);
		ivPersonaje.setPreserveRatio(true);
		Rectangle2D vpPersonaje = new Rectangle2D(3*128, 1*160, 128, 160);
		ivPersonaje.setViewport(vpPersonaje);
*/
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



		ventana.setScene(vista1);
		ventana.setTitle("Laberinto");
		ventana.show();

		
		iniciarJuego(rutaEscenario);
	}

	private void cargarDimensiones(Path rutaArchivo) {
		try {
	       	List<String> lineas = Files.readAllLines(rutaArchivo);
        	
        	// Imprimir las primeras líneas para verificar el contenido del archivo
        	//System.out.println("Contenido del archivo:");
        	for (String linea : lineas) {
            	//System.out.println(linea);
        	}

        	String primeraLinea = lineas.get(0); // Obtener la primera línea
        	String[] dimensiones = primeraLinea.split(" "); // Separar las dimensiones por espacio
        	
        	//System.out.println("Dimensiones leídas: " + dimensiones[0] + " x " + dimensiones[1]);

        	ancho = Integer.parseInt(dimensiones[0]); // Asignar el ancho
        	alto = Integer.parseInt(dimensiones[1]); // Asignar el alto
    	} catch (IOException e) {
        	e.printStackTrace();
    	}
	}

	public char[][] cargarEscenario(Path rutaArchivo) throws IOException {
    	List<String> lineas = Files.readAllLines(rutaArchivo);

    	// Ignorar la primera línea
    	lineas = lineas.subList(1, lineas.size()); 

	    List<char[]> mapaLista = new ArrayList<>();

    	for (String linea : lineas) {
        	String[] elementos = linea.trim().split(" ");
        	List<Character> fila = new ArrayList<>();
	        for (String elemento : elementos) {
    	        int cantidad = Integer.parseInt(elemento.substring(0, elemento.length() - 1));
        	    char tipo = elemento.charAt(elemento.length() - 1);
            	for (int i = 0; i < cantidad; i++) {
                	fila.add(tipo);
            	}
        	}

        	// Convertimos a array de chars
        	char[] filaArray = new char[fila.size()];
        	for (int i = 0; i < fila.size(); i++) {
            	filaArray[i] = fila.get(i);
        	}

        	mapaLista.add(filaArray);
    	}

    	// Convertimos a matriz
    	char[][] mapa = new char[mapaLista.size()][];
    	for (int i = 0; i < mapaLista.size(); i++) {
        	mapa[i] = mapaLista.get(i);
    	}

    	return mapa;
	}

	public void mostrarEscenario(char[][] mapa) {
	    //System.out.println("Dimensiones del escenario: " + mapa[0].length + " x " + mapa.length);
	   	for (int alto = 0; alto < mapa.length; alto++) {
    	    for (int ancho = 0; ancho < mapa[alto].length; ancho++) {
        	   // char tipo = mapa[alto][ancho];

            	// Crear un ImageView para cada celda
            	//ImageView imageView = new ImageView(imgEscenario);
         	    ImageView imageView1 = (ImageView) stackPanes[alto][ancho].getChildren().get(0);
            	imageView1.setViewport(obtenerViewport(mapa[alto][ancho]));  // Establecer el área de la imagen según el tipo
  				imageView1.setFitWidth(30.2);
            	imageView1.setFitHeight(40.2);
            	imageView1.setPreserveRatio(false);
            	imageView1.setImage(imgEscenario);
    		}
    	}
    	//Creación del Personaje
				Image imgPersonaje = new Image(this.getClass().getResourceAsStream("/personaje_animado.gif"));
				ivPersonaje = new ImageView(imgPersonaje);
				ivPersonaje.setFitHeight(30);
				ivPersonaje.setPreserveRatio(true);
				//Rectangle2D vpPersonaje = new Rectangle2D(1*100, 2*100, 100, 100);
				//ivPersonaje.setViewport(vpPersonaje);


  				//System.out.println(obtenerViewport(mapa[alto][ancho]));
  				// Colocar el ImageView en el GridPane
            	//gridPane.add(imageView, ancho, alto);  // Agregar la 
    	moverPersonaje(1, 1);
	}

	private Rectangle2D obtenerViewport(char tipo) {
	    switch (tipo) {
    	    case 'O':
         	   return new Rectangle2D(1*LADO, 3*LADO, LADO, LADO);
        	case 'I':
            	return new Rectangle2D(18.5* LADO, 7* LADO, LADO, LADO);
        	case 'D':
            	return new Rectangle2D(18.5* LADO, 7* LADO, LADO, LADO);
        	case 'A':
            	return new Rectangle2D(18.5* LADO, 7* LADO, LADO, LADO);
        	case 'B':
           		return new Rectangle2D(18.5* LADO, 7* LADO, LADO, LADO);
        	default:
            	return new Rectangle2D(4.5*LADO, 7* LADO, LADO, LADO);
        	//default:
            //	return new Rectangle2D(4*LADO, 2*LADO, LADO, LADO); // genérico
    	}
	}

	private void crearGrid(Integer alto, Integer ancho) {
		for(int i = 0; i < alto; i++) {
			//RowConstraints rowConstraints = new RowConstraints();
        	//rowConstraints.setPercentHeight(100.0 / alto); // Asegura que se distribuyan equitativamente
        	gridPane.getRowConstraints().add(new RowConstraints()); //128 es lo pixeles que quieres en la imagen
		}
		for(int i = 0; i < ancho; i++) {
			ColumnConstraints columnConstraints = new ColumnConstraints();
        	//columnConstraints.setPercentWidth(100.0 / ancho); // Asegura que se distribuyan equitativamente
        	gridPane.getColumnConstraints().add(new ColumnConstraints()); //esto es en el caso que quieras un cuadrado.
		}
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				StackPane stackPane = new StackPane();   //Poner una imagen encima de otra.
				//stackPane.setStyle("-fx-border-color: pink; -fx-border-width: 1;");
				stackPanes[i][j] = stackPane;
				ImageView imageView = new ImageView(imgEscenario);
				imageView.setFitWidth(60);
				imageView.setFitHeight(60);
				imageView.setPreserveRatio(true);
				stackPane.getChildren().add(imageView);
				gridPane.add(stackPane, j, i);
			}
		}
	}



	private void moverPersonaje(int nuevaFila, int nuevaCol) {
    stackPanes[nuevaFila][nuevaCol].getChildren().add(ivPersonaje);

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


	public void iniciarJuego(Path rutaEscenario) {
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
	}

	private Scene cargarVista(ControladorPrincipal controlador, String nombre) {
        Scene vista = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nombre + ".fxml"));
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
}
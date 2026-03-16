package view;

import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class VisorView {
    // Declaramos los componentes visuales
    private BorderPane raiz;
    private Label lblEstado;
    private Button btnCargar;
    private ImageView vistaCentral;
    private Slider sliderBrillo;
    private Slider sliderContraste;
    private Line lineaMedicion;
    private Label lblDistancia;

    public VisorView() {
        // 1. Inicializar componentes
        raiz = new BorderPane();
        lblEstado = new Label("Estado: Esperando directorio");
        btnCargar = new Button("Cargar Directorio");

        Label lblBrillo = new Label("Brillo:");
        sliderBrillo = new Slider(-1.0, 1.0, 0.0); // Mínimo, Máximo, Valor Inicial

        Label lblContraste = new Label("Contraste:");
        sliderContraste = new Slider(0.0, 3.0, 1.0); // Mínimo, Máximo, Valor Inicial

        lblDistancia = new Label("Distancia: 0.0 mm");
        lblDistancia.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

        // 2. Configuración de la Línea de Medición
        lineaMedicion = new Line();
        lineaMedicion.setStroke(Color.RED);     // Color rojo médico
        lineaMedicion.setStrokeWidth(2.0);      // Grosor de la línea
        lineaMedicion.setVisible(false);        // La ocultamos hasta que el usuario haga clic

        // 2. Construir el diseño
        raiz.setBottom(lblEstado);

        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setStyle("-fx-padding: 15px; -fx-background-color: #f4f4f4;");
        panelIzquierdo.getChildren().addAll(
                btnCargar,
                lblBrillo,
                sliderBrillo,
                lblContraste,
                sliderContraste,
                lblDistancia
        );

        raiz.setLeft(panelIzquierdo);

        vistaCentral = new ImageView();
        vistaCentral.setPreserveRatio(true);
        vistaCentral.setFitWidth(600);
        vistaCentral.setFitHeight(600);

        Pane contenedorImagen = new Pane();
        contenedorImagen.getChildren().addAll(vistaCentral, lineaMedicion);

        raiz.setCenter(contenedorImagen);
    }

    // --- GETTERS (Encapsulamiento) ---
    // El Controlador necesitará acceder a estos elementos para darles vida
    public BorderPane getRaiz() { return raiz; }
    public Button getBtnCargar() { return btnCargar; }
    public Label getLblEstado() { return lblEstado; }
    public ImageView getVistaCentral() { return vistaCentral; }
    public Slider getSliderBrillo() { return sliderBrillo; }
    public Slider getSliderContraste() { return sliderContraste; }
    public Line getLineaMedicion() { return lineaMedicion; }
    public Label getLblDistancia() { return lblDistancia; }
}

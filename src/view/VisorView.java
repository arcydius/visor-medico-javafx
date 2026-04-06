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
    private Button btnVolver;
    private Button btnMPR;
    private Button btnReporte;

    public VisorView() {
        raiz = new BorderPane();

        btnVolver = new Button("← Volver a Pacientes");
        btnVolver.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        javafx.scene.layout.HBox cabeceraSuperior = new javafx.scene.layout.HBox();
        cabeceraSuperior.setStyle("-fx-padding: 10px; -fx-background-color: #dcdde1;"); // Un gris claro de fondo
        cabeceraSuperior.getChildren().add(btnVolver);

        raiz.setTop(cabeceraSuperior);
        lblEstado = new Label("Estado: Esperando directorio");
        btnCargar = new Button("Cargar Directorio");

        Label lblBrillo = new Label("Brillo:");
        sliderBrillo = new Slider(-1.0, 1.0, 0.0); // Mínimo, Máximo, Valor Inicial

        Label lblContraste = new Label("Contraste:");
        sliderContraste = new Slider(0.0, 3.0, 1.0); // Mínimo, Máximo, Valor Inicial

        lblDistancia = new Label("Distancia: 0.0 mm");
        lblDistancia.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

        lineaMedicion = new Line();
        lineaMedicion.setStroke(Color.RED);
        lineaMedicion.setStrokeWidth(2.0);
        lineaMedicion.setVisible(false);

        raiz.setBottom(lblEstado);

        btnMPR = new Button("Abrir Reconstrucción 3D (MPR)");
        btnMPR.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold;"); // Un color morado tecnológico
        btnMPR.setMaxWidth(Double.MAX_VALUE);

        btnReporte = new Button("📝 Escribir Reporte");
        btnReporte.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold;");
        btnReporte.setMaxWidth(Double.MAX_VALUE);

        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setStyle("-fx-padding: 15px; -fx-background-color: #f4f4f4;");
        panelIzquierdo.getChildren().addAll(
                btnCargar,
                btnMPR,
                btnReporte,
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

    public BorderPane getRaiz() { return raiz; }
    public Button getBtnCargar() { return btnCargar; }
    public Label getLblEstado() { return lblEstado; }
    public ImageView getVistaCentral() { return vistaCentral; }
    public Slider getSliderBrillo() { return sliderBrillo; }
    public Slider getSliderContraste() { return sliderContraste; }
    public Line getLineaMedicion() { return lineaMedicion; }
    public Label getLblDistancia() { return lblDistancia; }
    public Button getBtnVolver() { return btnVolver; }
    public Button getBtnMPR() { return btnMPR; }
    public Button getBtnReporte() { return btnReporte; }
}

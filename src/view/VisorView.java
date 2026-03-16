package view;

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

    public VisorView() {
        // 1. Inicializar componentes
        raiz = new BorderPane();
        lblEstado = new Label("Estado: Esperando directorio");
        btnCargar = new Button("Cargar Directorio");

        Label lblBrillo = new Label("Brillo:");
        sliderBrillo = new Slider(-1.0, 1.0, 0.0); // Mínimo, Máximo, Valor Inicial

        Label lblContraste = new Label("Contraste:");
        sliderContraste = new Slider(0.0, 3.0, 1.0); // Mínimo, Máximo, Valor Inicial

        vistaCentral = new ImageView();

        // 2. Construir el diseño (Lo que tenías en tu start)
        raiz.setBottom(lblEstado);

        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setStyle("-fx-padding: 15px; -fx-background-color: #f4f4f4;");
        panelIzquierdo.getChildren().add(btnCargar);
        panelIzquierdo.getChildren().add(lblBrillo);
        panelIzquierdo.getChildren().add(sliderBrillo);
        panelIzquierdo.getChildren().add(lblContraste);
        panelIzquierdo.getChildren().add(sliderContraste);

        raiz.setLeft(panelIzquierdo);

        vistaCentral.setPreserveRatio(true);
        vistaCentral.setFitWidth(600);
        vistaCentral.setFitHeight(600);
        raiz.setCenter(vistaCentral);
    }

    // --- GETTERS (Encapsulamiento) ---
    // El Controlador necesitará acceder a estos elementos para darles vida
    public BorderPane getRaiz() { return raiz; }
    public Button getBtnCargar() { return btnCargar; }
    public Label getLblEstado() { return lblEstado; }
    public ImageView getVistaCentral() { return vistaCentral; }
    public Slider getSliderBrillo() { return sliderBrillo; }
    public Slider getSliderContraste() { return sliderContraste; }
}

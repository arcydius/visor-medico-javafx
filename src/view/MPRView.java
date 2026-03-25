package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MPRView {

    private BorderPane raiz;
    private Button btnVolver;

    // Las 3 pantallas de visualización
    private ImageView vistaAxial;   // Vista desde arriba
    private ImageView vistaCoronal; // Vista de frente
    private ImageView vistaSagital; // Vista de perfil

    // Los 3 controles de navegación
    private Slider sliderAxial;
    private Slider sliderCoronal;
    private Slider sliderSagital;

    public MPRView() {
        raiz = new BorderPane();
        raiz.setStyle("-fx-background-color: #1e1e1e;"); // Fondo oscuro radiológico

        // --- 1. CABECERA ---
        HBox cabecera = new HBox(15);
        cabecera.setPadding(new Insets(15));
        cabecera.setStyle("-fx-background-color: #2c3e50;");
        cabecera.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Reconstrucción Multiplanar (MPR) 3D");
        lblTitulo.setFont(new Font("Arial", 20));
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        btnVolver = new Button("← Volver al Visor 2D");
        btnVolver.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        cabecera.getChildren().addAll(lblTitulo, espaciador, btnVolver);
        raiz.setTop(cabecera);

        // --- 2. CUADRÍCULA CENTRAL (GridPane 2x2) ---
        GridPane cuadricula = new GridPane();
        cuadricula.setPadding(new Insets(20));
        cuadricula.setHgap(20);
        cuadricula.setVgap(20);
        cuadricula.setAlignment(Pos.CENTER);

        // Configurar los 3 paneles
        VBox panelAxial = crearPanelVisor("Plano Axial (Z) - Original", vistaAxial = new ImageView(), sliderAxial = new Slider());
        VBox panelCoronal = crearPanelVisor("Plano Coronal (Y) - Frente", vistaCoronal = new ImageView(), sliderCoronal = new Slider());
        VBox panelSagital = crearPanelVisor("Plano Sagital (X) - Perfil", vistaSagital = new ImageView(), sliderSagital = new Slider());

        // Ubicar en la cuadrícula (Columna, Fila)
        cuadricula.add(panelAxial, 0, 0);     // Arriba Izquierda
        cuadricula.add(panelCoronal, 1, 0);   // Arriba Derecha
        cuadricula.add(panelSagital, 0, 1);   // Abajo Izquierda

        // La celda [1, 1] (Abajo Derecha) la dejamos vacía por ahora, o podrías poner info del paciente allí después

        raiz.setCenter(cuadricula);
    }

    // Método auxiliar para no repetir código al crear cada "pantallita"
    private VBox crearPanelVisor(String titulo, ImageView visor, Slider slider) {
        VBox panel = new VBox(10);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-border-color: #555; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: #000;");

        Label lblSello = new Label(titulo);
        lblSello.setStyle("-fx-text-fill: #00ff00; -fx-font-family: monospace;"); // Letras verdes estilo monitor médico

        visor.setFitWidth(350);
        visor.setFitHeight(350);
        visor.setPreserveRatio(false);

        slider.setPrefWidth(350);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);

        panel.getChildren().addAll(lblSello, visor, slider);
        return panel;
    }

    // --- GETTERS ---
    public BorderPane getRaiz() { return raiz; }
    public Button getBtnVolver() { return btnVolver; }

    public ImageView getVistaAxial() { return vistaAxial; }
    public ImageView getVistaCoronal() { return vistaCoronal; }
    public ImageView getVistaSagital() { return vistaSagital; }

    public Slider getSliderAxial() { return sliderAxial; }
    public Slider getSliderCoronal() { return sliderCoronal; }
    public Slider getSliderSagital() { return sliderSagital; }
}
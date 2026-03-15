package view;
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

    public VisorView() {
        // 1. Inicializar componentes
        raiz = new BorderPane();
        lblEstado = new Label("Estado: Esperando directorio");
        btnCargar = new Button("Cargar Directorio");
        vistaCentral = new ImageView();

        // 2. Construir el diseño (Lo que tenías en tu start)
        raiz.setBottom(lblEstado);

        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setStyle("-fx-padding: 15px; -fx-background-color: #f4f4f4;");
        panelIzquierdo.getChildren().add(btnCargar);
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
}

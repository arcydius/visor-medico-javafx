package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ReporteView {

    private BorderPane raiz;
    private Button btnVolver;
    private Button btnExportarPDF;

    // Campos del formulario
    private TextField txtPaciente;
    private TextField txtEstudio;
    private TextArea txtHallazgos;
    private TextArea txtConclusion;

    public ReporteView() {
        raiz = new BorderPane();
        raiz.setStyle("-fx-background-color: #f4f4f4;");

        HBox cabecera = new HBox(15);
        cabecera.setPadding(new Insets(15));
        cabecera.setStyle("-fx-background-color: #2c3e50;");
        cabecera.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Reporte Radiológico");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        btnVolver = new Button("← Volver al Visor");
        btnVolver.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");

        cabecera.getChildren().addAll(btnVolver, lblTitulo);
        raiz.setTop(cabecera);

        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(30));
        formulario.setAlignment(Pos.TOP_LEFT);

        Label lblPaciente = new Label("Nombre del Paciente:");
        txtPaciente = new TextField();
        txtPaciente.setPromptText("Ej. Juan Pérez");

        Label lblEstudio = new Label("Tipo de Estudio:");
        txtEstudio = new TextField();
        txtEstudio.setPromptText("Ej. Tomografía Axial Computarizada de Cráneo");

        Label lblHallazgos = new Label("Hallazgos Clínicos:");
        lblHallazgos.setStyle("-fx-font-weight: bold;");
        txtHallazgos = new TextArea();
        txtHallazgos.setPromptText("Describa aquí lo que observa en las imágenes...");
        txtHallazgos.setPrefRowCount(8);

        Label lblConclusion = new Label("Conclusión / Diagnóstico:");
        lblConclusion.setStyle("-fx-font-weight: bold;");
        txtConclusion = new TextArea();
        txtConclusion.setPromptText("Diagnóstico final...");
        txtConclusion.setPrefRowCount(4);

        btnExportarPDF = new Button("📄 Exportar Reporte a PDF");
        btnExportarPDF.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnExportarPDF.setMaxWidth(Double.MAX_VALUE); // Para que ocupe todo el ancho

        formulario.getChildren().addAll(
                lblPaciente, txtPaciente,
                lblEstudio, txtEstudio,
                lblHallazgos, txtHallazgos,
                lblConclusion, txtConclusion,
                new Label(""),
                btnExportarPDF
        );

        raiz.setCenter(formulario);
    }

    public BorderPane getRaiz() { return raiz; }
    public Button getBtnVolver() { return btnVolver; }
    public Button getBtnExportarPDF() { return btnExportarPDF; }
    public TextField getTxtPaciente() { return txtPaciente; }
    public TextField getTxtEstudio() { return txtEstudio; }
    public TextArea getTxtHallazgos() { return txtHallazgos; }
    public TextArea getTxtConclusion() { return txtConclusion; }
}
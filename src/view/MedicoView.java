package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import model.Paciente;

import java.time.LocalDate;

public class MedicoView {

    private BorderPane raiz;
    private Label lblBienvenida;
    private Button btnCerrarSesion;
    private TableView<Paciente> tablaPacientes;
    private Button btnAbrirVisor;
    private Label lblMensaje;

    public MedicoView() {
        raiz = new BorderPane();
        raiz.setPadding(new Insets(20));
        raiz.setStyle("-fx-background-color: #f4f4f4;");

        // --- 1. CABECERA (Top) ---
        HBox cabecera = new HBox(20);
        cabecera.setAlignment(Pos.CENTER_LEFT);
        cabecera.setPadding(new Insets(0, 0, 20, 0));

        lblBienvenida = new Label("Cargando...");
        lblBienvenida.setFont(new Font("Arial", 22));
        lblBienvenida.setStyle("-fx-font-weight: bold;");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        // Añadimos la etiqueta, el resorte y el botón
        cabecera.getChildren().addAll(lblBienvenida, espaciador, btnCerrarSesion);
        raiz.setTop(cabecera);

        // --- 2. TABLA DE PACIENTES (Centro) ---
        VBox panelCentral = new VBox(10);

        Label lblTituloTabla = new Label("Lista de Pacientes Registrados");
        lblTituloTabla.setFont(new Font("Arial", 16));

        tablaPacientes = new TableView<>();
        tablaPacientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Paciente, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        TableColumn<Paciente, String> colNombre = new TableColumn<>("Nombre Completo");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));

        TableColumn<Paciente, LocalDate> colFecha = new TableColumn<>("Fecha de Nacimiento");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        // Añadimos las columnas a la tabla
        tablaPacientes.getColumns().addAll(colDni, colNombre, colFecha);

        panelCentral.getChildren().addAll(lblTituloTabla, tablaPacientes);
        raiz.setCenter(panelCentral);

        // --- 3. CONTROLES INFERIORES (Bottom) ---
        VBox panelInferior = new VBox(10);
        panelInferior.setAlignment(Pos.CENTER);
        panelInferior.setPadding(new Insets(20, 0, 0, 0));

        btnAbrirVisor = new Button("Abrir Tomografía del Paciente");
        btnAbrirVisor.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        btnAbrirVisor.setPrefWidth(350);
        btnAbrirVisor.setPrefHeight(40);

        lblMensaje = new Label("");

        panelInferior.getChildren().addAll(btnAbrirVisor, lblMensaje);
        raiz.setBottom(panelInferior);
    }

    // --- GETTERS ---
    public BorderPane getRaiz() { return raiz; }
    public Label getLblBienvenida() { return lblBienvenida; }
    public Button getBtnCerrarSesion() { return btnCerrarSesion; }
    public TableView<Paciente> getTablaPacientes() { return tablaPacientes; }
    public Button getBtnAbrirVisor() { return btnAbrirVisor; }
    public Label getLblMensaje() { return lblMensaje; }
}
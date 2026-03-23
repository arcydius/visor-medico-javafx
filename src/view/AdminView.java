package view;


import java.util.function.UnaryOperator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdminView {
    private BorderPane raiz;
    private Button btnRegistrarMedico;
    private Button btnRegistrarPaciente;
    private Button btnCerrarSesion;
    private VBox panelCentral;
    private Label lblBienvenida;
    private VBox formularioMedico;
    private TextField txtNombreMedico;
    private TextField txtUserMedico;
    private PasswordField txtPassMedico;
    private TextField txtPassMedicoVisible;
    private CheckBox chkMostrarClave;
    private ComboBox<String> cbEspecialidad;
    private Button btnGuardarMedico;
    private Label lblMensajeFormulario;

    private VBox formularioPaciente;
    private TextField txtDniPaciente;
    private TextField txtNombrePaciente;
    private DatePicker dpFechaNacimiento;
    private Button btnSeleccionarCarpeta;
    private Label lblRutaCarpeta;
    private Button btnGuardarPaciente;
    private Label lblMensajePaciente;

    public AdminView() {
        raiz = new BorderPane();

        // --- 1. MENÚ LATERAL (Izquierda) ---
        VBox menuLateral = new VBox(15);
        menuLateral.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20px;"); // Azul oscuro profesional
        menuLateral.setPrefWidth(200);

        Label lblTituloMenu = new Label("Panel Admin");
        lblTituloMenu.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");

        btnRegistrarMedico = new Button("Registrar Médico");
        btnRegistrarMedico.setMaxWidth(Double.MAX_VALUE); // Para que el botón ocupe todo el ancho del menú

        btnRegistrarPaciente = new Button("Registrar Paciente");
        btnRegistrarPaciente.setMaxWidth(Double.MAX_VALUE);

        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setMaxWidth(Double.MAX_VALUE);
        btnCerrarSesion.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        menuLateral.getChildren().addAll(lblTituloMenu, btnRegistrarMedico, btnRegistrarPaciente, btnCerrarSesion);
        raiz.setLeft(menuLateral);

        // --- 2. ÁREA DE TRABAJO (Centro) ---
        panelCentral = new VBox(20);
        panelCentral.setAlignment(Pos.CENTER);
        panelCentral.setStyle("-fx-background-color: #ffffff; -fx-padding: 40px;");

        lblBienvenida = new Label("Cargando...");
        lblBienvenida.setFont(new Font("Arial", 24));
        lblBienvenida.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        panelCentral.getChildren().add(lblBienvenida);
        raiz.setCenter(panelCentral);

        // --- 3. FORMULARIO DE MÉDICO (Oculto por defecto) ---
        formularioMedico = new VBox(15);
        formularioMedico.setAlignment(Pos.CENTER);

        Label lblTituloForm = new Label("Registrar Nuevo Médico");
        lblTituloForm.setFont(new Font("Arial", 20));
        lblTituloForm.setStyle("-fx-font-weight: bold;");

        txtNombreMedico = new TextField();
        txtNombreMedico.setPromptText("Nombre Completo (Ej. Ana Gómez)");
        txtNombreMedico.setMaxWidth(300);

        txtUserMedico = new TextField();
        txtUserMedico.setPromptText("Usuario para iniciar sesión");
        txtUserMedico.setMaxWidth(300);

        txtPassMedico = new PasswordField();
        txtPassMedico.setPromptText("Contraseña");
        txtPassMedico.setMaxWidth(300);

        txtPassMedicoVisible = new TextField();
        txtPassMedicoVisible.setPromptText("Contraseña");
        txtPassMedicoVisible.setMaxWidth(300);
        txtPassMedicoVisible.setVisible(false);
        txtPassMedicoVisible.setManaged(false);

        txtPassMedicoVisible.textProperty().bindBidirectional(txtPassMedico.textProperty());

        chkMostrarClave = new CheckBox("Mostrar contraseña");

        cbEspecialidad = new ComboBox<>();

        cbEspecialidad = new ComboBox<>();
        cbEspecialidad.getItems().addAll("Radiologia", "Neurologia", "Traumatologia", "Cardiologia", "General");
        cbEspecialidad.setPromptText("Seleccione Especialidad");
        cbEspecialidad.setMaxWidth(300);

        btnGuardarMedico = new Button("Guardar Médico");
        btnGuardarMedico.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardarMedico.setPrefWidth(300);

        lblMensajeFormulario = new Label("");

        formularioMedico.getChildren().addAll(
                lblTituloForm,
                txtNombreMedico,
                txtUserMedico,
                txtPassMedico,
                txtPassMedicoVisible,
                chkMostrarClave,
                cbEspecialidad,
                btnGuardarMedico,
                lblMensajeFormulario);

        // --- 4. FORMULARIO DE PACIENTE  ---
        formularioPaciente = new VBox(15);
        formularioPaciente.setAlignment(Pos.CENTER);

        Label lblTituloPaciente = new Label("Registrar Nuevo Paciente");
        lblTituloPaciente.setFont(new Font("Arial", 20));
        lblTituloPaciente.setStyle("-fx-font-weight: bold;");

        txtDniPaciente = new TextField();
        txtDniPaciente.setPromptText("DNI / Documento de Identidad");
        txtDniPaciente.setMaxWidth(300);

        txtNombrePaciente = new TextField();
        txtNombrePaciente.setPromptText("Nombre Completo del Paciente");
        txtNombrePaciente.setMaxWidth(300);

        dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.setPromptText("Fecha de Nacimiento");
        dpFechaNacimiento.setMaxWidth(300);
        dpFechaNacimiento.setEditable(false);

        // Controles para seleccionar la carpeta del tomógrafo
        btnSeleccionarCarpeta = new Button("Seleccionar Carpeta de Imágenes...");
        btnSeleccionarCarpeta.setMaxWidth(300);

        lblRutaCarpeta = new Label("Ninguna carpeta seleccionada");
        lblRutaCarpeta.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");

        btnGuardarPaciente = new Button("Guardar Paciente");
        btnGuardarPaciente.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardarPaciente.setPrefWidth(300);

        lblMensajePaciente = new Label("");

        formularioPaciente.getChildren().addAll(
                lblTituloPaciente, txtDniPaciente, txtNombrePaciente, dpFechaNacimiento,
                btnSeleccionarCarpeta, lblRutaCarpeta, btnGuardarPaciente, lblMensajePaciente
        );
    }

    // --- GETTERS ---
    public BorderPane getRaiz() { return raiz; }
    public Button getBtnRegistrarMedico() { return btnRegistrarMedico; }
    public Button getBtnRegistrarPaciente() { return btnRegistrarPaciente; }
    public Button getBtnCerrarSesion() { return btnCerrarSesion; }
    public VBox getPanelCentral() { return panelCentral; }
    public Label getLblBienvenida() { return lblBienvenida; }
    public VBox getFormularioMedico() { return formularioMedico; }
    public TextField getTxtNombreMedico() { return txtNombreMedico; }
    public TextField getTxtUserMedico() { return txtUserMedico; }
    public TextField getTxtPassMedicoVisible() { return txtPassMedicoVisible; }
    public CheckBox getChkMostrarClave() { return chkMostrarClave; }
    public PasswordField getTxtPassMedico() { return txtPassMedico; }
    public ComboBox<String> getCbEspecialidad() { return cbEspecialidad; }
    public Button getBtnGuardarMedico() { return btnGuardarMedico; }
    public Label getLblMensajeFormulario() { return lblMensajeFormulario; }
    public VBox getFormularioPaciente() { return formularioPaciente; }
    public TextField getTxtDniPaciente() { return txtDniPaciente; }
    public TextField getTxtNombrePaciente() { return txtNombrePaciente; }
    public DatePicker getDpFechaNacimiento() { return dpFechaNacimiento; }
    public Button getBtnSeleccionarCarpeta() { return btnSeleccionarCarpeta; }
    public Label getLblRutaCarpeta() { return lblRutaCarpeta; }
    public Button getBtnGuardarPaciente() { return btnGuardarPaciente; }
    public Label getLblMensajePaciente() { return lblMensajePaciente; }
}
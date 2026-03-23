package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LoginView {

    private VBox raiz;
    private TextField txtUsuario;
    private PasswordField txtPassword;
    private Button btnIngresar;
    private Button btnSalir;
    private Label lblMensaje;

    public LoginView() {
        raiz = new VBox(15); // 15 píxeles de espacio entre elementos
        raiz.setAlignment(Pos.CENTER);
        raiz.setStyle("-fx-padding: 50px; -fx-background-color: linear-gradient(to bottom, #2c3e50, #2980b9);");

        Label lblTitulo = new Label("Acceso al Sistema Médico");
        lblTitulo.setFont(new Font("Arial", 20));
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        txtUsuario = new TextField();
        txtUsuario.setPromptText("Nombre de usuario");
        txtUsuario.setMaxWidth(200);

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(200);

        btnIngresar = new Button("Ingresar");
        btnIngresar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        btnIngresar.setPrefWidth(200);
        btnIngresar.setDefaultButton(true);

        btnSalir = new Button("Salir");
        btnSalir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSalir.setPrefWidth(200);

        lblMensaje = new Label("");
        lblMensaje.setStyle("-fx-text-fill: #ffcccc; -fx-font-weight: bold;");

        raiz.getChildren().addAll(lblTitulo, txtUsuario, txtPassword, btnIngresar, btnSalir, lblMensaje);
    }

    // Getters
    public VBox getRaiz() { return raiz; }
    public TextField getTxtUsuario() { return txtUsuario; }
    public PasswordField getTxtPassword() { return txtPassword; }
    public Button getBtnIngresar() { return btnIngresar; }
    public Button getBtnSalir() { return btnSalir; }
    public Label getLblMensaje() { return lblMensaje; }
}
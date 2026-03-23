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
    private PasswordField txtPassword; // Oculta el texto con asteriscos
    private Button btnIngresar;
    private Label lblMensaje;

    public LoginView() {
        raiz = new VBox(15); // 15 píxeles de espacio entre elementos
        raiz.setAlignment(Pos.CENTER);
        raiz.setStyle("-fx-padding: 50px; -fx-background-color: #f4f4f4;");

        Label lblTitulo = new Label("Acceso al Sistema Médico");
        lblTitulo.setFont(new Font("Arial", 20));
        lblTitulo.setStyle("-fx-font-weight: bold;");

        txtUsuario = new TextField();
        txtUsuario.setPromptText("Nombre de usuario");
        txtUsuario.setMaxWidth(200);

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(200);

        btnIngresar = new Button("Ingresar");
        btnIngresar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        btnIngresar.setPrefWidth(200);

        lblMensaje = new Label("");
        lblMensaje.setStyle("-fx-text-fill: red;");

        raiz.getChildren().addAll(lblTitulo, txtUsuario, txtPassword, btnIngresar, lblMensaje);
    }

    // Getters
    public VBox getRaiz() { return raiz; }
    public TextField getTxtUsuario() { return txtUsuario; }
    public PasswordField getTxtPassword() { return txtPassword; }
    public Button getBtnIngresar() { return btnIngresar; }
    public Label getLblMensaje() { return lblMensaje; }
}
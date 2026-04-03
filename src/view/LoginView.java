package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.PointLight;
import javafx.scene.text.Font;

public class LoginView {

    private VBox raiz;
    private TextField txtUsuario;
    private PasswordField txtPassword;
    private Button btnIngresar;
    private Button btnSalir;
    private Label lblMensaje;

    public LoginView() {
        raiz = new VBox(15);
        raiz.setAlignment(Pos.CENTER);
        raiz.setStyle("-fx-padding: 50px; -fx-background-color: linear-gradient(to bottom, #2c3e50, #2980b9);");

        StackPane contenedor3D = new StackPane();
        contenedor3D.setPrefSize(400, 400);
        contenedor3D.setStyle("-fx-background-color: transparent;");

        Cruz3DView cross3D = new Cruz3DView();
        Group grupoRaiz3D = new Group();
        grupoRaiz3D.getChildren().add(cross3D.getGrupoCruz());

        PointLight luzFrontal = new PointLight(Color.rgb(200, 200, 200));
        luzFrontal.setTranslateX(0);
        luzFrontal.setTranslateY(0);
        luzFrontal.setTranslateZ(-150);

// Luz de relleno desde atrás (para aclarar la parte oscura)
        PointLight luzTrasera = new PointLight(Color.rgb(150, 150, 150));
        luzTrasera.setTranslateX(0);
        luzTrasera.setTranslateY(0);
        luzTrasera.setTranslateZ(150); // detrás del modelo

// Luces laterales suaves
        PointLight luzIzq = new PointLight(Color.rgb(130, 130, 150));
        luzIzq.setTranslateX(-120);
        luzIzq.setTranslateY(20);
        luzIzq.setTranslateZ(0);

        PointLight luzDer = new PointLight(Color.rgb(130, 130, 150));
        luzDer.setTranslateX(120);
        luzDer.setTranslateY(20);
        luzDer.setTranslateZ(0);

        SubScene escena3D = new SubScene(grupoRaiz3D, 400, 400, true, SceneAntialiasing.BALANCED);
        escena3D.setFill(Color.TRANSPARENT);

        PerspectiveCamera camara = new PerspectiveCamera(true);
        camara.setTranslateZ(-160);
        camara.setFieldOfView(45);
        camara.setNearClip(0.1);
        camara.setFarClip(1000);
        escena3D.setCamera(camara);

        contenedor3D.getChildren().add(escena3D);

        Label lblTitulo = new Label("Acceso al Sistema Médico");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        txtUsuario = new TextField();
        txtUsuario.setPromptText("Nombre de usuario");
        txtUsuario.setMaxWidth(220);

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(220);

        btnIngresar = new Button("Ingresar");
        btnIngresar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        btnIngresar.setPrefWidth(220);
        btnIngresar.setDefaultButton(true);

        btnSalir = new Button("Salir");
        btnSalir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSalir.setPrefWidth(220);

        lblMensaje = new Label("");
        lblMensaje.setStyle("-fx-text-fill: #ffcccc; -fx-font-weight: bold;");

        raiz.getChildren().addAll(contenedor3D, lblTitulo, txtUsuario, txtPassword, btnIngresar, btnSalir, lblMensaje);
    }

    public VBox getRaiz() { return raiz; }
    public TextField getTxtUsuario() { return txtUsuario; }
    public PasswordField getTxtPassword() { return txtPassword; }
    public Button getBtnIngresar() { return btnIngresar; }
    public Button getBtnSalir() { return btnSalir; }
    public Label getLblMensaje() { return lblMensaje; }
}

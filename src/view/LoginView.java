package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;

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
        contenedor3D.setPrefSize(300, 300);

        Cerebro3DView cerebro3D = new Cerebro3DView();

        SubScene escena3D = new SubScene(cerebro3D.getGrupoCerebro(), 300, 300, true, SceneAntialiasing.BALANCED);
        escena3D.setFill(Color.rgb(44, 62, 80));

        // ILUMINACIÓN
        PointLight luzPrincipal = new PointLight(Color.WHITE);
        luzPrincipal.setTranslateX(150);
        luzPrincipal.setTranslateY(150);
        luzPrincipal.setTranslateZ(300);

        PointLight luzAzul = new PointLight(Color.rgb(100, 150, 255));
        luzAzul.setTranslateX(-120);
        luzAzul.setTranslateY(80);
        luzAzul.setTranslateZ(250);

        PointLight luzCalida = new PointLight(Color.rgb(255, 200, 150));
        luzCalida.setTranslateX(0);
        luzCalida.setTranslateY(-100);
        luzCalida.setTranslateZ(200);

        AmbientLight luzAmbiente = new AmbientLight(Color.rgb(100, 100, 120));

        cerebro3D.getGrupoCerebro().getChildren().addAll(luzPrincipal, luzAzul, luzCalida, luzAmbiente);

        // Configurar cámara
        PerspectiveCamera camara = new PerspectiveCamera(true);
        camara.setTranslateZ(-220);
        camara.setFieldOfView(35);
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
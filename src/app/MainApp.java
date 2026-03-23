package app;

import controller.LoginController;
import view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.ConexionDB;
import java.sql.Connection;

public class MainApp extends Application{

    @Override
    public void start(Stage escenarioPrincipal) {
        // 1. Instanciamos la Vista de Login
        LoginView vistaLogin = new LoginView();

        // 2. Instanciamos el Controlador de Login
        LoginController controladorLogin = new LoginController(vistaLogin, escenarioPrincipal);

        // 3. Creamos la escena inicial (más pequeña para un login)
        Scene escena = new Scene(vistaLogin.getRaiz(), 400, 350);

        escenarioPrincipal.setTitle("Sistema Médico - Iniciar Sesión");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}

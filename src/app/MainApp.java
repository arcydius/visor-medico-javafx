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
        LoginView vistaLogin = new LoginView();

        LoginController controladorLogin = new LoginController(vistaLogin, escenarioPrincipal);

        Scene escena = new Scene(vistaLogin.getRaiz(), 600, 600);

        escenarioPrincipal.setTitle("Sistema Médico - Iniciar Sesión");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}

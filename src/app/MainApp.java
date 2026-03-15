package app;

import controller.VisorController;
import view.VisorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{
    @Override
    public void start(Stage escenarioPrincipal) {
        // 1. Instanciamos el diseño (La Vista)
        VisorView vista = new VisorView();

        // 2. Instanciamos el cerebro (El Controlador), pasándole la vista
        VisorController controlador = new VisorController(vista, escenarioPrincipal);

        // 3. Creamos la escena obteniendo el panel raíz (BorderPane) desde la vista
        Scene escena = new Scene(vista.getRaiz(), 800, 600);

        escenarioPrincipal.setTitle("Visor Médico de Cortes Tomográficos (MVC)");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import javafx.scene.image.ImageView;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class VisorMedicoApp extends Application {
    private List<Image> listaImagenes = new ArrayList<>();
    private ImageView vistaCentral = new ImageView();

    @Override
    public void start(Stage escenarioPrincipal) {
        BorderPane raiz = new BorderPane();

        Label lblEstado = new Label("Estado: Esperando directorio");
        raiz.setBottom(lblEstado);

        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setStyle("-fx-padding: 15px; -fx-background-color: #f4f4f4;");

        Button btnCargar = new Button("Cargar Directorio");
        panelIzquierdo.getChildren().add(btnCargar);

        raiz.setLeft(panelIzquierdo);

        btnCargar.setOnAction(evento -> {
            DirectoryChooser selectorDirectorio = new DirectoryChooser();
            selectorDirectorio.setTitle("Selecciona la carpeta con los cortes tomográficos");

            File carpetaSeleccionada = selectorDirectorio.showDialog(escenarioPrincipal);
            //File carpetaSeleccionada = selectorDirectorio.showDialog(null);
            if (carpetaSeleccionada != null) {
                lblEstado.setText("Carpeta seleccionada " + carpetaSeleccionada.getAbsolutePath());
                lblEstado.setText("Cargando imágenes...");
                cargarImagenesDesdeDirectorio(carpetaSeleccionada, lblEstado);
            } else {
                lblEstado.setText("Operación cancelada.");
            }

        });

        vistaCentral.setPreserveRatio(true);
        vistaCentral.setFitWidth(600);
        vistaCentral.setFitHeight(600);

        raiz.setCenter(vistaCentral);

        Scene escena = new Scene(raiz, 800, 600);

        escenarioPrincipal.setTitle("Visor Médico de Cortes Tomográficos");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();

    }
    private void cargarImagenesDesdeDirectorio(File directorio, Label lblEstado) {
        try (Stream<Path> rutas = Files.list(directorio.toPath())) {

            listaImagenes = rutas
                    .filter(Files::isRegularFile)
                    .filter(ruta -> {
                        String nombre = ruta.toString().toLowerCase();
                        return nombre.endsWith(".jpg") || nombre.endsWith(".png");
                    })
                    .sorted()
                    .map(ruta -> new Image(ruta.toUri().toString()))
                    .collect(Collectors.toList());

            if (listaImagenes.isEmpty()) {
                lblEstado.setText("No se encontraron imágenes (.jpg o .png) en la carpeta.");
            } else {
                lblEstado.setText("Éxito: Se cargaron " + listaImagenes.size() + " cortes.");
                vistaCentral.setImage(listaImagenes.get(0));
            }

        } catch (Exception e) {
            lblEstado.setText("Error al leer la carpeta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

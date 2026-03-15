package controller;

import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import view.VisorView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VisorController {

    private VisorView vista;
    private Stage escenarioPrincipal;
    private List<Image> listaImagenes = new ArrayList<>();

    // El constructor une la vista con el controlador
    public VisorController(VisorView vista, Stage escenarioPrincipal) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Usamos el "Getter" de la vista para asignarle la acción Lambda al botón
        vista.getBtnCargar().setOnAction(evento -> {
            DirectoryChooser selectorDirectorio = new DirectoryChooser();
            selectorDirectorio.setTitle("Selecciona la carpeta con los cortes tomográficos");

            File carpetaSeleccionada = selectorDirectorio.showDialog(escenarioPrincipal);

            if (carpetaSeleccionada != null) {
                vista.getLblEstado().setText("Cargando imágenes...");
                cargarImagenesDesdeDirectorio(carpetaSeleccionada);
            } else {
                vista.getLblEstado().setText("Operación cancelada.");
            }
        });
    }

    // Nuestro motor de Streams intacto, pero actualizando los componentes a través de "vista.get..."
    private void cargarImagenesDesdeDirectorio(File directorio) {
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
                vista.getLblEstado().setText("No se encontraron imágenes (.jpg o .png).");
            } else {
                vista.getLblEstado().setText("Éxito: Se cargaron " + listaImagenes.size() + " cortes.");
                vista.getVistaCentral().setImage(listaImagenes.get(0));
            }

        } catch (Exception e) {
            vista.getLblEstado().setText("Error al leer la carpeta: " + e.getMessage());
        }
    }
}

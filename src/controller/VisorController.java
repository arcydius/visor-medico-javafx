package controller;

import model.Usuario;
import view.MPRView;
import view.MedicoView;
import controller.MedicoController;
import javafx.scene.Scene;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import view.ReporteView;
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
    private Usuario medicoActual;
    private File directorioActual;
    private List<Image> listaImagenes = new ArrayList<>();
    private int indiceActual = 0;
    private double inicioX;
    private double inicioY;

    // El constructor une la vista con el controlador
    public VisorController(VisorView vista, Stage escenarioPrincipal, Usuario medicoActual) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        this.medicoActual = medicoActual;
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
        vista.getVistaCentral().setOnScroll(evento -> {
            // Si la lista está vacía, no hacemos nada
            if (listaImagenes.isEmpty()) return;

            // getDeltaY() nos dice la dirección: positivo es hacia arriba, negativo hacia abajo
            double movimientoY = evento.getDeltaY();

            if (movimientoY < 0) {
                // Rueda hacia arriba: Avanzamos (profundizamos)
                indiceActual++;
            } else if (movimientoY > 0) {
                // Rueda hacia abajo: Retrocedemos (salimos)
                indiceActual--;
            }

            // Validaciones de seguridad (Límites) para que el programa no colapse
            if (indiceActual < 0) {
                indiceActual = 0; // Topamos con el primer corte
            } else if (indiceActual >= listaImagenes.size()) {
                indiceActual = listaImagenes.size() - 1; // Topamos con el último corte
            }

            // Actualizamos la imagen mostrada
            //vista.getVistaCentral().setImage(listaImagenes.get(indiceActual));

            //Inicializamos los filtros
            aplicarFiltros();

            // Opcional: Mostramos en qué corte estamos en la barra inferior
            vista.getLblEstado().setText("Corte: " + (indiceActual + 1) + " / " + listaImagenes.size());
        });
        vista.getSliderBrillo().valueProperty().addListener((
                observable, valorViejo, valorNuevo) -> {aplicarFiltros();
        });

        vista.getSliderContraste().valueProperty().addListener((
                observable, valorViejo, valorNuevo) -> {aplicarFiltros();
        });

        // --- HERRAMIENTA DE MEDICIÓN ---

        // 1. Cuando el médico hace CLIC
        vista.getVistaCentral().setOnMousePressed(evento -> {
            // Guardamos las coordenadas exactas del clic (Punto A)
            inicioX = evento.getX();
            inicioY = evento.getY();

            // Colocamos el inicio y el fin de la línea en este punto
            vista.getLineaMedicion().setStartX(inicioX);
            vista.getLineaMedicion().setStartY(inicioY);
            vista.getLineaMedicion().setEndX(inicioX);
            vista.getLineaMedicion().setEndY(inicioY);

            // Hacemos visible la línea roja
            vista.getLineaMedicion().setVisible(true);
        });

        // 2. Cuando el médico ARRASTRA el ratón
        vista.getVistaCentral().setOnMouseDragged(evento -> {
            // Obtenemos las coordenadas actuales del cursor (Punto B)
            double actualX = evento.getX();
            double actualY = evento.getY();

            // Hacemos que la punta de la línea siga al cursor
            vista.getLineaMedicion().setEndX(actualX);
            vista.getLineaMedicion().setEndY(actualY);

            // Calculamos los catetos del triángulo
            double catetoX = actualX - inicioX;
            double catetoY = actualY - inicioY;

            // Aplicamos Pitágoras para obtener la distancia en píxeles
            double distanciaPixeles = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));

            // Convertimos píxeles a milímetros (Simulación: 1 px = 0.5 mm)
            double distanciaMilimetros = distanciaPixeles * 0.5;

            // Mostramos el resultado en la etiqueta con 2 decimales
            vista.getLblDistancia().setText(String.format("Distancia: %.2f mm", distanciaMilimetros));
        });

        // --- BOTÓN VOLVER AL DASHBOARD MÉDICO ---
        vista.getBtnVolver().setOnAction(evento -> {
            // Volvemos a crear la vista del panel médico
            MedicoView medicoVista = new MedicoView();

            // Le pasamos el escenario y nuestro usuario para no perder la sesión
            MedicoController medicoControlador = new MedicoController(medicoVista, escenarioPrincipal, medicoActual);

            // Cambiamos la escena
            escenarioPrincipal.getScene().setRoot(medicoVista.getRaiz());
            escenarioPrincipal.setTitle("Panel Médico - " + medicoActual.getNombreCompleto());
        });

        // --- MÓDULO MPR  ---
        vista.getBtnMPR().setOnAction(evento -> {
            if (directorioActual == null) {
                vista.getLblEstado().setText("Error: Primero debe cargar una tomografía.");
                return;
            }

            // Instanciamos la nueva vista y controlador MPR
            MPRView mprVista = new MPRView();
            MPRController mprControlador = new MPRController(mprVista, escenarioPrincipal, medicoActual, directorioActual);

            // Le damos una ventana más grande para que quepan las 3 pantallas
            escenarioPrincipal.getScene().setRoot(mprVista.getRaiz());
            escenarioPrincipal.setTitle("Visor Avanzado MPR 3D - " + medicoActual.getNombreCompleto());
        });

        // --- ABRIR FORMULARIO DE REPORTE ---
        vista.getBtnReporte().setOnAction(evento -> {
            ReporteView reporteVista = new ReporteView();
            ReporteController reporteControlador = new ReporteController(reporteVista, escenarioPrincipal, medicoActual, directorioActual);

            escenarioPrincipal.getScene().setRoot(reporteVista.getRaiz());
            escenarioPrincipal.setTitle("Redacción de Reporte - " + medicoActual.getNombreCompleto());
        });
    }

    public void cargarImagenesDesdeDirectorio(File directorio) {
        this.directorioActual = directorio;
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
                indiceActual = 0;
                vista.getLblEstado().setText("Éxito: Se cargaron " + listaImagenes.size() + " cortes.");

                //vista.getVistaCentral().setImage(listaImagenes.get(0));

                aplicarFiltros();
            }

        } catch (Exception e) {
            vista.getLblEstado().setText("Error al leer la carpeta: " + e.getMessage());
        }
    }

    // Método matemático para mantener el color entre 0.0 y 1.0
    private double limitarColor(double valor) {
        if (valor < 0.0) return 0.0;
        if (valor > 1.0) return 1.0;
        return valor;
    }

    // El motor de procesamiento
    private void aplicarFiltros() {
        if (listaImagenes.isEmpty()) return;

        Image imagenOriginal = listaImagenes.get(indiceActual);
        int ancho = (int) imagenOriginal.getWidth();
        int alto = (int) imagenOriginal.getHeight();

        // Creamos un lienzo en blanco del mismo tamaño
        WritableImage imagenModificada = new WritableImage(ancho, alto);
        PixelReader lector = imagenOriginal.getPixelReader();
        PixelWriter escritor = imagenModificada.getPixelWriter();

        double brillo = vista.getSliderBrillo().getValue();
        double contraste = vista.getSliderContraste().getValue();

        // Recorremos cada píxel de la imagen (Matriz 2D)
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Color colorViejo = lector.getColor(x, y);

                // Aplicamos la fórmula matemática a los canales RGB
                double r = limitarColor((colorViejo.getRed() - 0.5) * contraste + 0.5 + brillo);
                double g = limitarColor((colorViejo.getGreen() - 0.5) * contraste + 0.5 + brillo);
                double b = limitarColor((colorViejo.getBlue() - 0.5) * contraste + 0.5 + brillo);

                // Pintamos el nuevo píxel en la imagen modificada
                escritor.setColor(x, y, new Color(r, g, b, colorViejo.getOpacity()));
            }
        }

        // Mostramos la imagen ya procesada
        vista.getVistaCentral().setImage(imagenModificada);

        //Proceso de línea
        vista.getLineaMedicion().setVisible(false);
        vista.getLblDistancia().setText("Distancia: 0.0 mm");
    }
}

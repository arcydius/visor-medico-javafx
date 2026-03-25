package controller;

import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Usuario;
import model.VolumenTomografico;
import view.MPRView;
import view.VisorView;

import java.io.File;

public class MPRController {

    private MPRView vista;
    private Stage escenarioPrincipal;
    private Usuario medicoActual;
    private File directorioActual;
    private VolumenTomografico volumen;

    public MPRController(MPRView vista, Stage escenarioPrincipal, Usuario medicoActual, File directorio) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        this.medicoActual = medicoActual;
        this.directorioActual = directorio;

        this.volumen = new VolumenTomografico();

        // Si logra cargar el cubo de imágenes en RAM, iniciamos la magia
        if (volumen.cargarDesdeDirectorio(directorio)) {
            configurarNavegacion();
            inicializarEventos();
        } else {
            System.out.println("Error: No se pudieron cargar las imágenes para el MPR.");
        }
    }

    private void configurarNavegacion() {
        // Configuramos los límites de los sliders basados en el tamaño del cubo 3D
        // Slider Axial (Z - Arriba/Abajo)
        vista.getSliderAxial().setMin(0);
        vista.getSliderAxial().setMax(volumen.getProfundidad() - 1);
        vista.getSliderAxial().setValue(volumen.getProfundidad() / 2); // Empezar a la mitad

        // Slider Coronal (Y - Frente/Atrás)
        vista.getSliderCoronal().setMin(0);
        vista.getSliderCoronal().setMax(volumen.getAlto() - 1);
        vista.getSliderCoronal().setValue(volumen.getAlto() / 2);

        // Slider Sagital (X - Izquierda/Derecha)
        vista.getSliderSagital().setMin(0);
        vista.getSliderSagital().setMax(volumen.getAncho() - 1);
        vista.getSliderSagital().setValue(volumen.getAncho() / 2);

        // Dibujamos los primeros cortes iniciales
        actualizarAxial((int) vista.getSliderAxial().getValue());
        actualizarCoronal((int) vista.getSliderCoronal().getValue());
        actualizarSagital((int) vista.getSliderSagital().getValue());
    }

    private void inicializarEventos() {
        // Escuchadores de los Sliders: Cuando el médico mueve la barra, recalculamos la imagen
        vista.getSliderAxial().valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarAxial(newVal.intValue());
        });

        vista.getSliderCoronal().valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarCoronal(newVal.intValue());
        });

        vista.getSliderSagital().valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarSagital(newVal.intValue());
        });

        // Botón Volver: Regresa al Visor 2D Clásico sin perder la sesión
        vista.getBtnVolver().setOnAction(evento -> {
            VisorView visorVista = new VisorView();
            VisorController visorControlador = new VisorController(visorVista, escenarioPrincipal, medicoActual);
            visorControlador.cargarImagenesDesdeDirectorio(directorioActual); // Recarga la carpeta

            Scene escenaVisor = new Scene(visorVista.getRaiz(), 900, 700);
            escenarioPrincipal.setScene(escenaVisor);
        });
    }

    // --- LOS ALGORITMOS DE CINCELADO MATEMÁTICO ---

    private void actualizarAxial(int z) {
        // El corte Axial es fácil, ya existe en el disco duro. Solo lo pedimos.
        vista.getVistaAxial().setImage(volumen.getCorteAxial(z));
    }

    private void actualizarCoronal(int y) {
        // Corte de Frente. Congelamos la Y. Recorremos X (Ancho) y Z (Profundidad).
        int ancho = volumen.getAncho();
        int profundidad = volumen.getProfundidad();

        // Creamos un lienzo en blanco
        WritableImage imagenNueva = new WritableImage(ancho, profundidad);
        PixelWriter pincel = imagenNueva.getPixelWriter();

        for (int x = 0; x < ancho; x++) {
            for (int z = 0; z < profundidad; z++) {
                // Sacamos el color del cubo tridimensional
                Color colorPixel = volumen.getPixelColor(x, y, z);
                // Pintamos en nuestro lienzo (Z se convierte en el eje vertical de la nueva foto)
                pincel.setColor(x, z, colorPixel);
            }
        }
        vista.getVistaCoronal().setImage(imagenNueva);
    }

    private void actualizarSagital(int x) {
        // Corte de Perfil. Congelamos la X. Recorremos Z (Profundidad) y Y (Alto).
        int alto = volumen.getAlto();
        int profundidad = volumen.getProfundidad();

        WritableImage imagenNueva = new WritableImage(profundidad, alto);
        PixelWriter pincel = imagenNueva.getPixelWriter();

        for (int y = 0; y < alto; y++) {
            for (int z = 0; z < profundidad; z++) {
                Color colorPixel = volumen.getPixelColor(x, y, z);
                // Pintamos (Z es el eje horizontal, Y es el eje vertical)
                pincel.setColor(z, y, colorPixel);
            }
        }
        vista.getVistaSagital().setImage(imagenNueva);
    }
}
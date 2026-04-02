package model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VolumenTomografico {

    private List<Image> cortesAxiales;
    private int ancho;
    private int alto;
    private int profundidad;

    public VolumenTomografico() {
        cortesAxiales = new ArrayList<>();
    }

    public boolean cargarDesdeDirectorio(File directorio) {
        cortesAxiales.clear();

        // Filtramos solo archivos de imagen
        File[] archivos = directorio.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".jpg") ||
                        name.toLowerCase().endsWith(".png") ||
                        name.toLowerCase().endsWith(".jpeg")
        );

        if (archivos == null || archivos.length == 0) return false;

        Arrays.sort(archivos);

        for (File archivo : archivos) {
            // Cargamos la imagen en memoria
            Image img = new Image(archivo.toURI().toString());
            cortesAxiales.add(img);
        }

        ancho = (int) cortesAxiales.get(0).getWidth();
        alto = (int) cortesAxiales.get(0).getHeight();
        profundidad = cortesAxiales.size();

        System.out.println("Volumen 3D cargado en RAM: " + ancho + "x" + alto + " píxeles, " + profundidad + " cortes de profundidad.");
        return true;
    }

    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getProfundidad() { return profundidad; }

    public Image getCorteAxial(int z) {
        if (z >= 0 && z < profundidad) {
            return cortesAxiales.get(z);
        }
        return null;
    }


    public Color getPixelColor(int x, int y, int z) {
        // 1. Validamos que el corte Z exista dentro de nuestra lista
        if (z >= 0 && z < profundidad) {
            Image imagenActual = cortesAxiales.get(z);

            // --- EL ESCUDO DE DIMENSIONES ---
            // 2. Verificamos que las coordenadas X e Y realmente existan dentro de ESTA imagen específica
            if (x >= 0 && x < imagenActual.getWidth() && y >= 0 && y < imagenActual.getHeight()) {

                PixelReader lector = imagenActual.getPixelReader();
                if (lector != null) {
                    return lector.getColor(x, y);
                }
            }
        }
        // Si nos salimos de los límites o la foto es muy pequeña, devolvemos espacio "vacío" (negro)
        return Color.BLACK;
    }
}
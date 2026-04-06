package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Usuario;
import view.ReporteView;
import view.VisorView;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import java.io.FileOutputStream;
import java.util.Date;

import java.io.File;

public class ReporteController {

    private ReporteView vista;
    private Stage escenarioPrincipal;
    private Usuario medicoActual;
    private File directorioActual; // Para no perder la tomografía cargada

    public ReporteController(ReporteView vista, Stage escenarioPrincipal, Usuario medicoActual, File directorioActual) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        this.medicoActual = medicoActual;
        this.directorioActual = directorioActual;

        inicializarEventos();
    }

    private void inicializarEventos() {
        // --- BOTÓN VOLVER ---
        vista.getBtnVolver().setOnAction(evento -> {
            VisorView visorVista = new VisorView();
            VisorController visorControlador = new VisorController(visorVista, escenarioPrincipal, medicoActual);

            if (directorioActual != null) {
                visorControlador.cargarImagenesDesdeDirectorio(directorioActual);
            }

            escenarioPrincipal.getScene().setRoot(visorVista.getRaiz());
            escenarioPrincipal.setTitle("Visor Tomográfico - " + medicoActual.getNombreCompleto());
        });

        vista.getBtnExportarPDF().setOnAction(evento -> {
            generarPDF();
        });
    }

    private void generarPDF() {
        if (vista.getTxtPaciente().getText().isEmpty() || vista.getTxtConclusion().getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos Vacíos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, ingrese al menos el nombre del paciente y la conclusión antes de exportar.");
            alerta.showAndWait();
            return;
        }

        FileChooser selectorArchivo = new FileChooser();
        selectorArchivo.setTitle("Guardar Reporte Médico");
        // Sugerimos un nombre por defecto
        selectorArchivo.setInitialFileName("Reporte_" + vista.getTxtPaciente().getText().replace(" ", "_") + ".pdf");
        selectorArchivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

        File archivoDestino = selectorArchivo.showSaveDialog(escenarioPrincipal);

        if (archivoDestino != null) {
            try {
                // 3. Empezamos a dibujar el PDF con iText
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(archivoDestino));
                documento.open();

                com.itextpdf.text.Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
                Paragraph titulo = new Paragraph("REPORTE RADIOLÓGICO", fuenteTitulo);
                titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                documento.add(titulo);

                documento.add(new Paragraph(" ")); // Espacio en blanco

                com.itextpdf.text.Font fuenteNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                com.itextpdf.text.Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

                documento.add(new Paragraph("Fecha: " + new Date().toString(), fuenteNormal));
                documento.add(new Paragraph("Médico Tratante: " + medicoActual.getNombreCompleto(), fuenteNormal));
                documento.add(new Paragraph("Paciente: " + vista.getTxtPaciente().getText(), fuenteNormal));
                documento.add(new Paragraph("Estudio: " + vista.getTxtEstudio().getText(), fuenteNormal));

                documento.add(new Paragraph(" ")); // Espacio
                documento.add(new Paragraph("---------------------------------------------------------------------------------------------------------"));
                documento.add(new Paragraph(" "));

                documento.add(new Paragraph("HALLAZGOS CLÍNICOS:", fuenteNegrita));
                documento.add(new Paragraph(vista.getTxtHallazgos().getText(), fuenteNormal));

                documento.add(new Paragraph(" "));

                documento.add(new Paragraph("CONCLUSIÓN / DIAGNÓSTICO:", fuenteNegrita));
                documento.add(new Paragraph(vista.getTxtConclusion().getText(), fuenteNormal));

                documento.close();

                Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
                alertaExito.setTitle("Éxito");
                alertaExito.setHeaderText(null);
                alertaExito.setContentText("El reporte se ha exportado correctamente en:\n" + archivoDestino.getAbsolutePath());
                alertaExito.showAndWait();

            } catch (Exception e) {
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error de Exportación");
                alertaError.setHeaderText("No se pudo generar el PDF");
                alertaError.setContentText(e.getMessage());
                alertaError.showAndWait();
            }
        }
    }
}
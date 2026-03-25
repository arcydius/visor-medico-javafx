package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ConexionDB;
import model.Paciente;
import model.Usuario;
import view.LoginView;
import view.MedicoView;
import view.VisorView;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class MedicoController {

    private MedicoView vista;
    private Stage escenarioPrincipal;
    private Usuario medicoActual;

    public MedicoController(MedicoView vista, Stage escenarioPrincipal, Usuario medicoActual) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        this.medicoActual = medicoActual;

        vista.getLblBienvenida().setText("Bienvenido, " + medicoActual.getNombreCompleto());

        cargarPacientesDesdeDB();
        inicializarEventos();
    }

    private void cargarPacientesDesdeDB() {
        ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM pacientes";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("ruta_directorio")
                );
                listaPacientes.add(p);
            }

            FilteredList<Paciente> listaFiltrada = new FilteredList<>(listaPacientes, b -> true);

            vista.getTxtBuscarDni().textProperty().addListener((observable, valorViejo, valorNuevo) -> {
                listaFiltrada.setPredicate(paciente -> {
                    // Si la caja de búsqueda está vacía, mostramos todos los pacientes
                    if (valorNuevo == null || valorNuevo.isEmpty()) {
                        return true;
                    }

                    String filtro = valorNuevo.toLowerCase();

                    if (paciente.getDni().toLowerCase().contains(filtro)) {
                        return true; // ¡Coincide! Lo mostramos en la tabla
                    } else {
                        return false; // No coincide. Lo ocultamos.
                    }
                });
            });

            // 3. Envolvemos la lista filtrada en una lista ordenable (para que las columnas se puedan ordenar por clic)
            SortedList<Paciente> listaOrdenada = new SortedList<>(listaFiltrada);
            listaOrdenada.comparatorProperty().bind(vista.getTablaPacientes().comparatorProperty());

            // 4. Finalmente, metemos la lista super-poderosa en la tabla
            vista.getTablaPacientes().setItems(listaOrdenada);

        } catch (SQLException e) {
            vista.getLblMensaje().setStyle("-fx-text-fill: red;");
            vista.getLblMensaje().setText("Error al cargar pacientes: " + e.getMessage());
        }
    }

    private void inicializarEventos() {
        // --- CERRAR SESIÓN ---
        vista.getBtnCerrarSesion().setOnAction(evento -> {
            LoginView vistaLogin = new LoginView();
            LoginController controladorLogin = new LoginController(vistaLogin, escenarioPrincipal);
            Scene escenaLogin = new Scene(vistaLogin.getRaiz(), 400, 350);
            escenarioPrincipal.setScene(escenaLogin);
            escenarioPrincipal.setTitle("Sistema Médico - Iniciar Sesión");
        });

        // --- ABRIR VISOR ---
        vista.getBtnAbrirVisor().setOnAction(evento -> {
            Paciente pacienteSeleccionado = vista.getTablaPacientes().getSelectionModel().getSelectedItem();

            if (pacienteSeleccionado == null) {
                vista.getLblMensaje().setStyle("-fx-text-fill: red;");
                vista.getLblMensaje().setText("Por favor, seleccione un paciente de la tabla primero.");
                return;
            }

            File carpetaTomografia = new File(pacienteSeleccionado.getRutaDirectorio());

            if (!carpetaTomografia.exists() || !carpetaTomografia.isDirectory()) {
                vista.getLblMensaje().setStyle("-fx-text-fill: red;");
                vista.getLblMensaje().setText("Error: La carpeta de imágenes de este paciente no existe o fue movida.");
                return;
            }

            VisorView visorVista = new VisorView();
            VisorController visorControlador = new VisorController(visorVista, escenarioPrincipal, medicoActual);

            visorControlador.cargarImagenesDesdeDirectorio(carpetaTomografia);

            Scene escenaVisor = new Scene(visorVista.getRaiz(), 900, 700);
            escenarioPrincipal.setScene(escenaVisor);
            escenarioPrincipal.setTitle("Visor 3D - Paciente: " + pacienteSeleccionado.getNombreCompleto());
        });
    }
}
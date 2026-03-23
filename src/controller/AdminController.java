package controller;

import javafx.stage.DirectoryChooser;
import java.io.File;
import java.time.LocalDate;

import model.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Usuario;
import view.AdminView;
import view.LoginView;

public class AdminController {

    private AdminView vista;
    private Stage escenarioPrincipal;
    private Usuario adminActual;

    // Fíjate que el constructor ahora recibe al Usuario que se logueó
    public AdminController(AdminView vista, Stage escenarioPrincipal, Usuario adminActual) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        this.adminActual = adminActual;

        inicializarDatos();
        inicializarEventos();
    }

    private void inicializarDatos() {
        // Mostramos el nombre del administrador en el centro de la pantalla
        vista.getLblBienvenida().setText("Bienvenido, " + adminActual.getNombreCompleto());
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

        // --- MOSTRAR FORMULARIO DE MÉDICO ---
        vista.getBtnRegistrarMedico().setOnAction(evento -> {
            // Limpiamos el panel central y metemos el formulario
            vista.getPanelCentral().getChildren().clear();
            vista.getPanelCentral().getChildren().add(vista.getFormularioMedico());
            vista.getLblMensajeFormulario().setText(""); // Limpiar errores viejos
        });

        // --- MOSTRAR FORMULARIO DE PACIENTE ---
        vista.getBtnRegistrarPaciente().setOnAction(evento -> {
            vista.getPanelCentral().getChildren().clear();
            vista.getPanelCentral().getChildren().add(vista.getFormularioPaciente());
            vista.getLblMensajePaciente().setText("");
        });

        // --- SELECCIONAR CARPETA DE IMÁGENES ---
        vista.getBtnSeleccionarCarpeta().setOnAction(evento -> {
            DirectoryChooser selector = new DirectoryChooser();
            selector.setTitle("Seleccione la carpeta con las tomografías del paciente");

            File carpeta = selector.showDialog(escenarioPrincipal);
            if (carpeta != null) {
                // Guardamos la ruta absoluta en el Label para usarla al guardar
                vista.getLblRutaCarpeta().setText(carpeta.getAbsolutePath());
                vista.getLblRutaCarpeta().setStyle("-fx-text-fill: black;");
            }
        });

        // --- GUARDAR PACIENTE EN BASE DE DATOS ---
        vista.getBtnGuardarPaciente().setOnAction(evento -> guardarPacienteDB());

        // --- MOSTRAR / OCULTAR CONTRASEÑA ---
        vista.getChkMostrarClave().setOnAction(evento -> {
            boolean mostrar = vista.getChkMostrarClave().isSelected(); // ¿Está marcado?

            // Alternamos la visibilidad
            vista.getTxtPassMedicoVisible().setVisible(mostrar);
            vista.getTxtPassMedicoVisible().setManaged(mostrar);

            vista.getTxtPassMedico().setVisible(!mostrar);
            vista.getTxtPassMedico().setManaged(!mostrar);
        });

        // --- GUARDAR EN BASE DE DATOS ---
        vista.getBtnGuardarMedico().setOnAction(evento -> guardarMedicoDB());
    }
    // Nuevo método para interactuar con MySQL
    private void guardarMedicoDB() {
        String nombre = vista.getTxtNombreMedico().getText();
        String usuario = vista.getTxtUserMedico().getText();
        String clave = vista.getTxtPassMedico().getText();
        String especialidad = vista.getCbEspecialidad().getValue();

        // 1. Validación de seguridad: Que no haya campos vacíos
        if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty() || especialidad == null) {
            vista.getLblMensajeFormulario().setStyle("-fx-text-fill: red;");
            vista.getLblMensajeFormulario().setText("Por favor, complete todos los campos.");
            return;
        }

        // 2. Consulta SQL usando PreparedStatement (Seguridad contra Hackeos)
        String sql = "INSERT INTO medicos (usuario, clave, nombre_completo, especialidad) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, clave);
            pstmt.setString(3, nombre);
            pstmt.setString(4, especialidad);

            int filasAfectadas = pstmt.executeUpdate(); // Ejecutamos la inserción

            if (filasAfectadas > 0) {
                vista.getLblMensajeFormulario().setStyle("-fx-text-fill: green;");
                vista.getLblMensajeFormulario().setText("¡Médico registrado con éxito!");

                // Limpiamos los campos para que pueda registrar a otro
                vista.getTxtNombreMedico().clear();
                vista.getTxtUserMedico().clear();
                vista.getTxtPassMedico().clear();
                vista.getCbEspecialidad().setValue(null);
            }

        } catch (SQLException e) {
            vista.getLblMensajeFormulario().setStyle("-fx-text-fill: red;");

            // Si intenta meter un usuario que ya existe en la base de datos (Porque lo pusimos UNIQUE)
            if (e.getMessage().contains("Duplicate entry")) {
                vista.getLblMensajeFormulario().setText("Error: El nombre de usuario ya está en uso.");
            } else {
                vista.getLblMensajeFormulario().setText("Error en la base de datos: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }

    private void guardarPacienteDB() {
        String dni = vista.getTxtDniPaciente().getText().trim();
        String nombre = vista.getTxtNombrePaciente().getText().trim();
        LocalDate fechaNacimiento = vista.getDpFechaNacimiento().getValue();
        String ruta = vista.getLblRutaCarpeta().getText();

        // 1. Validar campos vacíos
        if (dni.isEmpty() || nombre.isEmpty() || fechaNacimiento == null || ruta.equals("Ninguna carpeta seleccionada")) {
            vista.getLblMensajePaciente().setStyle("-fx-text-fill: red;");
            vista.getLblMensajePaciente().setText("Por favor, complete todos los campos y seleccione una carpeta.");
            return;
        }

        // 2. Validación lógica: La fecha no puede ser en el futuro
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            vista.getLblMensajePaciente().setStyle("-fx-text-fill: red;");
            vista.getLblMensajePaciente().setText("Error: La fecha de nacimiento no puede ser en el futuro.");
            return;
        }

        // 3. Guardar en MySQL (Nota que cambiamos la consulta SQL)
        String sql = "INSERT INTO pacientes (dni, nombre_completo, fecha_nacimiento, ruta_directorio) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            // Convertimos el LocalDate de Java 8 al Date de SQL
            pstmt.setDate(3, java.sql.Date.valueOf(fechaNacimiento));
            pstmt.setString(4, ruta);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                vista.getLblMensajePaciente().setStyle("-fx-text-fill: green;");
                vista.getLblMensajePaciente().setText("¡Paciente registrado con éxito!");

                // Limpiar formulario
                vista.getTxtDniPaciente().clear();
                vista.getTxtNombrePaciente().clear();
                vista.getDpFechaNacimiento().setValue(null);
                vista.getLblRutaCarpeta().setText("Ninguna carpeta seleccionada");
                vista.getLblRutaCarpeta().setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            }

        } catch (SQLException e) {
            vista.getLblMensajePaciente().setStyle("-fx-text-fill: red;");
            if (e.getMessage().contains("Duplicate entry")) {
                vista.getLblMensajePaciente().setText("Error: Ya existe un paciente con este DNI.");
            } else {
                vista.getLblMensajePaciente().setText("Error DB: " + e.getMessage());
            }
        }
    }
}
package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ConexionDB;

import model.Usuario;
import view.AdminView;
import view.LoginView;
import view.MedicoView;
import view.VisorView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private LoginView vista;
    private Stage escenarioPrincipal;

    public LoginController(LoginView vista, Stage escenarioPrincipal) {
        this.vista = vista;
        this.escenarioPrincipal = escenarioPrincipal;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnIngresar().setOnAction(evento -> {
            String usuarioIngresado = vista.getTxtUsuario().getText();
            String claveIngresada = vista.getTxtPassword().getText();

            if (usuarioIngresado.isEmpty() || claveIngresada.isEmpty()) {
                vista.getLblMensaje().setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 18px;");
                vista.getLblMensaje().setText("Por favor, llene todos los campos.");
                return;
            }

            autenticarUsuario(usuarioIngresado, claveIngresada);
        });

        vista.getBtnSalir().setOnAction(evento -> {
            javafx.application.Platform.exit();
        });
    }

    private void autenticarUsuario(String usuarioStr, String claveStr) {
        try (Connection conn = ConexionDB.conectar()) {

            if (conn == null) {
                vista.getLblMensaje().setStyle("-fx-text-fill: #ffcccc; -fx-font-weight: bold; -fx-font-size: 15px;");
                vista.getLblMensaje().setText("Servidor apagado. Por favor, enciende MySQL en XAMPP.");
                return;
            }

            // 1. Buscamos PRIMERO en la tabla de administradores
            String sqlAdmin = "SELECT * FROM administradores WHERE usuario = ? AND clave = ?";
            try (PreparedStatement pstmtAdmin = conn.prepareStatement(sqlAdmin)) {
                pstmtAdmin.setString(1, usuarioStr);
                pstmtAdmin.setString(2, claveStr);
                ResultSet rsAdmin = pstmtAdmin.executeQuery();

                if (rsAdmin.next()) {
                    // ¡Encontrado! Creamos el objeto y le asignamos el rol ADMIN
                    Usuario adminLogueado = new Usuario(
                            rsAdmin.getInt("id"),
                            rsAdmin.getString("usuario"),
                            rsAdmin.getString("nombre_completo"),
                            "ADMIN"
                    );
                    redirigir(adminLogueado);
                    return; // Salimos del método para no buscar en médicos
                }
            }

            // 2. Si no era admin, buscamos SEGUNDO en la tabla de médicos
            String sqlMedico = "SELECT * FROM medicos WHERE usuario = ? AND clave = ?";
            try (PreparedStatement pstmtMedico = conn.prepareStatement(sqlMedico)) {
                pstmtMedico.setString(1, usuarioStr);
                pstmtMedico.setString(2, claveStr);
                ResultSet rsMedico = pstmtMedico.executeQuery();

                if (rsMedico.next()) {
                    // ¡Encontrado! Creamos el objeto y le asignamos el rol MÉDICO
                    Usuario medicoLogueado = new Usuario(
                            rsMedico.getInt("id"),
                            rsMedico.getString("usuario"),
                            rsMedico.getString("nombre_completo"),
                            "MEDICO"
                    );
                    redirigir(medicoLogueado);
                    return; // Salimos del método
                }
            }

            // 3. Si el código llega hasta aquí, es porque no estaba en ninguna tabla
            vista.getLblMensaje().setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 18px;");
            vista.getLblMensaje().setText("Usuario o clave incorrectos.");

        } catch (SQLException e) {
            vista.getLblMensaje().setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 18px;");
            vista.getLblMensaje().setText("Error de conexión con la base de datos.");
            e.printStackTrace();
        }
    }

    private void redirigir(Usuario usuario) {
        vista.getLblMensaje().setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-font-size: 18px;;");
        vista.getLblMensaje().setText("¡Acceso concedido! Bienvenido " + usuario.getNombreCompleto());

        if (usuario.getRol().equals("MEDICO")) {
            // --- Abrimos el Dashboard del Médico ---
            MedicoView medicoVista = new MedicoView();
            MedicoController medicoControlador = new MedicoController(medicoVista, escenarioPrincipal, usuario);

            Scene escenaMedico = new Scene(medicoVista.getRaiz(), 800, 600);
            escenarioPrincipal.setScene(escenaMedico);
            escenarioPrincipal.setTitle("Panel Médico - " + usuario.getNombreCompleto());

        } else if (usuario.getRol().equals("ADMIN")) {

            // --- Redirigimos al Dashboard del Administrador ---
            AdminView adminVista = new AdminView();

            // Le pasamos la vista, el escenario y el usuario que acaba de iniciar sesión
            AdminController adminControlador = new AdminController(adminVista, escenarioPrincipal, usuario);

            // Hacemos la ventana más grande para el panel de control
            Scene escenaAdmin = new Scene(adminVista.getRaiz(), 900, 600);
            escenarioPrincipal.setScene(escenaAdmin);
            escenarioPrincipal.setTitle("Panel de Administración - " + usuario.getNombreCompleto());
        }
    }
}
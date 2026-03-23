package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Cambia estos valores si configuraste algo distinto en tu XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/db_visor_medico";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("¡Conexión exitosa a la Base de Datos!");
            return conexion;

        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de MySQL. Revisa tus librerías.");
            return null;
        } catch (SQLException e) {
            System.err.println("Error de conexión SQL: " + e.getMessage());
            return null;
        }
    }
}
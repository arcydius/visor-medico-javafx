package model;

import java.time.LocalDate;

public class Paciente {
    private int id;
    private String dni;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String rutaDirectorio;

    public Paciente(int id, String dni, String nombreCompleto, LocalDate fechaNacimiento, String rutaDirectorio) {
        this.id = id;
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.rutaDirectorio = rutaDirectorio;
    }

    // --- GETTERS ---
    public int getId() { return id; }
    public String getDni() { return dni; }
    public String getNombreCompleto() { return nombreCompleto; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getRutaDirectorio() { return rutaDirectorio; }

    // Un método útil para mostrar la edad calculada al vuelo si la necesitamos
    public int getEdadCalculada() {
        if (fechaNacimiento != null) {
            return LocalDate.now().getYear() - fechaNacimiento.getYear();
        }
        return 0;
    }
}
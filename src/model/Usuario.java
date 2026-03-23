package model;

public class Usuario {
    private int id;
    private String usuario;
    private String nombreCompleto;
    private String rol; // Lo asignaremos manualmente en el controlador

    public Usuario(int id, String usuario, String nombreCompleto, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    // Getters
    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getRol() { return rol; }
}
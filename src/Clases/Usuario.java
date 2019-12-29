package Clases;

public class Usuario {
    private String nombre;
    private String apellido;
    private int carne;
    private String password;

    public Usuario(String nombre, String apellido, int carne, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.carne = carne;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getCarne() {
        return carne;
    }

    public void setCarne(int carne) {
        this.carne = carne;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

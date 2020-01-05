package Clases;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
    private String nombre;
    private String apellido;
    private int carne;
    private byte[] password;

    public Usuario(String nombre, String apellido, int carne, String password) throws NoSuchAlgorithmException {
        this.nombre = nombre;
        this.apellido = apellido;
        this.carne = carne;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        this.password = digest.digest(password.getBytes(StandardCharsets.UTF_8));
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

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        this.password = digest.digest(password.getBytes(StandardCharsets.UTF_8));;
    }
}

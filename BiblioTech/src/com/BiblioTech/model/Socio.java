package com.BiblioTech.model;

public abstract class Socio {
    private final String dni;
    private final String nombre;
    private final String apellido;
    private final String email;

    public Socio(String dni, String nombre, String apellido, String email) {
        validarEmail(email);
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    private void validarEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }

    public abstract int getLimitePrestamos();

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (DNI: " + dni + ")";
    }
}
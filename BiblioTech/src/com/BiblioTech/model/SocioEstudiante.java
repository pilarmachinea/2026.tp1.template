package com.BiblioTech.model;

public class SocioEstudiante extends Socio {

    private static final int LIMITE_PRESTAMOS = 3;

    public SocioEstudiante(String dni, String nombre, String apellido, String email) {
        super(dni, nombre, apellido, email);
    }

    @Override
    public int getLimitePrestamos() {
        return LIMITE_PRESTAMOS;
    }

    @Override
    public String toString() {
        return "[Estudiante] " + super.toString();
    }
}
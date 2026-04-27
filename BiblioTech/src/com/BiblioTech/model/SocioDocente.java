package com.BiblioTech.model;

public class SocioDocente extends Socio {

    private static final int LIMITE_PRESTAMOS = 5;

    public SocioDocente(String dni, String nombre, String apellido, String email) {
        super(dni, nombre, apellido, email);
    }

    @Override
    public int getLimitePrestamos() {
        return LIMITE_PRESTAMOS;
    }

    @Override
    public String toString() {
        return "[Docente] " + super.toString();
    }
}
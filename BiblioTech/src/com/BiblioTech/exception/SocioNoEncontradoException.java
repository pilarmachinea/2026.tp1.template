package com.BiblioTech.exception;

public class SocioNoEncontradoException extends BibliotecaException {
    public SocioNoEncontradoException(String dni) {
        super("No se encontró ningún socio con DNI: " + dni);
    }
}
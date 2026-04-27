package com.BiblioTech.exception;

public class LimitePrestamosAlcanzadoException extends BibliotecaException {
    public LimitePrestamosAlcanzadoException(String dni, int limite) {
        super("El socio con DNI " + dni + " alcanzó su límite de " + limite + " préstamos.");
    }
}
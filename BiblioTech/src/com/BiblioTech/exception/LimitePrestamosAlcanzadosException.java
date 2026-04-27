package com.BiblioTech.exception;

public class LimitePrestamosAlcanzadosException extends BibliotecaException {
    public LimitePrestamosAlcanzadosException(String dni, int limite) {
        super("El socio con DNI " + dni + " alcanzó su límite de " + limite + " préstamos.");
    }
}
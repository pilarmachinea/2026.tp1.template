package com.BiblioTech.exception;

public class LibroNoEncontradoException extends BibliotecaException {
    public LibroNoEncontradoException(String isbn) {
        super("No se encontró ningún libro con ISBN: " + isbn);
    }
}
package com.BiblioTech.service;

import com.BiblioTech.exception.BibliotecaException;
import com.BiblioTech.model.Prestamo;
import java.util.List;

public interface PrestamoService {
    void realizarPrestamo(String isbn, String dni) throws BibliotecaException;
    void registrarDevolucion(String isbn, String dni) throws BibliotecaException;
    List<Prestamo> obtenerHistorial();
    List<Prestamo> obtenerPrestamosActivosPorSocio(String dni);
}
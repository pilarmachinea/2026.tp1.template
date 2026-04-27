package com.BiblioTech.service;

import com.BiblioTech.exception.*;
import com.BiblioTech.model.Prestamo;
import com.BiblioTech.model.Recurso;
import com.BiblioTech.model.Socio;
import com.BiblioTech.repository.PrestamoRepositoryImpl;
import com.BiblioTech.repository.Repository;
import java.util.List;
import java.util.stream.Collectors;

public class PrestamoServiceImpl implements PrestamoService {

    private final Repository<Recurso, String> libroRepository;
    private final Repository<Socio, String> socioRepository;
    private final PrestamoRepositoryImpl prestamoRepository;

    public PrestamoServiceImpl(
            Repository<Recurso, String> libroRepository,
            Repository<Socio, String> socioRepository,
            PrestamoRepositoryImpl prestamoRepository) {
        this.libroRepository = libroRepository;
        this.socioRepository = socioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public void realizarPrestamo(String isbn, String dni) throws BibliotecaException {
        Recurso recurso = libroRepository.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoEncontradoException(isbn));

        Socio socio = socioRepository.buscarPorId(dni)
                .orElseThrow(() -> new SocioNoEncontradoException(dni));

        boolean libroYaPrestado = prestamoRepository.buscarPorId(isbn).isPresent();
        if (libroYaPrestado) {
            throw new LibroNoDisponibleException(isbn);
        }

        long prestamosActivos = obtenerPrestamosActivosPorSocio(dni).size();
        if (prestamosActivos >= socio.getLimitePrestamos()) {
            throw new LimitePrestamosAlcanzadoException(dni, socio.getLimitePrestamos());
        }

        prestamoRepository.guardar(Prestamo.crear(isbn, dni));
        System.out.println("Préstamo registrado: " + recurso.titulo() + " → " + socio);
    }

    @Override
    public void registrarDevolucion(String isbn, String dni) throws BibliotecaException {
        Prestamo prestamo = prestamoRepository.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoEncontradoException(isbn));

        Prestamo devuelto = prestamo.conDevolucion();
        prestamoRepository.actualizarPrestamo(devuelto);

        long diasRetraso = devuelto.calcularDiasRetraso();
        if (diasRetraso > 0) {
            System.out.println("Devolución con " + diasRetraso + " días de retraso.");
        } else {
            System.out.println("Devolución en término. ¡Gracias!");
        }
    }

    @Override
    public List<Prestamo> obtenerHistorial() {
        return prestamoRepository.buscarTodos();
    }

    @Override
    public List<Prestamo> obtenerPrestamosActivosPorSocio(String dni) {
        return prestamoRepository.buscarTodos().stream()
                .filter(p -> p.dniSocio().equals(dni) && !p.estaDevuelto())
                .collect(Collectors.toList());
    }
}
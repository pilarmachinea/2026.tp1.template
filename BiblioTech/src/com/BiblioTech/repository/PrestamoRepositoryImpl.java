package com.BiblioTech.repository;

import com.BiblioTech.model.Prestamo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestamoRepositoryImpl implements Repository<Prestamo, String> {

    private final List<Prestamo> prestamos = new ArrayList<>();

    @Override
    public void guardar(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String isbn) {
        return prestamos.stream()
                .filter(p -> p.isbn().equals(isbn) && !p.estaDevuelto())
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(prestamos);
    }

    public void actualizarPrestamo(Prestamo prestamoActualizado) {
        prestamos.replaceAll(p ->
                p.isbn().equals(prestamoActualizado.isbn()) &&
                        p.dniSocio().equals(prestamoActualizado.dniSocio()) &&
                        !p.estaDevuelto()
                        ? prestamoActualizado
                        : p
        );
    }
}
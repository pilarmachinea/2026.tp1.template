package com.BiblioTech.repository;

import com.BiblioTech.model.Recurso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LibroRepositoryImpl implements Repository<Recurso, String> {

    private final Map<String, Recurso> almacenamiento = new HashMap<>();

    @Override
    public void guardar(Recurso recurso) {
        almacenamiento.put(recurso.isbn(), recurso);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return Optional.ofNullable(almacenamiento.get(isbn));
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(almacenamiento.values());
    }
}
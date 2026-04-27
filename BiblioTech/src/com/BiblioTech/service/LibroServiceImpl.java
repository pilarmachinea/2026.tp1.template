package com.BiblioTech.service;

import com.BiblioTech.model.Categoria;
import com.BiblioTech.model.Recurso;
import com.BiblioTech.repository.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibroServiceImpl implements LibroService {

    private final Repository<Recurso, String> libroRepository;

    public LibroServiceImpl(Repository<Recurso, String> libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public void registrarLibro(Recurso recurso) {
        libroRepository.guardar(recurso);
    }

    @Override
    public Optional<Recurso> buscarPorIsbn(String isbn) {
        return libroRepository.buscarPorId(isbn);
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return libroRepository.buscarTodos().stream()
                .filter(r -> r.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return libroRepository.buscarTodos().stream()
                .filter(r -> r.autor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return libroRepository.buscarTodos().stream()
                .filter(r -> r.categoria() == categoria)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recurso> buscarTodos() {
        return libroRepository.buscarTodos();
    }
}
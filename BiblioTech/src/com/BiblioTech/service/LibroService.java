package com.BiblioTech.service;

import com.BiblioTech.model.Categoria;
import com.BiblioTech.model.Recurso;
import java.util.List;
import java.util.Optional;

public interface LibroService {
    void registrarLibro(Recurso recurso);
    Optional<Recurso> buscarPorIsbn(String isbn);
    List<Recurso> buscarPorTitulo(String titulo);
    List<Recurso> buscarPorAutor(String autor);
    List<Recurso> buscarPorCategoria(Categoria categoria);
    List<Recurso> buscarTodos();
}
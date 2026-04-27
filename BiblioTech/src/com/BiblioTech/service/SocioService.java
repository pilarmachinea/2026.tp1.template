package com.BiblioTech.service;

import com.BiblioTech.model.Socio;
import com.BiblioTech.exception.BibliotecaException;
import java.util.List;
import java.util.Optional;

public interface SocioService {
    void registrarSocio(Socio socio) throws BibliotecaException;
    Optional<Socio> buscarPorDni(String dni);
    List<Socio> buscarTodos();
}
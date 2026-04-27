package com.BiblioTech.service;

import com.BiblioTech.exception.BibliotecaException;
import com.BiblioTech.model.Socio;
import com.BiblioTech.repository.Repository;
import java.util.List;
import java.util.Optional;

public class SocioServiceImpl implements SocioService {

    private final Repository<Socio, String> socioRepository;

    public SocioServiceImpl(Repository<Socio, String> socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    public void registrarSocio(Socio socio) throws BibliotecaException {
        if (socioRepository.buscarPorId(socio.getDni()).isPresent()) {
            throw new BibliotecaException("Ya existe un socio con DNI: " + socio.getDni());
        }
        socioRepository.guardar(socio);
    }

    @Override
    public Optional<Socio> buscarPorDni(String dni) {
        return socioRepository.buscarPorId(dni);
    }

    @Override
    public List<Socio> buscarTodos() {
        return socioRepository.buscarTodos();
    }
}
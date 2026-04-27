package com.BiblioTech.repository;

import com.BiblioTech.model.Socio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SocioRepositoryImpl implements Repository<Socio, String> {

    private final Map<String, Socio> almacenamiento = new HashMap<>();

    @Override
    public void guardar(Socio socio) {
        almacenamiento.put(socio.getDni(), socio);
    }

    @Override
    public Optional<Socio> buscarPorId(String dni) {
        return Optional.ofNullable(almacenamiento.get(dni));
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(almacenamiento.values());
    }
}

package com.cefotec.centroatletas.service;

import java.util.List;
import java.util.Optional;

import com.cefotec.centroatletas.domain.Atleta;

public interface AtletaService {
	
    public void save(Atleta atleta);
    public Optional<Atleta> get(Long id);
    public List<Atleta> getAll();
    public List<Atleta> find(String nombre);
}

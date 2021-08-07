package com.cefotec.centroatletas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefotec.centroatletas.domain.Atleta;
import com.cefotec.centroatletas.repo.AtletaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaServiceImpl implements AtletaService {

	@Autowired
	AtletaRepository repo;
	
	@Override
	public void save(Atleta atleta) {
		repo.save(atleta);
	}

	@Override
	public Optional<Atleta> get(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Atleta> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Atleta> find(String searchCriteria) {
		return repo.findByNombreContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCase(searchCriteria, searchCriteria);
	}

}

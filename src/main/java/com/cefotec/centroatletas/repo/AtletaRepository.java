package com.cefotec.centroatletas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cefotec.centroatletas.domain.Atleta;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {
	public List<Atleta> findByNombreContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCase(String nombre, String apellido);
}
			
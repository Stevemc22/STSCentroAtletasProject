package com.cefotec.centroatletas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefotec.centroatletas.domain.AtletaIMC;
import com.cefotec.centroatletas.repo.AtletaIMCRepository;

@Service
public class AtletaIMCServiceImpl implements AtletaIMCService {

    @Autowired
    AtletaIMCRepository imcRepo;
    
	@Override
	public void save(AtletaIMC imc) {
		// TODO Auto-generated method stub
		imcRepo.save(imc);
	}

}

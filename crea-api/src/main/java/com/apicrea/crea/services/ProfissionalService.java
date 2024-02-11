package com.apicrea.crea.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.repositories.ProfissionalRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository proficioProfissionalRepository;
	
	public void create(Profissional profissional) {
		
		if(proficioProfissionalRepository.existsByEmail(profissional.getEmail())) {
			throw new EntityExistsException("O Profissional com o ID " + profissional.getEmail() + " já existe.");
		}
	}
	
	
	
	
}

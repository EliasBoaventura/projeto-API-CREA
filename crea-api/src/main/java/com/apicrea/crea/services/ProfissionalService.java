package com.apicrea.crea.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.common.responses.dto.ProfissionalDto;
import com.apicrea.crea.repositories.ProfissionalRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository profissionalRepository;

	public ProfissionalResponse create(ProfissionalRequest profissionalRequest) {

		if (profissionalRepository.existsByEmail(profissionalRequest.getEmail())) {
			throw new EntityExistsException(
					"O Profissional com o E-mail " + profissionalRequest.getEmail() + " já existe.");
		}
		saveData(profissionalRequest);

		return new ProfissionalResponse(profissionalRequest);
	}

	public void delete(ProfissionalRequest profissionalRequest) {
		validateData(profissionalRequest);

		ProfissionalDto profissionalDto = new ProfissionalDto(profissionalRequest);

		Profissional profissional = new Profissional(profissionalDto);

		profissionalRepository.delete(profissional);
	}

	public void update() {

	}

	public ProfissionalResponse finbyid(Long id) {

		if (profissionalRepository.findById(id).get() == null) {
			throw new EntityExistsException("Esse Profissional não existe.");
		}

		Profissional profissional = profissionalRepository.findById(id).get();

		return new ProfissionalResponse(profissional);
	}

	private void validateData(ProfissionalRequest profissionalRequest) {

		if (profissionalRepository.existsByEmail(profissionalRequest.getEmail())) {
			throw new EntityExistsException("Esse Profissional  " + profissionalRequest.getNome() + " não existe.");
		}
	}

	private void saveData(ProfissionalRequest profissionalRequest) {

		ProfissionalDto profissionalDto = new ProfissionalDto(profissionalRequest);

		Profissional profissional = new Profissional(profissionalDto);

		profissionalRepository.save(profissional);

	}
}

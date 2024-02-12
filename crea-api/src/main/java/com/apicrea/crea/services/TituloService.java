package com.apicrea.crea.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.requests.TituloRequest;
import com.apicrea.crea.common.responses.TituloResponse;
import com.apicrea.crea.repositories.TituloRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TituloService {

	@Autowired
	private TituloRepository tituloRepository;

	public TituloResponse create(TituloRequest tituloRequest) {
		if (tituloRepository.existsByNome(tituloRequest.getNome())) {
			throw new EntityExistsException("O título ja existe.");
		}

		Titulo titulo = tituloRepository.save(new Titulo(tituloRequest));

		return new TituloResponse(titulo);
	}

	public List<Titulo> findAll() {
		return tituloRepository.findAll();
	}

	public TituloResponse findById(Long id) {
		TituloRequest tituloRequest = new TituloRequest(tituloRepository.findById(id).get());

		return new TituloResponse(tituloRequest);
	}

	public TituloResponse update(TituloRequest tituloRequest) {
		if (tituloRepository.findById(tituloRequest.getId()) == null) {
			throw new EntityNotFoundException("O título não existe.");
		}

		if (tituloRepository.existsByNome(tituloRequest.getNome())) {
			throw new EntityExistsException("O título ja existe.");
		}

		else {
			Titulo titulo = tituloRepository.save(new Titulo(tituloRequest));

			return new TituloResponse(titulo);
		}

	}

	public void deleteById(Long id) {
		if (tituloRepository.findById(id) == null) {
			throw new EntityNotFoundException("O título não existe.");
		}

		tituloRepository.deleteById(id);
	}

}

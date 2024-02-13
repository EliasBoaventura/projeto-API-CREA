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
		if (tituloRepository.existsByDescricao(tituloRequest.getDescricao())) {
			throw new EntityExistsException("O título já existe.");
		}

		Titulo titulo = tituloRepository.save(new Titulo(tituloRequest));

		return new TituloResponse(titulo);
	}

	public List<Titulo> findAll() {
		if (tituloRepository.findAll().isEmpty()) {
			throw new EntityNotFoundException("Não existe títulos cadastrados.");
		}
		return tituloRepository.findAll();
	}

	public TituloResponse findById(Long tituloId) {
		verificaTitulo(tituloId);

		TituloRequest tituloRequest = new TituloRequest(tituloRepository.findById(tituloId).get());

		return new TituloResponse(tituloRequest);
	}

	public TituloResponse update(TituloRequest tituloRequest) {
		verificaTitulo(tituloRequest.getId());

		if (tituloRepository.existsByDescricao(tituloRequest.getDescricao())) {
			throw new EntityExistsException("O título já existe.");
		} else {
			Titulo titulo = tituloRepository.save(new Titulo(tituloRequest));

			return new TituloResponse(titulo);
		}
	}

	public void deleteById(Long tituloId) {
		verificaTitulo(tituloId);
		if (tituloRepository.verificaTituloEmUso(tituloId) > 0) {
			throw new EntityNotFoundException(
					"Este título não pode ser removido, pois está vinculado a um profissional.");
		}

		tituloRepository.deleteById(tituloId);
	}

	private void verificaTitulo(Long tituloId) {
		if (tituloRepository.findById(tituloId) != null) {
			new EntityNotFoundException("Título não encontrado com o ID: " + tituloId + ".");
		}
	}

}

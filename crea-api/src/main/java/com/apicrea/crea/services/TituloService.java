package com.apicrea.crea.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.requests.TituloRequest;
import com.apicrea.crea.common.responses.TituloResponse;
import com.apicrea.crea.common.utils.ConversorObjeto;
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

		Titulo titulo = tituloRepository.save(ConversorObjeto.converter(tituloRequest, Titulo.class));

		return ConversorObjeto.converter(titulo, TituloResponse.class);
	}

	public List<Titulo> findAll() {
		return tituloRepository.findAll();
	}

	public TituloResponse findById(Long tituloId) {
		Titulo titulo = carregarTitulo(tituloId);

		return ConversorObjeto.converter(titulo, TituloResponse.class);
	}

	public TituloResponse update(TituloRequest tituloRequest) {
		Titulo titulo = carregarTitulo(tituloRequest.getId());

		if (tituloRepository.existsByDescricao(tituloRequest.getDescricao())) {
			throw new EntityExistsException("O título já existe.");
		} else {
			titulo.setDescricao(titulo.getDescricao());
			titulo = tituloRepository.save(titulo);

			return ConversorObjeto.converter(titulo, TituloResponse.class);
		}
	}

	public void deleteById(Long tituloId) {
		Titulo titulo = carregarTitulo(tituloId);
		if (tituloRepository.verificaTituloEmUso(tituloId) > 0) {
			throw new EntityNotFoundException("Este título não pode ser removido, pois está vinculado a um profissional.");
		}

		tituloRepository.delete(titulo);
	}

	public Titulo carregarTitulo(Long idTitulo) {
		Optional<Titulo> titulo = tituloRepository.findById(idTitulo);
		
		return titulo.orElseThrow(() -> new EntityNotFoundException("Esse título não existe."));
	}
}

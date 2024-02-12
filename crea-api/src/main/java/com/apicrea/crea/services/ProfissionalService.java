package com.apicrea.crea.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.enums.SituacaoCadastro;
import com.apicrea.crea.common.enums.SituacaoRegistro;
import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.requests.ProfissionalTituloRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.common.responses.TituloResponse;
import com.apicrea.crea.repositories.ProfissionalRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfissionalService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private TituloService tituloService;

	public ProfissionalResponse create(ProfissionalRequest profissionalRequest) {
		validarDados(profissionalRequest);

		Profissional profissional = profissionalRepository.save(new Profissional(profissionalRequest));

		return new ProfissionalResponse(profissional);
	}

	private void validarDados(ProfissionalRequest profissionalRequest) {
		if (profissionalRepository.existsByEmail(profissionalRequest.getEmail())) {
			throw new EntityExistsException(
					"O Profissional com o E-mail " + profissionalRequest.getEmail() + " já existe.");
		}

		if (profissionalRequest.getStatusCadastro().equals(SituacaoCadastro.REGISTRADO)) {
			profissionalRequest.setDataVisto(null);
		} else {
			if (profissionalRequest.getDataVisto() == null) {
				throw new EntityExistsException(
						"A data do visto deve ser preenchida para a situação de cadastro Visado.");
			}
		}
	}

	public void deleteById(Long id) {

		if (profissionalRepository.findById(id).get() == null) {
			throw new EntityExistsException("Esse Profissional não existe.");
		}

		profissionalRepository.delete(profissionalRepository.findById(id).get());
	}

	public ProfissionalResponse adcionarTitulo(ProfissionalTituloRequest profissionalTituloRequest) {
		Profissional profissional = profissionalRepository.findById(profissionalTituloRequest.getIdProfissional())
				.get();
		if (profissional == null) {
			throw new EntityNotFoundException("Esse Profissional não existe.");
		}

		TituloResponse tituloResponse = tituloService.findById(profissionalTituloRequest.getIdTitulo());
		if (tituloResponse == null) {
			throw new EntityNotFoundException("Esse titulo não existe.");
		}

		if (profissional.getTitulos().isEmpty()) {
			List<Titulo> titulos = new ArrayList<>();

			titulos.add(new Titulo(tituloResponse));
			profissional.setTitulos(titulos);
			profissional.setStatusRegistro(SituacaoRegistro.ATIVO);
			profissional.setCodigo(geradorCodigo(String.valueOf(profissional.getId())));
			profissional = profissionalRepository.save(profissional);

			return new ProfissionalResponse(profissional);
		} else {
			List<Titulo> titulos = profissional.getTitulos();
			Titulo titulo = new Titulo(tituloResponse);

			if (titulos.contains(titulo)) {
				throw new EntityExistsException("Esse Profissional tem esse título.");
			}
			titulos.add(titulo);
			profissional.setTitulos(titulos);
			profissional = profissionalRepository.save(profissional);

			return new ProfissionalResponse(profissional);
		}

	}

	public ProfissionalResponse ativarProfissional(Long idProfissional) {
		Profissional profissional = profissionalRepository.findById(idProfissional).get();

		if (profissional == null) {
			throw new EntityNotFoundException("Esse profissional não existe.");
		}

		if (profissional.getStatusRegistro().equals(SituacaoRegistro.ATIVO)) {
			throw new EntityExistsException("Esse Profissional já está ativo");
		}

		if (profissional.getTitulos().isEmpty()) {
			throw new EntityNotFoundException("Impossivel ATIVAR profissional sem titulo");
		}

		profissional.setStatusRegistro(SituacaoRegistro.ATIVO);
		profissional = profissionalRepository.save(profissional);

		return new ProfissionalResponse(profissional);
	}

	public ProfissionalResponse desativarProfissional(Long id) {
		verificarExistenciaProfissional(id);

		Profissional profissional = new Profissional();

		if (profissional.getStatusRegistro().equals(SituacaoRegistro.INATIVO)) {
			throw new EntityExistsException("Esse Profissional já está inativo");
		}

		profissional.setStatusRegistro(SituacaoRegistro.INATIVO);

		profissionalRepository.save(profissional);

		return new ProfissionalResponse(profissional);
	}

	public ProfissionalResponse cancelarProfissional(Long id) {
		verificarExistenciaProfissional(id);

		Profissional profissional = new Profissional();

		if (profissional.getStatusRegistro().equals(SituacaoRegistro.CANCELADO)) {
			throw new EntityExistsException("Esse Profissional já está cancelado");
		}

		profissional.setStatusRegistro(SituacaoRegistro.CANCELADO);

		profissionalRepository.save(profissional);

		return new ProfissionalResponse(profissional);
	}

	public ProfissionalResponse finbyid(Long id) {

		if (profissionalRepository.findById(id).get() == null) {
			throw new EntityExistsException("Esse Profissional não existe.");
		}

		return new ProfissionalResponse(profissionalRepository.findById(id).get());
	}

	private String geradorCodigo(String numero) {
		Random random = new Random();
		StringBuilder codigo = new StringBuilder();

		for (int i = 0; i < (10 - numero.length()); i++) {
			codigo.append(random.nextInt(10));
		}

		codigo.append(numero);

		codigo.append("PI");

		return codigo.toString();

	}

	public void verificarExistenciaTitulo(Long idTitulo) {
		if (tituloService.findById(idTitulo) == null) {
			throw new EntityExistsException("Esse titulo não existe.");
		}
	}

	private void verificarExistenciaProfissional(Long idProfissional) {
		if (profissionalRepository.findById(idProfissional).get() == null) {
			throw new EntityExistsException("Esse profissional não existe.");
		}
	}

}

package com.apicrea.crea.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.entities.Titulos;
import com.apicrea.crea.common.enums.Cadastro;
import com.apicrea.crea.common.enums.Registro;
import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.common.responses.dto.ProfissionalDto;
import com.apicrea.crea.repositories.ProfissionalRepository;
import com.apicrea.crea.repositories.TitulosRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private TitulosRepository titulosRepository;

	public ProfissionalResponse create(ProfissionalRequest profissionalRequest) {

		if (profissionalRepository.existsByEmail(profissionalRequest.getEmail())) {
			throw new EntityExistsException(
					"O Profissional com o E-mail " + profissionalRequest.getEmail() + " já existe.");
		}

		ProfissionalDto profissionalDto = new ProfissionalDto(profissionalRequest);

		if (profissionalDto.getStatusCadastro().equals(Cadastro.REGISTRADO)) {
			profissionalDto.setVisto(null);
		}

		Profissional profissional = new Profissional(profissionalDto);

		profissionalRepository.save(profissional);

		String nome = geradorCodigo(String.valueOf(profissional.getId()));

		profissional.setCodigo(nome);

		profissionalRepository.save(profissional);

		return new ProfissionalResponse(new ProfissionalDto(profissional));
	}

	public void deleteById(Long id) {
		if (profissionalRepository.findById(id).get() == null) {
			throw new EntityExistsException("Esse Profissional não existe.");
		}

		profissionalRepository.delete(profissionalRepository.findById(id).get());
	}

	public ProfissionalResponse updateTitulo(Long idTitulo, Long idProfissional) {
		if (titulosRepository.findById(idTitulo).get() == null) {
			throw new EntityExistsException("Esse titulo não existe.");
		}
		if (profissionalRepository.findById(idProfissional).get() == null) {
			throw new EntityExistsException("Esse Profissional não existe.");
		}

		Profissional profissional = profissionalRepository.findById(idProfissional).get();

		Titulos titulo = titulosRepository.findById(idTitulo).get();

		if (profissional.getTitulos().isEmpty()) {
			List<Titulos> titulos = new ArrayList<>();

			titulos.add(titulo);

			profissional.setTitulos(titulos);

			profissional.setStatusRegistro(Registro.ATIVO);

			profissionalRepository.save(profissional);

			return new ProfissionalResponse(profissional);
		}

		else {
			List<Titulos> titulos = profissional.getTitulos();

			if (titulos.contains(titulo)) {
				throw new EntityExistsException("Esse Profissional já tem esse título.");
			}
			titulos.add(titulo);

			profissional.setTitulos(titulos);

			profissionalRepository.save(profissional);

			return new ProfissionalResponse(profissional);
		}

	}

	public ProfissionalResponse finbyid(Long id) {

		if (profissionalRepository.findById(id).get() == null) {
			throw new EntityExistsException("Esse Profissional não existe.");
		}

		return new ProfissionalResponse(profissionalRepository.findById(id).orElseThrow());
	}

	public String geradorCodigo(String numero) {
		Random random = new Random();
		StringBuilder codigo = new StringBuilder();

		for (int i = 0; i < (10 - numero.length()); i++) {
			codigo.append(random.nextInt(10));
		}

		codigo.append(numero);

		codigo.append("PI");

		return codigo.toString();

	}

}

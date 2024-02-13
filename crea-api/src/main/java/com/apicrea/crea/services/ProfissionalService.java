package com.apicrea.crea.services;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.enums.SituacaoCadastro;
import com.apicrea.crea.common.enums.SituacaoRegistro;
import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.requests.ProfissionalRequestUpdate;
import com.apicrea.crea.common.requests.ProfissionalTituloRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.common.responses.TituloResponse;
import com.apicrea.crea.repositories.ProfissionalRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private TituloService tituloService;

	// create
	public ProfissionalResponse create(ProfissionalRequest profissionalRequest) {
		validarDados(profissionalRequest);
		Profissional profissional = new Profissional(profissionalRequest);
		profissional.setSenha(criptografarSenha(profissional.getSenha()));
		profissional = profissionalRepository.save(profissional);

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
				throw new IllegalArgumentException(
						"A data do visto deve ser preenchida para a situação de cadastro Visado.");
			}
		}
	}

	// read
	public ProfissionalResponse finbyid(Long id) {
		verificarExistenciaProfissional(id);

		return new ProfissionalResponse(profissionalRepository.findById(id).get());
	}

	// update
	public void atualizarProfissional(ProfissionalRequestUpdate updateProfissionalRequest) {
		verificarExistenciaProfissional(updateProfissionalRequest.getId());
		Profissional profissional = profissionalRepository.findById(updateProfissionalRequest.getId()).get();
		BeanUtils.copyProperties(updateProfissionalRequest, profissional,
				getNullPropertyNames(updateProfissionalRequest));

		if (updateProfissionalRequest.getDataVisto() != null
				&& updateProfissionalRequest.getStatusCadastro() == SituacaoCadastro.REGISTRADO) {
			throw new IllegalArgumentException(
					"A data do visto não deve ser preenchida para a situação de cadastro Registrado.");
		}

		if (updateProfissionalRequest.getDataVisto() == null
				&& updateProfissionalRequest.getStatusCadastro() == SituacaoCadastro.VISADO) {
			throw new IllegalArgumentException(
					"A data do visto deve ser preenchida para a situação de cadastro Visado.");
		}

		if (updateProfissionalRequest.getStatusCadastro() == SituacaoCadastro.REGISTRADO
				&& profissional.getDataVisto() != null) {
			profissional.setDataVisto(null);
		}

		if (updateProfissionalRequest.getStatusCadastro() == SituacaoCadastro.VISADO
				&& profissional.getDataVisto() == null) {
			profissional.setDataVisto(updateProfissionalRequest.getDataVisto());
		}

		profissional = profissionalRepository.save(profissional);
	}

	// delete
	public void deleteById(Long id) {
		verificarExistenciaProfissional(id);
		profissionalRepository.delete(profissionalRepository.findById(id).get());
	}

	// *** listar TODOS
	public List<Profissional> findAll() {

		return profissionalRepository.findAll();
	}

	// Ativar Profissional
	public ProfissionalResponse ativarProfissional(Long idProfissional) {
		Profissional profissional = profissionalRepository.findById(idProfissional).get();

		if (profissional == null) {
			throw new EntityNotFoundException("Esse profissional não existe.");
		}

		if (profissional.getStatusRegistro().equals(SituacaoRegistro.ATIVO)) {
			throw new EntityExistsException("Esse Profissional já está ativo");
		}

		if (profissional.getTitulos().isEmpty()) {
			throw new IllegalArgumentException("Impossivel ATIVAR profissional sem titulo");
		}
		profissional.setStatusRegistro(SituacaoRegistro.ATIVO);
		profissional = profissionalRepository.save(profissional);

		return new ProfissionalResponse(profissional);
	}

	// Desativar Profissional
	public ProfissionalResponse desativarProfissional(Long id) {
		verificarExistenciaProfissional(id);
		Profissional profissional = profissionalRepository.findById(id).get();

		if (profissional.getStatusRegistro().equals(SituacaoRegistro.INATIVO)) {
			throw new EntityExistsException("Esse Profissional já está inativo");
		}
		profissional.setStatusRegistro(SituacaoRegistro.INATIVO);
		profissional = profissionalRepository.save(profissional);

		return new ProfissionalResponse(profissional);
	}

	// Cancelar Profissional
	public ProfissionalResponse cancelarProfissional(Long id) {
		verificarExistenciaProfissional(id);

		Profissional profissional = profissionalRepository.findById(id).get();

		if (profissional.getStatusRegistro().equals(SituacaoRegistro.CANCELADO)) {
			throw new EntityExistsException("Esse Profissional já está cancelado");
		}
		profissional.setStatusRegistro(SituacaoRegistro.CANCELADO);
		profissional = profissionalRepository.save(profissional);

		return new ProfissionalResponse(profissional);
	}

	// Adicionar Título
	public ProfissionalResponse adcionarTituloAoProfissional(ProfissionalTituloRequest profissionalTituloRequest) {
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
			profissional.setCodigo(geradorCodigo(profissional.getId()));
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

	// ***Remover Título
	public void removerTituloProfissao(ProfissionalTituloRequest profissionalTituloRequest) {
		verificarExistenciaDados(profissionalTituloRequest.getIdProfissional(),
				profissionalTituloRequest.getIdTitulo());
		Profissional profissional = profissionalRepository.findById(profissionalTituloRequest.getIdProfissional())
				.get();
		profissional.getTitulos().removeIf(t -> t.getId().equals(profissionalTituloRequest.getIdTitulo()));
		profissional = profissionalRepository.save(profissional);

		if (profissional.getTitulos().isEmpty()) {
			profissional.setStatusRegistro(SituacaoRegistro.INATIVO);
			profissional.setCodigo(null);
			profissional = profissionalRepository.save(profissional);
		}
	}

	private String geradorCodigo(Long idProfissional) {
		Random random = new Random();
		StringBuilder codigo = new StringBuilder();

		for (int i = 0; i < (10 - idProfissional.toString().length()); i++) {
			codigo.append(random.nextInt(10));
		}
		codigo.append(idProfissional);
		codigo.append("PI");

		return codigo.toString();
	}

	private void verificarExistenciaDados(Long idProfissional, Long idTitulo) {
		verificarExistenciaProfissional(idProfissional);
		verificarExistenciaTitulo(idTitulo);
	}

	private void verificarExistenciaTitulo(Long idTitulo) {
		if (tituloService.findById(idTitulo) == null) {
			throw new EntityNotFoundException("Esse titulo não existe.");
		}
	}

	private void verificarExistenciaProfissional(Long idProfissional) {
		if (profissionalRepository.findById(idProfissional).get() == null) {
			throw new EntityNotFoundException("Esse profissional não existe.");
		}
	}

	private String criptografarSenha(String senha) {
		byte[] senhaBytes = senha.getBytes();

		return Base64.getEncoder().encodeToString(senhaBytes);
	}

	private String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];

		return emptyNames.toArray(result);
	}
}

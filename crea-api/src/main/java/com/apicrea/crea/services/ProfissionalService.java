package com.apicrea.crea.services;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import com.apicrea.crea.common.requests.ProfissionalUpdateRequest;
import com.apicrea.crea.common.requests.ProfissionalTituloRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.common.utils.ConversorObjeto;
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
		profissionalRequest = validarDados(profissionalRequest);
		Profissional profissional = ConversorObjeto.converter(profissionalRequest, Profissional.class);
		profissional.setSenha(criptografarSenha(profissional.getSenha()));

		profissional = profissionalRepository.save(profissional);

		return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
	}

	private ProfissionalRequest validarDados(ProfissionalRequest profissionalRequest) {
		if (profissionalRepository.existsByEmail(profissionalRequest.getEmail())) {
			throw new EntityExistsException(
					"O Profissional com o E-mail " + profissionalRequest.getEmail() + " já existe.");
		}

		if (profissionalRequest.getStatusCadastro().equals(SituacaoCadastro.REGISTRADO)
				&& profissionalRequest.getDataVisto() != null) {
			profissionalRequest.setDataVisto(null);
		} else {
			if (profissionalRequest.getDataVisto() == null) {
				throw new IllegalArgumentException(
						"A data do visto deve ser preenchida para a situação de cadastro Visado.");
			}
		}
		
		return profissionalRequest;
	}

	// read
	public ProfissionalResponse findbById(Long id) {
		return ConversorObjeto.converter( carregarProfissional(id), ProfissionalResponse.class );
	}

	// update
	public ProfissionalResponse atualizarProfissional(ProfissionalUpdateRequest updateProfissionalRequest) {
		Profissional profissional = carregarProfissional(updateProfissionalRequest.getId());

		if (updateProfissionalRequest.getStatusCadastro() == SituacaoCadastro.REGISTRADO
				&& updateProfissionalRequest.getDataVisto() != null) {
			updateProfissionalRequest.setDataVisto(null);
		}

		if (updateProfissionalRequest.getStatusCadastro() == SituacaoCadastro.VISADO
				&& updateProfissionalRequest.getDataVisto() == null) {
			throw new IllegalArgumentException(
					"A data do visto deve ser preenchida para a situação de cadastro Visado.");
		}

		BeanUtils.copyProperties(updateProfissionalRequest, profissional,
				getNullPropertyNames(updateProfissionalRequest));
		
		profissional = profissionalRepository.save(profissional);
		
		return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
	}

	// delete
	public void deleteById(Long id) {
		Profissional profissional = carregarProfissional(id);
		profissionalRepository.delete(profissional);
	}

	// *** listar TODOS
	public List<Profissional> findAll() {
		return profissionalRepository.findAll();
	}

	// Ativar Profissional
	public ProfissionalResponse ativarProfissional(Long id) {
		Profissional profissional = carregarProfissional(id);

		if (profissional.getStatusRegistro() != null
				&& profissional.getStatusRegistro().equals(SituacaoRegistro.ATIVO)) {
			throw new EntityExistsException("Esse Profissional já está ativo.");
		}

		if (profissional.getTitulos().isEmpty()) {
			throw new IllegalArgumentException("Impossivel ATIVAR profissional sem titulo.");
		}

		if (profissional.getCodigo().isEmpty()) {
			throw new IllegalArgumentException("Impossivel ATIVAR profissional sem código.");
		}

		profissional.setStatusRegistro(SituacaoRegistro.ATIVO);
		profissional = profissionalRepository.save(profissional);

		return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
	}

	// Desativar Profissional
	public ProfissionalResponse desativarProfissional(Long id) {
		Profissional profissional = carregarProfissional(id);

		if (profissional.getStatusRegistro() != null
				&& profissional.getStatusRegistro().equals(SituacaoRegistro.INATIVO)) {
			throw new EntityExistsException("Esse Profissional já está inativo.");
		}

		profissional.setStatusRegistro(SituacaoRegistro.INATIVO);
		profissional = profissionalRepository.save(profissional);

		return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
	}

	// Cancelar Profissional
	public ProfissionalResponse cancelarProfissional(Long id) {
		Profissional profissional = carregarProfissional(id);

		if (profissional.getStatusRegistro() != null
				&& profissional.getStatusRegistro().equals(SituacaoRegistro.CANCELADO)) {
			throw new EntityExistsException("Esse Profissional já está cancelado.");
		}

		profissional.setStatusRegistro(SituacaoRegistro.CANCELADO);
		profissional = profissionalRepository.save(profissional);

		return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
	}

	// Adicionar Título
	public ProfissionalResponse adcionarTituloAoProfissional(ProfissionalTituloRequest profissionalTituloRequest) {
		Profissional profissional = carregarProfissional(profissionalTituloRequest.getIdProfissional());
		Titulo titulo = tituloService.carregarTitulo(profissionalTituloRequest.getIdTitulo());
		
		if (profissional.getTitulos().isEmpty()) {
			List<Titulo> titulos = new ArrayList<>();

			titulos.add(ConversorObjeto.converter(tituloService.findById(profissionalTituloRequest.getIdTitulo()), Titulo.class));
			profissional.setTitulos(titulos);
			profissional.setStatusRegistro(SituacaoRegistro.ATIVO);
			profissional.setCodigo(geradorCodigo(profissional.getId()));
			profissional = profissionalRepository.save(profissional);

			return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
		} else {
			List<Titulo> titulos = profissional.getTitulos();

			if (titulos.contains(titulo)) {
				throw new EntityExistsException("Esse Profissional já possui esse título.");
			}
			titulos.add(titulo);
			profissional.setTitulos(titulos);
			profissional = profissionalRepository.save(profissional);

			return ConversorObjeto.converter( profissional, ProfissionalResponse.class );
		}
	}

	// ***Remover Título
	public void removerTituloProfissao(ProfissionalTituloRequest profissionalTituloRequest) {
		Profissional profissional = carregarProfissional(profissionalTituloRequest.getIdProfissional());
		Titulo titulo = tituloService.carregarTitulo(profissionalTituloRequest.getIdTitulo());
		
		List<Titulo> titulos = profissional.getTitulos();

		if (titulos.isEmpty()) {
			throw new EntityNotFoundException("Esse Profissional não possui título.");
		}

		if (!titulos.contains(titulo)) {
			throw new EntityNotFoundException("Esse Profissional não possui esse título.");
		}
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

	private Profissional carregarProfissional(Long idProfissional) {
		Optional<Profissional> profissional = profissionalRepository.findById(idProfissional);
		
		return profissional.orElseThrow(() -> new EntityNotFoundException("Esse profissional não existe."));
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

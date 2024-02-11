package com.apicrea.crea.common.responses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.entities.Titulos;
import com.apicrea.crea.common.enums.Registro;
import com.apicrea.crea.common.enums.Cadastro;
import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.responses.dto.ProfissionalDto;

import lombok.Data;

@Data
public class ProfissionalResponse {

	private Long id;

	private String nome;

	private String email;

	private String senha;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataRegistro;

	private String numeroCelular;

	private String codigo;

	private Registro statusRegistro;

	private Cadastro statusCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate visto;

	private List<Titulos> titulos;

	public ProfissionalResponse() {

	}

	public ProfissionalResponse(Profissional profissional) {
		BeanUtils.copyProperties(profissional, this);
	}

	public ProfissionalResponse(ProfissionalRequest profissionalRequest) {
		BeanUtils.copyProperties(profissionalRequest, this);
	}
	
	public ProfissionalResponse(ProfissionalDto profissionalDto) {
		BeanUtils.copyProperties(profissionalDto, this);
	}
}

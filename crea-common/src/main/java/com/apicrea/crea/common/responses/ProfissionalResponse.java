package com.apicrea.crea.common.responses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.enums.SituacaoCadastro;
import com.apicrea.crea.common.enums.SituacaoRegistro;
import com.apicrea.crea.common.requests.ProfissionalRequest;

import lombok.Data;

@Data
public class ProfissionalResponse {

	private Long id;

	private String nome;

	private String email;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataRegistro;

	private String numeroCelular;

	private String codigo;

	private SituacaoRegistro statusRegistro;

	private SituacaoCadastro statusCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVisto;

	private List<Titulo> titulos;

	public ProfissionalResponse() {

	}

	public ProfissionalResponse(Profissional profissional) {
		BeanUtils.copyProperties(profissional, this);
	}

	public ProfissionalResponse(ProfissionalRequest profissionalRequest) {
		BeanUtils.copyProperties(profissionalRequest, this);
	}
}

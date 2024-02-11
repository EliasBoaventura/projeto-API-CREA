package com.apicrea.crea.common.responses.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.requests.ProfissionalRequest;

import lombok.Data;

@Data
public class ProfissionalDto {

	private Long id;

	private String nome;

	private String email;

	private String senha;

	private LocalDate dataNascimento;

	private LocalDate dataRegistro;

	private String numeroCelular;

	private String codigo;

	private LocalDate visto;

	public ProfissionalDto() {

	}

	public ProfissionalDto(Profissional profissional) {
		BeanUtils.copyProperties(profissional, this);
	}

	public ProfissionalDto(ProfissionalRequest profissionalRequest) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(profissionalRequest, this);
	}
	
	

}

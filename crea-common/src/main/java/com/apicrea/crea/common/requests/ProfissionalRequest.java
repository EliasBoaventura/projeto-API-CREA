package com.apicrea.crea.common.requests;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.apicrea.crea.common.enums.TipoCadastro;

import lombok.Data;

@Data
public class ProfissionalRequest {

	private Long id;

	@NonNull
	private String nome;

	@NonNull
	private String email;

	@NonNull
	private String senha;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NonNull
	private LocalDate dataNascimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NonNull
	private LocalDate dataRegistro;

	@NonNull
	private String numeroCelular;

	@NonNull
	private String codigo;

	@NonNull
	private TipoCadastro statusCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NonNull
	private LocalDate visto;

}

package com.apicrea.crea.common.requests;

import java.time.LocalDate;

import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.apicrea.crea.common.enums.SituacaoCadastro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalRequest {

	private Long id;

	@NonNull
	private String nome;

	@NonNull
	@Email(message = "O e-mail não é válido")
	private String email;

	@NonNull
	private String senha;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NonNull
	private LocalDate dataNascimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataRegistro;

	@NonNull
	private String numeroCelular;

	@NonNull
	private SituacaoCadastro statusCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVisto;

}

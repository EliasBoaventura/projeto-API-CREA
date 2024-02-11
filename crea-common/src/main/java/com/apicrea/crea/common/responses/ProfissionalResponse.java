package com.apicrea.crea.common.responses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.apicrea.crea.common.entities.Titulos;
import com.apicrea.crea.common.enums.StatusRegistro;
import com.apicrea.crea.common.enums.TipoCadastro;

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

	private StatusRegistro statusRegistro;

	private TipoCadastro statusCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate visto;

	private List<Titulos> titulos;
}

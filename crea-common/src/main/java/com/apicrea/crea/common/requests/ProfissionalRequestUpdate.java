package com.apicrea.crea.common.requests;

import java.time.LocalDate;

import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.apicrea.crea.common.enums.SituacaoCadastro;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalRequestUpdate {

	@NonNull
	private Long id;

	private String nome;

	@Email(message = "O e-mail não é válido")
	private String email;

	private String senha;

	private String numeroCelular;

	@Enumerated(EnumType.STRING)
	private SituacaoCadastro statusCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVisto;

}

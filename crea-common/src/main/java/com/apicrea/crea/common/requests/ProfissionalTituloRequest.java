package com.apicrea.crea.common.requests;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class ProfissionalTituloRequest {

	@NonNull
	private Long idProfissional;

	@NonNull
	private Long idTitulo;
}

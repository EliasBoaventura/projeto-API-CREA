package com.apicrea.crea.common.requests;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TituloRequest {

	private Long id;

	@NonNull
	private String descricao;
}

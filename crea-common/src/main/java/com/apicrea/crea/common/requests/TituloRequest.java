package com.apicrea.crea.common.requests;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class TituloRequest {
	private Long id;
	
	@NonNull
	private String nome;
}

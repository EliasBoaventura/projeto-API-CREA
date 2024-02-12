package com.apicrea.crea.common.requests;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.responses.TituloResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class TituloRequest {

	private Long id;

	@NonNull
	private String nome;

	public TituloRequest() {
	}

	public TituloRequest(Titulo titulo) {
		BeanUtils.copyProperties(titulo, this);
	}

	public TituloRequest(TituloResponse tituloResponse) {
		BeanUtils.copyProperties(tituloResponse, this);
	}

}

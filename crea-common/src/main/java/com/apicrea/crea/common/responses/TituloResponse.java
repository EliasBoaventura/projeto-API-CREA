package com.apicrea.crea.common.responses;

import org.springframework.beans.BeanUtils;

import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.requests.TituloRequest;

import lombok.Data;

@Data
public class TituloResponse {

	private Long Id;

	private String nome;

	public TituloResponse() {

	}

	public TituloResponse(Titulo titulo) {
		BeanUtils.copyProperties(titulo, this);
	}

	public TituloResponse(TituloRequest tituloRequest) {
		BeanUtils.copyProperties(tituloRequest, this);
	}
}

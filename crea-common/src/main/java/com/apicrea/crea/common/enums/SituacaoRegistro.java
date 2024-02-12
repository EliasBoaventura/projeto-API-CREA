package com.apicrea.crea.common.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SituacaoRegistro {

	ATIVO("Ativo"), INATIVO("Inativo"), CANCELADO("Cancelado");

	private String descricao;

	public static SituacaoRegistro of(String statusRegistro) {
		return Stream.of(values()).filter(c -> c.descricao.equals(statusRegistro)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}

package com.apicrea.crea.common.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SituacaoCadastro {

	REGISTRADO("Registrado"), VISADO("Visado");

	private String descricao;

	public static SituacaoCadastro of(String registro) {
		return Stream.of(values()).filter(c -> c.descricao.equals(registro)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}

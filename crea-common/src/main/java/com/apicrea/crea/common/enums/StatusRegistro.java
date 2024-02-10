package com.apicrea.crea.common.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusRegistro {

	ATIVO("Ativo"),
    INATIVO("Inativo"),
	CANCELADO("Cancelado");

    private String statusRegistro;

    public static StatusRegistro of(String statusRegistro) {
        return Stream.of(values())
                     .filter(c -> c.statusRegistro.equals(statusRegistro)) 
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
}

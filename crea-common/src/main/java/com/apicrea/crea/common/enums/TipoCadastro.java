package com.apicrea.crea.common.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoCadastro {
	
	 REGISTRADO("Registrado"),
	    VISADO("Visado");

	    private String statusCadastro;

	    public static TipoCadastro of(String registro) {
	        return Stream.of(values())
	                     .filter(c -> c.statusCadastro.equals(registro)) 
	                     .findFirst()
	                     .orElseThrow(IllegalArgumentException::new);
	    }
}

package com.apicrea.crea.common.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoRegistro {
	
	 REGISTRADO("Registrado"),
	    VISADO("Visado");

	    private String registro;

	    public static TipoRegistro of(String registro) {
	        return Stream.of(values())
	                     .filter(c -> c.registro.equals(registro)) 
	                     .findFirst()
	                     .orElseThrow(IllegalArgumentException::new);
	    }
}

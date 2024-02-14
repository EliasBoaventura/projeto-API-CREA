package com.apicrea.crea.common.utils;

import org.modelmapper.ModelMapper;

public class ConversorObjeto {

	public static <S, D> D converter( Object object, Class<D> destinationType ) {
		ModelMapper modelMapper = new ModelMapper();

		D objetoConvertido = null;

		try {
			objetoConvertido = modelMapper.map( object, destinationType );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return objetoConvertido;
	}
}

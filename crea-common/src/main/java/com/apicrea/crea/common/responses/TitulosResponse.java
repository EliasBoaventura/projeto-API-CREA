package com.apicrea.crea.common.responses;

import java.util.List;

import com.apicrea.crea.common.entities.Profissional;

import lombok.Data;

@Data
public class TitulosResponse {

	private Long Id;

	private String nome;

	private List<Profissional> profissionail;
}

package com.apicrea.crea.common.entities;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.apicrea.crea.common.requests.TituloRequest;
import com.apicrea.crea.common.responses.TituloResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TITULO")
public class Titulo implements Serializable {

	private static final long serialVersionUID = 412151881693349744L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long Id;

	@Column(name = "DC_DESCRICAO", nullable = false, unique = true)
	private String descricao;

	public Titulo() {

	}

	public Titulo(TituloResponse tituloResponse) {
		BeanUtils.copyProperties(tituloResponse, this);
	}

	public Titulo(TituloRequest tituloRequest) {
		BeanUtils.copyProperties(tituloRequest, this);
	}
}

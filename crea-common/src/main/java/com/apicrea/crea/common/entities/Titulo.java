package com.apicrea.crea.common.entities;

import java.io.Serializable;

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
}

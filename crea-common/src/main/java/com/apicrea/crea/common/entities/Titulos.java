package com.apicrea.crea.common.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TITULO")
public class Titulos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "ID")
	private Long Id;
	
	@Column(name = "DC_NOME", nullable = false)
	private String nome;
	
	@ManyToMany(mappedBy = "titulos")
    private List<Profissional> profissionail;
}

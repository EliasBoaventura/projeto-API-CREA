package com.apicrea.crea.common.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.apicrea.crea.common.enums.Registro;
import com.apicrea.crea.common.enums.Cadastro;
import com.apicrea.crea.common.responses.dto.ProfissionalDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "PROFISSIONAL")
public class Profissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DC_NOME", nullable = false)
	private String nome;

	@Column(name = "DC_EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "DC_SENHA", nullable = false)
	private String senha;

	@Column(name = "DATA_NASCIMENTO", nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate dataNascimento;

	@Column(name = "DATA_REGISTRO", nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate dataRegistro;

	@Column(name = "DC_NUMERO", nullable = false)
	private String numeroCelular;

	@Column(name = "DC_CODIGO")
	private String codigo;

	@Enumerated(EnumType.STRING)
	@Column(name = "DC_STATUS")
	private Registro statusRegistro;

	@Enumerated(EnumType.STRING)
	@Column(name = "DC_CADASTRO")
	private Cadastro statusCadastro;

	@Column(name = "DATA_VISTO")
	@Temporal(TemporalType.DATE)
	private LocalDate visto;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PROFISSIONAL_TITULO", joinColumns = @JoinColumn(name = "PROFISSIONAL_ID"), inverseJoinColumns = @JoinColumn(name = "TITULO_ID"))
	private List<Titulos> titulos;

	public Profissional() {

	}

	public Profissional(ProfissionalDto profissionalDto) {
		BeanUtils.copyProperties(profissionalDto, this);
	}

}

package com.apicrea.crea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apicrea.crea.common.entities.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
	boolean existsByNome(String nome);
}

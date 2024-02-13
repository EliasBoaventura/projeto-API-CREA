package com.apicrea.crea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apicrea.crea.common.entities.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
	boolean existsByDescricao(String descricao);

	@Query("SELECT COUNT(p.id) FROM Profissional p JOIN p.titulos t where t.id = :tituloId")
	public Long verificaTituloEmUso(Long tituloId);
}

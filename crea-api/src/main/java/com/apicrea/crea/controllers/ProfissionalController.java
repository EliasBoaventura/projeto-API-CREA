package com.apicrea.crea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.requests.ProfissionalTituloRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.services.ProfissionalService;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

	@Autowired
	private ProfissionalService profissionalService;

	@PostMapping
	public ResponseEntity<ProfissionalResponse> createprofissional(
			@RequestBody ProfissionalRequest profissionalRequest) {

		return ResponseEntity.ok(profissionalService.create(profissionalRequest));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfissionalResponse> buscaProfissional(@PathVariable Long id) {

		return ResponseEntity.ok(profissionalService.finbyid(id));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletaProfissional(@PathVariable Long id) {

		profissionalService.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	@PutMapping("/adicionar-titulo")
	public ResponseEntity<ProfissionalResponse> adicionarTitulo(
			@RequestBody ProfissionalTituloRequest profissionalTitulosResponse) {

		return ResponseEntity.ok(profissionalService.adcionarTitulo(profissionalTitulosResponse));
	}
}

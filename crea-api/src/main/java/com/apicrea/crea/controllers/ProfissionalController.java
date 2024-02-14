package com.apicrea.crea.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apicrea.crea.common.entities.Profissional;
import com.apicrea.crea.common.requests.ProfissionalRequest;
import com.apicrea.crea.common.requests.ProfissionalRequestUpdate;
import com.apicrea.crea.common.requests.ProfissionalTituloRequest;
import com.apicrea.crea.common.responses.ProfissionalResponse;
import com.apicrea.crea.services.ProfissionalService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

	@Autowired
	private ProfissionalService profissionalService;

	@Operation(summary = "Cria um profissional")
	@PostMapping("/criar")
	public ResponseEntity<ProfissionalResponse> createprofissional(
			@RequestBody ProfissionalRequest profissionalRequest) {
		try {
			return ResponseEntity.ok(profissionalService.create(profissionalRequest));
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Busca um profissional por ID")
	@GetMapping("/{id}")
	public ResponseEntity<ProfissionalResponse> findById(@PathVariable Long id) {
		try {
			ProfissionalResponse profissionalResponse = profissionalService.finbyid(id);

			return ResponseEntity.ok(profissionalResponse);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Atualiza um profissional")
	@PutMapping("/atualizar")
	public ResponseEntity<Void> atualizarProfissional(@RequestBody ProfissionalRequestUpdate updateProfissional) {
		try {
			profissionalService.atualizarProfissional(updateProfissional);

			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Deleta um profissional por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletaProfissional(@PathVariable Long id) {
		try {
			profissionalService.deleteById(id);

			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		}

	}

	@Operation(summary = "Lista todos os profissionais")
	@GetMapping("/listar")
	public ResponseEntity<List<Profissional>> findAllProfissionais() {
		try {
			List<Profissional> profissionais = profissionalService.findAll();

			return new ResponseEntity<>(profissionais, HttpStatus.OK);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage())
					.body(Collections.emptyList());
		}
	}

	@Operation(summary = "Ativa um profissional")
	@PutMapping("/ativar/{id}")
	public ResponseEntity<ProfissionalResponse> ativarProfissional(@PathVariable Long id) {
		try {
			ProfissionalResponse response = profissionalService.ativarProfissional(id);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Desativa um profissional por ID")
	@PutMapping("/desativar/{id}")
	public ResponseEntity<ProfissionalResponse> desativarProfissional(@PathVariable Long id) {
		try {
			ProfissionalResponse response = profissionalService.desativarProfissional(id);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Cancela um profissional por ID")
	@PutMapping("/{id}/cancelar")
	public ResponseEntity<ProfissionalResponse> cancelarProfissional(@PathVariable Long id) {
		try {
			ProfissionalResponse response = profissionalService.cancelarProfissional(id);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Adiciona um título a um profissional")
	@PutMapping("/adicionar-titulo")
	public ResponseEntity<ProfissionalResponse> adicionarTituloAoProfissional(
			@RequestBody ProfissionalTituloRequest profissionalTitulosResponse) {
		try {
			return ResponseEntity.ok(profissionalService.adcionarTituloAoProfissional(profissionalTitulosResponse));
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Remove um titulo de um profissional")
	@DeleteMapping("/titulo/remover")
	public ResponseEntity<Void> removerTituloProfissao(
			@RequestBody ProfissionalTituloRequest profissionalTituloRequest) {
		try {
			profissionalService.removerTituloProfissao(profissionalTituloRequest);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		}

	}
}

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

import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.requests.TituloRequest;
import com.apicrea.crea.common.responses.TituloResponse;
import com.apicrea.crea.services.TituloService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private TituloService tituloService;

	@Operation(summary = "Cria um título.")
	@PostMapping
	public ResponseEntity<TituloResponse> create(@RequestBody TituloRequest tituloRequest) {
		try {

			return ResponseEntity.ok(tituloService.create(tituloRequest));
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Lista todos os títulos.")
	@GetMapping
	public ResponseEntity<List<Titulo>> findAll() {
		try {
			List<Titulo> titulos = tituloService.findAll();

			return new ResponseEntity<>(titulos, HttpStatus.OK);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage())
					.body(Collections.emptyList());
		}
	}

	@Operation(summary = "Busca um título por ID.")
	@GetMapping("/{id}")
	public ResponseEntity<TituloResponse> findById(@PathVariable Long id) {
		try {
			TituloResponse tituloResponse = tituloService.findById(id);

			return ResponseEntity.ok(tituloResponse);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Atualiza um título.")
	@PutMapping()
	public ResponseEntity<TituloResponse> update(@RequestBody TituloRequest tituloRequest) {
		try {
			tituloService.update(tituloRequest);

			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		} catch (EntityExistsException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", e.getMessage()).body(null);
		}
	}

	@Operation(summary = "Deleta um título.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		try {
			tituloService.deleteById(id);

			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Error-Message", e.getMessage()).body(null);
		}
	}
}

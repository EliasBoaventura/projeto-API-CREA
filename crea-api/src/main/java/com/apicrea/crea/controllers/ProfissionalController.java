package com.apicrea.crea.controllers;

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

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

	@Autowired
	private ProfissionalService profissionalService;

	// creat
	@PostMapping("/criar")
	public ResponseEntity<ProfissionalResponse> createprofissional(
			@RequestBody ProfissionalRequest profissionalRequest) {

		return ResponseEntity.ok(profissionalService.create(profissionalRequest));
	}

	// read
	@GetMapping("/{id}")
	public ResponseEntity<ProfissionalResponse> findById(@PathVariable Long id) {
		ProfissionalResponse profissionalResponse = profissionalService.finbyid(id);

		return ResponseEntity.ok(profissionalResponse);
	}

	// update
	@PutMapping("/atualizar")
	public ResponseEntity<Void> atualizarProfissional(@RequestBody ProfissionalRequestUpdate updateProfissional) {
		profissionalService.atualizarProfissional(updateProfissional);

		return ResponseEntity.noContent().build();
	}

	// delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletaProfissional(@PathVariable Long id) {
		profissionalService.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	// *** lista TODOS
	@GetMapping("/listar")
	public ResponseEntity<List<Profissional>> findAllProfissionais() {
		List<Profissional> profissionais = profissionalService.findAll();

		return new ResponseEntity<>(profissionais, HttpStatus.OK);
	}

	// ativa o profissional
	@PutMapping("/{id}/ativar")
	public ResponseEntity<ProfissionalResponse> ativarProfissional(@PathVariable Long id) {
		ProfissionalResponse response = profissionalService.ativarProfissional(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// desativa o profissional
	@PutMapping("/{id}/desativar")
	public ResponseEntity<ProfissionalResponse> desativarProfissional(@PathVariable Long id) {
		ProfissionalResponse response = profissionalService.desativarProfissional(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// cancela o profissional
	@PutMapping("/{id}/cancelar")
	public ResponseEntity<ProfissionalResponse> cancelarProfissional(@PathVariable Long id) {
		ProfissionalResponse response = profissionalService.cancelarProfissional(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// adiciona o titulo
	@PutMapping("/adicionar-titulo")
	public ResponseEntity<ProfissionalResponse> adicionarTitulo(
			@RequestBody ProfissionalTituloRequest profissionalTitulosResponse) {

		return ResponseEntity.ok(profissionalService.adcionarTitulo(profissionalTitulosResponse));
	}

	// deletar titulo
	@DeleteMapping("/titulo/remover")
	public ResponseEntity<Void> removerTituloProfissao(
			@RequestBody ProfissionalTituloRequest profissionalTituloRequest) {
		profissionalService.removerTituloProfissao(profissionalTituloRequest);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

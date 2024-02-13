package com.apicrea.crea.controllers;

import java.util.List;

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

import com.apicrea.crea.common.entities.Titulo;
import com.apicrea.crea.common.requests.TituloRequest;
import com.apicrea.crea.common.responses.TituloResponse;
import com.apicrea.crea.services.TituloService;

@RestController
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private TituloService tituloService;

	@PostMapping
	public ResponseEntity<TituloResponse> create(@RequestBody TituloRequest tituloRequest) {

		return ResponseEntity.ok(tituloService.create(tituloRequest));
	}

	@GetMapping
	public List<Titulo> findAll() {

		return tituloService.findAll();
	}

	@GetMapping("/{id}")
	public TituloResponse findById(@PathVariable Long id) {

		return tituloService.findById(id);
	}

	@PutMapping()
	public TituloResponse update(@RequestBody TituloRequest tituloRequest) {

		return tituloService.update(tituloRequest);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		tituloService.deleteById(id);
	}
}

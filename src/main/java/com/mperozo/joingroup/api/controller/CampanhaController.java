package com.mperozo.joingroup.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mperozo.joingroup.api.assembler.CampanhaDTOAssembler;
import com.mperozo.joingroup.api.dto.CampanhaDTO;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.service.CampanhaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joingroup/api/campanha")
@RequiredArgsConstructor
public class CampanhaController {
	
	@Autowired
	private final CampanhaService campanhaService;
	
	@Autowired
	private final CampanhaDTOAssembler campanhaDTOAssembler;
	
	@PostMapping("/salvar")
	public ResponseEntity salvarCampanha(@RequestBody CampanhaDTO campanhaDTO) {

		Campanha campanha = campanhaDTOAssembler.toEntity(campanhaDTO);
		
		try {
			Campanha campanhaSalva = campanhaService.salvarCampanha(campanha);
			return new ResponseEntity(campanhaSalva, HttpStatus.CREATED);
		}catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity buscarCampanha(@PathVariable("id") Long id) {
		
		Campanha campanha = campanhaService.buscarPorId(id);
		
		if(campanha == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(campanha);
	}
	
}

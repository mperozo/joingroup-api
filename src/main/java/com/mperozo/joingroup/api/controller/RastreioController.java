package com.mperozo.joingroup.api.controller;

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

import com.mperozo.joingroup.api.assembler.RastreioDTOAssembler;
import com.mperozo.joingroup.api.dto.RastreioDTO;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Rastreio;
import com.mperozo.joingroup.service.RastreioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joingroup/api")
@RequiredArgsConstructor
public class RastreioController {
	
	@Autowired
	private final RastreioService rastreioService;
	
	@Autowired
	private final RastreioDTOAssembler rastreioDTOAssembler;
	
	@GetMapping("/v1/rastreios/{id}")
	public ResponseEntity findById(@PathVariable("id") Long id) {
		Rastreio rastreio = rastreioService.buscarPorId(id);
		if(rastreio == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(rastreio);
	}
	
	@GetMapping("/v1/campanhas/{id}/rastreios")
	public ResponseEntity findByIdCampanha(@PathVariable("id") Long idCampanha) {
		List<Rastreio> rastreios = rastreioService.buscarPorCampanha(idCampanha);
		List<RastreioDTO> rastreioDTOList = rastreioDTOAssembler.toDTOList(rastreios);

		return ResponseEntity.ok(rastreioDTOList);
	}
	
	@PostMapping("/v1/rastreios")
	public ResponseEntity save(@RequestBody RastreioDTO rastreioDTO) {
		try {
			Rastreio rastreio = rastreioDTOAssembler.toEntity(rastreioDTO);
			Rastreio rastreioSalvo = rastreioService.salvarRastreio(rastreio);
			return new ResponseEntity(rastreioSalvo, HttpStatus.CREATED);
		}catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/v1/rastreios/update/{id}")
	public ResponseEntity update( @PathVariable("id") Long id, @RequestBody RastreioDTO rastreioDTO) {
		try {
			Rastreio rastreioComNovosDados = rastreioDTOAssembler.toEntity(rastreioDTO);
			Rastreio rastreioAtualizado = rastreioService.atualizarRastreio(id, rastreioComNovosDados);
			
			return ResponseEntity.ok(rastreioAtualizado);
		} catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/v1/rastreios/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		
		try {
			rastreioService.deletar(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT); 
		} catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}

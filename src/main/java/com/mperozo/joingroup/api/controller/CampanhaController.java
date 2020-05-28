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

import com.mperozo.joingroup.api.assembler.CampanhaDTOAssembler;
import com.mperozo.joingroup.api.dto.CampanhaDTO;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.service.CampanhaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joingroup/api/campanhas")
@RequiredArgsConstructor
public class CampanhaController {
	
	@Autowired
	private final CampanhaService campanhaService;
	
	@Autowired
	private final CampanhaDTOAssembler campanhaDTOAssembler;
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity buscar(@PathVariable("id") Long id) {
		Campanha campanha = campanhaService.buscarPorId(id);
		if(campanha == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(campanha);
	}
	
	@GetMapping("/buscar-por-usuario/{id}")
	public ResponseEntity buscarPorUsuario(@PathVariable("id") Long idUsuarioResponsavel) {
		List<Campanha> campanhas = campanhaService.buscarPorUsuarioResponsavel(idUsuarioResponsavel);
		List<CampanhaDTO> campanhaDTOList = campanhaDTOAssembler.toDTOList(campanhas);

		return ResponseEntity.ok(campanhaDTOList);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity salvarCampanha(@RequestBody CampanhaDTO campanhaDTO) {

		try {
			Campanha campanha = campanhaDTOAssembler.toEntity(campanhaDTO);
			Campanha campanhaSalva = campanhaService.salvarCampanha(campanha);
			return new ResponseEntity(campanhaSalva, HttpStatus.CREATED);
		}catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizarCampanha( @PathVariable("id") Long id, @RequestBody CampanhaDTO campanhaDTO) {
		
		try {
			Campanha campanhaComNovosDados = campanhaDTOAssembler.toEntity(campanhaDTO);
			Campanha campanhaAtualizada = campanhaService.atualizarCampanha(id, campanhaComNovosDados);
			
			return ResponseEntity.ok(campanhaAtualizada);
		} catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletarCampanha(@PathVariable("id") Long id) {
		
		try {
			campanhaService.excluir(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT); 
		} catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}

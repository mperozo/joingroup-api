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

import com.mperozo.joingroup.api.assembler.GrupoDTOAssembler;
import com.mperozo.joingroup.api.dto.GrupoDTO;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.service.GrupoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joingroup/api")
@RequiredArgsConstructor
public class GrupoController {
	
	@Autowired
	private final GrupoService grupoService;
	
	@Autowired
	private final GrupoDTOAssembler grupoDTOAssembler;
	
	@GetMapping("/v1/grupos/{id}")
	public ResponseEntity findById(@PathVariable("id") Long id) {
		Grupo grupo = grupoService.buscarPorId(id);
		if(grupo == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		GrupoDTO grupoDTO = grupoDTOAssembler.toDTO(grupo);
		return ResponseEntity.ok(grupoDTO);
	}
	
	@GetMapping("/v1/campanhas/{id}/grupos")
	public ResponseEntity findByIdCampanha(@PathVariable("id") Long idCampanha) {
		List<Grupo> grupos = grupoService.buscarPorCampanha(idCampanha);
		List<GrupoDTO> grupoDTOList = grupoDTOAssembler.toDTOList(grupos);

		return ResponseEntity.ok(grupoDTOList);
	}
	
	@PostMapping("/v1/grupos/")
	public ResponseEntity save(@RequestBody GrupoDTO grupoDTO) {

		try {
			Grupo grupo = grupoDTOAssembler.toEntity(grupoDTO);
			Grupo grupoSalvo = grupoService.salvarGrupo(grupo);
			return new ResponseEntity(grupoSalvo, HttpStatus.CREATED);
		}catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/v1/grupos/update/{id}")
	public ResponseEntity update( @PathVariable("id") Long id, @RequestBody GrupoDTO grupoDTO) {
		
		try {
			Grupo grupoComNovosDados = grupoDTOAssembler.toEntity(grupoDTO);
			Grupo grupoAtualizado = grupoService.atualizarGrupo(id, grupoComNovosDados);
			
			return ResponseEntity.ok(grupoAtualizado);
		} catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/v1/grupos/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		
		try {
			grupoService.deletar(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT); 
		} catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}

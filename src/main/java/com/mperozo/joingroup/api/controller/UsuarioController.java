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

import com.mperozo.joingroup.api.assembler.UsuarioDTOAssembler;
import com.mperozo.joingroup.api.dto.UsuarioDTO;
import com.mperozo.joingroup.exception.AuthenticationException;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joingroup/api")
@RequiredArgsConstructor
public class UsuarioController {
	
	@Autowired
	private final UsuarioService usuarioService;
	
	@Autowired
	private final UsuarioDTOAssembler usuarioDTOAssembler;
	
	@GetMapping("/v1/usuarios/{id}")
	public ResponseEntity getById(@PathVariable("id") Long id) {
		Usuario usuario = usuarioService.buscarPorId(id);
		if(usuario == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping("/v1/usuarios/")
	public ResponseEntity save(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioDTOAssembler.toEntity(usuarioDTO);
		try {
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/v1/usuarios/login")
	public ResponseEntity login(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		}catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}

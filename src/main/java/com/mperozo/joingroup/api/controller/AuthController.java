package com.mperozo.joingroup.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mperozo.joingroup.api.assembler.UsuarioDTOAssembler;
import com.mperozo.joingroup.api.payload.request.LoginRequest;
import com.mperozo.joingroup.api.payload.request.RegisterRequest;
import com.mperozo.joingroup.api.payload.response.JwtResponse;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.security.jwt.JwtUtils;
import com.mperozo.joingroup.security.service.UserDetailsImpl;
import com.mperozo.joingroup.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/joingroup/api")
@RequiredArgsConstructor
/**
 * Controller responsável pelos endpoints de autenticação e autorização e registro.
 * Posteriormente criar um serviço em separado para ter essas responsabilidades e validar o token do usuário, bem como da aplicação web.
 * 
 * @author mperozo
 */
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioDTOAssembler usuarioDTOAssembler;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/v1/auth/login")
	public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}

	@PostMapping("v1/auth/register")
	public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
		try {
			Usuario usuario = usuarioService.registrarUsuario(registerRequest);
			return new ResponseEntity(usuario, HttpStatus.CREATED);
		}catch(BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("v1/auth/logout")
	public String logout() {
		//TODO Não será feito o logout server-side nessa primeira versão. Para implementar, seria necessário criar um mecanismo de blacklist.
		//Ex: https://blog.usejournal.com/springboot-how-to-invalidate-jwt-token-such-as-logout-or-reset-all-active-tokens-73f55289d47b
		// https://stackoverflow.com/questions/34475946/spring-boot-jwt-logout/42907381#42907381
		return null;
	}

}

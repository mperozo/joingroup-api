package com.mperozo.joingroup.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.exception.AuthenticationException;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.repository.UsuarioRepository;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceUnitTest {

	@MockBean
	private UsuarioRepository usuarioRepositoryMock;
	
	@MockBean
	private PasswordEncoder passwordEncoderMock;

	@SpyBean
	private UsuarioServiceImpl usuarioService;

	@Test
	public void deveValidarQueEmailAindaNaoFoiCadastrado() {

		lenient().when(usuarioRepositoryMock.existsByEmail(anyString())).thenReturn(false);
		usuarioService.verificarSeEmailJaEstaCadastrado(TestUtils.EMAIL_USUARIO_TESTE);
	}

	@Test
	public void deveValidarQueEmailJaFoiCadastradoELancarException() {

		lenient().when(usuarioRepositoryMock.existsByEmail(TestUtils.EMAIL_USUARIO_TESTE)).thenReturn(true);

		Exception exception = assertThrows(BusinessException.class, () -> {
			usuarioService.verificarSeEmailJaEstaCadastrado(TestUtils.EMAIL_USUARIO_TESTE);
		});

		assertThat(exception)
					.isInstanceOf(BusinessException.class)
					.hasMessage("E-mail já cadastrado.");
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {

		Usuario usuario = criarUsuario("Marcos", TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		lenient().when(usuarioRepositoryMock.findByEmail(TestUtils.EMAIL_USUARIO_TESTE)).thenReturn(Optional.of(usuario));

		Usuario usuarioAutenticado = usuarioService.autenticar(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);

		assertThat(usuarioAutenticado).isEqualToComparingFieldByField(usuarioAutenticado);
	}
	
	@Test
	public void deveLancarExceptionAoNaoEncontrarUsuarioPeloEmail() {

		lenient().when(usuarioRepositoryMock.findByEmail(anyString())).thenReturn(Optional.empty());

		Exception exception = assertThrows(AuthenticationException.class, () -> {
			usuarioService.autenticar(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		});

		assertThat(exception)
					.isInstanceOf(AuthenticationException.class)
					.hasMessage("Usuario ou senha inválidos.");
	}
	
	@Test
	public void deveLancarExceptionAoVerificarQueASenhaNaoEstaCorreta() {

		Usuario usuario = criarUsuario("Marcos", TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		lenient().when(usuarioRepositoryMock.findByEmail(TestUtils.EMAIL_USUARIO_TESTE)).thenReturn(Optional.of(usuario));

		Exception exception = assertThrows(AuthenticationException.class, () -> {
			usuarioService.autenticar(TestUtils.EMAIL_USUARIO_TESTE, "SENHA2");
		});

		assertThat(exception)
					.isInstanceOf(AuthenticationException.class)
					.hasMessage("Usuario ou senha inválidos.");
	}
	
	@Test
	public void deveIncluirUsuarioComSucesso() {
		
		Usuario usuario = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		lenient().doNothing().when(usuarioService).verificarSeEmailJaEstaCadastrado(TestUtils.EMAIL_USUARIO_TESTE);
		lenient().when(usuarioRepositoryMock.save(usuario)).thenReturn(usuario);
		
		Usuario result = usuarioService.salvarUsuario(usuario);
		
		assertThat(result).isEqualToComparingFieldByField(usuario);
	}
	
	@Test
	public void deveLancarExceptionAoTentarSalvarUsuarioComEmailJaCadastrado() {
		
		Usuario usuario = criarUsuario("Marcos", TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		lenient().doThrow(BusinessException.class).when(usuarioService).verificarSeEmailJaEstaCadastrado(TestUtils.EMAIL_USUARIO_TESTE);
		
		assertThrows(BusinessException.class, () -> {
			usuarioService.salvarUsuario(usuario);
		});
		
		verify(usuarioRepositoryMock, never()).save(usuario);
	}

	/* Métodos auxiliares */

	public static Usuario criarUsuario(String nome, String email, String senha) {
		return Usuario.builder()
				.nome(nome)
				.senha(senha)
				.email(email)
				.build();
	}

}

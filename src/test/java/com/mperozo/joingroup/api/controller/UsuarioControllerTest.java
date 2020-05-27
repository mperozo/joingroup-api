/*

package com.mperozo.joingroup.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mperozo.joingroup.api.assembler.UsuarioDTOAssembler;
import com.mperozo.joingroup.api.dto.UsuarioDTO;
import com.mperozo.joingroup.exception.AuthenticationException;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.service.UsuarioService;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	private static final String API = "/api/usuario";
	private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private UsuarioDTOAssembler UsuarioDTOAssembler;
	
	@Test
	public void deveAutenticarUmUsuario() throws Exception {
		
		Usuario usuario = criarUsuario();
		UsuarioDTO usuarioDTO = criarUsuarioDTO();
		
		Mockito.when(usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha())).thenReturn(usuario);
		
		String jsonRequested = new ObjectMapper().writeValueAsString(usuarioDTO);

		MockHttpServletRequestBuilder request = criarRequest(jsonRequested, "/autenticar");
		
		mvc.perform(request)
			.andExpect( MockMvcResultMatchers.status().isOk() )
			.andExpect( MockMvcResultMatchers.jsonPath("id").value(usuario.getId()) )
			.andExpect( MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()) )
		;
	}
	
	@Test
	public void deveRetornarBadRequestAoOcorrerUmErroDeAutenticacao() throws Exception {
		
		UsuarioDTO usuarioDTO = criarUsuarioDTO();
		
		Mockito.when(usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha())).thenThrow(AuthenticationException.class);
		
		String jsonRequested = new ObjectMapper().writeValueAsString(usuarioDTO);

		MockHttpServletRequestBuilder request = criarRequest(jsonRequested, "/autenticar");
		
		mvc.perform(request).andExpect( MockMvcResultMatchers.status().isBadRequest() )	;
	}
	
	@Test
	public void deveSalvarUmUsuario() throws Exception {
		
		Usuario usuario = criarUsuario();
		UsuarioDTO usuarioDTO = criarUsuarioDTO();
		
		Mockito.when(UsuarioDTOAssembler.toEntity(usuarioDTO)).thenReturn(usuario);
		Mockito.when(usuarioService.salvarUsuario(usuario)).thenReturn(usuario);
		
		String jsonRequested = new ObjectMapper().writeValueAsString(usuarioDTO);

		MockHttpServletRequestBuilder request = criarRequest(jsonRequested, "/salvar");
		
		mvc.perform(request)
			.andExpect( MockMvcResultMatchers.status().isCreated() )
			.andExpect( MockMvcResultMatchers.jsonPath("id").value(usuario.getId()) )
			.andExpect( MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()) )
		;
	}
	
	@Test
	public void deveRetornarBadRequestQuandoOcorreErroAoSalvarUsuario() throws Exception {
		
		Usuario usuario = criarUsuario();
		UsuarioDTO usuarioDTO = criarUsuarioDTO();
		
		Mockito.when(UsuarioDTOAssembler.toEntity(usuarioDTO)).thenReturn(usuario);
		Mockito.when(usuarioService.salvarUsuario(usuario)).thenThrow(BusinessException.class);
		
		String jsonRequested = new ObjectMapper().writeValueAsString(usuarioDTO);

		MockHttpServletRequestBuilder request = criarRequest(jsonRequested, "/salvar");
		
		mvc.perform(request).andExpect( MockMvcResultMatchers.status().isBadRequest() );
	}

*/
	
	/* Métodos Auxiliares */

/*

	private UsuarioDTO criarUsuarioDTO() {
		return TestUtils.criarUsuarioDTO(TestUtils.EMAIL_PARA_TESTE, TestUtils.SENHA_PARA_TESTE);
	}

	private Usuario criarUsuario() {
		return TestUtils.criarUsuario(TestUtils.EMAIL_PARA_TESTE, TestUtils.SENHA_PARA_TESTE);
	}
	
	private MockHttpServletRequestBuilder criarRequest(String jsonRequested, String acao) {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API + acao)
													.accept(MEDIA_TYPE_JSON)
													.contentType(MEDIA_TYPE_JSON)
													.content(jsonRequested);
		return request;
	}
}

*/

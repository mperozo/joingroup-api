package com.mperozo.joingroup.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.repository.CampanhaRepository;
import com.mperozo.joingroup.service.UsuarioService;
import com.mperozo.joingroup.service.impl.validation.CampanhaValidator;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CampanhaServiceUnitTest {

	@MockBean
	private CampanhaRepository campanhaRepositoryMock;
	
	@MockBean
	private CampanhaValidator campanhaValidatorMock;
	
	@MockBean
	private UsuarioService usuarioServiceMock;

	@SpyBean
	private CampanhaServiceImpl campanhaService;

	@Test
	public void deveIncluirCampanhaComSucesso() {
		
		Campanha campanha = TestUtils.criarCampanha(TestUtils.NOME_CAMPANHA_TESTE, 
													TestUtils.EMPRESA_CAMPANHA_TESTE,
													TestUtils.LINK_CAMPANHA_TESTE,
													TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE), 
													TestUtils.GROUP_CLICL_LIMIT_TESTE, 
													LocalDateTime.now());
		
		lenient().doNothing().when(campanhaValidatorMock).validarCampanha(campanha);
		lenient().when(campanhaRepositoryMock.save(campanha)).thenReturn(campanha);
		
		Campanha result = campanhaService.salvarCampanha(campanha);
		
		assertThat(result).isEqualToComparingFieldByField(campanha);
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

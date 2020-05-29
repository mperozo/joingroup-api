package com.mperozo.joingroup.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.exception.BusinessException;
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
	public void deveBuscarCampanhasDeUmUsuarioComSucesso() {
		
		Usuario usuario = TestUtils.criarUsuarioDefault();
		
		List<Campanha> campanhasDoUsuario = new LinkedList<Campanha>();
		campanhasDoUsuario.add(TestUtils.criarCampanhaDefault());
		campanhasDoUsuario.add(TestUtils.criarCampanhaDefault());
		
		lenient().when(usuarioServiceMock.buscarPorId(Mockito.any(Long.class))).thenReturn(usuario);
		lenient().when(campanhaRepositoryMock.findByUsuarioResponsavel(usuario)).thenReturn(campanhasDoUsuario);
		
		List<Campanha> resultCampanhasDoUsuario = campanhaService.buscarPorUsuarioResponsavel(1L);
		
		assertThat(resultCampanhasDoUsuario).hasSameSizeAs(campanhasDoUsuario);
		assertThat(resultCampanhasDoUsuario).isEqualTo(campanhasDoUsuario);
	}
	
	@Test
	public void deveIncluirCampanhaComSucesso() {
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		
		lenient().doNothing().when(campanhaValidatorMock).validarCampanha(campanha);
		lenient().when(campanhaRepositoryMock.save(campanha)).thenReturn(campanha);
		
		Campanha result = campanhaService.salvarCampanha(campanha);
		
		assertThat(result).isEqualToComparingFieldByField(campanha);
	}
	
	@Test
	public void deveAtualizarCampanhaComSucesso() {
		
		Campanha campanhaAntiga = TestUtils.criarCampanhaDefault();
		campanhaAntiga.setId(1L);
		
		Campanha campanhaComNovosDados = TestUtils.criarCampanhaDefault();
		campanhaComNovosDados.setNome("Novo Nome Campanha");
		
		lenient().doNothing().when(campanhaValidatorMock).validarAlteracaoDeCampanha(campanhaComNovosDados, campanhaAntiga);
		
		lenient().when(campanhaRepositoryMock.findById(campanhaAntiga.getId())).thenReturn(Optional.of(campanhaAntiga));
		lenient().when(campanhaRepositoryMock.saveAndFlush(campanhaAntiga)).thenReturn(campanhaAntiga);
		
		Campanha result = campanhaService.atualizarCampanha(campanhaAntiga.getId(), campanhaComNovosDados);
		
		assertThat(campanhaComNovosDados.getNome()).isEqualTo(result.getNome());
	}
	
	@Test
	public void deveDeletarCampanha() {
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		campanha.setId(1L);
		
		doReturn(Optional.of(campanha)).when(campanhaRepositoryMock).findById(campanha.getId());
		
		campanhaService.deletar(campanha.getId());
		
		verify(campanhaRepositoryMock, Mockito.times(1)).delete(campanha);
	}
	
	@Test
	public void deveLancarExceptionAoTentarDeletarCampanha() {
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			campanhaService.deletar(1L);
		});

		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Não foi encontrada a campanha a ser excluída de ID: 1");
		
		verify(campanhaRepositoryMock, Mockito.times(0)).delete(Mockito.any(Campanha.class));
	}

}

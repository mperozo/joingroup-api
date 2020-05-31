package com.mperozo.joingroup.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

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
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.model.repository.GrupoRepository;
import com.mperozo.joingroup.service.CampanhaService;
import com.mperozo.joingroup.service.impl.validation.GrupoValidator;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class GrupoServiceUnitTest {

	@MockBean
	private GrupoRepository grupoRepositoryMock;
	
	@MockBean
	private GrupoValidator grupoValidatorMock;
	
	@MockBean
	private CampanhaService campanhaServiceMock;

	@SpyBean
	private GrupoServiceImpl grupoService;
	
	@Test
	public void deveBuscarGruposDeUmaCampanhaComSucesso() {
		
		List<Grupo> grupos = TestUtils.criarListaDeGruposDefault(3);
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		campanha.setId(1L);
		
		grupos.stream().forEach(grupo -> grupo.setCampanha(campanha));
		
		lenient().when(campanhaServiceMock.buscarPorId(campanha.getId())).thenReturn(campanha);
		lenient().when(grupoRepositoryMock.findByCampanha(campanha)).thenReturn(grupos);
		
		List<Grupo> resultGruposDaCampanha = grupoService.buscarPorCampanha(1L);
		
		assertThat(resultGruposDaCampanha).hasSameSizeAs(grupos);
		assertThat(resultGruposDaCampanha).isEqualTo(grupos);
	}
	
	@Test
	public void deveIncluirGrupoComSucesso() {
		
		Grupo grupo = TestUtils.criarGrupoDefault();
		
		lenient().doNothing().when(grupoValidatorMock).validarGrupo(grupo);
		lenient().when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
		
		Grupo result = grupoService.salvarGrupo(grupo);
		
		assertThat(result).isEqualToComparingFieldByField(grupo);
	}
	
	@Test
	public void deveAtualizarGrupoComSucesso() {
		
		Grupo grupoAntigo = TestUtils.criarGrupoDefault();
		grupoAntigo.setId(1L);
		
		Grupo grupoComNovosDados = TestUtils.criarGrupoDefault();
		grupoComNovosDados.setNome("Novo Nome Grupo");
		grupoComNovosDados.setTotalRedirect(500);
		grupoComNovosDados.setUrl("Nova URL");
		
		lenient().doNothing().when(grupoValidatorMock).validarAlteracaoDeGrupo(grupoComNovosDados, grupoAntigo);
		
		lenient().when(grupoRepositoryMock.findById(grupoAntigo.getId())).thenReturn(Optional.of(grupoAntigo));
		lenient().when(grupoRepositoryMock.saveAndFlush(grupoAntigo)).thenReturn(grupoAntigo);
		
		Grupo result = grupoService.atualizarGrupo(grupoAntigo.getId(), grupoComNovosDados);
		
		assertThat(grupoComNovosDados.getNome()).isEqualTo(result.getNome());
	}
	
	@Test
	public void deveDeletarGrupo() {
		
		Grupo grupo = TestUtils.criarGrupoDefault();
		grupo.setId(1L);
		
		doReturn(Optional.of(grupo)).when(grupoRepositoryMock).findById(grupo.getId());
		
		grupoService.deletar(grupo.getId());
		
		verify(grupoRepositoryMock, Mockito.times(1)).delete(grupo);
	}
	
	@Test
	public void deveLancarExceptionAoTentarDeletarGrupo() {
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			grupoService.deletar(1L);
		});

		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Não foi encontrado o grupo a ser excluído de ID: 1");
		
		verify(grupoRepositoryMock, Mockito.times(0)).delete(Mockito.any(Grupo.class));
	}

}

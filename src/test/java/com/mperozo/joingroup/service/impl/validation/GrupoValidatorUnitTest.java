package com.mperozo.joingroup.service.impl.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.model.repository.GrupoRepository;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class GrupoValidatorUnitTest {
	
	private static final String URL_TESTE = "www.whatsapp/grupoteste1";
	private static final String NOME_GRUPO_TESTE = "Whatsapp Grupo #1";

	@MockBean
	private GrupoRepository grupoRepositoryMock;

	@SpyBean
	private GrupoValidator grupoValidator;
	
	@Test
	public void deveValidarSeURLJaExisteDentroDeUmaCampanhaComSucesso() {
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		
		List<Grupo> grupos = TestUtils.criarListaDeGruposDefault(3); 
		
		when(grupoRepositoryMock.findByCampanha(campanha)).thenReturn(grupos);
		
		grupoValidator.validarURLExistenteEntreGruposDeUmaMesmaCampanha(URL_TESTE, campanha);
	}
	
	@Test
	public void deveLancarExceptionAoValidarSeURLJaExisteDentroDeUmaCampanha() {
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		
		List<Grupo> grupos = TestUtils.criarListaDeGruposDefault(3);
		grupos.get(0).setUrl(URL_TESTE);

		when(grupoRepositoryMock.findByCampanha(campanha)).thenReturn(grupos);
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			grupoValidator.validarURLExistenteEntreGruposDeUmaMesmaCampanha(URL_TESTE, campanha);;
		});
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Já existe um grupo com a URL: " + URL_TESTE  + " para a campanha " + campanha.getNome());
	}
	
	@Test
	public void deveValidarSeNomeJaExisteNacampanhaComSucesso() {
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		
		List<Grupo> grupos = TestUtils.criarListaDeGruposDefault(3);
		
		when(grupoRepositoryMock.findByCampanha(campanha)).thenReturn(grupos);
		
		grupoValidator.validarNomeExistenteNaCampanhaDoGrupo(NOME_GRUPO_TESTE, campanha);
	}
	
	@Test
	public void deveLancarExceptionAoValidarSeNomeJaExisteNacampanha() {
		
		Campanha campanha = TestUtils.criarCampanhaDefault();
		
		List<Grupo> grupos = TestUtils.criarListaDeGruposDefault(3);
		grupos.get(0).setNome(NOME_GRUPO_TESTE);
		
		when(grupoRepositoryMock.findByCampanha(campanha)).thenReturn(grupos);
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			grupoValidator.validarNomeExistenteNaCampanhaDoGrupo(NOME_GRUPO_TESTE, campanha);
		});
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Já existe um grupo com o nome " + NOME_GRUPO_TESTE + " para a campanha " + campanha.getNome());
	}
	
	@Test
	public void deveValidarSeDataDeValidadeEhAnteriorADataCorrenteComSucesso() {
		
		Grupo grupoAntigo = TestUtils.criarGrupoDefault();
		Grupo grupoComDadosAtualizados = TestUtils.criarGrupoDefault();
		grupoComDadosAtualizados.setCampanha(TestUtils.criarCampanhaDefault());
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			grupoValidator.validarAlteracaoDeGrupo(grupoComDadosAtualizados, grupoAntigo);
		});
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Não é possível alterar a campanha de um grupo.");
	}

}

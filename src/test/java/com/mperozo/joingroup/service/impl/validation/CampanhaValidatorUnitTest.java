package com.mperozo.joingroup.service.impl.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.repository.CampanhaRepository;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CampanhaValidatorUnitTest {
	
	private static final String URL_TESTE = "www.dominio.com/empresa/link";
	private static final String NOME_CAMPANHA_TESTE = "Promoção imperdível de verão!";

	@MockBean
	private CampanhaRepository campanhaRepositoryMock;

	@SpyBean
	private CampanhaValidator campanhaValidator;
	
	@Test
	public void deveValidarSeURLJaExisteComSucesso() {
		
		List<Campanha> campanhas = TestUtils.criarListaDeCampanhasDefault(3); 
		
		when(campanhaRepositoryMock.findAll()).thenReturn(campanhas);
		
		campanhaValidator.validarURLExistente(URL_TESTE);
	}
	
	@Test
	public void deveLancarExceptionAoValidarSeURLJaExiste() {
		
		List<Campanha> campanhas = TestUtils.criarListaDeCampanhasDefault(3);
		campanhas.get(0).setUrl(URL_TESTE);

		when(campanhaRepositoryMock.findAll()).thenReturn(campanhas);
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			campanhaValidator.validarURLExistente(URL_TESTE);
		});
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Já existe uma campanha com a URL: " + URL_TESTE);
	}
	
	@Test
	public void deveValidarSeNomeJaExisteEntreCampanhasDoUsuarioComSucesso() {
		
		Usuario usuario = TestUtils.criarUsuarioDefault();
		List<Campanha> campanhas = TestUtils.criarListaDeCampanhasDefault(3);
		
		when(campanhaRepositoryMock.findByUsuarioResponsavel(usuario)).thenReturn(campanhas);
		
		campanhaValidator.validarNomeExistenteEntreCampanhasDoUsuario(NOME_CAMPANHA_TESTE, usuario);
	}
	
	@Test
	public void deveLancarExceptionAoValidarSeNomeJaExisteEntreCampanhasDoUsuario() {
		
		Usuario usuario = TestUtils.criarUsuarioDefault();
		List<Campanha> campanhas = TestUtils.criarListaDeCampanhasDefault(3);
		campanhas.get(0).setNome(NOME_CAMPANHA_TESTE);
		
		when(campanhaRepositoryMock.findByUsuarioResponsavel(usuario)).thenReturn(campanhas);
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			campanhaValidator.validarNomeExistenteEntreCampanhasDoUsuario(NOME_CAMPANHA_TESTE, usuario);
		});
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Já existe uma campanha com o nome: " + NOME_CAMPANHA_TESTE);
	}
	
	@Test
	public void deveValidarSeDataDeValidadeEhAnteriorADataCorrenteComSucesso() {
		
		campanhaValidator.validarDataValidadeAnteriorADataCorrente(LocalDate.now());
	}
	
	@Test
	public void deveLancarExceptionAoValidarSeDataDeValidadeEhAnteriorADataCorrente() {
		
		Exception exception = assertThrows(BusinessException.class, () -> {
			campanhaValidator.validarDataValidadeAnteriorADataCorrente(LocalDate.of(1999, 1, 1));
		});
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Data de validade da campanha deve ser posterior a data corrente.");
	}

}

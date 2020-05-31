
package com.mperozo.joingroup.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CampanhaRepositoryIntegrationTest {

	@Autowired
	private CampanhaRepository campanhaRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void devePersistirUmaCampanhaNaBaseDeDados() {
		
		Usuario usuarioResponsavel = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		Campanha campanha = TestUtils.criarCampanha( "Campanha Teste", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel, 200, LocalDateTime.now() );
		
		Usuario usuarioResponsavelSalvo = entityManager.persist(usuarioResponsavel);
		Campanha campanhaSalva = entityManager.persist(campanha);
		
		assertThat(campanhaSalva.getId()).isNotNull();
		assertThat(usuarioResponsavelSalvo).isEqualTo(campanhaSalva.getUsuarioResponsavel());
	}
	
	@Test
	public void deveBuscarCampanhasDeUmUsuarioResponsavel() {
		
		Usuario usuarioResponsavel1 = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		Campanha campanha1 = TestUtils.criarCampanha( "Campanha Teste 1", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel1, 200, LocalDateTime.now() );
		Campanha campanha2 = TestUtils.criarCampanha( "Campanha Teste 2", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel1, 200, LocalDateTime.now() );
		
		Usuario usuarioResponsavel2 = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		Campanha campanha3 = TestUtils.criarCampanha( "Campanha Teste 3", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel2, 200, LocalDateTime.now() );

		entityManager.persist(usuarioResponsavel1);
		entityManager.persist(campanha1);
		entityManager.persist(campanha2);
		entityManager.persist(usuarioResponsavel2);
		entityManager.persist(campanha3);
		
		List<Campanha> resultCampanhasUsuario1 = campanhaRepository.findByUsuarioResponsavel(usuarioResponsavel1);
		List<Campanha> resultCampanhasUsuario2 = campanhaRepository.findByUsuarioResponsavel(usuarioResponsavel2);

		assertThat(2).isEqualTo(resultCampanhasUsuario1.size());
		assertThat(1).isEqualTo(resultCampanhasUsuario2.size());
	}
	
}


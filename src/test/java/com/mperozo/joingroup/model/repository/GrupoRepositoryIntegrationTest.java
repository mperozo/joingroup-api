
package com.mperozo.joingroup.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class GrupoRepositoryIntegrationTest {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void devePersistirUmGrupoNaBaseDeDados() {
		
		Usuario usuarioResponsavel = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		Campanha campanha = TestUtils.criarCampanha( "Campanha Teste", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel, 200, LocalDateTime.now() );
		Grupo grupo = TestUtils.criarGrupo(TestUtils.NOME_GRUPO_TESTE, TestUtils.URL_GRUPO_TESTE, campanha, LocalDateTime.now());
		
		Usuario usuarioResponsavelSalvo = entityManager.persist(usuarioResponsavel);
		Campanha campanhaSalva = entityManager.persist(campanha);
		Grupo grupoSalvo = entityManager.persist(grupo);
		
		assertThat(grupoSalvo.getId()).isNotNull();
		assertThat(campanhaSalva).isEqualTo(grupoSalvo.getCampanha());
		assertThat(usuarioResponsavelSalvo).isEqualTo(grupoSalvo.getCampanha().getUsuarioResponsavel());
	}
	
	@Test
	public void deveBuscarGruposDeUmaCampanha() {
		
		Usuario usuarioResponsavel = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		Campanha campanha1 = TestUtils.criarCampanha( "Campanha Teste 1", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel, 200, LocalDateTime.now() );
		Campanha campanha2 = TestUtils.criarCampanha( "Campanha Teste 2", TestUtils.EMPRESA_CAMPANHA_TESTE, TestUtils.LINK_CAMPANHA_TESTE, usuarioResponsavel, 200, LocalDateTime.now() );
		
		List<Grupo> grupos1 = TestUtils.criarListaDeGruposDefault(2);
		List<Grupo> grupos2 = TestUtils.criarListaDeGruposDefault(3);
		grupos1.stream().forEach(grupo -> grupo.setCampanha(campanha1));
		grupos2.stream().forEach(grupo -> grupo.setCampanha(campanha2));

		entityManager.persist(usuarioResponsavel);
		entityManager.persist(campanha1);
		entityManager.persist(campanha2);
		grupos1.stream().forEach(grupo -> entityManager.persist(grupo));
		grupos2.stream().forEach(grupo -> entityManager.persist(grupo));
		
		List<Grupo> resultGruposCampanha1 = grupoRepository.findByCampanha(campanha1);
		List<Grupo> resultGruposCampanha2 = grupoRepository.findByCampanha(campanha2);

		assertThat(2).isEqualTo(resultGruposCampanha1.size());
		assertThat(3).isEqualTo(resultGruposCampanha2.size());
	}
	
}


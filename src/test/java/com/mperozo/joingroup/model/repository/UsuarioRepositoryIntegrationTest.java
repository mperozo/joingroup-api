
package com.mperozo.joingroup.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryIntegrationTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void deveVerificarExistenciaDeUmEmail() {

		Usuario usuario = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		entityManager.persist(usuario);

		boolean result = usuarioRepository.existsByEmail(TestUtils.EMAIL_USUARIO_TESTE);

		assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {

		boolean result = usuarioRepository.existsByEmail(TestUtils.EMAIL_USUARIO_TESTE);

		assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		
		Usuario usuario = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
		
		Usuario usuarioSalvo = entityManager.persist(usuario);
		
		assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		
		Usuario usuario = TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);

		Usuario usuarioSalvo = entityManager.persist(usuario);
		Optional<Usuario> result = usuarioRepository.findByEmail(TestUtils.EMAIL_USUARIO_TESTE);

		assertThat(result.isPresent()).isTrue();
		assertThat(usuarioSalvo).isEqualToComparingFieldByField(result.get());
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		
		Optional<Usuario> result = usuarioRepository.findByEmail(TestUtils.EMAIL_USUARIO_TESTE);
		assertThat(result.isPresent()).isFalse();
	}
	
}


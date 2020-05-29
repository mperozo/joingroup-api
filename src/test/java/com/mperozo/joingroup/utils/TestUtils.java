package com.mperozo.joingroup.utils;

import java.time.LocalDateTime;

import com.mperozo.joingroup.api.dto.UsuarioDTO;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.enums.StatusUsuarioEnum;

public class TestUtils {

	public static final String SENHA_USUARIO_TESTE = "SENHA1";
	public static final String EMAIL_USUARIO_TESTE = "emailparateste@email.com.br";
	
	public static final String NOME_CAMPANHA_TESTE = "Campanha Teste";
	public static final String DOMINIO = "http://www.joingroup.com";
	public static final String EMPRESA_CAMPANHA_TESTE = "mperozo";
	public static final String LINK_CAMPANHA_TESTE = "promocao-verao";
	public static final Integer GROUP_CLICL_LIMIT_TESTE = 200;
	
	private TestUtils() {}
	
	/* Métodos auxiliares */
	
	public static Usuario criarUsuario(String email, String senha) {
		return Usuario.builder()
				.nome("Nome")
				.senha(senha)
				.email(email)
				.status(StatusUsuarioEnum.ATIVO)
				.dataHoraInclusao(LocalDateTime.now())
				.build();
	}
	
	public static UsuarioDTO criarUsuarioDTO(String email, String senha) {

		return UsuarioDTO.builder()
				.email(email)
				.senha(senha)
				.build();
	}

	public static Campanha criarCampanha(String nome, String empresa, String link, Usuario usuarioResponsavel, Integer groupClickLimit, LocalDateTime dataHoraInclusao) {
		return Campanha.builder()
					.nome(nome)
					.empresa(empresa)
					.link(link)
					.url(criarURL(empresa, link))
					.usuarioResponsavel(usuarioResponsavel)
					.groupClickLimit(groupClickLimit)
					.dataHoraInclusao(dataHoraInclusao).build();
	}
	
	public static String criarURL(String empresa, String link) {
		return DOMINIO + "/" + empresa + "/" + link;
	}
}

package com.mperozo.joingroup.utils;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.mperozo.joingroup.api.dto.UsuarioDTO;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.enums.StatusUsuarioEnum;
import com.mperozo.joingroup.model.enums.TipoRedirectEnum;

public class TestUtils {

	public static final String SENHA_USUARIO_TESTE = "SENHA1";
	public static final String EMAIL_USUARIO_TESTE = "emailparateste@email.com.br";
	
	public static final String NOME_CAMPANHA_TESTE = "Campanha Teste";
	public static final String DOMINIO = "http://www.joingroup.com";
	public static final String EMPRESA_CAMPANHA_TESTE = "mperozo";
	public static final String LINK_CAMPANHA_TESTE = "promocao-verao";
	public static final Integer GROUP_CLICL_LIMIT_TESTE = 200;
	
	public static final String NOME_GRUPO_TESTE = "Nome Grupo teste";
	public static final String URL_GRUPO_TESTE = "www.whatsapp.grupo/01";
	
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

	public static Campanha criarCampanha(String nome, String empresa, String link, Usuario usuarioResponsavel, Integer groupClickLimit, TipoRedirectEnum tipoRedirect, LocalDateTime dataHoraInclusao) {
		return Campanha.builder()
					.nome(nome)
					.empresa(empresa)
					.link(link)
					.url(criarURLCampanha(empresa, link))
					.usuarioResponsavel(usuarioResponsavel)
					.groupClickLimit(groupClickLimit)
					.tipoRedirect(tipoRedirect)
					.dataHoraInclusao(dataHoraInclusao).build();
	}
	
	public static String criarURLCampanha(String empresa, String link) {
		return DOMINIO + "/" + empresa + "/" + link;
	}

	public static Campanha criarCampanhaDefault() {
		
		return criarCampanha(TestUtils.NOME_CAMPANHA_TESTE, 
								TestUtils.EMPRESA_CAMPANHA_TESTE,
								TestUtils.LINK_CAMPANHA_TESTE,
								TestUtils.criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE), 
								TestUtils.GROUP_CLICL_LIMIT_TESTE,
								TipoRedirectEnum.BOTAO,
								LocalDateTime.now());
	}
	
	public static List<Campanha> criarListaDeCampanhasDefault(int quantidadeCampanhas) {
		
		List<Campanha> campanhas = new LinkedList<Campanha>();
		
		for(int i = 0; i < quantidadeCampanhas; i++) {
			campanhas.add(criarCampanhaDefault());
		}
		
		return campanhas;
	}

	public static Usuario criarUsuarioDefault() {
		return criarUsuario(TestUtils.EMAIL_USUARIO_TESTE, TestUtils.SENHA_USUARIO_TESTE);
	}

	public static Grupo criarGrupoDefault() {
		return criarGrupo(TestUtils.NOME_GRUPO_TESTE, TestUtils.URL_GRUPO_TESTE, criarCampanhaDefault(), LocalDateTime.now());
	}

	public static Grupo criarGrupo(String nomeGrupoTeste, String urlGrupoTeste, Campanha campanha, LocalDateTime dataHoraInclusao) {
		return Grupo.builder().campanha(campanha)
				.nome(nomeGrupoTeste)
				.url(urlGrupoTeste)
				.dataHoraInclusao(dataHoraInclusao).build();
	}
	
	public static List<Grupo> criarListaDeGruposDefault(int quantidadeGrupos) {
		
		List<Grupo> grupos = new LinkedList<Grupo>();
		
		for(int i = 0; i < quantidadeGrupos; i++) {
			grupos.add(criarGrupoDefault());
		}
		
		return grupos;
	}
}

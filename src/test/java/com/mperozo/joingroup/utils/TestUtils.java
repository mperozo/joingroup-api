package com.mperozo.joingroup.utils;

import java.time.LocalDateTime;

import com.mperozo.joingroup.api.dto.UsuarioDTO;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.enums.StatusUsuarioEnum;

public class TestUtils {

	public static final String SENHA_PARA_TESTE = "SENHA1";
	public static final String EMAIL_PARA_TESTE = "emailparateste@email.com.br";
	
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
}

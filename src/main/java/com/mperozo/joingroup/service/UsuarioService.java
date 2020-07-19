package com.mperozo.joingroup.service;

import com.mperozo.joingroup.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	Usuario buscarPorId(Long id);
	
	Usuario buscarPorEmail(String email);

	/**
	 * Verifica se já existe o e-mail na base de dados
	 * 
	 * @param email
	 */
	void verificarSeEmailJaEstaCadastrado(String email);
	
	Usuario obterUsuarioAutenticado();

}

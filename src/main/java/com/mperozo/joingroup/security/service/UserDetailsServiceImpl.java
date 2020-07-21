package com.mperozo.joingroup.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.service.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioService.buscarPorEmail(email);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado para o e-mail: " + email);
		}

		return UserDetailsImpl.build(usuario);
	}

}

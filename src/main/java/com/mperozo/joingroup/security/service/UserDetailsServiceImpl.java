package com.mperozo.joingroup.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

		// TODO definir se vai utilizar outras ROLES
		List<GrantedAuthority> listGrantAuthority = new ArrayList<GrantedAuthority>();
		listGrantAuthority.add(new SimpleGrantedAuthority("ROLE_" + "USER"));

		boolean accountNonLocked = true;
		boolean enabledUser = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		User user = new User(usuario.getEmail(), usuario.getSenha(), enabledUser, accountNonExpired, credentialsNonExpired, accountNonLocked, listGrantAuthority);

		return user;
	}

}

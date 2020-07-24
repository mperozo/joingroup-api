package com.mperozo.joingroup.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mperozo.joingroup.api.payload.request.RegisterRequest;
import com.mperozo.joingroup.exception.AuthenticationException;
import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Role;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.enums.RolesEnum;
import com.mperozo.joingroup.model.enums.StatusUsuarioEnum;
import com.mperozo.joingroup.model.repository.UsuarioRepository;
import com.mperozo.joingroup.service.RoleService;
import com.mperozo.joingroup.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	private static final String USUARIO_OU_SENHA_INVALIDOS = "Usuario ou senha inválidos.";

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {

		// TODO MVP de autenticação. Evoluir futuramente.
		
		//Usuario usuarioAutenticado = authenticationProvider.autenticar(email, senha);
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			logger.info("Tentativa de autenticação: e-mail " + email + " inexistente.");
			throw new AuthenticationException(USUARIO_OU_SENHA_INVALIDOS);
		}

		if (!usuario.get().getSenha().equals(senha)) {
			logger.info("Tentativa de autenticação: e-mail: " + email + ". Senha digitada inválida.");
			throw new AuthenticationException(USUARIO_OU_SENHA_INVALIDOS);
		}

		return usuario.get();
	}
	
	public Usuario registrarUsuario(RegisterRequest registerRequest) {

		Usuario usuario = new Usuario();
		
		usuario.setEmail(registerRequest.getEmail());
		usuario.setNome(registerRequest.getNome());
		usuario.setSenha(registerRequest.getSenha());
		
		return salvarUsuario(usuario);
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		
		validarUsuarioParaSalvar(usuario);
		
		associarRoleUSER(usuario);
		
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setDataHoraInclusao(LocalDateTime.now());
		usuario.setStatus(StatusUsuarioEnum.ATIVO);
		
		return usuarioRepository.save(usuario);
	}

	/**
	 * Associa a role "default" ROLE_USER ao usuário.
	 */
	private void associarRoleUSER(Usuario usuario) {
		
		Role roleUser = roleService.buscarPorNome(RolesEnum.ROLE_USER);

		Set<Role> roles = new HashSet<Role>();
		roles.add(roleUser);
		usuario.setRoles(roles);
	}

	protected void validarUsuarioParaSalvar(Usuario usuario) {

		//TODO Validar se senha atende aos requisitos
		
		verificarSeEmailJaEstaCadastrado(usuario.getEmail());
	}

	@Override
	public void verificarSeEmailJaEstaCadastrado(String email) {
		if (usuarioRepository.existsByEmail(email)) {
			throw new BusinessException("E-mail já cadastrado.");
		}
	}

	@Override
	public Usuario buscarPorId(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		if(!usuario.isPresent()) {
			throw new BusinessException("Usuário não encontrado na base de dados: ID = " + id );
		}
		
		return usuario.get();
	}

	@Override
	public Usuario buscarPorEmail(String email) {

		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new BusinessException("Usuário não encontrado na base de dados para o e-mail = " + email );
		}
		
		return usuario.get();
	}

	@Override
	public Usuario obterUsuarioAutenticado() {
		// TODO Auto-generated method stub
		return null;
	}

}

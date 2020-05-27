package com.mperozo.joingroup.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findById(Long id);
	
	boolean existsByEmail(String email);
}

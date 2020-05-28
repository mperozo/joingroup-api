package com.mperozo.joingroup.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	Optional<Grupo> findById(Long id);

	Optional<Grupo> findByCampanha(Long id);
}

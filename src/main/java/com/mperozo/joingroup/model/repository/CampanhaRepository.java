package com.mperozo.joingroup.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Campanha;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

	List<Campanha> findByUsuarioResponsavel(Long idUsuarioResponsavel);
}

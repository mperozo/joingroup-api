package com.mperozo.joingroup.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

	List<Campanha> findByUsuarioResponsavel(Usuario usuarioResponsavel);
}

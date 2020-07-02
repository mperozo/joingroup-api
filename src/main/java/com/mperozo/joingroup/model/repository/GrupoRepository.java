package com.mperozo.joingroup.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	List<Grupo> findByCampanha(Campanha campanha);

	List<Grupo> deleteByCampanha(Campanha campanha);
}

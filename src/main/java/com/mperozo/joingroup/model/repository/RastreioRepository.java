package com.mperozo.joingroup.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Rastreio;

public interface RastreioRepository extends JpaRepository<Rastreio, Long> {

	List<Rastreio> findByCampanha(Campanha campanha);
}

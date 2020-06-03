package com.mperozo.joingroup.service;

import java.util.List;

import com.mperozo.joingroup.model.entity.Rastreio;

public interface RastreioService {

	Rastreio buscarPorId(Long id);
	
	List<Rastreio> buscarPorCampanha(Long id);
	
	Rastreio salvarRastreio(Rastreio rastreio);

	void deletar(Long id);

	Rastreio atualizarRastreio(Long id, Rastreio rastreioComNovosDados);
}

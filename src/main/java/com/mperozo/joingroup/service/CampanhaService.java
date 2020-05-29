package com.mperozo.joingroup.service;

import java.util.List;

import com.mperozo.joingroup.model.entity.Campanha;

public interface CampanhaService {

	List<Campanha> buscarPorUsuarioResponsavel(Long idUsuarioResponsavel);

	Campanha salvarCampanha(Campanha campanha);

	Campanha buscarPorId(Long id);

	Campanha atualizarCampanha(Long id, Campanha campanhaComNovosDados);

	void deletar(Long id);
}

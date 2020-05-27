package com.mperozo.joingroup.service;

import com.mperozo.joingroup.model.entity.Campanha;

public interface CampanhaService {

	Campanha buscarPorUsuarioResponsavel(Long idUsuarioResponsavel);

	Campanha salvarCampanha(Campanha campanha);

	Campanha buscarPorId(Long id);
}

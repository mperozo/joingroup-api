package com.mperozo.joingroup.service;

import java.util.List;

import com.mperozo.joingroup.model.entity.Grupo;

public interface GrupoService {

	Grupo buscarPorId(Long id);
	
	List<Grupo> buscarPorCampanha(Long id);
	
	Grupo salvarGrupo(Grupo grupo);

	void deletar(Long id);

	Grupo atualizarGrupo(Long id, Grupo grupoComNovosDados);
}

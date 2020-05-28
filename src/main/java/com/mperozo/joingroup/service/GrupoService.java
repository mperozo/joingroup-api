package com.mperozo.joingroup.service;

import com.mperozo.joingroup.model.entity.Grupo;

public interface GrupoService {

	Grupo buscarPorId(Long id);
	
	Grupo buscarPorIdCampanha(Long id);
	
	Grupo salvarGrupo(Grupo grupo);

}

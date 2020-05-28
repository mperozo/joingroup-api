package com.mperozo.joingroup.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.model.repository.GrupoRepository;
import com.mperozo.joingroup.service.GrupoService;


@Service
public class GrupoServiceImpl implements GrupoService {

	Logger logger = LoggerFactory.getLogger(GrupoServiceImpl.class);

	@Autowired
	private GrupoRepository grupoRepository;

	public GrupoServiceImpl(GrupoRepository grupoRepository) {
		super();
		this.grupoRepository = grupoRepository;
	}
	
	@Override
	public Grupo buscarPorId(Long id) {

		Optional<Grupo> grupo = grupoRepository.findById(id);
		
		if(!grupo.isPresent()) {
			throw new BusinessException("Grupo não encontrado na base de dados para ID = " + id );
		}
		
		return grupo.get();
	}

	@Override
	public Grupo buscarPorIdCampanha(Long id) {
		Optional<Grupo> grupo = grupoRepository.findByCampanha(id);
		
		if(!grupo.isPresent()) {
			throw new BusinessException("Grupo não encontrado na base de dados para campanha de ID = " + id );
		}
		
		return grupo.get();
	}

	@Override
	@Transactional
	public Grupo salvarGrupo(Grupo grupo) {
		
		grupo.setDataHoraInclusao(LocalDateTime.now());
		
		return grupoRepository.save(grupo);
	}

}

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
import com.mperozo.joingroup.model.enums.StatusCampanhaEnum;
import com.mperozo.joingroup.model.repository.CampanhaRepository;
import com.mperozo.joingroup.service.CampanhaService;
import com.mperozo.joingroup.service.UsuarioService;


@Service
public class CampanhaServiceImpl implements CampanhaService {

	Logger logger = LoggerFactory.getLogger(CampanhaServiceImpl.class);

	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	public CampanhaServiceImpl(CampanhaRepository campanhaRepository) {
		super();
		this.campanhaRepository = campanhaRepository;
	}

	@Override
	public Campanha buscarPorUsuarioResponsavel(Long id) {
		Optional<Campanha> campanha = campanhaRepository.findByUsuarioResponsavel(id);
		
		if(!campanha.isPresent()) {
			throw new BusinessException("Campanha não encontrada na base de dados para usuário de ID = " + id );
		}
		
		return campanha.get();
	}

	@Override
	@Transactional
	public Campanha salvarCampanha(Campanha campanha) {
		
		campanha.setUsuarioResponsavel(usuarioService.obterUsuarioAutenticado());
		campanha.setStatus(StatusCampanhaEnum.ATIVO);
		campanha.setDataHoraInclusao(LocalDateTime.now());
		
		return campanhaRepository.save(campanha);
	}

	@Override
	public Campanha buscarPorId(Long id) {

		Optional<Campanha> campanha = campanhaRepository.findById(id);
		
		if(!campanha.isPresent()) {
			throw new BusinessException("Campanha não encontrada na base de dados para ID = " + id );
		}
		
		return campanha.get();
	}

}

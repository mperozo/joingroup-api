package com.mperozo.joingroup.service.impl;

import java.time.LocalDateTime;
import java.util.List;
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
import com.mperozo.joingroup.service.CampanhaService;
import com.mperozo.joingroup.service.GrupoService;
import com.mperozo.joingroup.service.impl.validation.GrupoValidator;


@Service
public class GrupoServiceImpl implements GrupoService {

	Logger logger = LoggerFactory.getLogger(GrupoServiceImpl.class);

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CampanhaService campanhaService;
	
	@Autowired
	private GrupoValidator grupoValidator;

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
	public List<Grupo> buscarPorCampanha(Long idCampanha) {
		
		Campanha campanha = campanhaService.buscarPorId(idCampanha);
		List<Grupo> grupos = grupoRepository.findByCampanha(campanha);
		
		return grupos;
	}

	@Override
	@Transactional
	public Grupo salvarGrupo(Grupo grupo) {
		
		grupoValidator.validarGrupo(grupo);
		grupo.setDataHoraInclusao(LocalDateTime.now());
		
		return grupoRepository.save(grupo);
	}

	@Override
	public void deletar(Long id) {

		Optional<Grupo> grupoASerExcluido = grupoRepository.findById(id);
		
		if(!grupoASerExcluido.isPresent()) {
			throw new BusinessException("Não foi encontrado o grupo a ser excluído de ID: " + id);
		}
		
		grupoRepository.delete(grupoASerExcluido.get());
	}

	@Override
	@Transactional
	public Grupo atualizarGrupo(Long id, Grupo grupoComNovosDados) {
		
		Grupo grupoAntigo = buscarPorId(id);
		grupoValidator.validarAlteracaoDeGrupo(grupoComNovosDados, grupoAntigo);
		Grupo grupoAtualizado = atualizarGrupo(grupoComNovosDados, grupoAntigo);
		
		return grupoRepository.saveAndFlush(grupoAtualizado);
	}

	private Grupo atualizarGrupo(Grupo grupoComNovosDados, Grupo grupoAntigo) {
		
		grupoAntigo.setDataHoraAlteracao(LocalDateTime.now());
		grupoAntigo.setNome(grupoComNovosDados.getNome());
		grupoAntigo.setTotalRedirect(grupoComNovosDados.getTotalRedirect());
		grupoAntigo.setUrl(grupoComNovosDados.getUrl());
		
		return grupoAntigo;
	}

}

package com.mperozo.joingroup.service.impl;

import java.time.LocalDate;
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
import com.mperozo.joingroup.model.enums.StatusCampanhaEnum;
import com.mperozo.joingroup.model.repository.CampanhaRepository;
import com.mperozo.joingroup.service.CampanhaService;


@Service
public class CampanhaServiceImpl implements CampanhaService {

	Logger logger = LoggerFactory.getLogger(CampanhaServiceImpl.class);

	@Autowired
	private CampanhaRepository campanhaRepository;
	
	public CampanhaServiceImpl(CampanhaRepository campanhaRepository) {
		super();
		this.campanhaRepository = campanhaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Campanha> buscarPorUsuarioResponsavel(Long idUsuarioResponsavel) {
		List<Campanha> campanhas = campanhaRepository.findByUsuarioResponsavel(idUsuarioResponsavel);
		return campanhas;
	}

	@Override
	public Campanha buscarPorId(Long id) {

		Optional<Campanha> campanha = campanhaRepository.findById(id);
		
		if(!campanha.isPresent()) {
			throw new BusinessException("Campanha não encontrada na base de dados para ID = " + id );
		}
		
		return campanha.get();
	}
	
	@Override
	@Transactional
	public Campanha salvarCampanha(Campanha campanha) {
		
		campanha.setStatus(StatusCampanhaEnum.ATIVO);
		campanha.setDataHoraInclusao(LocalDateTime.now());
		
		if(campanha.getDataValidade() != null && campanha.getDataValidade().isBefore(LocalDate.now())) {
			throw new BusinessException("Data de validade da campanha deve ser posterior a data corrente.");
		}
		
		if(campanha.getUsuarioResponsavel() == null) {
			throw new BusinessException("Usuário responsável é obrigatório.");
		}
		
		return campanhaRepository.save(campanha);
	}

	@Override
	@Transactional
	public Campanha atualizarCampanha(Long id, Campanha campanhaComNovosDados) {

		Campanha campanhaAntiga = buscarPorId(id);
		Campanha campanhaAtualizada = atualizarCampanha(campanhaComNovosDados, campanhaAntiga);
		
		return campanhaRepository.saveAndFlush(campanhaAtualizada);
	}

	private Campanha atualizarCampanha(Campanha campanhaComNovosDados, Campanha campanhaAntiga) {
		
		validarAlteracaoDeCampanha(campanhaComNovosDados, campanhaAntiga);
		
		campanhaAntiga.setDataHoraAlteracao(LocalDateTime.now());
		campanhaAntiga.setDataValidade(campanhaComNovosDados.getDataValidade());
		campanhaAntiga.setGroupClickLimit(campanhaComNovosDados.getGroupClickLimit());
		campanhaAntiga.setNome(campanhaComNovosDados.getNome());
		campanhaAntiga.setTelefoneSuporte(campanhaComNovosDados.getTelefoneSuporte());
		campanhaAntiga.setUrl(campanhaComNovosDados.getUrl());
		campanhaAntiga.setEndUrl(campanhaComNovosDados.getEndUrl());
		
		return campanhaAntiga;
	}

	private void validarAlteracaoDeCampanha(Campanha campanhaComNovosDados, Campanha campanhaAntiga) {

		if(campanhaComNovosDados.getUsuarioResponsavel() != campanhaAntiga.getUsuarioResponsavel()) {
			throw new BusinessException("Não é possível alterar o usuário responsável pela campanha.");
		}
		
		if(campanhaComNovosDados.getStatus() != campanhaAntiga.getStatus()) {
			throw new BusinessException("Não é possível alterar o status de uma campanha através da edição de campanha.");
		}
	}

	@Override
	public void excluir(Long id) {
		campanhaRepository.deleteById(id);
	}

}

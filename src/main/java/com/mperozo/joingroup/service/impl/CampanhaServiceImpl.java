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
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.enums.StatusCampanhaEnum;
import com.mperozo.joingroup.model.repository.CampanhaRepository;
import com.mperozo.joingroup.service.CampanhaService;
import com.mperozo.joingroup.service.UsuarioService;
import com.mperozo.joingroup.service.impl.validation.CampanhaValidator;


@Service
public class CampanhaServiceImpl implements CampanhaService {
	
	//TODO colocar em um configuracaoService
	private static final String DOMINIO = "http://joingroup";

	Logger logger = LoggerFactory.getLogger(CampanhaServiceImpl.class);

	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CampanhaValidator campanhaValidator;
	
	public CampanhaServiceImpl(CampanhaRepository campanhaRepository) {
		super();
		this.campanhaRepository = campanhaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Campanha> buscarPorUsuarioResponsavel(Long idUsuarioResponsavel) {

		Usuario usuarioResponsavel = usuarioService.buscarPorId(idUsuarioResponsavel);
		
		if(usuarioResponsavel == null) {
			throw new BusinessException("Usuário não encontrado na base dados com o id = " + idUsuarioResponsavel);
		}
		
		List<Campanha> campanhas = campanhaRepository.findByUsuarioResponsavel(usuarioResponsavel);
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
		
		campanhaValidator.validarCampanha(campanha);

		campanha.setStatus(StatusCampanhaEnum.ATIVO);
		campanha.setDataHoraInclusao(LocalDateTime.now());
		
		return campanhaRepository.save(campanha);
	}

	@Override
	@Transactional
	public Campanha atualizarCampanha(Long id, Campanha campanhaComNovosDados) {

		Campanha campanhaAntiga = buscarPorId(id);
		campanhaValidator.validarAlteracaoDeCampanha(campanhaComNovosDados, campanhaAntiga);
		Campanha campanhaAtualizada = atualizarCampanha(campanhaComNovosDados, campanhaAntiga);
		
		return campanhaRepository.saveAndFlush(campanhaAtualizada);
	}

	private Campanha atualizarCampanha(Campanha campanhaComNovosDados, Campanha campanhaAntiga) {
		
		campanhaAntiga.setDataHoraAlteracao(LocalDateTime.now());
		campanhaAntiga.setDataValidade(campanhaComNovosDados.getDataValidade());
		campanhaAntiga.setGroupClickLimit(campanhaComNovosDados.getGroupClickLimit());
		campanhaAntiga.setNome(campanhaComNovosDados.getNome());
		campanhaAntiga.setTelefoneSuporte(campanhaComNovosDados.getTelefoneSuporte());
		campanhaAntiga.setEmpresa(campanhaComNovosDados.getEmpresa());
		campanhaAntiga.setLink(campanhaComNovosDados.getLink());
		campanhaAntiga.setUrl(criarURL(campanhaComNovosDados.getEmpresa(), campanhaComNovosDados.getLink()));
		campanhaAntiga.setEndUrl(campanhaComNovosDados.getEndUrl());
		
		return campanhaAntiga;
	}

	private String criarURL(String empresa, String link) {
		return DOMINIO + "/" + empresa + "/" + link;
	}
	
	@Override
	@Transactional
	public void deletar(Long id) {
		
		Optional<Campanha> campanhaASerExcluida = campanhaRepository.findById(id);
		
		if(!campanhaASerExcluida.isPresent()) {
			throw new BusinessException("Não foi encontrada a campanha a ser excluída de ID: " + id);
		}
		
		campanhaRepository.delete(campanhaASerExcluida.get());
	}

}

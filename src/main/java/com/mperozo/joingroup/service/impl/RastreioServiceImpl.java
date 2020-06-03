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
import com.mperozo.joingroup.model.entity.Rastreio;
import com.mperozo.joingroup.model.repository.RastreioRepository;
import com.mperozo.joingroup.service.CampanhaService;
import com.mperozo.joingroup.service.RastreioService;


@Service
public class RastreioServiceImpl implements RastreioService {

	Logger logger = LoggerFactory.getLogger(RastreioServiceImpl.class);

	@Autowired
	private RastreioRepository rastreioRepository;
	
	@Autowired
	private CampanhaService campanhaService;

	public RastreioServiceImpl(RastreioRepository rastreioRepository) {
		super();
		this.rastreioRepository = rastreioRepository;
	}
	
	@Override
	public Rastreio buscarPorId(Long id) {

		Optional<Rastreio> rastreio = rastreioRepository.findById(id);
		
		if(!rastreio.isPresent()) {
			throw new BusinessException("Rastreio não encontrado na base de dados para ID = " + id );
		}
		
		return rastreio.get();
	}

	@Override
	public List<Rastreio> buscarPorCampanha(Long idCampanha) {
		
		Campanha campanha = campanhaService.buscarPorId(idCampanha);
		List<Rastreio> rastreios = rastreioRepository.findByCampanha(campanha);
		
		return rastreios;
	}

	@Override
	@Transactional
	public Rastreio salvarRastreio(Rastreio rastreio) {
		
		rastreio.setDataHoraInclusao(LocalDateTime.now());
		
		return rastreioRepository.save(rastreio);
	}

	@Override
	public void deletar(Long id) {

		Optional<Rastreio> rastreioASerExcluido = rastreioRepository.findById(id);
		
		if(!rastreioASerExcluido.isPresent()) {
			throw new BusinessException("Não foi encontrado o rastreio a ser excluído de ID: " + id);
		}
		
		rastreioRepository.delete(rastreioASerExcluido.get());
	}

	@Override
	@Transactional
	public Rastreio atualizarRastreio(Long id, Rastreio rastreioComNovosDados) {
		
		Rastreio rastreioAntigo = buscarPorId(id);
		Rastreio rastreioAtualizado = atualizarRastreio(rastreioComNovosDados, rastreioAntigo);
		
		return rastreioRepository.saveAndFlush(rastreioAtualizado);
	}

	private Rastreio atualizarRastreio(Rastreio rastreioComNovosDados, Rastreio rastreioAntigo) {
		
		rastreioAntigo.setDataHoraAlteracao(LocalDateTime.now());
		//TODO verificar se pode alterar o tipo de rastreio
		rastreioAntigo.setTipo(rastreioComNovosDados.getTipo());
		rastreioAntigo.setCodigo(rastreioComNovosDados.getCodigo());
		rastreioAntigo.setScript(rastreioComNovosDados.getScript());

		return rastreioAntigo;
	}

}

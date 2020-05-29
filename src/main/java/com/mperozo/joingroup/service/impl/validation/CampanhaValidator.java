package com.mperozo.joingroup.service.impl.validation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.model.repository.CampanhaRepository;

@Service
public class CampanhaValidator {
	
	@Autowired
	private CampanhaRepository campanhaRepository;

	public void validarCampanha(Campanha campanha) {
		
		if(campanha.getDataValidade() != null && campanha.getDataValidade().isBefore(LocalDate.now())) {
			throw new BusinessException("Data de validade da campanha deve ser posterior a data corrente.");
		}
		
		if(campanha.getUsuarioResponsavel() == null) {
			throw new BusinessException("Usuário responsável é obrigatório.");
		}
		
		validarNomeExistenteEntreCampanhasDoUsuario(campanha.getNome(), campanha.getUsuarioResponsavel());

		validarURLExistente(campanha.getUrl());
	}
	
	public void validarAlteracaoDeCampanha(Campanha campanhaComNovosDados, Campanha campanhaAntiga) {

		if(campanhaComNovosDados.getUsuarioResponsavel() != campanhaAntiga.getUsuarioResponsavel()) {
			throw new BusinessException("Não é possível alterar o usuário responsável pela campanha.");
		}
		
		if(campanhaComNovosDados.getStatus() != campanhaAntiga.getStatus()) {
			throw new BusinessException("Não é possível alterar o status de uma campanha através da edição de campanha.");
		}
		
		if(campanhaComNovosDados.getNome() != campanhaAntiga.getNome()) {
			
			validarNomeExistenteEntreCampanhasDoUsuario(campanhaComNovosDados.getNome(), campanhaComNovosDados.getUsuarioResponsavel());
		}
		
		if(campanhaComNovosDados.getEmpresa() != campanhaAntiga.getEmpresa() || 
				campanhaComNovosDados.getLink() != campanhaAntiga.getLink() ||
				campanhaComNovosDados.getUrl() != campanhaAntiga.getUrl()) {
			
			validarURLExistente(campanhaComNovosDados.getUrl());
		}
	}

	protected void validarNomeExistenteEntreCampanhasDoUsuario(String nomeCampanha, Usuario usuarioResponsavel) {
		List<Campanha> campanhasDoUsuarioResponsavel = campanhaRepository.findByUsuarioResponsavel(usuarioResponsavel);
		
		if( campanhasDoUsuarioResponsavel.stream()
				.filter(campanhaExistente -> nomeCampanha.equals(campanhaExistente.getNome()))
				.findAny().isPresent() ) {
			
			throw new BusinessException("Já existe uma campanha com o nome: " + nomeCampanha);
		}
	}

	protected void validarURLExistente(String url) {
		List<Campanha> todasAsCampanhas = campanhaRepository.findAll();
		
		if( todasAsCampanhas.stream()
				.filter(campanhaExistente -> url.equals(campanhaExistente.getUrl()))
				.findAny().isPresent() ) {
			
			throw new BusinessException("Já existe uma campanha com a URL: " + url);
		}
	}
}

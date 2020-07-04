package com.mperozo.joingroup.service.impl.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.model.repository.GrupoRepository;

@Service
public class GrupoValidator {
	
	@Autowired
	private GrupoRepository grupoRepository;

	public void validarGrupo(Grupo grupo) {
		
		if(grupo.getCampanha() == null) {
			throw new BusinessException("Campanha é obrigatória.");
		}
		
		validarNomeExistenteNaCampanhaDoGrupo(grupo.getNome(), grupo.getCampanha());
		validarURLExistenteEntreGruposDeUmaMesmaCampanha(grupo.getUrl(), grupo.getCampanha());
	}
	
	public void validarAlteracaoDeGrupo(Grupo grupoComNovosDados, Grupo grupoAntigo) {
		
		//TODO validar se campanha do Grupo pertence ao usuário logado
		
		if(grupoComNovosDados.getCampanha() != grupoAntigo.getCampanha()) {
			throw new BusinessException("Não é possível alterar a campanha de um grupo.");
		}

		if(!grupoComNovosDados.getNome().equals(grupoAntigo.getNome())) {
			
			validarNomeExistenteNaCampanhaDoGrupo(grupoComNovosDados.getNome(), grupoComNovosDados.getCampanha());
		}
		
		if(!grupoComNovosDados.getUrl().equals(grupoAntigo.getUrl())) {
			
			validarURLExistenteEntreGruposDeUmaMesmaCampanha(grupoComNovosDados.getUrl(), grupoComNovosDados.getCampanha());
		}
	}

	protected void validarNomeExistenteNaCampanhaDoGrupo(String nomeGrupo, Campanha campanha) {
		List<Grupo> gruposDaCampanha = grupoRepository.findByCampanha(campanha);
		
		if( gruposDaCampanha.stream()
				.filter(grupoExistente -> nomeGrupo.equals(grupoExistente.getNome()))
				.findAny().isPresent() ) {
			
			throw new BusinessException("Já existe um grupo com o nome " + nomeGrupo + " para a campanha " + campanha.getNome());
		}
	}

	protected void validarURLExistenteEntreGruposDeUmaMesmaCampanha(String urlGrupo, Campanha campanha) {
		List<Grupo> gruposDaCampanha = grupoRepository.findByCampanha(campanha);
		
		if( gruposDaCampanha.stream()
				.filter(grupoExistente -> urlGrupo.equals(grupoExistente.getUrl()))
				.findAny().isPresent() ) {
			
			throw new BusinessException("Já existe um grupo com a URL: " + urlGrupo + " para a campanha " + campanha.getNome());
		}
	}
}

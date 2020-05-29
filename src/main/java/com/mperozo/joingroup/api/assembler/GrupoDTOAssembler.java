package com.mperozo.joingroup.api.assembler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mperozo.joingroup.api.dto.GrupoDTO;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Grupo;
import com.mperozo.joingroup.service.CampanhaService;

@Service
public class GrupoDTOAssembler {
	
	@Autowired
	private CampanhaService campanhaService;
	
	public Grupo toEntity(GrupoDTO dto) {
		
		Campanha campanha = campanhaService.buscarPorId(dto.getIdCampanha());
		
		return Grupo.builder()
				.nome(dto.getNome())
				.url(dto.getUrl())
				.totalRedirect(dto.getTotalRedirect())
				.campanha(campanha).build();
	}

	public GrupoDTO toDTO(Grupo entity) {
		
		return GrupoDTO.builder()
				.id(entity.getId())
				.nome(entity.getNome())
				.url(entity.getUrl())
				.totalRedirect(entity.getTotalRedirect())
				.idCampanha(entity.getCampanha().getId()).build();
	}
	
	public List<GrupoDTO> toDTOList(List<Grupo> entityList) {
		
		List<GrupoDTO> grupoDTOList = new LinkedList<GrupoDTO>();
		
		entityList.forEach(grupo -> grupoDTOList.add(toDTO(grupo)));
		
		return grupoDTOList;
	}
}

package com.mperozo.joingroup.api.assembler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mperozo.joingroup.api.dto.RastreioDTO;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Rastreio;
import com.mperozo.joingroup.service.CampanhaService;

@Service
public class RastreioDTOAssembler {
	
	@Autowired
	private CampanhaService campanhaService;
	
	public Rastreio toEntity(RastreioDTO dto) {
		
		Campanha campanha = campanhaService.buscarPorId(dto.getIdCampanha());
		
		return Rastreio.builder()
				.tipo(dto.getTipo())
				.codigo(dto.getCodigo())
				.script(dto.getScript())
				.campanha(campanha).build();
	}

	public RastreioDTO toDTO(Rastreio entity) {
		
		return RastreioDTO.builder()
				.tipo(entity.getTipo())
				.codigo(entity.getCodigo())
				.script(entity.getScript())
				.idCampanha(entity.getCampanha().getId()).build();
	}
	
	public List<RastreioDTO> toDTOList(List<Rastreio> entityList) {
		
		List<RastreioDTO> rastreioDTOList = new LinkedList<RastreioDTO>();
		
		entityList.forEach(rastreio -> rastreioDTOList.add(toDTO(rastreio)));
		
		return rastreioDTOList;
	}
}

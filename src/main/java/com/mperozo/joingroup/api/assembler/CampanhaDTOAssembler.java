package com.mperozo.joingroup.api.assembler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mperozo.joingroup.api.dto.CampanhaDTO;
import com.mperozo.joingroup.model.entity.Campanha;
import com.mperozo.joingroup.model.entity.Usuario;
import com.mperozo.joingroup.service.UsuarioService;

@Service
public class CampanhaDTOAssembler {
	
	@Autowired
	private UsuarioService usuarioService;

	public Campanha toEntity(CampanhaDTO dto) {
		
		//TODO implementar quando tiver autenticação
		Usuario usuarioResponsavel = usuarioService.buscarPorId(1L);
		
		return Campanha.builder()
				.nome(dto.getNome())
				.usuarioResponsavel(usuarioResponsavel)
				.empresa(dto.getEmpresa())
				.link(dto.getLink())
				.url(dto.getUrl())
				.status(dto.getStatus())
				.telefoneSuporte(dto.getTelefoneSuporte())
				.groupClickLimit(dto.getGroupClickLimit())
				.endUrl(dto.getEndUrl())
				.tipoRedirect(dto.getTipoRedirect())
				.tituloRedirect(dto.getTituloRedirect())
				.subtituloRedirect(dto.getSubtituloRedirect())
				.tempoRedirect(dto.getTempoRedirect())
				.tituloMetatag(dto.getTituloMetatag())
				.descricaoMetatag(dto.getDescricaoMetatag()).build();
	}

	public CampanhaDTO toDTO(Campanha entity) {
		
		return CampanhaDTO.builder()
				.id(entity.getId())
				.nome(entity.getNome())
				.empresa(entity.getEmpresa())
				.link(entity.getLink())
				.url(entity.getUrl())
				.status(entity.getStatus())
				.telefoneSuporte(entity.getTelefoneSuporte())
				.groupClickLimit(entity.getGroupClickLimit())
				.endUrl(entity.getEndUrl())
				.tipoRedirect(entity.getTipoRedirect())
				.tituloRedirect(entity.getTituloRedirect())
				.subtituloRedirect(entity.getSubtituloRedirect())
				.tempoRedirect(entity.getTempoRedirect())
				.tituloMetatag(entity.getTituloMetatag())
				.descricaoMetatag(entity.getDescricaoMetatag())
				.build();
	}
	
	public List<CampanhaDTO> toDTOList(List<Campanha> entityList) {
		
		List<CampanhaDTO> campanhaDTOList = new LinkedList<CampanhaDTO>();
		
		entityList.forEach(campanha -> campanhaDTOList.add(toDTO(campanha)));
		
		return campanhaDTOList;
	}
}

package com.mperozo.joingroup.api.assembler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mperozo.joingroup.api.dto.UsuarioDTO;
import com.mperozo.joingroup.model.entity.Usuario;

@Service
public class UsuarioDTOAssembler {

	public Usuario toEntity(UsuarioDTO dto) {
		
		return Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
	}

	public UsuarioDTO toDTO(Usuario entity) {
		
		return UsuarioDTO.builder()
				.id(entity.getId())
				.nome(entity.getNome())
				.email(entity.getEmail()).build();
	}
	
	public List<UsuarioDTO> toDTOList(List<Usuario> entityList) {
		
		List<UsuarioDTO> usuarioDTOList = new LinkedList<UsuarioDTO>();
		
		entityList.forEach(paciente -> usuarioDTOList.add(toDTO(paciente)));
		
		return usuarioDTOList;
	}

}

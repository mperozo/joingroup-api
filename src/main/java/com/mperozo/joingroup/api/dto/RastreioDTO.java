package com.mperozo.joingroup.api.dto;

import com.mperozo.joingroup.model.enums.TipoRastreioEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RastreioDTO {

	private Long id;
	private TipoRastreioEnum tipo;
	private String codigo;
	private String script;
	private Long idCampanha;
}

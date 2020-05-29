package com.mperozo.joingroup.api.dto;

import com.mperozo.joingroup.model.enums.StatusCampanhaEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampanhaDTO {

	private Long id;
	private Long idUsuarioResponsavel;
	private String nome;
	private String empresa;
	private String link;
	private String url;
	private StatusCampanhaEnum status;
	private String telefoneSuporte;
	private Integer groupClickLimit;
	private String endUrl;
}

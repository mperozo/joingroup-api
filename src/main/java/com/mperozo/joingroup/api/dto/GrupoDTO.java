package com.mperozo.joingroup.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDTO {

	private Long id;
	private String nome;
	private String url;
	private Integer totalRedirect;
	private Long idCampanha;
}

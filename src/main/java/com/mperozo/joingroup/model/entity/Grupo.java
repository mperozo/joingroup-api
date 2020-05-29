package com.mperozo.joingroup.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GRUPO", schema = "JOINGROUP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	@NotBlank(message = "Nome é obrigatório.")
	private String nome;
	
	@Column(name = "URL")
	@NotBlank(message = "URL é obrigatória.")
	private String url;
	
	@Column(name = "TOTAL_REDIRECT")
	private Integer totalRedirect;
	
	@ManyToOne
	@JoinColumn(name = "ID_CAMPANHA")
	private Campanha campanha;

	@Column(name = "DATA_HORA_INCLUSAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@NotNull
	private LocalDateTime dataHoraInclusao;
	
	@Column(name = "DATA_HORA_ALTERACAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dataHoraAlteracao;
}

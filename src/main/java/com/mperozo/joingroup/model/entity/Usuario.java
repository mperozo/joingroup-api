package com.mperozo.joingroup.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mperozo.joingroup.model.enums.StatusUsuarioEnum;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USUARIO", schema = "JOINGROUP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	@NotBlank(message = "Nome é obrigatório.")
	private String nome;

	@Column(name = "STATUS")
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private StatusUsuarioEnum status;

	@Column(name = "EMAIL")
	@NotBlank(message = "E-mail é obrigatório.")
	private String email;

	@Column(name = "SENHA")
	@NotBlank(message = "Senha é obrigatória.")
	@JsonIgnore
	private String senha;

	@Column(name = "DATA_HORA_INCLUSAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@NotNull
	private LocalDateTime dataHoraInclusao;
	
	@Column(name = "DATA_HORA_ALTERACAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dataHoraAlteracao;

}

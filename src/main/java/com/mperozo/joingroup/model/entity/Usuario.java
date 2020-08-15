package com.mperozo.joingroup.model.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mperozo.joingroup.model.enums.StatusUsuarioEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO", schema = "JOINGROUP", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class Usuario {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	@Size(max = 150)
	@NotBlank(message = "Nome é obrigatório.")
	private String nome;
	
	@Column(name = "SOBRENOME")
	@Size(max = 150)
	@NotBlank(message = "Sobrenome é obrigatório.")
	private String sobrenome;

	@Column(name = "STATUS")
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private StatusUsuarioEnum status;

	@Column(name = "EMAIL")
	@Size(max = 100)
	@NotBlank(message = "E-mail é obrigatório.")
	private String email;

	@Column(name = "SENHA")
	@Size(max = 100)
	@NotBlank(message = "Senha é obrigatória.")
	@JsonIgnore
	private String senha;
	
	@Column(name = "NEWSLETTER")
	@NotNull
	@JsonIgnore
	private Boolean newsletter;

	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USUARIO_ROLES", schema = "JOINGROUP", joinColumns = @JoinColumn(name = "USUARIO_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles = new HashSet<>();

	@Column(name = "DATA_HORA_INCLUSAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@NotNull
	private LocalDateTime dataHoraInclusao;

	@Column(name = "DATA_HORA_ALTERACAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dataHoraAlteracao;

}

package com.mperozo.joingroup.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mperozo.joingroup.model.enums.TipoRastreioEnum;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RASTREIO", schema = "JOINGROUP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rastreio {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TIPO")
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private TipoRastreioEnum tipo;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "SCRIPT")
	private String script;
	
	@ManyToOne
	@JoinColumn(name = "ID_CAMPANHA")
	private Campanha campanha;
}

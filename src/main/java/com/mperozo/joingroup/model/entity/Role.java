package com.mperozo.joingroup.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mperozo.joingroup.model.enums.RolesEnum;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROLE", schema = "JOINGROUP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "NOME")
	@NotNull
	private RolesEnum nome;
	
	public Role(RolesEnum role) {
		this.nome = role;
	}

}

package com.mperozo.joingroup.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.mperozo.joingroup.model.enums.StatusCampanhaEnum;
import com.mperozo.joingroup.model.enums.TipoRedirectEnum;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAMPANHA", schema = "JOINGROUP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campanha {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NOME")
	@NotBlank(message = "Nome é obrigatório.")
	private String nome;
	
	@Column(name = "EMPRESA")
	@NotBlank(message = "Nome da empresa é obrigatório.")
	private String empresa;
	
	@Column(name = "LINK")
	@NotBlank(message = "Link é obrigatório.")
	private String link;
	
	@Column(name = "URL")
	@NotBlank(message = "URL é obrigatória.")
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO_RESPONSAVEL")
	private Usuario usuarioResponsavel;
	
	@Column(name = "END_URL")
	private String endUrl;
	
	@Column(name = "TELEFONE_SUPORTE")
	private String telefoneSuporte;
	
	@Column(name = "GROUP_CLICK_LIMIT")
	private Integer groupClickLimit;
	
	@Column(name = "STATUS")
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private StatusCampanhaEnum status;
	
	@Column(name = "DATA_VALIDADE")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataValidade;
	
	@Column(name = "TIPO_REDIRECT")
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private TipoRedirectEnum tipoRedirect;
	
	@Column(name = "TITULO_REDIRECT")
	private String tituloRedirect;
	
	@Column(name = "SUBTITULO_REDIRECT")
	private String subtituloRedirect;
	
	@Column(name = "TEMPO_REDIRECT")
	private Integer tempoRedirect;

	@Column(name = "TITULO_METATAG")
	private String tituloMetatag;
	
	@Column(name = "DESCRICAO_METATAG")
	private String descricaoMetatag;
	
	@Column(name = "DATA_HORA_INCLUSAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@NotNull
	private LocalDateTime dataHoraInclusao;
	
	@Column(name = "DATA_HORA_ALTERACAO")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dataHoraAlteracao;

}

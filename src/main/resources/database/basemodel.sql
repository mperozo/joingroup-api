CREATE SCHEMA joingroup;

CREATE TABLE joingroup.usuario
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	email character varying(100) NOT NULL,
	senha character varying(20) NOT NULL,
	status character varying(10) NOT NULL,
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP,
	
	CONSTRAINT usuario_status_check CHECK (status::text = ANY (ARRAY['ATIVO'::character varying, 'INATIVO'::character varying, 'BLOQUEADO'::character varying]::text[]))
);

CREATE TABLE joingroup.campanha
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	url character varying(300) NOT NULL,
	id_usuario_responsavel bigint references joingroup.usuario(id),
	end_url character varying(300) NOT NULL,
	telefone_suporte character varying(16) NOT NULL,
	group_click_limit integer NOT NULL,
	status character varying(10) NOT NULL,
	data_validade TIMESTAMP,
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP
);

CREATE TABLE joingroup.grupo
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	url character varying(300) NOT NULL,
	total_redirect integer NOT NULL,
	id_campanha bigint references joingroup.campanha(id),
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP
);

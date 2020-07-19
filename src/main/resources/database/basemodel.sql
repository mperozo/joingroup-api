--CREATE SCHEMA joingroup;

CREATE TABLE joingroup.usuario
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	email character varying(100) NOT NULL,
	senha character varying(100) NOT NULL,
	status character varying(10) NOT NULL,
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP,
	
	CONSTRAINT usuario_status_check CHECK (status::text = ANY (ARRAY['ATIVO'::character varying, 'INATIVO'::character varying, 'BLOQUEADO'::character varying]::text[]))
);

CREATE TABLE joingroup.role
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(20) NOT NULL
);

CREATE TABLE joingroup.usuario_roles
(
	usuario_id bigserial references joingroup.usuario(id) NOT NULL,
	role_id bigserial references joingroup.roles(id) NOT NULL
);

CREATE TABLE joingroup.campanha
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	empresa character varying(100) NOT NULL,
	link character varying(100) NOT NULL,
	url character varying(300) NOT NULL,
	id_usuario_responsavel bigint references joingroup.usuario(id) NOT NULL,
	end_url character varying(300) NOT NULL,
	telefone_suporte character varying(16) NOT NULL,
	group_click_limit integer NOT NULL,
	status character varying(10) NOT NULL,
	data_validade TIMESTAMP,
	tipo_redirect character varying(10) NOT NULL,
	titulo_redirect character varying(150),
	subtitulo_redirect character varying(300),
	tempo_redirect integer,
	titulo_metatag character varying(150),
	descricao_metatag character varying(300),
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP
);

CREATE TABLE joingroup.grupo
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	url character varying(300) NOT NULL,
	total_redirect integer,
	id_campanha bigint references joingroup.campanha(id),
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP
);

CREATE TABLE joingroup.rastreio
(
	id bigserial NOT NULL PRIMARY KEY,
	tipo character varying(10) NOT NULL,
	codigo character varying(150),
	script character varying(500),
	id_campanha bigint references joingroup.campanha(id),
	data_hora_inclusao TIMESTAMP DEFAULT now() NOT NULL,
	data_hora_alteracao TIMESTAMP
);

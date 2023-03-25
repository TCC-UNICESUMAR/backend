CREATE TABLE tb_user(
	id bigint NOT NULL PRIMARY KEY,
	cpf_or_cnp varchar(255) NULL,
	email varchar(255) NOT NULL,
	firstname varchar(255) NOT NULL,
	lastname varchar(255) NULL,
	password varchar(255) NOT NULL
);
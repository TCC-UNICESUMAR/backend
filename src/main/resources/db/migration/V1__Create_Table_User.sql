CREATE TABLE tb_users(
	user_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	cpf_or_cnpj varchar(255) NULL,
	email varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NULL,
	profile_image_id varchar(100) NULL,
	password varchar(255) NOT NULL,
	created_at date NOT NULL,
	update_at date NOT NULL,
	delete_at date NULL,
	active int NOT NULL,
	isOng int NULL
);
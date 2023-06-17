CREATE TABLE tb_users(
	user_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	cpf_or_cnpj varchar(255) NULL,
	email varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	phone varchar(255) NULL,
	profile_image_id varchar(100) NULL,
	password varchar(255) NOT NULL,
	created_user_at timestamp(6) NOT NULL,
	update_user_at timestamp(6) NOT NULL,
	delete_user_at timestamp(6) NULL,
	user_active int NOT NULL,
	isOng int NULL,
	address_address_id bigint NULL
);
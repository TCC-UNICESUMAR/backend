CREATE TABLE IF NOT EXISTS tb_users(
	user_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	cpf_or_cnpj varchar(255) NULL,
	passport_document_number varchar(255) NULL,
	email varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	phone varchar(255) NULL,
	profile_image_id varchar(100) NULL,
	password varchar(255) NOT NULL,
	user_active int NOT NULL,
	fk_address_id bigint NULL,
	fk_date_id bigint NOT NULL
);

alter table tb_users add constraint UK_pcg68g34ilhfgv3j5sy0oc2mr unique (cpf_or_cnpj);
alter table tb_users add constraint UK_grd22228p1miaivbn9yg178pm unique (email);
alter table tb_users add constraint UK_grd22228p1msdfbbn9yg178pm unique (passport_document_number);
CREATE TABLE IF NOT EXISTS tb_users(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	cpf_or_cnpj varchar(30) NULL,
	passport_document_number varchar(30) NULL,
	email varchar(150) NOT NULL,
	name varchar(150) NOT NULL,
	phone varchar(14) NULL,
	profile_image_id varchar(255) NULL,
	password varchar(12) NOT NULL,
	user_active int NOT NULL,
	fk_address bigint NULL,
	created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_users add constraint UK_pcg68g34ilhfgv3j5sy0oc2mr unique (cpf_or_cnpj);
alter table tb_users add constraint UK_grd22228p1miaivbn9yg178pm unique (email);
alter table tb_users add constraint UK_grd22228p1msdfbbn9yg178pm unique (passport_document_number);
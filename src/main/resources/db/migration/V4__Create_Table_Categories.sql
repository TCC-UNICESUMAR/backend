CREATE TABLE IF NOT EXISTS tb_categories(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	category_name varchar(150) NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

insert into tb_categories (id,category_name,created_at,updated_at,deleted_at) values (1,'Eletronicos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (id,category_name,created_at,updated_at,deleted_at) values (2,'Roupas em Geral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (id,category_name,created_at,updated_at,deleted_at) values (3,'Alimentos não perecíveis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (id,category_name,created_at,updated_at,deleted_at) values (4,'Alimentos Perecíveis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (id,category_name,created_at,updated_at,deleted_at) values (5,'Cobertores', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (id,category_name,created_at,updated_at,deleted_at) values (6,'Material de Contrução', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
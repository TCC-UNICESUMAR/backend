CREATE TABLE tb_categories(
	category_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	category_name varchar(255) NULL,
	created_category_at timestamp(6) NOT NULL,
	update_category_at timestamp(6) NOT NULL,
	delete_category_at timestamp(6) NULL
);

insert into tb_categories (category_id,category_name,created_category_at,update_category_at,delete_category_at) values (1,'Eletronicos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (category_id,category_name,created_category_at,update_category_at,delete_category_at) values (2,'Roupas em Geral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (category_id,category_name,created_category_at,update_category_at,delete_category_at) values (3,'Alimentos não perecíveis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (category_id,category_name,created_category_at,update_category_at,delete_category_at) values (4,'Alimentos Perecíveis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (category_id,category_name,created_category_at,update_category_at,delete_category_at) values (5,'Cobertores', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
insert into tb_categories (category_id,category_name,created_category_at,update_category_at,delete_category_at) values (6,'Material de Contrução', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
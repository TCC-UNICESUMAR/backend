CREATE TABLE tb_categories(
	category_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	category_name varchar(255) NULL,
	created_at timestamp(6) NOT NULL,
	update_at timestamp(6) NOT NULL,
	delete_at timestamp(6) NULL,
	active int NOT NULL
);

insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (1,'Eletronicos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (2,'Roupas em Geral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (3,'Alimentos não perecíveis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (4,'Alimentos Perecíveis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (5,'Cobertores', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (6,'Material de Contrução', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, true);
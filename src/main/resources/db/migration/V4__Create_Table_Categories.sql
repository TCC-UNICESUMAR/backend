CREATE TABLE tb_categories(
	category_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	category_name varchar(255) NULL,
	created_at date NOT NULL,
	update_at date NOT NULL,
	delete_at date NULL,
	active int NOT NULL
);

insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (1,'Eletronicos', '2023-04-10', '2023-04-10', null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (2,'Roupas em Geral', '2023-04-10', '2023-04-10', null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (3,'Alimentos não perecíveis', '2023-04-10', '2023-04-10', null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (4,'Alimentos Perecíveis', '2023-04-10', '2023-04-10', null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (5,'Cobertores', '2023-04-10', '2023-04-10', null, true);
insert into tb_categories (category_id,category_name,created_at,update_at,delete_at,active) values (6,'Material de Contrução', '2023-04-10', '2023-04-10', null, true);
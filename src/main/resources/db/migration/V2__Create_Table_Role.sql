CREATE TABLE tb_role(
	role_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	role_name varchar(255) NULL
);

insert into tb_role (role_id,role_name) values (1,'ROLE_ADMIN');
insert into tb_role (role_id,role_name) values (2,'ROLE_ONG');
insert into tb_role (role_id,role_name) values (3,'ROLE_USER');
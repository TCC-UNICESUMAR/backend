CREATE TABLE IF NOT EXISTS tb_roles(
	role_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	role_name varchar(255) NOT NULL,
	created_role_at timestamp(6) NOT NULL,
    updated_role_at timestamp(6) NOT NULL,
    deleted_role_at timestamp(6) NULL
);

insert into tb_roles (role_id,role_name, created_role_at, updated_role_at, deleted_role_at) values (1,'ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
insert into tb_roles (role_id,role_name, created_role_at, updated_role_at, deleted_role_at) values (2,'ROLE_ONG', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
insert into tb_roles (role_id,role_name, created_role_at, updated_role_at, deleted_role_at) values (3,'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
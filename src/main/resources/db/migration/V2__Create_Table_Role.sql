CREATE TABLE IF NOT EXISTS tb_roles(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	role_name varchar(50) NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

insert into tb_roles values(1, 'ROLE_ADMIN', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL);
insert into tb_roles values(2, 'ROLE_USER', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL);
insert into tb_roles values(3, 'ROLE_ONG', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL);
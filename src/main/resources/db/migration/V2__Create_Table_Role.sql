CREATE TABLE IF NOT EXISTS tb_roles(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	role_name varchar(50) NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);
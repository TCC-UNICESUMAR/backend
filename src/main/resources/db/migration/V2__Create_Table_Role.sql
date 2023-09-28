CREATE TABLE IF NOT EXISTS tb_roles(
	role_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	role_name varchar(255) NOT NULL,
    fk_date_id bigint NOT NULL
);
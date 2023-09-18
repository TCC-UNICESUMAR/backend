CREATE TABLE IF NOT EXISTS tb_roles(
	role_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	role_name varchar(255) NOT NULL,
	created_role_at timestamp(6) NOT NULL,
    updated_role_at timestamp(6) NOT NULL,
    deleted_role_at timestamp(6) NULL,
    date_date_id bigint NOT NULL

);
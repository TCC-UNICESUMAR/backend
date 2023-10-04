CREATE TABLE IF NOT EXISTS tb_categories(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	category_name varchar(255) NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);
CREATE TABLE tb_products(
	product_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name varchar(255) NULL,
	quantity int NOT NULL,
	description varchar(255) NULL,
	user_id bigint NOT NULL,
	created_at date NOT NULL,
	update_at date NOT NULL,
	delete_at date NULL,
	active int NOT NULL,
	reserved int NOT NULL
);

ALTER TABLE tb_products ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_users (user_id) ;
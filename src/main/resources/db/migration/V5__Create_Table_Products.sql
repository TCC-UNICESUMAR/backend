CREATE TABLE tb_products(
	product_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name varchar(255) NULL,
	quantity int NOT NULL,
	description varchar(255) NULL,
	user_user_id bigint NOT NULL,
	created_at timestamp(6) NOT NULL,
	update_at timestamp(6) NOT NULL,
	delete_at timestamp(6) NULL,
	active int NOT NULL,
	reserved int NOT NULL,
	image_product_list JSON NULL
);
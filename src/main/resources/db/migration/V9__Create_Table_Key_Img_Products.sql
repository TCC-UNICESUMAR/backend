CREATE TABLE tb_products_image_key(
	product_image_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	image_key varchar(110) NOT NULL,
	product_product_id bigint NOT NULL,
	created_image_key_at timestamp(6) NOT NULL,
	update_image_key_at timestamp(6) NOT NULL,
	delete_image_key_at timestamp(6) NULL
);
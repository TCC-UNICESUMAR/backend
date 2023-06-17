CREATE TABLE tb_products_image_url(
	product_image_url_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	image_url varchar(110) NOT NULL,
	product_product_id bigint NOT NULL,
	created_image_url_at timestamp(6) NOT NULL,
	update_image_url_at timestamp(6) NOT NULL,
	delete_image_url_at timestamp(6) NULL
);

alter table tb_products_image_url add constraint FKq8plupmr9wr1q1vy31m9b0v92 foreign key (product_product_id) references tb_products (product_id);

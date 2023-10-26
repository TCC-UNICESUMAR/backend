CREATE TABLE IF NOT EXISTS tb_products_images(
	product_id bigint NOT NULL,
	image_id bigint NOT NULL
);

alter table tb_products_images add constraint FK6g6aaa2yginq6fp9k1tg6ls3r foreign key (product_id) references tb_products (id);
alter table tb_products_images add constraint FKpn4njnr07hiiam9pg4jh3xan7 foreign key (image_id) references tb_images (id);

CREATE TABLE IF NOT EXISTS tb_donation_products(
	donation_id bigint NOT NULL,
	product_id bigint NOT NULL
);

alter table tb_donation_products add constraint FK5h6aaa2yginq4fp9k1tg6ls3r foreign key (donation_id) references tb_donations (donation_id);
alter table tb_donation_products add constraint FKlk0njnr07hiiam9pg4pp3xan7 foreign key (product_id) references tb_products (product_id);

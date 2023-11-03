CREATE TABLE IF NOT EXISTS tb_donations_to_approve(
	user_id bigint NOT NULL,
	donation_order_id bigint NOT NULL
);

alter table tb_donations_to_approve add constraint FK6g6aaa2yginq6fp9f1tg6ls3r foreign key (user_id) references tb_users (id);
alter table tb_donations_to_approve add constraint FKpn4njnr07hiiam9pg4jh8xan7 foreign key (donation_order_id) references tb_donations_orders (id);

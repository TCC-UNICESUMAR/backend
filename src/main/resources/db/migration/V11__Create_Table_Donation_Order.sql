CREATE TABLE IF NOT EXISTS tb_donations_orders(
	donation_order_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	created_donation_order_at timestamp(6) NOT NULL,
    updated_donation_order_at timestamp(6) NOT NULL,
    deleted_donation_order_at timestamp(6) NULL,
    donor bigint NOT NULL,
    received bigint NOT NULL,
    intermediary bigint NOT NULL,
    donation_status_id bigint NOT NULL,
    donation_donation_id bigint NOT NULL
);

alter table tb_donations_orders add constraint FK5h6aaa2yginq4fp7k1tg6ls3r foreign key (donor) references tb_users (user_id);
alter table tb_donations_orders add constraint FKlk0njnr07hiiam3pg4pp3xan7 foreign key (received) references tb_users (user_id);
alter table tb_donations_orders add constraint FK5h6aaa2yginq4fp6k1tg6ls3r foreign key (intermediary) references tb_users (user_id);
alter table tb_donations_orders add constraint FK5kfdoibisn5ndfvikfn6divmd foreign key (donation_status_id) references tb_donations_status (donation_status_id);
alter table tb_donations_orders add constraint FKlk0njnr07hiiam1pg4pp3xan7 foreign key (donation_donation_id) references tb_donations (donation_id);

CREATE TABLE IF NOT EXISTS tb_donations_orders(
	donation_order_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    fk_date_id bigint NOT NULL,
    fk_user_donor bigint NOT NULL,
    fk_user_received bigint NOT NULL,
    fk_user_intermediary bigint NOT NULL,
    fk_donation_status_id bigint NOT NULL,
    fk_donation_id bigint NOT NULL
);

alter table tb_donations_orders add constraint FK5h6aaa2yginq4fp7k1tg6ls3r foreign key (fk_user_donor) references tb_users (user_id);
alter table tb_donations_orders add constraint FKlk0njnr07hiiam3pg4pp3xan7 foreign key (fk_user_received) references tb_users (user_id);
alter table tb_donations_orders add constraint FK5h6aaa2yginq4fp6k1tg6ls3r foreign key (fk_user_intermediary) references tb_users (user_id);
alter table tb_donations_orders add constraint FK5kfdoibisn5ndfvikfn6divmd foreign key (fk_donation_status_id) references tb_donations_status (donation_status_id);
alter table tb_donations_orders add constraint FKlk0njnr07hiiam1pg4pp3xan7 foreign key (fk_donation_id) references tb_donations (donation_id);

CREATE TABLE IF NOT EXISTS tb_donations_orders(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    fk_user_donor bigint NOT NULL,
    fk_user_received bigint NOT NULL,
    fk_user_intermediary bigint NULL,
    fk_donation_status bigint NOT NULL,
    fk_donation bigint NOT NULL,
    reason varchar(155) NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_donations_orders
add constraint FK5h6aaa2yginq4fp7k1tg6ls3r
foreign key (fk_user_donor) references tb_users (id);

alter table tb_donations_orders
add constraint FKlk0njnr07hiiam3pg4pp3xan7
foreign key (fk_user_received) references tb_users (id);

alter table tb_donations_orders
add constraint FK5h6aaa2yginq4fp6k1tg6ls3r
foreign key (fk_user_intermediary) references tb_users (id);

alter table tb_donations_orders
add constraint FK5kfdoibisn5ndfvikfn6divmd
foreign key (fk_donation_status) references tb_donations_status (id);

alter table tb_donations_orders
add constraint FKlk0njnr07hiiam1pg4pp3xan7
foreign key (fk_donation) references tb_donations (id);

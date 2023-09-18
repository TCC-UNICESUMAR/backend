CREATE TABLE IF NOT EXISTS tb_dates(
	date_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_donations_status add constraint FK5h6aaa2yginq4fp7k1tg6ls3u foreign key (date_date_id) references tb_dates (date_id);
alter table tb_addresses add constraint FK5h6aaa2yginq4fp7k1tg6ls3k foreign key (date_date_id) references tb_dates (date_id);
alter table tb_cities add constraint FK5h6aaa2yginq4fp7k1tg6ls3l foreign key (date_date_id) references tb_dates (date_id);
alter table tb_states add constraint FK5h6aaa2yginq4fp7k1tg6ls3f foreign key (date_date_id) references tb_dates (date_id);
alter table tb_roles add constraint FK5h6aaa2yginq4fp7k1tg6ls3d foreign key (date_date_id) references tb_dates (date_id);
alter table tb_donations_orders add constraint FK5h6aaa2yginq4fp7k1tg6lh3r foreign key (date_date_id) references tb_dates (date_id);
alter table tb_users add constraint FKlk0njnr07hyodh3pg4pp3xan7 foreign key (date_date_id) references tb_dates (date_id);
alter table tb_products add constraint FK5h6aaa2yginq4kp6k1tg6ls3r foreign key (date_date_id) references tb_dates (date_id);
alter table tb_donations add constraint FK5kfdoibisn5ndfvikfn8uivmd foreign key (date_date_id) references tb_dates (date_id);
alter table tb_categories add constraint FKlk0njnr07hiiam6pg4kk3xan7 foreign key (date_date_id) references tb_dates (date_id);

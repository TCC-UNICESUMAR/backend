CREATE TABLE IF NOT EXISTS tb_donations(
	donation_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    fk_date_id bigint NOT NULL,
    fk_address_id bigint NOT NULL,
    fk_user_by bigint NOT NULL
);

alter table tb_donations add constraint FKpn4ghjk07hsdfg9pg4pp3xan7 foreign key (fk_address_id) references tb_addresses (address_id);
alter table tb_donations add constraint FKpn4iukg07hsdfg9pg4ii8xan7 foreign key (fk_user_by) references tb_users (user_id);
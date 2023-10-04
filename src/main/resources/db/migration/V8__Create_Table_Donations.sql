CREATE TABLE IF NOT EXISTS tb_donations(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    fk_address bigint NOT NULL,
    fk_user_by bigint NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_donations
add constraint FKpn4ghjk07hsdfg9pg4pp3xan7
foreign key (fk_address)
references tb_addresses (id);

alter table tb_donations
add constraint FKpn4iukg07hsdfg9pg4ii8xan7
foreign key (fk_user_by)
references tb_users (id);
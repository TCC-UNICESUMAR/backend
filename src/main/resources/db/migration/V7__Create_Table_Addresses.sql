create table tb_addresses (
    address_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    complement varchar(255),
    street_name varchar(255),
    street_number varchar(255),
    zip_code varchar(10),
    state_state_id bigint NOT NULL,
    city_city_id bigint NOT NULL,
    date_date_id bigint NOT NULL
);

alter table tb_addresses add constraint FKpn4njnr07hsdfg9pg4pp3xan7 foreign key (state_state_id) references tb_states (state_id);
alter table tb_addresses add constraint FKpn4njnr07hsdfg9pg4ii8xan7 foreign key (city_city_id) references tb_cities (city_id);
alter table tb_users add constraint FKui8njnr07hsdfg9pg4ii8xan7 foreign key (address_address_id) references tb_addresses (address_id);

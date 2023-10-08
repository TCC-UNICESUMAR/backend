create table tb_addresses (
    id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    complement varchar(100),
    street_name varchar(100),
    street_number varchar(20),
    zip_code varchar(10),
    fk_state bigint NOT NULL,
    fk_city bigint NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_addresses
add constraint FKpn4njnr07hsdfg9pg4pp3xan7
foreign key (fk_state)
references tb_states (id);

alter table tb_addresses
add constraint FKpn4njnr07hsdfg9pg4ii8xan7
foreign key (fk_city)
references tb_cities (id);

alter table tb_users
add constraint FKui8njnr07hsdfg9pg4ii8xan7
foreign key (fk_address)
references tb_addresses (id);

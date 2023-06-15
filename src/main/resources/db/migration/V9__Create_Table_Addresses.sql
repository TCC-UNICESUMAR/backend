create table tb_addresses (
    address_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    complement varchar(255),
    created_at timestamp(6),
    delete_at timestamp(6),
    street_name varchar(255),
    street_number varchar(255),
    uf varchar(2),
    update_at timestamp(6),
    zip_code varchar(10)
);

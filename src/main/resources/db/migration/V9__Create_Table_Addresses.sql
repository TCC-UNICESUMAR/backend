CREATE TABLE tb_addresses(
	address_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	street_name varchar(255) NOT NULL,
	street_number varchar(14) NOT NULL,
	uf varchar(5) NOT NULL,
	complement varchar(255) NULL,
	zip_code varchar(15) NOT NULL,
	phone varchar(100) NOT NULL,
	created_at date NOT NULL,
	update_at date NOT NULL,
	delete_at date NULL
);
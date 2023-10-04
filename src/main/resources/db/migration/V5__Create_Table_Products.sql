CREATE TABLE IF NOT EXISTS tb_products(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name varchar(255) NULL,
	quantity int NOT NULL,
	description varchar(255) NULL,
	active int NOT NULL,
	reserved int NOT NULL,
	fk_category bigint NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_products
 add constraint FK4v38xarenv0rucfoal7phwy8y
 foreign key (fk_category) references tb_categories (id);
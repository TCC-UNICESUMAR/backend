CREATE TABLE IF NOT EXISTS tb_products(
	product_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name varchar(255) NULL,
	quantity int NOT NULL,
	description varchar(255) NULL,
	active int NOT NULL,
	reserved int NOT NULL,
	fk_category_id bigint NOT NULL,
    fk_date_id bigint NOT NULL
);

alter table tb_products add constraint FK4v38xarenv0rucfoal7phwy8y foreign key (fk_category_id) references tb_categories (category_id);
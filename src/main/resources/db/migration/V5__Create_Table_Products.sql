CREATE TABLE IF NOT EXISTS tb_products(
	product_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name varchar(255) NULL,
	quantity int NOT NULL,
	description varchar(255) NULL,
	created_product_at timestamp(6) NOT NULL,
	updated_product_at timestamp(6) NOT NULL,
	deleted_product_at timestamp(6) NULL,
	active int NOT NULL,
	reserved int NOT NULL,
	category_category_id bigint NOT NULL,
    date_date_id bigint NOT NULL
);

alter table tb_products add constraint FK4v38xarenv0rucfoal7phwy8y foreign key (category_category_id) references tb_categories (category_id);
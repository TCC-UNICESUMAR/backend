CREATE TABLE tb_product_category(
	product_id bigint NOT NULL,
	category_id bigint NOT NULL
);
ALTER TABLE tb_product_category ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES tb_products (product_id) ;
ALTER TABLE tb_product_category  ADD CONSTRAINT fk_category_id FOREIGN KEY(category_id) REFERENCES tb_categories (category_id);
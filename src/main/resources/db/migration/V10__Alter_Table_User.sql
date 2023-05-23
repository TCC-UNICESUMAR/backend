ALTER TABLE tb_users ADD address bigint NULL;
ALTER TABLE tb_users ADD CONSTRAINT fk_address FOREIGN KEY (address) REFERENCES tb_addresses (address_id) ;
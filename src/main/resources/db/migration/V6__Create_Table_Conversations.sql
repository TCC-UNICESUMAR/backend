CREATE TABLE tb_conversations(
	conversation_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_one bigint NOT NULL,
	user_two bigint NOT NULL,
	created_at date NOT NULL,
	delete_at date NULL
);

ALTER TABLE tb_conversations ADD CONSTRAINT fk_user_one FOREIGN KEY (user_one) REFERENCES tb_users (user_id) ;
ALTER TABLE tb_conversations ADD CONSTRAINT fk_user_two FOREIGN KEY (user_two) REFERENCES tb_users (user_id) ;
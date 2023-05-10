CREATE TABLE tb_conversations(
	conversation_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_from bigint NOT NULL,
	user_to bigint NOT NULL,
	created_at date NOT NULL,
	delete_at date NULL
);

ALTER TABLE tb_conversations ADD CONSTRAINT fk_user_from FOREIGN KEY (user_from) REFERENCES tb_users (user_id) ;
ALTER TABLE tb_conversations ADD CONSTRAINT fk_user_to FOREIGN KEY (user_to) REFERENCES tb_users (user_id) ;
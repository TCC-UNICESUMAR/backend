CREATE TABLE tb_messages(
	message_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_id bigint NOT NULL,
	message varchar(255) NOT NULL,
	order_by int NOT NULL,
	created_at date NOT NULL,
	conversation_id bigint NOT NULL
);

ALTER TABLE tb_messages ADD CONSTRAINT fk_user_message FOREIGN KEY (user_id) REFERENCES tb_users (user_id);
ALTER TABLE tb_messages ADD CONSTRAINT fk_conversation_id FOREIGN KEY (conversation_id) REFERENCES tb_conversations (conversation_id);
CREATE TABLE tb_messages(
	message_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_user_id bigint NOT NULL,
	message varchar(255) NOT NULL,
	created_at timestamp(6) NOT NULL,
	conversation_conversation_id bigint NOT NULL
);
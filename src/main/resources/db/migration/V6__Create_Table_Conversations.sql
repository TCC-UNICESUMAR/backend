CREATE TABLE tb_conversations(
	conversation_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_one_user_id bigint NOT NULL,
	user_two_user_id bigint NOT NULL,
	created_at timestamp(6) NOT NULL,
	delete_at timestamp(6) NULL
);
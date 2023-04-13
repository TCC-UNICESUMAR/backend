ALTER TABLE tb_users
ADD COLUMN user_conversation bigint NULL;

ALTER TABLE tb_users ADD CONSTRAINT fk_user_conversations FOREIGN KEY (user_conversation) REFERENCES tb_conversations (conversation_id) ;
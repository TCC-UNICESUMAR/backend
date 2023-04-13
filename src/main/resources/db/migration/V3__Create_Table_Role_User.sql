CREATE TABLE tb_user_role(
	user_id bigint NOT NULL,
	role_id bigint NOT NULL
);
ALTER TABLE tb_user_role ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES tb_users (user_id) ;
ALTER TABLE tb_user_role  ADD CONSTRAINT fk_role_id FOREIGN KEY(role_id) REFERENCES tb_roles (role_id);

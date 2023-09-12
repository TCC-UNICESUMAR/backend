CREATE TABLE IF NOT EXISTS tb_user_role(
	user_id bigint NOT NULL,
	role_id bigint NOT NULL
);

alter table tb_user_role add constraint FK6g6aaa2yginq4fp9k1tg6ls3r foreign key (role_id) references tb_roles (role_id);
alter table tb_user_role add constraint FKpn4njnr07hiiam9pg4pp3xan7 foreign key (user_id) references tb_users (user_id);
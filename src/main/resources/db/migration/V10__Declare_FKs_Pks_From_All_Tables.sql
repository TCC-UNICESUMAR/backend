

alter table tb_conversations add constraint FK34vovua5ro66h1g21k5tq33ar foreign key (user_one_user_id) references tb_users (user_id);
alter table tb_conversations add constraint FKlvubydwyeie5secknthvqi6sk foreign key (user_two_user_id) references tb_users (user_id);
alter table tb_messages add constraint FKaxwyof0ad9winj1u3iyx2b3et foreign key (conversation_conversation_id) references tb_conversations (conversation_id);
alter table tb_messages add constraint FKcjkfyyooaktm2k0d2ahwpohtg foreign key (user_user_id) references tb_users (user_id);
alter table tb_products add constraint FK4v38xarenv0rucfoal7phwy8m foreign key (user_user_id) references tb_users (user_id);
alter table tb_user_role add constraint FK6g6aaa2yginq4fp9k1tg6ls3r foreign key (role_id) references tb_roles (role_id);
alter table tb_user_role add constraint FKpn4njnr07hiiam9pg4pp3xan7 foreign key (user_id) references tb_users (user_id);
alter table tb_users add constraint UK_pcg68g34ilhfgv3j5sy0oc2mr unique (cpf_or_cnpj);
alter table tb_users add constraint UK_grd22228p1miaivbn9yg178pm unique (email);
alter table tb_products_image_key add constraint FKq8plupmr9wr1q1vy31m9b0v91 foreign key (product_product_id) references tb_products (product_id);
alter table tb_products add constraint FK4v38xarenv0rucfoal7phwy8y foreign key (category_category_id) references tb_categories (category_id);

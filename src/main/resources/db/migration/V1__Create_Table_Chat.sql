  create schema if not exists tb_chat;
  create table tb_chat(
  	id int not null primary key,
  	email varchar(255) null
  );
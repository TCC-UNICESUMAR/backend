CREATE TABLE IF NOT EXISTS tb_templates_sms(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	message_template varchar(150) NOT NULL,
	message varchar(255) NOT NULL
);

insert into tb_templates_sms (id,message_template,message) values (1,'DONATION_ORDER_CREATED',
'Seu pedido de doação foi realizado com sucesso, em breve traremos mais informacões de como deverá prosseguir, o numero do pedido é: ');

insert into tb_templates_sms (id,message_template,message) values (2,'NOTIFICATE_INTERMEDIARY',
'Olá tudo bem? Oq acha de dar uma olhadinha em uma nova doacao para aprovar e fazer alguem muito feliz? Contamos com seu apoio. Att Equipe BFN <3. O numero do pedido é: ');

insert into tb_templates_sms (id,message_template,message) values (3,'NOTIFICATE_DONOR',
'Ola tudo bem? A doacao que vc publicou recentemente foi escolhida e aprovada pela instituicao que fara a intermediacao desse beneficio, e sabe o melhor? Voce ja pode enviar ate a Ong, contamos com seu apoio. Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (4,'NOTIFICATE_RECEIVED_WITH_STATUS_WAITING_DONOR',
'Ola tudo bem? O pedido ja esta sendo encaminhado ate a ONG, agora falta pouco! Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (5,'NOTIFICATE_RECEIVED_TO_GET_DONATION',
'Ola tudo bem? O pedido ja esta na Ong, agora so falta ir retirar! Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (6,'NOTIFICATE_DONOR_ONG',
'Ola tudo bem? Ja pode encaminhar a doacao ate a ONG! Att Equipe BFN.');
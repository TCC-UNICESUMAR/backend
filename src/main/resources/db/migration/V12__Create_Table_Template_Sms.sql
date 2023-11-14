CREATE TABLE IF NOT EXISTS tb_templates_sms(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	message_template varchar(150) NOT NULL,
	message varchar(255) NOT NULL
);

insert into tb_templates_sms (id,message_template,message) values (1,'DONATION_ORDER_CREATED',
'Seu pedido de doação foi realizado com sucesso, em breve traremos mais informacões de como deverá prosseguir! Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (2,'NOTIFICATE_INTERMEDIARY',
'Olá tudo bem? Oq acha de dar uma olhadinha em uma nova doacao para aprovar e fazer alguem muito feliz? Contamos com seu apoio. Att Equipe BFN <3. O numero do pedido é: ');

insert into tb_templates_sms (id,message_template,message) values (3,'NOTIFICATE_DONOR',
'Ola tudo bem? A doacao que vc publicou recentemente foi aprovada pela instituicao que fara a intermediacao desse beneficio, e sabe o melhor? Voce ja pode enviar ate a Ong. Contamos com seu apoio. Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (4,'NOTIFICATE_RECEIVED_WITH_STATUS_WAITING_DONOR',
'Ola tudo bem? O pedido ja esta sendo encaminhado ate a ONG, agora falta pouco! Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (5,'NOTIFICATE_RECEIVED_TO_GET_DONATION',
'Ola tudo bem? O pedido ja esta na Ong, agora so falta ir retirar! Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (6,'NOTIFICATE_DONOR_ONG',
'Ola tudo bem? Ja pode encaminhar a doacao ate a ONG! Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (7,'NOTIFICATE_DONOR_TO_APPROVE_ORDER_DONATION',
'Ola tudo bem? Que tal dar uma olhadinha nas solitacoes e aprovar um pedido que foi feito agora mesmo? Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (8,'NOTIFICATE_RECEIVED_WITH_STATUS_WAITING_ONG_APPROVED',
'Ola tudo bem? Seu pedido de doacao, esta esperando um ONG aprovar, agora falta pouco. Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (9,'NOTIFICATE_RECEIVED_WITH_STATUS_NOT_APPROVED_BY_DONOR',
'Ola tudo bem? Sentimos muito, mas seu pedido de doação foi negado pelo dono, não desista temos varias outras doações te esperando. Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (10,'NOTIFICATE_RECEIVED_WITH_STATUS_NOT_APPROVED_BY_ONG',
'Ola tudo bem? Sentimos muito, mas seu pedido de doação foi negado pela ONG que estava intermediando esse pedido, não desista temos varias outras doações te esperando. Att Equipe BFN.');

insert into tb_templates_sms (id,message_template,message) values (11,'NOTIFICATE_DONOR_WITH_CONGRATULATION_BY_ACTION',
'Ola tudo bem? Ficamos muito felizes em ver vc ajudando alguem com uma doação sua entregue, continue assim. Att Equipe BFN.');
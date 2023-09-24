CREATE TABLE IF NOT EXISTS tb_donations_status(
	donation_status_id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    date_date_id bigint NOT NULL,
    status_description varchar(255) NOT NULL,
    approved int NOT NULL,
    available_for_pickup int NOT NULL,
    waiting_ong_approve int NOT NULL,
    approved_by bigint NOT NULL
);

alter table tb_donations_status add constraint FKlk0njnr07hiiam3pg4pp4fgh7 foreign key (approved_by) references tb_users (user_id);
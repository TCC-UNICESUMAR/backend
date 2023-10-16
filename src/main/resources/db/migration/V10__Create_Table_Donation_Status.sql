CREATE TABLE IF NOT EXISTS tb_donations_status(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	status ENUM('CREATED', 'SUCCESS', 'CANCELED', 'WAITING_ONG_APPROVED', 'WAITING_DONOR_SEND', 'WAITING_RECEIVED_PICKUP') NOT NULL,
    approved int NULL,
    available_for_pickup int NOT NULL,
    waiting_ong_approve int NOT NULL,
    fk_user_approved_by bigint NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_donations_status add constraint FKlk0njnr07hiiam3pg4pp4fgh7 foreign key (fk_user_approved_by)
references tb_users (id);

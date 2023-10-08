CREATE TABLE IF NOT EXISTS tb_donations_status(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	status_description varchar(120) NOT NULL,
    approved int NOT NULL,
    available_for_pickup int NOT NULL,
    waiting_ong_approve int NOT NULL,
    fk_user_approved_by bigint NOT NULL,
    created_at timestamp(6) NOT NULL,
    updated_at timestamp(6) NOT NULL,
    deleted_at timestamp(6) NULL
);

alter table tb_donations_status add constraint FKlk0njnr07hiiam3pg4pp4fgh7 foreign key (fk_user_approved_by)
references tb_users (id);

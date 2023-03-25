CREATE TABLE tb_user_role(
	[user_id] [bigint] NOT NULL,
	[role_id] [bigint] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE tb_user_role  WITH CHECK ADD  CONSTRAINT [fk_user_id] FOREIGN KEY([user_id])
REFERENCES tb_user ([id])
GO

ALTER TABLE tb_user_role CHECK CONSTRAINT [fk_user_id]
GO

ALTER TABLE tb_user_role  WITH CHECK ADD  CONSTRAINT [fk_role_id] FOREIGN KEY([role_id])
REFERENCES tb_role ([id])
GO

ALTER TABLE tb_user_role CHECK CONSTRAINT [fk_role_id]
GO

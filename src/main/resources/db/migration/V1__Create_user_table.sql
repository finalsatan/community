create table user
(
	id int auto_increment,
	account_id varchar(100) not null,
	name varchar(50) not null,
	token char(36) not null,
	created_at bigint not null,
	updated_at bigint not null,
	constraint user_id_uindex
		unique (id)
);

alter table user
	add primary key (id);


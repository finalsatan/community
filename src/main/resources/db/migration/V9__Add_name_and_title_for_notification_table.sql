alter table notification
	add notifier_name VARCHAR(50) not null;

alter table notification
	add outer_title VARCHAR(256) not null;
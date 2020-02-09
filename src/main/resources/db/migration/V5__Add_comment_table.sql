create table comment
(
	id BIGINT auto_increment primary key,
	parent_id BIGINT not null comment '父类id',
	type int not null comment '父类类型',
	commentator int not null comment '评论人id',
	content TEXT not null,
	gmt_create BIGINT not null,
	gmt_modified BIGINT not null,
	like_count BIGINT default 0 not null
)
comment '评论';
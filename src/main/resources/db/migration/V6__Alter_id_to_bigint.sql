alter table question modify id BIGINT auto_increment;
alter table user modify id BIGINT auto_increment;
alter table comment modify commentator BIGINT not null comment '评论人id';
alter table question modify creator BIGINT null;
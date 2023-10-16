--liquibase formatted sql

--changeset rustdv:1
create table users
(
    id          bigserial primary key,
    name        varchar(32)        not null,
    email       varchar(32) unique not null,
    password    varchar(128)       not null,
    register_at date
);
--rollback DROP table users

--changeset rustdv:2
create table post
(
    id       bigserial primary key,
    users_id bigint       not null references users (id) on delete cascade,
    title    varchar(128) not null,
    body     text         not null,
    created_at date not null
);
--rollback DROP table post

--changeset rustdv:3
create table post_image
(
    id bigserial primary key ,
    post_id bigint references post (id) on delete cascade,
    image   bytea
);
--rollback DROP table post_image

--changeset rustdv:4
create table sender_recipient
(
    id             bigserial primary key,
    sender_id      bigint      not null references users (id),
    recipient_id   bigint      not null references users (id),
    request_status varchar(32) not null,
    unique (sender_id, recipient_id)
);
--rollback DROP table sender_recipient
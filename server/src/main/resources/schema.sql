create table if not exists quote (id bigint primary key generated always as identity, "text" text);

create table if not exists "users" (
    id bigint primary key generated always as identity,
    "email" text unique,
    "nickname" text,
    "password_hash" text
);

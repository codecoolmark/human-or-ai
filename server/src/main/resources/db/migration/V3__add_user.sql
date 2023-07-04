create table if not exists "user" (
    id bigint primary key generated always as identity,
    "email" text unique,
    "nickname" text,
    "password_hash" text
);

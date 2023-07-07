alter table "user" add column is_admin boolean not null default false;
alter table "user" alter column is_admin drop default;
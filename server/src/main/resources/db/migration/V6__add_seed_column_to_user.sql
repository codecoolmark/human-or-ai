alter table "user" add column quote_seed bigint default random();
alter table "user" alter column quote_seed drop default;
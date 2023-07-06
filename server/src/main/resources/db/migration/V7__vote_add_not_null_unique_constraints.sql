alter table vote alter column quote_id set not null;
alter table vote alter column is_real set not null;
alter table vote alter column created set not null;
alter table vote alter column user_id set not null;
alter table vote add constraint unique_quote_id_user_id unique(quote_id, user_id);
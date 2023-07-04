create table vote (id bigint primary key generated always as identity,
    quote_id bigint references quote(id),
    is_real boolean,
    created timestamp)
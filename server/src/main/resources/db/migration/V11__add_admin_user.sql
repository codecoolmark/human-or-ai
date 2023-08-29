-- create a admin user with the email address admin@example.org and the password IamAdmin
insert into "user" (email, nickname, password_hash, quote_seed, is_admin)
values ('admin@example.org', 'admn',
        '$argon2id$v=19$m=19456,t=2,p=1$MR0je4Q/2/md8NsL5OeHfQ$9UjNjTEfRGl9fKnQIJVNEGpQde/ZM8/J7WxsElqYu+Q',
        '5847572137751730130',
        true);
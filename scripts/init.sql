create table urls (
    id serial primary key,
    url_hash char(7) not null,
    short_url varchar(64) not null,
    long_url text not null
);

create table users (
    id bigserial primary key,
    email varchar(100) not null,
    password_hash text not null,
    created_at timestamp default current_timestamp,
    role_type varchar(5) not null
);
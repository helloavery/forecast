drop table if exists users.users;

create table users.users
(
    user_id int not null,
    first_name varchar(36) not null,
    last_name varchar(36) not null,
    username varchar(255) not null,
    email varchar(36) not null,
    country_code varchar(9) not null,
    phone_number varchar(12) not null,
    region varchar(24) not null,
    in_date timestamp not null,
    out_date timestamp not null
);


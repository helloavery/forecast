drop table if exists users.account_status;

create table users.account_status
(
    status varchar(1) not null,
    email_verified varchar(1) not null,
    user_id int not null,
    active_and_verified int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


drop table if exists forecaster.account_status;

create table forecaster.account_status
(
    status varchar(1) not null,
    email_verified varchar(1) not null,
    user_id int not null,
    active_and_verified int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


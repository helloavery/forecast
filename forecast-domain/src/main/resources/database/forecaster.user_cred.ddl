drop table if exists forecaster.user_cred;

create table forecaster.user_cred
(
    user_id int not null,
    password varchar(2048) not null,
    created_date timestamp not null,
    times_modified int not null
);


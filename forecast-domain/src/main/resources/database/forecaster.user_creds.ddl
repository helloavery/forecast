drop table if exists forecaster.user_creds;

create table forecaster.user_creds
(
    user_id int not null,
    audit_action varchar(2048) not null,
    created_date timestamp not null,
    times_modified int not null
);


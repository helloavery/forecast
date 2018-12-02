drop table if exists users.user_creds;

create table users.user_creds
(
    user_id int not null,
    audit_action varchar(2048) not null,
    times_modified int not null,
    created_date timestamp not null,
    modified_date timestamp not null
);


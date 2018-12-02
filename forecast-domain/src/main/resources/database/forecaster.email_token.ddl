drop table if exists forecaster.email_token;

create table forecaster.email_token
(
    email varchar(255) not null,
    token varchar(255) not null,
    in_date timestamp not null,
    out_date timestamp not null
);


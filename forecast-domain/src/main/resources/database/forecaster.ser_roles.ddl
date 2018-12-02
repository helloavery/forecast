drop table if exists forecaster.ser_roles;

create table forecaster.ser_roles
(
    user_id int not null,
    role_id int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


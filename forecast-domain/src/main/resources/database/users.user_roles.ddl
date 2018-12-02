drop table if exists users.user_roles;

create table users.user_roles
(
    user_id int not null,
    role_id int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


drop table if exists user_roles;

create table user_roles
(
    user_id int not null,
    role_id int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


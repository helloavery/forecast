drop table if exists users.sequence;

create table users.sequence
(
    sequence_name varchar(50) not null,
    next_value bigint not null
);


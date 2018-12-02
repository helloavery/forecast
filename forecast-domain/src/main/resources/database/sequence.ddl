drop table if exists sequence;

create table sequence
(
    sequence_name varchar(50) not null,
    next_value bigint not null
);


drop table if exists forecaster.sequence;

create table forecaster.sequence
(
    sequence_name varchar(50) not null,
    next_value bigint not null
);


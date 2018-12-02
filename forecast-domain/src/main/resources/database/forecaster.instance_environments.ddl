drop table if exists forecaster.instance_environments;

create table forecaster.instance_environments
(
    environment_key varchar(50) not null,
    environment_url varchar(50) not null
);


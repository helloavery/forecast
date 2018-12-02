drop table if exists forecaster.audit_trail;

create table forecaster.audit_trail
(
    audit_id int not null,
    audit_action varchar(45) not null,
    audit_description varchar(255) not null,
    actioned_by varchar(30) not null,
    date_actioned timestamp not null
);


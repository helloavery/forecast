drop table if exists forecaster.audit_trail_product;

create table forecaster.audit_trail_product
(
    audit_id int not null,
    product_type varchar(255) not null,
    audit_action varchar(32) not null,
    audit_description varchar(2048) not null,
    actioned_by varchar(16) not null,
    date_actioned timestamp not null
);


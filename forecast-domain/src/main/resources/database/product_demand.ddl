drop table if exists product_demand;

create table product_demand
(
    product_demand_id int not null,
    product_code varchar(32) not null,
    entry_date timestamp not null,
    warehouse varchar(16) not null,
    product_category varchar(32) not null,
    order_demand varchar(8) not null,
    user_id int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


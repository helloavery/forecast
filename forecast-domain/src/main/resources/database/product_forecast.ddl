drop table if exists product_forecast;

create table product_forecast
(
    product_forecast_id int not null,
    sku int not null,
    national_inventory int not null,
    lead_time int not null,
    in_transit_qty int not null,
    forecast_three_months int not null,
    forecast_six_months int not null,
    forecast_nine_months int not null,
    sales_one_month int not null,
    sales_three_months int not null,
    sales_six_months int not null,
    sales_nine_months int not null,
    min_bank int not null,
    potential_issues varchar(64) not null,
    pieces_past_due int not null,
    perf_six_month_avg float8 not null,
    perf_twelve_month_avg float8 not null,
    local_bo_qty int not null,
    deck_risk varchar(64) not null,
    oe_constraint varchar(64) not null,
    ppap_risk varchar(64) not null,
    stop_auto_buy varchar(64) not null,
    rev_stop varchar(64) not null,
    backorder varchar(64) not null,
    user_id int not null,
    in_date timestamp not null,
    out_date timestamp not null
);


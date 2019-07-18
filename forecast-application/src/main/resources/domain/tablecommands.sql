CREATE TYPE role AS ENUM ('ADMIN', 'USER');
CREATE TYPE region AS ENUM ('AMERICAS', 'EMEA', 'APJ', 'APSG');

CREATE SEQUENCE user_sequence START WITH 101 INCREMENT BY 1;

CREATE TABLE Users (

  user_id VARCHAR(10) PRIMARY KEY,
  password VARCHAR(20) NOT NULL,
  first_name VARCHAR(15) NOT NULL,
  last_name VARCHAR(25) NOT NULL,
  username VARCHAR(7) NOT NULL,
  email VARCHAR(20) NOT NULL,
  country_code VARCHAR(3) NOT NULL,
  phone_number VARCHAR(15) NOT NULL,
  business_unit_id VARCHAR(10) NOT NULL,
  region region NOT NULL,
  Date TIMESTAMP NOT NULL,
    CONSTRAINT chk_deleted CHECK (deleted=NULL OR deleted='D')
    CONSTRAINT fk_business_unit FOREIGN KEY business_unit_id REFERENCES business_units(business_unit_id)

);

SELECT cron.schedule('0 */4 * * *', $$UPDATE users.account_status SET active_and_verified = (account_status.active = 1 and account_status.account_verified = 1)$$);

CREATE TABLE users.user_cred(
  user_cred_id INTEGER PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  user_id INTEGER NOT NULL,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users.users(user_id);

)

CREATE TABLE users.account_status (
  account_status_id INTEGER PRIMARY KEY,
  active INTEGER,
  email_verified INTEGER,
  active_and_verified BOOLEAN DEFAULT FALSE,
  user_id INTEGER
);

CREATE TABLE Roles (
  role_id VARCHAR(10) PRIMARY KEY,
  role varchar(255) DEFAULT NULL,
  CONSTRAINT chk_role CHECK (role=NULL OR role='ADMIN' OR role='USER')
);

CREATE TABLE User_Roles (
  user_id INTEGER NOT NULL,
  role_id VARCHAR(10) NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id),
  CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);

CREATE TABLE users.business_units(
  business_unit_id INTEGER PRIMARY KEY,
  business_unit VARCHAR(15) NOT NULL,
  unit_head VARCHAR(7) NOT NULL,
  description VARCHAR(255)
)


CREATE TABLE Product_Demand (

  product_demand_id VARCHAR(10) PRIMARY KEY,
  product_code VARCHAR(20) NOT NULL,
  warehouse VARCHAR(10) NOT NULL,
  product_category VARCHAR(20) NOT NULL,
  date TIMESTAMP NOT NULL,
  order_demand VARCHAR(7) NOT NULL,
  demand_user_id INTEGER REFERENCES users(user_id)

);


CREATE TABLE Product_Forecast (

  product_forecast_id VARCHAR(10) PRIMARY KEY,
  sku INTEGER NOT NULL,
  national_inventory INTEGER NOT NULL,
  lead_time INTEGER,
  in_transit_qty INT NOT NULL,
  forecast_three_months INT NOT NULL,
  forecast_six_months INT NOT NULL,
  forecast_nine_months INT NOT NULL,
  sales_one_month INT NOT NULL,
  sales_three_months INT NOT NULL,
  sales_six_months INT NOT NULL,
  sales_nine_months INT NOT NULL,
  min_bank iNT NOT NULL,
  potential_issues CHAR(1) NOT NULL,
  pieces_past_due INT NOT NULL,
  perf_six_month_avg DOUBLE PRECISION NOT NULL,
  perf_twelve_month_avg DOUBLE PRECISION NOT NULL,
  local_bo_qty INT NOT NULL,
  deck_risk CHAR(1) NOT NULL,
  oe_constraint CHAR(1) NOT NULL,
  ppap_risk CHAR(1) NOT NULL,
  stop_auto_buy CHAR(1) NOT NULL,
  rev_stop CHAR(1) NOT NULL,
  backorder CHAR(1) NOT NULL,
  forecast_user_id INTEGER REFERENCES users(user_id),
  CONSTRAINT chk_issues CHECK(potential_issues = 'Y' OR potential_issues = 'N'),
  CONSTRAINT chk_risk CHECK(deck_risk = 'Y' OR deck_risk = 'N'),
  CONSTRAINT chk_oe CHECK(oe_constraint = 'Y' OR oe_constraint = 'N'),
  CONSTRAINT chk_ppap_risk CHECK(ppap_risk = 'Y' OR ppap_risk = 'N'),
  CONSTRAINT chk_auto_buy CHECK(stop_auto_buy = 'Y' OR stop_auto_buy = 'N'),
  CONSTRAINT chk_rev_stop CHECK(rev_stop = 'Y' OR rev_stop = 'N'),
  CONSTRAINT chk_backorder CHECK(backorder = 'Y' OR backorder = 'N')


);

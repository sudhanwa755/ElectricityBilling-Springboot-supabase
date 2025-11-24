CREATE TABLE users(
  id IDENTITY PRIMARY KEY,
  username VARCHAR(80) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL,
  active BOOLEAN DEFAULT TRUE
);

CREATE TABLE customers(
  id IDENTITY PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(120),
  phone VARCHAR(20),
  address VARCHAR(1000),
  meter_no VARCHAR(40) UNIQUE NOT NULL,
  user_id BIGINT,
  CONSTRAINT fk_customer_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tariff(
  id IDENTITY PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  slab_start_kwh INT NOT NULL,
  slab_end_kwh INT,
  rate_per_kwh DOUBLE PRECISION NOT NULL,
  fixed_charge DOUBLE PRECISION NOT NULL,
  effective_from DATE NOT NULL,
  effective_to DATE
);

CREATE TABLE readings(
  id IDENTITY PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  period_start DATE NOT NULL,
  period_end DATE NOT NULL,
  previous_reading_kwh INT NOT NULL,
  current_reading_kwh INT NOT NULL,
  CONSTRAINT fk_reading_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
  CONSTRAINT chk_readings CHECK (current_reading_kwh >= previous_reading_kwh)
);

CREATE TABLE bills(
  id IDENTITY PRIMARY KEY,
  bill_no VARCHAR(40) UNIQUE NOT NULL,
  customer_id BIGINT NOT NULL,
  reading_id BIGINT NOT NULL,
  bill_date DATE NOT NULL,
  units INT NOT NULL,
  energy_charge DOUBLE PRECISION NOT NULL,
  fixed_charge DOUBLE PRECISION NOT NULL,
  taxes DOUBLE PRECISION NOT NULL,
  adjustments DOUBLE PRECISION DEFAULT 0,
  total DOUBLE PRECISION NOT NULL,
  status VARCHAR(20) DEFAULT 'DUE',
  CONSTRAINT fk_bill_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
  CONSTRAINT fk_bill_reading FOREIGN KEY (reading_id) REFERENCES readings(id)
);

CREATE TABLE payments(
  id IDENTITY PRIMARY KEY,
  bill_id BIGINT NOT NULL,
  paid_on DATE NOT NULL,
  amount DOUBLE PRECISION NOT NULL,
  method VARCHAR(30),
  txn_ref VARCHAR(80),
  CONSTRAINT fk_payment_bill FOREIGN KEY (bill_id) REFERENCES bills(id)
);

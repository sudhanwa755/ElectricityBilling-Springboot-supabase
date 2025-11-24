ALTER TABLE customers ADD CONSTRAINT uq_customers_email UNIQUE (email);
ALTER TABLE customers ADD CONSTRAINT uq_customers_phone UNIQUE (phone);

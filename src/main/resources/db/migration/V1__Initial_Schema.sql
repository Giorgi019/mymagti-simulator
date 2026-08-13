-- Create user table
CREATE TABLE _user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Create subscribers table
CREATE TABLE subscribers (
    id BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- Create service_packages table
CREATE TABLE service_packages (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    package_type VARCHAR(50) NOT NULL,
    price NUMERIC(19, 2) NOT NULL,
    megabytes INTEGER,
    validity_days INTEGER NOT NULL
);

-- Create subscriptions table
CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    subscriber_id BIGINT NOT NULL,
    package_id BIGINT NOT NULL,
    purchase_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    active BOOLEAN NOT NULL,
    CONSTRAINT fk_subscriber FOREIGN KEY (subscriber_id) REFERENCES subscribers (id),
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES service_packages (id)
);

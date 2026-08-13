-- Insert initial subscribers
INSERT INTO subscribers (phone_number, full_name, balance, status)
VALUES ('599123456', 'Giorgi Giorgadze', 20.00, 'ACTIVE'),
       ('599654321', 'Mariam Mariamidze', 5.50, 'ACTIVE');

-- Insert initial service packages
INSERT INTO service_packages (name, package_type, price, megabytes, validity_days)
VALUES ('Cocktail 1000 MB', 'INTERNET', 5.00, 1000, 30),
       ('Unlimited Calls', 'CALLS', 10.00, NULL, 30),
       ('Cocktail 5000 MB', 'INTERNET', 12.00, 5000, 30);

DELETE FROM tbl_rentals;
DELETE FROM tbl_customers;
DELETE FROM tbl_vehicles;

INSERT INTO tbl_customers (id, tax_id, full_name, email, phone) VALUES
  (998, '284828828282', 'John Doe The First', 'jdoe_first@voo.doo', '+23 555-555-2045'),
  (999, '333333444444', 'John Doe The Third', 'jdoe_third@voo.doo', '+23 777-555-1086');

INSERT INTO tbl_vehicles (id, model, brand, plate_number, plate_country, hour_price, daily_price) VALUES
  (997, 'Camaro SS 1962', 'Chevrolett', '18D4C0', 'Brazil', 1235, 29640),
  (998, 'Porsche 911 Carrera', 'Porsche', '82D90A', 'USA', 2459, 59016);

INSERT INTO tbl_rentals (id, vehicle_id, customer_id, rent_at, return_at, amount_to_pay) VALUES
  (1001, 997, 998, '2025-01-23 14:05:23', '2025-02-16 10:23:05', 918840),
  (1002, 997, 998, '2025-05-10 09:56:09', NULL, 0);

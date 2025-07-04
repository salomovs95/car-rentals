DELETE FROM tbl_rentals;
DELETE FROM tbl_customers;
DELETE FROM tbl_vehicles;

INSERT INTO tbl_customers
  (id, full_name, tax_id, phone, email) VALUES
  (996, 'user_998', 'user_998_tax_id', '19462919119', 'user_998@email.com'),
  (997, 'user_999', 'user_999_tax_id', '19194748880', 'user_999@email.com');

INSERT INTO tbl_vehicles
  (id, brand, model, daily_price, hour_price, plate_number, plate_country) VALUES
  (998, 'Chevrollet', 'Camaro SS', 12300, 2000, '19378', 'USA'),
  (999, 'Fiat', 'UNO Scad', 18000, 975, '19B73', 'Brazil');

INSERT INTO tbl_rentals
  (id, customer_id, vehicle_id, rent_at, amount_to_pay) VALUES
  (899, 996, 999, '2025-05-01 12:45', 0);

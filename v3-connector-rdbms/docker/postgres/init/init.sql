CREATE TABLE sales (
  id SERIAL PRIMARY KEY,
  sales_time TIMESTAMP NOT NULL,
  sales_id TEXT,
  item_id TEXT,
  amount INTEGER,
  unit_price INTEGER
);

INSERT INTO sales (sales_time, sales_id, item_id, amount, unit_price) VALUES
  ('2020-01-01 00:00:00', 'A001', 'B001', 1, 100),
  ('2020-01-01 00:00:01', 'A002', 'B002', 2, 200),
  ('2020-01-01 00:00:02', 'A003', 'B003', 3, 300),
  ('2020-01-01 00:00:03', 'A004', 'B004', 4, 400),
  ('2020-01-01 00:00:04', 'A005', 'B005', 5, 500);
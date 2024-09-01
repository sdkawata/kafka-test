ALTER TABLE sales ALTER COLUMN age DROP NOT NULL;
INSERT INTO sales(sales_time, sales_id, item_id, amount, unit_price, age) VALUES (now(), 'A007', 'B007', 7, 700, 7);
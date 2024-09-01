ALTER TABLE sales ADD COLUMN age INT NOT NULL;
INSERT INTO sales(sales_time, sales_id, item_id, amount, unit_price) VALUES (now(), 'A006', 'B006', 6, 600);

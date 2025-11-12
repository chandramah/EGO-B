-- Flyway Migration: Seed data (10 rows per table)
-- Note: Password hashes are BCrypt hashes for the string "password"

-- Suppliers (10)
INSERT INTO suppliers (name, email, phone, address, contact_person, is_active, created_at)
VALUES
 ('Supplier 1', 'supplier1@example.com', '+1-202-555-0001', '123 Main St', 'Alice', TRUE, NOW()),
 ('Supplier 2', 'supplier2@example.com', '+1-202-555-0002', '124 Main St', 'Bob', TRUE, NOW()),
 ('Supplier 3', 'supplier3@example.com', '+1-202-555-0003', '125 Main St', 'Carol', TRUE, NOW()),
 ('Supplier 4', 'supplier4@example.com', '+1-202-555-0004', '126 Main St', 'Dave', TRUE, NOW()),
 ('Supplier 5', 'supplier5@example.com', '+1-202-555-0005', '127 Main St', 'Eve', TRUE, NOW()),
 ('Supplier 6', 'supplier6@example.com', '+1-202-555-0006', '128 Main St', 'Frank', TRUE, NOW()),
 ('Supplier 7', 'supplier7@example.com', '+1-202-555-0007', '129 Main St', 'Grace', TRUE, NOW()),
 ('Supplier 8', 'supplier8@example.com', '+1-202-555-0008', '130 Main St', 'Heidi', TRUE, NOW()),
 ('Supplier 9', 'supplier9@example.com', '+1-202-555-0009', '131 Main St', 'Ivan', TRUE, NOW()),
 ('Supplier 10', 'supplier10@example.com', '+1-202-555-0010', '132 Main St', 'Judy', TRUE, NOW());

-- Products (10)
INSERT INTO products (name, sku, category, barcode, is_active, reorder_level, description, uint_price, tax_rate, created_at)
VALUES
 ('Grocery Item 1', 'SKU-0001', 'Grocery', 'BC100001', TRUE, 10, 'Sample product 1', 19.99, 5.00, NOW()),
 ('Grocery Item 2', 'SKU-0002', 'Grocery', 'BC100002', TRUE, 8, 'Sample product 2', 9.49, 0.00, NOW()),
 ('Beverage Item 3', 'SKU-0003', 'Beverages', 'BC100003', TRUE, 12, 'Sample product 3', 14.50, 12.00, NOW()),
 ('Snack Item 4', 'SKU-0004', 'Snacks', 'BC100004', TRUE, 6, 'Sample product 4', 4.99, 18.00, NOW()),
 ('Care Item 5', 'SKU-0005', 'Personal Care', 'BC100005', TRUE, 4, 'Sample product 5', 24.99, 5.00, NOW()),
 ('Household Item 6', 'SKU-0006', 'Household', 'BC100006', TRUE, 3, 'Sample product 6', 29.99, 12.00, NOW()),
 ('Snack Item 7', 'SKU-0007', 'Snacks', 'BC100007', TRUE, 9, 'Sample product 7', 2.49, 0.00, NOW()),
 ('Beverage Item 8', 'SKU-0008', 'Beverages', 'BC100008', TRUE, 7, 'Sample product 8', 7.99, 18.00, NOW()),
 ('Grocery Item 9', 'SKU-0009', 'Grocery', 'BC100009', TRUE, 5, 'Sample product 9', 12.00, 5.00, NOW()),
 ('Household Item 10', 'SKU-0010', 'Household', 'BC100010', TRUE, 2, 'Sample product 10', 39.00, 12.00, NOW());

-- Stock Batches (10)
-- Assume product ids 1..10 exist from the insert above
INSERT INTO stock_batches (product_id, quantity, cost_price, expiry_date, location, batch_number, created_at)
VALUES
 (1, 50, 12.00, DATE_ADD(CURDATE(), INTERVAL 180 DAY), 'Aisle-1', 'BATCH-SKU-0001-1', NOW()),
 (2, 40, 5.50, NULL, 'Aisle-1', 'BATCH-SKU-0002-1', NOW()),
 (3, 30, 9.00, DATE_ADD(CURDATE(), INTERVAL 120 DAY), 'Aisle-2', 'BATCH-SKU-0003-1', NOW()),
 (4, 100, 3.00, DATE_ADD(CURDATE(), INTERVAL 365 DAY), 'Aisle-3', 'BATCH-SKU-0004-1', NOW()),
 (5, 25, 16.00, DATE_ADD(CURDATE(), INTERVAL 200 DAY), 'Backroom', 'BATCH-SKU-0005-1', NOW()),
 (6, 10, 20.00, NULL, 'Backroom', 'BATCH-SKU-0006-1', NOW()),
 (7, 120, 1.50, DATE_ADD(CURDATE(), INTERVAL 90 DAY), 'Aisle-2', 'BATCH-SKU-0007-1', NOW()),
 (8, 60, 5.50, DATE_ADD(CURDATE(), INTERVAL 60 DAY), 'Aisle-1', 'BATCH-SKU-0008-1', NOW()),
 (9, 35, 8.50, NULL, 'Clearance', 'BATCH-SKU-0009-1', NOW()),
 (10, 15, 25.00, DATE_ADD(CURDATE(), INTERVAL 240 DAY), 'Backroom', 'BATCH-SKU-0010-1', NOW());

-- Purchase Orders (10)
-- Assume supplier ids 1..10 exist
INSERT INTO purchase_orders (supplier_id, expected_date, status, order_number, notes, created_at)
VALUES
 (1, DATE_ADD(CURDATE(), INTERVAL 10 DAY), 'DRAFT', 'PO-10001', 'Seed PO 1', NOW()),
 (2, DATE_ADD(CURDATE(), INTERVAL 12 DAY), 'PENDING', 'PO-10002', 'Seed PO 2', NOW()),
 (3, DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'APPROVED', 'PO-10003', 'Seed PO 3', NOW()),
 (4, DATE_ADD(CURDATE(), INTERVAL 9 DAY), 'ORDERED', 'PO-10004', 'Seed PO 4', NOW()),
 (5, DATE_ADD(CURDATE(), INTERVAL 15 DAY), 'ORDERED', 'PO-10005', 'Seed PO 5', NOW()),
 (6, DATE_ADD(CURDATE(), INTERVAL 6 DAY), 'PENDING', 'PO-10006', 'Seed PO 6', NOW()),
 (7, DATE_ADD(CURDATE(), INTERVAL 11 DAY), 'DRAFT', 'PO-10007', 'Seed PO 7', NOW()),
 (8, DATE_ADD(CURDATE(), INTERVAL 8 DAY), 'APPROVED', 'PO-10008', 'Seed PO 8', NOW()),
 (9, DATE_ADD(CURDATE(), INTERVAL 5 DAY), 'ORDERED', 'PO-10009', 'Seed PO 9', NOW()),
 (10, DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'PENDING', 'PO-10010', 'Seed PO 10', NOW());

-- Purchase Order Items (10) referencing the orders/products above
INSERT INTO purchase_order_items (purchase_order_id, product_id, quantity, cost_price, received_quantity)
VALUES
 (1, 1, 20, 12.00, 0),
 (2, 2, 15, 5.50, 0),
 (3, 3, 10, 9.00, 0),
 (4, 4, 50, 3.00, 0),
 (5, 5, 12, 16.00, 0),
 (6, 6, 5, 20.00, 0),
 (7, 7, 60, 1.50, 0),
 (8, 8, 25, 5.50, 0),
 (9, 9, 18, 8.50, 0),
 (10, 10, 8, 25.00, 0);

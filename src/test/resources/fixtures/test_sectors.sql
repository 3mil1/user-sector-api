DELETE FROM sectors WHERE id IN (1, 2, 3, 6, 7, 13);

INSERT INTO sectors (id, name, parent_id) VALUES 
(1, 'Manufacturing', NULL),
(2, 'Service', NULL),
(3, 'Other', NULL);

INSERT INTO sectors (id, name, parent_id) VALUES 
(6, 'Food and Beverage', 1),
(7, 'Textile and Clothing', 1),
(13, 'Furniture', 1);

INSERT INTO sectors (id, name, parent_id) VALUES 
(42, 'Fish & fish products', 6),
(43, 'Beverages', 6),
(44, 'Clothing', 7),
(45, 'Textile', 7),
(98, 'Kitchen', 13),
(99, 'Project furniture', 13);
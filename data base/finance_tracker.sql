
CREATE DATABASE IF NOT EXISTS finance_tracker;

USE finance_tracker;

DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    date  DATE NOT NULL,
    time  TIME NOT NULL,
    description VARCHAR(255) NOT NULL,
    vendor      VARCHAR(100) NOT NULL,
    amount      DECIMAL(10,2) NOT NULL,
    type ENUM('DEPOSIT', 'PAYMENT') NOT NULL,
    created_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_amount_positive CHECK (amount >= 0)
);

INSERT INTO transactions (saved_date, saved_time, description, vendor, amount, transaction_type)
VALUES ('2025-04-15', '10:13:25', 'ergonomic keyboard', 'Amazon', 89.50, 'PAYMENT'),
('2025-04-15', '11:15:00', 'Invoice 1001 paid', 'Joe', 1500.00, 'DEPOSIT'),
('2025-04-16', '14:30:45', 'Grocery shopping', 'Walmart', 120.35, 'PAYMENT'),
('2026-03-17', '09:05:10', 'Gasoline', 'Shell', 45.00, 'PAYMENT'),
('2026-03-18', '12:30:00', 'Monthly rent payment', 'ABC Apartments', 2000.00, 'PAYMENT'),
('2026-03-19', '15:20:30', 'Dinner with friends', 'Cheesecake Factory', 85.20, 'PAYMENT'),
('2026-04-21', '08:45:00', 'Salary deposit', 'ABC Company', 5000.00, 'DEPOSIT'),
('2026-04-23', '14:00:15', 'Haircut', "Mario's Barber Shop", 35.00, 'PAYMENT'),
('2026-04-30', '10:30:33', 'Babysitting', 'Carl', 50.00, 'DEPOSIT'),
('2026-04-30', '10:30:33', 'Babysitting', 'Carla', 50.00, 'DEPOSIT'),
('2026-03-20', '10:20:34', 'Amazon', 'hair ties', 5.00, 'PAYMENT'),
('2026-05-01', '10:30:33', 'paper', 'amazon', 10.00, 'DEPOSIT');
CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    to_customer_id INT NOT NULL,
    to_customer_email VARCHAR(100) NOT NULL,
    sender VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
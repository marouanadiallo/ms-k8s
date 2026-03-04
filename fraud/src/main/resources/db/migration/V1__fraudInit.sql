CREATE TABLE IF NOT EXISTS fraud_history (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    is_fraudster BOOLEAN NOT NULL DEFAULT TRUE
);
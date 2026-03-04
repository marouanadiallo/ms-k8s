CREATE TABLE IF NOT EXISTS fraud_history (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    is_fraudster BOOLEAN NOT NULL DEFAULT TRUE,
);
CREATE DATABASE IF NOT EXISTS `wallet-consumer`;

USE `wallet-consumer`;

CREATE TABLE IF NOT EXISTS balances (
    id VARCHAR(255) PRIMARY KEY,
    account_id VARCHAR(255),
    balance INT DEFAULT 0
);

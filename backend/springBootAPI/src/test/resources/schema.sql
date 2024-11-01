CREATE DATABASE accountManagementSys;
USE accountManagementSys;

CREATE TABLE users (
                       username VARCHAR(20) PRIMARY KEY,
                       name VARCHAR(40) NOT NULL,
                       email VARCHAR(50),
                       phone VARCHAR(20)
);

CREATE TABLE employees (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           user VARCHAR(20) NOT NULL REFERENCES users (username)
);

CREATE TABLE customers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           user VARCHAR(20) NOT NULL REFERENCES users (username),
                           created_date DATETIME NOT NULL DEFAULT NOW(),
                           address VARCHAR(100) NOT NULL
);

CREATE TABLE transaction_type(
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 type VARCHAR(20) UNIQUE NOT NULL
);


CREATE TABLE statuses(
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         status VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE account(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        balance DECIMAL NOT NULL DEFAULT 0,
                        customer VARCHAR(20) NOT NULL REFERENCES customers (id),
                        name VARCHAR(50),
                        start DATETIME NOT NULL DEFAULT NOW(),
                        status INT NOT NULL DEFAULT 1 REFERENCES statuses (id),

                        CONSTRAINT UNIQUE(customer, name)
);

CREATE TABLE transactions(
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             to_account INT REFERENCES accounts (id),
                             from_account INT REFERENCES accounts(id),
                             type INT NOT NULL REFERENCES transaction_type (id),
                             amount DECIMAL NOT NULL,
                             description VARCHAR(50),
                             datetime DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE beneficiaries(
                              customer INT REFERENCES customers (id),
                              account INT REFERENCES account (id),
                              relationship VARCHAR(40),
                              CONSTRAINT PRIMARY KEY (customer, account)
);
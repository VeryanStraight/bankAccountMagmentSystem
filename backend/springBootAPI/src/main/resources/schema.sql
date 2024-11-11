CREATE TABLE users (
                       username VARCHAR(20) PRIMARY KEY,
                       name VARCHAR(40) NOT NULL,
                       email VARCHAR(50) UNIQUE,
                       phone VARCHAR(20) UNIQUE
);

CREATE TABLE employees (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                            password VARCHAR(50) NOT NULL,
                           username VARCHAR(20) NOT NULL UNIQUE,
                            FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE customers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           password VARCHAR(50) NOT NULL,
                           username VARCHAR(20) NOT NULL UNIQUE,
                           created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           address VARCHAR(100) NOT NULL,

                            FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE transaction_type(
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 type VARCHAR(20) UNIQUE NOT NULL
);


CREATE TABLE statuses(
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         status VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE accounts(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        balance DECIMAL NOT NULL DEFAULT 0,
                        customer INT NOT NULL,
                        name VARCHAR(50),
                        start DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status INT NOT NULL DEFAULT 1,

                        FOREIGN KEY (customer) REFERENCES customers (id),
                        FOREIGN KEY (status) REFERENCES statuses (id),

                        CONSTRAINT unique_customer_name UNIQUE (customer, name));

CREATE TABLE transactions(
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             to_account INT,
                             from_account INT,
                             type INT NOT NULL,
                             amount DECIMAL NOT NULL,
                             description VARCHAR(50),
                             datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            FOREIGN KEY (to_account) REFERENCES accounts (id) ON DELETE SET NULL,
                             FOREIGN KEY (from_account) REFERENCES accounts (id) ON DELETE SET NULL,
                             FOREIGN KEY (type) REFERENCES transaction_type (id)
);

CREATE TABLE beneficiaries(
                              customer INT,
                              account INT,
                              relationship VARCHAR(40),
                              PRIMARY KEY (customer, account),
                              FOREIGN KEY (customer) REFERENCES customers (id),
                              FOREIGN KEY (account) REFERENCES accounts (id)
);
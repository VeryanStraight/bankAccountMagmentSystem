CREATE TABLE users (
                       username VARCHAR(20) PRIMARY KEY,
                       password VARCHAR(50) NOT NULL,
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
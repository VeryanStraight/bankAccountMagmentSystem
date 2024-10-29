CREATE DATABASE accountManagementSys;
USE accountManagementSys;

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

INSERT INTO statuses
VALUES (1, "Active");

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


INSERT INTO transaction_type (type)
VALUES ("Deposit"),
		("Withdrawal"),
		("Transfer");
		
INSERT INTO statuses (status)
VALUES ("Inactive");
		
INSERT INTO users (username, password, name)
VALUES ("username", "password", "name"),
		("sasmith", "12345", "sam"),
		("tojhonson", "abcd", "tom"),
		("jabaker", "1q2w3e", "jake"),
		("sacarp", "qwerty", "sally");
		
INSERT INTO customers (user, address)
VALUES ("sasmith", "sam_address"),
       ("tojhonson", "tom_address"),
       ("jabaker", "jake_address");

INSERT INTO employees (user)
VALUES ("username"), ("sasmith"), ("sacarp");

INSERT INTO accounts (balance, customer, name, start)
VALUES (1005, 1, "account_name", '2024-10-01'),
       (61005, 1, "account_name2", '2002-10-10'),
       (2005, 2, "cust2Account", '2005-05-5'),
       (17005, 3, "cust3Account", '2010-11-27');
		
INSERT INTO transactions (to_account, from_account, type, amount, description, datetime)
VALUES (null, 1, 2, 1005, "take out money", '2024-10-03 10:22:02'),
       (3, null, 1, 1028, "deposit money", NOW()),
       (4, 2, 3, 25, "pay someone", '2020-10-03 5:42:02');

INSERT INTO beneficiaries (customer, account, relationship)
VALUES (3, 1, "son"),
       (2, 1, "father"),
        (1, 3, null);
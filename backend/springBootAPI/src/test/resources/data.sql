INSERT INTO transaction_type (type)
VALUES ("Deposit"),
		("Withdrawal"),
		("Transfer");
		
INSERT INTO statuses (status)
VALUES ("Active"),("Inactive");
		
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
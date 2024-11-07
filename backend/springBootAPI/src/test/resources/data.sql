INSERT INTO transaction_type (type)
VALUES ('Deposit'),
		('Withdrawal'),
		('Transfer');
		
INSERT INTO statuses (status)
VALUES ('Active'),('Inactive');
		
INSERT INTO users (username, name)
VALUES ('username', 'name'),
		('sasmith', 'sam'),
		('tojhonson', 'tom'),
		('jabaker', 'jake'),
		('sacarp', 'sally');
		
INSERT INTO customers (username, address, password)
VALUES ('sasmith', 'sam_address', '12345'),
       ('tojhonson', 'tom_address', 'abcd'),
       ('jabaker', 'jake_address', '1q2w3e');

INSERT INTO employees (username, password)
VALUES ('username', 'password'), ('sasmith', '12345'), ('sacarp', 'qwerty');

INSERT INTO accounts (balance, customer, name, start)
VALUES (1005, 1, 'account_name', '2024-10-01'),
       (61005, 1, 'account_name2', '2002-10-10'),
       (2005, 3, 'cust2Account', '2005-05-5'),
       (17005, 3, 'cust3Account', '2010-11-27');
		
INSERT INTO transactions (to_account, from_account, type, amount, description, datetime)
VALUES (null, 1, 2, 1005, 'take out money', '2024-10-03 10:22:02'),
       (null, 3, 1, 1028, 'deposit money', NOW()),
       (4, 3, 3, 25, 'pay someone', '2020-10-03 5:42:02');

INSERT INTO beneficiaries (customer, account, relationship)
VALUES (3, 1, 'son'),
        (1, 3, null);
INSERT INTO transaction_type (type)
SELECT * FROM (
                  SELECT 'Deposit' AS type UNION ALL
                  SELECT 'Withdrawal' UNION ALL
                  SELECT 'Transfer'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM transaction_type);

INSERT INTO statuses (status)
SELECT * FROM (
                  SELECT 'Active' AS status UNION ALL
                  SELECT 'Inactive'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM statuses);

INSERT INTO users (username, name)
SELECT * FROM (
                  SELECT 'username' AS username, 'name' AS name UNION ALL
                  SELECT 'sasmith', 'sam' UNION ALL
                  SELECT 'tojhonson', 'tom' UNION ALL
                  SELECT 'jabaker', 'jake' UNION ALL
                  SELECT 'sacarp', 'sally'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users);

INSERT INTO customers (username, address, password)
SELECT * FROM (
                  SELECT 'sasmith' AS username, 'sam_address' AS address, '12345' AS password UNION ALL
                  SELECT 'tojhonson', 'tom_address', 'abcd' UNION ALL
                  SELECT 'jabaker', 'jake_address', '1q2w3e'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM customers);

INSERT INTO employees (username, password)
SELECT * FROM (
                  SELECT 'username' AS username, 'password' AS password UNION ALL
                  SELECT 'sasmith', '12345' UNION ALL
                  SELECT 'sacarp', 'qwerty'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM employees);

INSERT INTO accounts (balance, customer, name, start)
SELECT * FROM (
                  SELECT 1005 AS balance, 1 AS customer, 'account_name' AS name, '2024-10-01' AS start UNION ALL
                  SELECT 61005, 1, 'account_name2', '2002-10-10' UNION ALL
                  SELECT 2005, 3, 'cust2Account', '2005-05-05' UNION ALL
                  SELECT 17005, 3, 'cust3Account', '2010-11-27'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM accounts);

INSERT INTO transactions (to_account, from_account, type, amount, description, datetime)
SELECT * FROM (
                  SELECT NULL AS to_account, 1 AS from_account, 2 AS type, 1005 AS amount, 'take out money' AS description, '2024-10-03 10:22:02' AS datetime UNION ALL
                  SELECT NULL, 3, 1, 1028, 'deposit money', NOW() UNION ALL
                  SELECT 4, 3, 3, 25, 'pay someone', '2020-10-03 5:42:02'
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM transactions);

INSERT INTO beneficiaries (customer, account, relationship)
SELECT * FROM (
                  SELECT 3 AS customer, 1 AS account, 'son' AS relationship UNION ALL
                  SELECT 1, 3, NULL
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM beneficiaries);

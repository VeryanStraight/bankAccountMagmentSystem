package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@Transactional
class ServiceImplTest {
    //TODO: need to check invalid inputs

    @Autowired
    Service service;

    @Test
    void createUserTest() {
        User user = new User("testusername", "testname");
        try {
            User createdUser = service.createUser(user);
            assertNotEquals(createdUser, null);
            User foundUser = service.getUserByUsername("testusername");
            assertEquals(createdUser, foundUser);
        } catch (NoSuchRecordException | AlreadyExistsException e) {
            fail();
        }

    }

    @Test
    void createCustomerTest() {
        User user = new User("testusername", "testname");
        try {
            User createdUser = service.createUser(user);

            Customer customer = new Customer(createdUser, "testpassword");

            Customer createdCustomer = service.createCustomer(customer);
            Customer foundCustomer = service.getCustomerByUsername("testusername");

            assertEquals(createdCustomer, foundCustomer);
        } catch (AlreadyExistsException | NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void createEmployeeTest() {
        User user = new User("testusername", "testname");
        try {
            User createdUser = service.createUser(user);

            Employee employee = new Employee(createdUser, "testpassword");
            Employee createdEmployee = service.createEmployee(employee);
            Employee foundEmployee = service.getEmployeeByUsername("testusername");

            assertEquals(createdEmployee, foundEmployee);
        } catch (AlreadyExistsException | NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void createAccountTest() {
        Customer customer = null;
        try {
            customer = service.getCustomerByUsername("sasmith");
            Account account = new Account(customer);
            Account createdAccount = service.createAccount(account);

            List<Account> foundAccounts = service.getAccountByCustomerId(customer.getId());

            assertTrue(foundAccounts.contains(createdAccount));
        } catch (NoSuchRecordException | AlreadyExistsException e) {
            fail();
        }

    }

    @Test
    void createTransactionTest() {
        //TODO create transactions for all types
        //TODO make a better way of getting transactiontypes
        TransactionType type = new TransactionType(3, "Transfer");
        Transaction transaction = new Transaction(type, new BigDecimal("10"));
        try {
            Account toAccount = service.getAccountByCustomerId(1).get(0);
            Account fromAccount = service.getAccountByCustomerId(2).get(0);
            transaction.setToAccount(toAccount);
            transaction.setFromAccount(fromAccount);

            Transaction createdTransaction = service.createTransaction(transaction);
            Transaction foundTransaction = service.getTransactionByAccountId(fromAccount.getId()).get(0);
            assertEquals(createdTransaction, foundTransaction);

            Account toAccountAfter = service.getAccountByCustomerId(1).get(0);
            Account fromAccountAfter = service.getAccountByCustomerId(2).get(0);

            BigDecimal ten = new BigDecimal("10");
            assertEquals(toAccountAfter.getBalance().add(ten), toAccount.getBalance());
            assertEquals(fromAccountAfter.getBalance().subtract(ten), toAccount.getBalance());
        } catch (NoSuchRecordException | AlreadyExistsException | InvalidInputException e) {
            fail();
        }


    }

    @Test
    void getUserByUsernameTest() {
        try {
            User user = service.getUserByUsername("sasmith");
            assertNotEquals(null, user);
        } catch (NoSuchRecordException e) {
            fail();
        }
        
    }

    @Test
    void getCustomerByUsernameTest() {
        try {
            Customer customer = service.getCustomerByUsername("sasmith");
            assertNotEquals(null, customer);

        } catch (NoSuchRecordException e) {
            fail();
        }
            }

    @Test
    void getTransactionByAccountIdTest() {
        TransactionType type = new TransactionType(3, "Transfer");
        Transaction transaction = new Transaction(type, new BigDecimal("10"));
        try {
            Account toAccount = service.getAccountByCustomerId(1).get(0);
            Account fromAccount = service.getAccountByCustomerId(2).get(0);
            transaction.setToAccount(toAccount);
            transaction.setFromAccount(fromAccount);
            
            Transaction foundTransaction = service.getTransactionByAccountId(2).get(0);
            assertNotEquals(null, foundTransaction);
        } catch (NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void getAccountByCustomerIdTest() {
        try {
            Account account = service.getAccountByCustomerId(1).get(0);
            assertNotEquals(null, account);
        } catch (NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void updateUserTest() {
        try {
            User user = service.getUserByUsername("sasmith");
            user.setEmail("new email");
            service.updateUser(user, user.getUsername());
            user = service.getUserByUsername("sasmith");
            assertEquals("new email", user.getEmail());
        } catch (NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void updateCustomerTest() {
        try {
            Customer customer = service.getCustomerByUsername("sasmith");
            customer.setPassword("new password");
            service.updateCustomer(customer, customer.getId());
            customer = service.getCustomerByUsername("sasmith");
            assertEquals("new password", customer.getPassword());
        } catch (NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void updateAccountTest() {
        try {
            Account account = service.getAccountByCustomerId(1).get(0);
            account.setBalance(new BigDecimal("10"));
            service.updateAccount(account, account.getId());
            Account foundAccount = service.getAccountByCustomerId(1).get(0);
            assertEquals(foundAccount.getBalance(), new BigDecimal("10"));
        } catch (NoSuchRecordException e) {
            fail();
        }
    }

    @Test
    void deleteUserByUsernameTest() {
        service.deleteUserByUsername("sasmith");
        try {
            service.getUserByUsername("sasmith");
        } catch (NoSuchRecordException ignored) {
        }
    }

    @Test
    void deleteCustomerByIdTest() {
        service.deleteCustomerById(1);
        try {
            service.getCustomerByUsername("sasmith");
        } catch (NoSuchRecordException ignored) {
        };
    }

    @Test
    void deleteEmployeeByIdTest() {
        service.deleteEmployeeById(1);
        try {
            service.getEmployeeByUsername("username");
        } catch (NoSuchRecordException ignored) {
        }
    }

    @Test
    void deleteAccountByIdTest() {
        try {
            Account account = service.getAccountByCustomerId(1).get(0);

            service.deleteAccountById(account.getId());

            Account otherAccount = service.getAccountByCustomerId(1).get(0);
            assertNotEquals(account, otherAccount);
        } catch (NoSuchRecordException e) {
            fail();
        }
    }
}
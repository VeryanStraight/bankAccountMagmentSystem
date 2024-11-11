package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for the service implementation
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@Transactional
class ServiceImplTest {
    //TODO: need to check invalid inputs
    //todo: ensure the mocking is setup correctly (currently seems to be using my actual database)
    @Autowired
    private Service service;

    /**
     * test creating a valid user
     */
    @Test
    void createUserTest() {
        User user = new User("testusername", "testname");
        try {
            User createdUser = service.createUser(user);
            assertNotEquals(createdUser, null);
            User foundUser = service.getUserByUsername("testusername");
            assertEquals(createdUser, foundUser);
        } catch (NoSuchRecordException | AlreadyExistsException | InvalidInputException e) {
            fail("couldn't create user"+e);
        }

    }

    /**
     * test creating a valid customer
     */
    @Test
    void createCustomerTest() {
        User user = new User("testusername", "testname");
        try {
            User createdUser = service.createUser(user);

            Customer customer = new Customer(createdUser, "testpassword", "testaddress");
            customer.setCreatedDate(LocalDateTime.now());

            Customer createdCustomer = service.createCustomer(customer);
            Customer foundCustomer = service.getCustomerByUsername("testusername");

            assertEquals(createdCustomer, foundCustomer);
        } catch (AlreadyExistsException | NoSuchRecordException | InvalidInputException e) {
            fail("couldn't create customer"+e);
        }
    }

    /**
     * test creating a valid employee
     */
    @Test
    void createEmployeeTest() {
        User user = new User("testusername", "testname");
        try {
            User createdUser = service.createUser(user);

            Employee employee = new Employee(createdUser, "testpassword");
            Employee createdEmployee = service.createEmployee(employee);
            Employee foundEmployee = service.getEmployeeByUsername("testusername");

            assertEquals(createdEmployee, foundEmployee);
        } catch (AlreadyExistsException | NoSuchRecordException | InvalidInputException e) {
            fail("couldn't create employee"+e);
        }
    }

    /**
     * test creating a valid employee
     */
    @Test
    void createAccountTest() {
        try {
            Customer customer = service.getCustomerByUsername("sasmith");
            Account account = new Account(customer);
            Account createdAccount = service.createAccount(account);

            List<Account> foundAccounts = service.getAccountByCustomerId(customer.getId());

            assertTrue(foundAccounts.contains(createdAccount));
        } catch (NoSuchRecordException | AlreadyExistsException | InvalidInputException e) {
            fail("couldn't create account"+e);
        }

    }

    /**
     * test creating a valid transaction
     */
    @Test
    void createTransactionTest() {
        //TODO create transactions for all types
        List<TransactionType> transactionTypes = service.getTransactionTypes();
        TransactionType type = transactionTypes.get(1);
        Transaction transaction = new Transaction(type, new BigDecimal("10"));
        try {
            Account toAccount = service.getAccountByCustomerId(1).get(0);
            Account fromAccount = service.getAccountByCustomerId(3).get(0);
            System.out.println(toAccount);
            System.out.println(fromAccount);
            transaction.setToAccount(toAccount);
            transaction.setFromAccount(fromAccount);
            BigDecimal toAmount = toAccount.getBalance();
            BigDecimal fromAmount = fromAccount.getBalance();

            Transaction createdTransaction = service.createTransaction(transaction);
            Transaction foundTransaction = service.getTransactionsByAccountId(fromAccount.getId()).get(2);
            assertEquals(createdTransaction, foundTransaction);

            Account toAccountAfter = service.getAccountByCustomerId(1).get(0);
            Account fromAccountAfter = service.getAccountByCustomerId(3).get(0);

            BigDecimal ten = new BigDecimal("10");


            assertEquals(toAccountAfter.getBalance().subtract(ten), toAmount);
            assertEquals(fromAccountAfter.getBalance().add(ten), fromAmount);
        } catch (NoSuchRecordException | AlreadyExistsException | InvalidInputException e) {
            fail("couldn't create transaction"+e);
        }


    }

    /**
     * test getting a valid user
     */
    @Test
    void getUserByUsernameTest() {
        try {
            User user = service.getUserByUsername("sasmith");
            assertNotEquals(null, user);
        } catch (NoSuchRecordException e) {
            fail("couldn't get user"+e);
        }
        
    }

    /**
     * test getting a valid customer
     */
    @Test
    void getCustomerByUsernameTest() {
        try {
            Customer customer = service.getCustomerByUsername("sasmith");
            assertNotEquals(null, customer);

        } catch (NoSuchRecordException e) {
            fail("couldn't get customer"+e);
        }
    }

    /**
     * test getting a valid transaction
     */
    @Test
    void getTransactionByAccountIdTest() {
        try {
            Transaction foundTransaction = service.getTransactionsByAccountId(1).get(0);
            assertNotEquals(null, foundTransaction);
        } catch (NoSuchRecordException e) {
            fail("couldn't get transaction"+e);
        }
    }

    /**
     * test getting a valid account
     */
    @Test
    void getAccountByCustomerIdTest() {
        try {
            Account account = service.getAccountByCustomerId(1).get(0);
            assertNotEquals(null, account);
        } catch (NoSuchRecordException e) {
            fail("couldn't get account"+e);
        }
    }

    /**
     * test updating a valid user
     */
    @Test
    void updateUserTest() {
        try {
            User user = service.getUserByUsername("sasmith");
            User newuser = new User(user.getUsername(), user.getName());
            newuser.setEmail("new email");
            service.updateUser(newuser);
            user = service.getUserByUsername("sasmith");
            assertEquals("new email", user.getEmail());
        } catch (NoSuchRecordException e) {
            fail("couldn't update"+e);
        }
    }

    /**
     * test updating a valid customer
     */
    @Test
    void updateCustomerTest() {
        try {
            Customer customer = service.getCustomerByUsername("sasmith");
            Customer newCustomer = new Customer(customer.getUser(), customer.getPassword(), customer.getAddress());
            newCustomer.setPassword("new password");
            newCustomer.setId(customer.getId());
            newCustomer.setCreatedDate(customer.getCreatedDate());
            service.updateCustomer(newCustomer);
            customer = service.getCustomerByUsername("sasmith");
            assertEquals("new password", customer.getPassword());
        } catch (NoSuchRecordException e) {
            fail("couldn't update customer"+e);
        }
    }

    /**
     * test updating a valid account
     */
    @Test
    void updateAccountTest() {
        try {
            Account account = service.getAccountByCustomerId(1).get(0);
            Account newAccount = new Account(account.getCustomer());
            newAccount.setBalance(new BigDecimal("10"));
            newAccount.setId(account.getId());
            newAccount.setStart(account.getStart());
            newAccount.setStatus(account.getStatus());
            service.updateAccount(newAccount);
            Account foundAccount = service.getAccountByCustomerId(1).get(0);
            assertEquals(foundAccount.getBalance(), new BigDecimal("10"));
        } catch (NoSuchRecordException e) {
            fail("couldn't update account"+e);
        }
    }

    /**
     * test deleting a valid user
     */
    @Test
    void deleteUserByUsernameTest() {
        try {
            service.deleteUserByUsername("sasmith");
            service.getUserByUsername("sasmith");
            fail("found user after delete");
        } catch (NoSuchRecordException ignored) {
        }
    }

    /**
     * test deleting a valid customer
     */
    @Test
    void deleteCustomerByIdTest() {
        try {
            service.deleteCustomerById(2);
            service.getCustomerByUsername("tojhonson");
            fail("found customer after delete");
        } catch (NoSuchRecordException ignored) {
        }
    }

    /**
     * test deleting a valid employee
     */
    @Test
    void deleteEmployeeByIdTest() {
        try {
            Employee e = service.getEmployeeByUsername("username");
            service.deleteEmployeeById(e.getId());

            service.getEmployeeByUsername("username");
            fail("found employee after delete");
        } catch (NoSuchRecordException ignored) {
        }
    }

    /**
     * test deleting a valid account
     */
    @Test
    void deleteAccountByIdTest() {
        try {
            Account account = service.getAccountByCustomerId(1).get(0);

            service.deleteAccountById(account.getId());

            Account otherAccount = service.getAccountByCustomerId(1).get(0);
            assertNotEquals(account, otherAccount);
        } catch (NoSuchRecordException e) {
            fail("couldn't find account");
        }
    }
}
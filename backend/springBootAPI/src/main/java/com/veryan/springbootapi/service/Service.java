package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;

import java.util.List;

public interface Service {
    //todo: change some of the InvalidInputException to NoSuchRecord
    /**
     * creates a new user
     *
     * @param user the user to create
     * @return the created user, or null if it fails
     */
    User createUser(User user) throws AlreadyExistsException;

    /**
     * creates a customer. there must be an existing user for the customer
     * @param customer the customer to create
     * @return the created customer, or null if it fails
     */
    Customer createCustomer(Customer customer) throws AlreadyExistsException, InvalidInputException;

    /**
     * creates an employee. there must be an existing user for the customer
     * @param employee the employee to create
     * @return the created employee, or null if it fails
     */
    Employee createEmployee(Employee employee) throws AlreadyExistsException, InvalidInputException;

    /**
     * create an account for a customer
     * @param account the account to create
     * @return the created account, or null if it fails
     */
    Account createAccount(Account account) throws AlreadyExistsException, InvalidInputException;

    /**
     * creates and does a transaction
     * @param transaction the transaction to do
     * @return the completed transaction
     */
    Transaction createTransaction(Transaction transaction) throws AlreadyExistsException, InvalidInputException, NoSuchRecordException;
    User getUserByUsername(String username) throws NoSuchRecordException;
    Customer getCustomerByUsername(String username) throws NoSuchRecordException;
    Employee getEmployeeByUsername(String username) throws NoSuchRecordException;
    List<Transaction> getTransactionsByAccountId(int id) throws NoSuchRecordException;
    List<Account> getAccountByCustomerId(int id) throws NoSuchRecordException;
    Account getAccountById(int id) throws NoSuchRecordException;
    List<TransactionType> getTransactionTypes();
    User updateUser(User user) throws NoSuchRecordException;
    Customer updateCustomer(Customer customer) throws NoSuchRecordException;
    Account updateAccount(Account account) throws NoSuchRecordException;
    void deleteUserByUsername(String username) throws NoSuchRecordException;
    void deleteCustomerById(int id) throws NoSuchRecordException;
    void deleteEmployeeById(int id) throws NoSuchRecordException;
    void deleteAccountById(int id) throws NoSuchRecordException;

}

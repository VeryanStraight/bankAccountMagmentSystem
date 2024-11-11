package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;

import java.util.List;

/**
 * the service interface
 */
public interface Service {
    //todo: change some of the InvalidInputException to NoSuchRecord
    /**
     * creates a new user
     * @param user the user to create
     * @return the created user, or null if it fails
     * @throws AlreadyExistsException if a user with that username already exists
     */
    User createUser(User user) throws AlreadyExistsException, InvalidInputException;

    /**
     * creates a customer. there must be an existing user for the customer
     * @param customer the customer to create (the id can be null)
     * @return the created customer, or null if it fails
     * @throws AlreadyExistsException if a customer with that id already exists
     * @throws InvalidInputException if the user doesn't exist
     */
    Customer createCustomer(Customer customer) throws AlreadyExistsException, InvalidInputException;

    /**
     * creates an employee. there must be an existing user for the customer
     * @param employee the employee to create (the id can be null)
     * @return the created employee, or null if it fails
     * @throws AlreadyExistsException if an employee with that id already exists
     * @throws InvalidInputException if the user doesn't exist
     */
    Employee createEmployee(Employee employee) throws AlreadyExistsException, InvalidInputException;

    /**
     * create an account for a customer
     * @param account the account to create (the id can be null)
     * @return the created account, or null if it fails
     * @throws AlreadyExistsException if the account id already exits
     * @throws InvalidInputException if the customer doesn't exist
     */
    Account createAccount(Account account) throws AlreadyExistsException, InvalidInputException;

    /**
     * creates and does a transaction
     * @param transaction the transaction to do (don't need to include an id)
     * @return the completed transaction
     * @throws AlreadyExistsException if the transaction already exists
     * @throws InvalidInputException if the accounts are missing or the transaction type doesn't exist
     * @throws NoSuchRecordException if the accounts don't exist
     */
    Transaction createTransaction(Transaction transaction) throws AlreadyExistsException, InvalidInputException, NoSuchRecordException;

    /**
     * gets a user by username
     * @param username the username
     * @return the user
     * @throws NoSuchRecordException if the user doesn't exist
     */
    User getUserByUsername(String username) throws NoSuchRecordException;
    /**
     * gets a customer by username
     * @param username the username
     * @return the customer
     * @throws NoSuchRecordException if the customer doesn't exist
     */
    Customer getCustomerByUsername(String username) throws NoSuchRecordException;
    /**
     * gets an employee by username
     * @param username the username
     * @return the employee
     * @throws NoSuchRecordException if the employee doesn't exist
     */
    Employee getEmployeeByUsername(String username) throws NoSuchRecordException;
    /**
     * gets the transactions of an account
     * @param id the account id
     * @return the list of transactions
     * @throws NoSuchRecordException if the account doesn't exist
     */
    List<Transaction> getTransactionsByAccountId(int id) throws NoSuchRecordException;

    /**
     * get a list of account for a customer
     * @param id the customer id
     * @return the list of accounts
     * @throws NoSuchRecordException if the customer doesn't exist
     */
    List<Account> getAccountByCustomerId(int id) throws NoSuchRecordException;

    /**
     * gets an account by id
     * @param id the account id
     * @return the account
     * @throws NoSuchRecordException if the account doesn't exist
     */
    Account getAccountById(int id) throws NoSuchRecordException;

    /**
     * gets the transaction types
     * @return the transaction types
     */
    List<TransactionType> getTransactionTypes();


    /**
     * gets the statuses
     * @return the statuses
     */
    List<Status> getStatuses();

    /**
     * updates a user
     * @param user the new information to add to the user
     * @return the updated user
     * @throws NoSuchRecordException if the user doesn't exist
     */
    User updateUser(User user) throws NoSuchRecordException;

    /**
     * updates a customer
     * @param customer the new information to add to the customer
     * @return the updated customer
     * @throws NoSuchRecordException if the customer doesn't exist
     */
    Customer updateCustomer(Customer customer) throws NoSuchRecordException;

    /**
     * updates a account
     * @param account the new information to add to the account
     * @return the updated account
     * @throws NoSuchRecordException if the account doesn't exist
     */
    Account updateAccount(Account account) throws NoSuchRecordException;

    /**
     * deletes a user and the corresponding customer or employee.
     * @param username the user's username
     * @throws NoSuchRecordException if the user doesn't exist
     */
    void deleteUserByUsername(String username) throws NoSuchRecordException;

    /**
     * deletes a customer
     * @param id the customer id
     * @throws NoSuchRecordException if the id doesn't exist
     */
    void deleteCustomerById(int id) throws NoSuchRecordException;

    /**
     * deletes a employee
     * @param id the employee id
     * @throws NoSuchRecordException if the employee doesn't exist
     */
    void deleteEmployeeById(int id) throws NoSuchRecordException;

    /**
     * deletes an account
     * @param id the account id
     * @throws NoSuchRecordException if the account doesn't exist
     */
    void deleteAccountById(int id) throws NoSuchRecordException;

}

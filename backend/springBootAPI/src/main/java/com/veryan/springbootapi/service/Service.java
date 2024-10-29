package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;

import java.util.List;

public interface Service {
    User createUser(User user);
    Customer createCustomer(Customer customer);
    Employee createEmployee(Employee employee);
    Account createAccount(Account account);
    Transaction createTransaction(Transaction transaction);
    User getUserByUsername(String username);
    Customer getCustomerByUsername(String username);
    List<Transaction> getTransactionByAccountId(int id);
    List<Account> getAccountByCustomerId(int id);
    void updateUser(User user, int id);
    void updateCustomer(Customer customer, int id);
    void updateAccount(Account account, int id);
    void deleteUserByUsername(String username);
    void deleteCustomerById(int id);
    void deleteEmployeeById(int id);
    void deleteAccountById(int id);
}

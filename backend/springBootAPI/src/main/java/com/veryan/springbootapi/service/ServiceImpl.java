package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.reposities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl implements com.veryan.springbootapi.service.Service {
    AccountRepository accounts;
    CustomerRepository customers;
    EmployeeRepository employees;
    StatusRepository statuses;
    TransactionRepository transactions;
    TransactionTypeRepository transactionTypes;
    UserRepository users;

    @Autowired
    public ServiceImpl(AccountRepository accounts, CustomerRepository customers, EmployeeRepository employees,
                          StatusRepository statuses, TransactionRepository transactions,
                          TransactionTypeRepository transactionTypes, UserRepository users) {
        this.accounts = accounts;
        this.customers = customers;
        this.employees = employees;
        this.statuses = statuses;
        this.transactions = transactions;
        this.transactionTypes = transactionTypes;
        this.users = users;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return null;
    }

    @Override
    public Account createAccount(Account account) {
        return null;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return null;
    }

    @Override
    public List<Transaction> getTransactionByAccountId(int id) {
        return null;
    }

    @Override
    public List<Account> getAccountByCustomerId(int id) {
        return null;
    }

    @Override
    public void updateUser(User user, int id) {

    }

    @Override
    public void updateCustomer(Customer customer, int id) {

    }

    @Override
    public void updateAccount(Account account, int id) {

    }

    @Override
    public void deleteUserByUsername(String username) {

    }

    @Override
    public void deleteCustomerById(int id) {

    }

    @Override
    public void deleteEmployeeById(int id) {

    }

    @Override
    public void deleteAccountById(int id) {

    }
}

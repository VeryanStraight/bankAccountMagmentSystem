package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.Transaction;
import com.veryan.springbootapi.reposities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accountSystem")
public class MainController {
    AccountRepository accounts;
    CustomerRepository customers;
    EmployeeRepository employees;
    StatusRepository statuses;
    TransactionRepository transactions;
    TransactionTypeRepository transactionTypes;
    UserRepository users;

    @Autowired
    public MainController(AccountRepository accounts, CustomerRepository customers, EmployeeRepository employees,
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

    @GetMapping("/")
    public List<Transaction> index() {
        return transactions.findAll();
    }
}

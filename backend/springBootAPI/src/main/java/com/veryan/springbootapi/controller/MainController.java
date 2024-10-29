package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.Transaction;
import com.veryan.springbootapi.reposities.*;
import com.veryan.springbootapi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accountSystem")
public class MainController {
    ServiceImpl service;

    @Autowired
    public MainController(ServiceImpl service) {
        this.service = service;
    }

    @GetMapping("transactions/")
    public List<Transaction> getTransactions() {
        return null;
    }

    @GetMapping("transactions/{customer-id}")
    public List<Transaction> getTransactionsByAccount(@PathVariable int id) {
        return null;
    }
}

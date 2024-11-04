package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.reposities.*;
import com.veryan.springbootapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountSystem")
@CrossOrigin(origins = "http://localhost:5174")
public class MainController {
    Service service;

    @Autowired
    public MainController(Service service) {
        this.service = service;
    }


    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User createdUser = service.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        try{
            Customer createdCustomer = service.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (AlreadyExistsException | InvalidInputException e) {
            //if a duplicate key or non-existent foreign key
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createCustomer(@RequestBody Employee employee){
        try{
            Employee createdEmployee = service.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (AlreadyExistsException | InvalidInputException e) {
            //if a duplicate key or non-existent foreign key
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        try {
            Account createdAccount = service.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (AlreadyExistsException | InvalidInputException e) {
            //if a duplicate key or non-existent foreign key
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        try {
            Transaction createdTransaction = service.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (AlreadyExistsException | InvalidInputException | NoSuchRecordException e) {
            //if a duplicate key or non-existent foreign key
            //if missing to/from account when they are needed based on transaction type
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getUserByUsername(username));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{username}/customer")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getCustomerByUsername(username));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{username}/employee")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getEmployeeByUsername(username));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/account/{id}/transaction")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getTransactionsByAccountId(id));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer{id}/account")
    public ResponseEntity<List<Account>> getAccountByCustomerId(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAccountByCustomerId(id));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/transactiontypes")
    public ResponseEntity<List<TransactionType>> getTransactionTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransactionTypes());
    }

    @PatchMapping("/user/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user){
        try {
            user.setUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(user));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        try {
            customer.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.updateCustomer(customer));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/account/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account account){
        try {
            account.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(account));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username){
        try{
            service.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable int id){
        try{
            service.deleteCustomerById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id){
        try{
            service.deleteEmployeeById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable int id){
        try{
            service.deleteAccountById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}

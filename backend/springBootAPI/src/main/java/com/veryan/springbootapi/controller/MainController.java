package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.reposities.*;
import com.veryan.springbootapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountSystem")
@EnableMethodSecurity()
public class MainController {
    Service service;

    @Autowired
    public MainController(Service service) {
        this.service = service;
    }

    /**
     * an endpoint for creating a new user
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate username
     * a user must have a username (PK) and a name
     * a user can have a phone and email
     * @param user the new user
     * @return the completed user
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User createdUser = service.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * an endpoint for creating a new customer
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate key or missing user
     * a customer must have a user, password and address
     * @param customer the new customer (if no id it is generated)
     * @return the completed user
     */
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

    /**
     * an endpoint for creating a new employee
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate key or missing user
     * an employee must have a user and password
     * @param employee the new employee (if no id it is generated)
     * @return the completed employee
     */
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

    /**
     * an endpoint for creating a new account
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate key or missing customer
     * @param account the new account (if no id it is generated)
     * @return the completed account
     */
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

    /**
     * an endpoint for creating a new transaction
     * returns CREATED status if successful
     * return CONFLICT if it failed due to duplicate a key or missing account or incorrect transaction type
     * a transaction must have
     * - an amount
     * - a transaction type 1(Deposit), 2(Withdrawal), 3(Transfer)
     * - a from account (deposits go into the from account)
     * - a to account (if doing Transfer)
     * @param transaction the new transaction (if no id it is generated)
     * @return the completed transaction
     */
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

    @PreAuthorize("#username == authentication.principal.username or hasRole('EMPLOYEE')")
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
    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username){
        try{
            service.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable int id){
        try{
            service.deleteCustomerById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id){
        try{
            service.deleteEmployeeById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
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

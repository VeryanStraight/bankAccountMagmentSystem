package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PutMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            System.out.println("in create user");
            User createdUser = service.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        try{
            System.out.println(customer);
            Customer createdCustomer = service.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (AlreadyExistsException | InvalidInputException e) {
            System.out.println(e.getMessage());
            //if a duplicate key or non-existent foreign key
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/employee")
    public ResponseEntity<Employee> createCustomer(@RequestBody Employee employee){
        try{
            Employee createdEmployee = service.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (AlreadyExistsException | InvalidInputException e) {
            //if a duplicate key or non-existent foreign key
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        try {
            Account createdAccount = service.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (AlreadyExistsException | InvalidInputException e) {
            //if a duplicate key or non-existent foreign key
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        try {
            System.out.println("in create transaction");
            System.out.println(transaction.getFromAccount());
            Transaction createdTransaction = service.createTransaction(transaction);
            System.out.println(createdTransaction);
            ResponseEntity<Transaction> r = ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
            System.out.println(r);
            return r;
        } catch (AlreadyExistsException | InvalidInputException | NoSuchRecordException e) {
            System.out.println("in catch");
            System.out.println(e.getMessage());
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

    @GetMapping("/customer/{id}/account")
    public ResponseEntity<List<Account>> getAccountByCustomerId(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAccountByCustomerId(id));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAccountById(id));
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
            User oldUser = service.getUserByUsername(username);

            if(user.getName() != null){oldUser.setName(user.getName());}
            if(user.getEmail() != null){oldUser.setEmail(user.getEmail());}
            if(user.getPhone() != null){oldUser.setPhone(user.getPhone());}

            return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(oldUser));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        try {
            //todo let you change the user?
            Customer oldCustomer = service.getCustomerByUsername(customer.getUser().getUsername());

            if(customer.getAddress() != null){oldCustomer.setAddress(customer.getAddress());}
            if(customer.getPassword() != null){oldCustomer.setPassword(customer.getPassword());}

            return ResponseEntity.status(HttpStatus.OK).body(service.updateCustomer(oldCustomer));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/account/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account account){
        try {
            Account oldAccount = service.getAccountById(id);

            if(account.getBalance() != null){oldAccount.setBalance(account.getBalance());}
            if(account.getCustomer() != null){oldAccount.setCustomer(account.getCustomer());}
            if(account.getName() != null){oldAccount.setName(account.getName());}
            if(account.getStatus() != null){oldAccount.setStatus(account.getStatus());}
            if(account.getBeneficiaries() != null){oldAccount.setBeneficiaries(account.getBeneficiaries());}

            return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(oldAccount));
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

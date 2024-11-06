package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * the controller
 */
@RestController
@RequestMapping("/api/accountSystem")
@EnableMethodSecurity()
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {
    private final Service service;

    /**
     * the constructor
     * @param service the service layer
     */
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


    /**
     * an endpoint for creating a new customer
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate key or missing user
     * a customer must have a user, password and address
     * @param customer the new customer (if no id it is generated)
     * @return the completed user
     */
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

    /**
     * an endpoint for creating a new employee
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate key or missing user
     * an employee must have a user and password
     * @param employee the new employee (if no id it is generated)
     * @return the completed employee
     */
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

    /**
     * an endpoint for creating a new account
     * returns CREATED status if successful and CONFLICT if it failed due to duplicate key or missing customer
     * @param account the new account (if no id it is generated)
     * @return the completed account
     */
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

    /**
     * an end point for getting a user by username
     * @param username the username
     * @return the user if it exists
     */
    @PreAuthorize("#username == authentication.principal.username or hasRole('EMPLOYEE')")
    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getUserByUsername(username));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * an end point for getting a customer by username
     * @param username the username
     * @return the customer if it exists
     */
    @GetMapping("/user/{username}/customer")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getCustomerByUsername(username));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * an end point for getting an employee by username
     * @param username the username
     * @return the employee if it exists
     */
    @GetMapping("/user/{username}/employee")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getEmployeeByUsername(username));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * an end point for getting the transactions of an account
     * @param id the account id
     * @return the transactions
     */
    @GetMapping("/account/{id}/transaction")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getTransactionsByAccountId(id));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * an end point for getting the accounts of a customer
     * @param id the customer id
     * @return the accounts
     */
    @GetMapping("/customer/{id}/account")
    public ResponseEntity<List<Account>> getAccountByCustomerId(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAccountByCustomerId(id));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * an end point for getting an account
     * @param id the account id
     * @return the account
     */
    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAccountById(id));
        } catch (NoSuchRecordException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * an end point for getting the transaction types
     * @return the transaction types
     */
    @GetMapping("/transactiontypes")
    public ResponseEntity<List<TransactionType>> getTransactionTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransactionTypes());
    }

    /**
     * an end point to update a user
     * @param username the user to update
     * @param user the data to replace
     * @return the updated user
     */
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

    /**
     * an end point to update a customer
     * @param id the customer to update
     * @param customer the data to replace
     * @return the updated customer
     */
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

    /**
     * an end point to update an account
     * @param id the account to update
     * @param account the data to replace
     * @return the updated account
     */
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

    /**
     * an end point to delete a user. it will also delete the associated customer or employee
     * @param username the user
     * @return the response code
     */
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

    /**
     * an end point to delete a customer.
     * @param id the customer
     * @return the response code
     */
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

    /**
     * an end point to delete an employee.
     * @param id the employee
     * @return the response code
     */
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

    /**
     * an end point to delete an account.
     * @param id the account
     * @return the response code
     */
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

package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.reposities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    public User createUser(User user) throws AlreadyExistsException{
        Optional<User> u = users.findById(user.getUsername());
        if(u.isPresent()){throw new AlreadyExistsException(user.toString());}

        return users.save(user);

    }

    @Override
    public Customer createCustomer(Customer customer) throws AlreadyExistsException, NoSuchRecordException {
        //todo is there a more efficient way than using 3 query's
        Optional<Customer> c = customers.findById(customer.getId());
        if(c.isPresent()){throw new AlreadyExistsException(customer.toString());}

        User user = customer.getUser();
        Optional<User> u = users.findById(user.getUsername());
        if(u.isEmpty()){throw new NoSuchRecordException("no such user: "+ user);}

        return customers.save(customer);
    }

    @Override
    public Employee createEmployee(Employee employee) throws AlreadyExistsException, NoSuchRecordException {
        //todo is there a more efficient way than using 3 query's
        Optional<Employee> e = employees.findById(employee.getId());
        if(e.isPresent()){throw new AlreadyExistsException(employee.toString());}

        User user = employee.getUser();
        Optional<User> u = users.findById(user.getUsername());
        if(u.isEmpty()){throw new NoSuchRecordException("no such user: "+ user);}

        return employees.save(employee);
    }

    @Override
    public Account createAccount(Account account) throws AlreadyExistsException, NoSuchRecordException {
        //todo what if nonexistent customer
        Optional<Account> a = accounts.findById(account.getId());
        if(a.isPresent()){throw new AlreadyExistsException(account.toString());}

        Customer customer = account.getCustomer();
        Optional<Customer> c = customers.findById(customer.getId());
        if(c.isEmpty()){throw new NoSuchRecordException("no such user: "+ customer);}

        Status status = account.getStatus();
        Optional<Status> s = statuses.findById(status.getId());
        if(c.isEmpty()){throw new NoSuchRecordException("no such user: "+ customer);}

        return accounts.save(account);
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) throws AlreadyExistsException, InvalidInputException, NoSuchRecordException {

        BigDecimal amount = transaction.getAmount();
        String type = transaction.getType().getType();
        //ToDO somehow avoid this hard coding?
        switch (type){
            case "Deposit":
                Account depositAccount = transaction.getToAccount();
                depositAccount.addAmount(amount);
                try {
                    accounts.save(depositAccount);
                }catch (DataIntegrityViolationException e){
                    throw new NoSuchRecordException("invalid toAccount: "+ depositAccount);
                }
                break;
            case "Withdrawal":
                Account withdrawalAccount = transaction.getFromAccount();
                withdrawalAccount.addAmount(amount);
                try {
                    accounts.save(withdrawalAccount);
                }catch (DataIntegrityViolationException e){
                    throw new NoSuchRecordException("invalid fromAccount: "+ withdrawalAccount);
                }
                break;
            case "Transfer":
                Account toAccount = transaction.getToAccount();
                Account fromAccount = transaction.getFromAccount();
                toAccount.addAmount(amount);
                fromAccount.subtractAmount(amount);
                try {
                    //todo check this isn't just creating new accounts?
                    accounts.save(toAccount);
                    accounts.save(fromAccount);
                }catch (DataIntegrityViolationException e){
                    throw new NoSuchRecordException("invalid toAccount or fromAccount");
                }
                break;
            default:
                throw new InvalidInputException("unknown transaction type");
        }

        try {
            return transactions.save(transaction);
        }catch (DataIntegrityViolationException e){
            throw new AlreadyExistsException("could not save: "+ transaction);
        }
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
    public Employee getEmployeeByUsername(String username) {
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
    public void updateUser(User user, String username) {
        //TODO: add @DynamicUpdate to the User entity?
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

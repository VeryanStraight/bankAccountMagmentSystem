package com.veryan.springbootapi.service;

import com.veryan.springbootapi.entities.*;
import com.veryan.springbootapi.reposities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//todo change wording of from/to accounts
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
    public Customer createCustomer(Customer customer) throws AlreadyExistsException, InvalidInputException {
        Optional<Customer> c = customers.findById(customer.getId());
        if(c.isPresent()){throw new AlreadyExistsException(customer.toString());}

        if(customer.getCreated_date() == null){
            customer.setCreated_date(LocalDateTime.now());
        }

        try {
            return customers.save(customer);
        } catch (DataIntegrityViolationException e){
            throw new InvalidInputException("failed to add customer", e);
        }
    }

    @Override
    public Employee createEmployee(Employee employee) throws AlreadyExistsException, InvalidInputException {
        Optional<Employee> emp = employees.findById(employee.getId());
        if(emp.isPresent()){throw new AlreadyExistsException(employee.toString());}

        try {
            return employees.save(employee);
        } catch (DataIntegrityViolationException e){
            throw new InvalidInputException("failed to add employee", e);
        }
    }

    @Override
    public Account createAccount(Account account) throws AlreadyExistsException, InvalidInputException {
        Optional<Account> a = accounts.findById(account.getId());
        if(a.isPresent()){throw new AlreadyExistsException(account.toString());}

        //todo: avoid hard coding
        account.setBalance(BigDecimal.ZERO);
        account.setStart(LocalDateTime.now());
        account.setStatus(statuses.findById(1).get());

        try {
            return accounts.save(account);
        } catch (DataIntegrityViolationException e){
            throw new InvalidInputException("failed to add account", e);
        }
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) throws AlreadyExistsException, InvalidInputException, NoSuchRecordException {

        BigDecimal amount = transaction.getAmount();
        String type = transaction.getType().getType();
        Account toAccount = transaction.getToAccount();
        Account fromAccount = transaction.getFromAccount();
        if(toAccount != null){
            Optional<Account> a = accounts.findById(toAccount.getId());
            if(a.isEmpty()){throw new InvalidInputException("invalid toAccount");}
        }
        if(fromAccount != null){
            Optional<Account> a = accounts.findById(fromAccount.getId());
            if(a.isEmpty()){throw new InvalidInputException("invalid fromAccount");}
        }

        //ToDO somehow avoid this hard coding?
        switch (type){
            case "Deposit":
                if (fromAccount == null){
                    throw new InvalidInputException("must have from account for a deposit");
                }
                fromAccount.addAmount(amount);
                try {
                    accounts.save(fromAccount);
                }catch (DataIntegrityViolationException e){
                    throw new NoSuchRecordException("invalid toAccount: "+ toAccount, e);
                }
                break;
            case "Withdrawal":
                if (fromAccount == null){
                    throw new InvalidInputException("must have from account for a withdrawal");
                }
                fromAccount.addAmount(amount);
                try {
                    accounts.save(fromAccount);
                }catch (DataIntegrityViolationException e){
                    throw new NoSuchRecordException("invalid fromAccount: "+ fromAccount, e);
                }
                break;
            case "Transfer":
                if (toAccount == null || fromAccount==null){
                    throw new InvalidInputException("must have to and from account for a transfer");
                }
                toAccount.addAmount(amount);
                fromAccount.subtractAmount(amount);
                try {
                    //todo check this isn't just creating new accounts?
                    accounts.save(toAccount);
                    accounts.save(fromAccount);
                }catch (DataIntegrityViolationException e){
                    throw new NoSuchRecordException("invalid toAccount or fromAccount", e);
                }
                break;
            default:
                throw new InvalidInputException("unknown transaction type");
        }

        try {
            if(transaction.getDatetime() == null) {
                transaction.setDatetime(LocalDate.now());
            }
            return transactions.save(transaction);
        }catch (DataIntegrityViolationException e){
            throw new AlreadyExistsException("could not save: "+ transaction);
        }
    }

    @Override
    public User getUserByUsername(String username) throws NoSuchRecordException {
        Optional<User> user = users.findById(username);
        if(user.isEmpty()){throw new NoSuchRecordException("no user found");}
        return user.get();
    }

    @Override
    public Customer getCustomerByUsername(String username) throws NoSuchRecordException {
        Optional<Customer> customer = customers.findByUser_Username(username);
        if(customer.isEmpty()){throw new NoSuchRecordException("no such customer"+ username);}
        return customer.get();
    }

    @Override
    public Employee getEmployeeByUsername(String username) throws NoSuchRecordException {
        Optional<Employee> employee = employees.findByUser_Username(username);
        if(employee.isEmpty()){throw new NoSuchRecordException("no such customer"+ username);}
        return employee.get();
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int id) throws NoSuchRecordException {
        if(accounts.findById(id).isEmpty()){throw new NoSuchRecordException("no account: "+id);}

        return transactions.getTransactionByFromAccount_Id(id);
    }

    @Override
    public List<Account> getAccountByCustomerId(int id) throws NoSuchRecordException {
        if(customers.findById(id).isEmpty()){throw new NoSuchRecordException("no customer: "+id);}

        return accounts.getAccountByCustomer_Id(id);
    }

    public List<TransactionType> getTransactionTypes(){
        return transactionTypes.findAll();
    }
    @Override
    public User updateUser(User user) throws NoSuchRecordException {
        String username = user.getUsername();
        if(users.findById(username).isEmpty()){throw new NoSuchRecordException(username);}

        return users.save(user);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws NoSuchRecordException {
        int id = customer.getId();
        if(customers.findById(id).isEmpty()){throw new NoSuchRecordException(id+"");}

        return customers.save(customer);
    }

    @Override
    public Account updateAccount(Account account) throws NoSuchRecordException {
        int id = account.getId();
        if(accounts.findById(id).isEmpty()){throw new NoSuchRecordException(id+"");}

        return accounts.save(account);
    }

    @Override
    public void deleteUserByUsername(String username) throws NoSuchRecordException {
        //todo: have to delete any customers and employees first
        if(users.findById(username).isEmpty()){throw new NoSuchRecordException(username);}

        customers.deleteByUser_Username(username);
        employees.deleteByUser_Username(username);
        users.deleteById(username);
    }

    @Override
    public void deleteCustomerById(int id) throws NoSuchRecordException {
        if(customers.findById(id).isEmpty()){throw new NoSuchRecordException(id+"");}
        customers.deleteById(id);
    }

    @Override
    public void deleteEmployeeById(int id) throws NoSuchRecordException {
        if(employees.findById(id).isEmpty()){throw new NoSuchRecordException(id+"");}
        customers.deleteById(id);
    }

    @Override
    public void deleteAccountById(int id) throws NoSuchRecordException {
        if(accounts.findById(id).isEmpty()){throw new NoSuchRecordException(id+"");}
        accounts.deleteById(id);
    }
}

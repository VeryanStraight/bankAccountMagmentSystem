package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.Account;
import com.veryan.springbootapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> getAccountByCustomer(Customer customer);
}

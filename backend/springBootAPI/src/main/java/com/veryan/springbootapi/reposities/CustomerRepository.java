package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.Customer;
import com.veryan.springbootapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUser_Username(String user);
    void deleteByUser_Username(String user);
}

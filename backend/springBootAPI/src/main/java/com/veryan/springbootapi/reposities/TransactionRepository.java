package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> getTransactionByFromAccount_Id(int id);
}

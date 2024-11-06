package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * the jpa transaction type repository
 */
@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
}

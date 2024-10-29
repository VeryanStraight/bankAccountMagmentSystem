package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "transaction_type")
@Data
public class TransactionType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private String type;
}

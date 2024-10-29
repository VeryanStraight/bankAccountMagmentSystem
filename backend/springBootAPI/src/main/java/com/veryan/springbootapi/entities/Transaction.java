package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private Account to_account;

    @ManyToOne
    @JoinColumn(name = "from_account")
    private Account from_account;

    @ManyToOne
    @JoinColumn(name = "type")
    private TransactionType type;

    @Column
    private BigDecimal amount;

    @Column
    private String description;

    @Column
    private LocalDate datetime;

}

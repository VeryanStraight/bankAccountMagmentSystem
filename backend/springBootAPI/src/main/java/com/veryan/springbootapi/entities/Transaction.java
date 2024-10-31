package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "transactions")
@Data
@EqualsAndHashCode
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private Account toAccount;

    @ManyToOne
    @JoinColumn(name = "from_account")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "type")
    private TransactionType type;

    @Column
    private BigDecimal amount;

    @Column
    private String description;

    @Column
    private LocalDate datetime;

    public Transaction(TransactionType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public Transaction() {

    }
}

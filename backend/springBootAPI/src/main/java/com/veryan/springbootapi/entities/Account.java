package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "accounts")
@Data
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column
    private String name;

    @Column
    private LocalDateTime start;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "beneficiaries",
            joinColumns = {@JoinColumn(name = "account")},
            inverseJoinColumns = {@JoinColumn(name = "customer")}
    )
    private List<Customer> beneficiaries;

    public Account(Customer customer) {
        this.customer = customer;
    }

    public Account() {}

    public void addAmount(BigDecimal amount){
        balance = balance.add(amount);
    }

    public void subtractAmount(BigDecimal amount){
        balance = balance.subtract(amount);
    }

}

package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    @Column
    private String password;
}

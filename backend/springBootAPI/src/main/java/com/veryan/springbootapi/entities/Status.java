package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "statuses")
public class Status {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private String status;
}

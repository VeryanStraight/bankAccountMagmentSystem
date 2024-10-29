package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "statuses")
public class Status {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Column
    String status;
}

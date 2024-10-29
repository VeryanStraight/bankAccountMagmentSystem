package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "categories")
@Data
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Column
    String category;
}

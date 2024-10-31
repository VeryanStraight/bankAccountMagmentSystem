package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "transaction_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TransactionType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private String type;


}

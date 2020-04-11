package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int amount;

    @Column
    private Long idProduct;

    @Column
    private Long idUser;

    @Column
    private String type;
}

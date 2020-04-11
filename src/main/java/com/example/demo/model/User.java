package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY )
   @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String streat;

    @Column
    private String number;

    @Column
    private String eMail;

    @Column
    private String mobile;

}

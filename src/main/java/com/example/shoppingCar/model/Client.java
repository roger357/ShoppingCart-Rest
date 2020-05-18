package com.example.shoppingCar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="CLIENT")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME", nullable=false, length=100)
    private String name;

    @Column(name="DOCUMENT", nullable=false, length=50)
    private String document;

    public Client(String name, String document) {
        this.name = name;
        this.document = document;
    }

}

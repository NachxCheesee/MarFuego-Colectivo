package com.example.pedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name="pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
    //platoid
}

package com.example.producto.model;

import com.example.producto.enums.TipoDeProducto;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private double precio;
    @Enumerated(EnumType.STRING)
    private TipoDeProducto tipo;
//stock

    public Producto() {
    }

    public Producto(Long id, String nombre, double precio, TipoDeProducto tipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoDeProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeProducto tipo) {
        this.tipo = tipo;
    }
}

package com.example.msplato.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "platos")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long localId;
    private String nombre;
    private double precioVenta;
    private boolean disponible;
    @ElementCollection // Crea una tabla automática platos_producto_ids
    @CollectionTable(name = "plato_productos", joinColumns = @JoinColumn(name = "plato_id"))//configuramos esa tabla, le decimos que cree una columna plato_id
    // para que sepa a que plato va a pertenecer cada ingredientes
    @Column(name = "producto_id")//es la columna de nombre producto id que va a guardar los numeros de los ids
    private List<Long> productoId = new ArrayList<>();

    public Plato() {
    }

    public Plato(Long id, Long localId, String nombre, double precioVenta, boolean disponible, List<Long> productoId) {
        this.id = id;
        this.localId = localId;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.disponible = disponible;
        this.productoId = productoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Long> getProductoId() {
        return productoId;
    }

    public void setProductoId(List<Long> productoId) {
        this.productoId = productoId;
    }
}

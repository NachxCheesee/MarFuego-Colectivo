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
    private double precioVenta;
    private boolean disponibilidad;
    @ElementCollection // Crea una tabla automática platos_producto_ids
    @CollectionTable(name = "plato_productos", joinColumns = @JoinColumn(name = "plato_id"))
    @Column(name = "producto_id")
    private List<Long> productoIds = new ArrayList<>();

    public Plato() {
    }

    public Plato(Long id, Long localId, double precioVenta, boolean disponibilidad, List<Long> productoIds) {
        this.id = id;
        this.localId = localId;
        this.precioVenta = precioVenta;
        this.disponibilidad = disponibilidad;
        this.productoIds = productoIds;
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

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Long> productoIds) {
        this.productoIds = productoIds;
    }
}
